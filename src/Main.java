import java.util.Arrays;
import java.util.List;

import dao.QuestionDAO;
import service.QuizService;
import model.Question;

public class Main {

    public static void main(String[] args) {

        QuestionDAO questionDAO = new QuestionDAO();

        // Get questions from quiz ID 1
        List<Question> questions = questionDAO.getQuestionsByQuizId(1);

        // Student's answers
        List<String> answers = Arrays.asList("B");

        QuizService quizService = new QuizService();

        // User ID = 1, Quiz ID = 1
        boolean success = quizService.submitQuiz(
            1,
            1,
            questions,
            answers
        );

        if (success) {
            System.out.println("Quiz submitted successfully!");
        } else {
            System.out.println("Quiz submission failed!");
        }
    }
}