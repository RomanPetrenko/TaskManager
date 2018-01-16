package com.roman.petrenko.model;

import java.util.Iterator;

/**
 * Abstract class for create task list
 */
public abstract class TaskList implements Iterable<Task>, Cloneable{

    /**
     * abstract method where we add new task to task list
     *
     * @param task with Task type
     * @see add
     */
    public abstract void add(Task task);

    /**
     * abstract method where we remove task from task list
     *
     * @param task with Task type
     * @return boolean
     * @see remove
     */
    public abstract boolean remove(Task task);

    /**
     * abstract method where we get size of task list
     *
     * @return size
     * @see size
     */
    public abstract int size();

    /**
     * abstract method where we get task by index of task list
     *
     * @param index of task
     * @return task
     * @see getTask
     */
    public abstract Task getTask(int index);


    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (this == obj) return true;

        int index = 0;
        boolean result = true;

        if (obj instanceof TaskList) {
            TaskList listCopy = (TaskList) obj;
            if (this.size() == listCopy.size()) {
                for (Task i : this) {
                    if (!i.equals(listCopy.getTask(index))) {
                        result = false;
                    }
                    index++;
                }
            }
        } else return false;
        return result;
    }

    @Override
    public int hashCode() {
        int result = 1;
        Iterator iterator = this.iterator();
        while (iterator.hasNext()) {
            result *= 11 * iterator.next().hashCode();
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder myString = new StringBuilder();
        Iterator iterator = this.iterator();
        while (iterator.hasNext()) {
            myString.append(iterator.next().toString() + "\n");
        }
        return myString.toString();
    }
}

