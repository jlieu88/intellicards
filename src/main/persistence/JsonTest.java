package persistence;

import model.Flashcard;

import static org.junit.jupiter.api.Assertions.assertEquals;


// Effects: tests a flashcard's contents to ensure integrity after reading/writing to file.
public class JsonTest {
    protected void checkFlashcard(String question, String answer, Flashcard fc) {
        assertEquals(question, fc.getQuestion());
        assertEquals(answer, fc.getAnswer());
    }
}
