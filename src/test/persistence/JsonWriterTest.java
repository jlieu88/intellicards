package persistence;

import model.Flashcard;
import model.FlashcardSet;
import model.SetOfFlashcardSets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {
    SetOfFlashcardSets sofs;
    SetOfFlashcardSets sofsEmpty;
    FlashcardSet set1;
    FlashcardSet set2;
    Flashcard fc1;
    Flashcard fc2;
    Flashcard fc3;

    @BeforeEach
    void init() {
        sofsEmpty = new SetOfFlashcardSets();
        sofs = new SetOfFlashcardSets();
        set1 = new FlashcardSet("Mathematics");
        set2 = new FlashcardSet("Statistics");
        fc1 = new Flashcard("Question1", "Answer1");
        fc2 = new Flashcard("Question2", "Answer2");
        fc3 = new Flashcard("Question3", "Answer3");

        set1.addFlashcard(fc1);
        set1.addFlashcard(fc2);
        set1.addFlashcard(fc3);

        set2.addFlashcard(fc1);
        set2.addFlashcard(fc2);
        set2.addFlashcard(fc3);

        sofs.addFlashcardSet(set1);
        sofs.addFlashcardSet(set2);

    }

    @Test
    void testInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:filename.json");
            writer.open();
            fail("IOException was expected.");
        } catch (FileNotFoundException error) {
            // test is expected to fail with IOException.
        }
    }

    @Test
    void testWriterEmptySetOfFlashcardSets() {
        try {
            String path = "./data/testWriterEmptySetOfFlashcardSets.json";
            JsonWriter writer = new JsonWriter(path);
            writer.open();
            writer.write(sofsEmpty);
            writer.close();

            JsonReader reader = new JsonReader(path);
            sofsEmpty = reader.read();
            assertEquals(0, sofsEmpty.length());
        } catch (IOException error) {
            fail("No exception expected.");
        }
    }

    @Test
    void testWriterSetOfFlashcardSets() {
        try {
            String path = "./data/testWriterSetOfFlashcardSets.json";
            JsonWriter writer = new JsonWriter(path);
            writer.open();
            writer.write(sofs);
            writer.close();

            JsonReader reader = new JsonReader(path);
            sofs = reader.read();
            List<FlashcardSet> flashcardSets = sofs.getSets();
            assertEquals(flashcardSets.size(), 2);
            List<Flashcard> flashcardsSet1 = flashcardSets.get(0).getFlashcards();
            List<Flashcard> flashcardsSet2 = flashcardSets.get(1).getFlashcards();
            assertEquals(flashcardsSet1.size(), 3);
            assertEquals(flashcardsSet2.size(), 3);
            checkFlashcard("Question1", "Answer1", flashcardsSet1.get(0));
            checkFlashcard("Question3", "Answer3", flashcardsSet1.get(2));
            checkFlashcard("Question3", "Answer3", flashcardsSet2.get(2));
        } catch (IOException error) {
            fail("Exception not expected");
        }
    }
}
