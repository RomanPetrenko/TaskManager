package com.roman.petrenko.controller;
import com.roman.petrenko.model.*;
import com.roman.petrenko.view.MainPanel;
import java.util.Date;

public class TasksNotificationsController implements Runnable {

    private MainPanel theView;
    private MainTasksActionsController mainTasksActionsController;

    public TasksNotificationsController(MainPanel theView, MainTasksActionsController mainTasksActionsController) {
        this.theView = theView;
        this.mainTasksActionsController = mainTasksActionsController;
    }

    @Override
    public void run() {
        while (true) {
            TaskList list = mainTasksActionsController.getTaskList();
            long curTime = System.currentTimeMillis();
            Date curDate = new Date(curTime);
            if (list != null) {
                for (Task task : list) {
                    if (task != null) {
                        if (task.getTime().equals(curDate)) inform(task);
                        if (task.nextTimeAfter(curDate) != null) {
                            if (task.nextTimeAfter(new Date(curTime - 1)).equals(curDate))
                                inform(task);
                        }
                    }
                }
            }
        }
    }


    public void inform (Task task) {
        theView.displayInform("Time for doing:" + task.toString());
    }
}
