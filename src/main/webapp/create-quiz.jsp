<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

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
%>

<!DOCTYPE html>
<html>

<head>

    <meta charset="UTF-8">

    <title>Create Quiz</title>

    <link rel="stylesheet" href="style.css">

</head>

<body>

    <div class="container">

        <div class="card">

            <h1>Create New Quiz</h1>

            <p>
                Create a new quiz for students.
            </p>

        </div>


        <div class="card">

            <form action="CreateQuizServlet" method="post">

                <label>
                    <strong>Quiz Title:</strong>
                </label>

                <br><br>

                <input
                    type="text"
                    name="title"
                    placeholder="Enter quiz title"
                    required>


                <br><br>


                <label>
                    <strong>Description:</strong>
                </label>

                <br><br>

                <textarea
                    name="description"
                    rows="5"
                    cols="50"
                    placeholder="Enter quiz description"
                    required></textarea>


                <br><br>


                <label>
                    <strong>Duration (minutes):</strong>
                </label>

                <br><br>

                <input
                    type="number"
                    name="duration"
                    min="1"
                    placeholder="e.g. 10"
                    required>


                <br><br>


                <button type="submit">
                    Create Quiz
                </button>

            </form>

        </div>


        <div class="card">

            <a href="admin-dashboard.jsp">
                Back to Admin Dashboard
            </a>

        </div>

    </div>

</body>

</html>