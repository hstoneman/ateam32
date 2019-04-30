package application;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddQuestionWindow {
    static void addQuestion(Stage primaryStage) {

        GridPane grid = new GridPane();
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
        submit.setStyle("-fx-background-color: #C9EEFF");

        // Text fields
        TextField textField1 = new TextField("Question Text");
        TextField textField2 = new TextField("Topic");
        TextField textField3 = new TextField("File Path");
        TextField textField4 = new TextField("Answer");
        TextField textField5 = new TextField("Choice Text");

        // vBox spacing and adding nodes
        col1.setSpacing(20);
        col2.setSpacing(10);
        ObservableList<Node> list1 = col1.getChildren();
        list1.addAll(label2, label3, label4, label5, label6);
        ObservableList<Node> list2 = col2.getChildren();
        list2.addAll(textField1, textField2, textField3, textField4, textField5, choice, submit);

        // Grid pane setting
        grid.add(label1, 0, 0);
        grid.add(col1, 0, 1);
        grid.add(col2, 1, 1);

        // BorderPane setting
        BorderPane border = new BorderPane();
        border.setCenter(grid);

        // Scene setting
        Scene scene = new Scene(border, 1000, 600);
        scene.getStylesheets().add("application.css");
        primaryStage.setScene(scene);
      }
}