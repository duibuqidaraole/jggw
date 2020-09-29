package com.mt.government.common.task;

import cn.hutool.core.date.DateUtil;
import com.mt.government.mapper.QuantitativeMapper;
import com.mt.government.mapper.UserMapper;
import com.mt.government.service.QuantitativeService;
import com.mt.government.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;
import java.util.List;

/**
 * 换届提醒自动任务配置
 *
 * @author fuzhigang
 * @date 2019-10-17
 */
@Configuration
@EnableScheduling
public class QuantitativeSchedule {
    private final Logger LOGGER = LoggerFactory.getLogger(QuantitativeSchedule.class);
    @Autowired
    private QuantitativeService quantitativeService;
    @Autowired
    private UserMapper userMapper;

    /**
     * 每天1点执行，判断换届信息是否到期
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void configureTask() {
        int count = 0;
        // 获取所有用户id
        List<String> users = userMapper.selectAllUserId();

        // 查询所有人最近的换届日期
        for (String userId : users) {
            count += quantitativeService.updateQuantitativeFlag(userId);
            LOGGER.info("用户 : " + userId + "的换届提醒状态更新了");
        }
        LOGGER.info("本次更新： " + count + " 条记录");
    }
}
