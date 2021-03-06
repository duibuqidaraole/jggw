package com.mt.government.controller;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.mt.government.model.User;
import com.mt.government.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 通用控制层
 *
 * @author g
 * @date 2019-11-11 15:37
 */
@Controller
public class BaseController {
    @Autowired
    private RedisUtil redisUtil;
    @Value("${token.prefix}")
    private String TOKEN_PREFIX;

    protected User getCurrentUser(HttpServletRequest request) {
        // FIXME: MAY THROW NullPointException
        String token = request.getHeader("token");
        Object o = redisUtil.get(TOKEN_PREFIX + token);
        if (o != null) {
            User user = JSON.parseObject(o.toString(), User.class);
            return user;
        }
        return null;
    }

    protected String getClasspath() throws FileNotFoundException {
        return ResourceUtils.getURL("classpath:").getPath();
    }


    public static void main(String[] args) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parse = simpleDateFormat.parse("2020-08-10 00:00:00");
        Date parse1 = simpleDateFormat.parse("2020-08-11 00:00:00");
        int compare = DateUtil.compare(parse, parse1);
        System.out.println(compare);


    }
}