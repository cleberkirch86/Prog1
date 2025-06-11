package org.example.atividade2_rev02;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Esta é uma classe de teste simples para descobrir a versão exata
 * do banco de dados SQLite que está sendo usada pelo seu programa.
 */
public class VerificaVersaoSQLite {

    public static void main(String[] args) {
        System.out.println("Tentando verificar a versão do SQLite...");

        // O try-with-resources garante que a conexão com o banco seja fechada no final.
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            // 1. Executamos o comando SQL que pede a versão.
            ResultSet rs = stmt.executeQuery("SELECT sqlite_version();");

            // 2. Verificamos se obtivemos uma resposta.
            if (rs.next()) {
                // 3. Pegamos a versão da primeira coluna da resposta.
                String versao = rs.getString(1);

                // 4. Mostramos a versão no console.
                System.out.println("---------------------------------------------");
                System.out.println("A versão do seu banco de dados SQLite é: " + versao);
                System.out.println("---------------------------------------------");
            }

            // O ResultSet é fechado automaticamente aqui.

        } catch (Exception e) {
            System.err.println("Ocorreu um erro ao tentar verificar a versão:");
            e.printStackTrace(); // Mostra os detalhes do erro.
        }
    }
}
