package controller;

import java.io.IOException;
import java.util.List;

import dao.ResultDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Result;
import model.User;

@WebServlet("/AdminResultsServlet")
public class AdminResultsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Check login
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Check admin role
        User user = (User) session.getAttribute("user");

        if (!"ADMIN".equalsIgnoreCase(user.getRole())) {
            response.sendRedirect("dashboard.jsp");
            return;
        }

        // Get all student results
        ResultDAO resultDAO = new ResultDAO();

        List<Result> results = resultDAO.getAllResults();

        // Send results to JSP
        request.setAttribute("results", results);

        // Open admin results page
        request.getRequestDispatcher("admin-results.jsp")
               .forward(request, response);
    }
}