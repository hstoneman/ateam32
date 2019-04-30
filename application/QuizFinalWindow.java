package application;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class QuizFinalWindow {
    static void initializeFinalWindow(Stage primaryStage, int attemptedQuestions, int correctAnswers, int totalQuestions) {
        BorderPane root = new BorderPane();
        Label congrats = new Label("Congratulations! You have completed the quiz");
        root.setTop(congrats);
        VBox statsBox = new VBox();
        Label questionsAttempted = new Label("Questions attempted: " + attemptedQuestions);
        Label corrAnsLabel = new Label("Correct Answers: " + correctAnswers);
        Label percentage = new Label("Percentage: " + (float)correctAnswers * 100 / totalQuestions + "%");
        congrats.setStyle("-fx-font-size: 24px;");
        corrAnsLabel.setStyle("-fx-font-size: 20px;");
        percentage.setStyle("-fx-font-size: 20px;");
        questionsAttempted.setStyle("-fx-font-size: 20px;");
        Button but = new Button("Back to main menu");
        but.setOnAction((ActionEvent event) -> {
            try {
                QuizHomepageWindow.homepage(primaryStage);
              } catch (Exception e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
              }});
        statsBox.getChildren().addAll(questionsAttempted, corrAnsLabel, percentage);
        root.setCenter(statsBox);
        statsBox.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(congrats, Pos.TOP_CENTER);
        root.setBottom(but);
        BorderPane.setAlignment(but, Pos.BOTTOM_CENTER);
        primaryStage.setScene(new Scene(root, 1000, 600));
    }
}
