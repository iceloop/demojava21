package com.example.demojava21;


import com.example.demojava21.service.AsyncService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;

/**
 * testAsync
 * <p>
 * 创建人：hrniu
 * 创建日期：2024/1/2
 */
@SpringBootTest
public class TestAsync {

    @Autowired
    private AsyncService asyncService;
    @Test
    public void testAsync() throws InterruptedException {
        long start = System.currentTimeMillis();
        int n = 1000;
        CountDownLatch countDownLatch = new CountDownLatch(n);
        for (int i = 0; i < n; i++) {
            asyncService.doSomething(countDownLatch);
        }
        countDownLatch.await();
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - start) + "ms");
    }


}

