package controller;

import java.io.IOException;
import java.util.List;

import dao.QuizDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Quiz;
import model.User;

@WebServlet("/ManageQuestionServlet")
public class ManageQuestionServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Check login
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Check admin
        User user = (User) session.getAttribute("user");

        if (!"ADMIN".equalsIgnoreCase(user.getRole())) {
            response.sendRedirect("dashboard.jsp");
            return;
        }

        // Get all quizzes
        QuizDAO quizDAO = new QuizDAO();

        List<Quiz> quizzes = quizDAO.getAllQuizzes();

        // Send quizzes to JSP
        request.setAttribute("quizzes", quizzes);

        request.getRequestDispatcher("manage-questions.jsp")
               .forward(request, response);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }
}