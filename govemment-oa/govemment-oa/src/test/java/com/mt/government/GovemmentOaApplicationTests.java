package com.mt.government;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.mt.government.model.dto.Receivers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GovemmentOaApplicationTests {

   /* @Autowired
    RedisUtil redisUtil;*/
    @Resource
    RedisTemplate<String, Receivers> redisTemplate;

    @Test
    public void sSetTest() {
        /*List<String> list = new ArrayList<>();
        for (int i = 10; i < 20; i++) {
            list.add("string" + i);
        }*/
        Set<Receivers> receivers = new HashSet<>();

        Receivers receiver;
        for (int i = 0; i < 5; i++) {
            receiver = new Receivers();
            receiver.setId("receiver" + i);
            receiver.setName("name" + i);
            receivers.add(receiver);
        }
        // String[] list = {"string11", "string21", "string31", "string41", "string51"};
        Receivers[] array = receivers.toArray(new Receivers[0]);
        // redisUtil.sSet("receiver05", array);
        redisTemplate.opsForSet().add("test06", array);
        Set<Receivers> testSet = redisTemplate.opsForSet().members("test06");
        System.out.println(testSet);
    }

}
