package com.roman.petrenko.model;

import java.util.Iterator;

/**
 * Class for create linked task list
 */
public class LinkedTaskList extends TaskList {
    private int size;

    private class Node {
        public Task task;
        public Node next;

        private Node(Task task) {
            this.task = task;
        }
    }

    private Node head;
    private Node tail;

    public Iterator<Task> iterator() {
        return new LinkedListIterator();
    }

    /**
     * method where we add new task to linked task list
     * @see add
     * @param task with Task type
     * @throws  NullPointerException if field 'task' is null
     */
    @Override
    public void add(Task task)
            throws NullPointerException {
        if (task == null)
            throw new NullPointerException("Field \"Task\" can't be empty");

        Node node = new Node(task);

        if (head == null) {
            head = node;
            tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
        size++;

    }

    /**
     * method where we remove task from linked task list
     * @see remove
     * @param task with Task type
     * @throws  NullPointerException if field 'task' is null
     * @return boolean
     */
    @Override
    public boolean remove(Task task)
            throws NullPointerException {
        if (task == null)
            throw new NullPointerException("Field \"Task\" can't be empty");

        Node prev = null;
        Node current = head;

        while (current != null) {
            if (current.task.equals(task)) {
                if (prev == null) { //top of list
                    // head -> prev -> curr/null
                    // head ---------> curr/null
                    head = head.next;
                    // if the list is empty > tail = null
                    if (head == null) {
                        tail = null;
                    }
                } else { //middle or end of list
                    // head -> prev -> curr -> tail/null
                    // head -> prev ---------> tail/null
                    prev.next = current.next;
                    // if the end of list
                    if (current.next == null) {
                        tail = prev;
                    }
                }
                size--;
                return true;
            }
            prev = current;
            current = current.next;
        }
        return false;
    }

    /**
     * method where we get size of linked task list
     * @see size
     * @return size
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * method where we get task by index of linked task list
     * @see getTask
     * @param index of task
     * @throws  IndexOutOfBoundsException if index out of range
     * @return task;
     */
    @Override
    public Task getTask(int index)
            throws IndexOutOfBoundsException {
        if (index < 0 || index > size() - 1)
            throw new IndexOutOfBoundsException("Index out of range");

        Node element = head;

        for (int i = 0; i < index; i++) {
            element = element.next;
        }
        return element.task;
    }

    /**
     * Class for iteration tasks of linked task list
     */
    private class LinkedListIterator implements Iterator<Task> {
        private int index = 0;
        private Node current = head;

        @Override
        public boolean hasNext() {
            return index < size();
        }

        @Override
        public Task next() throws NullPointerException {
            if (!hasNext()) {
                throw new IllegalStateException();
            }
            Task currentIteratorTask = current.task;
            current = current.next;
            index++;
            return currentIteratorTask;
        }

        @Override
        public void remove() throws IllegalStateException {

            if (index == 0)
                throw new IllegalStateException();
            else {
                LinkedTaskList.this.remove(getTask(--index));
            }
        }
    }

    @Override
    public LinkedTaskList clone() throws CloneNotSupportedException {
        return (LinkedTaskList) super.clone();
    }
}