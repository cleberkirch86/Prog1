package org.example.atividade2_rev02;

public class Animal {
    private String especie;
    private int idade;
    private String nome;

    public Animal(String especie, int idade, String nome) {
        this.especie = especie;
        this.idade = idade;
        this.nome = nome;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String emitirSom() {
        return nome + " emitiu um som típico de um(a) " + especie + "!";
    }

    public String alimentar() {
        return nome + " foi alimentado e está feliz!";
    }

    public String mover() {
        return nome + " está se movendo pelo ambiente!";
    }
}
