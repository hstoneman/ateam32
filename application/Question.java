package application;

import javafx.scene.image.Image;

public class Question {
	private String metaData;
	private String text;
	private String topic;
	private Image image;
	private String[] choices;
	private int answer;
	
	/**
	 * Constructor without an image
	 */
	public Question(String metaData, String questionText, String topic, String[] choiceArray, int answer) {
		this.metaData = metaData;
		this.text = questionText;
		this.topic = topic;
		this.choices = choiceArray;
		this.answer = answer;
	}
	
	/**
	 * Constructor with an image
	 */
	public Question(String metaData, String questionText, String topic, Image image, String[] choiceArray, int answer) {
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
	
	public Image getImage() {
		return image;
	}
	
	public String[] choices() {
		return choices;
	}
	
	public int getAnswer() {
		return answer;
	}
}
