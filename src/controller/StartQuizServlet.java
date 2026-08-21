package controller;

import java.io.IOException;
import java.util.List;

import dao.QuestionDAO;
import dao.QuizDAO;
import dao.CodingQuestionDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.Question;
import model.Quiz;
import model.CodingQuestion;
import model.User;

@WebServlet("/StartQuizServlet")
public class StartQuizServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        // ==========================================
        // 1. Check login
        // ==========================================

        HttpSession session =
                request.getSession(false);

        if (session == null ||
            session.getAttribute("user") == null) {

            response.sendRedirect("login.jsp");
            return;
        }

        // ==========================================
        // 2. Only STUDENTS can take quizzes
        // ==========================================

        User user =
                (User) session.getAttribute("user");

        if (!"STUDENT".equalsIgnoreCase(user.getRole())) {

            response.sendRedirect("dashboard.jsp");
            return;
        }

        // ==========================================
        // 3. Get quiz ID
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
        // 4. Get quiz details
        // ==========================================

        QuizDAO quizDAO =
                new QuizDAO();

        Quiz quiz =
                quizDAO.getQuizById(quizId);

        if (quiz == null) {

            response.sendRedirect("QuizListServlet");
            return;
        }

        // ==========================================
        // 5. Get MCQ questions
        // ==========================================

        QuestionDAO questionDAO =
                new QuestionDAO();

        List<Question> questions =
                questionDAO.getQuestionsByQuizId(
                        quizId
                );

        // ==========================================
        // 6. Get coding questions
        // ==========================================

        CodingQuestionDAO codingQuestionDAO =
                new CodingQuestionDAO();

        List<CodingQuestion> codingQuestions =
                codingQuestionDAO
                        .getCodingQuestionsByQuizId(quizId);
        System.out.println("=================================");
        System.out.println("START QUIZ DEBUG");
        System.out.println("Quiz ID = " + quizId);
        System.out.println("Coding Questions Count = "
                + codingQuestions.size());

        for (CodingQuestion cq : codingQuestions) {
            System.out.println(
                "Coding ID = " + cq.getCodingQuestionId()
                + " | Quiz ID = " + cq.getQuizId()
                + " | Title = " + cq.getTitle()
            );
        }

        System.out.println("=================================");

        // ==========================================
        // 7. Store quiz start time
        // ==========================================

        String startTimeKey =
                "quizStartTime_" + quizId;

        Object startTimeObject =
                session.getAttribute(startTimeKey);

        long startTime;

        if (startTimeObject == null) {

            startTime =
                    System.currentTimeMillis();

            session.setAttribute(
                    startTimeKey,
                    startTime
            );

        } else {

            startTime =
                    (Long) startTimeObject;
        }

        // ==========================================
        // 8. Calculate remaining time
        // ==========================================

        long currentTime =
                System.currentTimeMillis();

        long elapsedTime =
                currentTime - startTime;

        long allowedTime =
                quiz.getDuration() * 60L * 1000L;

        long remainingTime =
                allowedTime - elapsedTime;

        long remainingSeconds =
                remainingTime / 1000;

        if (remainingSeconds < 0) {
            remainingSeconds = 0;
        }

        // ==========================================
        // 9. Send MCQ questions to JSP
        // ==========================================

        request.setAttribute(
                "questions",
                questions
        );

        // ==========================================
        // 10. Send coding questions to JSP
        // ==========================================

        request.setAttribute(
                "codingQuestions",
                codingQuestions
        );

        // ==========================================
        // 11. Send quiz information
        // ==========================================

        request.setAttribute(
                "quizId",
                quizId
        );

        request.setAttribute(
                "duration",
                quiz.getDuration()
        );

        request.setAttribute(
                "remainingSeconds",
                remainingSeconds
        );

        // ==========================================
        // 12. Open quiz page
        // ==========================================

        request.getRequestDispatcher(
                "quiz.jsp"
        ).forward(
                request,
                response
        );
    }
}