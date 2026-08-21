<%@ page language="java"
         contentType="text/html; charset=UTF-8"
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

    String role = user.getRole();

    // Only ADMIN and FACULTY can manage quizzes
    if (!"ADMIN".equalsIgnoreCase(role)
            && !"FACULTY".equalsIgnoreCase(role)) {

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

    <title>Manage Quizzes</title>

    <link rel="stylesheet" href="style.css">

</head>

<body>

    <div class="container">


        <!-- Header -->

        <div class="card">

            <h1>Manage Quizzes</h1>

            <p>
                Create, view and manage your quizzes.
            </p>

        </div>


        <!-- Quiz List -->

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
    <a href="AddQuestionServlet?quizId=<%= quiz.getQuizId() %>">
        Add Questions
    </a>

    &nbsp; | &nbsp;

    <a href="ViewQuestionsServlet?quizId=<%= quiz.getQuizId() %>">
        View Questions
    </a>

    &nbsp; | &nbsp;

    <a href="CreateCodingQuestionServlet?quizId=<%= quiz.getQuizId() %>">
        Create Coding Question
    </a>
</p>


                        <p>

                            <a
                                href="DeleteQuizServlet?quizId=<%= quiz.getQuizId() %>"
                                class="danger"
                                onclick="return confirm('Are you sure you want to delete this quiz? All questions and results for this quiz will also be deleted.');">

                                Delete Quiz

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
                        There are currently no quizzes available.
                    </p>

                </div>

        <%
            }
        %>


        <!-- Navigation -->

        <div class="card">

            <p>

                <a href="CreateQuizServlet">
                    Create New Quiz
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