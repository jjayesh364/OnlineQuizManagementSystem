package controller;

import jakarta.servlet.http.HttpSession;
import java.io.IOException;

import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("username");
        String password = request.getParameter("password");

        System.out.println("Email received: " + email);
        System.out.println("Password received: " + password);

        UserDAO userDAO = new UserDAO();

        User user = userDAO.loginUser(email, password);

        if (user != null) {

            HttpSession session = request.getSession();

            session.setAttribute("user", user);

            // Check user role
            if ("ADMIN".equalsIgnoreCase(user.getRole())) {

                response.sendRedirect("admin-dashboard.jsp");

            } else {

                response.sendRedirect("dashboard.jsp");
            }

        } else {

            response.setContentType("text/html");

            response.getWriter().println(
                "<h2>Login Failed!</h2>"
            );

            response.getWriter().println(
                "<p>Invalid username or password.</p>"
            );
        }
    }
}