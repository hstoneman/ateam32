// Team Project ATeam 32- Quiz Generator
// Created by: Anand Madathil, Mayukh Misra, Hayley Stoneman, Jake Schraufnagel, Shalini Bare

package application;

/**
 * QuestionADT which is a simple question interface
 * ATeam 32
 */
public interface QuestionADT {
  /**
   * returns the string data contained in meta-data
   * @return
   */
  public String getMetaData();
  
  /**
   * Returns the quetion text
   * @return
   */
  public String getQuestionText();
  
  /**
   * returns the topic
   * @return
   */
  public String getTopic();
  
  /**
   * returns the filepath to the image
   * @return
   */
  public String getImage();
  
  /**
   * returns the String array of choice text
   * @return
   */
  public String[] getChoices();
  
  /**
   * returns an int for which index int the chioce array is the correct answer
   * @return
   */
  public int getAnswer();
}
