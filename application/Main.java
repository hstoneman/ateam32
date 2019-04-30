package application;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

  boolean quitting = false;
  
  @Override
  public void start(Stage primaryStage) throws Exception {
    QuizHomepageWindow.homepage(primaryStage);
    primaryStage.setOnCloseRequest(event -> {
        QuitWindow.initializeQuitWindow(primaryStage); 
        if(!quitting) {
            event.consume();
            quitting = true;
        }
    });
    primaryStage.setTitle("Quiz Generator - A Team 32");
    primaryStage.show();

  }

  public static void main(String[] args) {
    launch(args);
  }
}

