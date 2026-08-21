<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="model.Result" %>
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

    List<Result> results =
        (List<Result>) request.getAttribute("results");
%>

<!DOCTYPE html>
<html>

<head>

    <meta charset="UTF-8">

    <title>Student Results</title>

    <link rel="stylesheet" href="style.css">

</head>

<body>

    <div class="container">

        <div class="card">

            <h1>Student Results</h1>

            <p>
                View quiz performance of all students.
            </p>

        </div>


        <%
            if (results != null && !results.isEmpty()) {

                for (Result result : results) {
        %>

                    <div class="card">

                        <h2>
                            <%= result.getQuizTitle() %>
                        </h2>

                        <p>
                            <strong>Student:</strong>
                            <%= result.getStudentName() %>
                        </p>

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

                    <h2>No Student Results</h2>

                    <p>
                        No student results found.
                    </p>

                </div>

        <%
            }
        %>


        <div class="card">

            <a href="admin-dashboard.jsp">
                Back to Admin Dashboard
            </a>

        </div>

    </div>

</body>

</html>