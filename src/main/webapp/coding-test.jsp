<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="model.User" %>
<%@ page import="model.CodingQuestion" %>
<%@ page import="model.CodingTestCase" %>

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

    CodingQuestion codingQuestion =
            (CodingQuestion) request.getAttribute("codingQuestion");

    List<CodingTestCase> sampleTestCases =
            (List<CodingTestCase>) request.getAttribute("sampleTestCases");

    if (codingQuestion == null) {
        response.sendRedirect("QuizListServlet");
        return;
    }
%>

<!DOCTYPE html>

<html>

<head>

    <meta charset="UTF-8">

    <title><%= codingQuestion.getTitle() %></title>

    <link rel="stylesheet" href="style.css">

    <style>

        .coding-header {
            margin-bottom: 20px;
        }

        .coding-section {
            margin-top: 20px;
        }

        .coding-section h2 {
            color: #1e3a5f;
        }

        .problem-text {
            white-space: pre-wrap;
            line-height: 1.6;
        }

        .sample-box {
            background: #f8fafc;
            border: 1px solid #d1d5db;
            border-radius: 8px;
            padding: 15px;
            margin-top: 15px;
        }

        .sample-box h3 {
            margin-top: 0;
            color: #1e3a5f;
        }

        pre {
            background: #111827;
            color: #ffffff;
            padding: 15px;
            border-radius: 8px;
            overflow-x: auto;
        }

        .code-editor {
            width: 100%;
            min-height: 350px;
            box-sizing: border-box;
            padding: 15px;
            font-family: Consolas, monospace;
            font-size: 15px;
            border: 1px solid #d1d5db;
            border-radius: 8px;
            resize: vertical;
        }

        .coding-button {
            margin-top: 15px;
            padding: 12px 25px;
            border: none;
            border-radius: 8px;
            background: #2563eb;
            color: white;
            font-size: 16px;
            cursor: pointer;
        }

        .coding-button:hover {
            background: #1d4ed8;
        }

    </style>

</head>

<body>

<div class="container">

    <!-- Header -->

    <div class="card coding-header">

        <h1>
            <%= codingQuestion.getTitle() %>
        </h1>

        <p>
            Programming Language:
            <strong>
                <%= codingQuestion.getLanguage() %>
            </strong>
        </p>

    </div>


    <!-- Problem Statement -->

    <div class="card">

        <div class="coding-section">

            <h2>Problem Statement</h2>

            <p class="problem-text">
                <%= codingQuestion.getProblemStatement() %>
            </p>

        </div>


        <!-- Input -->

        <div class="coding-section">

            <h2>Input</h2>

            <p class="problem-text">
                <%= codingQuestion.getInputDescription() %>
            </p>

        </div>


        <!-- Output -->

        <div class="coding-section">

            <h2>Output</h2>

            <p class="problem-text">
                <%= codingQuestion.getOutputDescription() %>
            </p>

        </div>


        <!-- Constraints -->

        <div class="coding-section">

            <h2>Constraints</h2>

            <p class="problem-text">
                <%= codingQuestion.getConstraints() %>
            </p>

        </div>

    </div>


    <!-- Sample Test Cases -->

    <div class="card">

        <h2>Sample Test Cases</h2>

        <%
            if (sampleTestCases != null &&
                !sampleTestCases.isEmpty()) {

                int sampleNumber = 1;

                for (CodingTestCase testCase :
                        sampleTestCases) {
        %>

                    <div class="sample-box">

                        <h3>
                            Sample Test Case
                            <%= sampleNumber %>
                        </h3>

                        <p>
                            <strong>Input:</strong>
                        </p>

                        <pre><%= testCase.getInputData() %></pre>

                        <p>
                            <strong>Expected Output:</strong>
                        </p>

                        <pre><%= testCase.getExpectedOutput() %></pre>

                    </div>

        <%
                    sampleNumber++;
                }

            } else {
        %>

                <p>
                    No sample test cases available.
                </p>

        <%
            }
        %>

    </div>


    <!-- Code Editor -->

    <div class="card">

        <h2>Write Your Java Code</h2>

        <form action="SubmitCodingCodeServlet"
              method="post">

            <input
                type="hidden"
                name="codingQuestionId"
                value="<%= codingQuestion.getCodingQuestionId() %>">


            <textarea
                name="sourceCode"
                class="code-editor"
                placeholder="Write your Java code here..."
                required>import java.util.*;

public class Main {

    public static void main(String[] args) {

        // Write your code here

    }
}</textarea>


            <br>

            <button
                type="submit"
                class="coding-button">

                Submit Code

            </button>

        </form>

    </div>


    <!-- Back -->

    <div class="card">

        <a href="QuizListServlet">
            Back to Quizzes
        </a>
    </div>

</div>

</body>

</html>