package controller;

import java.io.IOException;

import dao.QuestionDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

@WebServlet("/DeleteQuestionServlet")
public class DeleteQuestionServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Check login
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Check admin role
        User user = (User) session.getAttribute("user");

        if (!"ADMIN".equalsIgnoreCase(user.getRole())) {
            response.sendRedirect("dashboard.jsp");
            return;
        }

        // Get question ID
        String questionIdParameter = request.getParameter("questionId");
        String quizIdParameter = request.getParameter("quizId");

        if (questionIdParameter == null || quizIdParameter == null) {
            response.sendRedirect("ManageQuizServlet");
            return;
        }

        int questionId = Integer.parseInt(questionIdParameter);
        int quizId = Integer.parseInt(quizIdParameter);

        // Delete question
        QuestionDAO questionDAO = new QuestionDAO();

        boolean deleted = questionDAO.deleteQuestion(questionId);

        // Go back to the questions of the same quiz
        response.sendRedirect(
            "ViewQuestionsServlet?quizId=" + quizId
        );
    }
}