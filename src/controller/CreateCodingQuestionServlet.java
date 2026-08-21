package controller;

import java.io.IOException;

import dao.CodingQuestionDAO;
import dao.QuizDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.CodingQuestion;
import model.Quiz;
import model.User;

@WebServlet("/CreateCodingQuestionServlet")
public class CreateCodingQuestionServlet extends HttpServlet {

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

        // Only ADMIN and FACULTY can create
        // coding questions
        if (!"ADMIN".equalsIgnoreCase(user.getRole())
                && !"FACULTY".equalsIgnoreCase(user.getRole())) {

            response.sendRedirect("dashboard.jsp");
            return;
        }

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

        // Check that quiz exists
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

        // FACULTY can only add coding questions
        // to their own quizzes.
        //
        // ADMIN can add to any quiz.
        if ("FACULTY".equalsIgnoreCase(user.getRole())) {

            if (quiz.getCreatedBy() != user.getId()) {

                response.sendRedirect(
                        "ManageQuizServlet"
                );
                return;
            }
        }

        // Send quiz information to JSP
        request.setAttribute(
                "quiz",
                quiz
        );

        // Open coding question form
        request.getRequestDispatcher(
                "create-coding-question.jsp"
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

        // Only ADMIN and FACULTY can create
        // coding questions
        if (!"ADMIN".equalsIgnoreCase(user.getRole())
                && !"FACULTY".equalsIgnoreCase(user.getRole())) {

            response.sendRedirect("dashboard.jsp");
            return;
        }

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

        // FACULTY can only modify their own quiz.
        // ADMIN can modify any quiz.
        if ("FACULTY".equalsIgnoreCase(user.getRole())) {

            if (quiz.getCreatedBy() != user.getId()) {

                response.sendRedirect(
                        "ManageQuizServlet"
                );
                return;
            }
        }

        // Get form data
        String title =
                request.getParameter("title");

        String problemStatement =
                request.getParameter("problemStatement");

        String inputDescription =
                request.getParameter("inputDescription");

        String outputDescription =
                request.getParameter("outputDescription");

        String constraints =
                request.getParameter("constraints");

        String sampleInput =
                request.getParameter("sampleInput");

        String sampleOutput =
                request.getParameter("sampleOutput");

        // Create coding question
        CodingQuestion codingQuestion =
                new CodingQuestion(
                        0,
                        quizId,
                        title,
                        problemStatement,
                        inputDescription,
                        outputDescription,
                        constraints,
                        sampleInput,
                        sampleOutput,
                        "JAVA"
                );

        // Save coding question
        CodingQuestionDAO codingQuestionDAO =
                new CodingQuestionDAO();

        boolean created =
                codingQuestionDAO.createCodingQuestion(
                        codingQuestion
                );

        if (created) {

            response.sendRedirect(
                    "ManageQuizServlet"
            );

        } else {

            response.getWriter().println(
                    "Failed to create coding question."
            );
        }
    }
}