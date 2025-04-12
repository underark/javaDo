import java.time.LocalDate;
import java.util.ArrayList;

public class TaskManager {
    ArrayList<Task> taskList;
    public TaskManager() {
        taskList = new ArrayList<>();
    }

    public Task collectTaskData(InputManager inputManager) {
        System.out.println("Task Title: ");
        String taskName = inputManager.takeStringInput();
        System.out.println("Due Date: (YYYY-MM-DD)");
        String dueDate = inputManager.takeStringInput();
        return new Task(taskName, dueDate);
    }

    public void storeTask(Task task) {
        taskList.add(task);
        System.out.println("Task created successfully");
    }

    public void editTask(InputManager inputManager) {
        this.showTasks(this.taskList);
        int selectEditTask = inputManager.takeZeroIndexInput(this.taskList.size());
        System.out.println("Select field to edit: 1. Title, 2. Due Date 3. Tags");
        int selectEditField = inputManager.takeMenuInput(1, 3);
        System.out.println("Input new value (no value in case of deleting tag)");
        String newValue = inputManager.takeStringInput();

        Task editTask = this.taskList.get(selectEditTask);
        if (selectEditField == 1) {
            editTask.changeTitle(newValue);
        } else if (selectEditField == 2) {
            editTask.changeDueDate(newValue);
        } else {
            System.out.println("Select tag to edit");
            System.out.println(editTask.getTags());
            int editTagNumber = inputManager.takeZeroIndexInput(editTask.getTags().size());
            ArrayList<String> editTags = editTask.getTags();
            if (newValue.isBlank()) {
                editTags.remove(editTagNumber);
                System.out.println("Tag removed successfully");
            } else {
                editTags.set(editTagNumber, newValue);
                System.out.println("Tag edited successfully");
            }
        }
        System.out.println("Task edited successfully");
    }

    public void deleteTask(InputManager inputManager) {
        System.out.println("Select task to delete");
        this.showTasks(this.taskList);
        int selectDeleteTask = inputManager.takeZeroIndexInput(this.taskList.size());
        this.taskList.remove(selectDeleteTask);
        System.out.println("Task deleted successfully");
    }

    public void addTag(InputManager inputManager) {
        System.out.println("Input task number to add tag");
        this.showTasks(this.taskList);
        int selectAddTag = inputManager.takeZeroIndexInput(this.taskList.size());
        System.out.println("Input new tag");
        String tagInput = inputManager.takeStringInput();
        this.taskList.get(selectAddTag).getTags().add(tagInput);
        System.out.println("Tag added successfully");
    }

    public void editTag(InputManager inputManager) {
        System.out.println("Input task number to edit tag");
        this.showTasks(this.taskList);
        int editTaskNumber = inputManager.takeZeroIndexInput(this.taskList.size());
        Task editTask = this.taskList.get(editTaskNumber);

        System.out.println("Input number of tag");
        this.showTags(editTask.getTags());
        int editTagNumber = inputManager.takeZeroIndexInput(editTask.getTags().size());

        System.out.println("Input new tag");
        String newTagValue = inputManager.takeStringInput();
        editTask.getTags().set(editTagNumber, newTagValue);
        System.out.println("Tag successfully changed");
    }

    public void showTasks(ArrayList<Task> tasks) {
        int i = 1;
        System.out.println("Task Title, Due Date, Completed, Tags");
        for (Task task : tasks) {
            System.out.println(i + ". " + task.getTitle() + ", " + task.getDueDate() + ", " + task.getCompletion() + ", " + task.getTags());
            i++;
        }
        System.out.println("\n");
    }

    public void showTags(ArrayList<String> tags) {
        int i = 1;
        for (String tag : tags) {
            System.out.println(i + tag);
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
            for (String tag : task.getTags()) {
                if (tag.compareToIgnoreCase(searchTag) == 0) {
                    foundTags.add(task);
                }
            }
        }
        return foundTags;
    }

    public void markCompleted(int selectedTask) {
        boolean currentCompletion = taskList.get(selectedTask).getCompletion();
        taskList.get(selectedTask).changeCompletion(!currentCompletion);
        System.out.println("Task marked completed successfully");
    }
}