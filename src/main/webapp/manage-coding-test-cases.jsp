<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="model.User" %>
<%@ page import="model.CodingQuestion" %>
<%@ page import="model.CodingTestCase" %>

<%
    User user = (User) session.getAttribute("user");

    // Check login
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    // ADMIN and FACULTY can manage test cases
    if (!"ADMIN".equalsIgnoreCase(user.getRole())
            && !"FACULTY".equalsIgnoreCase(user.getRole())) {

        response.sendRedirect("dashboard.jsp");
        return;
    }

    CodingQuestion codingQuestion =
            (CodingQuestion) request.getAttribute("codingQuestion");

    List<CodingTestCase> testCases =
            (List<CodingTestCase>) request.getAttribute("testCases");

    if (codingQuestion == null) {
        response.sendRedirect("ManageQuizServlet");
        return;
    }
%>

<!DOCTYPE html>

<html>

<head>

    <meta charset="UTF-8">

    <title>Manage Test Cases</title>

    <link rel="stylesheet" href="style.css">

</head>

<body>

    <div class="container">

        <!-- Header -->

        <div class="card">

            <h1>Manage Test Cases</h1>

            <h2>
                <%= codingQuestion.getTitle() %>
            </h2>

            <p>
                Add and manage test cases for this Java coding problem.
            </p>

        </div>


        <!-- Add Test Case -->

        <div class="card">

            <h2>Add New Test Case</h2>

            <form action="AddCodingTestCaseServlet"
                  method="post">

                <input
                    type="hidden"
                    name="codingQuestionId"
                    value="<%= codingQuestion.getCodingQuestionId() %>">


                <label>

                    <strong>Input:</strong>

                </label>

                <br><br>

                <textarea
                    name="inputData"
                    rows="5"
                    cols="60"
                    placeholder="Example:
5 10"
                    required></textarea>


                <br><br>


                <label>

                    <strong>Expected Output:</strong>

                </label>

                <br><br>

                <textarea
                    name="expectedOutput"
                    rows="5"
                    cols="60"
                    placeholder="Example:
15"
                    required></textarea>


                <br><br>


                <label>

                    <input
                        type="checkbox"
                        name="isSample"
                        value="true">

                    <strong>
                        Show this test case as a sample to students
                    </strong>

                </label>


                <br><br>


                <button type="submit">

                    Add Test Case

                </button>

            </form>

        </div>


        <!-- Existing Test Cases -->

        <div class="card">

            <h2>Existing Test Cases</h2>

            <%

                if (testCases != null &&
                    !testCases.isEmpty()) {

                    for (CodingTestCase testCase : testCases) {

            %>

                        <div class="card">

                            <h3>
                                Test Case #<%= testCase.getTestCaseId() %>
                            </h3>


                            <p>

                                <strong>Input:</strong>

                            </p>

                            <pre><%= testCase.getInputData() %></pre>


                            <p>

                                <strong>Expected Output:</strong>

                            </p>

                            <pre><%= testCase.getExpectedOutput() %></pre>


                            <p>

                                <strong>Type:</strong>

                                <%

                                    if (testCase.isSample()) {

                                %>

                                    Sample Test Case

                                <%

                                    } else {

                                %>

                                    Hidden Test Case

                                <%

                                    }

                                %>

                            </p>


                            <p>

                                <a
                                    href="DeleteCodingTestCaseServlet?testCaseId=<%= testCase.getTestCaseId() %>&codingQuestionId=<%= codingQuestion.getCodingQuestionId() %>"
                                    class="danger"
                                    onclick="return confirm('Are you sure you want to delete this test case?');">

                                    Delete Test Case

                                </a>

                            </p>

                        </div>

            <%

                    }

                } else {

            %>

                    <p>

                        No test cases have been added yet.

                    </p>

            <%

                }

            %>

        </div>


        <!-- Navigation -->

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