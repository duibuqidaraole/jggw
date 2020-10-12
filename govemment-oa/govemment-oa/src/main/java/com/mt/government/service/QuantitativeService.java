package com.mt.government.service;

import com.mt.government.model.Quantitative;

import java.util.List;

/**
 * 换届信息 业务层接口
 *
 * @author fuzhigang
 * @date 2019-10-15
 */
public interface QuantitativeService {

    /**
     * 根据id查询换届信息
     *
     * @param id 记录id
     * @return 查询结果
     */
    Quantitative findOne(Integer id);

    /**
     * 查询所有换届信息
     *
     * @return 查询结果
     */
    List<Quantitative> findAll();

    /**
     * 根据用户id查询换届信息
     *
     * @param userId 用户id
     * @return 查询结果
     */
    List<Quantitative> findByUserId(String userId,String partySecretary);

    /**
     * 添加换届信息
     *
     * @param quantitative 换届信息
     * @return 插入结果
     */
    int add(Quantitative quantitative);

    /**
     * 修改换届信息
     *
     * @param quantitative 换届信息
     * @return 修改结果
     */
    int update(Quantitative quantitative);

    /**
     * 删除换届信息
     *
     * @param id 记录id
     * @return 删除结果
     */
    int delete(Integer id);

    /**
     * 更新换届提醒信息
     * @param userId 用户id
     */
    int updateQuantitativeFlag(String userId);
}
