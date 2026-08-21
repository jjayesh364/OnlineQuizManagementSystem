<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="model.Question" %>
<%@ page import="model.User" %>

<%
    User user = (User) session.getAttribute("user");

    // Check login
    if (user == null) {

        response.sendRedirect("login.jsp");
        return;
    }

    // ADMIN and FACULTY can view questions
    if (!"ADMIN".equalsIgnoreCase(user.getRole())
            && !"FACULTY".equalsIgnoreCase(user.getRole())) {

        response.sendRedirect("dashboard.jsp");
        return;
    }

    List<Question> questions =
            (List<Question>) request.getAttribute("questions");

    Integer quizId =
            (Integer) request.getAttribute("quizId");
%>

<!DOCTYPE html>

<html>

<head>

    <meta charset="UTF-8">

    <title>View Questions</title>

    <link rel="stylesheet" href="style.css">

</head>

<body>

    <div class="container">

        <!-- Header -->

        <div class="card">

            <h1>Quiz Questions</h1>

            <p>

                <strong>Quiz ID:</strong>

                <%= quizId %>

            </p>

        </div>


        <!-- Questions -->

        <%

            if (questions != null &&
                !questions.isEmpty()) {

                int questionNumber = 1;

                for (Question question : questions) {

        %>

                    <div class="card">

                        <h2>

                            Question
                            <%= questionNumber %>

                        </h2>


                        <h3>

                            <%= question.getQuestionText() %>

                        </h3>


                        <p>

                            A.
                            <%= question.getOptionA() %>

                        </p>


                        <p>

                            B.
                            <%= question.getOptionB() %>

                        </p>


                        <p>

                            C.
                            <%= question.getOptionC() %>

                        </p>


                        <p>

                            D.
                            <%= question.getOptionD() %>

                        </p>


                        <p>

                            <strong>

                                Correct Answer:

                                <%= question.getCorrectAnswer() %>

                            </strong>

                        </p>


                        <hr>


                        <p>

                            <a href="EditQuestionServlet?questionId=<%= question.getQuestionId() %>">

                                Edit Question

                            </a>

                            &nbsp; | &nbsp;

                            <a
                                href="DeleteQuestionServlet?questionId=<%= question.getQuestionId() %>&quizId=<%= quizId %>"

                                class="danger"

                                onclick="return confirm('Are you sure you want to delete this question?');">

                                Delete Question

                            </a>

                        </p>

                    </div>

        <%

                    questionNumber++;

                }

            } else {

        %>

                <div class="card">

                    <h2>No Questions Found</h2>

                    <p>

                        No questions have been added
                        to this quiz yet.

                    </p>

                </div>

        <%

            }

        %>


        <!-- Navigation -->

        <div class="card">

            <p>

                <a href="AddQuestionServlet?quizId=<%= quizId %>">

                    Add Another Question

                </a>

            </p>


            <p>

                <a href="ManageQuizServlet">

                    Back to Manage Quizzes

                </a>

            </p>

        </div>

    </div>

</body>

</html>