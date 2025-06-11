package org.example.atividade2_rev02;

public class Filme {
    private String titulo;
    private String genero;
    private int duracao;

    public Filme(String titulo, String genero, int duracao) {
        this.titulo = titulo;
        this.genero = genero;
        this.duracao = duracao;
    }

    public String getTitulo() { return titulo; }
    public String getGenero() { return genero; }
    public int getDuracao() { return duracao; }

    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setGenero(String genero) { this.genero = genero; }
    public void setDuracao(int duracao) { this.duracao = duracao; }

    public String exibirCadastro() {
        return "Título: " + titulo + "\nGênero: " + genero + "\nDuração: " + duracao + " minutos";
    }

    public String pausar() {
        int minuto = (int) (Math.random() * duracao + 1);
        return "O filme foi pausado após " + minuto + " minutos.";
    }

    public String reproduzir() {
        return "O filme está sendo reproduzido...";
    }
}
