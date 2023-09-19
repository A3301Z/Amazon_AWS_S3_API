package org.example;

import java.util.Scanner;

public class CommandHandler {
	public static void commandHandler() throws InterruptedException {
		String cmdCommand = """
				Список команд: "-cmd"
				_____________________""";
		boolean isExit = false;
		System.out.println(cmdCommand);
		while (!isExit) {
			String commands = """
					Выберите один из трёх пунктов
					1) Удалить из хранилища: "удалить".
					2) Загрузить в хранилище: "загрузить".
					3) Список элементов: "список
					4) Скачать файл из хранилища: "скачать".
					4) Завершить программу: "завершить".
					__________________________________________""";
			Scanner sc = new Scanner(System.in);
			String userChoice = sc.nextLine();

			switch (userChoice.trim()) {
				case "-cmd" -> System.out.println(commands);
				case "удалить" -> {
					System.out.println("""
       
							Укажите ключ файла.
							___________________""");
					String key = sc.nextLine();
					if (key.equalsIgnoreCase("назад")) {
						System.out.println("\n" + cmdCommand);
						break;
					}
					BasicFunctionality.deleteFrom(key);
				}
				case "загрузить" -> {
					System.out.println("""
							Укажите полный путь к файлу вместе с названием и расширением файла.
							___________________________________________________________________""");
					String pathToFile = sc.nextLine().trim();
					System.out.println("""
							Укажите ключ файла.
							___________________""");
					String key = sc.nextLine() + BasicFunctionality.getFileExtension(pathToFile);

					HashSumClass.createHashFile(key, pathToFile);

					if (key.equalsIgnoreCase("назад")) {
						System.out.println("\n" + cmdCommand);
						break;
					}
					BasicFunctionality.uploadInParts(pathToFile, key);
				}
				case "скачать" -> {
					System.out.println("""
							Укажите ключ файла.
							___________________""");
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
					System.out.print("\nЗавершаю");
					for (int i = 0; i < 3; i++) {
						Thread.sleep(350);
						System.out.print(".");
					}
				}
				default -> System.out.println("\nНеверная команда.\n");
			}
		}
	}
}
