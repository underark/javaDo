import java.util.Scanner;
import java.io.*;

class Task {
    String title;
    boolean completion;

    public Task(String title) {
        this.title = title;
        completion = false;
    }

    public void getTask() {
        System.out.println(title);
    }
}

class fileReader {
    String fileName;

    public fileReader(String filename) {
        fileName = filename;
    }

    public void makeFile(String filename) {
        File output = new File("toDo.txt");
        try {
            output.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

public class ToDo {
    public static void main(String[] args) {
        Scanner menuInput = new Scanner(System.in);
        Scanner taskInput = new Scanner(System.in);

        fileReader newFile = new fileReader("toDo.txt");
        newFile.makeFile(newFile.fileName);

        while (true) {
            int input = menuInput.nextInt();

            switch (input) {
                case 0:
                    return;
                case 1:
                    String taskName = taskInput.nextLine() + "\n";
                    Task newTask = new Task(taskName);
            }
        }
    }
}
