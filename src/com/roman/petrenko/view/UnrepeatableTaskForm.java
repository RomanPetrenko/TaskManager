package com.roman.petrenko.view;

import javax.swing.*;
import java.awt.event.ActionListener;

public class UnrepeatableTaskForm extends MainPanel {

    public JFrame unRepeatableTaskForm = new  JFrame();
    public JButton unrepeatableTaskButton = new JButton("Create task");

    public void createUnrepeatableTaskForm() {

        unRepeatableTaskForm.setSize(350,450);
        unRepeatableTaskForm.setLocationRelativeTo(null);
        unRepeatableTaskForm.setResizable(false);

        unrepeatableTaskButton.setBounds(30,300,280,40);

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

    public void close () {
        unRepeatableTaskForm.setVisible(false);
    }

    public void addTasksListener(ActionListener actionListener) {
        unrepeatableTaskButton.addActionListener(actionListener);
        cancelButton.addActionListener(actionListener);
    }

}
