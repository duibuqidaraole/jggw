package com.mt.government.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.mt.government.common.exception.GlobalException;
import com.mt.government.common.util.FileUtils;
import com.mt.government.mapper.CommonTaskInfoMapper;
import com.mt.government.model.CommonTaskInfo;
import com.mt.government.model.DeductedConfig;
import com.mt.government.model.TaskUserRelationship;
import com.mt.government.model.User;
import com.mt.government.model.dto.PublishTaskDto;
import com.mt.government.model.dto.Receivers;
import com.mt.government.service.ComTaskService;
import com.mt.government.service.TaskUserRelationshipService;
import com.mt.government.utils.Result;
import com.mt.government.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * 任务controller
 *
 * @author 程序鬼才
 * @version 1.0
 * @date 2019/8/19 10:57
 */
@RestController
@RequestMapping("/comTask")
@CrossOrigin
public class ComTaskController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ComTaskController.class);

    @Resource
    private ComTaskService comTaskService;
    @Resource
    private TaskUserRelationshipService taskUserRelationshipService;
    @Resource
    private CommonTaskInfoMapper commonTaskInfoMapper;

    /**
     * 发布普通任务
     *
     * @return 任务信息
     */
    @PostMapping(value = "/addComTask")
    public Result addComTask(@RequestBody PublishTaskDto commonTaskInfo) {
        if (commonTaskInfo.getReceivers() == null || commonTaskInfo.getReceivers().length == 0) {
            return ResultUtil.error("请选择任务接收人！");
        }
        int ref = comTaskService.addComTask(commonTaskInfo.getCommonTaskInfo(), commonTaskInfo.getReceivers());
        if (ref < 0) {
            return ResultUtil.error("发布失败");
        }
        return ResultUtil.success("发布成功");
    }

    /**
     * 用户需要处理的任务列表
     *
     * @param user 用户信息
     * @return 查询结果
     */
    @PostMapping("comTaskList")
    public Result comTaskList(User user) {
        if (StrUtil.isEmpty(user.getUserId())) {
            return ResultUtil.error("用户id不能为空");
        }

        //在用户和任务的关联表中查询此用户的任务
        return ResultUtil.success(comTaskService.findComTaskByUserId(user));
    }

    /**
     * 任务列表
     *
     * @param commonTaskInfo 查询信息
     * @return 查询结果
     */
    @PostMapping("List")
    public Result List(CommonTaskInfo commonTaskInfo, Integer page, Integer pageSize) {
        return ResultUtil.success(comTaskService.List(commonTaskInfo, page, pageSize));
    }

    /**
     * 删除删除任务
     *
     * @param id 任务id
     * @return 删除记录数
     */
    @GetMapping("/delete")
    public Result delete(Integer id) {
        if (id == null || id == 0) {
            return ResultUtil.error("id不能为空");
        }
        return ResultUtil.success(comTaskService.delete(id));
    }

    @PostMapping("/update")
    public Result update(@RequestBody PublishTaskDto commonTaskInfo) {
        if (commonTaskInfo.getReceivers() == null || commonTaskInfo.getReceivers().length == 0) {
            return ResultUtil.error("任务接收人不能为空");
        }
        return ResultUtil.success(comTaskService.update(commonTaskInfo));
    }

    /**
     * 根据ID获取任务详情
     *
     * @param taskId 任务id
     * @return 查询结果
     */
    @GetMapping("getById")
    public Result getById(Integer taskId) {
        if (taskId == null || taskId == 0) {
            return ResultUtil.error("ID不能为空");
        }
        return ResultUtil.success(comTaskService.getById(taskId));
    }

    /**
     * 获取任务提交情况列表
     *
     * @param taskId 任务id
     * @param status 提交状态
     * @return 查询结果
     */
    @GetMapping("/getTaskDetail")
    public Result getTaskDetail(Integer taskId, Integer status) {
        if (taskId == null) {
            return ResultUtil.error("参数错误");
        }
        System.err.println(status);
        return ResultUtil.success(comTaskService.findCommonTaskDetail(taskId, status));
    }

    /**
     * 获取任务发布列表
     *
     * @return 发布结果
     */
    @PostMapping("TaskUserList")
    public Result TaskUserList(String userId, Integer status, String title, Integer page, Integer pageSize) {
        if (StringUtils.isEmpty(userId)) {
            return ResultUtil.error("用户ID不能为空");
        }
        return ResultUtil.success(taskUserRelationshipService.TaskUserList(userId, status, title, page, pageSize));
    }

    /**
     * 将提交任务的附件打包下载
     *
     * @param taskId 提交任务的id
     * @return
     */
    @GetMapping("/packageDownload")
    public void packageDownload(Integer taskId, HttpServletResponse response, HttpServletRequest request) {
        File file = comTaskService.zipAttachment(taskId);
        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setHeader("Content-Disposition",
                    "attachment;fileName=" + FileUtils.setFileDownloadHeader(request, file.getName()));

            FileUtils.writeBytes(file.getAbsolutePath(), response.getOutputStream());
        } catch (Exception e) {
            LOGGER.error("下载文件失败", e);
        }
    }

    /**
     * 导出任务汇总数据
     *
     * @param taskId 任务id
     * @return 导出结果
     */
    @GetMapping("/summary")
    public Result exportSummaryData(int taskId, HttpServletResponse response, HttpServletRequest request) {
        String fileName = "汇总.xlsx";
        // 设置响应头
        response.setContentType("application/octet-stream;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        try {
            response.setHeader("Content-Disposition",
                    "attachment;fileName=" + FileUtils.setFileDownloadHeader(request, fileName));
        } catch (UnsupportedEncodingException e) {
            throw new GlobalException("文件转码失败，请稍后重试", -1);
        }
        OutputStream out;
        ExcelWriter writer;
        try {
            // 获取输出流
            out = response.getOutputStream();
            // 获取excel数据
            writer = comTaskService.summaryData(taskId);
            // 将文件信息导出到输出流
            writer.flush(out);
            // 输出数据
            out.flush();

            writer.close();
            IoUtil.close(out);
            return null;
        } catch (IOException e) {
            LOGGER.error("任务汇总失败", e);
            return ResultUtil.error("数据导出失败");
        }
    }
}
