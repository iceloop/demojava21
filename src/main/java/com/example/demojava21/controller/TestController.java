package com.example.demojava21.controller;


import com.alibaba.fastjson.JSON;
import com.example.demojava21.domain.Article;
import com.example.demojava21.domain.dto.ArticleDTO;
import com.example.demojava21.service.AsyncService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.valves.JsonAccessLogValve;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * TestController
 * <p>
 * 创建人：hrniu
 * 创建日期：2024/1/2
 */
@RestController
public class TestController {

    @Autowired
    private AsyncService asyncService;

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
     List<ArticleDTO> list= asyncService.getarticleslist();
     List<ArticleDTO> list1= asyncService.getarticleslistfactory();
     List<ArticleDTO> list2= asyncService.getarticleslistmodelmapper();
     List<ArticleDTO> list3= asyncService.getarticleslistbeanutils();

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

}

