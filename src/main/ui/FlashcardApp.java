package ui;

import model.Flashcard;
import model.FlashcardSet;
import model.SetOfFlashcardSets;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

// Flashcard Application
public class FlashcardApp {
    private static final String JSON_STORE = "./data/flashcarddata.json";
    private SetOfFlashcardSets sofs;
    private Scanner input;
    private int amountStudied;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;


    // Effects: Runs the flashcard application
    public FlashcardApp() {
        runFlashcardApp();
    }

    // Modifies: this
    // Effects: processes user input
    private void runFlashcardApp() {
        boolean keepGoing = true;
        String command;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("You studied " + amountStudied + " flashcards this session.");
    }

    // Modifies: this
    // Effects: does something according to user input
    private void processCommand(String command) {
        if (command.equals("d")) {
            doDisplaySet();
        } else if (command.equals("e")) {
            doEditSet();
        } else if (command.equals("l")) {
            doLearnSet();
        } else if (command.equals("b")) {
            doBuildSet();
        } else if (command.equals("s")) {
            saveState();
        } else if (command.equals("r")) {
            loadSaveState();
        } else {
            System.out.println("Sorry, this isn't a valid input.");
        }
    }

    // Modifies: this
    // Effects: initializes flashcards
    private void init() {
        sofs = new SetOfFlashcardSets();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // Effects: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nPlease select an action below:");
        System.out.println("\n(B) - Build a new flashcard set");
        System.out.println("\n(D) - Display current flashcard sets");
        System.out.println("\n(E) - Edit an existing flashcard set");
        System.out.println("\n(L) - Begin learning a flashcard set");
        System.out.println("\n(S) - Save current process to file");
        System.out.println("\n(R) - Reload a previous save state to the program");
        System.out.println("\n(Q) - Quit the app");
    }

    // Effects: returns the current flashcard sets that exists and index
    private void doDisplaySet() {
        int i = 1;
        System.out.println("\nCurrent existing sets: ");
        for (FlashcardSet fcs : sofs.getSets()) {
            System.out.println("\n" + "(" + i + ")" + " - " + fcs.getName());
            i++;
        }
        System.out.println("\nEnter any key to continue.");
        input.next();
    }

    // Modifies: FlashcardSet, Flashcard
    // Effects: allows user to edit a set of flashcards' name or a specific flashcard.
    private void doEditSet() {
        boolean continueEditing = true;
        String editCommand;
        FlashcardSet selectedSet;
        System.out.print("\nWhich flashcard set would you like to edit? Select according to the list's number");
        doDisplaySet();
        selectedSet = selectFlashcardSet(Integer.parseInt(input.next()));
        while (continueEditing) {
            System.out.print("\nEnter 'n' to edit the set name, or 'f' to edit a flashcard or 'q' to quit to menu");
            editCommand = input.next();
            if (editCommand.equals("n")) {
                editFlashcardSetName(selectedSet);
            } else if (editCommand.equals("f")) {
                flashcardEditMaster(selectedSet);
            } else if (editCommand.equals("q")) {
                continueEditing = false;
            }
        }
    }


    // Requires: valid flashcard (with question and answer)
    // Modifies: this
    // Effects: edits the flashcard's question
    private void editFlashcardQuestion(Flashcard selectedFlashcard) {
        System.out.println("\nEnter a new question for this flashcard: ");
        String oldQuestion = selectedFlashcard.getQuestion();
        selectedFlashcard.editQuestion(input.next());
        System.out.println("\nChanged flashcard question from: " + oldQuestion
                + " to " + selectedFlashcard.getQuestion());
    }

    // Requires: valid flashcard (with question and answer)
    // Modifies: this
    // Effects: edits the flashcard's answer
    private void editFlashcardAnswer(Flashcard selectedFlashcard) {
        System.out.println("\nEnter a new answer for this flashcard: ");
        String oldAnswer = selectedFlashcard.getAnswer();
        selectedFlashcard.editAnswer(input.next());
        System.out.println("\nChanged flashcard answer from: " + oldAnswer
                + " to " + selectedFlashcard.getAnswer());
    }

    // Modifies: FlashcardSet
    // Effects: edits the flashcard set's name
    private void editFlashcardSetName(FlashcardSet selectedSet) {
        System.out.println("\nPlease enter a new name for the set");
        selectedSet.editFlashcardSetName(input.next());
        System.out.println("\nSet name successfully changed to: " + selectedSet.getName());
    }

    // Effects: selects a flashcard for editing
    private String selectFlashcardToEdit(FlashcardSet selectedSet) {
        System.out.println("\nYou are now in flashcard editing mode.");
        int i = 1;
        for (Flashcard fc : selectedSet.getFlashcards()) {
            System.out.println("\n" + "(" + i + ")" + " Question: "
                    + fc.getQuestion() + " Answer: " + fc.getAnswer());
            i++;
        }
        System.out.println("\nSelect flashcard according to it's number (e.g. '1') or 'c' to cancel");
        return input.next();
    }

