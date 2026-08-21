package controller;

import java.io.IOException;
import java.util.List;

import dao.QuestionDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Question;

@WebServlet("/StartQuizServlet")
public class StartQuizServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String quizIdParameter = request.getParameter("quizId");

        if (quizIdParameter == null) {
            response.sendRedirect("QuizListServlet");
            return;
        }

        int quizId = Integer.parseInt(quizIdParameter);

        QuestionDAO questionDAO = new QuestionDAO();

        List<Question> questions = questionDAO.getQuestionsByQuizId(quizId);

        request.setAttribute("questions", questions);
        request.setAttribute("quizId", quizId);

        request.getRequestDispatcher("quiz.jsp").forward(request, response);
    }
}