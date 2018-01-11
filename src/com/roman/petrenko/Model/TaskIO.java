package com.roman.petrenko.Model;

import java.io.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class TaskIO {

    public static final String datePattern = "[yyyy-MM-dd HH:mm:ss.sss]";
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);

    /**
     * method where we write tasks to output stream in binary format
     * @see write
     * @param tasks
     * @param OutputStream
     * @throws IOException
     */
    public static void write(TaskList tasks, OutputStream out) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(out)) {
            dos.writeInt(tasks.size());
            for (Task task : tasks) {
                dos.writeInt(task.getTitle().length());
                dos.writeBytes(task.getTitle());
                dos.writeBoolean(task.isActive());
                dos.writeInt(task.getRepeatInterval());
                dos.writeLong(task.getStartTime().getTime());
                if (task.isRepeated()) {
                    dos.writeLong(task.getStartTime().getTime());
                    dos.writeLong(task.getEndTime().getTime());
                } else {
                    dos.writeLong(task.getTime().getTime());
                }
            }
        }
        catch(IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * method where we read tasks from output stream in binary format
     * @see read
     * @param tasks
     * @param OutputStream
     * @throws IOException
     */
    public static void read(TaskList tasks, InputStream in) throws IOException{
        try (DataInputStream dis = new DataInputStream(in)) {
            for (int i = 0; i < dis.readInt(); i++) {

                Task inputTask = null;
                String title = dis.readUTF();
                boolean active = dis.readBoolean();
                int interval = dis.readInt();
                Date startTime = new Date(dis.readLong());

                if (interval > 0) {
                    Date endTime = new Date(dis.readLong());
                    inputTask = new Task(title, startTime, endTime, interval);
                } else {
                    inputTask = new Task(title, startTime);
                }
                inputTask.setActive(active);
                tasks.add(inputTask);
            }
        }
        catch(IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * method where we write tasks to file in binary format
     * @see writeBinary
     * @param tasks
     * @param file
     * @throws IOException
     */
    public static void writeBinary(TaskList tasks, File file) throws IOException {
        try(FileOutputStream fos = new FileOutputStream(file)) {
            write(tasks,fos);
        }
        catch(IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * method where we read tasks from file in binary format
     * @see readBinary
     * @param tasks
     * @param file
     * @throws IOException
     */
    public static void readBinary(TaskList tasks, File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file)){
            read(tasks, fis);
        }
        catch(IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * method where we write tasks to output stream in text format
     * @see write
     * @param tasks
     * @param out
     * @throws IOException
     */
    public static void write(TaskList tasks, Writer out) throws IOException {
        try {
            int line = 1;
            for (Task t: tasks) {
                String activeStr;
                if (t.isActive()) {
                    activeStr = "";
                } else activeStr = "is inactive";

                String newTitle = "\"" +
                        t.getTitle().replaceAll("\"", "\"\"") + "\"";
                out.write(newTitle);
                if (!t.isRepeated()) {
                    out.write(" at " + dateFormat.format(t.getStartTime()) + activeStr);
                } else {
                    out.write(" from " + dateFormat.format(t.getStartTime())
                            + " to " + dateFormat.format(t.getEndTime())
                            + " every " + intervalToString(t.getRepeatInterval
                            ()) + activeStr);
                }
                if (line < tasks.size()) {
                    out.write(";");
                } else {
                    out.write(".");
                }
                out.write(System.getProperty("line.separator"));
                line++;
            }
        } finally {
            out.flush();
            out.close();
        }
    }

    /**
     * method where we read tasks from output stream in text format
     * @see read
     * @param tasks
     * @param in
     * @throws IOException
     */
    public static void read(TaskList tasks, Reader in) throws IOException, ParseException {
        try (BufferedReader bufferedReader = new BufferedReader(in)){
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                Task task = parseTask(s);
                tasks.add(task);
            }
        }
        catch(IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * method where we write tasks to file in text format
     * @see writeText
     * @param tasks
     * @param file
     * @throws IOException
     */
    public static void writeText(TaskList tasks, File file) throws IOException {
        try (FileWriter writer = new FileWriter(file)) {
            write(tasks, writer);
        }
        catch(IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * method where we read tasks from file in text format
     * @see readText
     * @param tasks
     * @param file
     * @throws IOException
     */
    public static void readText(TaskList tasks, File file) throws IOException, ParseException {
        try (FileReader reader = new FileReader(file)) {
            read(tasks, reader);
        }
        catch(IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * method where we convert interval to string
     * @see readText
     * @param time
     * @return result
     */
    public static String intervalToString(int time) {
        StringBuffer result = new StringBuffer("[");
        int days = time / (60 * 60 * 24);
        int hours = (time - days * 60 * 60 * 24) / (60 * 60);
        int minutes = (time - (days * 60 * 60 * 24) - (hours * 60 * 60)) / 60;
        int seconds = time - (days * 60 * 60 * 24) - (hours * 60 * 60) - (minutes * 60);

        if (days > 0) result.append(days + " day(s) ");
        if (hours > 0) result.append(hours + " hour(s) ");
        if (minutes > 0) result.append(minutes +  " minute(s) ");
        if (seconds > 0) result.append(seconds + " second(s) ");
        result = result.append("]");
        return result.toString();
    }

    /**
     * method where we parse task from string format
     * @see parseTask
     * @param s string
     * @throws ParseException
     * @return task
     */
    private static Task parseTask(String s) throws ParseException {
        Task task;
        String title;
        Date startTime;
        Date endTime;
        int interval;

        if (s.contains("to")) {
            title = s.substring(1, s.lastIndexOf(" from ") - 1);
            title = title.replaceAll("\"\"", "\"");
            int startTimePosition = s.lastIndexOf(" from ") + " from ".length();
            String startTimeString = s.substring(startTimePosition, startTimePosition + datePattern.length());
            startTime = dateFormat.parse(startTimeString);
            int endTimePos = s.lastIndexOf(" to ") + " to ".length();
            String endTimeString = s.substring(endTimePos, endTimePos + datePattern.length());
            endTime = dateFormat.parse(endTimeString);
            String stringToInterval = s.substring(s.lastIndexOf("[") + 1, s.lastIndexOf("]"));
            interval = parseInterval(stringToInterval);
            task = new Task(title, startTime, endTime, interval);
        } else {
            title = s.substring(1, s.lastIndexOf(" at ") - 1);
            title = title.replaceAll("\"\"", "\"");
            int timePosition = s.lastIndexOf(" at ") + " at ".length();
            String timeString = s.substring(timePosition, timePosition + datePattern.length());
            startTime = dateFormat.parse(timeString);
            task = new Task(title, startTime);
        }
        if (!s.contains("inactive")) {
            task.setActive(true);
        }
        return task;
    }

    /**
     * method where we parse interval from string format
     * @see parseInterval
     * @param stringToInterval
     * @throws ParseException
     * @throws IllegalArgumentException
     * @return day
     * @return hour
     * @return minute
     * @return second
     */
    private static int parseInterval(String stringToInterval) throws
            ParseException, IllegalArgumentException {
        int day = 0;
        int hour = 0;
        int minute = 0;
        int second = 0;
        String[] dividedString = stringToInterval.split(" ");
        for (int i = 0; i < dividedString.length; i = i + 2) {
            if (dividedString[i + 1].contains("day")) {
                day = Integer.parseInt(dividedString[i]);
                continue;
            }
            if (dividedString[i + 1].contains("hour")) {
                hour = Integer.parseInt(dividedString[i]);
                continue;
            }
            if (dividedString[i + 1].contains("minute")) {
                minute = Integer.parseInt(dividedString[i]);
                continue;
            }
            if (dividedString[i + 1].contains("second")) {
                second = Integer.parseInt(dividedString[i]);
            }
        }
        if (day < 0 || hour < 0 || minute < 0 || second < 0 || hour >= 24 ||
                minute > 59 || second > 59) {
            throw new IllegalArgumentException("Time out of range");
        }
        return ((day * 60 * 60 * 24) + (hour * 60 * 60) + (minute * 60) + second);
    }

}
