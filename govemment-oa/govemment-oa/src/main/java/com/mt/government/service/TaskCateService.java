package com.mt.government.service;

import com.mt.government.model.TaskCategory;

/**
 * @author 程序鬼才
 * @version 1.0
 * @date 2019/8/19 11:33
 */
public interface TaskCateService {

    /**
     * 添加任务类型
     *
     * @param taskCategory
     * @return
     */
    int addTaskCate(TaskCategory taskCategory);

    /**
     * 更新任务分类的状态
     *
     * @param taskCategory
     * @return
     */
    int updateTaskCate(TaskCategory taskCategory);
}
