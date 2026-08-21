package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dao.CodingQuestionDAO;
import dao.CodingTestCaseDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.CodingQuestion;
import model.CodingTestCase;
import model.User;

@WebServlet("/CodingTestServlet")
public class CodingTestServlet extends HttpServlet {

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

        // Only students can access coding tests
        User user =
                (User) session.getAttribute("user");

        if (!"STUDENT".equalsIgnoreCase(user.getRole())) {

            response.sendRedirect("dashboard.jsp");
            return;
        }

        // Get coding question ID
        String codingQuestionIdParameter =
                request.getParameter("codingQuestionId");

        if (codingQuestionIdParameter == null) {

            response.sendRedirect("QuizListServlet");
            return;
        }

        int codingQuestionId =
                Integer.parseInt(codingQuestionIdParameter);


        // Get coding question
        CodingQuestionDAO codingQuestionDAO =
                new CodingQuestionDAO();

        CodingQuestion codingQuestion =
                codingQuestionDAO.getCodingQuestionById(
                        codingQuestionId
                );

        if (codingQuestion == null) {

            response.sendRedirect("QuizListServlet");
            return;
        }


        // Get all test cases
        CodingTestCaseDAO testCaseDAO =
                new CodingTestCaseDAO();

        List<CodingTestCase> allTestCases =
                testCaseDAO.getTestCasesByQuestionId(
                        codingQuestionId
                );


        /*
         * Only sample test cases are sent
         * to the student.
         *
         * Hidden test cases remain on the server.
         */
        List<CodingTestCase> sampleTestCases =
                new ArrayList<>();

        for (CodingTestCase testCase : allTestCases) {

            if (testCase.isSample()) {

                sampleTestCases.add(testCase);
            }
        }


        // Send coding question to JSP
        request.setAttribute(
                "codingQuestion",
                codingQuestion
        );

        // Send ONLY sample test cases
        request.setAttribute(
                "sampleTestCases",
                sampleTestCases
        );


        // Open coding test page
        request.getRequestDispatcher(
                "coding-test.jsp"
        ).forward(
                request,
                response
        );
    }
}