package com.mt.government.common.config.shiro;

import com.alibaba.fastjson.JSON;
import com.mt.government.model.User;
import com.mt.government.service.UserService;
import com.mt.government.utils.RedisUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author g
 * @date 2019-11-01 9:22
 */
public class UserRealm extends AuthorizingRealm {

    @Value("${token.prefix}")
    private String TOKEN_PREFIX;
    @Value("${token.expire}")
    private Long TOKEN_EXPIRE;

    @Autowired
    RedisUtil redisUtil;
    @Autowired
    UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 获取controller传来的登陆信息
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String[] strs = token.getUsername().split("-");
        String username = strs[0];
        String tokenKey = strs[1];
        // 获取用户
        User user = userService.findById(username);
        if (user == null) {
            throw new AuthenticationException("用户名不存在");
        }
        String password = new String(token.getPassword());

        if (!password.equals(user.getPassword())) {
            throw new AuthenticationException("用户名或密码错误");
        }
        redisUtil.set(TOKEN_PREFIX + tokenKey, JSON.toJSONString(user), TOKEN_EXPIRE);
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, getName());

        return info;
    }
}
