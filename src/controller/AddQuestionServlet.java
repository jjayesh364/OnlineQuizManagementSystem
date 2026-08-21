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
        HttpSession session = request.getSession(false);

        if (session == null ||
            session.getAttribute("user") == null) {

            response.sendRedirect("login.jsp");
            return;
        }

        // Get logged-in user
        User user =
                (User) session.getAttribute("user");

        // DEBUG INFORMATION
        System.out.println("===== ADD QUESTION DEBUG =====");
        System.out.println("User ID: " + user.getId());
        System.out.println("User Name: " + user.getName());
        System.out.println("User Role: [" + user.getRole() + "]");
        System.out.println("==============================");

        // Get quiz ID
        String quizIdParameter =
                request.getParameter("quizId");

        System.out.println(
                "Quiz ID Parameter: " + quizIdParameter
        );

        if (quizIdParameter == null) {

            System.out.println(
                    "ERROR: quizId parameter is NULL"
            );

            response.sendRedirect(
                    "ManageQuizServlet"
            );
            return;
        }

        int quizId =
                Integer.parseInt(quizIdParameter);

        System.out.println(
                "Quiz ID: " + quizId
        );

        // Get quiz
        QuizDAO quizDAO =
                new QuizDAO();

        Quiz quiz =
                quizDAO.getQuizById(quizId);

        if (quiz == null) {

            System.out.println(
                    "ERROR: Quiz not found"
            );

            response.sendRedirect(
                    "ManageQuizServlet"
            );
            return;
        }

        // DEBUG QUIZ INFORMATION
        System.out.println(
                "Quiz Title: " + quiz.getTitle()
        );

        System.out.println(
                "Quiz Created By: " + quiz.getCreatedBy()
        );

        System.out.println(
                "Logged-in User ID: " + user.getId()
        );

        // Check authorization
        if ("ADMIN".equalsIgnoreCase(user.getRole())) {

            System.out.println(
                    "ACCESS GRANTED: ADMIN"
            );

        } else if ("FACULTY".equalsIgnoreCase(user.getRole())) {

            if (quiz.getCreatedBy() != user.getId()) {

                System.out.println(
                        "ACCESS DENIED: Faculty does not own this quiz"
                );

                response.sendRedirect(
                        "ManageQuizServlet"
                );
                return;
            }

            System.out.println(
                    "ACCESS GRANTED: Faculty owns quiz"
            );

        } else {

            System.out.println(
                    "ACCESS DENIED: User is not ADMIN or FACULTY"
            );

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

        // DEBUG INFORMATION
        System.out.println("===== ADD QUESTION POST DEBUG =====");
        System.out.println("User ID: " + user.getId());
        System.out.println("User Name: " + user.getName());
        System.out.println("User Role: [" + user.getRole() + "]");
        System.out.println("===================================");

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

        // Check authorization
        if ("ADMIN".equalsIgnoreCase(user.getRole())) {

            // ADMIN is allowed

        } else if ("FACULTY".equalsIgnoreCase(user.getRole())) {

            if (quiz.getCreatedBy() != user.getId()) {

                response.sendRedirect(
                        "ManageQuizServlet"
                );
                return;
            }

        } else {

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