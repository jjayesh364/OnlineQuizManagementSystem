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

@WebServlet("/EditQuestionServlet")
public class EditQuestionServlet extends HttpServlet {

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


        // Find question
        QuestionDAO questionDAO =
                new QuestionDAO();

        Question question =
                questionDAO.getQuestionById(questionId);


        if (question == null) {

            response.sendRedirect(
                    "ManageQuizServlet"
            );

            return;
        }


        // Get the quiz containing this question
        int quizId =
                question.getQuizId();


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
         * ADMIN can edit any question.
         *
         * FACULTY can edit questions only
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

            // Students cannot edit questions
            response.sendRedirect(
                    "dashboard.jsp"
            );

            return;
        }


        // Send question to JSP
        request.setAttribute(
                "question",
                question
        );


        // Open edit form
        request.getRequestDispatcher(
                "edit-question.jsp"
        ).forward(
                request,
                response
        );
    }


    protected void doPost(HttpServletRequest request,
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


        // Find existing question
        QuestionDAO questionDAO =
                new QuestionDAO();

        Question existingQuestion =
                questionDAO.getQuestionById(
                        questionId
                );


        if (existingQuestion == null) {

            response.sendRedirect(
                    "ManageQuizServlet"
            );

            return;
        }


        // Get quiz ID from existing question
        int quizId =
                existingQuestion.getQuizId();


        // Get quiz
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
         * ADMIN can edit any question.
         *
         * FACULTY can edit questions only
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

            // Students cannot edit questions
            response.sendRedirect(
                    "dashboard.jsp"
            );

            return;
        }


        // Get form data
        String questionText =
                request.getParameter("questionText");

        String optionA =
                request.getParameter("optionA");

        String optionB =
                request.getParameter("optionB");

        String optionC =
                request.getParameter("optionC");

        String optionD =
                request.getParameter("optionD");

        String correctAnswer =
                request.getParameter("correctAnswer");


        /*
         * IMPORTANT:
         * Use the quizId from the existing database
         * question instead of trusting the quizId
         * sent by the browser.
         */
        Question question =
                new Question(
                        questionId,
                        quizId,
                        questionText,
                        optionA,
                        optionB,
                        optionC,
                        optionD,
                        correctAnswer
                );


        // Update question
        boolean updated =
                questionDAO.updateQuestion(
                        question
                );


        if (updated) {

            response.sendRedirect(
                    "ViewQuestionsServlet?quizId="
                    + quizId
            );

        } else {

            response.getWriter().println(
                    "Failed to update question."
            );
        }
    }
}