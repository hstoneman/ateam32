package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.collections.ObservableList;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Insets;
import java.util.*;

public class Main extends Application {
	int i = 0;
	String question;
	String topic;
	ImageView image;
	String answer;
	List<String> allChoices = new ArrayList<String>();

	@Override
	public void start(Stage primaryStage) {
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
			Label label5 = new Label("Question answer");
			Label label6 = new Label("Question choices");

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
			TextField textField[] = new TextField[4];
			textField[0] = new TextField("Choice Text");

			// vBox spacing and adding nodes
			col1.setSpacing(20);
			col2.setSpacing(10);
			ObservableList<Node> list1 = col1.getChildren();
			list1.addAll(label2, label3, label4, label5, label6);
			ObservableList<Node> list2 = col2.getChildren();
			list2.addAll(questionField, topicField, pathField, ansField, textField[0]);

			// Grid pane setting
			grid.add(label1, 0, 0);
			grid.add(col1, 0, 1);
			grid.add(col2, 1, 1);
			grid.add(choice, 1, 8);
			grid.add(submit, 1, 9);

			// Data collection
			choice.setOnAction(e -> {
				if (i < 4) {
					textField[i] = new TextField("Choice Text");
					grid.add(textField[i], 1, i + 2);
					i = i + 1;
				}

			});
			
			submit.setOnAction(e -> {
				if (!questionField.getText().equals("Question Text"))
				question = questionField.getText();
				if (!topicField.getText().equals("Topic"))
				topic = topicField.getText();
				if (!pathField.getText().equals("File Path")) {
					Image file = new Image(pathField.getText());
					image = new ImageView(file);
				}
				if (!ansField.getText().equals("Answer"))
				answer = ansField.getText();
				for (int cnt = 0; cnt < i; cnt++) {
					String tmp = textField[cnt].getText();
					if (!tmp.equals("Choice Text"))
						allChoices.add(tmp);
				}
			});
			
			// Scene setting
			Scene scene = new Scene(grid, 400, 400);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
