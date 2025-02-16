package es.daw.jakarta.controllers;

import es.daw.jakarta.bd.DaoProducto;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/productos/eliminar")
public class ProductoEliminarServlet extends HttpServlet {

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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        try {
            if (idParam != null && !idParam.isEmpty()) {
                int id = Integer.parseInt(idParam);
                daoProducto.delete(id);
            }
            response.sendRedirect(request.getContextPath() + "/productos");
        } catch (SQLException e) {
            throw new ServletException("Error eliminando el producto", e);
        }
    }
}
