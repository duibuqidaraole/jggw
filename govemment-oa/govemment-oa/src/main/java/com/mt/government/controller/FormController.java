package com.mt.government.controller;

import com.mt.government.model.Form;
import com.mt.government.service.FormService;
import com.mt.government.utils.Result;
import com.mt.government.utils.ResultUtil;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

@CrossOrigin
@RestController
@RequestMapping("/form")
public class FormController {

    @Resource
    private FormService formService;

    /**
     * 添加表单记录
     *
     * @return
     */
    @PostMapping("Insert")
    public Result Insert(Form form) {
        if (StringUtils.isEmpty(form.getData())) {
            return ResultUtil.error("内容不能为空");
        }
        if (StringUtils.isEmpty(form.getAddTime())) {
            form.setAddTime(new Date());
        }
        formService.Insert(form);
        return ResultUtil.success();
    }

    /**
     * 获取表单记录列表
     *
     * @return
     */
    @PostMapping("List")
    public Result List() {
        return ResultUtil.success(formService.List());
    }

    /**
     * 根据ID删除记录
     *
     * @param form
     * @return
     */
    @PostMapping("Delete")
    public Result Delete(Form form) {
        if (StringUtils.isEmpty(form.getId())) {
            return ResultUtil.error("ID不能为空");
        }
        formService.Delete(form);
        return ResultUtil.success();
    }

    /**
     * 根据ID获取记录
     *
     * @return
     */
    @PostMapping("GetById")
    public Result GetById(Form form) {
        if (StringUtils.isEmpty(form.getId())) {
            return ResultUtil.error("ID不能为空");
        }

        return ResultUtil.success(formService.GetById(form));
    }
}
