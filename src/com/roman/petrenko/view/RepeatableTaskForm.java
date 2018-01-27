package com.roman.petrenko.view;

import javax.swing.*;
import java.awt.event.ActionListener;

public class RepeatableTaskForm extends MainPanel {

    public JFrame repeatableTaskForm = new  JFrame();
    public JButton repeatableTaskButton = new JButton("Create task");

    public void createRepeatableTaskForm() {

        repeatableTaskForm.setSize(350,450);
        repeatableTaskForm.setLocationRelativeTo(null);
        repeatableTaskForm.setResizable(false);

        repeatableTaskButton.setBounds(30,300,280,40);

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

    public void close () {
        repeatableTaskForm.setVisible(false);
    }

    public void addTasksListener(ActionListener actionListener) {
        repeatableTaskButton.addActionListener(actionListener);
        cancelButton.addActionListener(actionListener);
    }

}
