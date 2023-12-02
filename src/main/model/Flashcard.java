package model;

// Represents a simple flashcard with a question/definition and an answer
public class Flashcard {
    private String question; // flashcard question
    private String answer; // flashcard answer
    private boolean correctness; // flashcard's correctness

    // Requires: non-empty question, non-empty answer
    // Effects: constructs a flashcard with question and an answer associated with it

    public Flashcard(String question, String answer) {
        this.question = question;
        this.answer = answer;
        this.correctness = false;
    }

    // Modifies: this
    // Effects: changes correctness of the flashcard
    public void setCorrectness(Boolean b) {
        String correctness;
        if (b) {
            correctness = "correct";
        } else {
            correctness = "incorrect";
        }
        EventLog.getInstance().logEvent(new Event("Flashcard with question " + this.question
                + " and " + this.answer + " marked as " + correctness + "."));
        this.correctness = b;
    }

    public String getQuestion() {
        return this.question;
    }

    public String getAnswer() {
        return this.answer;
    }

    public boolean getCorrectness() {
        return this.correctness;
    }

    // Requires: a new question (string) input
    // Modifies: this
    // Effects: modifies the existing question on the flashcard
    public void editQuestion(String newQuestion) {

        EventLog.getInstance().logEvent(new Event("Flashcard with question " + this.question
                + " has been replaced with the new question: " + newQuestion));
        this.question = newQuestion;
    }

    // Requires: a new answer (string) input
    // Modifies: this
    // Effects: modifies the existing answer on the flashcard
    public void editAnswer(String newAnswer) {
        EventLog.getInstance().logEvent(new Event("Flashcard with answer " + this.answer
                + " has been replaced with the new question: " + newAnswer));
        this.answer = newAnswer;
    }

    // Modifies: this
    // Effects: changes the flashcard to "correct" status
    public void correctAnswer() {
        EventLog.getInstance().logEvent(new Event("Flashcard with question " + this.question
                + " and answer: " + this.answer + ", marked as correct."));
        this.correctness = true;
    }

}
