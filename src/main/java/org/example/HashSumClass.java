package org.example;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HashSumClass {

    protected static void createHashFile(String key, String fullPath) {
        String hashSum = getHashSum(fullPath);
        if (key != null && fullPath != null) {

            Date dateTime = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH-mm");
            String currentDate = formatter.format(dateTime);

            String hashFileFullName = ("src/main/java/FilesHashSum/" + key + " (" + currentDate + ")");

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(hashFileFullName))) {
                writer.write(hashSum);

            } catch (IOException e) {
                throw new RuntimeException("Не удалось создать файл: ",e );
            }
        }
    }

    private static String getHashSum(String fullPath) {
        try {
            return DigestUtils.sha256Hex(new FileInputStream(fullPath));
        } catch (IOException e) {
            throw new RuntimeException("Не удалось считать HashSum файла: ", e);
        }
    }
}
