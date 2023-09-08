package org.example;

import java.util.Scanner;

public class CommandHandler {
	public static void commandHandler() {
		String cmdCommand = "Список команд: \"-cmd\"\n";
		boolean isExit = false;
		System.out.println(cmdCommand);
		while (!isExit) {
			String commands = """
					\nВыберите один из трёх пунктов:
					1) Удалить из хранилища: "удалить".
					2) Загрузить в хранилище: "загрузить".
					3) Список элементов: "список".
					4) Скачать файл из хранилища: "скачать".
					4) Завершить программу: "завершить".""";
			Scanner sc = new Scanner(System.in);
			String userChoice = sc.nextLine();

			switch (userChoice.trim()) {
				case "-cmd" -> System.out.println(commands);
				case "удалить" -> {
					System.out.println("\nУкажите ключ файла.");
					String key = sc.nextLine();
					if (key.equalsIgnoreCase("назад")) {
						System.out.println("\n" + cmdCommand);
						break;
					}
					BasicFunctionality.deleteFrom(key);
				}
				case "загрузить" -> {
					System.out.println("\nУкажите полный путь к файлу вместе с названием и расширением файла.");
					String pathToFile = sc.nextLine().trim();
					System.out.println("\nУкажите ключ файла.");
					String key = sc.nextLine() + BasicFunctionality.getFileExtension(pathToFile);
					if (key.equalsIgnoreCase("назад")) {
						System.out.println("\n" + cmdCommand);
						break;
					}
					BasicFunctionality.uploadInParts(pathToFile, key);
				}
				case "скачать" -> {
					System.out.println("\nУкажите ключ файла.");
					String key = sc.nextLine();
					if (key.equalsIgnoreCase("назад")) {
						System.out.println("\n" + cmdCommand);
						break;
					}
					BasicFunctionality.downloadingFile(key);
				}
				case "список" -> BasicFunctionality.listObjects();
				case "завершить" -> {
					isExit = true;
					System.out.println();
				}
				default -> System.out.println("\nНеверная команда.\n");
			}
		}
	}
}
