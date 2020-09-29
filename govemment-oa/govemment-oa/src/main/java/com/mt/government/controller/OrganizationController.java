package com.mt.government.controller;

import cn.hutool.core.util.StrUtil;
import com.mt.government.common.exception.UserException;
import com.mt.government.model.Organization;
import com.mt.government.service.OrganizationService;
import com.mt.government.utils.Result;
import com.mt.government.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 组织机构管理 controller
 *
 * @author fuzhigang
 * @date 2019/10/10
 */
@CrossOrigin
@RestController
@RequestMapping("/organization")
public class OrganizationController extends BaseController {
    @Autowired
    private OrganizationService organizationService;

    /**
     * 查询单个单位信息
     *
     * @param id 单位id
     * @return 查询结果
     */
    @GetMapping("/findOne")
    public Result findOne(Integer id) {
        if (id == null || id.equals(0)) {
            return ResultUtil.error("参数校验失败，id不能为空");
        }
        return ResultUtil.success(organizationService.findOne(id));
    }

    /**
     * 查询所有单位
     *
     * @return 查询结果
     */
    @GetMapping("/findAll")
    public Result findAll(@RequestParam(defaultValue = "") String orgName, HttpServletRequest request) {
        return ResultUtil.success(organizationService.findAll(getCurrentUser(request).getOrgId(), orgName));
    }

    /**
     * 查询该单位及其下属单位
     *
     * @param orgId 单位id
     */
    @GetMapping("/listChildren")
    public Result listChildren(Integer orgId) {
        return ResultUtil.success(organizationService.getChildren(orgId));
    }

    /**
     * 获取未分配账号列表
     *
     * @param orgId 单位id
     * @return 单位列表
     */
    @GetMapping("/listUnassignedOrg")
    public Result listUnassignedOrg(Integer orgId) {
        return ResultUtil.success(organizationService.listNoAccountOrg(orgId));
    }

    /**
     * 添加单位
     *
     * @param organization 单位信息
     * @return 插入结果
     */
    @PostMapping("/add")
    public Result add(@RequestBody Organization organization, HttpServletRequest request) {
        // 参数校验
        if (StrUtil.isEmpty(organization.getOrgName())) {
            return ResultUtil.error("单位名称不能为空");
        }
        // 设置上级单位id
        if (organization.getParent().getParentId() != null) {
            organization.setParentId(organization.getParent().getParentId());
        } else {
            organization.setParentId(getCurrentUser(request).getOrgId());
        }
        int ref = organizationService.add(organization);
        return ResultUtil.success(ref);
    }

    /**
     * 更新单位
     *
     * @param organization 单位信息
     * @return 更新结果
     */
    @PostMapping("/update")
    public Result update(@RequestBody Organization organization) {
        if (organization.getParentId() == null) {
            organization.setParentId(0);
        }
        // 参数校验
        if (StrUtil.isEmpty(organization.getOrgName())) {
            return ResultUtil.error("单位名称不能为空");
        }
        // 设置上级单位id
        organization.setParentId(organization.getParent().getParentId());
        int ref = organizationService.update(organization);
        return ResultUtil.success(ref);
    }

    /**
     * 删除单个单位
     *
     * @param id
     * @return
     */
    @GetMapping("/delete")
    public Result delete(Integer id) {
        return ResultUtil.success(organizationService.delete(id));
    }

    /**
     * 批量删除单位
     *
     * @param ids
     * @return
     */
    @PostMapping("/delete")
    public Result delete(int[] ids) {
        return ResultUtil.success(organizationService.delete(ids));
    }

}
