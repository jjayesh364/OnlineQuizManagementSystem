package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.CodingQuestion;
import util.DatabaseConnection;

public class CodingQuestionDAO {

    // Create coding question
    public boolean createCodingQuestion(CodingQuestion question) {

        String sql = "INSERT INTO coding_questions "
                   + "(quiz_id, title, problem_statement, input_description, "
                   + "output_description, constraints, sample_input, "
                   + "sample_output, language) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, question.getQuizId());
            statement.setString(2, question.getTitle());
            statement.setString(3, question.getProblemStatement());
            statement.setString(4, question.getInputDescription());
            statement.setString(5, question.getOutputDescription());
            statement.setString(6, question.getConstraints());
            statement.setString(7, question.getSampleInput());
            statement.setString(8, question.getSampleOutput());
            statement.setString(9, question.getLanguage());

            int rows = statement.executeUpdate();

            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    // Get coding question by ID
    public CodingQuestion getCodingQuestionById(int codingQuestionId) {

        String sql = "SELECT * FROM coding_questions "
                   + "WHERE coding_question_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, codingQuestionId);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                if (resultSet.next()) {

                    return new CodingQuestion(
                            resultSet.getInt("coding_question_id"),
                            resultSet.getInt("quiz_id"),
                            resultSet.getString("title"),
                            resultSet.getString("problem_statement"),
                            resultSet.getString("input_description"),
                            resultSet.getString("output_description"),
                            resultSet.getString("constraints"),
                            resultSet.getString("sample_input"),
                            resultSet.getString("sample_output"),
                            resultSet.getString("language")
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    // Get all coding questions for a quiz
    public List<CodingQuestion> getCodingQuestionsByQuizId(int quizId) {

        String sql = "SELECT * FROM coding_questions "
                   + "WHERE quiz_id = ?";

        List<CodingQuestion> questions =
                new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, quizId);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                while (resultSet.next()) {

                    CodingQuestion question =
                            new CodingQuestion(
                                    resultSet.getInt("coding_question_id"),
                                    resultSet.getInt("quiz_id"),
                                    resultSet.getString("title"),
                                    resultSet.getString("problem_statement"),
                                    resultSet.getString("input_description"),
                                    resultSet.getString("output_description"),
                                    resultSet.getString("constraints"),
                                    resultSet.getString("sample_input"),
                                    resultSet.getString("sample_output"),
                                    resultSet.getString("language")
                            );

                    questions.add(question);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return questions;
    }


    // Delete coding question
    public boolean deleteCodingQuestion(int codingQuestionId) {

        String sql = "DELETE FROM coding_questions "
                   + "WHERE coding_question_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, codingQuestionId);

            int rows = statement.executeUpdate();

            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}