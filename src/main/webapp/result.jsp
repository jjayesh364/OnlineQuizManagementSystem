<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Quiz Result</title>

    <link rel="stylesheet" href="style.css">
</head>

<body>

    <div class="container">

        <div class="card">

            <h1>Quiz Result</h1>

            <p>Here is your quiz performance.</p>

        </div>


        <div class="card">

            <h2>Your Score</h2>

            <p>
                <strong>Score:</strong>
                <%= request.getAttribute("score") %> /
                <%= request.getAttribute("totalQuestions") %>
            </p>

            <p>
                <strong>Percentage:</strong>
                <%= request.getAttribute("percentage") %>%
            </p>

        </div>


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