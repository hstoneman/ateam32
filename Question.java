package application;

public class Question implements QuestionADT {
  private String metaData;
  private String text;
  private String topic;
  private String image;
  private String[] choices;
  private int answer;

  /**
   * Constructor with an image
   */
  public Question(String metaData, String questionText, String topic, String image, String[] choiceArray, int answer) {
    if (metaData == null) {
      throw new IllegalArgumentException();
    }
    if (questionText == null) {
      throw new IllegalArgumentException();
    }
    if (topic == null) {
      throw new IllegalArgumentException();
    }
    if (image == null) {
      throw new IllegalArgumentException();
    }
    if (choiceArray == null) {
      throw new IllegalArgumentException();
    }
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
