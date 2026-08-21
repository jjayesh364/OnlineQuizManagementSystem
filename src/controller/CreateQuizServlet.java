package controller;

import java.io.IOException;

import dao.QuizDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.Quiz;
import model.User;

@WebServlet("/CreateQuizServlet")
public class CreateQuizServlet extends HttpServlet {

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

        // Check role
        User user =
                (User) session.getAttribute("user");

        // ADMIN and FACULTY can create quizzes
        if (!"ADMIN".equalsIgnoreCase(user.getRole())
                && !"FACULTY".equalsIgnoreCase(user.getRole())) {

            response.sendRedirect("dashboard.jsp");
            return;
        }

        request.getRequestDispatcher(
                "create-quiz.jsp"
        ).forward(
                request,
                response
        );
    }


    protected void doPost(HttpServletRequest request,
                           HttpServletResponse response)
            throws ServletException, IOException {

        // Get current session
        HttpSession session =
                request.getSession(false);

        // Check login
        if (session == null ||
            session.getAttribute("user") == null) {

            response.sendRedirect("login.jsp");
            return;
        }

        // Get logged-in user
        User user =
                (User) session.getAttribute("user");

        // ADMIN and FACULTY can create quizzes
        if (!"ADMIN".equalsIgnoreCase(user.getRole())
                && !"FACULTY".equalsIgnoreCase(user.getRole())) {

            response.sendRedirect("dashboard.jsp");
            return;
        }

        // Get form data
        String title =
                request.getParameter("title");

        String description =
                request.getParameter("description");

        int duration =
                Integer.parseInt(
                        request.getParameter("duration")
                );

        // Create quiz with creator's ID
        Quiz quiz =
                new Quiz(
                        0,
                        title,
                        description,
                        duration,
                        user.getId()
                );

        // Save quiz
        QuizDAO quizDAO =
                new QuizDAO();

        boolean created =
                quizDAO.createQuiz(quiz);

        if (created) {

            response.sendRedirect(
                    "ManageQuizServlet"
            );

        } else {

            response.getWriter().println(
                    "Failed to create quiz."
            );
        }
    }
}