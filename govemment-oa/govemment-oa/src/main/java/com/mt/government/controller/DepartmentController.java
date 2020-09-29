package com.mt.government.controller;


import com.mt.government.enums.NoticeStatusEnum;
import com.mt.government.model.Department;
import com.mt.government.service.DepartmentService;
import com.mt.government.utils.Result;
import com.mt.government.utils.ResultUtil;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 部门信息接口
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/department")
public class DepartmentController {
    @Resource
    private DepartmentService departmentService;

    /**
     * 获取部门信息列表
     *
     * @param department
     * @return
     */
    @PostMapping("/List")
    public Result List(Department department) {
        return ResultUtil.success(departmentService.List(department));
    }

    /**
     * 新增部门信息
     *
     * @param department
     * @return
     */
    @PostMapping("/Insert")
    public Result Insert(Department department) {
        if (StringUtils.isEmpty(department.getSuperiorDept())) {
            department.setSuperiorDept(NoticeStatusEnum.NOSHOW.getCode());
        }
        if (StringUtils.isEmpty(department.getDeptName())) {
            return ResultUtil.error("部门名称不能为空");
        }
        departmentService.insert(department);
        return ResultUtil.success();
    }

    /**
     * 修改部门信息
     *
     * @param department
     * @return
     */
    @PostMapping("/Update")
    public Result Update(Department department) {
        if (StringUtils.isEmpty(department.getDeptId())) {
            return ResultUtil.error("部门ID不能为空");
        }
        departmentService.updateByExample(department);
        return ResultUtil.success();
    }

    /**
     * 删除部门
     *
     * @param department
     * @return
     */
    @PostMapping("/Delete")
    public Result Delete(Department department) {
        if (StringUtils.isEmpty(department.getDeptId())) {
            return ResultUtil.error("部门ID不能为空");
        }

        departmentService.deleteByExample(department);
        return ResultUtil.success();
    }

}
