package org.example.atividade2_rev02;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FilmeDAO {

    public void inserir(Filme filme) throws SQLException {
        String sql = "INSERT INTO Filmes (titulo, genero, duracao) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, filme.getTitulo());
            stmt.setString(2, filme.getGenero());
            stmt.setInt(3, filme.getDuracao());
            stmt.executeUpdate();
        }
    }

    public void atualizar(Filme filme) throws SQLException {
        String sql = "UPDATE Filmes SET genero = ?, duracao = ? WHERE titulo = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, filme.getGenero());
            stmt.setInt(2, filme.getDuracao());
            stmt.setString(3, filme.getTitulo());
            stmt.executeUpdate();
        }
    }

    public void deletar(String titulo) throws SQLException {
        String sql = "DELETE FROM Filmes WHERE titulo = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, titulo);
            stmt.executeUpdate();
        }
    }

    public List<Filme> listarTodos() throws SQLException {
        String sql = "SELECT * FROM Filmes";
        List<Filme> lista = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Filme(
                        rs.getString("titulo"),
                        rs.getString("genero"),
                        rs.getInt("duracao")
                ));
            }
        }
        return lista;
    }

    public boolean existeFilme(String titulo) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Filmes WHERE UPPER(titulo) = UPPER(?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, titulo);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }
}
