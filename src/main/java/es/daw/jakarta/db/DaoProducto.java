package es.daw.jakarta.bd;

import es.daw.jakarta.models.Producto;
import es.daw.jakarta.models.Categoria;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DaoProducto implements Dao<Producto> {

    private Connection con;

    public DaoProducto(String dbSettingsPropsFilePath) throws SQLException {
        super();
        con = DBConnection.getConnection(dbSettingsPropsFilePath);
    }

    @Override
    public Producto select(int id) throws SQLException {
        String sql = "SELECT p.*, c.nombre AS categoria_nombre FROM Productos p " +
                "INNER JOIN Categorias c ON p.categoria_id = c.id WHERE p.id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return obtenerProductoDesdeResultSet(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Producto> selectAll() throws SQLException {
        String sql = "SELECT p.*, c.nombre AS categoria_nombre FROM Productos p " +
                "INNER JOIN Categorias c ON p.categoria_id = c.id ORDER BY p.id ASC";
        List<Producto> productos = new ArrayList<>();
        try (PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Producto producto = obtenerProductoDesdeResultSet(rs);
                productos.add(producto);
            }
        }
        return productos;
    }

    @Override
    public void insert(Producto t) throws SQLException {
        String sql = "INSERT INTO Productos (nombre, precio, sku, fecha_registro, categoria_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, t.getNombre());
            ps.setInt(2, t.getPrecio());
            ps.setString(3, t.getSku());
            ps.setDate(4, Date.valueOf(t.getFechaRegistro()));
            ps.setLong(5, t.getCategoria().getId());
            ps.executeUpdate();
            // ! TODO : NO ENTIENDO ESTO DEL TODO, REPASAR Y MIRAR LUEGO OTRAS SOLUCIONES

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    t.setId(generatedKeys.getLong(1));
                }
            }
        }
    }

    @Override
    public void update(Producto t) throws SQLException {
        String sql = "UPDATE Productos SET nombre = ?, precio = ?, sku = ?,fecha_registro = categoria_id = ? WHERE id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, t.getNombre());
            ps.setInt(2, t.getPrecio());
            ps.setString(3, t.getSku());
            ps.setLong(4, t.getCategoria().getId());
            ps.setLong(5, t.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(Producto t) throws SQLException {
        delete(t.getId().intValue());
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Productos WHERE id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private Producto obtenerProductoDesdeResultSet(ResultSet rs) throws SQLException {
        Producto producto = new Producto();
        producto.setId(rs.getLong("id"));
        producto.setNombre(rs.getString("nombre"));
        producto.setPrecio(rs.getInt("precio"));
        producto.setSku(rs.getString("sku"));
        producto.setFechaRegistro(rs.getDate("fecha_registro").toLocalDate());

        Categoria categoria = new Categoria();
        categoria.setId(rs.getLong("categoria_id"));
        categoria.setNombre(rs.getString("categoria_nombre"));
        producto.setCategoria(categoria);

        return producto;
    }
}
