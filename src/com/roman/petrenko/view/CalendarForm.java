package com.roman.petrenko.view;

import javax.swing.*;
import java.awt.event.ActionListener;

public class CalendarForm extends MainPanel {

    public JFrame calendarForm = new  JFrame();
    public JButton calendarButton = new JButton("Set period");
    public JButton calendarCloseButton = new JButton("Close");
    private JTextArea calendarField = new JTextArea();
    private JScrollPane calendarScrollPane = new JScrollPane(calendarField);

    public void createCalendarForm() {
        JLabel startPeriodLabel = new JLabel("Start period");
        JLabel endPeriodLabel = new JLabel("End period");


        calendarForm.setSize(800,550);
        calendarForm.setLocationRelativeTo(null);
        calendarForm.setResizable(false);

        calendarField.setBounds(30,180,740,300);
        calendarField.setEditable(false);
        calendarScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        calendarScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        calendarScrollPane.setBounds(30,180,740,300);

        startPeriodLabel.setBounds(150,55,280,15);
        endPeriodLabel.setBounds(150,105,280,15);

        calendarButton.setBounds(350,60,200,40);
        calendarCloseButton.setBounds(350,120,200,40);

        calendarForm.add(calendarScrollPane);

        calendarField.setText("");
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

    public void setCalendar(String calendar) {
        calendarField.setText(calendar);
    }

    public void close() {
        calendarForm.setVisible(false);
    }

    public void addTasksListener(ActionListener actionListener) {
        calendarButton.addActionListener(actionListener);
        calendarCloseButton.addActionListener(actionListener);
    }
}
