package org.example.atividade2_rev02;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class CelularController implements Initializable {

    @FXML private TextField txtMarca;
    @FXML private TextField txtModelo;
    @FXML private TextField mensagemField;
    @FXML private TableView<Celular> tabelaCelulares;
    @FXML private TableColumn<Celular, String> colunaMarca;
    @FXML private TableColumn<Celular, String> colunaModelo;

    private final CelularDAO dao = new CelularDAO();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colunaMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colunaModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));

        tabelaCelulares.getSelectionModel().selectedItemProperty().addListener(
                (obs, antigo, novo) -> {
                    if (novo != null) {
                        txtMarca.setText(novo.getMarca());
                        txtModelo.setText(novo.getModelo());
                    }
                }
        );
        carregarDadosNaTabela();
    }

    private void carregarDadosNaTabela() {
        try {
            List<Celular> lista = dao.listarTodos();
            ObservableList<Celular> observableList = FXCollections.observableArrayList(lista);
            tabelaCelulares.setItems(observableList);
        } catch (SQLException e) {
            mostrarAlerta("Erro de Banco de Dados", "Não foi possível carregar os celulares.");
        }
    }

    @FXML
    public void salvarCelular() {
        if (!validarCampos()) return;
        try {
            if (dao.existeCelular(txtMarca.getText(), txtModelo.getText())) {
                mostrarAlerta("Celular Duplicado", "Este celular (marca e modelo) já está cadastrado.");
                return;
            }
            Celular celular = new Celular(txtMarca.getText(), txtModelo.getText());
            dao.inserir(celular);
            mensagemField.setText("Celular salvo com sucesso!");
            limparCampos();
            carregarDadosNaTabela();
        } catch (SQLException e) {
            mostrarAlerta("Erro ao Salvar", "Ocorreu um erro: " + e.getMessage());
        }
    }

    @FXML
    public void atualizarCelular() {
        if (!validarCampos()) return;
        try {
            Celular celular = new Celular(txtMarca.getText(), txtModelo.getText());
            dao.atualizar(celular);
            mensagemField.setText("Celular atualizado com sucesso!");
            limparCampos();
            carregarDadosNaTabela();
        } catch (SQLException e) {
            mostrarAlerta("Erro ao Atualizar", "Ocorreu um erro: " + e.getMessage());
        }
    }

    @FXML
    public void deletarCelular() {
        if (txtModelo.getText().isEmpty()) {
            mostrarAlerta("Campo Vazio", "Selecione um celular na tabela ou digite um modelo para deletar.");
            return;
        }
        try {
            dao.deletar(txtModelo.getText());
            mensagemField.setText("Celular deletado com sucesso!");
            limparCampos();
            carregarDadosNaTabela();
        } catch (SQLException e) {
            mostrarAlerta("Erro ao Deletar", "Ocorreu um erro: " + e.getMessage());
        }
    }

    private boolean validarCampos() {
        if (txtMarca.getText().isEmpty() || txtModelo.getText().isEmpty()) {
            mostrarAlerta("Campos Vazios", "Por favor, preencha os campos de marca e modelo.");
            return false;
        }
        return true;
    }

    private void limparCampos() {
        txtMarca.clear();
        txtModelo.clear();
        tabelaCelulares.getSelectionModel().clearSelection();
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
