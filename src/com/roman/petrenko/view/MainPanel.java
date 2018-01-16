package com.roman.petrenko.view;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MainPanel extends JFrame {

    private JTextArea tasksField = new JTextArea();

    public JTextField titleField = new JTextField(20);
    public JTextField timeField = new JTextField(20);
    public JTextField endTimeField = new JTextField(20);
    public JTextField intervalField = new JTextField(20);

    public JLabel titleLabel = new JLabel("Title");
    public JLabel timeLabel = new JLabel("Time");
    public JLabel startTimeLabel = new JLabel("Start");
    public JLabel endTimeLabel = new JLabel("End");
    public JLabel intervalLabel = new JLabel("Interval(seconds)");

    public JButton unrepeatableTaskFormButton = new JButton("Create task");
    public JButton repeatableTaskFormButton = new JButton("Create repeatable task");
    public JButton selectEditTaskFormButton = new JButton("Edit Task");
    public JButton selectDeleteTaskFormButton = new JButton("Delete Task");
    public JButton calendarFormButton = new JButton("Show calendar");

    public JButton cancelButton = new JButton("Cancel");
    public JButton exitButton = new JButton("Exit");

    public JCheckBox activeBox = new JCheckBox("is Active");


    public MainPanel() {
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
        selectEditTaskFormButton.setBounds(300,300,200,40);
        selectDeleteTaskFormButton.setBounds(300,350,200,40);
        calendarFormButton.setBounds(570,300,200,40);

        cancelButton.setBounds(30,350,280,40);
        exitButton.setBounds(570,350,200,40);

        mainPanel.setBackground(Color.BLUE);
        mainPanel.add(unrepeatableTaskFormButton);
        mainPanel.add(repeatableTaskFormButton);
        mainPanel.add(selectEditTaskFormButton);
        mainPanel.add(selectDeleteTaskFormButton);
        mainPanel.add(calendarFormButton);
        mainPanel.add(exitButton);

        mainPanel.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(mainPanel);
    }

    public void flushAdditionalFields() {
        titleField.setText("");
        timeField.setText("");
        endTimeField.setText("");
        intervalField.setText("");
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

    public void close () {
        this.setVisible(false);
    }

    public void addTasksListener(ActionListener actionListener) {
        unrepeatableTaskFormButton.addActionListener(actionListener);
        repeatableTaskFormButton.addActionListener(actionListener);
        activeBox.addActionListener(actionListener);
        selectEditTaskFormButton.addActionListener(actionListener);
        selectDeleteTaskFormButton.addActionListener(actionListener);
        calendarFormButton.addActionListener(actionListener);

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

