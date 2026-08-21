<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="model.User" %>

<%
    User user = (User) session.getAttribute("user");

    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    if (!"STUDENT".equalsIgnoreCase(user.getRole())) {
        response.sendRedirect("dashboard.jsp");
        return;
    }

    String status = (String) request.getAttribute("status");

    Integer passedTests =
            (Integer) request.getAttribute("passedTests");

    Integer totalTests =
            (Integer) request.getAttribute("totalTests");

    Integer score =
            (Integer) request.getAttribute("score");

    String message =
            (String) request.getAttribute("message");

    String compilationError =
            (String) request.getAttribute("compilationError");

    if (passedTests == null) {
        passedTests = 0;
    }

    if (totalTests == null) {
        totalTests = 0;
    }

    if (score == null) {
        score = 0;
    }
%>

<!DOCTYPE html>

<html>

<head>

    <meta charset="UTF-8">

    <title>Coding Result</title>

    <link rel="stylesheet" href="style.css">

    <style>

        .result-status {
            font-size: 28px;
            font-weight: bold;
            margin-bottom: 20px;
        }

        .accepted {
            color: #15803d;
        }

        .error {
            color: #dc2626;
        }

        .warning {
            color: #ca8a04;
        }

        .result-box {
            padding: 20px;
            margin-top: 20px;
            border-radius: 8px;
            background: #f8fafc;
            border: 1px solid #d1d5db;
        }

        .result-stat {
            font-size: 18px;
            margin: 10px 0;
        }

        .error-output {
            background: #111827;
            color: #ffffff;
            padding: 15px;
            border-radius: 8px;
            white-space: pre-wrap;
            overflow-x: auto;
        }

        .result-button {
            display: inline-block;
            margin-top: 15px;
            padding: 12px 20px;
            border-radius: 8px;
            background: #2563eb;
            color: white;
            text-decoration: none;
        }

        .result-button:hover {
            background: #1d4ed8;
        }

    </style>

</head>

<body>

<div class="container">

    <div class="card">

        <h1>Coding Submission Result</h1>

        <%
            if ("ACCEPTED".equals(status)) {
        %>

            <div class="result-status accepted">
                ✓ Accepted
            </div>

            <p>
                Congratulations! Your solution passed all
                test cases.
            </p>

        <%
            } else if ("COMPILATION_ERROR".equals(status)) {
        %>

            <div class="result-status error">
                ✗ Compilation Error
            </div>

            <p>
                Your Java program could not be compiled.
            </p>

        <%
            } else if ("RUNTIME_ERROR".equals(status)) {
        %>

            <div class="result-status error">
                ✗ Runtime Error
            </div>

            <p>
                Your program compiled successfully but
                produced a runtime error.
            </p>

        <%
            } else if ("WRONG_ANSWER".equals(status)) {
        %>

            <div class="result-status error">
                ✗ Wrong Answer
            </div>

            <p>
                Your program produced an incorrect output
                for one of the test cases.
            </p>

        <%
            } else if ("TIME_LIMIT_EXCEEDED".equals(status)) {
        %>

            <div class="result-status warning">
                ⏱ Time Limit Exceeded
            </div>

            <p>
                Your program took too long to execute.
            </p>

        <%
            } else if ("COMPILATION_TIMEOUT".equals(status)) {
        %>

            <div class="result-status warning">
                ⏱ Compilation Timeout
            </div>

            <p>
                Compilation took too long.
            </p>

        <%
            } else {
        %>

            <div class="result-status error">
                ✗ Submission Error
            </div>

            <p>
                <%= message != null ? message : "An error occurred." %>
            </p>

        <%
            }
        %>

    </div>


    <!-- Score -->

    <div class="card">

        <h2>Result Summary</h2>

        <div class="result-box">

            <div class="result-stat">
                <strong>Passed Test Cases:</strong>
                <%= passedTests %> / <%= totalTests %>
            </div>

            <div class="result-stat">
                <strong>Score:</strong>
                <%= score %>%
            </div>

            <div class="result-stat">
                <strong>Status:</strong>
                <%= status %>
            </div>

        </div>

    </div>


    <!-- Compilation Error -->

    <%
        if ("COMPILATION_ERROR".equals(status)
                && compilationError != null
                && !compilationError.isEmpty()) {
    %>

        <div class="card">

            <h2>Compiler Error</h2>

            <pre class="error-output"><%= compilationError %></pre>

        </div>

    <%
        }
    %>


    <!-- Runtime / Wrong Answer Error -->

    <%
        if (("RUNTIME_ERROR".equals(status)
                || "WRONG_ANSWER".equals(status)
                || "TIME_LIMIT_EXCEEDED".equals(status))
                && message != null
                && !message.isEmpty()) {
    %>

        <div class="card">

            <h2>
                <%
                    if ("WRONG_ANSWER".equals(status)) {
                %>
                    Test Case Result
                <%
                    } else {
                %>
                    Error Details
                <%
                    }
                %>
            </h2>

            <pre class="error-output"><%= message %></pre>

        </div>

    <%
        }
    %>


    <!-- Navigation -->

    <div class="card">

        <a
            class="result-button"
            href="CodingTestServlet?codingQuestionId=1">

            Try Again

        </a>

        <br><br>

        <a href="QuizListServlet">
            Back to Quizzes
        </a>

    </div>

</div>

</body>

</html>