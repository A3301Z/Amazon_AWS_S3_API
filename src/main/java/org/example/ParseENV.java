package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ParseENV {
	public record Credentials(String ACCESS_KEY, String SECRET_KEY) {
	}

	protected static Credentials parseEnvFile2() {
		String ACCESS_KEY = null;
		String SECRET_KEY = null;
		try (InputStream inputStream = ParseENV.class.getResourceAsStream("/.env");
		     BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

			String line;
			while ((line = reader.readLine()) != null) {
				if (line.startsWith("ACCESS_KEY=")) {
					ACCESS_KEY = line.substring("ACCESS_KEY=".length());
				} else if (line.startsWith("SECRET_KEY=")) {
					SECRET_KEY = line.substring("SECRET_KEY=".length());
				} else if (ACCESS_KEY == null) {
					throw new RuntimeException("ACCESS_KEY отсутствует или имеет некорректный формат.");
				} else if (SECRET_KEY == null) {
					throw new RuntimeException("SECRET_KEY отсутствует или имеет некорректный формат.");
				}
			}
		} catch (IOException e) {
			throw new RuntimeException("Не удалось считать настройки: ", e);
		}
		return new Credentials(ACCESS_KEY, SECRET_KEY);
	}
}
