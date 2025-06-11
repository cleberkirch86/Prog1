package org.example.atividade2_rev02;

import java.util.Scanner;

public class Fracao {
    private int numerador;
    private int denominador;

    public Fracao(int numerador, int denominador) {
        if (denominador == 0) {
            throw new IllegalArgumentException("O denominador não pode ser zero.");
        }
        this.numerador = numerador;
        this.denominador = denominador;
        simplificar();
    }

    private void simplificar() {
        int gcd = gcd(numerador, denominador);
        numerador /= gcd;
        denominador /= gcd;
    }

    private int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    public Fracao somar(Fracao outra) {
        int novoNumerador = this.numerador * outra.denominador + outra.numerador * this.denominador;
        int novoDenominador = this.denominador * outra.denominador;
        return new Fracao(novoNumerador, novoDenominador);
    }

    public Fracao subtrair(Fracao outra) {
        int novoNumerador = this.numerador * outra.denominador - outra.numerador * this.denominador;
        int novoDenominador = this.denominador * outra.denominador;
        return new Fracao(novoNumerador, novoDenominador);
    }

    public Fracao multiplicar(Fracao outra) {
        int novoNumerador = this.numerador * outra.numerador;
        int novoDenominador = this.denominador * outra.denominador;
        return new Fracao(novoNumerador, novoDenominador);
    }

    public Fracao dividir(Fracao outra) {
        int novoNumerador = this.numerador * outra.denominador;
        int novoDenominador = this.denominador * outra.numerador;
        return new Fracao(novoNumerador, novoDenominador);
    }

    @Override
    public String toString() {
        return numerador + "/" + denominador;
    }


        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);

            // Solicitar a primeira fração
            System.out.println("Insira o numerador da primeira fração: ");
            int numerador1 = scanner.nextInt();
            System.out.println("Insira o denominador da primeira fração: ");
            int denominador1 = scanner.nextInt();
            Fracao fracao1 = new Fracao(numerador1, denominador1);

            // Solicitar a segunda fração
            System.out.println("Insira o numerador da segunda fração: ");
            int numerador2 = scanner.nextInt();
            System.out.println("Insira o denominador da segunda fração: ");
            int denominador2 = scanner.nextInt();
            Fracao fracao2 = new Fracao(numerador2, denominador2);

            // Realizar operações e exibir resultados
            Fracao soma = fracao1.somar(fracao2);
            Fracao subtracao = fracao1.subtrair(fracao2);
            Fracao multiplicacao = fracao1.multiplicar(fracao2);
            Fracao divisao = fracao1.dividir(fracao2);

            System.out.println("Soma: " + soma);
            System.out.println("Subtração: " + subtracao);
            System.out.println("Multiplicação: " + multiplicacao);
            System.out.println("Divisão: " + divisao);

        }
    }
