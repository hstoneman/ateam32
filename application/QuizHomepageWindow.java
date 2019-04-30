package application;

import java.io.IOException;
import java.util.ArrayList;
import org.json.simple.parser.ParseException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class QuizHomepageWindow {
    static void homepage(Stage primaryStage) throws Exception {

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

        // Create TextField to get the path for the JSON file.
        TextField textField = new TextField("Enter JSon file path");
        vBox2.getChildren().add(textField);

        // Creates a new question collection with the file entered.
//        textField.setOnMouseClicked(textField.setText(""));
        String filePath = textField.getText();
        QuestionCollection qc = new QuestionCollection(filePath);

        // Populates test values for Choices
        ArrayList<String> qTopics = new ArrayList<String>();
        qTopics = qc.getTopics();
        qTopics.add(0, "<Select>");
        qTopics.add("Topic 1");
        qTopics.add("Topic 2");
        qTopics.add("Topic 3");
        qTopics.add("Topic 4");

        // Create instance of a combo box and ArrayList of strings for the choices.
        ArrayList<String> choice = new ArrayList<String>();
        Label selected = new Label();

        ComboBox<String> topics = new ComboBox<String>(FXCollections.observableArrayList(qTopics));
        // Add a listener that detects changes in value and adds them to the choices in topics.
        topics.valueProperty().addListener(new ChangeListener<String>() {
          @SuppressWarnings("rawtypes")
          @Override
          public void changed(ObservableValue ov, String t, String t1) {
            if (!choice.contains(t1)) {
              choice.add(t1);
              selected.setText("Topics selected: " + choice.toString());
            } else {
              choice.remove(t1);
              selected.setText("Topics selected: " + choice.toString());
            }
          }
        });

        topics.setValue("<Select>");

        // Adds the text for the ComboBox
        Text selectTopics = new Text(
            "Select topics you want in your quiz from the dropdown. \nSelect again to remove.");
        // Add the comboBox and text to the grid.

        vBox1.getChildren().add(selectTopics);
        vBox2.getChildren().add(topics);

        // Label to show topics selected
        choice.clear();
        // selected.property

        // Refresh button to refresh output
        Button clear = new Button("Clear Topics");
        clear.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            choice.clear();
            selected.setText(choice.toString());
          }
        });

        // Display choices taken.
        vBox1.getChildren().add(selected);
        vBox2.getChildren().add(clear);

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

        vBox1.getChildren().add(numQuestionPrompt);
        vBox2.getChildren().add(numQ);

        // Start Button to start the quiz
        Button start = new Button("StartQuiz");
        start.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            try {
                QuizMainWindow.initializeQuiz(primaryStage, new ParseJSON("test.json").parseFile());
            } catch (IOException | ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
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
      
}
