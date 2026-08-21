package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Result;
import util.DatabaseConnection;

public class ResultDAO {

    // Save quiz result
    public boolean saveResult(Result result) {

        String sql = "INSERT INTO results " +
                     "(user_id, quiz_id, score, total_questions, percentage) " +
                     "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, result.getUserId());
            statement.setInt(2, result.getQuizId());
            statement.setInt(3, result.getScore());
            statement.setInt(4, result.getTotalQuestions());
            statement.setDouble(5, result.getPercentage());

            int rows = statement.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    // Get results of a particular user
    public List<Result> getResultsByUserId(int userId) {

        String sql = "SELECT r.*, q.title " +
                     "FROM results r " +
                     "JOIN quizzes q ON r.quiz_id = q.quiz_id " +
                     "WHERE r.user_id = ? " +
                     "ORDER BY r.attempt_date DESC";

        List<Result> results = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Result result = new Result(
                    resultSet.getInt("result_id"),
                    resultSet.getInt("user_id"),
                    resultSet.getInt("quiz_id"),
                    resultSet.getInt("score"),
                    resultSet.getInt("total_questions"),
                    resultSet.getDouble("percentage"),
                    resultSet.getString("attempt_date")
                );

                result.setQuizTitle(resultSet.getString("title"));

                results.add(result);
            }

            return results;

        } catch (SQLException e) {
            e.printStackTrace();
            return results;
        }
    }


    // Get all student results
    public List<Result> getAllResults() {

        String sql = "SELECT r.*, q.title, u.name " +
                     "FROM results r " +
                     "JOIN quizzes q ON r.quiz_id = q.quiz_id " +
                     "JOIN users u ON r.user_id = u.id " +
                     "ORDER BY r.attempt_date DESC";

        List<Result> results = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Result result = new Result(
                    resultSet.getInt("result_id"),
                    resultSet.getInt("user_id"),
                    resultSet.getInt("quiz_id"),
                    resultSet.getInt("score"),
                    resultSet.getInt("total_questions"),
                    resultSet.getDouble("percentage"),
                    resultSet.getString("attempt_date")
                );

                result.setQuizTitle(resultSet.getString("title"));
                result.setStudentName(resultSet.getString("name"));

                results.add(result);
            }

            return results;

        } catch (SQLException e) {
            e.printStackTrace();
            return results;
        }
    }
}