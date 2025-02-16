package es.daw.jakarta.controllers;

import es.daw.jakarta.bd.DaoProducto;
import es.daw.jakarta.bd.DaoCategoria;
import es.daw.jakarta.models.Producto;
import es.daw.jakarta.models.Categoria;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@WebServlet("/productos/form")
public class ProductoFormServlet extends HttpServlet {

    private DaoProducto daoProducto;
    private DaoCategoria daoCategoria;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            String dbSettingsPropsFilePath = getServletContext().getRealPath("/WEB-INF/JDBC.properties");
            daoProducto = new DaoProducto(dbSettingsPropsFilePath);
            daoCategoria = new DaoCategoria(dbSettingsPropsFilePath);
        } catch (SQLException e) {
            throw new ServletException("Error inicializando los DAOs", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Categoria> categoriasList = daoCategoria.selectAll();
            Set<Categoria> categorias = new LinkedHashSet<>(categoriasList);

            request.setAttribute("categorias", categorias);

            String idParam = request.getParameter("id");
            if (idParam != null && !idParam.isEmpty()) {
                int id = Integer.parseInt(idParam);
                Producto producto = daoProducto.select(id);
                if (producto != null) {
                    request.setAttribute("producto", producto);
                } else {
                    response.sendRedirect(request.getContextPath() + "/productos");
                    return;
                }
            }

            request.getRequestDispatcher("/productoForm.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Error accediendo a la base de datos", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nombre = request.getParameter("nombre");
        String precioStr = request.getParameter("precio");
        String sku = request.getParameter("sku");
        String categoriaIdStr = request.getParameter("categoriaId");
        String idStr = request.getParameter("id");
        String fechaRegistroStr = request.getParameter("fechaRegistro");

        Map<String, String> errores = new HashMap<>();

        // validaciones con map coomo las de clase
        if (nombre == null || nombre.isEmpty()) {
            errores.put("nombre", "El nombre es requerido");
        }
        if (precioStr == null || precioStr.isEmpty()) {
            errores.put("precio", "El precio es obligatorio");
        }
        if (sku == null || sku.isEmpty()) {
            errores.put("sku", "El SKU es requerido");
        }
        if (categoriaIdStr == null || categoriaIdStr.isEmpty()) {
            errores.put("categoriaId", "La categoría es obligatoria");
        }
        if ((idStr == null || idStr.isEmpty()) && (fechaRegistroStr == null || fechaRegistroStr.isEmpty())) {
            errores.put("fechaRegistro", "La fecha de registro es obligatoria");
        }

        if (!errores.isEmpty()) {

            try {
                List<Categoria> categoriasList = daoCategoria.selectAll();
                Set<Categoria> categorias = new LinkedHashSet<>(categoriasList);

                request.setAttribute("categorias", categorias);
            } catch (SQLException e) {
                throw new ServletException("Error accediendo a la base de datos", e);
            }
            request.setAttribute("errores", errores);
            request.setAttribute("producto",
                    obtenerProductoDesdeParametros(nombre, precioStr, sku, categoriaIdStr, idStr, fechaRegistroStr));
            request.getRequestDispatcher("/productoForm.jsp").forward(request, response);
            return;
        }

        try {
            int precio = Integer.parseInt(precioStr);
            int categoriaId = Integer.parseInt(categoriaIdStr);
            Categoria categoria = daoCategoria.select(categoriaId);

            if (categoria == null) {
                errores.put("categoriaId", "Categoría no válida");
                try {
                    List<Categoria> categorias = daoCategoria.selectAll();
                    request.setAttribute("categorias", categorias);
                } catch (SQLException e) {
                    throw new ServletException("Error accediendo a la base de datos", e);
                }
                request.setAttribute("errores", errores);
                request.setAttribute("producto", obtenerProductoDesdeParametros(nombre, precioStr, sku, categoriaIdStr,
                        idStr, fechaRegistroStr));
                request.getRequestDispatcher("/productoForm.jsp").forward(request, response);
                return;
            }

            Producto producto = new Producto();
            producto.setNombre(nombre);
            producto.setPrecio(precio);
            producto.setSku(sku);
            producto.setCategoria(categoria);

            if (idStr == null || idStr.isEmpty()) {
                LocalDate fechaRegistro = LocalDate.parse(fechaRegistroStr);
                producto.setFechaRegistro(fechaRegistro);
                daoProducto.insert(producto);
            } else {

                int id = Integer.parseInt(idStr);
                producto.setId((long) id);
                Producto productoExistente = daoProducto.select(id);
                if (productoExistente != null) {
                    producto.setFechaRegistro(productoExistente.getFechaRegistro());
                }
                daoProducto.update(producto);
            }

            response.sendRedirect(request.getContextPath() + "/productos");
        } catch (NumberFormatException e) {
            errores.put("precio", "El precio debe ser un número válido");
            errores.put("categoriaId", "La categoría debe ser un número válido");
            try {
                List<Categoria> categorias = daoCategoria.selectAll();
                request.setAttribute("categorias", categorias);
            } catch (SQLException ex) {
                throw new ServletException("Error accediendo a la base de datos", ex);
            }
            request.setAttribute("errores", errores);
            request.setAttribute("producto",
                    obtenerProductoDesdeParametros(nombre, precioStr, sku, categoriaIdStr, idStr, fechaRegistroStr));
            request.getRequestDispatcher("/productoForm.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Error guardando el producto", e);
        }
    }

    private Producto obtenerProductoDesdeParametros(String nombre, String precioStr, String sku, String categoriaIdStr,
            String idStr, String fechaRegistroStr) {
        Producto producto = new Producto();
        producto.setNombre(nombre);
        try {
            producto.setPrecio(Integer.parseInt(precioStr));
        } catch (NumberFormatException e) {
            producto.setPrecio(0);
        }
        producto.setSku(sku);
        if (categoriaIdStr != null && !categoriaIdStr.isEmpty()) {
                int categoriaId = Integer.parseInt(categoriaIdStr);
                Categoria categoria = new Categoria();
                categoria.setId((long) categoriaId);
                producto.setCategoria(categoria);
           
        }
        if (idStr != null && !idStr.isEmpty()) {
                producto.setId(Long.parseLong(idStr));
        }
        if (fechaRegistroStr != null && !fechaRegistroStr.isEmpty()) {
          
                producto.setFechaRegistro(LocalDate.parse(fechaRegistroStr));
        
        }
        return producto;
    }

}
