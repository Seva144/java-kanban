package service;

public class Main {

    public static void main(String[] args) {
        Manager manager = new Manager();

        manager.getSimpleTask();
        manager.getEpicTask();
        manager.readTask();
        manager.changeStatusId();
    }

}
