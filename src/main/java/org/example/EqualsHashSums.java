package org.example;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class EqualsHashSums {
    public static void main(String[] args) throws IOException {
        String pathOne = "C:\\Users\\3301\\Desktop\\AmazonAWSApi.rar";
        String pathTwo = "F:\\Проекты Java\\AmazonAWSApi.rar";
        System.out.println(equalHashSum(pathOne, pathTwo));
        concatStr();
    }

    public static boolean equalHashSum(String pathOne, String pathTwo) throws IOException {
        String fileOne = DigestUtils.sha256Hex(new FileInputStream(pathOne));
        String fileTwo = DigestUtils.sha256Hex(new FileInputStream(pathTwo));
        return fileTwo.equals(fileOne);
    }

    public static void concatStr() {
        File hashSumDir = new File("src\\main\\java\\FilesHashSum\\");
        File[] files = hashSumDir.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    System.out.println(file.getName());
                }
            }
        } else {
            throw new RuntimeException("Папка пуста или не существует.");
        }
    }
}
