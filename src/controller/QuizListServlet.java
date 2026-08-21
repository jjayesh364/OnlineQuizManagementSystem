package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import dao.QuizDAO;
import model.Quiz;

@WebServlet("/QuizListServlet")
public class QuizListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        QuizDAO quizDAO = new QuizDAO();

        List<Quiz> quizzes = quizDAO.getAllQuizzes();

        request.setAttribute("quizzes", quizzes);

        request.getRequestDispatcher("quiz-list.jsp").forward(request, response);
    }
}