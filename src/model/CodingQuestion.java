package model;

public class CodingQuestion {

    private int codingQuestionId;
    private int quizId;
    private String title;
    private String problemStatement;
    private String inputDescription;
    private String outputDescription;
    private String constraints;
    private String sampleInput;
    private String sampleOutput;
    private String language;

    public CodingQuestion(
            int codingQuestionId,
            int quizId,
            String title,
            String problemStatement,
            String inputDescription,
            String outputDescription,
            String constraints,
            String sampleInput,
            String sampleOutput,
            String language) {

        this.codingQuestionId = codingQuestionId;
        this.quizId = quizId;
        this.title = title;
        this.problemStatement = problemStatement;
        this.inputDescription = inputDescription;
        this.outputDescription = outputDescription;
        this.constraints = constraints;
        this.sampleInput = sampleInput;
        this.sampleOutput = sampleOutput;
        this.language = language;
    }

    public int getCodingQuestionId() {
        return codingQuestionId;
    }

    public void setCodingQuestionId(int codingQuestionId) {
        this.codingQuestionId = codingQuestionId;
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

    public String getProblemStatement() {
        return problemStatement;
    }

    public void setProblemStatement(String problemStatement) {
        this.problemStatement = problemStatement;
    }

    public String getInputDescription() {
        return inputDescription;
    }

    public void setInputDescription(String inputDescription) {
        this.inputDescription = inputDescription;
    }

    public String getOutputDescription() {
        return outputDescription;
    }

    public void setOutputDescription(String outputDescription) {
        this.outputDescription = outputDescription;
    }

    public String getConstraints() {
        return constraints;
    }

    public void setConstraints(String constraints) {
        this.constraints = constraints;
    }

    public String getSampleInput() {
        return sampleInput;
    }

    public void setSampleInput(String sampleInput) {
        this.sampleInput = sampleInput;
    }

    public String getSampleOutput() {
        return sampleOutput;
    }

    public void setSampleOutput(String sampleOutput) {
        this.sampleOutput = sampleOutput;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}