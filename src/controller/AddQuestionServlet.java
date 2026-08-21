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

@WebServlet("/AddQuestionServlet")
public class AddQuestionServlet extends HttpServlet {

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
         * ADMIN can add questions to any quiz.
         *
         * FACULTY can add questions only
         * to quizzes created by themselves.
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


        // Send quiz ID to JSP
        request.setAttribute(
                "quizId",
                quizId
        );


        // Show question form
        request.getRequestDispatcher(
                "add-question.jsp"
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


        // Check quiz ownership
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
         * ADMIN can add questions to any quiz.
         *
         * FACULTY can add questions only
         * to their own quizzes.
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


        // Create Question object
        Question question =
                new Question(
                        0,
                        quizId,
                        questionText,
                        optionA,
                        optionB,
                        optionC,
                        optionD,
                        correctAnswer
                );


        // Save question
        QuestionDAO questionDAO =
                new QuestionDAO();

        boolean added =
                questionDAO.addQuestion(question);


        if (added) {

            response.sendRedirect(
                    "ManageQuizServlet"
            );

        } else {

            response.getWriter().println(
                    "Failed to add question."
            );
        }
    }
}