package service;


import tests.ManagerTest;

import java.io.*;


public class Main {
    public static void main(String[] args) throws IOException {

        ManagerTest managerTest = new ManagerTest();
//
//        managerTest.Get_All_Tasks();
//        managerTest.Get_All_EpicTasks();
//        managerTest.Get_All_SubTasks();
//        managerTest.Update_Task();
//        managerTest.Update_EpicTask();
//        managerTest.Update_SubTask();
//        managerTest.Remove_EpicTask();
//        managerTest.Remove_Task();

        managerTest.CheckSave();

        managerTest.CheckLoad();

    }
}