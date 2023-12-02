# *PROJECT PHASE 0.*

##### (And showcasing my awesome markdown skills.)

What will my project application *do*?
Who will *use* my application?
Why is this project *interesting* to me?

To answer all of these questions, we must first look at who I am. I'm currently a second year student in the 
faculty of Science at UBC, meaning that I am studying hard (I promise I am mom!). Now as a student, it may 
prove difficult to learn all of the content that I am given from all of my courses. Therefore, I will be writing
this application as a means to *counteract* that.

To address the first point, I am creating an application that will allow a user (or student like myself)
to create flashcards that:
- prompt you to **define** the term written on the front
- answer a question with a **short sentence**
- ask you to answer a **multiple choice prompt** (still undecided)

Basically, make some form of a flashcard program that can seamlessly integrate all of the information from
courses into **intuitive and helpful** flashcards with multiple formats.

Another feature that is currently in the works is a system that provides certain statistics and maybe points out areas
that need attention.

This application will of course be helpful to people who want to learn a certain topic, namely students. But
really, anyone who wants to learn any topic will find it useful to have this tool at their fingertips.

As a student, I'm always trying to find ways to fast-track my learning and make it as efficient as possible. I'm sure
many students, at one point in time during their classes, have looked up "how to study better", in the searchbar on
YouTube. If they haven't, I guess I'm the only one! That aside, I just want to create this application to better my
studies as a student, as I have at least two more years left to study. Yay.

## *User Stories*
#### Phase 0:

As a user, I want to:
- be able to **add (or remove) a flashcard** to/from a set
- add a **question/definition** and an **answer** to the flashcard
- be able to **edit** a flashcard's contents
- be able to mark a flashcard as **correctly** or **incorrectly** answered
- see how much I studied that day

## *Instructions for Grader*

How to generate first of two required actions related to user story "add multiple Xs to Y" (building a deck)
- Select "Build a deck"
- Enter a name for the deck and hit "enter"
- Enter a question and an answer in the fields then click "add"
- You have successfully added an X (Flashcard) to Y (FlashcardSet)
- You may add an arbitrary number of Xs to Ys as well.

How to generate second of the two required actions:
- Select "Flashcards to review"
- You will be prompted to select to view recently correct/incorrect flashcards.
- In this case, we will filter to view incorrect flashcards. 
- By default, all of the flashcards will have "incorrect" status. However, when you "learn" a set,
it will change the correctness (depending on if you got the flashcard correct or not).

Where to find the visual component:
- In the initial loading screen, there will be a loading gif added by myself.
- Also, when you load/save the program state, the popup windows have checkmarks and crosses indicating whether or not
they were loaded successfully
- When you learn a set, depending on if you get the flashcard correct/incorrect, you will see a checkmark or the cross
as well.

How does the user save the state of the application to file?
- You will press the "save progress" button, which will in turn save the program state to a file
- The user will also be prompted to save before exiting, but cannot save from that window directly; they must
select no and then they will go back and hit "save progress".

How does the user load the state of the applicatino from file?
- The user will be prompted upon loading whether or not they want to load the previous save state
- Alternatively, they may use the "load progress" button to load the contents of the save file 

## *Phase 4 Task 2*
### Building a new set

*This is the first related action to Xs and Y, (adding Flashcards (X) to a FlashcardSet(Y)).*

Fri Apr 07 23:57:57 PDT 2023

New Set was added to the SetOfFlashcardSets.

Fri Apr 07 23:57:57 PDT 2023

Flashcard with question: New Question 1 and answer: New Answer 1 was added to set: New Set

Fri Apr 07 23:58:03 PDT 2023

Flashcard with question: New Question 2 and answer: New Answer 2 was added to set: New Set

Fri Apr 07 23:58:09 PDT 2023

Flashcard with question: New Question 3 and answer: New Answer 3 was added to set: New Set

### Begin learning a new set

*All the flashcards are being marked as incorrect by default when you begin learning a new set. This is to clear the
progress from the previous attempt of learning the set so that the recently correct/incorrect flashcards are displayed
properly after the user finishes learning the set.*


Fri Apr 07 23:58:18 PDT 2023

Flashcard with question New Question 1 and New Answer 1 marked as incorrect.

Fri Apr 07 23:58:18 PDT 2023

Flashcard with question New Question 2 and New Answer 2 marked as incorrect.

Fri Apr 07 23:58:18 PDT 2023

Flashcard with question New Question 3 and New Answer 3 marked as incorrect.

### Marking a flashcard as correct

*As the user is going through each flashcard, they have the option to mark the flashcard as correct, and leaves
the flashcard unchanged if incorrect (as flashcards are constructed as incorrect by default). This is also the second
action related to Xs and Y, (modifying the Flashcards' correctness (X) in the respective FlashcardSet (Y)). To add,
the user may view the flashcards they got incorrect/correct recently within the application.*

Fri Apr 07 23:58:26 PDT 2023

Flashcard with question New Question 1 and answer: New Answer 1, marked as correct.

Fri Apr 07 23:58:37 PDT 2023

Flashcard with question New Question 3 and answer: New Answer 3, marked as correct.

## *Phase 4 Task 3*
If I had more time, I would most likely make my **SetOfFlashcardSets** a singleton. This is because there will always
be *ONLY* one instance globally of the SetOfFlashcardSets class, because it's effectively a list of FlashcardSets. 
Of course, you would need to add implementation of the iterator and such. Also,
I would change the Flashcard class into an Abstract or Interface. Ideally, my program would support multiple types of
flashcards (i.e. short answer, term/definition, true/false, long answer, etc.).

As for my user interface, there is definitely a lot of repetitiveness especially when creating JFrames, JInternalFrames,
and just adding a bunch of different windows in general. I would have to spend more time familiarizing myself with how
to prevent duplication using Java Swing. Overall, my program is largely unfinished with many features left unimplemented
due to the constraint of time, and has the potential to be a lot more well rounded and designed.






