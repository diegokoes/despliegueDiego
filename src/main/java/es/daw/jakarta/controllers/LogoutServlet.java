package es.daw.jakarta.controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import es.daw.jakarta.services.*;
import java.util.Optional;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        LoginService auth = new LoginServiceImpl();
        Optional<String> username = auth.getUserName(request);

        if (username.isPresent()) {
            // invalidar la sesión tras hacer logout
            request.getSession().removeAttribute("username");
            request.getSession().invalidate();
            Cookie color = new Cookie("color", "");
            color.setMaxAge(0);
            response.addCookie(color);
        }

        response.sendRedirect("logout.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // doGet(request, response);
    }
}