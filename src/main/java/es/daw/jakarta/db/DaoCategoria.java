package es.daw.jakarta.bd;

import es.daw.jakarta.models.Categoria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaoCategoria implements Dao<Categoria> {

    private Connection con;

    public DaoCategoria(String dbSettingsPropsFilePath) throws SQLException {
        super();
        con = DBConnection.getConnection(dbSettingsPropsFilePath);
    }

    @Override
    public Categoria select(int id) throws SQLException {
        String sql = "SELECT * FROM Categorias WHERE id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Categoria categoria = new Categoria();
                    categoria.setId(rs.getLong("id"));
                    categoria.setNombre(rs.getString("nombre"));
                    return categoria;
                }
            }
        }
        return null;
    }

    @Override
    public List<Categoria> selectAll() throws SQLException {
        String sql = "SELECT DISTINCT * FROM Categorias";
        List<Categoria> categorias = new ArrayList<>();
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Categoria categoria = new Categoria();
                categoria.setId(rs.getLong("id"));
                categoria.setNombre(rs.getString("nombre"));
                categorias.add(categoria);
            }
        }
        return categorias;
    }

    @Override
    public void insert(Categoria t) throws SQLException {
        String sql = "INSERT INTO Categorias (nombre) VALUES (?)";
        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, t.getNombre());
            ps.executeUpdate();
            //! TODO : NO ENTIENDO ESTO DEL TODO, REPASAR Y MIRAR LUEGO OTRAS SOLUCIONES
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    t.setId(generatedKeys.getLong(1));
                }
            }
        }
    }

    @Override
    public void update(Categoria t) throws SQLException {
        String sql = "UPDATE Categorias SET nombre = ? WHERE id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, t.getNombre());
            ps.setLong(2, t.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(Categoria t) throws SQLException {
        delete(t.getId().intValue());
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Categorias WHERE id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
