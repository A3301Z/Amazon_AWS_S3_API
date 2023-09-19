package org.example;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.FileInputStream;
import java.io.IOException;

public class EqualsHashSums {
    public static void main(String[] args) throws IOException {
        String pathOne = "C:\\Users\\3301\\Desktop\\AmazonAWSApi.rar";
        String pathTwo = "F:\\Проекты Java\\AmazonAWSApi.rar";
        System.out.println(equalHashSum(pathOne, pathTwo));
    }

    public static boolean equalHashSum(String pathOne, String pathTwo) throws IOException {
        String fileOne = DigestUtils.sha256Hex(new FileInputStream(pathOne));
        String fileTwo = DigestUtils.sha256Hex(new FileInputStream(pathTwo));
        return fileTwo.equals(fileOne);
    }
}
