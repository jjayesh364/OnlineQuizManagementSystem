<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="model.Quiz" %>
<%@ page import="model.User" %>

<%
    User user = (User) session.getAttribute("user");

    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    if (!"ADMIN".equalsIgnoreCase(user.getRole())) {
        response.sendRedirect("dashboard.jsp");
        return;
    }

    List<Quiz> quizzes =
        (List<Quiz>) request.getAttribute("quizzes");
%>

<!DOCTYPE html>
<html>

<head>

    <meta charset="UTF-8">

    <title>Manage Questions</title>

    <link rel="stylesheet" href="style.css">

</head>

<body>

    <div class="container">

        <div class="card">

            <h1>Manage Questions</h1>

            <p>
                Select a quiz to add, edit, view or delete its questions.
            </p>

        </div>


        <%
            if (quizzes != null && !quizzes.isEmpty()) {

                for (Quiz quiz : quizzes) {
        %>

                    <div class="card">

                        <h2>
                            <%= quiz.getTitle() %>
                        </h2>

                        <p>
                            <strong>Description:</strong>
                            <%= quiz.getDescription() %>
                        </p>

                        <p>
                            <strong>Duration:</strong>
                            <%= quiz.getDuration() %> minutes
                        </p>

                        <hr>

                        <p>

                            <a href="ViewQuestionsServlet?quizId=<%= quiz.getQuizId() %>">
                                View Questions
                            </a>

                            &nbsp; | &nbsp;

                            <a href="AddQuestionServlet?quizId=<%= quiz.getQuizId() %>">
                                Add Questions
                            </a>

                        </p>

                    </div>

        <%
                }

            } else {
        %>

                <div class="card">

                    <h2>No Quizzes Found</h2>

                    <p>
                        Create a quiz first before managing questions.
                    </p>

                </div>

        <%
            }
        %>


        <div class="card">

            <p>
                <a href="admin-dashboard.jsp">
                    Back to Admin Dashboard
                </a>
            </p>

            <p>
                <a href="ManageQuizServlet">
                    Back to Manage Quizzes
                </a>
            </p>

        </div>

    </div>

</body>

</html>