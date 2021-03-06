// Team Project ATeam 32- Quiz Generator
// Created by: Anand Madathil, Mayukh Misra, Hayley Stoneman, Jake Schraufnagel, Shalini Bare

package application;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import org.json.simple.parser.ParseException;

/**
 * Interface for the question collection which can read questions in from a file,
 * add new quetions, and manipulate them by topic
 * ATeam 32
 *
 */
public interface CollectionADT {
  
  /**
   * Returns the list of topics
   * @return
   */
  public ArrayList<String> getTopics();
  
  /**
   * builds a collection of questions that are only of the passed in topic(s)
   * @param topics
   */
  public void buildQuizQuestions(ArrayList<String> topics);
  
  /**
   * gets a random selection of n questions from the questions that are of desired topics
   * @param n
   */
  public void randomSelection(int n);
  
  /**
   * returns the questions that are of the desired topic
   * @return
   */
  public ArrayList<QuestionADT> getTopicQuestions();
  
  /**
   * returns the list of n questions for the quiz
   * @return
   */
  public ArrayList<QuestionADT> getRandomQuestions();
  
  /**
   * method to add a new question to the collection with an image
   * @param metadata
   * @param questionText
   * @param topic
   * @param image
   * @param choiceArray
   * @param answer
   */
  public void addQuestion(String metadata, String questionText, String topic, String image, String[] choiceArray, int answer);

  /**
   * method to save all questions in question bank to a JSON file
   * @throws IOException
   */
  public void writeToJSON() throws IOException;
  
  /**
   * returns the number of questions of the specified topic(s)
   * @return
   */
  public int getSize();
  
  /**
   * returns total size of question collection
   * @return
   */
  public int getTotalNumberQuestions();
  
  /**
   * Add questions via a JSON file whose filepath is passed in
   * @param filePath path to json file
   * @throws various exceptions based on input errors
   */
  public void addQuestionsFromJSON(String filepath) throws FileNotFoundException, IOException, ParseException ;

}
