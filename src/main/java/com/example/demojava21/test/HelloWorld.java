package com.example.demojava21.test;


public class HelloWorld {
    public static void main(String[] args) {
        // 使用匿名类实现GreetingService接口
        GreetingService service = new GreetingService() {
            public void sayMessage(String message) {
                System.out.println("Hello " + message);
            }
        };
        String jsonstr="{\"name\":\"张三\",\"age\":18}";
        String jsonstr2= """
                {
                    "name":"张三",
                    "age":18
                }
                """;
        System.out.println(jsonstr);
        System.out.println(jsonstr2);
        service.sayMessage("World");
    }
}

