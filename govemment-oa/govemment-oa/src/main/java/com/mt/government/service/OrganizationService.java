package com.mt.government.service;

import com.mt.government.common.exception.UserException;
import com.mt.government.model.Organization;
import com.mt.government.model.vo.PagedResult;
import com.mt.government.model.vo.ReceiversVo;
import com.mt.government.model.vo.TreeVo;

import java.util.List;

/**
 * 机构管理 业务层接口
 *
 * @author fuzhigang
 * @date 2019/10/10
 */
public interface OrganizationService {
    /**
     * 查询下级单位以树形结构返回
     *
     * @param orgId 当前单位id
     * @return 树形结构单位列表
     */
    TreeVo getChildren(Integer orgId);

    /**
     * 查询单个单位信息
     *
     * @param id 单位id
     * @return 查询结果
     */
    Organization findOne(Integer id);

    /**
     * 分页条件查询
     *
     * @param page    当前页
     * @param size    每页数量
     * @param orgName 单位名称
     * @return 分页信息
     */
    PagedResult findByPage(int page, int size, String orgName);

    /**
     * 查询所有单位
     *
     * @param orgId   当前用户单位
     * @param orgName
     * @return 所有单位
     */
    List<TreeVo> findAll(Integer orgId, String orgName);

    /**
     * 查询该单位下级没有分配账号的单位列表
     *
     * @param orgId 当前单位id
     * @return 列表
     */
    List<Organization> listNoAccountOrg(Integer orgId);

    /**
     * 添加单位
     *
     * @param organization
     * @return
     */
    int add(Organization organization) throws UserException;

    /**
     * 更新单位信息
     *
     * @param organization
     * @return
     */
    int update(Organization organization);

    /**
     * 批量删除
     *
     * @param ids 单位id数组
     * @return
     */
    int delete(int[] ids);

    /**
     * 删除单个
     *
     * @param id 单位id
     * @return
     */
    int delete(int id);
}
