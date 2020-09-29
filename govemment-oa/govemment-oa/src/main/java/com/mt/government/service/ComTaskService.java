package com.mt.government.service;

import cn.hutool.poi.excel.ExcelWriter;
import com.mt.government.model.CommonTaskInfo;
import com.mt.government.model.DeductedConfig;
import com.mt.government.model.TaskUserRelationship;
import com.mt.government.model.User;
import com.mt.government.model.dto.PublishTaskDto;
import com.mt.government.model.dto.Receivers;
import com.mt.government.model.vo.PagedResult;
import com.mt.government.model.vo.CommonTaskDetailVo;
import com.mt.government.model.vo.PagedResult;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author 程序鬼才
 * @version 1.0
 * @date 2019/8/19 14:52
 */
public interface ComTaskService {

    /**
     * 添加普通任务
     *
     * @param commonTaskInfo 任务信息
     * @return
     */
    int addComTask(CommonTaskInfo commonTaskInfo, Receivers[] receivers);

    /**
     * 通过用户id查询此用户需处理的任务
     *
     * @return
     */
    List<CommonTaskInfo> findComTaskByUserId(User user);


    /**
     * 任务列表
     *
     * @param commonTaskInfo 查询参数
     * @return 查询结果
     */
    PagedResult List(CommonTaskInfo commonTaskInfo, Integer page, Integer pageSize);

    /**
     * 更新任务
     *
     * @param commonTaskInfo 任务信息
     * @return 更新记录
     */
    int update(PublishTaskDto commonTaskInfo);

    /**
     * 删除任务
     *
     * @param id 任务id
     * @return 影响行数
     */
    int delete(Integer id);

    /**
     * 根据ID获取任务详情
     *
     * @param taskId 任务id
     * @return
     */
    Map<String, Object> getById(Integer taskId);

    /**
     * 获取发布任务详细信息
     *
     * @param taskId   发布任务id
     * @param status 提交状态
     * @return
     */
    List<CommonTaskDetailVo> findCommonTaskDetail(int taskId, int status);

    /**
     * 将一个任务下提交的excel表数据汇总到一张表中
     *
     * @param taskId 任务id
     * @return writer
     */
    ExcelWriter summaryData(Integer taskId);

    /**
     * 将指定任务的提交附件打包压缩
     *
     * @param taskId 任务id
     * @return 压缩文件
     */
    File zipAttachment(Integer taskId);

    /**
     * 统计该账号未提交任务
     *
     * @param userId 用户id
     * @return
     */
    long countUnSubmit(String userId);
}