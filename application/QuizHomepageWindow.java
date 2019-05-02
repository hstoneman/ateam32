// Team Project ATeam 32- Quiz Generator
// Created by: Anand Madathil, Mayukh Misra, Hayley Stoneman, Jake Schraufnagel, Shalini Bare

package application;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import org.json.simple.parser.ParseException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Wrapper class for homepage functionality
 * @author Anand Madathil, Mayukh Misra
 *
 */
public class QuizHomepageWindow {
    
    
  static CollectionADT qc; // holds database for questions
  static ArrayList<String> topics; // current topics available to user
  
  // various FX object references used to provide feedback to the user
  static Label topicsLabel;
  static TextField topicsRequested;
  static Label topicsRequestLabel;
  static TextField numQ;
  static Label numberQLabel;
  
  /**
   * Method that sets the stage's scene to the homepage
   * @param primaryStage stage to set the scene to 
   */
  static void homepage(Stage primaryStage) {
    
    // create question collection if not already created
    if(qc == null) qc = new QuestionCollection(); 
    
    // update topics is called early so this label must be initialized here
    numberQLabel = new Label("Number of questions in database: 0");
    
    // Declare BorderPane
    BorderPane border = new BorderPane();

    VBox vBox1 = new VBox(15);
    VBox vBox2 = new VBox(10);


    // This is the node for the text that has a file chooser prompt.
    Text loadQ = new Text("Enter filepath of Question JSON file to add its questions:");
    vBox1.getChildren().add(loadQ);

    HBox hBox1 = new HBox();
    // Create TextField to get the path for the JSON file.
    TextField textField = new TextField("Enter JSON file path");
    hBox1.getChildren().add(textField);
    
    // button pressed to load file
    Button enter = new Button("Load");
    hBox1.getChildren().add(enter);
    vBox2.getChildren().add(hBox1);

    // JSON parser, adds questions if the file path given in the text field is valid
    enter.setOnAction(event -> {
        try {
            String filePath = textField.getText();
            qc.addQuestionsFromJSON(filePath);
            UpdateTopics();
          } catch (FileNotFoundException fnf) {
            textField.setText("File not found");
          } catch (IOException e) {
            textField.setText("Unknown Error Occurred");
          } catch (ParseException e) {
            textField.setText("Unable to Parse file.");
          }
    });

    // Instance of button to add Question. Sets new scene when clicked.
    Button addQ = new Button("Add Question");
    addQ.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        AddQuestionWindow.addQuestion(primaryStage);
      }
    });

    // Add title for the button.
    Text addQuestionPrompt = new Text("Add your own Question: ");
    vBox1.getChildren().add(addQuestionPrompt);
    vBox2.getChildren().add(addQ);
    
    // used to enter N (number of questions for quiz)
    Text numQuestionPrompt = new Text("\nEnter number of questions to use: ");
    numQ = new TextField();
    vBox1.getChildren().add(numQuestionPrompt);
    vBox2.getChildren().add(numQ);
    
    // used to select topics
    topicsRequestLabel = new Label("Enter the topics you want to use by entering their numbers (Space separated):");
    topicsRequested = new TextField();
    vBox1.getChildren().add(topicsRequestLabel);
    vBox2.getChildren().add(topicsRequested);
    
    
    topicsLabel = new Label("Topics List: (empty)");
    vBox1.getChildren().add(topicsLabel);
    UpdateTopics();
    
    // example label for topic selection input
    Label exampleLabel = new Label("An example to enter if displayed topics were \n"
            + "(1. Apple), (2. Banana), (3. Orange) and you wanted to pick Apple and Orange: 1 3");
    vBox1.getChildren().add(exampleLabel);
    vBox1.getChildren().add(numberQLabel); // add number of questions in database label 
    
    // Start Button to start the quiz
    Button start = new Button("StartQuiz");
    start.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        if(buildQuiz())
            QuizMainWindow.initializeQuiz(primaryStage, qc.getRandomQuestions());
      }
    });

    Text title = new Text("Welcome to Quiz Generator!\n");
    title.setStyle("-fx-font-size: 24px;");


    border.setTop(title);
    BorderPane.setAlignment(title, Pos.CENTER);

    border.setLeft(vBox1);
    border.setRight(vBox2);
    BorderPane.setAlignment(start, Pos.CENTER);

    border.setBottom(start);
    border.setStyle("-fx-background-color: C9EEFF");


    Scene homepage = new Scene(border, 1000, 600);
    primaryStage.setScene(homepage);
  }
  
  /**
   * Checks if a passed string can be interpreted as a string and sets a textfield's value to 
   * an error message if it can't
   * @param inputField field used to display error message
   * @param text string to parse into an int
   * @return true if string can be interpreted as an int, false otherwise
   */
  private static boolean isInt(TextField inputField, String text) {
    try {
      Integer.parseInt(text);
      return true;
    } catch (NumberFormatException e) { // parsing failed if an exception is thrown
      inputField.setText("Enter a number!");
      return false;
    } catch (NullPointerException npe) {
      inputField.setText("Load a file and select topics first.");
      return false;
    }
  }
  
  /**
   * Updates the topic display to match the question collections current topics
   */
  private static void UpdateTopics() {
      // update number of questions
      numberQLabel.setText("Number of questions in database: " + qc.getTotalNumberQuestions());
      topics = new ArrayList<String>();
      topics.addAll(qc.getTopics());
      
      // update topics
      System.out.println("Current topics: " + topics.toString());
      if(topics.size() == 0) {
          topicsLabel.setText("Topics List: (empty)");
          return;
      }
      String topicsString = "Topics List: ";
      
      // add stylization to the string
      for(int i = 0; i < topics.size(); i++) {
          topicsString += "(" + (i + 1) + ". " + topics.get(i) + ")";
          if(i != topics.size() - 1) topicsString += ", ";
      }
      topicsLabel.setText(topicsString);
  }
  
  /**
   * Builds the quiz questions using the selected topics
   * @return true if building was valid, false otherwise
   */
  private static boolean buildQuiz() {
    int numberQ = 0;
    if (isInt(numQ, numQ.getText())) {
      numberQ = Integer.parseInt(numQ.getText());
    } 
    else return false; // parsing for N failed
    
    // parse topic list
    String[] topicIndices = topicsRequested.getText().trim().split(" ");
    ArrayList<String> selectedTopics = new ArrayList<String>();
    try {
        for(int i = 0; i < topicIndices.length; i++) {
            selectedTopics.add(topics.get(Integer.parseInt(topicIndices[i]) - 1));
        }
    } catch(Exception e) { 
        // parsing topics list failed 
        // (Can be either due to not entering integers or entering invalid integers)
        topicsRequested.setText("Invalid format of topic numbers!");
        return false;
    }
    
    // build questions and generate random selection
    qc.buildQuizQuestions(selectedTopics);
    qc.randomSelection(numberQ);
    return true;
  }
}
