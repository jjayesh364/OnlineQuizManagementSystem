<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="model.User" %>
<%@ page import="model.Quiz" %>

<%
    User user = (User) session.getAttribute("user");

    // Check login
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    // ADMIN and FACULTY can create coding questions
    if (!"ADMIN".equalsIgnoreCase(user.getRole())
            && !"FACULTY".equalsIgnoreCase(user.getRole())) {

        response.sendRedirect("dashboard.jsp");
        return;
    }

    Quiz quiz =
            (Quiz) request.getAttribute("quiz");

    if (quiz == null) {
        response.sendRedirect("ManageQuizServlet");
        return;
    }
%>

<!DOCTYPE html>

<html>

<head>

    <meta charset="UTF-8">

    <title>Create Coding Question</title>

    <link rel="stylesheet" href="style.css">

</head>

<body>

    <div class="container">

        <!-- Header -->

        <div class="card">

            <h1>Create Java Coding Question</h1>

            <h2>
                Quiz: <%= quiz.getTitle() %>
            </h2>

            <p>
                Create a Java programming problem for your students.
            </p>

        </div>


        <!-- Coding Question Form -->

        <div class="card">

            <form action="CreateCodingQuestionServlet"
                  method="post">

                <!-- Quiz ID -->

                <input type="hidden"
                       name="quizId"
                       value="<%= quiz.getQuizId() %>">


                <!-- Title -->

                <label>
                    <strong>Problem Title:</strong>
                </label>

                <br><br>

                <input
                    type="text"
                    name="title"
                    placeholder="Enter problem title"
                    required>


                <br><br>


                <!-- Problem Statement -->

                <label>
                    <strong>Problem Statement:</strong>
                </label>

                <br><br>

                <textarea
                    name="problemStatement"
                    rows="8"
                    cols="60"
                    placeholder="Describe the programming problem..."
                    required></textarea>


                <br><br>


                <!-- Input -->

                <label>
                    <strong>Input Description:</strong>
                </label>

                <br><br>

                <textarea
                    name="inputDescription"
                    rows="5"
                    cols="60"
                    placeholder="Describe the input format..."
                    required></textarea>


                <br><br>


                <!-- Output -->

                <label>
                    <strong>Output Description:</strong>
                </label>

                <br><br>

                <textarea
                    name="outputDescription"
                    rows="5"
                    cols="60"
                    placeholder="Describe the expected output..."
                    required></textarea>


                <br><br>


                <!-- Constraints -->

                <label>
                    <strong>Constraints:</strong>
                </label>

                <br><br>

                <textarea
                    name="constraints"
                    rows="5"
                    cols="60"
                    placeholder="Example: 1 <= N <= 100000"
                    required></textarea>


                <br><br>


                <!-- Sample Input -->

                <label>
                    <strong>Sample Input:</strong>
                </label>

                <br><br>

                <textarea
                    name="sampleInput"
                    rows="5"
                    cols="60"
                    placeholder="Example:
5 10"
                    required></textarea>


                <br><br>


                <!-- Sample Output -->

                <label>
                    <strong>Sample Output:</strong>
                </label>

                <br><br>

                <textarea
                    name="sampleOutput"
                    rows="5"
                    cols="60"
                    placeholder="Example:
15"
                    required></textarea>


                <br><br>


                <!-- Language -->

                <label>
                    <strong>Programming Language:</strong>
                </label>

                <br><br>

                <input
                    type="text"
                    value="Java"
                    readonly>


                <br><br>


                <!-- Submit -->

                <button type="submit">

                    Create Coding Question

                </button>

            </form>

        </div>


        <!-- Back -->

        <div class="card">

            <p>

                <a href="ManageQuizServlet">

                    Back to Manage Quizzes

                </a>

            </p>

        </div>

    </div>

</body>

</html>