package org.example.atividade2_rev02;

/**
 * Esta é a classe "Molde" atualizada.
 * Para que a TableView do JavaFX consiga ler os dados, precisamos
 * fornecer métodos "get" públicos para cada atributo.
 */
public class Carros {

    // É uma boa prática de programação manter os atributos como "private"
    // e acessá-los através de métodos.
    private String marca;
    private String modelo;
    private int ano;

    /**
     * Construtor Padrão (Vazio)
     */
    public Carros() {
    }

    /**
     * Construtor com parâmetros.
     */
    public Carros(String marca, String modelo, int ano) {
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
    }

    // --- GETTERS ---
    // Métodos públicos que a TableView usará para ler os dados.

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public int getAno() {
        return ano;
    }

    // --- SETTERS (Opcional, mas bom ter) ---
    // Métodos para modificar os dados depois que o objeto foi criado.

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    // Os métodos de comportamento continuam os mesmos
    public void ligar() {
        System.out.println("O carro " + this.modelo + " está ligado.");
    }

    public void acelerar() {
        System.out.println("Acelerando o " + this.modelo + "...");
    }

    public void frear() {
        System.out.println("Freando o " + this.modelo + ".");
    }
}
