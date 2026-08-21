package model;

public class CodingTestCase {

    private int testCaseId;
    private int codingQuestionId;
    private String inputData;
    private String expectedOutput;
    private boolean sample;

    public CodingTestCase(
            int testCaseId,
            int codingQuestionId,
            String inputData,
            String expectedOutput,
            boolean sample) {

        this.testCaseId = testCaseId;
        this.codingQuestionId = codingQuestionId;
        this.inputData = inputData;
        this.expectedOutput = expectedOutput;
        this.sample = sample;
    }

    public int getTestCaseId() {
        return testCaseId;
    }

    public void setTestCaseId(int testCaseId) {
        this.testCaseId = testCaseId;
    }

    public int getCodingQuestionId() {
        return codingQuestionId;
    }

    public void setCodingQuestionId(int codingQuestionId) {
        this.codingQuestionId = codingQuestionId;
    }

    public String getInputData() {
        return inputData;
    }

    public void setInputData(String inputData) {
        this.inputData = inputData;
    }

    public String getExpectedOutput() {
        return expectedOutput;
    }

    public void setExpectedOutput(String expectedOutput) {
        this.expectedOutput = expectedOutput;
    }

    public boolean isSample() {
        return sample;
    }

    public void setSample(boolean sample) {
        this.sample = sample;
    }
}