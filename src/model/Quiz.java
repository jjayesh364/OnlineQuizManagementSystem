package model;

public class Quiz {

    private int quizId;

    private String title;

    private String description;

    private int duration;

    private int createdBy;


    // Existing constructor
    // Kept so existing code continues to work
    public Quiz(int quizId, String title, String description, int duration) {

        this.quizId = quizId;

        this.title = title;

        this.description = description;

        this.duration = duration;

    }


    // New constructor with faculty/user ID
    public Quiz(int quizId, String title, String description,
                int duration, int createdBy) {

        this.quizId = quizId;

        this.title = title;

        this.description = description;

        this.duration = duration;

        this.createdBy = createdBy;

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


    public int getCreatedBy() {

        return createdBy;

    }

    public void setCreatedBy(int createdBy) {

        this.createdBy = createdBy;

    }

}