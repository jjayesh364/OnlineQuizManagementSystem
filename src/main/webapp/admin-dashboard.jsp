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
    <title>Admin Dashboard</title>

    <link rel="stylesheet" href="style.css">
</head>

<body>

    <div class="container">

        <div class="card">

            <h1>Online Quiz Management System</h1>

            <h2>Welcome, <%= user.getName() %>!</h2>

            <p>Role: <strong><%= user.getRole() %></strong></p>

        </div>


        <div class="card">

            <h2>Admin Dashboard</h2>

            <p>
                Manage quizzes, questions and student results from here.
            </p>

            <p>
                <a href="CreateQuizServlet">Create Quiz</a>
            </p>

            <p>
                <a href="ManageQuizServlet">Manage Quizzes</a>
            </p>

            <p>
                <a href="ManageQuestionServlet">Manage Questions</a>
            </p>

            <p>
                <a href="AdminResultsServlet">View Student Results</a>
            </p>

            <p>
                <a href="login.jsp">Logout</a>
            </p>

        </div>

    </div>

</body>

</html>