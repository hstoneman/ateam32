// Team Project ATeam 32- Quiz Generator
// Created by: Anand Madathil, Mayukh Misra, Hayley Stoneman, Jake Schraufnagel, Shalini Bare

package application;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main class used to drive the front-end
 * @author Anand K Madathil
 *
 */
public class Main extends Application {

  boolean quitting = false; // if this is true the user has already tried to quit
  
  @Override
  /**
   * Called to initialize the project
   * @Stage primaryStage stage used for the project
   * @throws Exception unknown
   */
  public void start(Stage primaryStage) throws Exception {
    QuizHomepageWindow.homepage(primaryStage);
    primaryStage.setOnCloseRequest(event -> {
        QuitWindow.initializeQuitWindow(primaryStage); 
        if(!quitting) { // if user has already tried quitting once let them quit
            event.consume();
            quitting = true;
        }
    });
    primaryStage.setTitle("Quiz Generator - A Team 32");
    primaryStage.show();

  }
  
  /**
   * Main method
   * @param args unknown
   */
  public static void main(String[] args) {
    launch(args);
  }
}

