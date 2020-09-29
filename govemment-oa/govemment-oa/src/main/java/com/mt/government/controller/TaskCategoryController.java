package com.mt.government.controller;

import cn.hutool.core.util.StrUtil;
import com.mt.government.model.TaskCategory;
import com.mt.government.service.TaskCateService;
import com.mt.government.utils.Result;
import com.mt.government.utils.ResultUtil;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 程序鬼才
 * @version 1.0
 * @date 2019/8/19 11:13
 * 任务分类接口
 */
@RestController
@RequestMapping("taskCate")
@CrossOrigin
public class TaskCategoryController {
    @Resource
    private TaskCateService taskCateService;

    /**
     * 添加任务分类
     *
     * @param taskCategory
     * @return
     */
    @RequestMapping("addTaskCate")
    public Result addTaskCate(TaskCategory taskCategory) {

        if (StrUtil.isEmpty(taskCategory.getName())) {
            return ResultUtil.error("分类名称不能为空");
        }
        int ref = taskCateService.addTaskCate(taskCategory);

        if (ref < 0) {
            return ResultUtil.error("添加失败");
        }
        return ResultUtil.success("添加成功");
    }

    /**
     * 更新任务分类的状态
     *
     * @param taskCategory
     * @return
     */
    @RequestMapping("updateTaskCate")
    public Result updateTaskCate(TaskCategory taskCategory) {
        if (taskCategory.getId() == null) {
            return ResultUtil.error("分类id不能为空");
        }

        int ref = taskCateService.updateTaskCate(taskCategory);
        if (ref < 0) {
            return ResultUtil.error("更新失败");
        }
        return ResultUtil.success("更新成功");
    }
}
