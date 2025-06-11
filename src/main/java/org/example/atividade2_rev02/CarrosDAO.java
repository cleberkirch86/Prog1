package org.example.atividade2_rev02;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarrosDAO {

    // (Os métodos inserir, atualizar, deletar, buscarPorModelo e existeCarro continuam os mesmos)

    public void inserir(Carros carro) throws SQLException {
        String sql = "INSERT INTO Carros (marca, modelo, ano) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, carro.getMarca());
            stmt.setString(2, carro.getModelo());
            stmt.setInt(3, carro.getAno());
            stmt.executeUpdate();
        }
    }

    public void atualizar(Carros carro) throws SQLException {
        String sql = "UPDATE Carros SET marca = ?, ano = ? WHERE modelo = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, carro.getMarca());
            stmt.setInt(2, carro.getAno());
            stmt.setString(3, carro.getModelo());
            stmt.executeUpdate();
        }
    }

    public void deletar(String modelo) throws SQLException {
        String sql = "DELETE FROM Carros WHERE modelo = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, modelo);
            stmt.executeUpdate();
        }
    }

    public Carros buscarPorModelo(String modelo) throws SQLException {
        String sql = "SELECT * FROM Carros WHERE modelo = ?";
        Carros carro = null;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, modelo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    carro = new Carros(
                            rs.getString("marca"),
                            rs.getString("modelo"),
                            rs.getInt("ano")
                    );
                }
            }
        }
        return carro;
    }

    public boolean existeCarro(String marca, String modelo, int ano) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Carros WHERE UPPER(marca) = UPPER(?) AND UPPER(modelo) = UPPER(?) AND ano = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, marca);
            stmt.setString(2, modelo);
            stmt.setInt(3, ano);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * NOVO MÉTODO - READ ALL
     * Busca todos os carros cadastrados no banco e retorna uma lista.
     */
    public List<Carros> listarTodos() throws SQLException {
        String sql = "SELECT * FROM Carros";
        List<Carros> listaDeCarros = new ArrayList<>(); // Cria uma lista vazia

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Loop "while" para percorrer cada linha do resultado
            while (rs.next()) {
                // Para cada linha, cria um objeto Carro
                Carros carro = new Carros(
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        rs.getInt("ano")
                );
                // Adiciona o carro criado na nossa lista
                listaDeCarros.add(carro);
            }
        }
        return listaDeCarros; // Retorna a lista com todos os carros
    }
}
