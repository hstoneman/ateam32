package application;
	
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,1000,600);
			scene.getStylesheets().add("application/test.css");
			root.getStyleClass().add("body-screen");
			Label quizNo = new Label("Question X/X");
			quizNo.getStyleClass().add("quiz-text");
			VBox buttonBox = new VBox();
			Button but = new Button("Hello world!");
			but.setMinWidth(300);
			but.setStyle("-fx-background-color: cyan;");
			but.setOnAction((ActionEvent event) -> { 
			    but.setStyle("-fx-background-color: lime;"
			            + "-fx-text-fill: white;");
			    but.setText("Correct!");
			
			});
			buttonBox.getStyleClass().add("quiz-vbox");
			buttonBox.getChildren().add(but);
			root.setTop(quizNo);
			root.setLeft(buttonBox);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
