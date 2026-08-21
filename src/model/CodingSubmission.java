package model;

import java.sql.Timestamp;

public class CodingSubmission {

    private int submissionId;

    private int codingQuestionId;

    private int studentId;

    private String sourceCode;

    private String status;

    private int passedTests;

    private int totalTests;

    private int score;

    private Timestamp submittedAt;

    private String codingQuestionTitle;

    private String quizTitle;


    public CodingSubmission(
            int submissionId,
            int codingQuestionId,
            int studentId,
            String sourceCode,
            String status,
            int passedTests,
            int totalTests,
            int score,
            Timestamp submittedAt) {

        this.submissionId = submissionId;
        this.codingQuestionId = codingQuestionId;
        this.studentId = studentId;
        this.sourceCode = sourceCode;
        this.status = status;
        this.passedTests = passedTests;
        this.totalTests = totalTests;
        this.score = score;
        this.submittedAt = submittedAt;
    }


    public int getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(int submissionId) {
        this.submissionId = submissionId;
    }


    public int getCodingQuestionId() {
        return codingQuestionId;
    }

    public void setCodingQuestionId(int codingQuestionId) {
        this.codingQuestionId = codingQuestionId;
    }


    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }


    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public int getPassedTests() {
        return passedTests;
    }

    public void setPassedTests(int passedTests) {
        this.passedTests = passedTests;
    }


    public int getTotalTests() {
        return totalTests;
    }

    public void setTotalTests(int totalTests) {
        this.totalTests = totalTests;
    }


    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }


    public Timestamp getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(Timestamp submittedAt) {
        this.submittedAt = submittedAt;
    }


    public String getCodingQuestionTitle() {
        return codingQuestionTitle;
    }

    public void setCodingQuestionTitle(String codingQuestionTitle) {
        this.codingQuestionTitle = codingQuestionTitle;
    }


    public String getQuizTitle() {
        return quizTitle;
    }

    public void setQuizTitle(String quizTitle) {
        this.quizTitle = quizTitle;
    }
}