import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;


class Task {
    @Expose
    String title;
    @Expose
    String dueDate;
    @Expose
    boolean completion;

    public Task(String title, String dueDate) {
        this.title = title;
        this.dueDate = dueDate;
        completion = false;
    }

    public String getTitle() {
        return title;
    }

    public String getDueDate() {
        return dueDate;
    }

    public boolean getCompletion() {
        return completion;
    }
}

class TaskManager {
    ArrayList<Task> taskList;
    ArrayList<Task> deletedTasks;

    public TaskManager() {
        taskList = new ArrayList<>();
        deletedTasks = new ArrayList<>();
    }

    public void storeTask(Task task) {
        taskList.add(task);
    }

    public void showTasks(ArrayList<Task> tasks) {
        int i = 1;
        System.out.println("Task Title, Due Date, Completed");
        for (Task task : tasks) {
            System.out.println(i + ". " + task.title + ", " + task.dueDate + ", " + task.completion);
        }
        System.out.println("\n");
    }

    public ArrayList<Task> findOverdue() {
        ArrayList<Task> overdueTasks = new ArrayList<>();
        for (Task task : taskList) {
            LocalDate date = LocalDate.parse(task.dueDate);
            if (date.isBefore(LocalDate.now())) {
                overdueTasks.add(task);
            }
        }
        return overdueTasks;
    }

    public void markCompleted(int selectedTask) {
        taskList.get(selectedTask - 1).completion = !taskList.get(selectedTask - 1).completion;
    }
}

class FileManager {
    File file;
    Gson gson;
    JsonReader reader;
    FileWriter writer;

    public FileManager(String filename) {
        gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        try {
            file = new File(filename);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void readFromFile(TaskManager myTaskManager) {
        try {
            if (!file.exists()) file.createNewFile();
            reader = new JsonReader(new FileReader(file));
            try {
                myTaskManager.taskList = gson.fromJson(reader, new TypeToken<ArrayList<Task>>(){}.getType());
                if (myTaskManager.taskList == null) {
                    myTaskManager.taskList = new ArrayList<>();
                }
            } catch (JsonIOException e) {
                throw new JsonIOException(e);
            }
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeToFile(ArrayList<Task> tasks) {
        try {
            writer = new FileWriter(file);
            try {
                gson.toJson(tasks, writer);
            } catch (JsonIOException e) {
                throw new RuntimeException(e);
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

class InputManager {
    Scanner menuInput;

    public InputManager() {
        menuInput = new Scanner(System.in);
    }

    public int takeIntInput() {
        int userInput = menuInput.nextInt();
        menuInput.nextLine();
        return userInput;
    }

    public String takeStringInput() {
        return menuInput.nextLine();
    }
}

public class ToDo {
    public static void main(String[] args) {
        InputManager myInputManager = new InputManager();

        TaskManager myTaskManager = new TaskManager();

        FileManager myFileManager = new FileManager("toDo.json");
        myFileManager.readFromFile(myTaskManager);

        ArrayList<Task> overdueTasks = myTaskManager.findOverdue();
        if (!overdueTasks.isEmpty()) {
            System.out.println("Following tasks are overdue");
            myTaskManager.showTasks(overdueTasks);
        }

        System.out.println("""
                0. Save and quit
                1. Add Task
                2. Edit Task
                3. Delete Task
                4. Mark Completion
                5. Show Tasks\n""");

        while (true) {
            int input = myInputManager.takeIntInput();
            switch (input) {
                case 0:
                    myFileManager.writeToFile(myTaskManager.taskList);
                    myInputManager.menuInput.close();
                    return;
                case 1:
                    System.out.println("Task Title: ");
                    String taskName = myInputManager.takeStringInput();
                    System.out.println("Due Date: (YYYY-MM-DD)");
                    String dueDate = myInputManager.takeStringInput();
                    Task newTask = new Task(taskName, dueDate);
                    myTaskManager.storeTask(newTask);
                    break;
                case 2:
                    System.out.println("Select task to edit");
                    int selectEditTask = myInputManager.takeIntInput();
                    System.out.println("Select field to edit: 1. Title, 2. Due Date");
                    int selectEditField = myInputManager.takeIntInput();
                    System.out.println("Input new value");
                    String newValue = myInputManager.takeStringInput();
                    if (selectEditField == 1) {
                        myTaskManager.taskList.get(selectEditTask - 1).title = newValue;
                    } else {
                        myTaskManager.taskList.get(selectEditTask - 1).dueDate = newValue;
                    }
                    break;
                case 3:
                    System.out.println("Select task to delete (0 to undo last delete)");
                    int selectDeleteTask = myInputManager.takeIntInput();
                    if (selectDeleteTask == 0 && !myTaskManager.deletedTasks.isEmpty()) {
                        int lastDeleted = myTaskManager.deletedTasks.size() - 1;
                        myTaskManager.taskList.add(myTaskManager.deletedTasks.get(lastDeleted));
                    } else {
                        Task removed = myTaskManager.taskList.remove(selectDeleteTask - 1);
                        myTaskManager.deletedTasks.add(removed);
                    }
                    break;
                case 4:
                    myTaskManager.showTasks(myTaskManager.taskList);
                    System.out.println("Select task to mark completed");
                    int selectCompleteTask = myInputManager.takeIntInput();
                    myTaskManager.markCompleted(selectCompleteTask);
                    break;
                case 5:
                    myTaskManager.showTasks(myTaskManager.taskList);
                    break;
                default: break;
            }
        }
    }
}
