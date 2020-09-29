package com.mt.government.mapper;

import com.mt.government.model.Quantitative;
import com.mt.government.common.config.MyMapper;

import java.util.Date;

public interface QuantitativeMapper extends MyMapper<Quantitative> {
    /**
     * 获取该用户最近的换届时间
     *
     * @param userId 用户id
     * @return 换届日期
     */
    Date selectEndDayByUserId(String userId);
}