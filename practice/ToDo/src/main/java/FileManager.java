import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileManager {
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

    public ArrayList<Task> readFromFile() {
        ArrayList<Task> tasks;
        try {
            if (!file.exists()) file.createNewFile();
            reader = new JsonReader(new FileReader(file));
            try {
                tasks = gson.fromJson(reader, new TypeToken<ArrayList<Task>>(){}.getType());
                if (tasks == null) {
                    tasks = new ArrayList<>();
                }
            } catch (JsonIOException e) {
                throw new JsonIOException(e);
            }
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return tasks;
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
