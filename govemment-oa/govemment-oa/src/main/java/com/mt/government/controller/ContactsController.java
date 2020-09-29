package com.mt.government.controller;

import cn.hutool.core.util.StrUtil;
import com.mt.government.model.Contacts;
import com.mt.government.service.ContactsService;
import com.mt.government.utils.Result;
import com.mt.government.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 联系人控制层
 *
 * @author fuzhigang
 * @date 2019-10-16
 */
@CrossOrigin
@RestController
@RequestMapping("/contacts")
public class ContactsController {
    @Autowired
    private ContactsService contactsService;

    /**
     * 查询单个联系人信息
     *
     * @param id 联系人id
     * @return 查询结果
     */
    @GetMapping("/findOne")
    public Result findOne(Integer id) {
        if (id == null || id == 0) {
            return ResultUtil.error("error");
        }
        return ResultUtil.success(contactsService.findOne(id));
    }

    /**
     * 查询所有
     *
     * @return
     */
    @GetMapping("/findAll")
    public Result findAll() {
        return ResultUtil.success(contactsService.findAll());
    }

    /**
     * 根据单位分页查询
     *
     * @param page     当前页
     * @param pageSize 每页数量
     * @param userId   单位id
     * @param linkman  联系人名称
     * @return 分页结果
     */
    @GetMapping("/list")
    public Result findByUserId(Integer page, Integer pageSize, String userId, @RequestParam(defaultValue = "") String linkman) {
        return ResultUtil.success(contactsService.findByUserId(page, pageSize, userId, linkman));
    }

    /**
     * 添加联系人
     *
     * @param contacts 联系人信息
     * @return 添加条数
     */
    @PostMapping("/add")
    public Result add(@RequestBody Contacts contacts) {
        if (StrUtil.isEmpty(contacts.getLinkman())) {
            return ResultUtil.error("联系人名称不能为空");
        }
        return ResultUtil.success(contactsService.add(contacts));
    }

    /**
     * 更新联系人
     *
     * @param contacts
     * @return
     */
    @PostMapping("/update")
    public Result update(@RequestBody Contacts contacts) {
        if (StrUtil.isEmpty(contacts.getLinkman())) {
            return ResultUtil.error("联系人名称不能为空");
        }
        return ResultUtil.success(contactsService.update(contacts));
    }

    @GetMapping("/delete")
    public Result delete(Integer id) {
        if (id == null || id == 0) {
            return ResultUtil.error("error");
        }
        return ResultUtil.success(contactsService.delete(id));
    }
}
