package org.example.atividade2_rev02;

public class Cadeira {
    // Atributos
    private String material;
    private String cor;
    private boolean dobravel;

    // Construtor
    public Cadeira(String material, String cor, boolean dobravel) {
        this.material = material;
        this.cor = cor;
        this.dobravel = dobravel;
    }

    // Métodos

    // Exibir informações da cadeira
    public void exibirInformacoes() {
        System.out.println("Material: " + material);
        System.out.println("Cor: " + cor);
        System.out.println("É dobrável? " + (dobravel ? "Sim" : "Não"));
    }

    // Alterar a cor da cadeira
    public void alterarCor(String novaCor) {
        this.cor = novaCor;
        System.out.println("A cor da cadeira foi alterada para: " + novaCor);
    }

    // Verificar se a cadeira é dobrável
    public void verificarDobradura() {
        if (dobravel) {
            System.out.println("Esta cadeira é dobrável.");
        } else {
            System.out.println("Esta cadeira não é dobrável.");
        }
    }

    // Exemplo de uso
    public static void main(String[] args) {
        Cadeira cadeira1 = new Cadeira("Madeira", "Marrom", true);
        cadeira1.exibirInformacoes();
        cadeira1.alterarCor("Preto");
        cadeira1.verificarDobradura();
    }
}