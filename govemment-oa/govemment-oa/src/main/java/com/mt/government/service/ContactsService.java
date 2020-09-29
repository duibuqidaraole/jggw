package com.mt.government.service;

import com.mt.government.model.Contacts;
import com.mt.government.model.vo.PagedResult;

import java.util.List;

/**
 * 联系人业务层接口
 *
 * @author fuzhigang
 * @date 2019-10-16
 */
public interface ContactsService {

    /**
     * 查询单个
     *
     * @param id 联系人id
     * @return 查询结果
     */
    Contacts findOne(Integer id);

    /**
     * 根据单位查询 带分页
     *
     * @param userId   用户id
     * @param page     当前页
     * @param pageSize 每页数量
     * @param linkman  联系人名称
     * @return 查询结果
     */
    PagedResult findByUserId(Integer page, Integer pageSize, String userId, String linkman);

    /**
     * 查询所有
     *
     * @return 查询结果
     */
    List<Contacts> findAll();

    /**
     * 添加联系人
     *
     * @param contacts 联系人信息
     * @return 添加条数
     */
    int add(Contacts contacts);

    /**
     * 修改联系人
     *
     * @param contacts 联系人信息
     * @return 修改条数
     */
    int update(Contacts contacts);

    /**
     * 删除联系人
     *
     * @param id 联系人id
     * @return 删除条数
     */
    int delete(Integer id);
}
