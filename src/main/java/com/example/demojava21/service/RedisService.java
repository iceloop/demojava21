package com.example.demojava21.service;

import com.example.demojava21.utils.SnowflakeIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * RedisService
 *
 * <p>创建人：hrniu 创建日期：2024/1/23
 */
@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void storeData() {
        for (int i = 0; i < 100000; i++) {
            String uuid = UUID.randomUUID().toString();
            redisTemplate.opsForValue().set("key" + i, uuid);
        }
    }

    public String retrieveData(int keyIndex) {
        return redisTemplate.opsForValue().get("key" + keyIndex);
    }

    public boolean addToSet(String key, String value) {
        Long result = redisTemplate.opsForSet().add(key, value);
        return result != null && result == 1;
    }
    public void createSetWithTenThousandEntries(String setName) {
        SnowflakeIdGenerator snowflakeIdGenerator = new SnowflakeIdGenerator();
        for (int i = 0; i < 600000; i++) {
            long uniqueData = snowflakeIdGenerator.generateUniqueId();
            redisTemplate.opsForSet().add(setName, String.valueOf(uniqueData));
        }
    }
    public boolean checkIfInSet(String key, String value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }
    public long checkIfInSetAdd(String key, String value) {
        return redisTemplate.opsForSet().add(key, value);
    }
}

