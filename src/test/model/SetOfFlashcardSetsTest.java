package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SetOfFlashcardSetsTest {
    SetOfFlashcardSets sofs1;
    FlashcardSet fcs1;
    FlashcardSet fcs2;

    @BeforeEach
    public void init() {
        sofs1 = new SetOfFlashcardSets();
        fcs1 = new FlashcardSet("Math");
        fcs2 = new FlashcardSet("Statistics");
    }

    @Test
    public void testAddFlashcardSet() {
        sofs1.addFlashcardSet(fcs1);
        ArrayList<FlashcardSet> testArray = new ArrayList<>();
        testArray.add(fcs1);
        assertEquals(sofs1.getSets(), testArray);
    }

    @Test
    public void testRmvFlashcardSet() {
        ArrayList<FlashcardSet> testArray = new ArrayList<>();
        testArray.add(fcs1);
        sofs1.addFlashcardSet(fcs1);
        assertEquals(sofs1.getSets(), testArray);

        testArray.remove(fcs1);
        sofs1.rmvFlashcardSet(fcs1);
        assertEquals(sofs1.getSets(), testArray);


    }

    @Test
    public void testLength() {
        assertEquals(sofs1.length(), 0);
        sofs1.addFlashcardSet(fcs1);
        sofs1.addFlashcardSet(fcs2);
        assertEquals(sofs1.length(), 2);
    }

}
