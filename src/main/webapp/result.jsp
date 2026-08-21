<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>

    <meta charset="UTF-8">

    <title>Quiz Result</title>

    <link rel="stylesheet" href="style.css">

    <style>

        .result-header {
            text-align: center;
        }

        .score-box {
            text-align: center;
        }

        .score {
            font-size: 42px;
            font-weight: bold;
            color: #2563eb;
            margin: 15px 0;
        }

        .percentage {
            font-size: 24px;
            font-weight: bold;
        }

        .time-warning {
            background: #fef2f2;
            border: 1px solid #fecaca;
            color: #991b1b;
            padding: 15px;
            border-radius: 8px;
            text-align: center;
            font-weight: bold;
            margin-top: 15px;
        }

        .success-message {
            background: #f0fdf4;
            border: 1px solid #bbf7d0;
            color: #166534;
            padding: 15px;
            border-radius: 8px;
            text-align: center;
            font-weight: bold;
            margin-top: 15px;
        }

        .result-links {
            text-align: center;
        }

        .result-links p {
            margin: 12px 0;
        }

    </style>

</head>


<body>

<div class="container">


    <!-- Result Header -->

    <div class="card result-header">

        <h1>Quiz Result</h1>

        <p>
            Here is your quiz performance.
        </p>

    </div>


    <!-- Score -->

    <div class="card score-box">

        <h2>Your Score</h2>

        <div class="score">

            <%= request.getAttribute("score") %>
            /
            <%= request.getAttribute("totalQuestions") %>

        </div>


        <p class="percentage">

            <strong>Percentage:</strong>

            <%= request.getAttribute("percentage") %>%

        </p>


        <%

            Boolean timeExpired =
                    (Boolean) request.getAttribute("timeExpired");

            if (Boolean.TRUE.equals(timeExpired)) {

        %>


            <div class="time-warning">

                ⏰ Time's up! Your quiz was
                automatically submitted.

            </div>


        <%

            } else {

        %>


            <div class="success-message">

                ✓ Quiz submitted successfully.

            </div>


        <%

            }

        %>

    </div>


    <!-- Navigation -->

    <div class="card result-links">

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