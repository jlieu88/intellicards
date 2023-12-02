package ui;

import model.Event;
import model.EventLog;
import model.Flashcard;
import model.FlashcardSet;
import model.SetOfFlashcardSets;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

// Represents the main workspace for the Application.
public class FlashcardAppUI extends JFrame implements ActionListener {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final String JSON_STORE = "./data/flashcarddata.json";
    private SetOfFlashcardSets sofs = new SetOfFlashcardSets();
    private JDesktopPane desktop;
    private JInternalFrame controlPanel;
    private JsonWriter jsonWriter = new JsonWriter(JSON_STORE);
    private JsonReader jsonReader = new JsonReader(JSON_STORE);
    private ImageIcon checkIcon = new ImageIcon(
            new ImageIcon("./images/check.png").getImage().getScaledInstance(20,
                    20, Image.SCALE_DEFAULT));
    private ImageIcon crossIcon = new ImageIcon(
            new ImageIcon("./images/cross.png").getImage().getScaledInstance(20,
                    20, Image.SCALE_DEFAULT));
    private JButton buildButton = new JButton("Build new set");
    private JButton learnButton = new JButton("Learn set");
    private JButton displayButton = new JButton("Display sets");
    private JButton displayIncorrectFlashcardsButton = new JButton("Flashcards to review");
    private JButton saveButton = new JButton("Save progress");
    private JButton loadButton = new JButton("Load progress");
    private JButton quitButton = new JButton("Quit");
    private ArrayList<String> setNames = new ArrayList<>();
    private JComboBox<String> comboBox;
    private FlashcardSet selectedSet;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private double width = screenSize.getWidth();
    private double height = screenSize.getHeight();
    private JComboBox optionBox;
    private JInternalFrame displayIncorrectPanel;
    private JPanel buttonPanel;

