package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FlashcardTest {
    Flashcard fc1;

    @BeforeEach
    public void init() {
        fc1 = new Flashcard("Test", "Test");
    }

    @Test
    public void testEditQuestion() {
        assertEquals(fc1.getQuestion(), "Test");
        fc1.editQuestion("New Question");
        assertEquals(fc1.getQuestion(), "New Question");
    }

    @Test
    public void testEditAnswer() {
        assertEquals(fc1.getAnswer(), "Test");
        fc1.editAnswer("New Answer");
        assertEquals(fc1.getAnswer(), "New Answer");
    }

    @Test
    public void testCorrectAnswer() {
        assertFalse(fc1.getCorrectness());
        fc1.correctAnswer();
        assertTrue(fc1.getCorrectness());
    }

    @Test
    public void testSetCorrectness() {
        fc1.correctAnswer();
        assertTrue(fc1.getCorrectness());
        fc1.setCorrectness(false);
        assertFalse(fc1.getCorrectness());

        fc1.setCorrectness(true);
        assertTrue(fc1.getCorrectness());
    }

}