package com.mt.government.mapper;

import com.mt.government.model.TaskSubmitInfo;
import com.mt.government.model.vo.MySubmitListVo;
import com.mt.government.common.config.MyMapper;

import java.util.List;

public interface TaskSubmitInfoMapper extends MyMapper<TaskSubmitInfo> {

    /**
     * 通过用户id查询用户提交后需审核的任务
     *
     * @param userId
     * @return
     */
    List<TaskSubmitInfo> findSubmitTaskByUserId(String userId);

    /**
     * 查询所有提交任务附件
     *
     * @param taskId 提交任务对应的发布任务
     * @return
     */
    List<String> findSubmitFilesByTaskId(int taskId);

    /**
     * 查询我提交的任务视图列表
     *
     * @param userId 用户id
     * @param title  提交任务标题
     * @return 查询结果
     */
    List<MySubmitListVo> selectMySubmitList(String userId, String title);
}