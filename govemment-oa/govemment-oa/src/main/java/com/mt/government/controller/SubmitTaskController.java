package com.mt.government.controller;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mt.government.mapper.CommonTaskInfoMapper;
import com.mt.government.mapper.TaskSubmitInfoMapper;
import com.mt.government.mapper.UserMapper;
import com.mt.government.model.CommonTaskInfo;
import com.mt.government.model.DeductedConfig;
import com.mt.government.model.TaskSubmitInfo;
import com.mt.government.model.User;
import com.mt.government.model.vo.PagedResult;
import com.mt.government.service.SubmitTaskService;
import com.mt.government.utils.Result;
import com.mt.government.utils.ResultUtil;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 程序鬼才
 * @version 1.0
 * @date 2019/8/19 17:06
 * 提交任务接口
 */
@RestController
@RequestMapping("/submitTask")
@CrossOrigin
public class SubmitTaskController {
    @Resource
    private SubmitTaskService submitTaskService;
    @Resource
    private TaskSubmitInfoMapper taskSubmitInfoMapper;

    /**
     * 获取我提交的任务列表
     *
     * @param page     分页
     * @param pageSize 分页
     * @param userId   当前用户
     * @param title    提交任务标题
     * @return 查询结果
     */
    @GetMapping("/list")
    public Result findByUserId(Integer page, Integer pageSize, String userId, String title) {
        if (StrUtil.isEmpty(title)) {
            title = "";
        }
        return ResultUtil.success(submitTaskService.findByUserId(page, pageSize, userId, title));
    }

    /**
     * 提交任务
     *
     * @param taskSubmitInfo 任务信息
     * @return 插入结果
     */
    @PostMapping("/addSubmitTask")
    public Result addSubmitTask(@RequestBody TaskSubmitInfo taskSubmitInfo) {
        if (StrUtil.isEmpty(taskSubmitInfo.getUserId()) || taskSubmitInfo.getCommonTaskId() == null) {
            return ResultUtil.error("任务id和提交人id不能为空");
        }
        int ref = submitTaskService.addSubmitTask(taskSubmitInfo);

        if (ref < 0) {
            return ResultUtil.error("发布失败");
        }
        return ResultUtil.success("发布成功");
    }

    /**
     * 通过用户id 查询需要审核的任务(近一个月的符合条件的任务)
     *
     * @param user 用户id
     * @return 查询结果
     */
    @PostMapping("/submitTaskList")
    public Result submitTaskList(User user) {
        if (StrUtil.isEmpty(user.getUserId())) {
            return ResultUtil.error("用户id不能为空");
        }

        //查询列表
        return ResultUtil.success(taskSubmitInfoMapper.findSubmitTaskByUserId(user.getUserId()));
    }

    /**
     * 修改任务
     *
     * @param taskSubmitInfo 任务信息
     * @return 修改结果
     */
    @PostMapping("/update")
    public Result update(@RequestBody TaskSubmitInfo taskSubmitInfo) {
        if (StringUtils.isEmpty(taskSubmitInfo.getSubmitId())) {
            return ResultUtil.error("任务ID不能为空");
        }
        submitTaskService.update(taskSubmitInfo);
        return ResultUtil.success();
    }

    /**
     * 根据ID获取任务
     *
     * @param taskSubmitInfo 提交任务对象
     * @return 查询结果
     */
    @GetMapping("/getById")
    public Result getById(TaskSubmitInfo taskSubmitInfo) {
        return ResultUtil.success(taskSubmitInfoMapper.selectByPrimaryKey(taskSubmitInfo.getSubmitId()));
    }
}
