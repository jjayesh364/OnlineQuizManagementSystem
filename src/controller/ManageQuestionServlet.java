package controller;

import java.io.IOException;
import java.util.List;

import dao.QuizDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.Quiz;
import model.User;

@WebServlet("/ManageQuestionServlet")
public class ManageQuestionServlet extends HttpServlet {

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

        QuizDAO quizDAO =
                new QuizDAO();

        List<Quiz> quizzes;

        // ADMIN can manage questions for all quizzes
        if ("ADMIN".equalsIgnoreCase(user.getRole())) {

            quizzes =
                    quizDAO.getAllQuizzes();

        }

        // FACULTY can manage questions for their own quizzes
        else if ("FACULTY".equalsIgnoreCase(user.getRole())) {

            quizzes =
                    quizDAO.getQuizzesByCreatedBy(
                            user.getId()
                    );

        }

        // STUDENTS cannot manage questions
        else {

            response.sendRedirect("dashboard.jsp");
            return;
        }

        // Send quizzes to JSP
        request.setAttribute(
                "quizzes",
                quizzes
        );

        request.getRequestDispatcher(
                "manage-questions.jsp"
        ).forward(
                request,
                response
        );
    }


    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }
}