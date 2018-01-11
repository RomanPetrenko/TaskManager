package com.roman.petrenko.Controller;
import com.roman.petrenko.Model.*;
import com.roman.petrenko.View.TasksView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.*;
import java.util.*;
import org.apache.log4j.Logger;

public class TasksActionsController implements TasksActions {

    private static final String datePattern = "dd.MM.yyyy HH:mm";
    private static final DateFormat dateFormat = new SimpleDateFormat(datePattern);
    private static org.apache.log4j.Logger log = Logger.getLogger(TasksActionsController.class);

    private TasksView theView;
    private TaskList taskList;

    public TasksActionsController(TasksView theView, TaskList tasklist) {
        this.theView = theView;
        this.theView.addTasksListener(new TasksActionsController.TasksListener());
        this.taskList = tasklist;
    }

    public TaskList getTaskList() {
        return taskList;
    }

    public void createUnrepeatableTask(boolean isActive) throws IOException, ParseException {
        Date time = parseToDate(theView.getTime());

        Task task = new Task(theView.getTitle(), time);
        task.setActive(isActive);
        taskList.add(task);

        TasksSaveController.saveTasks(taskList);
        showTaskList();
    }

    public void createRepeatableTask(boolean isActive) throws IOException, ParseException {
        Date start = parseToDate(theView.getTime());
        Date end = parseToDate(theView.getEnd());
        int interval = Integer.parseInt(theView.getInterval());

        Task task = new Task(theView.getTitle(), start, end, interval);
        task.setActive(isActive);
        taskList.add(task);

        TasksSaveController.saveTasks(taskList);
        showTaskList();
    }

    public void editTask(Task task) throws IOException, ParseException {
        task.setTitle(theView.getTitle());
        task.setActive(theView.activeBox.isSelected());
        Date time = parseToDate(theView.getTime());

        if (task.isRepeated()) {
            Date end = parseToDate(theView.getEnd());
            int interval = Integer.parseInt(theView.getInterval());
            task.setTime(time, end, interval);
        }
        if (!task.isRepeated()) {
            task.setTime(time);
        }

        TasksSaveController.saveTasks(taskList);
        showTaskList();
    }

    public void deleteTask(Task task) throws IOException {
        taskList.remove(task);
        TasksSaveController.saveTasks(taskList);
        showTaskList();
    }

    public void showTaskList() {
        theView.setTaskList(taskList.toString());
    }

    public void showCalendar() throws ParseException {
        Date start = parseToDate(theView.getStartPeriod());
        Date end = parseToDate(theView.getEndPeriod());

        StringBuilder calendarToView = new StringBuilder();
        for (byte i = 0; i < 20; i++) calendarToView.append(" ");
        calendarToView.append("Dates");
        for (byte i = 0; i < 80; i++) calendarToView.append(" ");
        calendarToView.append("Tasks" + "\n");
        for (Map.Entry<Date, Set<Task>> entry : Tasks.calendar(taskList, start, end).entrySet()) {
            Date key = entry.getKey();
            Set <Task> value = entry.getValue();

            calendarToView.append(key.toString() + "  ");
            boolean firstTask = true;
            for (Task task: value) {
                if (!task.isRepeated() && !firstTask) {
                    for (byte i = 0; i < 58; i++) calendarToView.append(" ");
                }
                calendarToView.append(task.toString() + "\n");
                firstTask = false;
            }
        }

        theView.setCalendar(calendarToView.toString());

    }

    public static Date parseToDate(String date) throws ParseException {
        Date parsingDate = dateFormat.parse(date);
        return parsingDate;
    }

    class TasksListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            try {
                if (e.getSource().equals(theView.unrepeatableTaskFormButton)) {
                    theView.createUnrepeatableTaskForm();
                    theView.setTime(dateFormat.format(new Date()));
                }
                if (e.getSource().equals(theView.unrepeatableTaskButton)) {
                    createUnrepeatableTask(theView.activeBox.isSelected());
                    theView.unRepeatableTaskForm.setVisible(false);
                }

                if (e.getSource().equals(theView.repeatableTaskFormButton)) {
                    theView.createRepeatableTaskForm();
                    theView.setTime(dateFormat.format(new Date()));
                    theView.setEndTime(dateFormat.format(new Date()));
                }
                if (e.getSource().equals(theView.repeatableTaskButton)) {
                    createRepeatableTask(theView.activeBox.isSelected());
                    theView.repeatableTaskForm.setVisible(false);
                }

