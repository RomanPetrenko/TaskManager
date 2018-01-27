package com.roman.petrenko.controller;
import com.roman.petrenko.model.*;
import com.roman.petrenko.view.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.*;
import java.util.*;

import org.apache.log4j.*;

public class MainTasksActionsController {

    private static final String datePattern = "dd.MM.yyyy HH:mm";
    private static final DateFormat dateFormat = new SimpleDateFormat(datePattern);
    private static org.apache.log4j.Logger log = Logger.getLogger(MainTasksActionsController.class);

    private MainPanel mainPanel;
    private TaskList taskList;

    private UnrepeatableTaskForm unRepeatableTaskView = new UnrepeatableTaskForm();
    private RepeatableTaskForm repeatableTaskView = new RepeatableTaskForm();
    private SelectTaskForm selectTaskView = new SelectTaskForm();
    private EditTaskForm editTaskView = new EditTaskForm();
    private CalendarForm calendarView = new CalendarForm();

    public MainTasksActionsController(MainPanel mainPanel, TaskList tasklist) {
        this.mainPanel = mainPanel;
        MainTasksActionsController.TasksListener tasksListener = new MainTasksActionsController.TasksListener();
        this.mainPanel.addTasksListener(tasksListener);
        this.unRepeatableTaskView.addTasksListener(tasksListener);
        this.repeatableTaskView.addTasksListener(tasksListener);
        this.selectTaskView.addTasksListener(tasksListener);
        this.editTaskView.addTasksListener(tasksListener);
        this.calendarView.addTasksListener(tasksListener);
        this.taskList = tasklist;
    }

    public TaskList getTaskList() {
        return taskList;
    }

    public void createUnrepeatableTask(boolean isActive) throws IOException, ParseException {
        Date time = parseToDate(unRepeatableTaskView.getTime());

        Task task = new Task(unRepeatableTaskView.getTitle(), time);
        task.setActive(isActive);
        taskList.add(task);

        log.debug("Create unrepeatable task: " + task.toString());
        TasksSaveController.saveTasks(taskList);
        showTaskList();
    }

    public void createRepeatableTask(boolean isActive) throws IOException, ParseException {
        Date start = parseToDate(repeatableTaskView.getTime());
        Date end = parseToDate(repeatableTaskView.getEnd());
        int interval = Integer.parseInt(repeatableTaskView.getInterval());

        Task task = new Task(repeatableTaskView.getTitle(), start, end, interval);
        task.setActive(isActive);
        taskList.add(task);

        log.debug("Create repeatable task: " + task.toString());
        TasksSaveController.saveTasks(taskList);
        showTaskList();
    }

    public void editTask(Task task) throws IOException, ParseException {
        task.setTitle(editTaskView.getTitle());
        task.setActive(editTaskView.activeBox.isSelected());
        Date time = parseToDate(editTaskView.getTime());

        if (task.isRepeated()) {
            Date end = parseToDate(editTaskView.getEnd());
            int interval = Integer.parseInt(editTaskView.getInterval());
            task.setTime(time, end, interval);
        }
        if (!task.isRepeated()) {
            task.setTime(time);
        }

        log.debug("Edit task: " + task.toString());
        TasksSaveController.saveTasks(taskList);
        showTaskList();
    }

    public void deleteTask(Task task) throws IOException {
        taskList.remove(task);
        log.debug("Delete task: " + task.toString());
        TasksSaveController.saveTasks(taskList);
        showTaskList();
    }

    public void showTaskList() {
        mainPanel.setTaskList(taskList.toString());
    }

    public void showCalendar() throws ParseException {
        Date start = parseToDate(calendarView.getStartPeriod());
        Date end = parseToDate(calendarView.getEndPeriod());

        if (Tasks.calendar(taskList, start, end).isEmpty()) {
            calendarView.setCalendar("No tasks for this period");
            return;
        }
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

        calendarView.setCalendar(calendarToView.toString());

    }

    public static Date parseToDate(String date) throws ParseException {
        Date parsingDate = dateFormat.parse(date);
        return parsingDate;
    }

    class TasksListener implements ActionListener {

        private void editTasksToComboBox () {
            selectTaskView.comboBox.removeAllItems();
            for (Task task : taskList) {
                selectTaskView.comboBox.addItem(task);
            }
        }

