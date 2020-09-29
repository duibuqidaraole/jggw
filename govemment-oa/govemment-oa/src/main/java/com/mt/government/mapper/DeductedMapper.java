package com.mt.government.mapper;

import com.mt.government.common.config.MyMapper;
import com.mt.government.model.Deducted;
import com.mt.government.model.vo.DeductedDetailVo;
import com.mt.government.model.vo.DeductedListVo;
import org.apache.ibatis.annotations.Param;

import javax.xml.stream.events.EndDocument;
import java.util.Date;
import java.util.List;

public interface DeductedMapper extends MyMapper<Deducted> {
    /**
     * 查询单位扣分列表
     *
     * @param startDay 开始日期
     * @param endDay   结束日期
     * @param orgName  单位名称
     * @return 查询列表
     */
    List<DeductedListVo> selectDeductedListVo(@Param("startDay") Date startDay,
                                              @Param("endDay") Date endDay,
                                              @Param("orgId") Integer orgId,
                                              @Param("orgName") String orgName);

    /**
     * 查询扣分详情列表
     *
     * @param userId 用户id
     * @return 查询列表
     */
    List<DeductedDetailVo> selectDeductedDetailVo(@Param("userId") String userId);
}