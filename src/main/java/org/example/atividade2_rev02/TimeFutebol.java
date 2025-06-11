package org.example.atividade2_rev02;

public class TimeFutebol {

    private String nome;
    private String cidade;
    private int titulos;

    public TimeFutebol(String nome, String cidade, int titulos) {
        this.nome = nome;
        this.cidade = cidade;
        this.titulos = titulos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public int getTitulos() {
        return titulos;
    }

    public void setTitulos(int titulos) {
        this.titulos = titulos;
    }

    public void registrarVitoria() {
        this.titulos++;
    }

    public String exibirInformacoes() {
        return "Time: " + nome + "\nCidade: " + cidade + "\nTítulos: " + titulos;
    }

    public String desafiar(String adversario, String local) {
        return "⚽ " + nome + " desafiou " + adversario + " no estádio " + local + "!";
    }
}
