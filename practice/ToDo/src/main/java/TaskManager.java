import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class TaskManager {
    ArrayList<Task> taskList;

    public TaskManager() {
        taskList = new ArrayList<>();
    }

    public Task createNewTaskFromInput() {
        String taskTitle = getNewTaskTitle();
        String taskDueDate = getNewTaskDueDate();
        String taskTag = getNewTaskTag();
        return new Task(taskTitle, taskDueDate, taskTag);
    }

    private String getNewTaskTitle() {
        Scanner titleInput = new Scanner(System.in);
        System.out.println("Input task title");
        return titleInput.nextLine();
    }

    private String getNewTaskDueDate() {
        Scanner dueDateInput = new Scanner(System.in);
        System.out.println("Input due date (YYYY-MM-DD");
        return dueDateInput.nextLine();
    }

    private String getNewTaskTag() {
        Scanner tagInput = new Scanner(System.in);
        System.out.println("Input tag");
        return tagInput.nextLine();
    }

    public void storeTask(Task task) {
        taskList.add(task);
        System.out.println("Task created successfully");
    }

    public void editTask() {
        this.showTasks(this.taskList);
        int selectedTask = MenuNavigator.getMenuInput("Please input a number between 1 and " + this.taskList.size(), this.taskList.size());
        int selectedField = MenuNavigator.getMenuInput("Select field to edit: 1. Title, 2. Due Date 3. Tag", 3);
        String newValue = MenuNavigator.getTextInput("Input new value");
        Task TaskToEdit = this.taskList.get(selectedTask);
        if (selectedField == 0) {
            TaskToEdit.changeTitle(newValue);
        } else if (selectedField == 1) {
            TaskToEdit.changeDueDate(newValue);
        } else {
            TaskToEdit.changeTag(newValue);
        }
        System.out.println("Task edited successfully");
    }

    public void deleteTask() {
        this.showTasks(this.taskList);
        int selectedTask = MenuNavigator.getMenuInput("Select task to delete", this.taskList.size());
        this.taskList.remove(selectedTask);
        System.out.println("Task deleted successfully");
    }


    public void showTasks(ArrayList<Task> tasks) {
        int i = 1;
        System.out.println("Task Title, Due Date, Completed, Tags");
        for (Task task : tasks) {
            System.out.println(i + ". " + task.getTitle() + ", " + task.getDueDate() + ", " + task.getCompletion() + ", " + task.getTag());
            i++;
        }
        System.out.println("\n");
    }

    public ArrayList<Task> findOverdue() {
        ArrayList<Task> overdueTasks = new ArrayList<>();
        for (Task task : taskList) {
            LocalDate date = LocalDate.parse(task.getDueDate());
            if (date.isBefore(LocalDate.now()) && !task.getCompletion()) {
                overdueTasks.add(task);
            }
        }
        return overdueTasks;
    }

    public ArrayList<Task> findTags(String searchTag) {
        ArrayList<Task> foundTags = new ArrayList<>();

        for (Task task : taskList) {
            if (task.getTag().compareToIgnoreCase(searchTag) == 0) {
                foundTags.add(task);
            }
        }
        return foundTags;
    }

    public void markCompleted(int selectedTask) {
        boolean currentCompletion = taskList.get(selectedTask).getCompletion();
        taskList.get(selectedTask).changeCompletion(!currentCompletion);
        System.out.println("Task marked completed successfully");
    }

    final static class  MenuNavigator {
        private MenuNavigator() {}

        static public int getMenuInput(String prompt, int rangeHigh) {
            Scanner menuInput = new Scanner(System.in);
            System.out.println(prompt);
            int output = menuInput.nextInt();
            menuInput.nextLine();
            while (output < 1 || output > rangeHigh) {
                System.out.println(prompt);
                output = menuInput.nextInt();
                menuInput.nextLine();
            }
            return output - 1;
        }

        static public String getTextInput(String prompt) {
            System.out.println(prompt);
            Scanner textInput = new Scanner(System.in);
            return textInput.nextLine();
        }
    }
}