package com.roman.petrenko.model;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Class for create array task list
 */
public class ArrayTaskList extends TaskList {

    private int size;
    private int maxSize = 100;

    private Task[] arrayTaskList = new Task[maxSize];

    /**
     * constructor without arguments
     *
     * @see ArrayTaskList
     */
    public ArrayTaskList() {
        arrayTaskList = new Task[maxSize];
    }

    public Iterator<Task> iterator() {
        return new ArrayListIterator();
    }

    /**
     * method where we add new task to array task list
     *
     * @param task with Task type
     * @throws NullPointerException if field 'task' is null
     * @see add
     */
    @Override
    public void add(Task task)
            throws NullPointerException {
        if (task == null)
            throw new NullPointerException("Field \"Task\" can't be empty");
        ++size;
        if (size > maxSize) {
            maxSize = (size * 3 / 2) + 1;
            Task[] tmp = new Task[maxSize];
            for (int i = 0; i < size - 1; i++) {
                tmp[i] = this.arrayTaskList[i];
            }
            arrayTaskList = tmp;
            arrayTaskList[size - 1] = task;
        } else {
            arrayTaskList[size - 1] = task;
        }
    }

    /**
     * method where we remove task from array task list
     *
     * @param task with Task type
     * @return boolean
     * @throws NullPointerException if field 'task' is null
     * @see remove
     */
    @Override
    public boolean remove(Task task) throws NullPointerException {
        if (task == null)
            throw new NullPointerException("Field \"Task\" can't be empty");
        for (int i = 0; i < size; i++) {
            if (arrayTaskList[i].equals(task)) {
                for (int j = i; j < size - 1; j++) {
                    arrayTaskList[j] = arrayTaskList[j + 1];
                }
                arrayTaskList[size - 1] = null;
                size--;
                return true;
            }
        }
        return false;
    }

    /**
     * method where we get size of array task list
     *
     * @return size
     * @see size
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * method where we get task by index of array task list
     *
     * @param index of task
     * @return task
     * @throws IndexOutOfBoundsException if index out of range
     * @see getTask
     */
    @Override
    public Task getTask(int index)
            throws IndexOutOfBoundsException {
        if (index < 0 || index > size() - 1)
            throw new IndexOutOfBoundsException("Index out of range");

        return arrayTaskList[index];
    }

    /**
     * Class for iteration tasks of array task list
     */
    private class ArrayListIterator implements Iterator<Task> {
        private int index = 0;

        public boolean hasNext() {
            return index < size();
        }

        public Task next() throws NoSuchElementException {
            if (!hasNext()) {
                throw new NoSuchElementException("No such element");
            } else {
                Task nextTask = getTask(index);
                index++;
                return nextTask;
            }
        }

        public void remove() {
            if (index == 0) {
                throw new IllegalStateException();
            }
            --index;
            ArrayTaskList.this.remove(getTask(index));
        }
    }

    @Override
    public ArrayTaskList clone() throws CloneNotSupportedException {
        ArrayTaskList arrayListClone = (ArrayTaskList) super.clone();
        arrayListClone.arrayTaskList = this.arrayTaskList.clone();
        return arrayListClone;
    }


}