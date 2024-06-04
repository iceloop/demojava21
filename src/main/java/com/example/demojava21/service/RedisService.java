package com.example.demojava21.service;

import com.example.demojava21.utils.SnowflakeIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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

        for (int i = 0; i < 100000; i++) {
            long uniqueData = snowflakeIdGenerator.generateUniqueId();
            redisTemplate.opsForSet().add(setName, String.valueOf(uniqueData));
        }
    }
    @Async
    public void createSetWithTenThousandEntriesHr(String setName) throws InterruptedException {
        SnowflakeIdGenerator snowflakeIdGenerator = new SnowflakeIdGenerator();
        ExecutorService executor = Executors.newFixedThreadPool(10);
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                for (int j = 0; j < 10000; j++) {
                    long uniqueData = snowflakeIdGenerator.generateUniqueId();
                    redisTemplate.opsForSet().add(setName, String.valueOf(uniqueData));
                }
            }, executor);
            futures.add(future);
        }

        // 等待所有任务完成
        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        allOf.join();

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.HOURS);
    }
    public boolean checkIfInSet(String key, String value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }
    public long checkIfInSetAdd(String key, String value) {
        return redisTemplate.opsForSet().add(key, value);
    }
}

