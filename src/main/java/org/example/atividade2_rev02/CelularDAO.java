package org.example.atividade2_rev02;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe para conversar com a tabela 'Celulares' no banco de dados.
 */
public class CelularDAO {

    public void inserir(Celular celular) throws SQLException {
        String sql = "INSERT INTO Celulares (marca, modelo) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, celular.getMarca());
            stmt.setString(2, celular.getModelo());
            stmt.executeUpdate();
        }
    }

    public void atualizar(Celular celular) throws SQLException {
        String sql = "UPDATE Celulares SET marca = ? WHERE modelo = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, celular.getMarca());
            stmt.setString(2, celular.getModelo());
            stmt.executeUpdate();
        }
    }

    public void deletar(String modelo) throws SQLException {
        String sql = "DELETE FROM Celulares WHERE modelo = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, modelo);
            stmt.executeUpdate();
        }
    }

    public List<Celular> listarTodos() throws SQLException {
        String sql = "SELECT * FROM Celulares";
        List<Celular> lista = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Celular(
                        rs.getString("marca"),
                        rs.getString("modelo")
                ));
            }
        }
        return lista;
    }

    public boolean existeCelular(String marca, String modelo) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Celulares WHERE UPPER(marca) = UPPER(?) AND UPPER(modelo) = UPPER(?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, marca);
            stmt.setString(2, modelo);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }
}
