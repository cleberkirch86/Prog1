package org.example.atividade2_rev02;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.LinkedList;

public class RelogioController {

    @FXML private TextField txtMarca, txtModelo;
    @FXML private CheckBox chkMonitoramentoSono;
    @FXML private TextArea txtAreaRelogios;


    private LinkedList<RelogioInteligente> listaRelogios = new LinkedList<>();

    @FXML
    public void cadastrarRelogio() {
        String marca = txtMarca.getText();
        String modelo = txtModelo.getText();
        boolean monitoramentoSono = chkMonitoramentoSono.isSelected();

        if (marca.isEmpty() || modelo.isEmpty()) {
            txtAreaRelogios.setText("Por favor, preencha todos os campos.");
            return;
        }

        RelogioInteligente relogio = new RelogioInteligente(marca, modelo, monitoramentoSono);
        listaRelogios.add(relogio);
        txtAreaRelogios.setText("Relógio cadastrado: " + relogio);
        limparCampos();
    }

    @FXML
    public void listarRelogios() {
        if (listaRelogios.isEmpty()) {
            txtAreaRelogios.setText("Nenhum relógio cadastrado.");
            return;
        }

        StringBuilder sb = new StringBuilder("Relógios cadastrados:\n");
        for (RelogioInteligente relogio : listaRelogios) {
            sb.append("- ").append(relogio).append("\n");
        }
        txtAreaRelogios.setText(sb.toString());
    }

    @FXML
    public void limparCampos() {
        txtMarca.clear();
        txtModelo.clear();
        chkMonitoramentoSono.setSelected(false);
    }

    public void limparLista(){
        listaRelogios.clear();
        txtAreaRelogios.setText("Lista de Relógios foi apagada!");

    }
}
