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

@WebServlet("/MyResultsServlet")
public class MyResultsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // Check if user is logged in
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Get logged-in user
        User user = (User) session.getAttribute("user");

        // Get results for this user
        ResultDAO resultDAO = new ResultDAO();

        List<Result> results = resultDAO.getResultsByUserId(user.getId());

        // Send results to JSP
        request.setAttribute("results", results);

        request.getRequestDispatcher("my-results.jsp")
               .forward(request, response);
    }
}