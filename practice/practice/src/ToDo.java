import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

class Task {
    String title;
    boolean completion;

    public Task(String title) {
        this.title = title;
        completion = false;
    }
}

class TaskManager {
    ArrayList<Task> tasks;

    public TaskManager() {
        tasks = new ArrayList<>();
    }

    public void addTask(Task newTask) {
        tasks.add(newTask);
    }
}

class FileReading {
    String fileName;

    public FileReading(String filename) {
        fileName = filename;
    }

    public void makeFile() {
        File output = new File("toDo.txt");
        try {
            output.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class FileWriting {
    String fileName;

    public FileWriting(String filename) {
        fileName = filename;
    }

    public FileWriter makeFileWriter(String filename) {
        FileWriter fileWrite;
        try {
            fileWrite = new FileWriter(filename);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileWrite;
    }

    public void writeToFile(ArrayList<Task> tasks, FileWriter writer) {
        for (Task task : tasks) {
            try {
                writer.write(task.title);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

public class ToDo {
    public static void main(String[] args) {
        Scanner menuInput = new Scanner(System.in);
        Scanner taskInput = new Scanner(System.in);

        // Make our task manager to hold outstanding tasks
        TaskManager myManager = new TaskManager();

        // Make a new file
        FileReading myFile = new FileReading("toDo.txt");
        myFile.makeFile();

        // Make a file writer
        FileWriting myWriter = new FileWriting("toDo.txt");
        FileWriter fileWriter = myWriter.makeFileWriter(myWriter.fileName);
        myWriter.writeToFile(myManager.tasks, fileWriter);

        System.out.println("0. Save and quit\n1. Add Task");

        while (true) {
            int input = menuInput.nextInt();
            switch (input) {
                case 0:
                    myWriter.writeToFile(myManager.tasks, fileWriter);
                    try {
                        fileWriter.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    return;
                case 1:
                    System.out.println("Task Title: ");
                    String taskName = taskInput.nextLine() + "\n";
                    Task newTask = new Task(taskName);
                    myManager.addTask(newTask);
            }
        }
    }
}
