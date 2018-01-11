package com.roman.petrenko.Controller;
import com.roman.petrenko.Model.ArrayTaskList;
import com.roman.petrenko.Model.TaskList;
import com.roman.petrenko.View.TasksView;

public class TasksViewController {

    private TasksView theView;
    private TaskList theModel;

    public TasksViewController(TasksView theView, TaskList theModel) {
        this.theView = theView;
        this.theModel = theModel;
    }



}
