package application;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.json.simple.parser.ParseException;
import javafx.scene.image.Image;

/**
 * Class that contains and builds the collection of questions. It uses the ParseJSON class to 
 * build the overall question collection by reading the questions in a file. It also builds a 
 * collection of questions for the user based on the passed in topics desired. Questions can also
 * be added to the collection. 
 *
 */
public class QuestionCollection {
  private ArrayList<Question> questions;
  private ArrayList<String> topics;
  public ParseJSON parser;
  private ArrayList<Question> topicQuestions;
  private ArrayList<Question> randomQuestions;
  
  /**
   * constructor to initialize all fields
   * @param filepath
   */
  public QuestionCollection(String filepath) {
    parser = new ParseJSON(filepath);
    questions = new ArrayList<Question>();
    topics = new ArrayList<String>();
    topicQuestions = new ArrayList<Question>();
    randomQuestions = new ArrayList<Question>();
  }
  
  /**
   * method to build the question collection by reading a file 
   * @throws FileNotFoundException
   * @throws IOException
   * @throws ParseException
   */
  public void buildQuestionCollection() throws FileNotFoundException, IOException, ParseException {
    questions = parser.parseFile();
    topics = parser.getTopicList(questions);
  }
  
  /**
   * method to get a list of all topics available
   * @return
   */
  public ArrayList<String> getTopics() {
    return this.topics;
  }
  
  /**
   * private helper method to get all questions with a specific topic
   * @param topic
   * @return
   */
  private ArrayList<Question> getQuestionsByTopic(String topic) {
    ArrayList<Question> q = new ArrayList<Question>();
    for (int i = 0; i < questions.size(); i++) {
      if (questions.get(i).getTopic().equalsIgnoreCase(topic)) {
        q.add(questions.get(i));
      }
    }
    return q;
  }
  
  /**
   * method to build the questions for a quiz given an ArrayList of topics chosen by user
   * @param topics
   */
  public void buildQuizQuestions(ArrayList<String> topics) {
    for (int i = 0; i < topics.size(); i++) {
      ArrayList<Question> possibleQuestions = getQuestionsByTopic(topics.get(i));
      topicQuestions.addAll(possibleQuestions);
    }
  }
  
  /**
   * method to randomly select a number of questions from the total question list compiled from the topics
   * @param n is the number of questions to pick
   */
  public void randomSelection(int n) {
	  Random rng = new Random();
	  for (int i = 0; i < n; i++) {
		  int index = rng.nextInt(topicQuestions.size());
		  Question q = topicQuestions.get(index);
		  randomQuestions.add(q);
	  }
  }
  
  /**
   * method to get the topic questions for the quiz
   * @return
   */
  public ArrayList<Question> getTopicQuestions() {
    return this.topicQuestions;
  }
  
  /**
   * method to get a set number of random questions for the quiz
   * @return
   */
  public ArrayList<Question> getRandomQuestions() {
    return this.randomQuestions;
  }
  
  /**
   * method to manually add a new question to the question collection that has an image
   * @param metadata
   * @param questionText
   * @param topic
   * @param image
   * @param choiceArray
   * @param answer
   */
  public void addQuestion(String metadata, String questionText, String topic, String image, String[] choiceArray, int answer) {
    Question q = new Question(metadata, questionText, topic, image, choiceArray, answer);
    questions.add(q);
  }
  
  /**
   * method to manually add a new question to the collection without an image
   * @param metadata
   * @param questionText
   * @param topic
   * @param choiceArray
   * @param answer
   */
  public void addQuestion(String metadata, String questionText, String topic, String[] choiceArray, int answer) {
    Question q = new Question(metadata, questionText, topic, choiceArray, answer);
    questions.add(q);
  }
  
  /**
   * method to return the size of the collection of questions for the quiz that are of the specified
   * topic(s)
   * @return
   */
  public int getSize() {
    return topicQuestions.size();
  }
  
  /**
   * returns the total number of questions in the collection
   * @return
   */
  public int getTotalNumberQuestions() {
    return questions.size();
  }
}