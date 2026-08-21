<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="model.Question" %>
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

    Question question = (Question) request.getAttribute("question");

    if (question == null) {
        response.sendRedirect("ManageQuizServlet");
        return;
    }
%>

<!DOCTYPE html>
<html>

<head>

    <meta charset="UTF-8">

    <title>Edit Question</title>

    <link rel="stylesheet" href="style.css">

</head>

<body>

    <div class="container">

        <div class="card">

            <h1>Edit Question</h1>

            <p>
                Update the question and its options below.
            </p>

        </div>


        <div class="card">

            <form action="EditQuestionServlet" method="post">

                <!-- Hidden IDs -->

                <input type="hidden"
                       name="questionId"
                       value="<%= question.getQuestionId() %>">

                <input type="hidden"
                       name="quizId"
                       value="<%= question.getQuizId() %>">


                <label>
                    <strong>Question:</strong>
                </label>

                <br><br>

                <textarea
                    name="questionText"
                    rows="4"
                    cols="50"
                    required><%= question.getQuestionText() %></textarea>


                <br><br>


                <label>
                    <strong>Option A:</strong>
                </label>

                <br><br>

                <input type="text"
                       name="optionA"
                       value="<%= question.getOptionA() %>"
                       required>


                <br><br>


                <label>
                    <strong>Option B:</strong>
                </label>

                <br><br>

                <input type="text"
                       name="optionB"
                       value="<%= question.getOptionB() %>"
                       required>


                <br><br>


                <label>
                    <strong>Option C:</strong>
                </label>

                <br><br>

                <input type="text"
                       name="optionC"
                       value="<%= question.getOptionC() %>"
                       required>


                <br><br>


                <label>
                    <strong>Option D:</strong>
                </label>

                <br><br>

                <input type="text"
                       name="optionD"
                       value="<%= question.getOptionD() %>"
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

                    <option value="A"
                        <%= "A".equals(question.getCorrectAnswer()) ? "selected" : "" %>>
                        A
                    </option>

                    <option value="B"
                        <%= "B".equals(question.getCorrectAnswer()) ? "selected" : "" %>>
                        B
                    </option>

                    <option value="C"
                        <%= "C".equals(question.getCorrectAnswer()) ? "selected" : "" %>>
                        C
                    </option>

                    <option value="D"
                        <%= "D".equals(question.getCorrectAnswer()) ? "selected" : "" %>>
                        D
                    </option>

                </select>


                <br><br>


                <button type="submit">
                    Update Question
                </button>

            </form>

        </div>


        <div class="card">

            <a href="ViewQuestionsServlet?quizId=<%= question.getQuizId() %>">
                Cancel
            </a>

        </div>

    </div>

</body>

</html>