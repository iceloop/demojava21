package com.example.demojava21.controller;

import com.alibaba.fastjson.JSON;
import com.example.demojava21.domain.Article;
import com.example.demojava21.domain.dto.ArticleDTO;
import com.example.demojava21.service.AsyncService;
import com.example.demojava21.service.RedisService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.valves.JsonAccessLogValve;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * TestController
 *
 * <p>创建人：hrniu 创建日期：2024/1/2
 */
@RestController
public class TestController {

  @Autowired private AsyncService asyncService;
  @Autowired private RedisService redisService;

  @RequestMapping("/get")
  public Object get() throws Exception {
    Thread.sleep(50);

    return HttpStatus.OK;
  }

  @RequestMapping("/getbussinessno")
  public Object get1(@RequestParam(value = "bussinessNo") String bussinessNo) throws Exception {
    System.out.println(bussinessNo);
    System.out.println(Thread.currentThread().toString());
    System.out.println(Thread.currentThread().isVirtual());
    return HttpStatus.OK;
  }

  // 获取articles信息
  @GetMapping("/getarticleslist")
  @ResponseBody
  public List getarticleslist() {
    List<ArticleDTO> list = asyncService.getarticleslist();
    List<ArticleDTO> list1 = asyncService.getarticleslistfactory();
    List<ArticleDTO> list2 = asyncService.getarticleslistmodelmapper();
    List<ArticleDTO> list3 = asyncService.getarticleslistbeanutils();

    printListAsJson(list);
    printListAsJson(list1);
    printListAsJson(list2);
    printListAsJson(list3);

    return list;
  }

  public void printListAsJson(List<ArticleDTO> list) {
    String json = JSON.toJSONString(list, true); // 第二个参数为true时格式化输出
    System.out.println(json);
  }

  // 验证Redis
  @GetMapping("/redis")
  @ResponseBody
  public String redis() {

    redisService.storeData();

    // 仅作为示例，这里查询第50000条数据
    String retrievedData = redisService.retrieveData(50000);

    return "Retrieved data: " + retrievedData;
  }

  @GetMapping("/createSet")
  public String createSet() {
    String setName = "uniqueDataSethaha";
    //输出开始时间
    long startTime = System.currentTimeMillis();
    redisService.createSetWithTenThousandEntries(setName);
    //输出结束时间
    long endTime = System.currentTimeMillis();
    //计算并打印所需时间
    long duration = endTime - startTime;
    System.out.println("执行方法耗时：" + duration + " 毫秒");
    return "Created SET with 600,000 unique entries";
  }
  @GetMapping("/createSet1")
    public String createSet1() throws InterruptedException {
    //输出开始时间
    long startTime = System.currentTimeMillis();
    redisService.createSetWithTenThousandEntriesHr("uniqueDataSethrniu1");
    //输出结束时间
    long endTime = System.currentTimeMillis();
    //计算并打印所需时间
    long duration = endTime - startTime;
    System.out.println("执行方法耗时：" + duration + " 毫秒");
        return "createSet1";
    }

  @GetMapping("/checkValueInSet")
  @ResponseBody
  public String checkValueInSet(@RequestParam String value) {
    String key = "uniqueDataSet";


    // 获取开始时间
    long startTime = System.currentTimeMillis();
    // long isInSet = redisService.checkIfInSetAdd(key, value);
    boolean isInSet = redisService.checkIfInSet(key, value);

    // 获取结束时间
    long endTime = System.currentTimeMillis();
    // 计算并打印所需时间
    long duration = endTime - startTime;
    System.out.println("执行方法耗时：" + duration + " 毫秒");
    //return isInSet == 1 ? "值不存在于集合中" : "值存在集合中";

    return isInSet ? "值存在于集合中" : "值不在集合中";

  }

  @GetMapping("/checkValueInSet1")
  @ResponseBody
  public String checkValueInSet1(@RequestParam String id,@RequestParam String name) {
   return id+name;
  }

}
