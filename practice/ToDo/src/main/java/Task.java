import com.google.gson.annotations.Expose;
import java.util.ArrayList;

public class Task {
    @Expose
    private String title;
    @Expose
    private String dueDate;
    @Expose
    private boolean completion;
    @Expose
    private ArrayList<String> tags;

    public Task(String title, String dueDate) {
        this.title = title;
        this.dueDate = dueDate;
        completion = false;
        tags = new ArrayList<>();
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

    public ArrayList<String> getTags() {
        return this.tags;
    }

    public void changeTitle(String newTitle) {
        this.title = newTitle;
    }

    public void changeDueDate(String newDueDate) {
        this.dueDate = newDueDate;
    }

    public void changeCompletion(boolean newCompletion) {
        this.completion = newCompletion;
    }
}

