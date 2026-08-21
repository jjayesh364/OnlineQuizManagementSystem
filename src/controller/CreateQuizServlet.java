package controller;

import java.io.IOException;

import dao.QuizDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Quiz;

@WebServlet("/CreateQuizServlet")
public class CreateQuizServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("create-quiz.jsp")
               .forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String title = request.getParameter("title");
        String description = request.getParameter("description");
        int duration = Integer.parseInt(request.getParameter("duration"));

        Quiz quiz = new Quiz(
                0,
                title,
                description,
                duration
        );

        QuizDAO quizDAO = new QuizDAO();

        boolean created = quizDAO.createQuiz(quiz);

        if (created) {
            response.sendRedirect("ManageQuizServlet");
        } else {
            response.getWriter().println("Failed to create quiz.");
        }
    }
}