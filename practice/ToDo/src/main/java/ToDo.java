import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.io.*;
import com.google.gson.Gson;
import java.time.LocalDateTime;


class Task {
    String title;
    String dueDate;
    boolean completion;

    public Task(String title, String dueDate) {
        this.title = title;
        this.dueDate = dueDate;
        completion = false;
    }
}

class TaskManager {
    ArrayList<Task> taskList;

    public TaskManager() {
        taskList = new ArrayList<>();
    }

    public void storeTask(Task task) {
        taskList.add(task);
    }

    public void showTasks() {
        int i = 1;
        System.out.println("Task Title, Due Date, Completed");
        for (Task task : taskList) {
            System.out.println(i + ". " + task.title + ", " + task.dueDate + ", " + task.completion);
        }
    }

    public void markCompleted(int selectedTask) {
        taskList.get(selectedTask - 1).completion = !taskList.get(selectedTask - 1).completion;
    }
}

class FileManager {
    File file;
    Gson gson;
    Scanner reader;
    FileWriter writer;

    public FileManager(String filename) {
        gson = new Gson();

        try {
            file = new File(filename);
            file.createNewFile();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            reader = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void readFromFile(TaskManager myTaskManager) {
        while (reader.hasNextLine()) {
            String taskData = reader.nextLine();
            Task task = gson.fromJson(taskData, Task.class);
            myTaskManager.storeTask(task);
        }
    }

    public void writeToFile(ArrayList<Task> tasks) {
        try {
            writer = new FileWriter(file);

            for (Task task : tasks) {
                try {
                    String jsonText = gson.toJson(task);
                    writer.write(jsonText + "\n");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

public class ToDo {
    public static void main(String[] args) {
        Scanner menuInput = new Scanner(System.in);
        Scanner taskInput = new Scanner(System.in);

        // Task manager allows us to store tasks and interact with them
        TaskManager myTaskManager = new TaskManager();

        // File manager will handle file i/o including reading from the file
        FileManager myFileManager = new FileManager("toDo.json");
        myFileManager.readFromFile(myTaskManager);
        myFileManager.reader.close();

        System.out.println("""
                0. Save and quit
                1. Add Task
                2. Display Tasks
                3. Mark Completion
                4. Sort by""");

        while (true) {
            int input = menuInput.nextInt();
            switch (input) {
                case 0:
                    myFileManager.writeToFile(myTaskManager.taskList);
                    menuInput.close();
                    taskInput.close();
                    return;
                case 1:
                    System.out.println("Task Title: ");
                    String taskName = taskInput.nextLine();
                    System.out.println("Due Date: (YYYY-MM-DD)");
                    String dueDate = taskInput.next();

                    Task newTask = new Task(taskName, dueDate);
                    myTaskManager.storeTask(newTask);
                    break;
                case 2:
                    myTaskManager.showTasks();
                    break;
                case 3:
                    myTaskManager.showTasks();
                    int menuOption = menuInput.nextInt();
                    myTaskManager.markCompleted(menuOption);
                    break;
                case 4:
                default: break;
            }
        }
    }
}
