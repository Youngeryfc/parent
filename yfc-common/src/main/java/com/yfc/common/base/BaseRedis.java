package com.yfc.common.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author yfc
 * @date 2019-06-12
 * @description
 * @parmas
 */
@Component
public class BaseRedis {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void setString(String key,Object data){
        setString(key,data,null);
    }

    public void setString(String key,Object data,Long timeOut){
        System.out.println("------");
        if(data instanceof String ){
            String value=(String) data;
            if(timeOut!=null){
                stringRedisTemplate.opsForValue().set(key,value,timeOut, TimeUnit.SECONDS);
            }else{
                stringRedisTemplate.opsForValue().set(key,value);
            }
        }
    }

    public String getString(String key){
        return stringRedisTemplate.opsForValue().get(key);
    }

    public void delKey(String key){
        stringRedisTemplate.delete(key);
    }
}
