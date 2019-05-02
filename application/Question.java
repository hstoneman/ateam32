// Team Project ATeam 32- Quiz Generator
// Created by: Anand Madathil, Mayukh Misra, Hayley Stoneman, Jake Schraufnagel, Shalini Bare

package application;

/**
 * A class that stores data for questions
 * @author @author Hayley Stoneman, Jake Schraufnagel
 *
 */
public class Question implements QuestionADT{
    
    // instance fields used to store data for question
    private String metaData;
    private String text;
    private String topic;
    private String image;
    private String[] choices;
    private int answer;
    
    /**
     * Constructor without an image
     */
    public Question(String metaData, String questionText, String topic, String[] choiceArray, int answer) {
        // initialize instance fields
        this.metaData = metaData;
        this.text = questionText;
        this.topic = topic;
        this.choices = choiceArray;
        this.answer = answer;
    }
    
    /**
     * Constructor with an image
     */
    public Question(String metaData, String questionText, String topic, String image, String[] choiceArray, int answer) {
        // initialize instance fields
        this.metaData = metaData;
        this.text = questionText;
        this.topic = topic;
        this.image = image;
        this.choices = choiceArray;
        this.answer = answer;
    }
    
    public String getMetaData() {
        return metaData;
    }
    
    public String getQuestionText() {
        return text;
    }
    
    public String getTopic() {
        return topic;
    }
    
    public String getImage() {
        return image;
    }
    
    public String[] getChoices() {
        return choices;
    }
    
    public int getAnswer() {
        return answer;
    }
}
