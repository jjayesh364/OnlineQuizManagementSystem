package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.CodingTestCase;
import util.DatabaseConnection;

public class CodingTestCaseDAO {

    // Add a test case
    public boolean addTestCase(CodingTestCase testCase) {

        String sql = "INSERT INTO coding_test_cases "
                   + "(coding_question_id, input_data, expected_output, is_sample) "
                   + "VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, testCase.getCodingQuestionId());
            statement.setString(2, testCase.getInputData());
            statement.setString(3, testCase.getExpectedOutput());
            statement.setBoolean(4, testCase.isSample());

            int rows = statement.executeUpdate();

            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    // Get all test cases for a coding question
    public List<CodingTestCase> getTestCasesByQuestionId(
            int codingQuestionId) {

        String sql = "SELECT * FROM coding_test_cases "
                   + "WHERE coding_question_id = ?";

        List<CodingTestCase> testCases =
                new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, codingQuestionId);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                while (resultSet.next()) {

                    CodingTestCase testCase =
                            new CodingTestCase(
                                    resultSet.getInt("test_case_id"),
                                    resultSet.getInt("coding_question_id"),
                                    resultSet.getString("input_data"),
                                    resultSet.getString("expected_output"),
                                    resultSet.getBoolean("is_sample")
                            );

                    testCases.add(testCase);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return testCases;
    }


    // Delete a test case
    public boolean deleteTestCase(int testCaseId) {

        String sql = "DELETE FROM coding_test_cases "
                   + "WHERE test_case_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, testCaseId);

            int rows = statement.executeUpdate();

            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}