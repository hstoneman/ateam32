//package application;
//    
//import java.util.ArrayList;
//import java.util.List;
//import javafx.application.Application;
//import javafx.event.ActionEvent;
//import javafx.geometry.Pos;
//import javafx.stage.Stage;
//import javafx.scene.Node;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.layout.BorderPane;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.StackPane;
//import javafx.scene.layout.VBox;
//import javafx.scene.shape.Rectangle;
//import javafx.scene.text.Text;
//
//
//public class Main extends Application {
//    
//    class ButtonPair {
//        
//        private HBox box;
//        private ButtonPair(String optionText, Node view, String answerText, boolean correctAnswer) {
//            box = new HBox();
//            box.getStyleClass().add("quiz-hbox");
//            Text text = new Text(optionText);
//            StackPane pane = new StackPane();
//            Rectangle rect = new Rectangle(26,26);
//            pane.getChildren().addAll(rect, text);
//            rect.getStyleClass().add("quiz-rect");
//            box.getChildren().add(pane);
//            Button but = new Button(answerText);
//            but.setMinWidth(300);
//            but.setStyle("-fx-background-color: cyan;");
//            but.setAlignment(Pos.BASELINE_LEFT);
//            but.setOnAction(
//                correctAnswer ?    
//                (ActionEvent event) -> { 
//                but.setStyle("-fx-background-color: lime;"
//                        + "-fx-text-fill: white;");
//                but.setText("Correct!");
//                view.setVisible(true);
//            } : (ActionEvent event) -> { 
//                but.setStyle("-fx-background-color: red;"
//                        + "-fx-text-fill: white;");
//                but.setText("Incorrect!");
//                view.setVisible(true);
//            });
//            box.getChildren().add(but);
//        }
//    }
//    
//    private BorderPane initialize() {
//        BorderPane root = new BorderPane();
//        root.getStyleClass().add("body-screen");
//        
//        VBox labelPair = new VBox();
//    
//        // these labels will become private fields that will later be updated
//        Label quizNo = new Label("Question 1/???");
//        questionLabel = new Label("Question not initialized!");
//        Label questionsAnswered = new Label("Questions answered: ???/???");
//        
//        labelPair.getChildren().addAll(quizNo, questionLabel, questionsAnswered);
//        quizNo.getStyleClass().add("quiz-text");
//        questionLabel.getStyleClass().add("quiz-questiontext");
//        questionsAnswered.getStyleClass().add("quiz-questiontext");
//
//        root.setTop(labelPair);
//        
//        nextQuestion = new Button("Next Question!");
//        nextQuestion.setVisible(false);
//        root.setBottom(nextQuestion);
//        BorderPane.setAlignment(nextQuestion, Pos.BOTTOM_RIGHT);
//        
//        StackPane imagePane = new StackPane();
//        view = new ImageView();
//
//        imagePane.getChildren().add(view);
//        imagePane.getStyleClass().add("quiz-image");
//        view.setFitHeight(200);
//        view.setFitWidth(200);
//        
//        questionButtonBox = new VBox();
//            
//        questionButtonBox.getStyleClass().add("quiz-vbox");
//
//        root.setLeft(questionButtonBox);
//        root.setRight(imagePane);
//
//        return root;
//    }
//    
//    Label questionLabel;
//    VBox questionButtonBox;
//    Button nextQuestion;
//    ImageView view;
//    
//    void setQuestion(String question, String[] answers, List<Integer> correctIndices) {
//        questionLabel.setText(question);
//        questionButtonBox.getChildren().clear();
//        
//        for(int i = 0; i < answers.length; i++) {
//            questionButtonBox.getChildren().add(new ButtonPair((char)('A' + i) + "", nextQuestion, answers[i], correctIndices.contains(i)).box);
//        }
//    }
//    
//    void setQuestion(String question, String[] answers, List<Integer> correctIndices, Image image) {
//        view.setImage(image);
//        setQuestion(question, answers, correctIndices);
//    }
//    @Override
//    public void start(Stage primaryStage) {
//        
//        try {
//            Scene scene = new Scene(initialize(),1000,600);
//            scene.getStylesheets().add("application/test.css");
//            String[] answers = new String[] {
//                    "1",
//                    "12.33 pounds/inch",
//                    "No idea",
//                    "African or European?"
//            };
//            
//            ArrayList<Integer> correctIndex = new ArrayList<Integer>();
//            correctIndex.add(3);
//            correctIndex.add(1);
//            Image image = new Image("application/1st-pic.jpg");
//            setQuestion("How much wood would a woodchuck chuck if a woodchuck could chuck wood?", answers, correctIndex, image);
//            
//            primaryStage.setScene(scene);
//            primaryStage.show();
//        } catch(Exception e) {
//            e.printStackTrace();
//        }
//    }
//    
//    public static void main(String[] args) {
//        launch(args);
//    }
//}
//
