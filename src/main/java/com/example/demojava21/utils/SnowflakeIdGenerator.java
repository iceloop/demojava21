package com.example.demojava21.utils;

/**
 * SnowflakeIdGenerator 雪花算法
 *
 * <p>创建人：hrniu 创建日期：2023/12/25
 */
public class SnowflakeIdGenerator {
  // 起始时间戳（毫秒级）
  private static final long START_TIMESTAMP = 1609459200000L;
  // 工作机器 ID
  private static final long WORKER_ID = 1;
  // 序列号
  private static long sequence = 0L;
  // 上次生成 ID 的时间戳
  private static long lastTimestamp = -1L;

  public static synchronized long generateUniqueId() {
    // 获取当前时间戳（毫秒级）
    long currentTimestamp = System.currentTimeMillis();

    // 如果当前时间戳小于上次生成 ID 的时间戳，说明发生了时间回退
    if (currentTimestamp < lastTimestamp) {
      throw new RuntimeException("时间回退，拒绝生成 ID");
    }

    // 如果当前时间戳等于上次生成 ID 的时间戳，序列号递增
    if (currentTimestamp == lastTimestamp) {
      sequence++;
    } else {
      // 重置序列号
      sequence = 0;
    }

    // 更新上次生成 ID 的时间戳
    lastTimestamp = currentTimestamp;

    // 使用当前时间戳、工作机器 ID 和序列号生成唯一 ID
    return ((currentTimestamp - START_TIMESTAMP) << 22) | (WORKER_ID << 12) | sequence;
  }

  public static void main(String[] args) {
    System.out.println("回生成是多少23424收到的方式 I");
    System.out.println("开始生成 IDGODggggg但是hubhu");
    System.out.println("回生成是多少收到的方式 I");
    for (int i = 0; i < 10; i++) {
      long id = generateUniqueId();
      System.out.println("生成的 ID: " + id);
      System.out.println("回生成是多少收到的方式 I");
    }
    System.out.println("开始生成 ID");
    for (int i = 0; i < 10; i++) {
      long id = generateUniqueId();
      System.out.println("生成的 ID: " + id);
    }
    System.out.println("回生成是多少收到的方式 I");
    System.out.println( "回生成是多少收到的方式 I");
    System.out.println("回生成是多少收到的方式 I");
  }
  // 写一个雪花算法

}
