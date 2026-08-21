package controller;

import java.io.IOException;
import java.util.List;

import dao.QuestionDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Question;
import model.User;

@WebServlet("/ViewQuestionsServlet")
public class ViewQuestionsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Check login
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Check admin role
        User user = (User) session.getAttribute("user");

        if (!"ADMIN".equalsIgnoreCase(user.getRole())) {
            response.sendRedirect("dashboard.jsp");
            return;
        }

        // Get quiz ID
        String quizIdParameter = request.getParameter("quizId");

        if (quizIdParameter == null) {
            response.sendRedirect("ManageQuizServlet");
            return;
        }

        int quizId = Integer.parseInt(quizIdParameter);

        // Get questions for this quiz
        QuestionDAO questionDAO = new QuestionDAO();

        List<Question> questions =
                questionDAO.getQuestionsByQuizId(quizId);

        // Send data to JSP
        request.setAttribute("questions", questions);
        request.setAttribute("quizId", quizId);

        // Open questions page
        request.getRequestDispatcher("view-questions.jsp")
               .forward(request, response);
    }
}