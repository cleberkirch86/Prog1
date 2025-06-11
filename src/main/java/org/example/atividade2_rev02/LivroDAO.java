package org.example.atividade2_rev02;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável por todas as operações de banco de dados para a entidade Livro.
 */
public class LivroDAO {

    /**
     * Salva um novo livro no banco.
     */
    public void inserir(Livro livro) throws SQLException {
        String sql = "INSERT INTO Livros (titulo, autor, paginas) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, livro.getTitulo());
            stmt.setString(2, livro.getAutor());
            stmt.setInt(3, livro.getPaginas());
            stmt.executeUpdate();
        }
    }

    /**
     * Atualiza um livro existente, encontrando-o pelo título.
     */
    public void atualizar(Livro livro) throws SQLException {
        String sql = "UPDATE Livros SET autor = ?, paginas = ? WHERE titulo = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, livro.getAutor());
            stmt.setInt(2, livro.getPaginas());
            stmt.setString(3, livro.getTitulo());
            stmt.executeUpdate();
        }
    }

    /**
     * Deleta um livro do banco, encontrando-o pelo título.
     */
    public void deletar(String titulo) throws SQLException {
        String sql = "DELETE FROM Livros WHERE titulo = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, titulo);
            stmt.executeUpdate();
        }
    }

    /**
     * Busca todos os livros cadastrados no banco.
     */
    public List<Livro> listarTodos() throws SQLException {
        String sql = "SELECT * FROM Livros";
        List<Livro> listaDeLivros = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Livro livro = new Livro(
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getInt("paginas")
                );
                listaDeLivros.add(livro);
            }
        }
        return listaDeLivros;
    }

    /**
     * Verifica se um livro já existe, ignorando maiúsculas/minúsculas.
     */
    public boolean existeLivro(String titulo, String autor) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Livros WHERE UPPER(titulo) = UPPER(?) AND UPPER(autor) = UPPER(?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, titulo);
            stmt.setString(2, autor);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return true;
                }
            }
        }
        return false;
    }
}
