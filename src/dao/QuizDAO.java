package dao;
import util.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import model.Quiz;
import java.util.List;
import java.util.ArrayList;
import java.sql.ResultSet;

public class QuizDAO {

	// Create a quiz
	public boolean createQuiz(Quiz quiz) {

	    String sql = "INSERT INTO quizzes " +
	                 "(title, description, duration, created_by) " +
	                 "VALUES (?, ?, ?, ?)";

	    try (Connection connection =
	                 DatabaseConnection.getConnection();

	         PreparedStatement statement =
	                 connection.prepareStatement(sql)) {


	        statement.setString(
	                1,
	                quiz.getTitle()
	        );


	        statement.setString(
	                2,
	                quiz.getDescription()
	        );


	        statement.setInt(
	                3,
	                quiz.getDuration()
	        );


	        statement.setInt(
	                4,
	                quiz.getCreatedBy()
	        );


	        int rows =
	                statement.executeUpdate();


	        return rows > 0;


	    } catch (SQLException e) {

	        e.printStackTrace();

	        return false;
	    }
	}


    // Get all quizzes
    public List<Quiz> getAllQuizzes() {

        String sql = "SELECT * FROM quizzes";

        List<Quiz> quizzes = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Quiz quiz = new Quiz(
                    resultSet.getInt("quiz_id"),
                    resultSet.getString("title"),
                    resultSet.getString("description"),
                    resultSet.getInt("duration")
                );

                quizzes.add(quiz);
            }

            return quizzes;

        } catch (SQLException e) {
            e.printStackTrace();
            return quizzes;
        }
    }
    
 // Get quizzes created by a specific faculty member
    public List<Quiz> getQuizzesByCreatedBy(int createdBy) {

        String sql = "SELECT * FROM quizzes WHERE created_by = ?";

        List<Quiz> quizzes = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, createdBy);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {

                    Quiz quiz = new Quiz(
                        resultSet.getInt("quiz_id"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getInt("duration"),
                        resultSet.getInt("created_by")
                    );

                    quizzes.add(quiz);
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return quizzes;
    }
    public Quiz getQuizById(int quizId) {
        String sql = "SELECT * FROM quizzes WHERE quiz_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, quizId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return new Quiz(
                        resultSet.getInt("quiz_id"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getInt("duration")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Delete a quiz and its related questions/results
    public boolean deleteQuiz(int quizId) {

        String deleteQuestions =
                "DELETE FROM questions WHERE quiz_id = ?";

        String deleteResults =
                "DELETE FROM results WHERE quiz_id = ?";

        String deleteQuiz =
                "DELETE FROM quizzes WHERE quiz_id = ?";

        Connection connection = null;

        try {

            connection = DatabaseConnection.getConnection();

            // Start transaction
            connection.setAutoCommit(false);

            // Delete questions
            try (PreparedStatement statement =
                    connection.prepareStatement(deleteQuestions)) {

                statement.setInt(1, quizId);
                statement.executeUpdate();
            }

            // Delete results
            try (PreparedStatement statement =
                    connection.prepareStatement(deleteResults)) {

                statement.setInt(1, quizId);
                statement.executeUpdate();
            }

            // Delete quiz
            int rows;

            try (PreparedStatement statement =
                    connection.prepareStatement(deleteQuiz)) {

                statement.setInt(1, quizId);
                rows = statement.executeUpdate();
            }

            // Commit everything
            connection.commit();

            return rows > 0;

        } catch (SQLException e) {

            e.printStackTrace();

            // Rollback if something fails
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackException) {
                    rollbackException.printStackTrace();
                }
            }

            return false;

        } finally {

            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException closeException) {
                    closeException.printStackTrace();
                }
            }
        }
    }
}