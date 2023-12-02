package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FlashcardSetTest {
    FlashcardSet fcs1;
    FlashcardSet fcs2;
    Flashcard fc1;
    Flashcard fc2;
    Flashcard fc3;

    @BeforeEach
    public void init() {
        fcs1 = new FlashcardSet("Math");
        fcs2 = new FlashcardSet("Japanese");
        fc1 = new Flashcard("Hi", "Hello");
        fc2 = new Flashcard("1", "2");
        fc3 = new Flashcard("3", "4");
    }

    @Test
    public void testAddFlashcard() {
        ArrayList<Flashcard> testArray;
        testArray = new ArrayList<>();
        testArray.add(fc1);

        fcs1.addFlashcard(fc1);
        assertEquals(fcs1.getFlashcards(), testArray);

        testArray.add(fc2);
        fcs1.addFlashcard(fc2);
        assertEquals(fcs1.getFlashcards(), testArray);
    }

    @Test
    public void testEditFlashcardQuestion() {
        fcs1.addFlashcard(fc1);
        fcs1.editFlashcardQuestion(0, "New Question");
        assertEquals(fcs1.getFlashcards().get(0).getQuestion(), "New Question");
        fcs1.addFlashcard(fc2);
        fcs1.editFlashcardQuestion(1, "Newer Question");
        assertEquals(fcs1.getFlashcards().get(1).getQuestion(), "Newer Question");
        fcs1.editFlashcardQuestion(0, "");
        assertEquals(fcs1.getFlashcards().get(0).getQuestion(), "You forgot to add a question!");
    }

    @Test
    public void testEditFlashcardAnswer() {
        fcs1.addFlashcard(fc1);
        fcs1.editFlashcardAnswer(0, "New Answer");
        assertEquals(fcs1.getFlashcards().get(0).getAnswer(), "New Answer");
        fcs1.addFlashcard(fc2);
        fcs1.editFlashcardAnswer(1, "Newer Answer");
        assertEquals(fcs1.getFlashcards().get(1).getAnswer(), "Newer Answer");
        fcs1.editFlashcardAnswer(0, "");
        assertEquals(fcs1.getFlashcards().get(0).getAnswer(), "You forgot to add an answer!");
    }

    @Test
    public void testEditFlashcardSetName() {
        assertEquals(fcs1.getName(), "Math");

        fcs1.editFlashcardSetName("Statistics");
        assertEquals(fcs1.getName(), "Statistics");
    }

    @Test
    public void testRemoveFlashcard() {
        ArrayList<Flashcard> testArray;
        testArray = new ArrayList<>();
        testArray.add(fc1);

        fcs1.addFlashcard(fc1);
        assertEquals(fcs1.getFlashcards(), testArray);
        fcs1.removeFlashcard(fc1);
        testArray.remove(fc1);
        assertEquals(fcs1.getFlashcards(), testArray);
    }

    @Test
    public void testLength() {
        assertEquals(fcs1.length(), 0);
        fcs1.addFlashcard(fc1);
        fcs1.addFlashcard(fc2);
        assertEquals(fcs1.length(), 2);
    }

    @Test
    public void testSelectFlashcard() {
        fcs1.addFlashcard(fc1);
        fcs1.addFlashcard(fc2);
        fcs1.addFlashcard(fc3);

        assertEquals(fcs1.selectFlashcard(1), fc2);
    }

}
