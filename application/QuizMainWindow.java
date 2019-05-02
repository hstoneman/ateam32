package application;

import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 * Main window for the quiz
 * @author Anand K Madathil
 *
 */
public class QuizMainWindow {
    
    // FX node references used for changing questions without creating new objects
    static Label questionLabel;
    static VBox questionButtonBox;
    static Button nextQuestion;
    static ImageView view;
    static ArrayList<Question> questions;
    static Label quizNo;
    static Label correctDisplay;
    
    // used to store number of correct answers and current question
    static int curQuestion = 0;
    static int correctAnswers = 0;
    static boolean answered;
    
    /**
     * Initializes the quiz window
     * @param primaryStage Stage to change the scene for
     * @param questions questions to use in the quiz
     */
    static void initializeQuiz(Stage primaryStage, ArrayList<Question> questions) {
      correctAnswers = curQuestion = 0;
      QuizMainWindow.questions = questions;
      BorderPane root = new BorderPane();
      root.getStyleClass().add("body-screen");

      VBox infoLabelPair = new VBox(); // vbox for top labels

      // initialize labels
      quizNo = new Label("Question " + "/" + questions.size());
      correctDisplay = new Label("not initialized!");
      questionLabel = new Label("Question not initialized!");
      questionLabel.setWrapText(true);
      
      // add styles and add labels into vbox
      infoLabelPair.getChildren().addAll(quizNo, questionLabel, correctDisplay);
      quizNo.getStyleClass().add("quiz-text");
      questionLabel.getStyleClass().add("quiz-questiontext");
      correctDisplay.getStyleClass().add("quiz-questiontext");
      root.setTop(infoLabelPair);

      // initialize button to move to next question/finish quiz
      nextQuestion = new Button("Next Question!");
      nextQuestion.setOnAction((ActionEvent event) -> {
          if(curQuestion < questions.size()) {
              if(curQuestion == questions.size() - 1 || questions.size() == 1) nextQuestion.setText("Complete Quiz!");
              setQuestion(questions.get(curQuestion));
          }
          else QuizFinalWindow.initializeFinalWindow(primaryStage, curQuestion, correctAnswers, questions.size());
      });
      nextQuestion.setVisible(false); // should appear after a question is answered
      
      // set button's alignment and set to bottom
      BorderPane.setAlignment(nextQuestion, Pos.BOTTOM_CENTER);
      root.setBottom(nextQuestion);
      
      // initialize image display
      StackPane imagePane = new StackPane();
      view = new ImageView();

      imagePane.getChildren().add(view);
      imagePane.getStyleClass().add("quiz-image"); // add style
      view.setFitHeight(200);
      view.setFitWidth(200);
      root.setRight(imagePane);

      questionButtonBox = new VBox(); // used to hold the buttons pressed to answer questions
      questionButtonBox.getStyleClass().add("quiz-vbox");
      root.setLeft(questionButtonBox);
      
      // create scene, set stage scene, add first question to begin the quiz
      Scene scene = new Scene(root, 1000, 600);
      scene.getStylesheets().add("application/test.css");
      if(questions.size() > 0) setQuestion(questions.get(0));
      else {
          QuizFinalWindow.initializeFinalWindow(primaryStage, 0, 0, 0);
          return;
      }
      primaryStage.setScene(scene);
    }
    
    /**
     * Update view to match next question
     * @param question question to update to
     */
    static void setQuestion(Question question) {
      answered = false; // reset answered so answer buttons don't break statistics
      
      // update relevant references and variables
      curQuestion++;
      quizNo.setText("Question " + curQuestion + "/" + questions.size());
      correctDisplay.setText("Questions answered correctly: " + correctAnswers);
      nextQuestion.setVisible(false);
      questionLabel.setText(question.getQuestionText());
      questionButtonBox.getChildren().clear(); // reset button list

      // create new button pairs for new choices
      for (int i = 0; i < question.getChoices().length; i++) {
        questionButtonBox.getChildren().add(new ButtonPair((char) ('A' + i) + "", nextQuestion,
                question.getChoices()[i], i == question.getAnswer()).box);
      }
      if(question.getImage() != null && !question.getImage().equals("none")) {
          try {
              view.setImage(new Image(question.getImage()));
          } catch(IllegalArgumentException e) { // file URL not found
              view.setAccessibleText("Image URL not found!");
          }
      } else view.setImage(null);
    }

    /**
     * The buttons used to answer questions
     * @author Anand K Madathil
     *
     */
    static class ButtonPair {

      private HBox box; // Stores the option square and the answer button

      /**
       * Constructs the button-option square pair
       * @param optionText the option number/letter
       * @param view the "next question" button, answer buttons make it visible
       * @param answerText text for the answer
       * @param correctAnswer whether this button represents the correct answer or not
       */
      private ButtonPair(String optionText, Node view, String answerText, boolean correctAnswer) {
        box = new HBox();
        box.getStyleClass().add("quiz-hbox");
        
        // Create option square
        Text text = new Text(optionText);
        StackPane pane = new StackPane();
        Rectangle rect = new Rectangle(26, 26);
        
        pane.getChildren().addAll(rect, text);
        rect.getStyleClass().add("quiz-rect");
        
        // create and position button
        box.getChildren().add(pane);
        Button but = new Button(answerText);
        but.setMinWidth(300);
        but.setStyle("-fx-background-color: cyan;");
        but.setAlignment(Pos.BASELINE_LEFT);
        
        // shadow effect
        DropShadow shadow = new DropShadow();
        but.addEventHandler(MouseEvent.MOUSE_ENTERED, 
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        but.setEffect(shadow);
                    }
                }
        );
        but.addEventHandler(MouseEvent.MOUSE_EXITED, 
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        but.setEffect(null);
                    }
                }
        );
        
        // action effect
        but.setOnAction(correctAnswer ? (ActionEvent event) -> {
          // correct answer
          but.setStyle("-fx-background-color: lime;" + "-fx-text-fill: white;");
          but.setText("Correct!");
          view.setVisible(true);
          if(!answered) correctAnswers++;
          answered = true;
          correctDisplay.setText("Questions answered correctly: " + correctAnswers);
        } : (ActionEvent event) -> {
          answered = true; // answered now, do not allow correctAnswers to increment
          but.setStyle("-fx-background-color: red;" + "-fx-text-fill: white;");
          but.setText("Incorrect!");
          view.setVisible(true);
        });
        box.getChildren().add(but);
      }
    }
}
