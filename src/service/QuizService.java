package service;

import java.time.LocalDate;
import java.util.List;
import dao.ResultDAO;
import model.Question;
import model.Result;

public class QuizService {
	private ResultDAO resultDAO = new ResultDAO();
	
	public boolean submitQuiz(int userId, int quizId, List<Question> questions, List<String> answers) {
		int score = calculateScore(questions, answers);

		Result result = createResult(userId, quizId, score, questions.size());

		return resultDAO.saveResult(result);
	}
	
	public int calculateScore(List<Question> questions, List<String> answers) {
		int score = 0;

		for (int i = 0; i < questions.size(); i++) {

		    if (questions.get(i).getCorrectAnswer().equals(answers.get(i))) {
		        score++;
		    }
		}

		return score;
	}
	public double calculatePercentage(int score, int totalQuestions) {

	    if (totalQuestions == 0) {
	        return 0;
	    }

	    return (score * 100.0) / totalQuestions;
	}
	public Result createResult(int userId, int quizId, int score, int totalQuestions) {

	    double percentage = calculatePercentage(score, totalQuestions);

	    return new Result(
	        0,
	        userId,
	        quizId,
	        score,
	        totalQuestions,
	        percentage,
	        LocalDate.now().toString()
	    );
	}
}
