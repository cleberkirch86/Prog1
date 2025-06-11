package org.example.atividade2_rev02;

import java.sql.Connection;
import java.sql.Statement;

/**
 * Esta classe prepara todo o nosso banco de dados.
 * Você só precisa rodar ela UMA VEZ para criar todas as tabelas.
 */
public class CriarTabela {

    public static void main(String[] args) {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            System.out.println("Iniciando configuração do banco de dados...");

            // --- Tabela Carros ---
            stmt.execute("CREATE TABLE IF NOT EXISTS Carros ("
                    + " id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + " marca TEXT NOT NULL, modelo TEXT NOT NULL, ano INTEGER NOT NULL);");
            System.out.println("-> Tabela 'Carros' pronta.");

            // --- Tabela Livros ---
            stmt.execute("CREATE TABLE IF NOT EXISTS Livros ("
                    + " id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + " titulo TEXT NOT NULL, autor TEXT NOT NULL, paginas INTEGER NOT NULL);");
            System.out.println("-> Tabela 'Livros' pronta.");

            // --- Tabela Alunos ---
            stmt.execute("CREATE TABLE IF NOT EXISTS Alunos ("
                    + " id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + " nome TEXT NOT NULL, matricula TEXT NOT NULL UNIQUE, curso TEXT NOT NULL);");
            System.out.println("-> Tabela 'Alunos' pronta.");

            // --- Tabela Animais ---
            stmt.execute("CREATE TABLE IF NOT EXISTS Animais ("
                    + " id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + " nome TEXT NOT NULL, especie TEXT NOT NULL, idade INTEGER NOT NULL);");
            System.out.println("-> Tabela 'Animais' pronta.");

            // --- Tabela Celulares ---
            stmt.execute("CREATE TABLE IF NOT EXISTS Celulares ("
                    + " id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + " marca TEXT NOT NULL, modelo TEXT NOT NULL);");
            System.out.println("-> Tabela 'Celulares' pronta.");

            // --- Tabela Filmes ---
            stmt.execute("CREATE TABLE IF NOT EXISTS Filmes ("
                    + " id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + " titulo TEXT NOT NULL, genero TEXT NOT NULL, duracao INTEGER NOT NULL);");
            System.out.println("-> Tabela 'Filmes' pronta.");

            // --- Tabela TimesFutebol ---
            stmt.execute("CREATE TABLE IF NOT EXISTS TimesFutebol ("
                    + " id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + " nome TEXT NOT NULL, cidade TEXT NOT NULL, titulos INTEGER NOT NULL);");
            System.out.println("-> Tabela 'TimesFutebol' pronta.");


            System.out.println("\nBanco de dados configurado com sucesso!");

        } catch (Exception e) {
            System.err.println("Ocorreu um erro ao criar as tabelas:");
            e.printStackTrace();
        }
    }
}
