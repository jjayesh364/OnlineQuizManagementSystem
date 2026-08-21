package controller;

import java.io.IOException;
import java.util.List;

import dao.QuestionDAO;
import dao.QuizDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.Question;
import model.Quiz;
import model.User;

@WebServlet("/ViewQuestionsServlet")
public class ViewQuestionsServlet extends HttpServlet {

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

            response.sendRedirect(
                    "ManageQuizServlet"
            );

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
         * ADMIN can view questions from any quiz.
         *
         * FACULTY can view questions only
         * from their own quizzes.
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

            // Students cannot access quiz management
            response.sendRedirect(
                    "dashboard.jsp"
            );

            return;
        }


        // Get questions for this quiz
        QuestionDAO questionDAO =
                new QuestionDAO();

        List<Question> questions =
                questionDAO.getQuestionsByQuizId(
                        quizId
                );


        // Send data to JSP
        request.setAttribute(
                "questions",
                questions
        );

        request.setAttribute(
                "quizId",
                quizId
        );


        // Open questions page
        request.getRequestDispatcher(
                "view-questions.jsp"
        ).forward(
                request,
                response
        );
    }
}