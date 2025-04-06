package ru.sibsutis.threads;

public class ConsoleUtils {
    public static void moveCursorToLine(int line) {
        System.out.printf("\033[%d;0H", line);
    }

    public static void clearConsole() {
        System.out.print("\033[2J"); // Очистка
        System.out.print("\033[H"); // Перемещение курсора в начало
    }
}
