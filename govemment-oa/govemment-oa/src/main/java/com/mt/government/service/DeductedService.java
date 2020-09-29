package com.mt.government.service;

import com.mt.government.model.Deducted;
import com.mt.government.model.DeductedConfig;
import com.mt.government.model.vo.DeductedListVo;
import com.mt.government.model.vo.PagedResult;

import java.util.Date;
import java.util.List;

/**
 * 扣分信息业务接口
 *
 * @author g
 * @date 2019-11-11 9:56
 */
public interface DeductedService {
    /**
     * 查询单位扣分列表
     *
     * @param startDay 开始日期
     * @param endDay   结束日期
     * @param orgId 当前单位id
     * @param orgName  单位名称
     * @return 查询结果
     */
    List<DeductedListVo> listDeductedListVo(Date startDay, Date endDay, Integer orgId, String orgName);

    /**
     * 查看用户扣分详情
     *
     * @param userId
     * @return
     */
    PagedResult listDeductedByUser(Integer page, Integer pageSize, String userId);

    /**
     * 添加扣分记录
     *
     * @param deducted 记录信息
     */
    int add(Deducted deducted);

    /**
     * 更新扣分记录
     *
     * @param deducted 记录信息
     */
    int update(Deducted deducted);

    /**
     * 删除扣分记录
     *
     * @param id
     */
    int delete(Long id);

    /**
     * 保存自动扣分配置
     *
     * @param deductedConfig 配置信息
     * @return 保存结果
     */
    int saveDeductedConfig(DeductedConfig deductedConfig);

    /**
     * 获取自动扣分配置信息
     */
    DeductedConfig getDeductedConfig();
}
