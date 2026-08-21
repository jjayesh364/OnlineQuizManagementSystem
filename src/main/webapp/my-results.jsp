<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="model.Result" %>
<%@ page import="model.CodingSubmission" %>

<!DOCTYPE html>

<html>

<head>

    <meta charset="UTF-8">

    <title>My Results</title>

    <link rel="stylesheet" href="style.css">

</head>

<body>

<div class="container">

    <!-- =========================================
         PAGE HEADER
         ========================================= -->

    <div class="card">

        <h1>My Results</h1>

        <p>
            View your previous quiz attempts and coding submissions.
        </p>

    </div>


    <!-- =========================================
         MCQ RESULTS
         ========================================= -->

    <div class="card">

        <h2>Quiz Results</h2>

        <%

            List<Result> results =
                    (List<Result>) request.getAttribute("results");

            if (results != null && !results.isEmpty()) {

                for (Result result : results) {

        %>

                    <div class="card">

                        <h2>
                            <%= result.getQuizTitle() %>
                        </h2>

                        <p>

                            <strong>Score:</strong>

                            <%= result.getScore() %>
                            /
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

                <p>
                    No MCQ quiz results yet.
                </p>

        <%

            }

        %>

    </div>


    <!-- =========================================
         CODING RESULTS
         ========================================= -->

    <div class="card">

        <h2>Coding Submissions</h2>

        <%

            List<CodingSubmission> codingSubmissions =
                    (List<CodingSubmission>)
                    request.getAttribute("codingSubmissions");

            if (codingSubmissions != null &&
                !codingSubmissions.isEmpty()) {

                for (CodingSubmission submission :
                        codingSubmissions) {

        %>

                    <div class="card">

                        <h2>
                            <%= submission.getCodingQuestionTitle() %>
                        </h2>

                        <p>

                            <strong>Quiz:</strong>

                            <%= submission.getQuizTitle() %>

                        </p>

                        <p>

                            <strong>Passed Test Cases:</strong>

                            <%= submission.getPassedTests() %>
                            /
                            <%= submission.getTotalTests() %>

                        </p>

                        <p>

                            <strong>Score:</strong>

                            <%= submission.getScore() %>%

                        </p>

                        <p>

                            <strong>Status:</strong>

                            <%= submission.getStatus() %>

                        </p>

                        <p>

                            <strong>Submitted At:</strong>

                            <%= submission.getSubmittedAt() %>

                        </p>

                    </div>

        <%

                }

            } else {

        %>

                <p>
                    No coding submissions yet.
                </p>

        <%

            }

        %>

    </div>


    <!-- =========================================
         NAVIGATION
         ========================================= -->

    <div class="card">

        <p>

            <a href="QuizListServlet">
                Take Another Quiz
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