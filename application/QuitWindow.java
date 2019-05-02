// Team Project ATeam 32- Quiz Generator
// Created by: Anand Madathil, Mayukh Misra, Hayley Stoneman, Jake Schraufnagel, Shalini Bare

package application;

import java.io.IOException;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * Window used to quit from the program, allows user to save JSON into a file called questions.json
 * @author Anand K Madathil
 *
 */
public class QuitWindow {
    
    /**
     * Method that sets the scene to the quit window
     * @param primaryStage stage to set the scene for
     */
    static void initializeQuitWindow(Stage primaryStage) {
        // create pane and information label
        BorderPane pane = new BorderPane();
        Label confirmLabel = new Label("Would you like to save your question bank into a JSON file?"
                + "\n           (JSON is saved into a file called questions.json)");
        confirmLabel.setStyle("-fx-font-size: 24px; -fx-padding: 15 0 0 0;");
        pane.setTop(confirmLabel);
        BorderPane.setAlignment(confirmLabel, Pos.TOP_CENTER);
        HBox buttonBox = new HBox();
        buttonBox.setSpacing(20);
        buttonBox.setAlignment(Pos.CENTER);
        Button but1 = new Button("Save");
        
        // Save
        but1.setOnAction(event -> {
            if(QuizHomepageWindow.qc != null) {
                try {
                    QuizHomepageWindow.qc.writeToJSON();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                primaryStage.close(); // close primary stage
            }
        });
        
        // Do not save
        Button but2 = new Button("Exit without save");
        but2.setOnAction(event -> {
            primaryStage.close();
        });
        buttonBox.getChildren().addAll(but1, but2);
        pane.setCenter(buttonBox);
        
        Scene scene = new Scene(pane, 1000, 600);
        primaryStage.setScene(scene);
    }
}
