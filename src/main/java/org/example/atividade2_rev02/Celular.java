package org.example.atividade2_rev02;

/**
 * Classe Molde para guardar os dados de um Celular.
 */
public class Celular {

    private String marca;
    private String modelo;

    // Construtor vazio, importante para o JavaFX.
    public Celular() {}

    public Celular(String marca, String modelo) {
        this.marca = marca;
        this.modelo = modelo;
    }

    // --- Getters e Setters ---
    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
}
