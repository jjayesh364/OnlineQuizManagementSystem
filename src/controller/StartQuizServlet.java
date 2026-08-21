package controller;

import java.io.IOException;
import java.util.List;

import dao.QuestionDAO;
import dao.QuizDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Question;
import model.Quiz;
import model.User;

@WebServlet("/StartQuizServlet")
public class StartQuizServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        // Check logged-in user
        HttpSession session = request.getSession(false);

        if (session == null ||
            session.getAttribute("user") == null) {

            response.sendRedirect("login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");

        String quizIdParameter =
                request.getParameter("quizId");

        if (quizIdParameter == null) {
            response.sendRedirect("QuizListServlet");
            return;
        }

        int quizId =
                Integer.parseInt(quizIdParameter);


        // Get quiz details
        QuizDAO quizDAO = new QuizDAO();

        Quiz quiz =
                quizDAO.getQuizById(quizId);

        if (quiz == null) {
            response.sendRedirect("QuizListServlet");
            return;
        }


        // Get questions
        QuestionDAO questionDAO =
                new QuestionDAO();

        List<Question> questions =
                questionDAO.getQuestionsByQuizId(quizId);


        /*
         * Store quiz start time.
         *
         * If it already exists, don't reset it.
         */
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


        /*
         * Calculate how much time is actually
         * remaining.
         */
        long currentTime =
                System.currentTimeMillis();

        long elapsedTime =
                currentTime - startTime;


        long allowedTime =
                quiz.getDuration() * 60L * 1000L;


        long remainingTime =
                allowedTime - elapsedTime;


        // Convert milliseconds to seconds
        long remainingSeconds =
                remainingTime / 1000;


        // Never send a negative value
        if (remainingSeconds < 0) {
            remainingSeconds = 0;
        }


        // Send data to quiz.jsp
        request.setAttribute(
                "questions",
                questions
        );

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


        request.getRequestDispatcher(
                "quiz.jsp"
        ).forward(
                request,
                response
        );
    }
}