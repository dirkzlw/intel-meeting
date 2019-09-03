package com.intel.meeting;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;

/**
 * @author Ranger
 * @create 2019-09-03 18:39
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = IntelMeetingApplication.class)
public class TestRedis {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Test
    public void testSet(){
        redisTemplate.opsForValue().set("dirkzh-2609542741@qq.com", "654321",10L, TimeUnit.MINUTES);

    }

    @Test
    public void testGet(){
        String key1 = redisTemplate.opsForValue().get("dirkzh-2609542741@qq.com");
        System.out.println("key1 = " + key1);
    }

}
