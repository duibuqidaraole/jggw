package com.mt.government.controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.mt.government.service.UserService;
import com.mt.government.utils.RedisUtil;
import com.mt.government.utils.Result;
import com.mt.government.utils.ResultUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登陆controller
 *
 * @author fuzhigang
 */
@CrossOrigin
@RestController
public class LoginController {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private UserService userService;
    @Value("${token.prefix}")
    private String TOKEN_PREFIX;
    @Value("${token.expire}")
    private Long TOKEN_EXPIRE;

    /**
     * 登陆
     *
     * @param username 用户名
     * @param password 密码
     * @return 登陆结果
     */
    @RequestMapping("/login")
    public Result login(String username, String password) {
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            return ResultUtil.error("用户名和密码不能为空");
        }
        String tokenKey = IdUtil.fastSimpleUUID();
        username = username + "-" + tokenKey;
        UsernamePasswordToken token = new UsernamePasswordToken(username, SecureUtil.md5(password));
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            return ResultUtil.success(tokenKey);
        } catch (AuthenticationException e) {
            return ResultUtil.error(e.getMessage());
        }
    }

    /**
     * 退出登录方法
     *
     * @param token
     * @return
     */
    @GetMapping("/logout")
    public Result logout(String token) {
        // 删除缓存中的用户信息
        redisUtil.del(TOKEN_PREFIX + token);
        return ResultUtil.success("退出成功");
    }
}
