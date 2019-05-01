package application;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Class for parsing a JSON file of questions given the file path
 *
 */
public class ParseJSON {
  private String filepath; // file path

  /**
   * constructor to set the filepath for the ParseJSON object
   * @param filepath
   */
  public ParseJSON(String filepath) {
    this.filepath = filepath;
  }

  /**
   * Public method to parse the file in the ParseJSON object, returns an ArrayList of Question
   * @return
   * @throws FileNotFoundException
   * @throws IOException
   * @throws ParseException
   */
  public ArrayList<Question> parseFile()
      throws FileNotFoundException, IOException, ParseException {
    ArrayList<Question> questions = new ArrayList<Question>();
    Object obj;

    obj = new JSONParser().parse(new FileReader(filepath));
    JSONObject jsonObj = (JSONObject) obj;
    JSONArray questionArray = (JSONArray) jsonObj.get("questionArray");

    // for each object in the array of questions
    for (int i = 0; i < questionArray.size(); i++) {
      JSONObject question = (JSONObject) questionArray.get(i); // get the object
      String metadata = (String) question.get("meta-data"); // get the metadata
      String questionText = (String) question.get("questionText"); // get the question text
      String topic = (String) question.get("topic"); // get the topic
      String imgText = (String) question.get("image"); // get the image text (may be "none")
      JSONArray choiceArray = (JSONArray) question.get("choiceArray"); // get the choice array obj
      String[] choices = new String[choiceArray.size()];
      int correctAnswer = 0;
      for (int j = 0; j < choiceArray.size(); j++) { // for each object in choice array
        JSONObject choice = (JSONObject) choiceArray.get(j);
        String choiceText = (String) choice.get("choice"); // get the choice text
        String isCorrect = (String) choice.get("isCorrect"); // get T/F for if is correct answer
        choices[j] = choiceText;
        if (isCorrect.equalsIgnoreCase("T")) { // update index of correct answer when T found
          correctAnswer = j;
        }
      }
      Question newQuestion; // create new question
      
        newQuestion = new Question(metadata, questionText, topic, imgText, choices, correctAnswer);
      
      questions.add(newQuestion); // add new question to the array of questions to be returned
    }
    return questions;
  }

  /**
   * method to get the list of topics from the questions
   * @param questions
   * @return
   */
  public ArrayList<String> getTopicList(ArrayList<Question> questions) {
    ArrayList<String> topics = new ArrayList<String>();
    for (int i = 0; i < questions.size(); i++) {
      if (!topics.contains(questions.get(i).getTopic())) {
        topics.add(questions.get(i).getTopic());
      }
    }
    Collections.sort(topics);
    return topics;
  }
}
