package com.example.demojava21.test;

import java.util.HashSet;
import java.util.Random;

public class HashSetPerformance {

  public static void main(String[] args) {
    HashSet<String> hashSet = new HashSet<>();
    Random random = new Random();

    // 插入测试
    long startTime = System.nanoTime();
    for (int i = 0; i < 1000000; i++) {
      hashSet.add(String.valueOf(random.nextInt()));
    }
    hashSet.add("世界的终点");
    long endTime = System.nanoTime();
    System.out.println("插入操作耗时: " + (endTime - startTime) / 1e6 + " 毫秒");

    // 查找测试
    startTime = System.nanoTime();
    // 实现模糊查询
    boolean n = hashSet.contains("的终");
    System.out.println(n);
    endTime = System.nanoTime();
    System.out.println("查找操作耗时: " + (endTime - startTime) / 1e6 + " 毫秒");

    // 删除测试
    startTime = System.nanoTime();
    for (int i = 0; i < 1000000; i++) {
      hashSet.remove(random.nextInt());
    }
    endTime = System.nanoTime();
    System.out.println("删除操作耗时: " + (endTime - startTime) / 1e6 + " 毫秒");
  }
}
