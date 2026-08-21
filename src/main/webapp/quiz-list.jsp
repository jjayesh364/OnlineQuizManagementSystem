<%@ page import="java.util.List" %>
<%@ page import="model.Quiz" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Available Quizzes</title>

    <link rel="stylesheet" href="style.css">
</head>

<body>

    <div class="container">

        <div class="card">

            <h1>Available Quizzes</h1>

            <p>Choose a quiz below to start your attempt.</p>

        </div>


        <%
            List quizzes = (List) request.getAttribute("quizzes");

            if (quizzes != null && !quizzes.isEmpty()) {

                for (Object obj : quizzes) {

                    Quiz quiz = (Quiz) obj;
        %>

                    <div class="card">

                        <h2>
                            <%= quiz.getTitle() %>
                        </h2>

                        <p>
                            <%= quiz.getDescription() %>
                        </p>

                        <p>
                            <strong>Duration:</strong>
                            <%= quiz.getDuration() %> minutes
                        </p>

                        <p>
                            <a href="StartQuizServlet?quizId=<%= quiz.getQuizId() %>">
                                Start Quiz
                            </a>
                        </p>

                    </div>

        <%
                }

            } else {
        %>

            <div class="card">

                <h2>No quizzes available</h2>

                <p>Please check back later for new quizzes.</p>

            </div>

        <%
            }
        %>


        <div class="card">

            <a href="dashboard.jsp">
                Back to Dashboard
            </a>

        </div>

    </div>

</body>

</html>