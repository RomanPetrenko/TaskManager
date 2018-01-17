package com.roman.petrenko.view;

import javax.swing.*;
import java.awt.event.ActionListener;

public class SelectTaskForm extends MainPanel {

    public JFrame selectTaskForm = new  JFrame();
    public JButton editTaskButton = new JButton("Select Task");
    public JButton deleteTaskButton = new JButton("Delete Task");
    public JComboBox comboBox = new JComboBox();

    public void createSelectTaskForm(JButton button) {

        selectTaskForm.setSize(350,500);
        selectTaskForm.setLocationRelativeTo(null);

        button.setBounds(30,300,280,40);
        selectTaskForm.add(button);
        selectTaskForm.add(cancelButton);

        comboBox.setBounds(30, 100, 300,100);
        comboBox.setSelectedIndex(0);
        selectTaskForm.add(comboBox);

        selectTaskForm.setLayout(null);
        selectTaskForm.setVisible(true);
    }

    public void close () {
        selectTaskForm.remove(editTaskButton);
        selectTaskForm.remove(deleteTaskButton);
        selectTaskForm.setVisible(false);
    }

    public void addTasksListener(ActionListener actionListener) {
        editTaskButton.addActionListener(actionListener);
        deleteTaskButton.addActionListener(actionListener);
        cancelButton.addActionListener(actionListener);
    }
}
