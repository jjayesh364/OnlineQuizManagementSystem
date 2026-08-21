<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="model.Question" %>
<%@ page import="model.CodingQuestion" %>

<%
    // ==========================================
    // Check login
    // ==========================================

    if (session.getAttribute("user") == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    // ==========================================
    // Get MCQ questions
    // ==========================================

    List<Question> questions =
            (List<Question>) request.getAttribute("questions");

    // ==========================================
    // Get coding questions
    // ==========================================

    List<CodingQuestion> codingQuestions =
            (List<CodingQuestion>) request.getAttribute("codingQuestions");

    // ==========================================
    // Get quiz ID
    // ==========================================

    Integer quizId =
            (Integer) request.getAttribute("quizId");
%>

<!DOCTYPE html>

<html>

<head>

    <meta charset="UTF-8">

    <title>Quiz</title>

    <link rel="stylesheet" href="style.css">

    <style>

        /* ==========================================
           QUIZ HEADER
           ========================================== */

        .quiz-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .quiz-header h1 {
            margin: 0;
        }

        /* ==========================================
           TIMER
           ========================================== */

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

        /* ==========================================
           MCQ QUESTION
           ========================================== */

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

        /* ==========================================
           MCQ SUBMIT BUTTON
           ========================================== */

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

        /* ==========================================
           CODING QUESTIONS
           ========================================== */

        .coding-question {
            margin-top: 20px;
            padding: 20px;
            border: 1px solid #d1d5db;
            border-radius: 10px;
            background: #f8fafc;
        }

        .coding-question h2 {
            color: #1e3a5f;
            margin-top: 0;
        }

        .coding-question p {
            line-height: 1.6;
        }

        .coding-button {
            display: inline-block;
            margin-top: 15px;
            padding: 12px 20px;
            background: #2563eb;
            color: white;
            text-decoration: none;
            border-radius: 8px;
        }

        .coding-button:hover {
            background: #1d4ed8;
        }

        /* ==========================================
           CODING SECTION
           ========================================== */

        .coding-section {
            margin-top: 25px;
        }

    </style>

</head>


<body>

<div class="container">


    <!-- ==========================================
         QUIZ HEADER
         ========================================== -->

    <div class="card quiz-header">

        <div>

            <h1>Quiz</h1>

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



    <!-- ==========================================
         MCQ QUESTIONS
         ========================================== -->

    <div class="card">

        <h2>MCQ Questions</h2>


        <form id="quizForm"
              action="SubmitQuizServlet"
              method="post">


            <!-- Quiz ID -->

            <input type="hidden"
                   name="quizId"
                   value="<%= quizId %>">


            <%

                if (questions != null &&
                    !questions.isEmpty()) {

                    int questionNumber = 1;

                    for (Question question : questions) {

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

                                <input
                                    type="radio"
                                    name="question_<%= question.getQuestionId() %>"
                                    value="A">

                                <%= question.getOptionA() %>

                            </label>


                            <!-- Option B -->

                            <label class="option">

                                <input
                                    type="radio"
                                    name="question_<%= question.getQuestionId() %>"
                                    value="B">

                                <%= question.getOptionB() %>

                            </label>


                            <!-- Option C -->

                            <label class="option">

                                <input
                                    type="radio"
                                    name="question_<%= question.getQuestionId() %>"
                                    value="C">

                                <%= question.getOptionC() %>

                            </label>


                            <!-- Option D -->

                            <label class="option">

                                <input
                                    type="radio"
                                    name="question_<%= question.getQuestionId() %>"
                                    value="D">

                                <%= question.getOptionD() %>

                            </label>


                        </div>


            <%

                        questionNumber++;

                    }

            %>


                    <!-- Submit MCQ Quiz -->

                    <button
                        type="submit"
                        class="quiz-submit">

                        Submit MCQ Quiz

                    </button>


            <%

                } else {

            %>


                    <p>
                        No MCQ questions available for this quiz.
                    </p>


            <%

                }

            %>


        </form>

    </div>



    <!-- ==========================================
         CODING QUESTIONS
         ========================================== -->

    <%

        if (codingQuestions != null &&
            !codingQuestions.isEmpty()) {

    %>


        <div class="card coding-section">


            <h2>Coding Questions</h2>


            <p>
                Solve the following programming problems.
            </p>


            <%

                for (CodingQuestion codingQuestion :
                        codingQuestions) {

            %>


                    <div class="coding-question">


                        <!-- Coding Question Title -->

                        <h2>

                            <%= codingQuestion.getTitle() %>

                        </h2>


                        <!-- Programming Language -->

                        <p>

                            <strong>
                                Programming Language:
                            </strong>

                            <%= codingQuestion.getLanguage() %>

                        </p>


                        <!-- Problem Statement -->

                        <p>

                            <strong>
                                Problem Statement:
                            </strong>

                        </p>

                        <p>

                            <%= codingQuestion.getProblemStatement() %>

                        </p>


                        <!-- Input -->

                        <p>

                            <strong>
                                Input:
                            </strong>

                        </p>

                        <p>

                            <%= codingQuestion.getInputDescription() %>

                        </p>


                        <!-- Output -->

                        <p>

                            <strong>
                                Output:
                            </strong>

                        </p>

                        <p>

                            <%= codingQuestion.getOutputDescription() %>

                        </p>


                        <!-- Constraints -->

                        <p>

                            <strong>
                                Constraints:
                            </strong>

                        </p>

                        <p>

                            <%= codingQuestion.getConstraints() %>

                        </p>


                        <!-- Solve Button -->

                        <a
                            class="coding-button"
                            href="CodingTestServlet?codingQuestionId=<%= codingQuestion.getCodingQuestionId() %>">

                            Solve Coding Question

                        </a>


                    </div>


            <%

                }

            %>


        </div>


    <%

        }

    %>



    <!-- ==========================================
         BACK BUTTON
         ========================================== -->

    <div class="card">

        <a href="QuizListServlet">

            Back to Quizzes

        </a>

    </div>


</div>



<!-- ==========================================
     QUIZ TIMER
     ========================================== -->

<script>

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


    if (minutes < 10) {

        minutes = "0" + minutes;

    }


    if (seconds < 10) {

        seconds = "0" + seconds;

    }


    timer.textContent =
        minutes + ":" + seconds;


    if (timeRemaining <= 60) {

        timer.classList.add("warning");

    }


    if (timeRemaining <= 0) {

        clearInterval(timerInterval);

        alert(
            "Time is over! Your MCQ quiz will be submitted automatically."
        );


        if (quizForm) {

            quizForm.submit();

        }

        return;

    }


    timeRemaining--;

}


updateTimer();


const timerInterval =
    setInterval(updateTimer, 1000);

</script>


</body>

</html>