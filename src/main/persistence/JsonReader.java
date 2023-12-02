package persistence;


import model.Flashcard;
import model.FlashcardSet;
import model.SetOfFlashcardSets;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads flashcard data stored in JSON file.
public class JsonReader {
    private String source;

    // EFFECTS: constructs a JSON reader to read a JSON file
    public JsonReader(String source) {
        this.source = source;
    }


    // Effects: reads a set of flashcard sets and returns flashcard data, throws IOException if error in reading data.
    public SetOfFlashcardSets read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseSetOfFlashcardSets(jsonObject);
    }

    // Effects: reads JSON file as string and returns contents
    private String readFile(String source) throws IOException {
        StringBuilder builder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> builder.append(s));
        }
        return builder.toString();
    }

    // Effects: parses set of flashcard sets from JSON object and returns result
    private SetOfFlashcardSets parseSetOfFlashcardSets(JSONObject jsonObject) {
        SetOfFlashcardSets sofs = new SetOfFlashcardSets();
        addSets(sofs, jsonObject);
        return sofs;
    }

    // Modifies: SetOfFlashcardSets
    // Effects: parses flashcard sets from JSON object and adds to set of flashcard sets
    private void addSets(SetOfFlashcardSets sofs, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("flashcardsets");
        for (Object json : jsonArray) {
            JSONObject nextSet = (JSONObject) json;
            addSet(sofs, nextSet);
        }
    }

    // Modifies: SetOfFlashcardSets
    // Effects: parses flashcard set from JSON object and adds to set of flashcard sets
    private void addSet(SetOfFlashcardSets sofs, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        FlashcardSet fcs = new FlashcardSet(name);
        JSONArray jsonArray = jsonObject.getJSONArray("flashcards");
        for (Object json : jsonArray) {
            JSONObject nextFlashcard = (JSONObject) json;
            addFlashcards(fcs, nextFlashcard);
        }
        sofs.addFlashcardSet(fcs);
    }

    // Modifies: FlashcardSet
    // Effects: parses flashcard from JSON object and adds to flashcard set
    private void addFlashcards(FlashcardSet fcs, JSONObject nextFlashcard) {
        String question = nextFlashcard.getString("question");
        String answer = nextFlashcard.getString("answer");
        Flashcard flashcard = new Flashcard(question, answer);
        fcs.addFlashcard(flashcard);
    }

}
