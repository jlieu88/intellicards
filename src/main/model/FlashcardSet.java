package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Flashcard set representing a list of flashcards (presumably to one subject).
public class FlashcardSet implements Writable {
    private String name; // name of the flashcard set
    private ArrayList<Flashcard> flashcards; // flashcards in the set

    // Requires: name of a flashcard set
    // Effects: name of the flashcard set is set, initializes a new
    // list of flashcards that can allow flashcards to be appended/deleted.
    public FlashcardSet(String name) {
        this.name = name;
        this.flashcards = new ArrayList<>();
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<Flashcard> getFlashcards() {
        return this.flashcards;
    }

    public Flashcard selectFlashcard(int i) {
        return flashcards.get(i);
    }

    public Integer length() {
        return flashcards.size();
    }

    // Requires: index i >= 0, String newQuestion to be non-empty
    // Modifies: flashcard
    // Effects: modifies the flashcard at index i with the new question as newQuestion
    public void editFlashcardQuestion(int i, String newQuestion) {
        Flashcard currentEdit = flashcards.get(i);
        if (newQuestion.equals("")) {
            currentEdit.editQuestion("You forgot to add a question!");
        } else {
            currentEdit.editQuestion(newQuestion);
        }
    }

    // Requires: index i >= 0, String newAnswer to be non-empty
    // Modifies: flashcard
    // Effects: modifies the flashcard at index i with the new answer as newAnswer
    public void editFlashcardAnswer(int i, String newAnswer) {
        Flashcard currentEdit = flashcards.get(i);
        if (newAnswer.equals("")) {
            currentEdit.editAnswer("You forgot to add an answer!");
        } else {
            currentEdit.editAnswer(newAnswer);
        }
    }

    // Requires: non-empty string newName
    // Modifies: this
    // Effects: changes flashcard set name to given new name
    public void editFlashcardSetName(String newName) {
        EventLog.getInstance().logEvent(new Event("Set with name " + this.getName() + " has been changed to "
                + newName));
        this.name = newName;
    }
    // Add function to add a flashcard to the flashcard set

    // Requires: valid flashcard
    // Modifies: this
    // Effects: adds a flashcard to the set
    public void addFlashcard(Flashcard flashcard) {
        EventLog.getInstance().logEvent(new Event("Flashcard with question: " + flashcard.getQuestion()
                + " and answer: " + flashcard.getAnswer() + " was added to set: " + this.getName()));
        this.flashcards.add(flashcard);
    }

    // Requires: valid flashcard
    // Modifies: this
    // Effects: removes a flashcard from the set
    public void removeFlashcard(Flashcard flashcard) {
        EventLog.getInstance().logEvent(new Event("Flashcard with question: " + flashcard.getQuestion()
                + " and answer: " + flashcard.getAnswer() + " was removed from this set: " + this.getName()));
        this.flashcards.remove(flashcard);
    }

    // Effects: writes flashcard set data (name, flashcards) to json representation
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("flashcards", flashcards);
        return json;
    }
}