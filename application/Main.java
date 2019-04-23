package application;
	
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
	    
	    class ButtonPair {
	        
	        private HBox box;
	        private ButtonPair(String txt) {
	            box = new HBox();
	            box.getStyleClass().add("quiz-hbox");
	            Text text = new Text(txt);
	            StackPane pane = new StackPane();
	            Rectangle rect = new Rectangle(26,26);
	            pane.getChildren().addAll(rect, text);
	            rect.getStyleClass().add("quiz-rect");
	            box.getChildren().add(pane);
	            Button but = new Button("Hello world!");
	            but.setMinWidth(300);
	            but.setStyle("-fx-background-color: cyan;");
	            but.setOnAction((ActionEvent event) -> { 
	                but.setStyle("-fx-background-color: lime;"
	                        + "-fx-text-fill: white;");
	                but.setText("Correct!");
	            
	            });
	            box.getChildren().add(but);
	        }
	    }
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,1000,600);
			scene.getStylesheets().add("application/test.css");
			root.getStyleClass().add("body-screen");
			Label quizNo = new Label("Question X/X");
			quizNo.getStyleClass().add("quiz-text");
			VBox buttonBox = new VBox();
			
			buttonBox.getStyleClass().add("quiz-vbox");
			for(int i = 0; i < 4; i++) {
			    buttonBox.getChildren().add(new ButtonPair("A").box);
			}

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
