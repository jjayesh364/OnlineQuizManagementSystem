package controller;

import java.io.IOException;
import java.util.List;

import dao.CodingQuestionDAO;
import dao.CodingTestCaseDAO;
import dao.QuizDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.CodingQuestion;
import model.CodingTestCase;
import model.Quiz;
import model.User;

@WebServlet("/ManageCodingTestCasesServlet")
public class ManageCodingTestCasesServlet extends HttpServlet {

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

        // ADMIN and FACULTY can manage test cases
        if (!"ADMIN".equalsIgnoreCase(user.getRole())
                && !"FACULTY".equalsIgnoreCase(user.getRole())) {

            response.sendRedirect("dashboard.jsp");
            return;
        }

        // Get coding question ID
        String questionIdParameter =
                request.getParameter("codingQuestionId");

        if (questionIdParameter == null) {

            response.sendRedirect(
                    "ManageQuizServlet"
            );
            return;
        }

        int codingQuestionId =
                Integer.parseInt(questionIdParameter);

        // Get coding question
        CodingQuestionDAO codingQuestionDAO =
                new CodingQuestionDAO();

        CodingQuestion codingQuestion =
                codingQuestionDAO.getCodingQuestionById(
                        codingQuestionId
                );

        if (codingQuestion == null) {

            response.sendRedirect(
                    "ManageQuizServlet"
            );
            return;
        }

        // Get quiz containing this coding question
        int quizId =
                codingQuestion.getQuizId();

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
         * ADMIN can manage test cases for any quiz.
         *
         * FACULTY can manage test cases only
         * for coding questions in their own quizzes.
         */
        if ("FACULTY".equalsIgnoreCase(user.getRole())) {

            if (quiz.getCreatedBy() != user.getId()) {

                response.sendRedirect(
                        "ManageQuizServlet"
                );
                return;
            }
        }

        // Get all test cases
        CodingTestCaseDAO testCaseDAO =
                new CodingTestCaseDAO();

        List<CodingTestCase> testCases =
                testCaseDAO.getTestCasesByQuestionId(
                        codingQuestionId
                );

        // Send data to JSP
        request.setAttribute(
                "codingQuestion",
                codingQuestion
        );

        request.setAttribute(
                "testCases",
                testCases
        );

        // Open test-case management page
        request.getRequestDispatcher(
                "manage-coding-test-cases.jsp"
        ).forward(
                request,
                response
        );
    }
}