        public void actionPerformed(ActionEvent e) {
            try {
                if (e.getSource().equals(mainPanel.unrepeatableTaskFormButton)) {
                    unRepeatableTaskView.createUnrepeatableTaskForm();
                    unRepeatableTaskView.setTime(dateFormat.format(new Date()));
                }
                if (e.getSource().equals(unRepeatableTaskView.unrepeatableTaskButton)) {
                    createUnrepeatableTask(unRepeatableTaskView.activeBox.isSelected());
                    unRepeatableTaskView.unRepeatableTaskForm.setVisible(false);
                }

                if (e.getSource().equals(mainPanel.repeatableTaskFormButton)) {
                    repeatableTaskView.createRepeatableTaskForm();
                    repeatableTaskView.setTime(dateFormat.format(new Date()));
                    repeatableTaskView.setEndTime(dateFormat.format(new Date()));
                }
                if (e.getSource().equals(repeatableTaskView.repeatableTaskButton)) {
                    createRepeatableTask(repeatableTaskView.activeBox.isSelected());
                    repeatableTaskView.repeatableTaskForm.setVisible(false);
                }

                if (e.getSource().equals(mainPanel.selectEditTaskFormButton)) {
                    editTasksToComboBox();
                    selectTaskView.createSelectTaskForm(selectTaskView.editTaskButton);
                }
                if (e.getSource().equals(selectTaskView.editTaskButton)) {
                    Task task = (Task) selectTaskView.comboBox.getSelectedItem();
                    editTaskView.createEditTaskForm();
                    if (task.isRepeated()) {
                        editTaskView.setTitle(task.getTitle());
                        editTaskView.setTime(dateFormat.format(task.getTime()));
                        editTaskView.setEndTime(dateFormat.format(task.getEndTime()));
                        editTaskView.setInterval(String.valueOf(task.getRepeatInterval()));
                        editTaskView.endTimeField.setVisible(true);
                        editTaskView.intervalField.setVisible(true);
                        editTaskView.endTimeLabel.setVisible(true);
                        editTaskView.intervalLabel.setVisible(true);
                    }
                    else {
                        editTaskView.setTitle(task.getTitle());
                        editTaskView.setTime(dateFormat.format(task.getTime()));
                        editTaskView.endTimeField.setVisible(false);
                        editTaskView.intervalField.setVisible(false);
                        editTaskView.endTimeLabel.setVisible(false);
                        editTaskView.intervalLabel.setVisible(false);
                    }
                }
                if (e.getSource().equals(editTaskView.editTaskButton)) {
                    editTask((Task) selectTaskView.comboBox.getSelectedItem());
                    editTaskView.close();
                    selectTaskView.close();
                }

                if (e.getSource().equals(mainPanel.selectDeleteTaskFormButton)) {
                    editTasksToComboBox();
                    selectTaskView.createSelectTaskForm(selectTaskView.deleteTaskButton);
                }
                if (e.getSource().equals(selectTaskView.deleteTaskButton)) {
                    deleteTask((Task) selectTaskView.comboBox.getSelectedItem());
                    selectTaskView.close();
                }

                if (e.getSource().equals(mainPanel.calendarFormButton)) {
                    calendarView.createCalendarForm();
                    calendarView.setTime(dateFormat.format(new Date()));
                    calendarView.setEndTime(dateFormat.format(new Date()));
                }
                if (e.getSource().equals(calendarView.calendarButton)) showCalendar();
                if (e.getSource().equals(calendarView.calendarCloseButton))
                    calendarView.close();

                if (e.getSource().equals(unRepeatableTaskView.cancelButton)) unRepeatableTaskView.close();
                if (e.getSource().equals(repeatableTaskView.cancelButton)) repeatableTaskView.close();
                if (e.getSource().equals(editTaskView.cancelButton)) editTaskView.close();
                if (e.getSource().equals(selectTaskView.cancelButton)) selectTaskView.close();

                if (e.getSource().equals(mainPanel.exitButton)) {
                    log.info("Exit");
                    mainPanel.setVisible(false);
                    mainPanel.dispose();
                    System.exit(0);
                }
            }
            catch (IllegalArgumentException iae){
                mainPanel.displayErrorMessage(iae.getMessage());
                log.error("IllegalArgumentException: ", iae);
            }
            catch (IOException io) {
                mainPanel.displayErrorMessage(io.getMessage());
                log.error("IOException: ", io);
            }
            catch (NullPointerException npe) {
                mainPanel.displayErrorMessage(npe.getMessage());
                log.error("NullPointerException: ", npe);
            }
            catch (IndexOutOfBoundsException ioe) {
                mainPanel.displayErrorMessage(ioe.getMessage());
                log.error("IndexOutOfBoundsException: ", ioe);
            }
            catch (NoSuchElementException nse) {
                mainPanel.displayErrorMessage(nse.getMessage());
                log.error("NoSuchElementException: ", nse);
            }
            catch (ParseException pe) {
                mainPanel.displayErrorMessage(pe.getMessage());
                log.error("Incorrect format: ", pe);
            }
            catch (Exception ex){
                mainPanel.displayErrorMessage(ex.getMessage());
                log.error("Exception: ", ex);
            }
        }

    }

    public static void main(String[] args) {
        log.info("Start");
        MainPanel theView = new MainPanel();
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

        MainTasksActionsController actionsController = new MainTasksActionsController(theView, tasklist);

        Runnable notificationRun = new TasksNotificationsController(theView, actionsController);
        Thread notificationThread = new Thread(notificationRun);
        notificationThread.start();

        actionsController.showTaskList();
        theView.setVisible(true);
    }
}




