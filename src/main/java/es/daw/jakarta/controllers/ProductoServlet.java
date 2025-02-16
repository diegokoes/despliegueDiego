package es.daw.jakarta.controllers;

import es.daw.jakarta.bd.DaoProducto;
import es.daw.jakarta.models.Producto;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/productos")
public class ProductoServlet extends HttpServlet {

    private DaoProducto daoProducto;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            String dbSettingsPropsFilePath = getServletContext().getRealPath("/WEB-INF/JDBC.properties");
            daoProducto = new DaoProducto(dbSettingsPropsFilePath);
        } catch (SQLException e) {
            throw new ServletException("Error inicializando DaoProducto", e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Producto> productos = daoProducto.selectAll();
            request.setAttribute("productos", productos);
            request.getRequestDispatcher("/productos.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Error obteniendo productos", e);
        }
    }
}
