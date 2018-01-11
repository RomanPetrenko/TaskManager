package com.roman.petrenko.Controller;
import com.roman.petrenko.Model.*;
import java.io.*;
import java.text.ParseException;

public class TasksSaveController {

    private static final String DIRECTORY = System.getProperty("user.dir") + "\\TasksFile.txt";

    public static void saveTasks(TaskList list) throws IOException {
        TaskIO.writeText(list, new File(DIRECTORY));
    }

    public static void loadTasks(TaskList list) throws IOException, ParseException {
        TaskIO.readText(list, new File(DIRECTORY));
    }

}
