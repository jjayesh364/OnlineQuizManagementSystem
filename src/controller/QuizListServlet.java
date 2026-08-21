package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

import dao.QuizDAO;
import model.Quiz;
import model.User;

@WebServlet("/QuizListServlet")
public class QuizListServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        // Check login
        HttpSession session =
                request.getSession(false);

        if (session == null ||
            session.getAttribute("user") == null) {

            response.sendRedirect("login.jsp");
            return;
        }

        // Get logged-in user
        User user =
                (User) session.getAttribute("user");

        // Only students can take quizzes
        if (!"STUDENT".equalsIgnoreCase(user.getRole())) {

            response.sendRedirect("dashboard.jsp");
            return;
        }

        // Get all available quizzes
        QuizDAO quizDAO =
                new QuizDAO();

        List<Quiz> quizzes =
                quizDAO.getAllQuizzes();

        // Send quizzes to JSP
        request.setAttribute(
                "quizzes",
                quizzes
        );

        // Open available quizzes page
        request.getRequestDispatcher(
                "quiz-list.jsp"
        ).forward(
                request,
                response
        );
    }
}