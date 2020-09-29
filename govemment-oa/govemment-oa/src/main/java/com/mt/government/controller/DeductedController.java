package com.mt.government.controller;

import cn.hutool.core.date.DateUtil;
import com.mt.government.model.Deducted;
import com.mt.government.model.DeductedConfig;
import com.mt.government.service.DeductedService;
import com.mt.government.utils.Result;
import com.mt.government.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 扣分控制层
 *
 * @author g
 * @date 2019-11-11 15:08
 */
@RestController
@RequestMapping("/deducted")
@CrossOrigin
public class DeductedController extends BaseController {
    @Autowired
    private DeductedService deductedService;

    /**
     * 查询扣分列表
     *
     * @param startDay 开始日期
     * @param endDay   结束日期
     * @param orgName  单位名称
     * @return 视图列表
     */
    @GetMapping("/listDeducted")
    public Result listDeductedListVo(String startDay, String endDay, Integer orgId, String orgName) {
        Date start = null;
        Date end = null;
        if (startDay != null) {
            start = DateUtil.parse(startDay);
        }
        if (endDay != null) {
            end = DateUtil.parse(endDay);
        }
        return ResultUtil.success(deductedService.listDeductedListVo(start, end, orgId, orgName));
    }

    /**
     * 查看单位扣分详情
     *
     * @param page     分页
     * @param pageSize 分页
     * @param userId   用户id
     * @return 视图列表
     */
    @GetMapping("/listDetail")
    public Result listDetail(Integer page, Integer pageSize, String userId) {
        return ResultUtil.success(deductedService.listDeductedByUser(page, pageSize, userId));
    }

    /**
     * 对任务进行扣分
     *
     * @param deducted 扣分信息
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody Deducted deducted, HttpServletRequest request) {
        deducted.setCreateTime(new Date());
        deducted.setCreateBy(getCurrentUser(request).getUserId());
        deducted.setUpdateTime(deducted.getCreateTime());
        deducted.setUpdateBy(deducted.getCreateBy());
        return ResultUtil.success(deductedService.add(deducted));
    }

    /**
     * 更新扣分信息
     *
     * @param deducted
     * @param request
     * @return
     */
    @PostMapping("/update")
    public Result update(@RequestBody Deducted deducted, HttpServletRequest request) {
        deducted.setUpdateBy(getCurrentUser(request).getUserId());
        deducted.setUpdateTime(new Date());
        return ResultUtil.success(deductedService.update(deducted));
    }

    @GetMapping("/delete")
    public Result delete(Long id) {
        return ResultUtil.success(deductedService.delete(id));
    }

    /**
     * 获取扣分配置
     *
     * @return 查询信息
     */
    @GetMapping("/getDeductedConfig")
    public Result getDeductedConfig() {
        return ResultUtil.success(deductedService.getDeductedConfig());
    }

    /**
     * 保存扣分配置信息
     *
     * @param deductedConfig 扣分配置
     * @return 影响结果
     */
    @PostMapping("/saveDeductedConfig")
    public Result saveDeductedConfig(@RequestBody DeductedConfig deductedConfig) {
        return ResultUtil.success(deductedService.saveDeductedConfig(deductedConfig));
    }


}
