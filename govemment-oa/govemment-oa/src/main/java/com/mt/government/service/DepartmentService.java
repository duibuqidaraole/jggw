package com.mt.government.service;

import com.mt.government.model.Department;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

public interface DepartmentService {
    /**
     * 获取部门信息列表
     *
     * @param department
     * @return
     */
    List<Department> List(Department department);

    /**
     * 新增部门信息
     *
     * @param department
     */
    void insert(Department department);

    /**
     * 修改部门信息
     *
     * @param department
     */
    void updateByExample(Department department);

    /**
     * 删除部门信息
     *
     * @param department
     */
    void deleteByExample(Department department);
}