    // Modifies: this
    // Effects: constructs a flashcard application window interface for user to interact with
    public FlashcardAppUI() {
        displaySplashScreen();
        desktop = new JDesktopPane(); // Create a new framed window

        desktop.addMouseListener(new FlashcardAppUI.DesktopFocusAction()); // focus on the window pane
        controlPanel = new JInternalFrame("Control Panel", false,
                false, false, false); // another window within the frame
        controlPanel.setLayout(new BorderLayout()); // the layout of the new window

        setContentPane(desktop);
        setTitle("Flashy App");
        setSize(WIDTH, HEIGHT);

        addButtonPanel();

        controlPanel.pack();
        controlPanel.setVisible(true);
        desktop.add(controlPanel);

        initialLoadHelper();
        setLocation((int) width / 2 - 400, (int) height / 2 - 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // close program when window is closed
        setVisible(true); // making the window visible
    }

    // Effects: Prompts user whether or not they want to load previous save data.
    private void initialLoadHelper() {
        int response = JOptionPane.showConfirmDialog(null, "Would you like to load your"
                        + " previous save?",
                "Load Notification", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            loadHelper();
        }
    }

    // Modifies: this
    // Effects: Helper to display initial loading splash screen.
    private void displaySplashScreen() {
        JWindow window = new JWindow();
        window.getContentPane().add(
                new JLabel("", new ImageIcon("./images/splash.gif"), SwingConstants.CENTER));
        window.setBounds((int) (width / 2) - 300, (int) (height / 2) - 175, 600, 350);
        window.getContentPane().setBackground(Color.black);
        window.setVisible(true);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        window.setVisible(false);
        window.dispose();
    }

    // Effects: Helper to make window focus when clicked on.
    private class DesktopFocusAction extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            FlashcardAppUI.this.requestFocusInWindow();
        }
    }

    public SetOfFlashcardSets getSofs() {
        return this.sofs;
    }

    // Modifies: this
    // Effects: adds the necessary buttons to control panel
    private void addButtonPanel() {
        buttonPanel = new JPanel();

        // Building actual buttons

        buildButton.setActionCommand("build");
        buildButton.addActionListener(this);

        learnButton.setActionCommand("learn");
        learnButton.addActionListener(this);

        displayButton.setActionCommand("display");
        displayButton.addActionListener(this);

        displayIncorrectFlashcardsButton.setActionCommand("incorrect");
        displayIncorrectFlashcardsButton.addActionListener(this);

        saveButton.setActionCommand("save");
        saveButton.addActionListener(this);

        loadButton.setActionCommand("load");
        loadButton.addActionListener(this);

        quitButton.setActionCommand("quit");
        quitButton.addActionListener(this);


        // Making listeners for each button

        buttonPanel.setLayout(new GridLayout(7, 1));
        buttonAdder();
        controlPanel.add(buttonPanel, BorderLayout.WEST);
    }


    // Effects: adds button to the buttonPanel which is then added to the controlPanel window
    public void buttonAdder() {
        buttonPanel.add(buildButton);
        buttonPanel.add(learnButton);
        buttonPanel.add(displayButton);
        buttonPanel.add(displayIncorrectFlashcardsButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(quitButton);
    }

    // Modifies: this
    // Effects: Helper to select a set (to learn)
    public void selectSet() {
        JInternalFrame selectPanel = new JInternalFrame("Set Selection", false, true, false);
        for (FlashcardSet fcs : getSofs().getSets()) {
            if (!setNames.contains(fcs.getName())) {
                setNames.add(fcs.getName());
            }
        }

        comboBox = new JComboBox<>(setNames.toArray(new String[0]));
        JButton confirm = new JButton("Confirm");
        confirm.setActionCommand("confirm");
        confirm.addActionListener(this);
        selectPanel.setLayout(new FlowLayout());
        selectPanel.add(confirm);
        selectPanel.add(comboBox);
        selectPanel.pack();
        selectPanel.setVisible(true);
        desktop.add(selectPanel);
    }

    // Modifies: this
    // Effects: prompts the user to view recently correct/incorrect flashcards
    public void selectTrueFalse() {
        JInternalFrame selectTrueFalsePanel = new JInternalFrame("Correct vs. incorrect", false, true, false);

        JLabel prompt = new JLabel("Select the flashcards you would like to view (recently correct/incorrect): ");

        ArrayList<String> options = new ArrayList<>();
        options.add("Correct");
        options.add("Incorrect");

        optionBox = new JComboBox<>(options.toArray(new String[0]));
        JButton selectOption = new JButton("Confirm");
        selectOption.setActionCommand("selectOption");
        selectOption.addActionListener(this);
        selectTrueFalsePanel.setLayout(new FlowLayout());
        selectTrueFalsePanel.add(prompt);
        selectTrueFalsePanel.add(optionBox);
        selectTrueFalsePanel.add(selectOption);
        selectTrueFalsePanel.pack();
        selectTrueFalsePanel.setVisible(true);
        desktop.add(selectTrueFalsePanel);
    }

    // Effects: displays the flashcards that are recently marked incorrect/correct (specified by user)
    public void displayIncorrectHelper(Boolean b) {
        displayIncorrectPanel = new JInternalFrame("Current Sets", true, true, false);
        displayIncorrectPanel.getContentPane().setLayout(
                new BoxLayout(displayIncorrectPanel.getContentPane(), BoxLayout.PAGE_AXIS));
        displayIncorrectPanel.setPreferredSize(new Dimension(300, 150));
        assembleFlashcards(b);
        displayIncorrectPanel.pack();
        displayIncorrectPanel.setVisible(true);
        displayIncorrectPanel.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        desktop.add(displayIncorrectPanel);
    }

    // Effects: Assembles the flashcard information that are incorrect/correct (specified by user)
    public void assembleFlashcards(Boolean b) {
        for (FlashcardSet fcs : sofs.getSets()) {
            displayIncorrectPanel.add(new JLabel("\nSet name: " + fcs.getName()));
            for (Flashcard fc : fcs.getFlashcards()) {
                if (b) {
                    if (fc.getCorrectness()) {
                        displayIncorrectPanel.add(new JLabel("\n Correct: Question: " + fc.getQuestion()
                                + " Answer: " + fc.getAnswer()));
                    }
                } else {
                    if (!fc.getCorrectness()) {
                        displayIncorrectPanel.add(new JLabel("\n Incorrect: Question: " + fc.getQuestion()
                                + " Answer: " + fc.getAnswer()));
                    }
                }
            }
        }
    }

    // Effects: Helper to assemble a simple display of all existing sets.
    public void displayHelper() {
        JInternalFrame displayPanel = new JInternalFrame("Current Sets", true,
                true, false);
        displayPanel.getContentPane().setLayout(new BoxLayout(displayPanel.getContentPane(), BoxLayout.PAGE_AXIS));
        displayPanel.setPreferredSize(new Dimension(300, 150));
        for (FlashcardSet fcs : sofs.getSets()) {
            displayPanel.add(new JLabel("\nSet name: " + fcs.getName()));
            for (Flashcard fc : fcs.getFlashcards()) {
                displayPanel.add(new JLabel("\n   Question: " + fc.getQuestion()
                        + " Answer: " + fc.getAnswer()));
            }
        }

        displayPanel.pack();
        displayPanel.setVisible(true);
        displayPanel.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        desktop.add(displayPanel);
    }

    // Effects: saves the file to specified location
    public void saveHelper() {
        try {
            jsonWriter.open();
            jsonWriter.write(sofs);
            jsonWriter.close();
            JOptionPane.showConfirmDialog(null, "Successfully saved file to " + JSON_STORE,
                    "Save Notification", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                    checkIcon);
        } catch (FileNotFoundException error) {
            System.out.println("Unable to write to file: " + JSON_STORE);
            JOptionPane.showConfirmDialog(null, "Could not save file to " + JSON_STORE,
                    "Save Notification", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                    crossIcon);
        } finally {
            repaint();
        }
    }

    // Effects: loads flashcard data from specified location
    public void loadHelper() {
        try {
            sofs = jsonReader.read();
            JOptionPane.showConfirmDialog(null, "Successfully loaded file from " + JSON_STORE,
                    "Load Notification", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                    checkIcon);
        } catch (IOException error) {
            JOptionPane.showConfirmDialog(null, "Couldn't load file from " + JSON_STORE,
                    "Load Notification", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                    crossIcon);
        }
    }

    // Effects: Helper to quit the program, also prompts user if they want to save first.
    public void quitHelper() {
        int response = JOptionPane.showConfirmDialog(null, "Have you already saved? (Ignore if you don't "
                        + "want to save).",
                "Save Notification", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            for (Event e : EventLog.getInstance()) {
                System.out.println(e.toString());
            }
            System.exit(0);
        }
    }

    // Effects: helper to find the contents of JComboBox comboBox and add a learning UI with the selected set
    public void confirmSelectionHelper() {
        for (FlashcardSet fcs : sofs.getSets()) {
            if (fcs.getName().equals(comboBox.getSelectedItem().toString())) {
                selectedSet = fcs;
            }
        }
        desktop.add(new LearnSetUI(selectedSet, FlashcardAppUI.this));
    }

    // Effects: Handles all the actions performed when user interacts with the GUI.
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("build")) {
            desktop.add(new BuildSetUI(getSofs(), FlashcardAppUI.this));
        } else if (e.getActionCommand().equals("learn")) {
            selectSet();
        } else if (e.getActionCommand().equals("display")) {
            displayHelper();
        } else if (e.getActionCommand().equals("incorrect")) {
            selectTrueFalse();
        } else if (e.getActionCommand().equals("selectOption")) {
            if (optionBox.getSelectedItem().toString().equals("Correct")) {
                displayIncorrectHelper(true);
            } else {
                displayIncorrectHelper(false);
            }
        } else if (e.getActionCommand().equals("save")) {
            saveHelper();
        } else if (e.getActionCommand().equals("load")) {
            loadHelper();
        } else if (e.getActionCommand().equals("quit")) {
            quitHelper();
        } else if (e.getActionCommand().equals("confirm")) {
            confirmSelectionHelper();
        }
    }
}
