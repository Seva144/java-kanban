import service.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Manager manager = new Manager();

        while (true) {
            printMenu();
            int userInput = scanner.nextInt();

            if (userInput == 1) {
                manager.readerTask();
            }
            if (userInput == 2) {
                manager.clearTask();
            }
            if (userInput == 3) {
                manager.changeStatusId();
            }
            if (userInput == 4) {
                System.out.println("Какую задачу хотите создать: 1-Простая задача; 2 - Эпик-задача");
                int userInputTask = scanner.nextInt();
                if(userInputTask==1){
                    manager.setSimpleTask();
                }
                if(userInputTask==2){
                    manager.setEpicTasks();
                    manager.setSubTasks();
                }

            }
            if (userInput == 5) {
                manager.findTask();
            }
            if (userInput == 0) {
                break;
            }
        }

    }
    private static void printMenu() {
        System.out.println("Что вы хотите сделать? ");
        System.out.println("1 - Получить список всех задач");
        System.out.println("2 - Удаление задач");
        System.out.println("3 - Обновление задачи");
        System.out.println("4 - Создание новой задачи");
        System.out.println("5 - Найти задачу по id");
        System.out.println("0 - Выход");
    }
}
