import java.util.ArrayList;

public class ToDo {
    public static void main(String[] args) {
        TaskManager myTaskManager = new TaskManager();

        FileManager myFileManager = new FileManager("toDo.json");
        myTaskManager.taskList = myFileManager.readFromFile();

        ArrayList<Task> overdueTasks = myTaskManager.findOverdue();
        if (!overdueTasks.isEmpty()) {
            System.out.println("Following tasks are overdue");
            myTaskManager.showTasks(overdueTasks);
        }

        while (true) {
            System.out.println("""
                1. Save and quit
                2. Add Task
                3. Edit Task
                4. Delete Task
                5. Mark Completion
                6. Add/Filter tags
                7. Show Tasks""");
            int menuInput = TaskManager.MenuNavigator.getMenuInput("Input a number between 1 and 7", 7);
            switch (menuInput) {
                case 0:
                    myFileManager.writeToFile(myTaskManager.taskList);
                    return;
                case 1:
                    Task newTask = myTaskManager.createNewTaskFromInput();
                    myTaskManager.storeTask(newTask);
                    break;
                case 2:
                    myTaskManager.editTask();
                    break;
                case 3:
                    myTaskManager.deleteTask();
                    break;
                case 4:
                    myTaskManager.showTasks(myTaskManager.taskList);
                    int selectCompleteTask = TaskManager.MenuNavigator.getMenuInput("Choose a task to mark completed", myTaskManager.taskList.size());
                    myTaskManager.markCompleted(selectCompleteTask);
                    break;
                case 5:
                    String searchTag = TaskManager.MenuNavigator.getTextInput("Input a tag to search");
                    ArrayList<Task> foundTags = myTaskManager.findTags(searchTag);
                    myTaskManager.showTasks(foundTags);
                    break;
                case 6:
                    myTaskManager.showTasks(myTaskManager.taskList);
                    break;
                default: break;
            }
        }
    }
}
