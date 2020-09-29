package com.mt.government.mapper;

import com.mt.government.model.CommonTaskInfo;
import com.mt.government.common.config.MyMapper;
import com.mt.government.model.vo.MyTaskListVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommonTaskInfoMapper extends MyMapper<CommonTaskInfo> {
    /**
     * 查询该用户接收到的任务列表
     *
     * @param userId 用户id
     * @return 查询结果
     */
    List<MyTaskListVo> selectMyTaskListVo(@Param("userId") String userId, @Param("status") Integer status, @Param("title") String title);
}
