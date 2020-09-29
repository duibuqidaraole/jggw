package com.mt.government.service.impl;

import com.mt.government.enums.TaskCateStatusEnum;
import com.mt.government.mapper.TaskCategoryMapper;
import com.mt.government.model.TaskCategory;
import com.mt.government.service.TaskCateService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 程序鬼才
 * @version 1.0
 * @date 2019/8/19 11:33
 */
@Service
public class TaskCateServiceImpl implements TaskCateService {
    @Resource
    private TaskCategoryMapper taskCategoryMapper;


    @Override
    public int addTaskCate(TaskCategory taskCategory) {
        //默认设定状态为开启
        taskCategory.setStatus(TaskCateStatusEnum.OPEN.getCode());
        return taskCategoryMapper.insert(taskCategory);
    }

    @Override
    public int updateTaskCate(TaskCategory taskCategory) {
        return taskCategoryMapper.updateByPrimaryKeySelective(taskCategory);
    }
}
