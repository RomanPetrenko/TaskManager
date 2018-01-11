package com.roman.petrenko.Controller;
import com.roman.petrenko.Model.*;
import java.io.IOException;
import java.text.ParseException;

public interface TasksActions {
    void createUnrepeatableTask(boolean isActive) throws IOException, ParseException;
    void createRepeatableTask(boolean isActive) throws IOException, ParseException;
    void editTask(Task task) throws IOException, ParseException;
    void deleteTask(Task task) throws IOException, ParseException;
    void showTaskList();
    void showCalendar()throws ParseException;
}
