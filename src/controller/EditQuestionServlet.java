package controller;

import java.io.IOException;

import dao.QuestionDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Question;
import model.User;

@WebServlet("/EditQuestionServlet")
public class EditQuestionServlet extends HttpServlet {

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

        // Get question ID
        String questionIdParameter = request.getParameter("questionId");

        if (questionIdParameter == null) {
            response.sendRedirect("ManageQuizServlet");
            return;
        }

        int questionId = Integer.parseInt(questionIdParameter);

        // Find the question
        QuestionDAO questionDAO = new QuestionDAO();

        Question question = questionDAO.getQuestionById(questionId);

        if (question == null) {
            response.sendRedirect("ManageQuizServlet");
            return;
        }

        // Send question to JSP
        request.setAttribute("question", question);

        // Open edit form
        request.getRequestDispatcher("edit-question.jsp")
               .forward(request, response);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get form data
        int questionId = Integer.parseInt(request.getParameter("questionId"));
        int quizId = Integer.parseInt(request.getParameter("quizId"));

        String questionText = request.getParameter("questionText");
        String optionA = request.getParameter("optionA");
        String optionB = request.getParameter("optionB");
        String optionC = request.getParameter("optionC");
        String optionD = request.getParameter("optionD");
        String correctAnswer = request.getParameter("correctAnswer");

        // Create updated Question object
        Question question = new Question(
                questionId,
                quizId,
                questionText,
                optionA,
                optionB,
                optionC,
                optionD,
                correctAnswer
        );

        // Update question
        QuestionDAO questionDAO = new QuestionDAO();

        boolean updated = questionDAO.updateQuestion(question);

        if (updated) {

            response.sendRedirect(
                "ViewQuestionsServlet?quizId=" + quizId
            );

        } else {

            response.getWriter().println("Failed to update question.");
        }
    }
}