    // Requires: flashcard set with a flashcard in it
    // Modifies: this
    // Effects: allows user to edit a flashcard from a set
    private void flashcardEditMaster(FlashcardSet selectedSet) {
        Flashcard selectedFlashcard;
        selectedFlashcard = selectedSet.selectFlashcard(Integer.parseInt(selectFlashcardToEdit(selectedSet)) - 1);
        System.out.println("\nYou have selected the flashcard with question: "
                + selectedFlashcard.getQuestion() + " and answer: " + selectedFlashcard.getAnswer());
        System.out.println("\nNow select an action: 'q' to edit question, 'a' to edit the answer, 'x' to "
                + "delete the flashcard.");
        String editCommandFlashcard = input.next();
        if (editCommandFlashcard.equals("q")) {
            editFlashcardQuestion(selectedFlashcard);
        } else if (editCommandFlashcard.equals("a")) {
            editFlashcardAnswer(selectedFlashcard);
        } else if (editCommandFlashcard.equals("x")) {
            System.out.println("Successfully removed the selected flashcard.");
            selectedSet.removeFlashcard(selectedFlashcard);
        }
    }

    // Modifies: FlashcardSet
    // Effects: allows user to learn a set
    private void doLearnSet() {
        boolean continueStudying = true;
        FlashcardSet selectedSet;
        System.out.println("\nPlease select a number to select a set to practice: ");
        doDisplaySet();
        selectedSet = selectFlashcardSet(Integer.parseInt(input.next()));
        System.out.println("\nYou've selected the set: " + selectedSet.getName());

        while (continueStudying) {
            this.amountStudied += learnFlashcard(selectedSet);
            System.out.println("\nEnter 'q' to quit studying, or any other key to continue studying.");
            if (input.next().equals("q")) {
                continueStudying = false;
            }
        }
    }

    // Effects: allows for user to learn a flashcard set of their choosing
    public int learnFlashcard(FlashcardSet selectedSet) {
        Random rand = new Random();
        for (int i = 0; i < selectedSet.length(); i++) {
            int random = rand.nextInt(selectedSet.length());
            Flashcard flashcard = selectedSet.selectFlashcard(random);

            System.out.println("\nQuestion: " + flashcard.getQuestion());
            System.out.println("\nPlease answer the question: ");
            String answer = input.next();

            System.out.println("\nYou answered: " + answer + "\nActual answer: " + flashcard.getAnswer());
            System.out.println("\nCorrect? (y/n)");
            if (input.next().equals("y")) {
                flashcard.correctAnswer();
                System.out.println("\nGood job!");
            } else {
                System.out.println("\nAnswer: " + flashcard.getAnswer() + "\nWe'll revisit this flashcard later.");
            }
            amountStudied++;
        }
        return amountStudied;
    }

    // Effects: returns a FlashcardSet based on the user's input
    private FlashcardSet selectFlashcardSet(int selection) {
        return sofs.getSets().get(selection - 1);
    }

    // Modifies: SetOfFlashcardSets, FlashcardSet
    // Effects: builds a flashcard set
    private void doBuildSet() {
        FlashcardSet tempFlashcardSet;
        boolean continueBuilding = true;
        String command;
        String question;
        String answer;
        System.out.println("Enter a name for your new Flashcard Set: ");
        tempFlashcardSet = new FlashcardSet(input.next());
        while (continueBuilding) {
            System.out.println("\nEnter 'q' to cancel at any time\nEnter a question for your flashcard:");
            question = input.next();
            System.out.println("\nEnter an answer for your flashcard: ");
            answer = input.next();
            tempFlashcardSet.addFlashcard(new Flashcard(question, answer));
            System.out.println("\nFlashcard with the question: " + question + " and answer " + answer + " created!");
            System.out.println("\nEnter 'q' to quit, or enter any key to continue!");
            command = input.next();
            if (command.equals("q")) {
                continueBuilding = false;
            }
        }
        System.out.println("New flashcard set " + tempFlashcardSet.getName() + " successfully created!");
        sofs.addFlashcardSet(tempFlashcardSet);
    }

    // Effects: saves current state of the application to a file
    private void saveState() {
        try {
            jsonWriter.open();
            jsonWriter.write(sofs);
            jsonWriter.close();
            System.out.println("Saved the current state to " + JSON_STORE);
        } catch (FileNotFoundException error) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // Modifies: this
    // Effects: loads previous save states of the application from a file
    private void loadSaveState() {
        try {
            sofs = jsonReader.read();
            System.out.println("Successfully loaded previous save state from: "  + JSON_STORE);
        } catch (IOException error) {
            System.out.println("Unable to read data from: " + JSON_STORE);
        }
    }
}

