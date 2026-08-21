<!DOCTYPE html>
<html>

<head>

    <meta charset="UTF-8">

    <title>Online Quiz - Login</title>

    <link rel="stylesheet" href="style.css">

    <style>
        .login-container {
            width: 100%;
            max-width: 400px;
            margin: 80px auto;
        }

        .login-card {
            background-color: white;
            padding: 35px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }

        .login-card h1 {
            text-align: center;
            margin-bottom: 10px;
        }

        .login-card p {
            text-align: center;
            color: #666;
            margin-bottom: 30px;
        }

        .login-card label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }

        .login-card input[type="email"],
        .login-card input[type="password"] {
            width: 100%;
            box-sizing: border-box;
            padding: 12px;
            margin-bottom: 20px;
        }

        .login-card input[type="submit"] {
            width: 100%;
            background-color: #2563eb;
            color: white;
            border: none;
            padding: 12px;
            border-radius: 6px;
            cursor: pointer;
            font-size: 16px;
        }

        .login-card input[type="submit"]:hover {
            background-color: #1d4ed8;
        }
    </style>

</head>

<body>

    <div class="login-container">

        <div class="login-card">

            <h1>Online Quiz</h1>

            <p>
                Quiz Management System
            </p>


            <form action="LoginServlet" method="post">

                <label>
                    Email:
                </label>

                <input
                    type="email"
                    name="username"
                    placeholder="Enter your email"
                    required>


                <label>
                    Password:
                </label>

                <input
                    type="password"
                    name="password"
                    placeholder="Enter your password"
                    required>


                <input
                    type="submit"
                    value="Login">

            </form>

        </div>

    </div>

</body>

</html>