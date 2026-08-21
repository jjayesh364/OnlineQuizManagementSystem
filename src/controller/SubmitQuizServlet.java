package controller;

import java.io.IOException;
import java.util.List;

import dao.QuestionDAO;
import dao.ResultDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Question;
import model.Result;
import model.User;

@WebServlet("/SubmitQuizServlet")
public class SubmitQuizServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get quiz ID
        int quizId = Integer.parseInt(request.getParameter("quizId"));

        // Get logged-in user from session
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");

        // Get questions for this quiz
        QuestionDAO questionDAO = new QuestionDAO();

        List<Question> questions = questionDAO.getQuestionsByQuizId(quizId);

        int score = 0;

        // Check each answer
        for (Question question : questions) {

            String selectedAnswer =
                    request.getParameter("question_" + question.getQuestionId());

            if (selectedAnswer != null &&
                selectedAnswer.equals(question.getCorrectAnswer())) {

                score++;
            }
        }

        // Calculate total questions
        int totalQuestions = questions.size();

        // Calculate percentage
        double percentage = 0;

        if (totalQuestions > 0) {
            percentage = ((double) score / totalQuestions) * 100;
        }

        // Create Result object
        Result result = new Result(
                0,
                user.getId(),
                quizId,
                score,
                totalQuestions,
                percentage,
                null
        );

        // Save result
        ResultDAO resultDAO = new ResultDAO();

        boolean saved = resultDAO.saveResult(result);

        if (saved) {

            request.setAttribute("score", score);
            request.setAttribute("totalQuestions", totalQuestions);
            request.setAttribute("percentage", percentage);

            request.getRequestDispatcher("result.jsp")
                   .forward(request, response);

        } else {

            response.getWriter().println("Error saving result.");
        }
    }
}