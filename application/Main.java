package application;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import org.json.simple.parser.ParseException;
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
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
  int curQuestion = 0;
  ArrayList<Question> questions;
  Label quizNo;
  Label correctDisplay;
  int correctAnswers = 0;
  
  private void initializeQuiz(Stage primaryStage, ArrayList<Question> questions) {
    correctAnswers = curQuestion = 0;
    this.questions = questions;
    BorderPane root = new BorderPane();
    root.getStyleClass().add("body-screen");

    VBox labelPair = new VBox();

    quizNo = new Label("Question " + "/" + questions.size());
    correctDisplay = new Label("not initialized!");
    questionLabel = new Label("Question not initialized!");
    questionLabel.setWrapText(true);

    labelPair.getChildren().addAll(quizNo, questionLabel, correctDisplay);
    quizNo.getStyleClass().add("quiz-text");
    questionLabel.getStyleClass().add("quiz-questiontext");
    correctDisplay.getStyleClass().add("quiz-questiontext");
    root.setTop(labelPair);

    nextQuestion = new Button("Next Question!");
    nextQuestion.setOnAction((ActionEvent event) -> {
        if(curQuestion < questions.size()) setQuestion(questions.get(curQuestion));
        else initializeFinalWindow(primaryStage);
    });
    nextQuestion.setVisible(false);
    BorderPane.setAlignment(nextQuestion, Pos.BOTTOM_CENTER);
    root.setBottom(nextQuestion);
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
    setQuestion(questions.get(0));
    primaryStage.setScene(scene);
  }

  private void initializeFinalWindow(Stage primaryStage) {
      BorderPane root = new BorderPane();
      Label congrats = new Label("Congratulations! You have completed the quiz");
      root.setTop(congrats);
      VBox statsBox = new VBox();
      Label questionsAttempted = new Label("Questions attempted: " + curQuestion);
      Label corrAnsLabel = new Label("Correct Answers: " + correctAnswers);
      Label percentage = new Label("Percentage: " + (float)correctAnswers * 100 / questions.size() + "%");
      congrats.setStyle("-fx-font-size: 24px;");
      corrAnsLabel.setStyle("-fx-font-size: 20px;");
      percentage.setStyle("-fx-font-size: 20px;");
      questionsAttempted.setStyle("-fx-font-size: 20px;");
      Button but = new Button("Back to main menu");
      but.setOnAction((ActionEvent event) -> {
          try {
              homepage(primaryStage);
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
  
  void setQuestion(Question question) {
    curQuestion++;
    quizNo.setText("Question " + curQuestion + "/" + questions.size());
    correctDisplay.setText("Questions answered correctly: " + correctAnswers);
    nextQuestion.setVisible(false);
    questionLabel.setText(question.getQuestionText());
    questionButtonBox.getChildren().clear();

    for (int i = 0; i < question.choices().length; i++) {
      questionButtonBox.getChildren().add(new ButtonPair((char) ('A' + i) + "", nextQuestion,
          question.choices()[i], i == question.getAnswer()).box);
    }
    view.setImage(question.getImage());
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
      DropShadow shadow = new DropShadow();
      but.addEventHandler(MouseEvent.MOUSE_ENTERED, 
              new EventHandler<MouseEvent>() {
                  @Override
                  public void handle(MouseEvent e) {
                      but.setEffect(shadow);
                  }
              }
      );
      but.addEventHandler(MouseEvent.MOUSE_EXITED, 
              new EventHandler<MouseEvent>() {
                  @Override
                  public void handle(MouseEvent e) {
                      but.setEffect(null);
                  }
              }
      );
      but.setOnAction(correctAnswer ? (ActionEvent event) -> {
        but.setStyle("-fx-background-color: lime;" + "-fx-text-fill: white;");
        but.setText("Correct!");
        view.setVisible(true);
        correctAnswers++;
        correctDisplay.setText("Questions answered correctly: " + correctAnswers);
      } : (ActionEvent event) -> {
        but.setStyle("-fx-background-color: red;" + "-fx-text-fill: white;");
        but.setText("Incorrect!");
        view.setVisible(true);
      });
      box.getChildren().add(but);
    }
  }

  private void homepage(Stage primaryStage) throws Exception {

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

    // Start Button to start the quiz
    Button start = new Button("StartQuiz");
    start.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        try {
            initializeQuiz(primaryStage, new ParseJSON("test.json").parseFile());
        } catch (IOException | ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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
    primaryStage.setScene(homepage);
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
  
  boolean quitting = false;
  
  @Override
  public void start(Stage primaryStage) throws Exception {
    homepage(primaryStage);
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

