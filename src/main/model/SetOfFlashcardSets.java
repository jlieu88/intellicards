package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Represents a list of flashcard sets
public class SetOfFlashcardSets implements Writable {
    private ArrayList<FlashcardSet> setOfFlashcardSets;

    public SetOfFlashcardSets() {
        this.setOfFlashcardSets = new ArrayList<>();
    }

    // Requires: flashcard set
    // Modifies: this
    // Effects:  adds a flashcard set from the set of flashcard sets
    public void addFlashcardSet(FlashcardSet x) {
        EventLog.getInstance().logEvent(new Event(x.getName() + " was added to the SetOfFlashcardSets."));
        this.setOfFlashcardSets.add(x);
    }

    // Requires: flashcard set
    // Modifies: this
    // Effects: removes a flashcard set from the set of flashcard sets
    public void rmvFlashcardSet(FlashcardSet x) {
        EventLog.getInstance().logEvent(new Event(x.getName() + " removed from SetOfFlashcardSets."));
        this.setOfFlashcardSets.remove(x);
    }

    public ArrayList<FlashcardSet> getSets() {
        return this.setOfFlashcardSets;
    }

    public int length() {
        return this.setOfFlashcardSets.size();
    }

    // Effects: takes flashcard sets and converts them into json objects.
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("flashcardsets", flashcardSetToJson());
        return json;
    }

    // Effects: writes flashcard sets from a set of flashcard sets to json representation.
    private JSONArray flashcardSetToJson() {
        JSONArray jsonArray = new JSONArray();

        for (FlashcardSet fcs : setOfFlashcardSets) {
            jsonArray.put(fcs.toJson());
        }

        return jsonArray;
    }
}
