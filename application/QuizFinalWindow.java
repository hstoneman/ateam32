// Team Project ATeam 32- Quiz Generator
// Created by: Anand Madathil, Mayukh Misra, Hayley Stoneman, Jake Schraufnagel, Shalini Bare

package application;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Wrapper class for the results window displayed after the completion of a quiz
 * @author Anand K Madathil
 *
 */
public class QuizFinalWindow {
    
    /**
     * Sets the scene to the results window
     * @param primaryStage stage to set the scene for
     * @param attemptedQuestions number of attempted questions
     * @param correctAnswers number of correct answers
     * @param totalQuestions number of total questions
     */
    static void initializeFinalWindow(Stage primaryStage, int attemptedQuestions, int correctAnswers, int totalQuestions) {
        // Set borderpane and aesthetics
        BorderPane root = new BorderPane();
        Label congrats = new Label("Congratulations! You have completed the quiz");
        root.setTop(congrats);
        VBox statsBox = new VBox();
        Label questionsAttempted = new Label("Questions attempted: " + attemptedQuestions);
        Label corrAnsLabel = new Label("Correct Answers: " + correctAnswers);
        
        // calculate percentage
        Label percentage = new Label("Percentage: " + (float)correctAnswers * 100 / totalQuestions + "%");
        if(percentage.getText().equals("Percentage: NaN%")) percentage.setText("Not enough questions answered for a percentage");
        congrats.setStyle("-fx-font-size: 24px;");
        corrAnsLabel.setStyle("-fx-font-size: 20px;");
        percentage.setStyle("-fx-font-size: 20px;");
        questionsAttempted.setStyle("-fx-font-size: 20px;");
        Button but = new Button("Back to main menu");
        
        // return to homepage
        but.setOnAction((ActionEvent event) -> {
            try {
                QuizHomepageWindow.homepage(primaryStage);
              } catch (Exception e) {
                  e.printStackTrace();
              }});
        
        // set scene and borderpane up
        statsBox.getChildren().addAll(questionsAttempted, corrAnsLabel, percentage);
        root.setCenter(statsBox);
        statsBox.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(congrats, Pos.TOP_CENTER);
        root.setBottom(but);
        BorderPane.setAlignment(but, Pos.BOTTOM_CENTER);
        primaryStage.setScene(new Scene(root, 1000, 600));
    }
}
