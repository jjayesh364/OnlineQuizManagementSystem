<%@ page import="model.User" %>

<%
    User user = (User) session.getAttribute("user");

    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Student Dashboard</title>

    <link rel="stylesheet" href="style.css">
</head>

<body>

    <div class="container">

        <div class="card">

            <h1>Online Quiz Management System</h1>

            <h2>Welcome, <%= user.getName() %>!</h2>

            <p>
                Role: <strong><%= user.getRole() %></strong>
            </p>

        </div>


        <div class="card">

            <h2>Student Dashboard</h2>

            <p>
                <a href="QuizListServlet">Available Quizzes</a>
            </p>

            <p>
                <a href="MyResultsServlet">My Results</a>
            </p>

            <p>
                <a href="login.jsp">Logout</a>
            </p>

        </div>

    </div>

</body>

</html>