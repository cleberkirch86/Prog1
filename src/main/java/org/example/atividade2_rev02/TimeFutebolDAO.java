package org.example.atividade2_rev02;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TimeFutebolDAO {

    public void inserir(TimeFutebol time) throws SQLException {
        String sql = "INSERT INTO TimesFutebol (nome, cidade, titulos) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, time.getNome());
            stmt.setString(2, time.getCidade());
            stmt.setInt(3, time.getTitulos());
            stmt.executeUpdate();
        }
    }

    public void atualizar(TimeFutebol time) throws SQLException {
        String sql = "UPDATE TimesFutebol SET cidade = ?, titulos = ? WHERE nome = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, time.getCidade());
            stmt.setInt(2, time.getTitulos());
            stmt.setString(3, time.getNome());
            stmt.executeUpdate();
        }
    }

    public void deletar(String nome) throws SQLException {
        String sql = "DELETE FROM TimesFutebol WHERE nome = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.executeUpdate();
        }
    }

    public List<TimeFutebol> listarTodos() throws SQLException {
        String sql = "SELECT * FROM TimesFutebol";
        List<TimeFutebol> lista = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new TimeFutebol(
                        rs.getString("nome"),
                        rs.getString("cidade"),
                        rs.getInt("titulos")
                ));
            }
        }
        return lista;
    }

    public boolean existeTime(String nome) throws SQLException {
        String sql = "SELECT COUNT(*) FROM TimesFutebol WHERE UPPER(nome) = UPPER(?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }
}
