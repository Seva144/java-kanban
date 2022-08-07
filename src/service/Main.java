package service;

import tests.ManagerTest;

public class Main {
    public static void main(String[] args) {
        ManagerTest managerTest = new ManagerTest();

        managerTest.Get_All_Tasks();
        managerTest.Get_All_EpicTasks();
        managerTest.Get_All_SubTasks();
        managerTest.Update_Task();
        managerTest.Update_EpicTask();
        managerTest.Update_SubTask();
        managerTest.Remove_EpicTask();
        managerTest.Remove_Task();
        managerTest.Check_History();
    }
}