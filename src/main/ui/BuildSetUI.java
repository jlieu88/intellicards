package ui;

import model.Flashcard;
import model.FlashcardSet;
import model.SetOfFlashcardSets;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Represents user interface for building a set.
 */
class BuildSetUI extends JInternalFrame implements ActionListener {
    private JTextField question;
    private JTextField answer;
    private JTextField setName;
    private JTextField confirmAdd;
    private JButton setNameButton;
    private JButton close;
    private JLabel enterName;
    private FlashcardSet fcs;
    private SetOfFlashcardSets localSofs;

    // Modifies: this
    // Effects: constructs a window to help build a set (adding flashcards with a question and answer to a set)
    public BuildSetUI(SetOfFlashcardSets sofs, Component parent) {
        super("Build a set", false, false, false, false);
        setPreferredSize(new Dimension(600, 350));
        this.setResizable(true);
        localSofs = sofs;
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        setLayout(new FlowLayout());
        pack();
        enterName = new JLabel("Please enter a set name:");
        add(enterName);
        setNameButton = new JButton("Enter");
        setName = new JTextField(10);
        setNameButton.setActionCommand("setName");
        setNameButton.addActionListener(this);
        add(setName);
        add(setNameButton);
        setPosition(parent);
        setVisible(true);
    }

    // Effects: sets the position of the window on the desktop.
    private void setPosition(Component parent) {
        setLocation(150, 20);
    }

    // Modifies: this
    // Effects: Helper to set the name of a set and change the window to adding flashcards.
    private void setNameHelper() {
        pack();
        fcs = new FlashcardSet(setName.getText());
        localSofs.addFlashcardSet(fcs);
        question = new JTextField(9);
        answer = new JTextField(9);
        close = new JButton("Quit");

        JButton addButton = new JButton("Add");
        addButton.setActionCommand("add");
        addButton.addActionListener(this);
        close.setActionCommand("close");
        close.addActionListener(this);
        add(new JLabel("Question"));
        add(question);
        add(new JLabel("Answer"));
        add(answer);
        add(addButton);
        add(close);
        setName.setVisible(false);
        setNameButton.setVisible(false);
        enterName.setVisible(false);
        repaint();
    }

    // Modifies: this
    // Effects: Helper to add flashcards to a set.
    private void addHelper() {
        confirmAdd = new JTextField("Successfully added flashcard with " + question.getText()
                + " as the question and " + answer.getText() + " as the answer!");
        fcs.addFlashcard(new Flashcard(question.getText(), answer.getText()));
        confirmAdd.setEditable(false);
        confirmAdd.setAlignmentX(CENTER_ALIGNMENT);
        question.setText("");
        answer.setText("");
        add(confirmAdd);
        confirmAdd.setVisible(true);
        pack();
    }

    // Effects: handles the actions when user interacts with the GUI.
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("setName")) {
            setNameHelper();
        }
        if (e.getActionCommand().equals("add")) {
            addHelper();
        }
        if (e.getActionCommand().equals("close")) {
            this.dispose();
        }


    }
}

