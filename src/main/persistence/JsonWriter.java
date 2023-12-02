package persistence;


import model.SetOfFlashcardSets;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

// Represents a JSON file writer that writes a representation of flashcard data as a JSON file.
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to a specified destination
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // Modifies: this
    // Effects: opens a writer, throws FileNotFoundException if destination file cannot be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // Modifies: this
    // Effects: writes JSON representation of flashcard data to file
    public void write(SetOfFlashcardSets sofs) {
        JSONObject json = sofs.toJson();
        saveToFile(json.toString(TAB));
    }

    // Modifies: this
    // Effects: closes writer
    public void close() {
        writer.close();
    }

    // Modifies: this
    // Effects: writes the JSON string to a file
    private void saveToFile(String json) {
        writer.print(json);
    }


}
