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

@WebServlet("/DeleteQuizServlet")
public class DeleteQuizServlet extends HttpServlet {

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


        // Get quiz ID
        String quizIdParameter =
                request.getParameter("quizId");

        if (quizIdParameter == null) {

            response.sendRedirect("ManageQuizServlet");
            return;
        }


        int quizId =
                Integer.parseInt(quizIdParameter);


        // Get quiz details
        QuizDAO quizDAO =
                new QuizDAO();

        Quiz quiz =
                quizDAO.getQuizById(quizId);


        if (quiz == null) {

            response.sendRedirect(
                    "ManageQuizServlet"
            );

            return;
        }


        /*
         * ADMIN can delete any quiz.
         *
         * FACULTY can delete only quizzes
         * created by themselves.
         */
        if ("ADMIN".equalsIgnoreCase(user.getRole())) {

            // Admin is allowed

        } else if ("FACULTY".equalsIgnoreCase(user.getRole())) {

            if (quiz.getCreatedBy() != user.getId()) {

                response.sendRedirect(
                        "ManageQuizServlet"
                );

                return;
            }

        } else {

            // Students are not allowed
            response.sendRedirect(
                    "dashboard.jsp"
            );

            return;
        }


        // Delete quiz
        boolean deleted =
                quizDAO.deleteQuiz(quizId);


        // Return to Manage Quizzes
        response.sendRedirect(
                "ManageQuizServlet"
        );
    }


    protected void doPost(HttpServletRequest request,
                           HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }
}