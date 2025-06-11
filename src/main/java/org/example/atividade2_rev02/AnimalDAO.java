package org.example.atividade2_rev02;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnimalDAO {

    public void inserir(Animal animal) throws SQLException {
        String sql = "INSERT INTO Animais (nome, especie, idade) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, animal.getNome());
            stmt.setString(2, animal.getEspecie());
            stmt.setInt(3, animal.getIdade());
            stmt.executeUpdate();
        }
    }

    public void atualizar(Animal animal) throws SQLException {
        String sql = "UPDATE Animais SET especie = ?, idade = ? WHERE nome = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, animal.getEspecie());
            stmt.setInt(2, animal.getIdade());
            stmt.setString(3, animal.getNome());
            stmt.executeUpdate();
        }
    }

    public void deletar(String nome) throws SQLException {
        String sql = "DELETE FROM Animais WHERE nome = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.executeUpdate();
        }
    }

    public List<Animal> listarTodos() throws SQLException {
        String sql = "SELECT * FROM Animais";
        List<Animal> lista = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Animal(
                        rs.getString("especie"),
                        rs.getInt("idade"),
                        rs.getString("nome")
                ));
            }
        }
        return lista;
    }

    public boolean existeAnimal(String nome, String especie) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Animais WHERE UPPER(nome) = UPPER(?) AND UPPER(especie) = UPPER(?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, especie);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }
}
