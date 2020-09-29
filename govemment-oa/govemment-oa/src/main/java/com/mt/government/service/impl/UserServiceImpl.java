package com.mt.government.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mt.government.common.exception.UserException;
import com.mt.government.mapper.OrganizationMapper;
import com.mt.government.mapper.UserMapper;
import com.mt.government.model.Organization;
import com.mt.government.model.User;
import com.mt.government.model.vo.PagedResult;
import com.mt.government.model.vo.ReceiversVo;
import com.mt.government.model.vo.TreeVo;
import com.mt.government.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.*;

/**
 * 用户业务层实现类
 *
 * @author fuzhigang
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Autowired
    private OrganizationMapper organizationMapper;

    @Override
    public User findById(String userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    @Override
    public List<User> findAll() {
        return userMapper.selectAll();
    }

    @Override
    public List<Map<String, String>> findUserIdAndUserName() {
        return userMapper.selectUserIdAndOrgName();
    }

    @Override
    public List<User> findByOrg(Integer orgId) {
        // 创建查询对象
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orgId" , orgId);

        return userMapper.selectByExample(example);
    }

    @Override
    public PagedResult findByPage(int page, int pageSize) {
        // 开启分页
        PageHelper.startPage(page, pageSize);
        Example example = new Example(User.class);
        List<User> list = userMapper.selectByExample(example);

        return PagedResult.commonResult(list);
    }

    @Override
    public int add(User user) throws UserException {
        // 判断用户是否存在
        User result = userMapper.selectByPrimaryKey(user.getUserId());
        if (result != null) {
            throw new UserException("账号已存在" , -1);
        }
        // 判断该单位是否已有账号
        Example example = new Example(Organization.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orgId" , user.getOrgId());
        int count = userMapper.selectCountByExample(example);
        if (count > 0) {
            throw new UserException("该单位已有账号" , -1);
        }

        // 密码加密
        String password = SecureUtil.md5(user.getPassword());
        user.setPassword(password);
        // 设置创建时间
        user.setCreateTime(new Date());
        user.setUpdateTime(user.getCreateTime());
        // 设置用户状态
        user.setUserStatus(1);
        user.setQuantitativeFlag(0);
        return userMapper.insert(user);
    }

    @Override
    public int delete(String userId) {
        return userMapper.deleteByPrimaryKey(userId);
    }

    @Override
    public int delete(String[] userIds) {
        int ref = 0;
        for (String id : userIds) {
            ref += userMapper.deleteByPrimaryKey(id);
        }
        return ref;
    }

    @Override
    public int update(User user) throws UserException {
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public int updatePassword(String userId, String oldPassword, String newPassword) throws UserException {
        // 判断旧密码是否正确,如果不正确抛出异常信息
        User user = userMapper.selectByPrimaryKey(userId);
        if (!user.getPassword().equals(SecureUtil.md5(oldPassword))) {
            throw new UserException("修改失败,密码错误" , -1);
        }
        // 原密码正确设置新密码
        user.setPassword(SecureUtil.md5(newPassword));
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public int resetPassword(String userId, String password) {
        User user = userMapper.selectByPrimaryKey(userId);

        user.setPassword(SecureUtil.md5(password));
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public List<TreeVo> getByUserRole(String userId, Integer userRole) {
        // 获取当前用户单位
        User user = userMapper.selectByPrimaryKey(userId);
        // 设置根节点数据
        TreeVo root = new TreeVo();
        root.setId(user.getOrgId());
        root.setName(user.getOrgName());
        root.setPId(0);
        root.setParam1(user.getUserId());
        root.setParam2(1);
        // 获取下级已分配账号的单位设置为茎节点
        List<TreeVo> stems = userMapper.selectAssignedOrgAsTree(user.getOrgId());
        root.setChildren(stems);
        // 如果是顶级账号，遍历茎节点获取叶节点
        if (user.getUserRole().equals(0)) {
            for (TreeVo tree : stems) {
                if (tree.getParam2().equals(1)) {
                    tree.setChildren(userMapper.selectAssignedOrgAsTree(tree.getId()));
                }
            }
        }

      /*  Integer parentId = null;
        // 获取该用户的单位
        if(StrUtil.isNotBlank(userId)) {
            User user = userMapper.selectByPrimaryKey(userId);
            Example example = new Example(Organization.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("orgId", user.getOrgId());
            Organization organization = organizationMapper.selectOneByExample(example);
            parentId = organization.getOrgId();
        }

        // 获取该用户单位及其下属单位的信息及账号名
        List<ReceiversVo> list = organizationMapper.findUserIdAndChildren(userId, parentId, userRole);
        // 将当前用户的parentId设置为0匹配前端显示数据
        for(ReceiversVo vo : list) {
            if(vo.getUserId().equals(userId)) {
                vo.setParentId(0);
            }
        }*/
        List<TreeVo> result = new ArrayList<>(1);
        result.add(root);
        return result;
    }

    @Override
    public List<ReceiversVo> findReceivers(String userId, Integer userRole) {
        // 获取该用户所属单位
        User user = userMapper.selectByPrimaryKey(userId);
        Example example = new Example(Organization.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orgId" , user.getOrgId());
        Organization organization = organizationMapper.selectOneByExample(example);

        // 获取其相应的下属单位信息
        if (!userId.equals("admin")) {
            userRole = 2;
        }
        List<ReceiversVo> list = organizationMapper.selectByUserRole(userId, organization.getOrgId(), userRole);

        return list;
    }
}
