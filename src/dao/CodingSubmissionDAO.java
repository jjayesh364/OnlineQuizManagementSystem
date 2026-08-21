package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import model.CodingSubmission;
import util.DatabaseConnection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CodingSubmissionDAO {

    public boolean saveSubmission(
            int codingQuestionId,
            int studentId,
            String sourceCode,
            String status,
            int passedTests,
            int totalTests,
            int score) {

        String sql =
                "INSERT INTO coding_submissions " +
                "(coding_question_id, student_id, source_code, " +
                "status, passed_tests, total_tests, score) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection =
                     DatabaseConnection.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, codingQuestionId);
            statement.setInt(2, studentId);
            statement.setString(3, sourceCode);
            statement.setString(4, status);
            statement.setInt(5, passedTests);
            statement.setInt(6, totalTests);
            statement.setInt(7, score);

            int rows = statement.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }
 // Get all coding submissions of a student
    public List<CodingSubmission> getSubmissionsByStudentId(int studentId) {

        String sql =
                "SELECT s.*, " +
                "cq.title AS coding_title, " +
                "q.title AS quiz_title " +
                "FROM coding_submissions s " +
                "JOIN coding_questions cq " +
                "ON s.coding_question_id = cq.coding_question_id " +
                "JOIN quizzes q " +
                "ON cq.quiz_id = q.quiz_id " +
                "WHERE s.student_id = ? " +
                "ORDER BY s.submitted_at DESC";

        List<CodingSubmission> submissions =
                new ArrayList<>();

        try (Connection connection =
                     DatabaseConnection.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, studentId);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                while (resultSet.next()) {

                    CodingSubmission submission =
                            new CodingSubmission(
                                    resultSet.getInt("submission_id"),
                                    resultSet.getInt("coding_question_id"),
                                    resultSet.getInt("student_id"),
                                    resultSet.getString("source_code"),
                                    resultSet.getString("status"),
                                    resultSet.getInt("passed_tests"),
                                    resultSet.getInt("total_tests"),
                                    resultSet.getInt("score"),
                                    resultSet.getTimestamp("submitted_at")
                            );

                    submission.setCodingQuestionTitle(
                            resultSet.getString("coding_title")
                    );

                    submission.setQuizTitle(
                            resultSet.getString("quiz_title")
                    );

                    submissions.add(submission);
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return submissions;
    }	
}