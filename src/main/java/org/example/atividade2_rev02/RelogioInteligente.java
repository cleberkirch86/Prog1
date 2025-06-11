package org.example.atividade2_rev02;

public class RelogioInteligente {
    private String marca;
    private String modelo;
    private boolean monitoramentoSono;

    public RelogioInteligente(String marca, String modelo, boolean monitoramentoSono) {
        this.marca = marca;
        this.modelo = modelo;
        this.monitoramentoSono = monitoramentoSono;
    }

    public String getMarca() { return marca; }
    public String getModelo() { return modelo; }
    public boolean temMonitoramentoSono() { return monitoramentoSono; }

    @Override
    public String toString() {
        return modelo + " da " + marca + " - Monitoramento de Sono: " + (monitoramentoSono ? "Ativado" : "Desativado");
    }
}
