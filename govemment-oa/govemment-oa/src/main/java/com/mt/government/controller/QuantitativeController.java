package com.mt.government.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.mt.government.model.Quantitative;
import com.mt.government.model.User;
import com.mt.government.service.QuantitativeService;
import com.mt.government.service.UserService;
import com.mt.government.utils.RedisUtil;
import com.mt.government.utils.Result;
import com.mt.government.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 换届信息控制层
 *
 * @author fuzhigang
 * @date 2019-10-15
 */
@RestController
@RequestMapping("/quantitative")
@CrossOrigin
public class QuantitativeController {
    @Autowired
    private QuantitativeService quantitativeService;
    @Autowired
    UserService userService;
    @Autowired
    RedisUtil redisUtil;
    @Value("${token.prefix}")
    private String TOKEN_PREFIX;


    /**
     * 根据id获取换届信息
     *
     * @param id id
     * @return 查询结果
     */
    @GetMapping("/findOne")
    public Result findOne(Integer id) {
        return ResultUtil.success(quantitativeService.findOne(id));
    }

    /**
     * 查询所有
     *
     * @return 查询结果
     */
    @GetMapping("/findAll")
    public Result findAll() {
        return ResultUtil.success(quantitativeService.findAll());
    }

    /**
     * 根据用户id查询换届信息
     *
     * @param userId 用户id
     * @return 查询结果
     */
    @GetMapping("/findByUserId")
    public Result findByUserId(String userId,String partySecretary) {
        if (userId == null) {
            return ResultUtil.error("操作失败");
        }
        return ResultUtil.success(quantitativeService.findByUserId(userId,partySecretary));
    }

    /**
     * 添加换届信息
     *
     * @param quantitative 换届信息
     * @return 插入行数
     */
    @PostMapping("/add")
    public Result add(@RequestBody Quantitative quantitative) {
        // 校验数据
        if (StrUtil.isEmpty(quantitative.getPartyName()) || StrUtil.isEmpty(quantitative.getPartySecretary())) {
            return ResultUtil.error("党组织名称和党支部书记不能为空");
        }
        int ref = quantitativeService.add(quantitative);
        if (ref > 0) {
            return ResultUtil.success(ref);
        }
        return ResultUtil.error("添加失败，请检查您的数据是否正确");
    }

    /**
     * 修改换届信息
     *
     * @param quantitative 换届信息
     * @return 更新行数
     */
    @PostMapping("/update")
    public Result update(@RequestBody Quantitative quantitative, HttpServletRequest request) {
        // 校验数据
        if (StrUtil.isEmpty(quantitative.getPartyName()) || StrUtil.isEmpty(quantitative.getPartySecretary())) {
            return ResultUtil.error("党组织名称和党支部书记不能为空");
        }
        int ref = quantitativeService.update(quantitative);

        if (ref > 0) {
            // 更新用户 token 信息
            User user = userService.findById(quantitative.getUserId());
            redisUtil.set(TOKEN_PREFIX + request.getHeader("token"), JSON.toJSONString(user), 1800L);
            return ResultUtil.success(ref);
        }
        return ResultUtil.error("更新失败，请检查您的数据是否正确");
    }

    /**
     * 删除换届信息
     *
     * @param id 换届id
     * @return 影响行数
     */
    @GetMapping("/delete")
    public Result delete(Integer id) {
        if (id == null || id == 0) {
            return ResultUtil.error("操作失败");
        }
        int ref = quantitativeService.delete(id);
        if (ref > 0) {
            return ResultUtil.success(ref);
        }
        return ResultUtil.error("删除失败");
    }
}
