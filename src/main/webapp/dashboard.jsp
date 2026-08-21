<%@ page import="model.User" %>

<%
    User user = (User) session.getAttribute("user");

    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    String role = user.getRole();
%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Dashboard</title>
    <link rel="stylesheet" href="style.css">
</head>

<body>

    <div class="container">

        <!-- Welcome Card -->
        <div class="card">

            <h1>Online Quiz Management System</h1>

            <h2>
                Welcome, <%= user.getName() %>!
            </h2>

            <p>
                Role:
                <strong><%= user.getRole() %></strong>
            </p>

        </div>


        <!-- STUDENT DASHBOARD -->
        <% if ("STUDENT".equalsIgnoreCase(role)) { %>

        <div class="card">

            <h2>Student Dashboard</h2>

            <p>
                <a href="QuizListServlet">
                    Available Quizzes
                </a>
            </p>

            <p>
                <a href="MyResultsServlet">
                    My Results
                </a>
            </p>

            <p>
                <a href="LogoutServlet">
                    Logout
                </a>
            </p>

        </div>

        <% } %>


        <!-- FACULTY DASHBOARD -->
        <% if ("FACULTY".equalsIgnoreCase(role)) { %>

        <div class="card">

            <h2>Faculty Dashboard</h2>

            <p>
                <a href="CreateQuizServlet">
                    Create Quiz
                </a>
            </p>

            <p>
                <a href="ManageQuizServlet">
                    Manage My Quizzes
                </a>
            </p>

            <p>
                <a href="LogoutServlet">
                    Logout
                </a>
            </p>

        </div>

        <% } %>


        <!-- ADMIN DASHBOARD -->
        <% if ("ADMIN".equalsIgnoreCase(role)) { %>

        <div class="card">

            <h2>Admin Dashboard</h2>

            <p>
                <a href="admin-dashboard.jsp">
                    Admin Panel
                </a>
            </p>

            <p>
                <a href="LogoutServlet">
                    Logout
                </a>
            </p>

        </div>

        <% } %>

    </div>

</body>

</html>