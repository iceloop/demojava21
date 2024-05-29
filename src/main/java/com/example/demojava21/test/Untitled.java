package com.example.demojava21.test;
public class Untitled {
   // 输出统一社会信用代码,一万条
    public static void main(String[] args) {
         for (int i = 0; i < 10000; i++) {
              System.out.println(generateCreditCode());
         }
    }
    public static String generateCreditCode() {
        // 统一社会信用代码中不使用I、O、Z、S、V
        char[] chars = "0123456789ABCDEFGHJKLMNPQRTUWXY".toCharArray();
        int length = chars.length;
        StringBuilder sb = new StringBuilder();
        // 第1位
        sb.append(chars[(int) (Math.random() * 31)]);
        // 第2位
        sb.append(chars[(int) (Math.random() * length)]);
        // 第3位
        sb.append(chars[(int) (Math.random() * length)]);
        // 第4位
        sb.append(chars[(int) (Math.random() * length)]);
        // 第5位
        sb.append(chars[(int) (Math.random() * length)]);
        // 第6位
        sb.append(chars[(int) (Math.random() * length)]);
        // 第7位
        sb.append(chars[(int) (Math.random() * length)]);
        // 第8位
        sb.append(chars[(int) (Math.random() * length)]);
        // 第9位
        sb.append(chars[(int) (Math.random() * length)]);
        // 第10位
        sb.append(chars[(int) (Math.random() * length)]);
        // 第11位
        sb.append(chars[(int) (Math.random() * length)]);
        // 第12位
        sb.append(chars[(int) (Math.random() * length)]);
        // 第13位
        sb.append(chars[(int) (Math.random() * length)]);
        // 第14位
        sb.append(chars[(int) (Math.random() * length)]);
        // 第15位
        sb.append(chars[(int) (Math.random() * length)]);
        // 第16位
        sb.append(chars[(int) (Math.random() * length)]);
        // 第17位
        sb.append(chars[(int) (Math.random() * length)]);
        return sb.toString();
    }

}
