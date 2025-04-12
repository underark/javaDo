import java.util.ArrayList;

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
                5. Add/Filter tags
                6. Show Tasks""");

        while (true) {
            int input = myInputManager.takeMenuInput(0, 6);
            switch (input) {
                case 0:
                    myFileManager.writeToFile(myTaskManager.taskList);
                    myInputManager.closeScanner();
                    return;
                case 1:
                    myTaskManager.storeTask(myTaskManager.collectTaskData(myInputManager));
                    break;
                case 2:
                    myTaskManager.editTask(myInputManager);
                    break;
                case 3:
                    myTaskManager.deleteTask(myInputManager);
                    break;
                case 4:
                    myTaskManager.showTasks(myTaskManager.taskList);
                    int selectCompleteTask = myInputManager.takeZeroIndexInput(myTaskManager.taskList.size());
                    myTaskManager.markCompleted(selectCompleteTask);
                    break;
                case 5:
                    System.out.println("1. Add tag 2. Edit Tag 3. Filter by tag");
                    int tagMenuInput = myInputManager.takeMenuInput(1, 3);
                    if (tagMenuInput == 1) {
                        myTaskManager.addTag(myInputManager);
                    } else if (tagMenuInput == 2) {
                        myTaskManager.editTag(myInputManager);
                    } else {
                        System.out.println("Input tag to search");
                        String searchTag = myInputManager.takeStringInput();
                        ArrayList<Task> foundTags = myTaskManager.findTags(searchTag);
                        myTaskManager.showTasks(foundTags);
                    }
                    break;
                case 6:
                    myTaskManager.showTasks(myTaskManager.taskList);
                    break;
                default: break;
            }
        }
    }
}
