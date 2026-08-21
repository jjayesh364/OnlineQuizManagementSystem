package model;

public class Quiz {
	private int quizId;
	private String title;
	private String description;
	private int duration;
	
	public Quiz(int quizId, String title, String description, int duration) {
		super();
		this.quizId = quizId;
		this.title = title;
		this.description = description;
		this.duration = duration;
	}
	
	public int getQuizId() {
		return quizId;
	}
	public void setQuizId(int quizId) {
		this.quizId = quizId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
}
