package ui;

import model.Flashcard;
import model.FlashcardSet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Represents the UI for learning a flashcard set.
class LearnSetUI extends JInternalFrame implements ActionListener {
    private FlashcardSet localFcs;
    private JLabel currentQuestion;
    private int currentQuestionIndex = 0;
    private JTextField answerBox;
    private JButton button;
    private ImageIcon checkIcon = new ImageIcon(
            new ImageIcon("./images/check.png").getImage().getScaledInstance(20,
                    20, Image.SCALE_DEFAULT));
    private ImageIcon crossIcon = new ImageIcon(
            new ImageIcon("./images/cross.png").getImage().getScaledInstance(20,
                    20, Image.SCALE_DEFAULT));

    // Modifies: this
    // Effects: constructs a window for user to learn a flashcard set
    public LearnSetUI(FlashcardSet fcs, Component parent) {
        super("Learning a set", false, true, false, false);
        setPreferredSize(new Dimension(600, 400));
        localFcs = fcs;
        setLayout(new FlowLayout());
        pack();

        for (Flashcard fc: localFcs.getFlashcards()) {
            fc.setCorrectness(false);
        }

        currentQuestion = new JLabel("Question: "
                + localFcs.getFlashcards().get(currentQuestionIndex).getQuestion());
        add(currentQuestion);

        answerBox = new JTextField(10);
        add(answerBox);

        button = new JButton("Continue");
        add(button);

        button.setActionCommand("continue");
        button.addActionListener(this);

        setVisible(true);

    }

    // Modifies: this
    // Effects: gives the user the next flashcard to learn until the set is empty (completed studying)
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("continue")) {
            answerChecker();

            currentQuestionIndex += 1;
            if (currentQuestionIndex >= localFcs.getFlashcards().size()) {
                JOptionPane.showConfirmDialog(null, "You have completed studying this set.",
                        "Complete Notification", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
            }
            answerBox.setText("");
            currentQuestion.setText("Question: "
                    + localFcs.getFlashcards().get(currentQuestionIndex).getQuestion());
            repaint();
        }
    }

    // Modifies: Flashcard
    // Effects: Helper to prompt the user to check if their answer was correct or not
    public void answerChecker() {
        Flashcard currentFlashcard = localFcs.getFlashcards().get(currentQuestionIndex);
        int response = JOptionPane.showConfirmDialog(null, "Here's the answer: "
                        + currentFlashcard.getAnswer() + ". Did you get it right?",
                "Check your answer", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            JOptionPane.showConfirmDialog(null, "Good job!",
                    "Correct answer!", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                    checkIcon);
            currentFlashcard.correctAnswer();
        } else {
            JOptionPane.showConfirmDialog(null, "Please review this question again.",
                    "Incorrect Answer", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                    crossIcon);
        }

    }
}
