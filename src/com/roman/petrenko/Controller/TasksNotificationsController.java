package com.roman.petrenko.Controller;
import com.roman.petrenko.Model.*;
import com.roman.petrenko.View.TasksView;
import java.util.Date;

public class TasksNotificationsController implements Runnable {

    private TasksView theView;
    private TasksActionsController tasksActionsController;

    public TasksNotificationsController(TasksView theView, TasksActionsController tasksActionsController) {
        this.theView = theView;
        this.tasksActionsController = tasksActionsController;
    }

    @Override
    public void run() {
        while (true) {
            TaskList list = tasksActionsController.getTaskList();
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
