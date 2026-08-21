package controller;

import java.io.IOException;
import java.util.List;

import dao.QuestionDAO;
import dao.QuizDAO;
import dao.ResultDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.Question;
import model.Quiz;
import model.Result;
import model.User;

@WebServlet("/SubmitQuizServlet")
public class SubmitQuizServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        // ==========================================
        // 1. Check login
        // ==========================================

        HttpSession session = request.getSession(false);

        if (session == null ||
            session.getAttribute("user") == null) {

            response.sendRedirect("login.jsp");
            return;
        }

        // ==========================================
        // 2. Only STUDENTS can submit MCQ tests
        // ==========================================

        User user = (User) session.getAttribute("user");

        if (!"STUDENT".equalsIgnoreCase(user.getRole())) {

            response.sendRedirect("dashboard.jsp");
            return;
        }

        // ==========================================
        // 3. Get Quiz ID
        // ==========================================

        String quizIdParameter =
                request.getParameter("quizId");

        if (quizIdParameter == null) {

            response.sendRedirect("QuizListServlet");
            return;
        }

        int quizId =
                Integer.parseInt(quizIdParameter);

        // ==========================================
        // 4. Get Quiz
        // ==========================================

        QuizDAO quizDAO = new QuizDAO();

        Quiz quiz =
                quizDAO.getQuizById(quizId);

        if (quiz == null) {

            response.sendRedirect("QuizListServlet");
            return;
        }

        // ==========================================
        // 5. Get quiz start time
        // ==========================================

        String startTimeKey =
                "quizStartTime_" + quizId;

        Object startTimeObject =
                session.getAttribute(startTimeKey);

        if (startTimeObject == null) {

            response.sendRedirect("QuizListServlet");
            return;
        }

        long startTime =
                (Long) startTimeObject;

        // ==========================================
        // 6. Check time
        // ==========================================

        long currentTime =
                System.currentTimeMillis();

        long elapsedTime =
                currentTime - startTime;

        long allowedTime =
                quiz.getDuration() * 60L * 1000L;

        boolean timeExpired =
                elapsedTime > allowedTime;

        // ==========================================
        // 7. Get MCQ questions
        // ==========================================

        QuestionDAO questionDAO =
                new QuestionDAO();

        List<Question> questions =
                questionDAO.getQuestionsByQuizId(quizId);

        // ==========================================
        // 8. Calculate MCQ score
        // ==========================================

        int score = 0;

        for (Question question : questions) {

            String selectedAnswer =
                    request.getParameter(
                            "question_" +
                            question.getQuestionId()
                    );

            if (selectedAnswer != null &&
                selectedAnswer.equals(
                        question.getCorrectAnswer())) {

                score++;
            }
        }

        // ==========================================
        // 9. Total questions
        // ==========================================

        int totalQuestions =
                questions.size();

        // ==========================================
        // 10. Calculate percentage
        // ==========================================

        double percentage = 0;

        if (totalQuestions > 0) {

            percentage =
                    ((double) score /
                    totalQuestions) * 100;
        }

        // ==========================================
        // 11. Create MCQ result
        // ==========================================

        Result result = new Result(
                0,
                user.getId(),
                quizId,
                score,
                totalQuestions,
                percentage,
                null
        );

        // ==========================================
        // 12. Save result
        // ==========================================

        ResultDAO resultDAO =
                new ResultDAO();

        boolean saved =
                resultDAO.saveResult(result);

        // ==========================================
        // 13. Remove quiz timer
        // ==========================================

        session.removeAttribute(startTimeKey);

        // ==========================================
        // 14. Show result
        // ==========================================

        if (saved) {

            request.setAttribute(
                    "score",
                    score
            );

            request.setAttribute(
                    "totalQuestions",
                    totalQuestions
            );

            request.setAttribute(
                    "percentage",
                    percentage
            );

            request.setAttribute(
                    "timeExpired",
                    timeExpired
            );

            request.getRequestDispatcher(
                    "result.jsp"
            ).forward(
                    request,
                    response
            );

        } else {

            response.getWriter().println(
                    "Error saving result."
            );
        }
    }
}