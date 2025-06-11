package org.example.atividade2_rev02;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe para conversar com a tabela 'Alunos' no banco de dados.
 */
public class AlunoDAO {

    public void inserir(Aluno aluno) throws SQLException {
        String sql = "INSERT INTO Alunos (nome, matricula, curso) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getMatricula());
            stmt.setString(3, aluno.getCurso());
            stmt.executeUpdate();
        }
    }

    public void atualizar(Aluno aluno) throws SQLException {
        String sql = "UPDATE Alunos SET nome = ?, curso = ? WHERE matricula = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getCurso());
            stmt.setString(3, aluno.getMatricula());
            stmt.executeUpdate();
        }
    }

    public void deletar(String matricula) throws SQLException {
        String sql = "DELETE FROM Alunos WHERE matricula = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, matricula);
            stmt.executeUpdate();
        }
    }

    public List<Aluno> listarTodos() throws SQLException {
        String sql = "SELECT * FROM Alunos";
        List<Aluno> lista = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Aluno(
                        rs.getString("nome"),
                        rs.getString("matricula"),
                        rs.getString("curso")
                ));
            }
        }
        return lista;
    }

    public boolean existeMatricula(String matricula) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Alunos WHERE matricula = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, matricula);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }
}
