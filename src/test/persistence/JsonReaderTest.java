package persistence;

import model.FlashcardSet;
import model.SetOfFlashcardSets;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/invalidFile.json");
        try {
            SetOfFlashcardSets sofs = reader.read();
            fail("IOException expected");
        } catch (IOException error) {
            // code shouldn't run to here
        }
    }

    @Test
    void testReaderEmptySetOfFlashcardSets() {
        JsonReader reader = new JsonReader("./data/testReaderEmptySetOfFlashcardSets.json");
        try {
            SetOfFlashcardSets sofs = reader.read();
            assertEquals(0, sofs.length());
        } catch (IOException error) {
            fail("Failed to read from file.");
        }
    }

    @Test
    void testReaderSetOfFlashcardSets() {
        JsonReader reader = new JsonReader("./data/testReaderSetOfFlashcardSets.json");
        try {
            SetOfFlashcardSets sofs = reader.read();
            assertEquals(2, sofs.length());
            List<FlashcardSet> sets = sofs.getSets();
            assertEquals("Mathematics", sets.get(0).getName());
            assertEquals("Statistics", sets.get(1).getName());
            checkFlashcard("Question1", "Answer1", sets.get(0).selectFlashcard(0));
            checkFlashcard("Question3", "Answer3", sets.get(1).selectFlashcard(2));
        } catch (IOException error) {
            fail("Couldn't read from file");
        }
    }

}
