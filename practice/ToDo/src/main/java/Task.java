import com.google.gson.annotations.Expose;

public class Task {
    @Expose
    private String title;
    @Expose
    private String dueDate;
    @Expose
    private boolean completion;
    @Expose
    private String tag;

    public Task(String title, String dueDate, String tag) {
        this.title = title;
        this.dueDate = dueDate;
        completion = false;
        this.tag = tag;
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

    public String getTag() {
        return this.tag;
    }

    public void changeTitle(String newTitle) {
        this.title = newTitle;
    }

    public void changeDueDate(String newDueDate) {
        this.dueDate = newDueDate;
    }

    public void changeTag(String newTag) {
        this.tag = newTag;
    }


    public void changeCompletion(boolean newCompletion) {
        this.completion = newCompletion;
    }
}

