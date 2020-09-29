package com.mt.government.service;

import com.mt.government.model.DeductedConfig;
import com.mt.government.model.TaskSubmitInfo;
import com.mt.government.model.User;
import com.mt.government.model.vo.MySubmitListVo;
import com.mt.government.model.vo.PagedResult;

import java.util.List;

/**
 * @author 程序鬼才
 * @version 1.0
 * @date 2019/8/19 17:22
 */
public interface SubmitTaskService {

    /**
     * 分页查询用户提交的任务列表
     *
     * @param page     分页
     * @param pageSize 分页
     * @param userId   用户id
     * @param title    提交任务标题
     * @return 视图结果
     */
    PagedResult findByUserId(Integer page, Integer pageSize, String userId, String title);

    /**
     * 提交任务
     *
     * @param taskSubmitInfo 任务信息
     * @return
     */
    int addSubmitTask(TaskSubmitInfo taskSubmitInfo);

    /**
     * 根据id查找提交任务
     *
     * @param id 提交任务id
     * @return
     */
    TaskSubmitInfo findOne(int id);

    /**
     * 更新任务信息
     *
     * @param taskSubmitInfo 修改后的任务信息
     * @return
     */
    int update(TaskSubmitInfo taskSubmitInfo);
}
