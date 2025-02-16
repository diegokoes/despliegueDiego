package es.daw.jakarta.controllers;

import es.daw.jakarta.models.Carro;
import es.daw.jakarta.models.ItemCarro;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.*;

@WebServlet("/carro/actualizar")
public class ActualizarCarroServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Carro carro = (Carro) session.getAttribute("carro");

        if (carro != null) {
            String[] eliminarIds = request.getParameterValues("eliminar");
            List<String> eliminarList = eliminarIds != null ? Arrays.asList(eliminarIds) : Collections.emptyList();

            List<ItemCarro> items = new ArrayList<>(carro.getItems());

            for (ItemCarro item : items) {
                Long productId = item.getProducto().getId();

                if (eliminarList.contains(String.valueOf(productId))) {
                    carro.eliminarItem(productId);
                } else {

                    String cantidadParam = request.getParameter("cantidad_" + productId);
                    int cantidad = Integer.parseInt(cantidadParam);

                    if (cantidad == 0) {
                        carro.eliminarItem(productId);
                    } else {
                        carro.actualizarCantidadItem(productId, cantidad);
                    }
                }
            }
        }

        response.sendRedirect(request.getContextPath() + "/carro/ver");
    }
}
