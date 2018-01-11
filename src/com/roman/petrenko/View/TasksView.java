package com.roman.petrenko.View;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class TasksView extends JFrame {
    public JFrame repeatableTaskForm = new  JFrame();
    public JFrame unRepeatableTaskForm = new  JFrame();
    public JFrame selectTaskForm = new  JFrame();
    public JFrame editTaskForm = new  JFrame();
    public JFrame deleteTaskForm = new  JFrame();
    public JFrame calendarForm = new  JFrame();

    private JTextArea tasksField = new JTextArea();
    private JTextArea calendarField = new JTextArea();
    public JScrollPane calendarScrollPane = new JScrollPane(calendarField);
    public JTextField titleField = new JTextField(20);
    public JTextField timeField = new JTextField(20);
    public JTextField endTimeField = new JTextField(20);
    public JTextField intervalField = new JTextField(20);

    public JButton unrepeatableTaskFormButton = new JButton("Create task");
    public JButton repeatableTaskFormButton = new JButton("Create repeatable task");
    public JButton unrepeatableTaskButton = new JButton("Create task");
    public JButton repeatableTaskButton = new JButton("Create task");

    public JButton editTaskFormButton = new JButton("Edit Task");
    public JButton selectTaskButton = new JButton("Select Task");
    public JButton editTaskButton = new JButton("Edit Task");

    public JButton deleteTaskFormButton = new JButton("Delete Task");
    public JButton deleteTaskButton = new JButton("Delete Task");

    public JButton calendarFormButton = new JButton("Show calendar");
    public JButton calendarButton = new JButton("Set period");
    public JButton calendarCloseButton = new JButton("Close");

    public JButton cancelButton = new JButton("Cancel");
    public JButton exitButton = new JButton("Exit");

    public JCheckBox activeBox = new JCheckBox("is Active");
    public JComboBox combo = new JComboBox();

    public JLabel titleLabel = new JLabel("Title");
    public JLabel timeLabel = new JLabel("Time");
    public JLabel startTimeLabel = new JLabel("Start");
    public JLabel endTimeLabel = new JLabel("End");
    public JLabel intervalLabel = new JLabel("Interval(seconds)");
    public JLabel startPeriodLabel = new JLabel("Start period");
    public JLabel endPeriodLabel = new JLabel("End period");

    public TasksView() {
        JPanel mainPanel = new JPanel();
        this.setTitle("Task Manager");
        this.setSize(810,450);


        tasksField.setBounds(30,10,740,280);
        tasksField.setEditable(false);

        JScrollPane areaScrollPane = new JScrollPane(tasksField);
        areaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        areaScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        areaScrollPane.setBounds(30,10, 740, 280);
        getContentPane().add(areaScrollPane);

        titleLabel.setBounds(150,5,280,15);
        timeLabel.setBounds(150,55,280,15);
        startTimeLabel.setBounds(150,55,280,15);
        endTimeLabel.setBounds(150,105,280,15);
        intervalLabel.setBounds(130,155,280,15);

        titleField.setBounds(30,20,280,30);
        timeField.setBounds(30,70,280,30);
        endTimeField.setBounds(30,120,280,30);
        intervalField.setBounds(30,170,280,30);

        activeBox.setBounds(30, 230, 80, 30);

        unrepeatableTaskFormButton.setBounds(30,300,200,40);
        repeatableTaskFormButton.setBounds(30,350,200,40);
        unrepeatableTaskButton.setBounds(30,300,280,40);
        repeatableTaskButton.setBounds(30,300,280,40);

        editTaskFormButton.setBounds(300,300,200,40);
        selectTaskButton.setBounds(30,300,280,40);
        editTaskButton.setBounds(30,300,280,40);

        deleteTaskFormButton.setBounds(300,350,200,40);
        deleteTaskButton.setBounds(30,300,280,40);

        calendarField.setBounds(30,180,740,300);
        calendarField.setEditable(false);
        calendarScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        calendarScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        calendarScrollPane.setBounds(30,180,740,300);

        startPeriodLabel.setBounds(150,55,280,15);
        endPeriodLabel.setBounds(150,105,280,15);

        calendarFormButton.setBounds(570,300,200,40);
        calendarButton.setBounds(350,60,200,40);
        calendarCloseButton.setBounds(350,120,200,40);

        cancelButton.setBounds(30,350,280,40);
        exitButton.setBounds(570,350,200,40);

        mainPanel.setBackground(Color.BLUE);
        //mainPanel.add(tasksField);

        mainPanel.add(unrepeatableTaskFormButton);
        mainPanel.add(repeatableTaskFormButton);
        mainPanel.add(editTaskFormButton);
        mainPanel.add(deleteTaskFormButton);
        mainPanel.add(calendarFormButton);
        mainPanel.add(exitButton);


        mainPanel.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(mainPanel);
    }

    public void createUnrepeatableTaskForm() {

        unRepeatableTaskForm.setSize(350,450);
        unRepeatableTaskForm.setLocationRelativeTo(null);

        flushAdditionalFields();
        activeBox.setSelected(true);

        unRepeatableTaskForm.add(titleField);
        unRepeatableTaskForm.add(timeField);
        unRepeatableTaskForm.add(activeBox);
        unRepeatableTaskForm.add(titleLabel);
        unRepeatableTaskForm.add(timeLabel);

        unRepeatableTaskForm.add(unrepeatableTaskButton);
        unRepeatableTaskForm.add(cancelButton);

        unRepeatableTaskForm.setLayout(null);
        unRepeatableTaskForm.setVisible(true);
    }

    public void createRepeatableTaskForm() {

        repeatableTaskForm.setSize(350,450);
        repeatableTaskForm.setLocationRelativeTo(null);

        flushAdditionalFields();
        activeBox.setSelected(true);
        repeatableTaskForm.add(titleField);
        repeatableTaskForm.add(timeField);
        repeatableTaskForm.add(endTimeField);
        repeatableTaskForm.add(intervalField);
        repeatableTaskForm.add(titleLabel);
        repeatableTaskForm.add(startTimeLabel);
        repeatableTaskForm.add(endTimeLabel);
        repeatableTaskForm.add(intervalLabel);

        repeatableTaskForm.add(activeBox);
        repeatableTaskForm.add(repeatableTaskButton);
        repeatableTaskForm.add(cancelButton);

        repeatableTaskForm.setLayout(null);
        repeatableTaskForm.setVisible(true);
    }

    public void createSelectTaskForm(JComboBox combo) {

        selectTaskForm.setSize(350,500);
        selectTaskForm.setLocationRelativeTo(null);

        selectTaskForm.add(selectTaskButton);
        selectTaskForm.add(cancelButton);

        combo.setBounds(30, 100, 300,100);
        combo.setSelectedIndex(0);
        selectTaskForm.add(combo);


        selectTaskForm.setLayout(null);
        selectTaskForm.setVisible(true);
    }

    public void createEditTaskForm() {

        editTaskForm.setSize(350,500);
        editTaskForm.setLocationRelativeTo(null);

        flushAdditionalFields();
        activeBox.setSelected(true);
        editTaskForm.add(titleField);
        editTaskForm.add(timeField);
        editTaskForm.add(endTimeField);
        editTaskForm.add(intervalField);

        editTaskForm.add(titleLabel);
        editTaskForm.add(timeLabel);
        editTaskForm.add(endTimeLabel);
        editTaskForm.add(intervalLabel);

        editTaskForm.add(activeBox);
        editTaskForm.add(editTaskButton);
        editTaskForm.add(cancelButton);

        editTaskForm.setLayout(null);
        editTaskForm.setVisible(true);
    }

    public void createDeleteTaskForm(JComboBox combo) {

        deleteTaskForm.setSize(350,500);
        deleteTaskForm.setLocationRelativeTo(null);

        deleteTaskForm.add(deleteTaskButton);
        deleteTaskForm.add(cancelButton);

        combo.setBounds(30, 100, 300,100);
        combo.setSelectedIndex(0);
        deleteTaskForm.add(combo);

        deleteTaskForm.setLayout(null);
        deleteTaskForm.setVisible(true);
    }

    public void createCalendarForm() {

        calendarForm.setSize(800,550);
        calendarForm.setLocationRelativeTo(null);

        calendarForm.add(calendarScrollPane);

        flushAdditionalFields();

        calendarForm.add(startPeriodLabel);
        calendarForm.add(endPeriodLabel);
        calendarForm.add(timeField);
        calendarForm.add(endTimeField);

        calendarForm.add(calendarButton);
        calendarForm.add(calendarCloseButton);

        calendarForm.setLayout(null);
        calendarForm.setVisible(true);
    }

    public void flushAdditionalFields() {
        titleField.setText("");
        timeField.setText("");
        endTimeField.setText("");
        intervalField.setText("");
        calendarField.setText("");
    }

    public void backToMainMenu() {
        unRepeatableTaskForm.setVisible(false);
        repeatableTaskForm.setVisible(false);
        editTaskForm.setVisible(false);
        selectTaskForm.setVisible(false);
        deleteTaskForm.setVisible(false);
        calendarForm.setVisible(false);
        endTimeField.setVisible(true);
        intervalField.setVisible(true);
        endTimeLabel.setVisible(true);
        intervalLabel.setVisible(true);
    }

    public String getTitle() {
        return titleField.getText();
    }

    public String getTime() {
        return timeField.getText();
    }

    public String getEnd() {
        return endTimeField.getText();
    }

    public String getStartPeriod() {
        return timeField.getText();
    }

    public String getEndPeriod() {
        return endTimeField.getText();
    }

    public String getInterval() {
        return intervalField.getText();
    }

    public void setTitle(String title) {
        titleField.setText(title);
    }

    public void setTime(String time) {
        timeField.setText(time);
    }

    public void setEndTime(String endTime) {
        endTimeField.setText(endTime);
    }

    public void setInterval(String interval) {
        intervalField.setText(interval);
    }

    public void setTaskList(String taskList) {
        tasksField.setText(taskList);
    }


    public void setCalendar(String calendar) {
        calendarField.setText(calendar);
    }


    public void addTasksListener(ActionListener actionListener) {
        unrepeatableTaskFormButton.addActionListener(actionListener);
        repeatableTaskFormButton.addActionListener(actionListener);
        unrepeatableTaskButton.addActionListener(actionListener);
        repeatableTaskButton.addActionListener(actionListener);

        activeBox.addActionListener(actionListener);

        editTaskFormButton.addActionListener(actionListener);
        selectTaskButton.addActionListener(actionListener);
        editTaskButton.addActionListener(actionListener);

        deleteTaskFormButton.addActionListener(actionListener);
        deleteTaskButton.addActionListener(actionListener);

        calendarFormButton.addActionListener(actionListener);
        calendarButton.addActionListener(actionListener);
        calendarCloseButton.addActionListener(actionListener);

        cancelButton.addActionListener(actionListener);
        exitButton.addActionListener(actionListener);

    }

    public void displayInform(String inform) {
        JOptionPane.showMessageDialog(this, inform);
    }

    public void displayErrorMessage(String errorMessage) {
        JOptionPane.showMessageDialog(this, errorMessage);
    }

}

