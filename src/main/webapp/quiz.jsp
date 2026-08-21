<%@ page import="java.util.List" %>
<%@ page import="model.Question" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Online Quiz</title>

    <link rel="stylesheet" href="style.css">
</head>

<body>

    <div class="container">

        <div class="card">

            <h1>Online Quiz</h1>

            <p>
                Select one answer for each question.
            </p>

        </div>


        <%
            List questions = (List) request.getAttribute("questions");

            Integer quizId = (Integer) request.getAttribute("quizId");

            if (questions != null && !questions.isEmpty()) {
        %>

            <form action="SubmitQuizServlet" method="post">

                <input type="hidden"
                       name="quizId"
                       value="<%= quizId %>">


                <%
                    int questionNumber = 1;

                    for (Object obj : questions) {

                        Question question = (Question) obj;
                %>

                        <div class="card">

                            <h2>
                                Question <%= questionNumber %>
                            </h2>

                            <h3>
                                <%= question.getQuestionText() %>
                            </h3>


                            <p>
                                <label>
                                    <input type="radio"
                                           name="question_<%= question.getQuestionId() %>"
                                           value="A"
                                           required>

                                    A. <%= question.getOptionA() %>
                                </label>
                            </p>


                            <p>
                                <label>
                                    <input type="radio"
                                           name="question_<%= question.getQuestionId() %>"
                                           value="B">

                                    B. <%= question.getOptionB() %>
                                </label>
                            </p>


                            <p>
                                <label>
                                    <input type="radio"
                                           name="question_<%= question.getQuestionId() %>"
                                           value="C">

                                    C. <%= question.getOptionC() %>
                                </label>
                            </p>


                            <p>
                                <label>
                                    <input type="radio"
                                           name="question_<%= question.getQuestionId() %>"
                                           value="D">

                                    D. <%= question.getOptionD() %>
                                </label>
                            </p>

                        </div>

                <%
                        questionNumber++;
                    }
                %>


                <div class="card">

                    <button type="submit">
                        Submit Quiz
                    </button>

                </div>

            </form>

        <%
            } else {
        %>

            <div class="card">

                <h2>No questions available</h2>

                <p>
                    There are no questions available for this quiz.
                </p>

            </div>

        <%
            }
        %>


        <div class="card">

            <a href="QuizListServlet">
                Back to Quizzes
            </a>

        </div>

    </div>

</body>

</html>