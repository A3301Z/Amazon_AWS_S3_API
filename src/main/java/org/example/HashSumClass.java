package org.example;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class HashSumClass {

    protected static void createHashFile(String key, String fullPath) throws IOException {
        String hashSum = getHashSum(fullPath);
        if (key != null && fullPath != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            Date dateTime = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH-mm");
            String currentDate = formatter.format(dateTime);

            String hashFileFullName = ("src/main/java/FilesHashSum/" + key);

            List<String> cloudListFiles = BasicFunctionality.listObjects();
            if (cloudListFiles.contains(key)) {
                System.out.println("Файл с таким ключом уже есть. Хотите дозаписать файл?");
                switch (reader.readLine()) {
                    case "да", "ДА", "Да" -> {
                        try {
                            BufferedWriter writer = new BufferedWriter(new FileWriter(hashFileFullName, true));
                            writer.write(hashSum + " (" + currentDate + ")");
                            writer.newLine();
                            writer.flush();
                            writer.close();
                        } catch (IOException e) {
                            throw new RuntimeException("Не удалось создать файл: ", e);
                        }
                    }
                }
            }
//            try {
//                BufferedWriter writer = new BufferedWriter(new FileWriter(hashFileFullName, true));
//                writer.write(hashSum + " (" + currentDate + ")");
//                writer.newLine();
//                writer.flush();
//                writer.close();
//            } catch (IOException e) {
//                throw new RuntimeException("Не удалось создать файл: ",e );
//            }
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
