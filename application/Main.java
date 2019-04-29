package application;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.event.EventHandler;


public class Main extends Application {

  Label questionLabel;
  VBox questionButtonBox;
  Button nextQuestion;
  ImageView view;

  private Scene initialize(Stage primaryStage) {
    BorderPane root = new BorderPane();
    root.getStyleClass().add("body-screen");

    VBox labelPair = new VBox();

    // these labels will become private fields that will later be updated
    Label quizNo = new Label("Question 1/???");
    questionLabel = new Label("Question not initialized!");
    Label questionsAnswered = new Label("Questions answered: ???/???");

    labelPair.getChildren().addAll(quizNo, questionLabel, questionsAnswered);
    quizNo.getStyleClass().add("quiz-text");
    questionLabel.getStyleClass().add("quiz-questiontext");
    questionsAnswered.getStyleClass().add("quiz-questiontext");

    root.setTop(labelPair);

    nextQuestion = new Button("Next Question!");
    nextQuestion.setVisible(false);
    root.setBottom(nextQuestion);
    BorderPane.setAlignment(nextQuestion, Pos.BOTTOM_RIGHT);

    StackPane imagePane = new StackPane();
    view = new ImageView();

    imagePane.getChildren().add(view);
    imagePane.getStyleClass().add("quiz-image");
    view.setFitHeight(200);
    view.setFitWidth(200);

    questionButtonBox = new VBox();

    questionButtonBox.getStyleClass().add("quiz-vbox");

    root.setLeft(questionButtonBox);
    root.setRight(imagePane);
    Scene scene = new Scene(root, 1000, 600);
    scene.getStylesheets().add("application/test.css");
    return scene;
  }

  void setQuestion(String question, String[] answers, List<Integer> correctIndices) {
    questionLabel.setText(question);
    questionButtonBox.getChildren().clear();

    for (int i = 0; i < answers.length; i++) {
      questionButtonBox.getChildren().add(new ButtonPair((char) ('A' + i) + "", nextQuestion,
          answers[i], correctIndices.contains(i)).box);
    }
  }

  void setQuestion(String question, String[] answers, List<Integer> correctIndices, Image image) {
    view.setImage(image);
    setQuestion(question, answers, correctIndices);
  }

  class ButtonPair {

    private HBox box;

    private ButtonPair(String optionText, Node view, String answerText, boolean correctAnswer) {
      box = new HBox();
      box.getStyleClass().add("quiz-hbox");
      Text text = new Text(optionText);
      StackPane pane = new StackPane();
      Rectangle rect = new Rectangle(26, 26);
      pane.getChildren().addAll(rect, text);
      rect.getStyleClass().add("quiz-rect");
      box.getChildren().add(pane);
      Button but = new Button(answerText);
      but.setMinWidth(300);
      but.setStyle("-fx-background-color: cyan;");
      but.setAlignment(Pos.BASELINE_LEFT);
      but.setOnAction(correctAnswer ? (ActionEvent event) -> {
        but.setStyle("-fx-background-color: lime;" + "-fx-text-fill: white;");
        but.setText("Correct!");
        view.setVisible(true);
      } : (ActionEvent event) -> {
        but.setStyle("-fx-background-color: red;" + "-fx-text-fill: white;");
        but.setText("Incorrect!");
        view.setVisible(true);
      });
      box.getChildren().add(but);
    }
  }

  private Scene homepage(Stage primaryStage) throws Exception {

    // Declare BorderPane
    BorderPane border = new BorderPane();
    // Declare GridPane
    VBox vBox1 = new VBox(15);
    VBox vBox2 = new VBox(10);

    // grid.setHgrow(child, value);
    // Add title text to the grid

    // This is the node for the text that has a file chooser prompt.
    Text loadQ = new Text("Load Question JSON File");
    vBox1.getChildren().add(loadQ);

    // Create TextField to get the path for the JSON file.
    TextField textField = new TextField("Enter JSon file path");
    vBox2.getChildren().add(textField);

    // Creates a new question collection with the file entered.
//    textField.setOnMouseClicked(textField.setText(""));
    String filePath = textField.getText();
    QuestionCollection qc = new QuestionCollection(filePath);

    // Populates test values for Choices
    ArrayList<String> qTopics = new ArrayList<String>();
    qTopics = qc.getTopics();
    qTopics.add(0, "<Select>");
    qTopics.add("Topic 1");
    qTopics.add("Topic 2");
    qTopics.add("Topic 3");
    qTopics.add("Topic 4");

    // Create instance of a combo box and ArrayList of strings for the choices.
    ArrayList<String> choice = new ArrayList<String>();
    Label selected = new Label();

    ComboBox<String> topics = new ComboBox<String>(FXCollections.observableArrayList(qTopics));
    // Add a listener that detects changes in value and adds them to the choices in topics.
    topics.valueProperty().addListener(new ChangeListener<String>() {
      @SuppressWarnings("rawtypes")
      @Override
      public void changed(ObservableValue ov, String t, String t1) {
        if (!choice.contains(t1)) {
          choice.add(t1);
          selected.setText("Topics selected: " + choice.toString());
        } else {
          choice.remove(t1);
          selected.setText("Topics selected: " + choice.toString());
        }
      }
    });

    topics.setValue("<Select>");

    // Adds the text for the ComboBox
    Text selectTopics = new Text(
        "Select topics you want in your quiz from the dropdown. \nSelect again to remove.");
    // Add the comboBox and text to the grid.

    vBox1.getChildren().add(selectTopics);
    vBox2.getChildren().add(topics);

    // Label to show topics selected
    choice.clear();
    // selected.property

    // Refresh button to refresh output
    Button clear = new Button("Clear Topics");
    clear.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        choice.clear();
        selected.setText(choice.toString());
      }
    });

    // Display choices taken.
    vBox1.getChildren().add(selected);
    vBox2.getChildren().add(clear);

    // Instance of button to add Question. Sets new scene when clicked.
    Button addQ = new Button("Add Question");
    addQ.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        primaryStage.setScene(addQuestion(primaryStage));
      }
    });

    // Add title for the button.
    Text addQuestionPrompt = new Text("Add your own Question: ");
    vBox1.getChildren().add(addQuestionPrompt);
    vBox2.getChildren().add(addQ);

    Text numQuestionPrompt = new Text("Enter number of questions to use: ");
    TextField numQ = new TextField();

    vBox1.getChildren().add(numQuestionPrompt);
    vBox2.getChildren().add(numQ);

    try {
      Integer numQuestions = Integer.parseInt(numQ.getText());
    } catch (Exception E) {

    }

    // Start Button to start the quiz
    Button start = new Button("StartQuiz");
    start.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        primaryStage.setScene(initialize(primaryStage));
      }
    });

    Text title = new Text("Welcome to Quiz Generator!\n");
    title.setStyle("-fx-font-size: 24px;");


    border.setTop(title);
    BorderPane.setAlignment(title, Pos.CENTER);

    border.setLeft(vBox1);
    border.setRight(vBox2);
    BorderPane.setAlignment(start, Pos.CENTER);

    border.setBottom(start);
    border.setStyle("-fx-background-color: C9EEFF");


    Scene homepage = new Scene(border, 1000, 600);
    return homepage;
  }

  private Scene addQuestion(Stage primaryStage) {

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
    return scene;
  }

  @Override
  public void start(Stage primaryStage) throws Exception {

    primaryStage.setScene(homepage(primaryStage));
    primaryStage.setTitle("Quiz Generator - A Team 32");
    primaryStage.show();

  }

  public static void main(String[] args) {
    launch(args);
  }

}
