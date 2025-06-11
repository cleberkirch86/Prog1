package org.example.atividade2_rev02;

public class Refrigerante {
    // Atributos
    private String nome;
    private double volume; // em litros
    private boolean gasoso;

    // Construtor
    public Refrigerante(String nome, double volume, boolean gasoso) {
        this.nome = nome;
        this.volume = volume;
        this.gasoso = gasoso;
    }

    // Métodos

    // Exibir informações do refrigerante
    public void exibirInformacoes() {
        System.out.println("Nome: " + nome);
        System.out.println("Volume: " + volume + "L");
        System.out.println("É gasoso? " + (gasoso ? "Sim" : "Não"));
    }

    // Alterar o volume
    public void alterarVolume(double novoVolume) {
        this.volume = novoVolume;
        System.out.println("O volume foi alterado para: " + volume + "L");
    }

    // Verificar se é gasoso
    public void verificarGasoso() {
        if (gasoso) {
            System.out.println("Este refrigerante é gasoso.");
        } else {
            System.out.println("Este refrigerante não é gasoso.");
        }
    }

    // Exemplo de uso
    public static void main(String[] args) {
        Refrigerante refri = new Refrigerante("Cola", 2.0, true);
        refri.exibirInformacoes();
        refri.alterarVolume(1.5);
        refri.verificarGasoso();
    }
}