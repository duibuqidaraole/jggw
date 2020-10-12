package com.mt.government.mapper;

import com.mt.government.model.TaskUserRelationship;
import com.mt.government.model.vo.CommonTaskDetailVo;
import com.mt.government.common.config.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TaskUserRelationshipMapper extends MyMapper<TaskUserRelationship> {
    /**
     * 查询任务提交情况列表视图
     *
     * @param status 提交状态
     * @param taskId 任务id
     * @return 视图列表
     */
    List<CommonTaskDetailVo> findCommonTaskDetail(@Param("status") int status, @Param("taskId") int taskId);
}