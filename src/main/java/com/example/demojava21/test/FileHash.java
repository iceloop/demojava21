package com.example.demojava21.test;

/**
 * FileHash
 *
 * <p>创建人：hrniu 创建日期：2024/3/18
 */
import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;

public class FileHash {
    public static String calculateFileHash(String filePath) {
        try (FileInputStream fis = new FileInputStream(new File(filePath))) {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            byte[] byteArray = new byte[1024];
            int bytesCount = 0;

            while ((bytesCount = fis.read(byteArray)) != -1) {
                digest.update(byteArray, 0, bytesCount);
            }

            byte[] bytes = digest.digest();

            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }

            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static void main(String[] args){
    String filePath = "C:\\Users\\87028\\Desktop\\part5.xlsx";
        String fileHash = calculateFileHash(filePath);
        System.out.println("File hash: " + fileHash);
    }
}
// 569cad80759ffe02f2a55ae7780434d01b8dcae70556ccc112d2b24c596c9376
// 569cad80759ffe02f2a55ae7780434d01b8dcae70556ccc112d2b24c596c9376
// 569cad80759ffe02f2a55ae7780434d01b8dcae70556ccc112d2b24c596c9376
// 7977155d773a4972a65662c414775967a8375b1846bef2d8c91a76d49300ac55

