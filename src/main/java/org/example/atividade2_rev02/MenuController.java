package org.example.atividade2_rev02;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.Objects;

public class MenuController {

    public Button btnCarro;
    public Button btnCadeira;
    public Button btnAluno;
    public Button btnRefrigerante;
    public Button btnFilme;
    public Button btnAnimal;
    public Button btnTimeFutebol;
    public Button btnRelogio;
    public StackPane painelConteudo;
    @FXML
    private StackPane conteudoArea;



    public void abrirCarro() {
        carregarFXML("/org/example/atividade2_rev02/Carros.fxml");
    }

    public void abrirCadeira() {
        carregarFXML("/org/example/atividade2_rev02/LivroView.fxml");
    }

    public void abrirLivro() {
        carregarFXML("cadeira.fxml");
    }

    public void abrirRefrigerante() {
        carregarFXML("refrigerante.fxml");
    }

    public void abrirAluno() {
        carregarFXML("/org/example/atividade2_rev02/AlunoView.fxml"); // Abre a tela de aluno
    }


    public void abrirCelular() {
        carregarFXML("/org/example/atividade2_rev02/CelularView.fxml");
    }

    public void abrirFilme() {
        carregarFXML("/org/example/atividade2_rev02/FilmeView.fxml");
    }

    public void abrirAnimal() {
        carregarFXML("/org/example/atividade2_rev02/AnimalView.fxml");
    }

    public void abrirTime() {
        carregarFXML("/org/example/atividade2_rev02/TimeFutebol.fxml");
    }

    public void abrirRelogio() {
        carregarFXML("/org/example/atividade2_rev02/relogio.fxml");
    }



    public void sair() {
        System.exit(0);
    }

    private void carregarFXML(String fxml) {
        try {
            Node tela = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxml)));
            conteudoArea.getChildren().setAll(tela);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
