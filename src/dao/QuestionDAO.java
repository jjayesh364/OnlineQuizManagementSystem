package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.Question;
import util.DatabaseConnection;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAO {

    // Add a question
    public boolean addQuestion(Question question) {

        String sql = "INSERT INTO questions " +
                     "(quiz_id, question_text, option_a, option_b, option_c, option_d, correct_answer) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, question.getQuizId());
            statement.setString(2, question.getQuestionText());
            statement.setString(3, question.getOptionA());
            statement.setString(4, question.getOptionB());
            statement.setString(5, question.getOptionC());
            statement.setString(6, question.getOptionD());
            statement.setString(7, question.getCorrectAnswer());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    // Get all questions for a particular quiz
    public List<Question> getQuestionsByQuizId(int quizId) {

        String sql = "SELECT * FROM questions WHERE quiz_id = ?";

        List<Question> questions = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, quizId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Question question = new Question(
                    resultSet.getInt("question_id"),
                    resultSet.getInt("quiz_id"),
                    resultSet.getString("question_text"),
                    resultSet.getString("option_a"),
                    resultSet.getString("option_b"),
                    resultSet.getString("option_c"),
                    resultSet.getString("option_d"),
                    resultSet.getString("correct_answer")
                );

                questions.add(question);
            }

            return questions;

        } catch (SQLException e) {
            e.printStackTrace();
            return questions;
        }
    }


    // Delete a question
    public boolean deleteQuestion(int questionId) {

        String sql = "DELETE FROM questions WHERE question_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, questionId);

            int rows = statement.executeUpdate();

            if (rows > 0) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    // Update a question
    public boolean updateQuestion(Question question) {

        String sql = "UPDATE questions SET " +
                     "question_text = ?, " +
                     "option_a = ?, " +
                     "option_b = ?, " +
                     "option_c = ?, " +
                     "option_d = ?, " +
                     "correct_answer = ? " +
                     "WHERE question_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, question.getQuestionText());
            statement.setString(2, question.getOptionA());
            statement.setString(3, question.getOptionB());
            statement.setString(4, question.getOptionC());
            statement.setString(5, question.getOptionD());
            statement.setString(6, question.getCorrectAnswer());
            statement.setInt(7, question.getQuestionId());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
 // Get a single question by question ID
    public Question getQuestionById(int questionId) {

        String sql = "SELECT * FROM questions WHERE question_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, questionId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                return new Question(
                    resultSet.getInt("question_id"),
                    resultSet.getInt("quiz_id"),
                    resultSet.getString("question_text"),
                    resultSet.getString("option_a"),
                    resultSet.getString("option_b"),
                    resultSet.getString("option_c"),
                    resultSet.getString("option_d"),
                    resultSet.getString("correct_answer")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}