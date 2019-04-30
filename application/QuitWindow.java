package application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;




public class QuitWindow {
    static void initializeQuitWindow(Stage primaryStage) {
        BorderPane pane = new BorderPane();
        Label confirmLabel = new Label("Would you like to save your question bank into a JSON file?");
        confirmLabel.setStyle("-fx-font-size: 24px; -fx-padding: 15 0 0 0;");
        pane.setTop(confirmLabel);
        BorderPane.setAlignment(confirmLabel, Pos.TOP_CENTER);
        HBox buttonBox = new HBox();
        buttonBox.setSpacing(20);
        buttonBox.setAlignment(Pos.CENTER);
        Button but1 = new Button("Save");
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
