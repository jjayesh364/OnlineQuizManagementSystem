package controller;

import java.io.IOException;

import dao.QuizDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

@WebServlet("/DeleteQuizServlet")
public class DeleteQuizServlet extends HttpServlet {

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

        // Get quiz ID
        String quizIdParameter = request.getParameter("quizId");

        if (quizIdParameter == null) {
            response.sendRedirect("ManageQuizServlet");
            return;
        }

        int quizId = Integer.parseInt(quizIdParameter);

        // Delete quiz
        QuizDAO quizDAO = new QuizDAO();

        boolean deleted = quizDAO.deleteQuiz(quizId);

        // Return to Manage Quizzes
        response.sendRedirect("ManageQuizServlet");
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }
}