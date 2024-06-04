package com.example.demojava21.test;

/**
 * FileTest
 *
 * <p>创建人：hrniu 创建日期：2024/6/3
 */
public class FileTest {

    public static void main(String[] args){
        // 编写测试数据
        String data = "食品、饮料、烟草及饲料生产专用设备制造";
        String name = "-";
    System.out.println(cleanNameChat(data));
    }
    private static String cleanName(String name) {
        return name.replaceAll("[^A-Za-z0-9]", "_");
    }
    private static String cleanNameChat(String name) {
        StringBuilder cleanName = new StringBuilder();
        for (char c : name.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                cleanName.append(c);
            } else {
                cleanName.append(Integer.toHexString(c));
            }
        }
        return cleanName.toString();
    }
    // 获取字符哈西值
    public static int getHashCode(String str) {
        return str.hashCode();
    }
}
