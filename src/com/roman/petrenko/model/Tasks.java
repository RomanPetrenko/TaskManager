package com.roman.petrenko.model;

import java.util.*;

public class Tasks {

    public static final Date firstDate = new Date(0);

    /**
     * method where we get task list in exact period of time
     *
     * @param tasks period of time
     * @param start period of time
     * @param end   period of time
     * @return taskList
     * @throws IndexOutOfBoundsException if start before first date
     * @throws IndexOutOfBoundsException if end before first date
     * @throws IndexOutOfBoundsException if start after end
     * @throws ClassCastException        if class wasn't found
     * @see incoming
     */
    public static Iterable<Task> incoming(Iterable<Task> tasks, Date start, Date end)
            throws IndexOutOfBoundsException {
        if (start.before(firstDate))
            throw new IndexOutOfBoundsException("\"Start\" can not be less than zero");
        if (end.before(firstDate))
            throw new IndexOutOfBoundsException("\"End\" can not be less than zero");
        if (start.after(end))
            throw new IndexOutOfBoundsException("\"Start\" can not be after \"End\"");

        TaskList taskList = null;
        try {
            taskList = (TaskList) tasks.getClass().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        //TaskList taskList = new ArrayTaskList();

        for (Task task : tasks) {
            if (task.nextTimeAfter(start) != null
                    && task.nextTimeAfter(start).compareTo(end) != 1) {
                taskList.add(task);
            }
        }
        return taskList;
    }

    /**
     * method where we get calendar of tasks from list
     *
     * @param tasks period of time
     * @param start period of time
     * @param end   period of time
     * @return calendar
     * @throws IndexOutOfBoundsException if start before first date
     * @throws IndexOutOfBoundsException if end before first date
     * @throws IndexOutOfBoundsException if start after end
     * @see calendar
     */
    public static SortedMap<Date, Set<Task>> calendar(Iterable<Task> tasks, Date start, Date end)
            throws IndexOutOfBoundsException {
        if (start.before(firstDate))
            throw new IndexOutOfBoundsException("\"Start\" can not be less than zero");
        if (end.before(firstDate))
            throw new IndexOutOfBoundsException("\"End\" can not be less than zero");
        if (start.after(end))
            throw new IndexOutOfBoundsException("\"Start\" can not be after \"End\"");

        TreeMap<Date, Set<Task>> calendar = new TreeMap<>();
        Iterable<Task> incoming = incoming(tasks, start, end);
        for (Task task : incoming) {
            Date keyDate = task.nextTimeAfter(start);
            while (keyDate != null && keyDate.compareTo(end) <= 0) {
                if (calendar.containsKey(keyDate)) {
                    calendar.get(keyDate).add(task);
                } else {
                    Set<Task> hashTasksSet = new HashSet<>();
                    hashTasksSet.add(task);
                    calendar.put(keyDate, hashTasksSet);
                }
                keyDate = task.nextTimeAfter(keyDate);
            }
        }
        return calendar;
    }

}