                if (e.getSource().equals(theView.editTaskFormButton)) {
                    theView.combo.removeAllItems();
                    for (Task task : taskList) {
                        theView.combo.addItem(task);
                    }
                    theView.createSelectTaskForm(theView.combo);
                }
                if (e.getSource().equals(theView.selectTaskButton)) {
                    theView.createEditTaskForm();
                    Task task = (Task) theView.combo.getSelectedItem();
                    if (task.isRepeated()) {
                        theView.setTitle(task.getTitle());
                        theView.setTime(dateFormat.format(task.getTime()));
                        theView.setEndTime(dateFormat.format(task.getEndTime()));
                        theView.setInterval(String.valueOf(task.getRepeatInterval()));
                    }
                    else {
                        theView.setTitle(task.getTitle());
                        theView.setTime(dateFormat.format(task.getTime()));
                        theView.endTimeField.setVisible(false);
                        theView.intervalField.setVisible(false);
                        theView.endTimeLabel.setVisible(false);
                        theView.intervalLabel.setVisible(false);
                    }
                }

                if (e.getSource().equals(theView.editTaskButton)) {
                    editTask((Task) theView.combo.getSelectedItem());
                    theView.endTimeField.setVisible(true);
                    theView.intervalField.setVisible(true);
                    theView.endTimeLabel.setVisible(true);
                    theView.intervalLabel.setVisible(true);
                    theView.editTaskForm.setVisible(false);
                    theView.selectTaskForm.setVisible(false);
                }

                if (e.getSource().equals(theView.deleteTaskFormButton)) {
                    theView.combo.removeAllItems();
                    for (Task task : taskList) {
                        theView.combo.addItem(task);
                    }
                    theView.createDeleteTaskForm(theView.combo);
                }
                if (e.getSource().equals(theView.deleteTaskButton)) {
                    deleteTask((Task) theView.combo.getSelectedItem());
                    theView.deleteTaskForm.setVisible(false);
                }

                if (e.getSource().equals(theView.calendarFormButton)) {
                    theView.createCalendarForm();
                    theView.setTime(dateFormat.format(new Date()));
                    theView.setEndTime(dateFormat.format(new Date()));
                }
                if (e.getSource().equals(theView.calendarButton)) showCalendar();
                if (e.getSource().equals(theView.calendarCloseButton))
                    theView.calendarForm.setVisible(false);

                if (e.getSource().equals(theView.cancelButton)) theView.backToMainMenu();

                if (e.getSource().equals(theView.exitButton)) {
                    theView.setVisible(false);
                    theView.dispose();
                    System.exit(0);
                }
            }
            catch (IllegalArgumentException iae){
                theView.displayErrorMessage(iae.getMessage());
                log.error("IllegalArgumentException: ", iae);
            }
            catch (IOException io) {
                theView.displayErrorMessage(io.getMessage());
                log.error("IOException: ", io);
            }
            catch (NullPointerException npe) {
                theView.displayErrorMessage(npe.getMessage());
                log.error("NullPointerException: ", npe);
            }
            catch (IndexOutOfBoundsException ioe) {
                theView.displayErrorMessage(ioe.getMessage());
                log.error("IndexOutOfBoundsException: ", ioe);
            }
            catch (NoSuchElementException nse) {
                theView.displayErrorMessage(nse.getMessage());
                log.error("NoSuchElementException: ", nse);
            }
            catch (ParseException pe) {
                theView.displayErrorMessage(pe.getMessage());
                log.error("Incorrect format: ", pe);
            }
            catch (Exception ex){
                theView.displayErrorMessage(ex.getMessage());
                log.error("Exception: ", ex);
            }
        }

    }


    public static void main(String[] args) {
        TasksView theView = new TasksView();
        TaskList tasklist = new ArrayTaskList();

        try {
            TasksSaveController.loadTasks(tasklist);
        } catch (IOException ioe) {
            theView.displayErrorMessage(ioe.getMessage());
            log.error("IOException: ", ioe);
        } catch (ParseException pe) {
            theView.displayErrorMessage(pe.getMessage());
            log.error("Incorrect format: ", pe);
        }

        TasksActionsController actionsController = new TasksActionsController(theView, tasklist);

        Runnable notificationRun = new TasksNotificationsController(theView, actionsController);
        Thread notificationThread = new Thread(notificationRun);
        notificationThread.start();

        actionsController.showTaskList();
        theView.setVisible(true);
    }
}




