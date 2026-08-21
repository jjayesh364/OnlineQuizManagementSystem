package controller;

import java.io.IOException;

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

@WebServlet("/DeleteQuestionServlet")
public class DeleteQuestionServlet extends HttpServlet {

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


        // Get question ID
        String questionIdParameter =
                request.getParameter("questionId");

        if (questionIdParameter == null) {

            response.sendRedirect(
                    "ManageQuizServlet"
            );

            return;
        }


        int questionId =
                Integer.parseInt(questionIdParameter);


        // Find the question
        QuestionDAO questionDAO =
                new QuestionDAO();

        Question question =
                questionDAO.getQuestionById(
                        questionId
                );


        if (question == null) {

            response.sendRedirect(
                    "ManageQuizServlet"
            );

            return;
        }


        /*
         * Get the quiz ID from the question
         * stored in the database.
         *
         * We don't trust the quizId sent by
         * the browser.
         */
        int quizId =
                question.getQuizId();


        // Find the quiz
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
         * ADMIN can delete questions from
         * any quiz.
         *
         * FACULTY can delete questions only
         * from quizzes created by themselves.
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

            // Students cannot delete questions
            response.sendRedirect(
                    "dashboard.jsp"
            );

            return;
        }


        // Delete question
        boolean deleted =
                questionDAO.deleteQuestion(
                        questionId
                );


        // Return to the questions page
        response.sendRedirect(
                "ViewQuestionsServlet?quizId="
                + quizId
        );
    }


    protected void doPost(HttpServletRequest request,
                           HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }
}