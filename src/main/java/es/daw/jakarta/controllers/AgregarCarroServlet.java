package es.daw.jakarta.controllers;

import java.io.IOException;
import java.sql.SQLException;

import es.daw.jakarta.bd.DaoProducto;
import es.daw.jakarta.models.Carro;
import es.daw.jakarta.models.ItemCarro;
import es.daw.jakarta.models.Producto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/carro/agregar")
public class AgregarCarroServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private DaoProducto daoProducto;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            daoProducto = new DaoProducto("JDBC.properties");
        } catch (SQLException e) {
            throw new ServletException("Error inicializando DaoProducto", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Obtener el parámetro 'id' de la solicitud
        String idStr = request.getParameter("id");
        if (idStr == null || idStr.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/productos");
            return;
        }

        try {
            int id = Integer.parseInt(idStr);

            Producto producto = daoProducto.select(id);

            if (producto != null) {
                HttpSession session = request.getSession();
                Carro carro = (Carro) session.getAttribute("carro");
                if (carro == null) {
                    carro = new Carro();
                    session.setAttribute("carro", carro);
                }

                ItemCarro itemCarro = new ItemCarro(1, producto);

                carro.addItemCarro(itemCarro);

            }

            response.sendRedirect(request.getContextPath() + "/carro/ver");
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/productos");
        } catch (SQLException e) {
            throw new ServletException("Error al acceder a la base de datos", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
