<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="model.Question" %>

<!DOCTYPE html>
<html>

<head>

    <meta charset="UTF-8">

    <title>Online Quiz</title>

    <link rel="stylesheet" href="style.css">

    <style>

        /* Quiz Header */

        .quiz-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .quiz-header h1 {
            margin: 0;
        }


        /* Timer */

        .timer-box {
            background: #f1f5f9;
            padding: 12px 20px;
            border-radius: 10px;
            font-size: 18px;
            font-weight: bold;
        }

        #timer {
            margin-left: 8px;
            color: #dc2626;
            font-size: 24px;
        }

        #timer.warning {
            color: #991b1b;
        }


        /* Question */

        .question {
            padding: 20px 0;
            border-bottom: 1px solid #e5e7eb;
        }

        .question:last-child {
            border-bottom: none;
        }

        .question h3 {
            color: #1e3a5f;
            margin-bottom: 10px;
        }

        .question-text {
            font-size: 18px;
            font-weight: 500;
            margin-bottom: 15px;
        }


        /* Options */

        .option {
            display: block;
            padding: 12px 15px;
            margin: 8px 0;
            border: 1px solid #d1d5db;
            border-radius: 8px;
            cursor: pointer;
            transition: 0.2s;
        }

        .option:hover {
            background: #f3f4f6;
        }

        .option input {
            margin-right: 10px;
        }


        /* Submit Button */

        .quiz-submit {
            margin-top: 25px;
            padding: 12px 25px;
            border: none;
            border-radius: 8px;
            background: #2563eb;
            color: white;
            font-size: 16px;
            cursor: pointer;
        }

        .quiz-submit:hover {
            background: #1d4ed8;
        }

    </style>

</head>


<body>

<%

    List questions =
            (List) request.getAttribute("questions");

    Integer quizId =
            (Integer) request.getAttribute("quizId");

    Integer duration =
            (Integer) request.getAttribute("duration");

%>


<div class="container">


    <!-- Quiz Header -->

    <div class="card quiz-header">

        <div>

            <h1>Online Quiz</h1>

            <p>
                Answer all questions before the time expires.
            </p>

        </div>


        <div class="timer-box">

            Time Remaining:

            <span id="timer">
                00:00
            </span>

        </div>

    </div>



    <!-- Quiz Questions -->

    <div class="card">

        <form id="quizForm"
              action="SubmitQuizServlet"
              method="post">


            <input type="hidden"
                   name="quizId"
                   value="<%= quizId %>">


<%

    if (questions != null && !questions.isEmpty()) {

        int questionNumber = 1;

        for (Object obj : questions) {

            Question question =
                    (Question) obj;

%>


            <div class="question">


                <h3>
                    Question <%= questionNumber %>
                </h3>


                <p class="question-text">

                    <%= question.getQuestionText() %>

                </p>


                <!-- Option A -->

                <label class="option">

                    <input type="radio"
                           name="question_<%= question.getQuestionId() %>"
                           value="A">

                    <%= question.getOptionA() %>

                </label>


                <!-- Option B -->

                <label class="option">

                    <input type="radio"
                           name="question_<%= question.getQuestionId() %>"
                           value="B">

                    <%= question.getOptionB() %>

                </label>


                <!-- Option C -->

                <label class="option">

                    <input type="radio"
                           name="question_<%= question.getQuestionId() %>"
                           value="C">

                    <%= question.getOptionC() %>

                </label>


                <!-- Option D -->

                <label class="option">

                    <input type="radio"
                           name="question_<%= question.getQuestionId() %>"
                           value="D">

                    <%= question.getOptionD() %>

                </label>


            </div>


<%

            questionNumber++;

        }

%>


            <button type="submit"
                    class="quiz-submit">

                Submit Quiz

            </button>


<%

    } else {

%>


        <p>
            No questions available for this quiz.
        </p>


<%

    }

%>


        </form>

    </div>



    <!-- Back Button -->

    <div class="card">

        <a href="QuizListServlet">
            Back to Quizzes
        </a>

    </div>


</div>



<!-- =========================
     QUIZ TIMER
     ========================= -->

<script>

//Remaining time calculated by the server
let timeRemaining =
    <%= request.getAttribute("remainingSeconds") != null
        ? request.getAttribute("remainingSeconds")
        : 0 %>;


    const timer =
        document.getElementById("timer");

    const quizForm =
        document.getElementById("quizForm");


    function updateTimer() {

        let minutes =
            Math.floor(timeRemaining / 60);

        let seconds =
            timeRemaining % 60;


        // Add leading zero

        if (minutes < 10) {
            minutes = "0" + minutes;
        }

        if (seconds < 10) {
            seconds = "0" + seconds;
        }


        // Display timer

        timer.textContent =
            minutes + ":" + seconds;


        // Warning when one minute remains

        if (timeRemaining <= 60) {

            timer.classList.add("warning");

        }


        // Time expired

        if (timeRemaining <= 0) {

            clearInterval(timerInterval);


            alert(
                "Time is over! Your quiz will be submitted automatically."
            );


            if (quizForm) {

                quizForm.submit();

            }

            return;

        }


        timeRemaining--;

    }


    // Display timer immediately

    updateTimer();


    // Update every second

    const timerInterval =
        setInterval(updateTimer, 1000);

</script>


</body>

</html>