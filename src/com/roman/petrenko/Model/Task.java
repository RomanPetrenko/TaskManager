package com.roman.petrenko.Model;

import java.util.Date;

/**
 * Class for create tasks and carry out start actions with them
 */
public class Task implements Cloneable {

    private String title;
    private Date time;
    private Date start;
    private Date end;
    private int interval;
    private boolean active;
    private boolean repeated;
    public static final Date firstTime = new Date(0);

    /**
     * creating task using first constructor
     * @see Task
     * @param title of un repeat task
     * @param time of un repeat task
     * @throws  IllegalArgumentException if title is empty
     * @throws  IllegalArgumentException if time < 0
     */
    public Task(String title, Date time)
            throws IllegalArgumentException {
        if (title.isEmpty())
            throw new IllegalArgumentException("Title can not be empty");
        if (time.before(firstTime))
            throw new IllegalArgumentException("Time can not be earlier than 01.01.1970");
        this.title = title;
        this.time = time;
        active = false;
        repeated = false;
    }
    /**
     * creating task using second constructor
     * @see Task
     * @param title of repeat task
     * @param start of repeat task
     * @param end of repeat task
     * @param interval of repeat task
     * @throws  IllegalArgumentException if if title is empty
     * @throws  IllegalArgumentException if start time < 0
     * @throws  IllegalArgumentException if end time < 0
     * @throws  IllegalArgumentException if interval < 0
     */
    public Task(String title, Date start, Date end, int interval)
            throws IllegalArgumentException {
        if (title.isEmpty())
            throw new IllegalArgumentException("Title can not be empty");
        if (start.before(firstTime))
            throw new IllegalArgumentException("Start time can not be earlier than 01.01.1970");
        if (interval < 0)
            throw new IllegalArgumentException("Interval can not be less than zero");
        this.title = title;
        this.start = start;
        this.end = end;
        this.interval = interval;
        active = false;
        repeated = true;
    }
    /**
     * method where we get the title of a task
     * @see getTitle
     * @return title
     */
    public String getTitle() {
        return title;
    }
    /**
     * method where we set the title of a task
     * @see setTitle
     * @param title of a task
     * @throws  IllegalArgumentException if title is empty
     */
    public void setTitle(String title)
            throws IllegalArgumentException {
        if (title.isEmpty())
            throw new IllegalArgumentException("Title can not be empty");
        this.title = title;
    }
    /**
     * method where we get the status of a task
     * @see isActive
     * @return active
     */
    public boolean isActive() {
        return active;
    }
    /**
     * method where we set the status of a task
     * @see setActive
     * @param active status of a task
     */
    public void setActive(boolean active) {
        this.active = active;
    }
    /**
     * method where we get time of repeat task
     * @see getTime
     * @return time
     */
    public Date getTime() {
        if (!repeated) return time;
        else return start;
    }
    /**
     * method where we set time of unrepeat task
     * @see setTime
     * @param time of unrepeat task
     * @throws  IllegalArgumentException if time < 0
     */
    public void setTime(Date time)
            throws IllegalArgumentException {
        if (time.before(firstTime))
            throw new IllegalArgumentException("Start time can not be earlier than 01.01.1970");
        this.time = time;
        if (repeated) repeated = false;
    }
    /**
     * method where we get start time of a task
     * @see getStartTime
     * @return start
     */
    public Date getStartTime() {
        if (repeated) return start;
        else return time;
    }
    /**
     * method where we get end time of a task
     * @see getEndTime
     * @return end
     */
    public Date getEndTime() {
        if (repeated) return  end;
        else return time;
    }
    /**
     * method where we get repeat interval of a task
     * @see getRepeatInterval
     * @return interval
     */
    public int getRepeatInterval() {
        if (repeated) return interval;
        else return 0;
    }
    /**
     * method where we set start time, end time, repeat interval of a task
     * @see setTime
     * @param start of repeat task
     * @param end of repeat task
     * @param interval of repeat task
     * @throws  IllegalArgumentException if start time < 0
     * @throws  IllegalArgumentException if end time < 0
     * @throws  IllegalArgumentException if interval < 0
     */
    public void setTime(Date start, Date end, int interval)
            throws IllegalArgumentException {
        if (start.before(firstTime))
            throw new IllegalArgumentException("Start time can not be earlier than 01.01.1970");
        if (interval < 0)
            throw new IllegalArgumentException("Interval can not be less than zero");
        this.start = start;
        this.end = end;
        this.interval = interval;
        repeated = true;
    }
    /**
     * method where we get information about the repetition of a task
     * @see isRepeated
     * @return repeated
     */
    public boolean isRepeated() {
        return repeated;
    }
    /**
     * method where we find next time after current time of a task
     * @see nextTimeAfter
     * @param current time
     * @throws  IllegalArgumentException if current < 0
     * @return time
     */
    public Date nextTimeAfter(Date current)
            throws IllegalArgumentException, NullPointerException {
        if (current.before(firstTime))
            throw new IllegalArgumentException("Time can not be earlier than 01.01.1970");
        if (isActive() && !isRepeated() && (current.before(time)))
            return time; //for unrepeated tasks
        if (isActive() && isRepeated() && (current.before(end))) { //for repeated tasks
            for (long result = getStartTime().getTime(); result <= getEndTime().getTime();
                 result += getRepeatInterval() * 1000) {  //convert to seconds
                if (result > current.getTime()) {
                    return new Date(result);
                }
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object target) {
        if (target != null && target.getClass() == this.getClass()) {
            Task temp = (Task) target;
            if (temp.getTitle().equals(this.getTitle())
                    && this.isActive() == temp.isActive()
                    && this.getStartTime().compareTo(temp.getStartTime()) == 0
                    && this.getEndTime().compareTo(temp.getEndTime()) == 0
                    && this.getRepeatInterval() == temp.getRepeatInterval())
                return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.getTitle().hashCode()*11;
    }

    @Override
    public String toString() {
        if (this.repeated)
            return "Task" +
                    "(" + title +
                    "): start = " + start +
                    ", end = " + end +
                    ", interval = " + interval +
                    ", active = " + active +
                    ", repeated. ";
        return "Task" +
                "(" + title +
                "): time = " + time +
                ", active = " + active +
                ", unrepeated. ";
    }


    @Override
    public final Task clone() throws CloneNotSupportedException {
        Task cloneOfTask = (Task) super.clone();
        cloneOfTask.setTime(new Date(this.getStartTime().getTime()), new Date(this
                .getEndTime().getTime()),this.interval);

        return cloneOfTask;
    }

}