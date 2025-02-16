package es.daw.jakarta.controllers;

import java.io.IOException;

import es.daw.jakarta.models.Carro;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/carro/ver")
public class VerCarroServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Obtener el carrito de la sesión
        HttpSession session = request.getSession();
        Carro carro = (Carro) session.getAttribute("carro");

        if (carro == null) {
            carro = new Carro();
            session.setAttribute("carro", carro);
        }

        // Redirigir al JSP del carrito
        getServletContext().getRequestDispatcher("/carro.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
