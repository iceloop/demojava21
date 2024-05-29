package com.example.demojava21.test;


/**
 * TestAnimal
 * <p>
 * 创建人：hrniu
 * 创建日期：2024/1/11
 */
public class TestAnimal {
    public static void main(String[] args) {
        Animal myAnimal;

        myAnimal = new Dog();
        myAnimal.sound(); // 输出 "汪汪汪！"

        myAnimal = new Cat();
        myAnimal.sound(); // 输出 "喵喵喵！"

        if(2==2 && 2==2 || 3==1){
            System.out.println("1==2 && 2==2");
        }
    }

}

