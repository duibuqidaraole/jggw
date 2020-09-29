package com.mt.government.mapper;

import com.mt.government.common.config.MyMapper;
import com.mt.government.model.Deducted;
import com.mt.government.model.Dues;
import com.mt.government.model.vo.DeductedDetailVo;
import com.mt.government.model.vo.DeductedListVo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface DuesMapper extends MyMapper<Dues> {

    /**
     * 查看该用户最新的一条党费信息记录
     *
     * @param userId 用户id
     * @return 查询记录
     */
    Dues selectLatestRecordByUserId(@Param("userId") String userId);
}