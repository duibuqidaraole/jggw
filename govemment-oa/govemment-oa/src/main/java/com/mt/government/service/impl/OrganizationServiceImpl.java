package com.mt.government.service.impl;

import com.github.pagehelper.PageHelper;
import com.mt.government.common.exception.UserException;
import com.mt.government.mapper.OrganizationMapper;
import com.mt.government.mapper.UserMapper;
import com.mt.government.model.Organization;
import com.mt.government.model.User;
import com.mt.government.model.vo.PagedResult;
import com.mt.government.model.vo.Parent;
import com.mt.government.model.vo.TreeVo;
import com.mt.government.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 单位管理业务实现
 */
@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationMapper organizationMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public Organization findOne(Integer id) {
        Organization organization = organizationMapper.selectByPrimaryKey(id);
        // 设置上级单位
        if (organization.getParentId() != 0) {
            Organization parent = organizationMapper.selectByPrimaryKey(organization.getParentId());
            organization.setParent(new Parent(parent.getOrgId(), parent.getOrgName()));
        }

        return organization;
    }

    @Override
    public PagedResult findByPage(int page, int size, String orgName) {
        // 开启分页
        PageHelper.startPage(page, size);
        // 创建查询条件
        Example example = new Example(Organization.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andLike("orgName" , orgName);
        List<Organization> list = organizationMapper.selectByExample(example);
        return PagedResult.commonResult(list);
    }

    @Override
    public List<TreeVo> findAll(Integer orgId, String orgName) {
        Example example = new Example(Organization.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andLike("orgName" , "%" + orgName + "%");
        criteria.andEqualTo("parentId" , orgId);
        List<Organization> list = organizationMapper.selectByExample(example);
        List<TreeVo> result = new LinkedList<>();
        TreeVo tree;
        for (Organization org : list) {
            tree = new TreeVo();
            tree.setId(org.getOrgId());
            tree.setName(org.getOrgName());
            tree.setPId(org.getParentId());
            tree.setParam1(org.getCreateTime());
            result.add(tree);
        }

        return result;
    }

    @Override
    public TreeVo getChildren(Integer orgId) {
        TreeVo tree = new TreeVo();
        // 获取根节点单位
        Organization root = organizationMapper.selectByPrimaryKey(orgId);
        tree.setId(root.getOrgId());
        tree.setName(root.getOrgName());
        tree.setParam1(root.getCreateTime());
        tree.setPId(0);
        // 获取子节点
        List<TreeVo> stems = organizationMapper.selectByParentIdAsTreeNode(orgId);
        tree.setChildren(stems);

        User user = userMapper.findUserByOrdId(orgId);
        if (user.getUserRole().equals(0)) {
            // 遍历设置叶子节点
            for (TreeVo org : stems) {
                // 如果该单位有下级单位，将其下级设置为叶节点
                if (org.getParam2().equals(1)) {
                    org.setChildren(organizationMapper.selectByParentIdAsTreeNode(org.getId()));
                }
            }
        }
        return tree;
    }

    @Override
    public List<Organization> listNoAccountOrg(Integer orgId) {
        return organizationMapper.selectNoAccountOrg(orgId);
    }

    @Override
    public int add(Organization organization) throws UserException {
        // 判断单位名称是否存在
        if (orgNameIsExist(organization.getOrgName())) {
            throw new UserException("单位名称已存在" , -1);
        }
        int ref = 0;
        ref = setParent(organization, ref);

        // 插入下级单位
        organization.setCreateTime(new Date());
        organization.setUpdateTime(organization.getCreateTime());
        ref += organizationMapper.insertSelective(organization);
        return ref;
    }


    @Override
    public int update(Organization organization) {
        // 判断单位名称是否存在
        Example example = new Example(Organization.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andNotEqualTo("orgId" , organization.getOrgId());
        criteria.andEqualTo("orgName" , organization.getOrgName());
        int count = organizationMapper.selectCountByExample(example);
        if (count > 0) {
            throw new UserException("单位名称重复" , -1);
        }
        int ref = 0;
        // 设置更新时间
        organization.setUpdateTime(new Date());
        ref += organizationMapper.updateByPrimaryKeySelective(organization);
        // 更新用户表
        // MAY THROW NullPointException FIXED AT 2020-3-13 14:53
        User user = userMapper.findUserByOrdId(organization.getOrgId());
        if (user != null && !user.getOrgName().equals(organization.getOrgName())) {
            user.setUpdateTime(organization.getUpdateTime());
            user.setOrgName(organization.getOrgName());
            userMapper.updateByPrimaryKey(user);
        }
        return ref;
    }

    @Override
    public int delete(int[] ids) {
        int ref = 0;
        for (int id : ids) {
            ref += organizationMapper.deleteByPrimaryKey(id);
        }
        return ref;
    }

    @Override
    @Transactional
    public int delete(int id) {
        int ref = 0;
        Organization organization = organizationMapper.selectByPrimaryKey(id);
        // 将下级单位和关联账号也全都删除
        List<Organization> list = organizationMapper.findByParentId(id);
        List<Map<String, String>> userList = userMapper.selectUserIdAndOrgName();
        for (Map map : userList) {
            for (Organization o : list) {
                if (map.containsValue(o.getOrgName())) {
                    ref += userMapper.deleteByPrimaryKey(map.get("userId"));
                }
                ref += organizationMapper.deleteByPrimaryKey(o.getOrgId());
            }
            if (map.containsValue(organization.getOrgName())) {
                ref += userMapper.deleteByPrimaryKey(map.get("userId"));
            }
        }

        ref += organizationMapper.deleteByPrimaryKey(id);
        return ref;
    }

    /**
     * 修改上级单位属性
     *
     * @param organization
     * @param ref
     * @return
     */
    private int setParent(Organization organization, int ref) {
        // 判断是否有上级单位
        Integer parentId = organization.getParentId();
        if (parentId != null) {
            // 获取上级单位
            Organization parent = organizationMapper.selectByPrimaryKey(parentId);
            if (parent != null) {
                // 修改上级单位的属性
                if (parent.getIsParent() == 0) {
                    parent.setIsParent(1);
                    parent.setUpdateTime(new Date());
                    ref += organizationMapper.updateByPrimaryKey(parent);
                }
            }
        }
        return ref;
    }

    /**
     * 判断单位名称是否存在
     *
     * @param orgName 单位名称
     * @return
     */
    private Boolean orgNameIsExist(String orgName) {
        // 判断单位名称是否存在
        Example example = new Example(Organization.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orgName" , orgName);
        int count = organizationMapper.selectCountByExample(example);

        return count > 0;
    }
}
