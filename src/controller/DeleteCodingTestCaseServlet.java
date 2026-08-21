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
import model.Quiz;
import model.User;

@WebServlet("/DeleteCodingTestCaseServlet")
public class DeleteCodingTestCaseServlet extends HttpServlet {

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

        // ADMIN and FACULTY can delete test cases
        if (!"ADMIN".equalsIgnoreCase(user.getRole())
                && !"FACULTY".equalsIgnoreCase(user.getRole())) {

            response.sendRedirect("dashboard.jsp");
            return;
        }

        // Get test case ID
        String testCaseIdParameter =
                request.getParameter("testCaseId");

        // Get coding question ID
        String codingQuestionIdParameter =
                request.getParameter("codingQuestionId");

        if (testCaseIdParameter == null ||
            codingQuestionIdParameter == null) {

            response.sendRedirect("ManageQuizServlet");
            return;
        }

        int testCaseId =
                Integer.parseInt(testCaseIdParameter);

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

            response.sendRedirect("ManageQuizServlet");
            return;
        }

        // Get quiz ID from coding question
        int quizId =
                codingQuestion.getQuizId();

        // Find quiz
        QuizDAO quizDAO =
                new QuizDAO();

        Quiz quiz =
                quizDAO.getQuizById(quizId);

        if (quiz == null) {

            response.sendRedirect("ManageQuizServlet");
            return;
        }

        /*
         * ADMIN can delete test cases from
         * any coding question.
         *
         * FACULTY can delete test cases only
         * from their own quizzes.
         */
        if ("FACULTY".equalsIgnoreCase(user.getRole())) {

            if (quiz.getCreatedBy() != user.getId()) {

                response.sendRedirect(
                        "ManageQuizServlet"
                );
                return;
            }
        }

        // Delete test case
        CodingTestCaseDAO testCaseDAO =
                new CodingTestCaseDAO();

        boolean deleted =
                testCaseDAO.deleteTestCase(testCaseId);

        // Return to same test-case page
        response.sendRedirect(
                "ManageCodingTestCasesServlet?codingQuestionId="
                + codingQuestionId
        );
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }
}