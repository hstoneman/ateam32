// Team Project ATeam 32- Quiz Generator
// Created by: Anand Madathil, Mayukh Misra, Hayley Stoneman, Jake Schraufnagel, Shalini Bare

package application;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Wrapper class for adding a question
 * @author Anand K Madathil, Shalini Bare
 *
 */
public class AddQuestionWindow {
    
    static int numChoices; // current number of choices
    static String question; // question string
    static String topic; // topic string
    static int answer; // answer index
    static String metaData;
    static List<String> allChoices; // holds all choices
    
    /**
     * Sets the scene to add a question
     * @param primaryStage stage to set the scene for
     */
    static void addQuestion(Stage primaryStage) {
        // initialize instance fields
        numChoices = 1;
        question = null;
        topic = null;
        answer = 0;
        metaData = null;
        allChoices = new ArrayList<String>();
        
        try {
            // Layout Panes
            GridPane grid = new GridPane();
            grid.setStyle("-fx-background-color: #FAF0E6");
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(0, 10, 0, 10));
            VBox col1 = new VBox();
            VBox col2 = new VBox();

            // Labels
            Label label1 = new Label("Adding to the Question Bank");
            label1.setStyle("-fx-font-weight: bold");
            Label label2 = new Label("Question text");
            Label label3 = new Label("Topic");
            Label label4 = new Label("Image if desired");
            Label label5 = new Label("Question answer index (first choice is index 1)");
            Label label6 = new Label("Question choices");
            Label label7 = new Label("Meta-data");
            Label label8 = new Label("You must change EVERY text-field given to you\n from its default value or else\n the question won't be added!");
            // Buttons
            Button choice = new Button("ADD CHOICE");
            Button submit = new Button("SUBMIT");
            submit.setStyle("-fx-background-color: linear-gradient(#f0ff35, #a9ff00),\r\n"
                    + "radial-gradient(center 50% -40%, radius 200%, #b8ee36 45%, #80c800 50%)");

            // Text fields
            TextField questionField = new TextField("Question Text");
            TextField topicField = new TextField("Topic");
            TextField pathField = new TextField("File Path");
            TextField ansField = new TextField("Answer");
            TextField textField[] = new TextField[5];
            TextField metaDataField = new TextField("Meta-data");
            textField[0] = new TextField("Choice Text");

            // vBox spacing and adding nodes
            col1.setSpacing(20);
            col2.setSpacing(10);
            ObservableList<Node> list1 = col1.getChildren();
            list1.addAll(label2, label3, label4, label5, label7, label6);
            ObservableList<Node> list2 = col2.getChildren();
            list2.addAll(questionField, topicField, pathField, ansField, metaDataField, textField[0]);

            // Grid pane setting
            grid.add(label1, 0, 0);
            grid.add(label8, 0, 1);
            grid.add(col1, 0, 2);
            grid.add(col2, 1, 2);
            grid.add(choice, 1, 8);
            grid.add(submit, 1, 9);

            // Data collection
            choice.setOnAction(e -> {
                if (numChoices <= 4) {
                    textField[numChoices] = new TextField("Choice Text");
                    grid.add(textField[numChoices], 1, numChoices + 2);
                    numChoices++;
                }

            });
            
            submit.setOnAction(e -> {
                
                // ensure each text field has a valid output, otherwise return
                 
                if (!questionField.getText().equals("Question Text"))
                question = questionField.getText();
                else return;
                
                if (!topicField.getText().equals("Topic"))
                topic = topicField.getText();
                else return;
                if (!ansField.getText().equals("Answer"))
                    try {
                        answer = Integer.parseInt(ansField.getText()) - 1;
                    } catch(Exception e1) {
                        ansField.setText("Enter a valid choice index");
                        return;
                    }
                else return;
                
                // go through choice text fields
                for (int cnt = 0; cnt < numChoices; cnt++) {
                    String tmp = textField[cnt].getText();
                    if (!tmp.equals("Choice Text"))
                        allChoices.add(tmp);
                    else return;
                } 
                
                if(!metaDataField.getText().equals("Meta-data")) {
                    metaData = metaDataField.getText();
                } else return;
                String[] allChoicesArray = new String[allChoices.size()];
                // add question
                QuizHomepageWindow.qc.addQuestion(metaData, question, topic, pathField.getText(), allChoices.toArray(allChoicesArray), answer);
                try {
                    QuizHomepageWindow.homepage(primaryStage);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            });
            
            // Scene setting
            Scene scene = new Scene(grid, 540, 650);
            primaryStage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
