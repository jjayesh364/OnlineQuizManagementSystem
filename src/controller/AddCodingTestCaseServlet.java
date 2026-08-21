package controller;

import java.io.IOException;

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

@WebServlet("/AddCodingTestCaseServlet")
public class AddCodingTestCaseServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

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

        // ADMIN and FACULTY can add test cases
        if (!"ADMIN".equalsIgnoreCase(user.getRole())
                && !"FACULTY".equalsIgnoreCase(user.getRole())) {

            response.sendRedirect("dashboard.jsp");
            return;
        }

        // Get coding question ID
        String codingQuestionIdParameter =
                request.getParameter("codingQuestionId");

        if (codingQuestionIdParameter == null) {

            response.sendRedirect(
                    "ManageQuizServlet"
            );
            return;
        }

        int codingQuestionId =
                Integer.parseInt(codingQuestionIdParameter);

        // Find coding question
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

        // Get quiz ID from the coding question
        int quizId =
                codingQuestion.getQuizId();

        // Find quiz
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
         * ADMIN can add test cases to any
         * coding question.
         *
         * FACULTY can add test cases only
         * to coding questions in their own quizzes.
         */
        if ("FACULTY".equalsIgnoreCase(user.getRole())) {

            if (quiz.getCreatedBy() != user.getId()) {

                response.sendRedirect(
                        "ManageQuizServlet"
                );
                return;
            }
        }

        // Get form data
        String inputData =
                request.getParameter("inputData");

        String expectedOutput =
                request.getParameter("expectedOutput");

        boolean isSample =
                "true".equals(
                        request.getParameter("isSample")
                );

        // Create test case
        CodingTestCase testCase =
                new CodingTestCase(
                        0,
                        codingQuestionId,
                        inputData,
                        expectedOutput,
                        isSample
                );

        // Save test case
        CodingTestCaseDAO testCaseDAO =
                new CodingTestCaseDAO();

        boolean added =
                testCaseDAO.addTestCase(testCase);

        if (added) {

            response.sendRedirect(
                    "ManageCodingTestCasesServlet?codingQuestionId="
                    + codingQuestionId
            );

        } else {

            response.getWriter().println(
                    "Failed to add test case."
            );
        }
    }
}