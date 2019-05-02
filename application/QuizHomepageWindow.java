package application;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import org.json.simple.parser.ParseException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class QuizHomepageWindow {
  static QuestionCollection qc;
  static int numberQ;
  static String filepath;
  static ArrayList<String> topics;
  static Label topicsLabel;
  static TextField topicsRequested;
  static Label topicsRequestLabel;
  
  static void homepage(Stage primaryStage) throws Exception {

    if(qc == null) qc = new QuestionCollection();
    // Declare BorderPane
    BorderPane border = new BorderPane();
    // Declare GridPane
    VBox vBox1 = new VBox(15);
    VBox vBox2 = new VBox(10);

    // grid.setHgrow(child, value);
    // Add title text to the grid

    // This is the node for the text that has a file chooser prompt.
    Text loadQ = new Text("Load Question JSON File");
    vBox1.getChildren().add(loadQ);

    HBox hBox1 = new HBox();
    // Create TextField to get the path for the JSON file.
    TextField textField = new TextField("Enter JSON file path");
    hBox1.getChildren().add(textField);
    Button enter = new Button("Load");
    hBox1.getChildren().add(enter);
    vBox2.getChildren().add(hBox1);

    enter.setOnAction(event -> {
        try {
            String filePath = textField.getText();
            qc.addQuestionsFromJSON(filePath);
            // Populates test values for Choices
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

    Text numQuestionPrompt = new Text("Enter number of questions to use: ");
    TextField numQ = new TextField();
    numQ.setOnAction(e -> {
      if (isInt(numQ, numQ.getText()))
        numberQ = Integer.parseInt(numQ.getText());
    });
    vBox1.getChildren().add(numQuestionPrompt);
    vBox2.getChildren().add(numQ);
    
    topicsRequestLabel = new Label("\nEnter the topics you want to use by entering their numbers (Space separated):");
    topicsRequested = new TextField();
    vBox1.getChildren().add(topicsRequestLabel);
    vBox2.getChildren().add(topicsRequested);
    
    
    topicsLabel = new Label("Topics List: (empty)");
    vBox1.getChildren().add(topicsLabel);
    UpdateTopics();
    
    Label exampleLabel = new Label("An example to enter if displayed topics were \n(1. Apple), (2. Banana), (3. Orange) and you wanted to pick Apple and Orange: 1 3");
    vBox1.getChildren().add(exampleLabel);
    
    // Start Button to start the quiz
    Button start = new Button("StartQuiz");
    start.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        buildQuiz();
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

  private static boolean isInt(TextField inputField, String text) {
    try {
      Integer.parseInt(text);
      return true;
    } catch (NumberFormatException e) {
      inputField.setText("Enter a number!");
      return false;
    } catch (NullPointerException npe) {
      inputField.setText("Load a file and select topics first.");
      return false;
    }
  }
  
  private static void UpdateTopics() {
      topics = new ArrayList<String>();
      topics.addAll(qc.getTopics());
      System.out.println("Current topics: " + topics.toString());
      if(topics.size() == 0) {
          topicsLabel.setText("Topics List: (empty)");
          return;
      }
      String topicsString = "Topics List: ";
      
      for(int i = 0; i < topics.size(); i++) {
          topicsString += "(" + (i + 1) + ". " + topics.get(i) + ")";
          if(i != topics.size() - 1) topicsString += ", ";
      }
      topicsLabel.setText(topicsString);
  }
  
  private static void buildQuiz() {
    String[] topicIndices = topicsRequested.getText().trim().split(" ");
    ArrayList<String> selectedTopics = new ArrayList<String>();
    try {
        for(int i = 0; i < topicIndices.length; i++) {
            selectedTopics.add(topics.get(Integer.parseInt(topicIndices[i]) - 1));
        }
    } catch(Exception e) {
        topicsRequestLabel.setText("Invalid format of topic numbers given!");
    }
    qc.buildQuizQuestions(selectedTopics);
    qc.randomSelection(numberQ);
  }
}
