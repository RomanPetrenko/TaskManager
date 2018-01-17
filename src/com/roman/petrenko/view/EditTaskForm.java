package com.roman.petrenko.view;

import javax.swing.*;
import java.awt.event.ActionListener;

public class EditTaskForm extends MainPanel {

    public JFrame editTaskForm = new  JFrame();
    public JButton editTaskButton = new JButton("Edit Task");

    public void createEditTaskForm() {

        editTaskForm.setSize(350,500);
        editTaskForm.setLocationRelativeTo(null);

        editTaskButton.setBounds(30,300,280,40);

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

    public void close () {
        editTaskForm.setVisible(false);
    }

    public void addTasksListener(ActionListener actionListener) {
        editTaskButton.addActionListener(actionListener);
        cancelButton.addActionListener(actionListener);
    }

}
