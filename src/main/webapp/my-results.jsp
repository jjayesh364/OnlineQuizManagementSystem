<%@ page import="java.util.List" %>
<%@ page import="model.Result" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>My Results</title>

    <link rel="stylesheet" href="style.css">
</head>

<body>

    <div class="container">

        <div class="card">

            <h1>My Results</h1>

            <p>
                View your previous quiz attempts and scores.
            </p>

        </div>


        <%
            List results = (List) request.getAttribute("results");

            if (results != null && !results.isEmpty()) {

                for (Object obj : results) {

                    Result result = (Result) obj;
        %>

                    <div class="card">

                        <h2>
                            <%= result.getQuizTitle() %>
                        </h2>

                        <p>
                            <strong>Score:</strong>
                            <%= result.getScore() %> /
                            <%= result.getTotalQuestions() %>
                        </p>

                        <p>
                            <strong>Percentage:</strong>
                            <%= result.getPercentage() %>%
                        </p>

                        <p>
                            <strong>Attempt Date:</strong>
                            <%= result.getAttemptDate() %>
                        </p>

                    </div>

        <%
                }

            } else {
        %>

            <div class="card">

                <h2>No Results Yet</h2>

                <p>
                    You have not attempted any quizzes yet.
                </p>

            </div>

        <%
            }
        %>


        <div class="card">

            <p>
                <a href="QuizListServlet">
                    Take Another Quiz
                </a>
            </p>

            <p>
                <a href="dashboard.jsp">
                    Back to Dashboard
                </a>
            </p>

        </div>

    </div>

</body>

</html>