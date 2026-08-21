<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="model.User" %>

<%
    User user = (User) session.getAttribute("user");

    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    if (!"ADMIN".equalsIgnoreCase(user.getRole())) {
        response.sendRedirect("dashboard.jsp");
        return;
    }

    Integer quizId = (Integer) request.getAttribute("quizId");

    if (quizId == null) {
        response.sendRedirect("ManageQuizServlet");
        return;
    }
%>

<!DOCTYPE html>
<html>

<head>

    <meta charset="UTF-8">

    <title>Add Question</title>

    <link rel="stylesheet" href="style.css">

</head>

<body>

    <div class="container">

        <div class="card">

            <h1>Add Question</h1>

            <p>
                <strong>Quiz ID:</strong> <%= quizId %>
            </p>

        </div>


        <div class="card">

            <form action="AddQuestionServlet" method="post">

                <input type="hidden"
                       name="quizId"
                       value="<%= quizId %>">


                <label>
                    <strong>Question:</strong>
                </label>

                <br><br>

                <textarea
                    name="questionText"
                    rows="4"
                    cols="50"
                    required></textarea>


                <br><br>


                <label>
                    <strong>Option A:</strong>
                </label>

                <br><br>

                <input
                    type="text"
                    name="optionA"
                    required>


                <br><br>


                <label>
                    <strong>Option B:</strong>
                </label>

                <br><br>

                <input
                    type="text"
                    name="optionB"
                    required>


                <br><br>


                <label>
                    <strong>Option C:</strong>
                </label>

                <br><br>

                <input
                    type="text"
                    name="optionC"
                    required>


                <br><br>


                <label>
                    <strong>Option D:</strong>
                </label>

                <br><br>

                <input
                    type="text"
                    name="optionD"
                    required>


                <br><br>


                <label>
                    <strong>Correct Answer:</strong>
                </label>

                <br><br>

                <select name="correctAnswer" required>

                    <option value="">
                        -- Select Correct Answer --
                    </option>

                    <option value="A">A</option>

                    <option value="B">B</option>

                    <option value="C">C</option>

                    <option value="D">D</option>

                </select>


                <br><br>


                <button type="submit">
                    Add Question
                </button>

            </form>

        </div>


        <div class="card">

            <a href="ManageQuizServlet">
                Back to Manage Quizzes
            </a>

        </div>

    </div>

</body>

</html>