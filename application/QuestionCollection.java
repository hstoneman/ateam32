package application;


import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Class that contains and builds the collection of questions. It uses the ParseJSON class to 
 * build the overall question collection by reading the questions in a file. It also builds a 
 * collection of questions for the user based on the passed in topics desired. Questions can also
 * be added to the collection. 
 *
 */
public class QuestionCollection implements CollectionADT{
  private ArrayList<Question> questions;
  private ArrayList<String> topics;
  public ParseJSON parser;
  private ArrayList<Question> topicQuestions;
  private ArrayList<Question> randomQuestions;
  private Random rng;
  
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
    rng = new Random();
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
    System.out.println("Getting questions of topic(s): " + topics.toString());
    for (int i = 0; i < topics.size(); i++) {
      ArrayList<Question> possibleQuestions = getQuestionsByTopic(topics.get(i));
      topicQuestions.addAll(possibleQuestions);
    }
    System.out.println("Found " + topicQuestions.size() + " questions");
  }
  
  /**
   * method to randomly select a number of questions from the total question list compiled from the topics
   * @param n is the number of questions to pick
   */
  public void randomSelection(int n) {
	  if (n >= topicQuestions.size()) { // Returns all of the questions if the number desired is greater than the topic list size
		  randomQuestions.addAll(topicQuestions);
		  return;
	  }
	  ArrayList<Integer> current_questions = new ArrayList<Integer>();
	  while (current_questions.size() != n) {
		  int index = rng.nextInt(topicQuestions.size());
		  if (!current_questions.contains(index)) { // Checks if the question has already been selected
			  Question q = topicQuestions.get(index);
			  randomQuestions.add(q);
			  current_questions.add(index);
		  }
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
   * Writes a question to a JSONObject 
   * @param q
   * @return
   */
  @SuppressWarnings("unchecked")
  public JSONObject toJSONString(Question q) {
    JSONObject question = new JSONObject();
    question.put("meta-data", q.getMetaData());
    question.put("questionText", q.getQuestionText());
    question.put("topic", q.getTopic());
    question.put("image", q.getImage());
    JSONArray choices = new JSONArray();
    int correctChoice = q.getAnswer();
    for (int i = 0; i < q.getChoices().length; i++) {
      JSONObject option = new JSONObject();
      if (i == correctChoice) {
        option.put("isCorrect", "T");
      } else {
        option.put("isCorrect", "F");
      }
      option.put("choice", q.getChoices()[i]);
      choices.add(option);
    }
    question.put("choiceArray", choices);
    return question;
  }
  
  /**
   * method to save the question list to a json file
   * @throws IOException 
   */
  public void writeToJSON() throws IOException {
      BufferedWriter toJSON = new BufferedWriter(new FileWriter("questions.json"));
      toJSON.write("{ \"questionArray\": [");
      for (Question q : questions) {
          toJSON.write(toJSONString(q).toJSONString());
      }
      toJSON.write("]}");
      toJSON.close();
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
  
  /**
   * Adds new question to the collection from a json file
   * @throws ParseException 
   * @throws IOException 
   * @throws FileNotFoundException 
   */
  public void addQuestionsFromJSON(String filepath) throws FileNotFoundException, IOException, ParseException {
	  parser = new ParseJSON(filepath);
	  ArrayList<Question> new_questions = parser.parseFile();
	  questions.addAll(new_questions);
	  topics = parser.getTopicList(questions);
  }
}