package com.mt.government.service.impl;

import com.mt.government.mapper.DepartmentMapper;
import com.mt.government.model.Department;
import com.mt.government.service.DepartmentService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Resource
    private DepartmentMapper departmentMapper;

    /**
     * 获取部门信息列表
     *
     * @param department
     * @return
     */
    @Override
    public List<Department> List(Department department) {
        RowBounds page = new RowBounds(0, 10);
        return departmentMapper.selectByExampleAndRowBounds(getExample(department), page);
    }

    /**
     * 新增部门信息
     *
     * @param department
     */
    @Override
    public void insert(Department department) {
        departmentMapper.insert(department);
    }

    /**
     * 修改部门信息
     *
     * @param department
     */
    @Override
    public void updateByExample(Department department) {
        departmentMapper.updateByExample(department, getExample(department));
    }

    /**
     * 删除部门信息
     *
     * @param department
     */
    @Override
    public void deleteByExample(Department department) {
        departmentMapper.deleteByExample(getExample(department));
    }

    /**
     * 封装Example对象逻辑处理
     *
     * @param department
     * @return
     */
    private Example getExample(Department department) {
        Example example = new Example(Department.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("deptId" , department.getDeptId());
        criteria.andEqualTo("deptName" , department.getDeptName());
        criteria.andEqualTo("deptNo" , department.getDeptNo());
        return example;
    }
}
