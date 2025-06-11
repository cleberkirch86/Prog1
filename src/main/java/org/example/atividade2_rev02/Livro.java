package org.example.atividade2_rev02;

/**
 * Classe Molde para guardar os dados de um Livro.
 * Possui os getters e setters necessários para a TableView funcionar.
 */
public class Livro {

    private String titulo;
    private String autor;
    private int paginas;

    public Livro() {
    }

    public Livro(String titulo, String autor, int paginas) {
        this.titulo = titulo;
        this.autor = autor;
        this.paginas = paginas;
    }

    // --- Getters ---
    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public int getPaginas() {
        return paginas;
    }

    // --- Setters ---
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setPaginas(int paginas) {
        this.paginas = paginas;
    }
}
