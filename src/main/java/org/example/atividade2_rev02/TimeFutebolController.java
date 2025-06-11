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

public class TimeFutebolController implements Initializable {

    @FXML private TextField txtNome;
    @FXML private TextField txtCidade;
    @FXML private TextField txtTitulos;
    @FXML private TextField mensagemField;
    @FXML private TableView<TimeFutebol> tabelaTimes;
    @FXML private TableColumn<TimeFutebol, String> colunaNome;
    @FXML private TableColumn<TimeFutebol, String> colunaCidade;
    @FXML private TableColumn<TimeFutebol, Integer> colunaTitulos;

    private final TimeFutebolDAO dao = new TimeFutebolDAO();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaCidade.setCellValueFactory(new PropertyValueFactory<>("cidade"));
        colunaTitulos.setCellValueFactory(new PropertyValueFactory<>("titulos"));

        tabelaTimes.getSelectionModel().selectedItemProperty().addListener(
                (obs, antigo, novo) -> {
                    if (novo != null) {
                        txtNome.setText(novo.getNome());
                        txtCidade.setText(novo.getCidade());
                        txtTitulos.setText(String.valueOf(novo.getTitulos()));
                    }
                }
        );
        carregarDadosNaTabela();
    }

    private void carregarDadosNaTabela() {
        try {
            List<TimeFutebol> lista = dao.listarTodos();
            ObservableList<TimeFutebol> observableList = FXCollections.observableArrayList(lista);
            tabelaTimes.setItems(observableList);
        } catch (SQLException e) {
            mostrarAlerta("Erro de Banco de Dados", "Não foi possível carregar os times.");
        }
    }

    @FXML
    public void salvarTime() {
        if (!validarCampos()) return;
        try {
            if (dao.existeTime(txtNome.getText())) {
                mostrarAlerta("Time Duplicado", "Um time com este nome já está cadastrado.");
                return;
            }
            int titulos = Integer.parseInt(txtTitulos.getText());
            TimeFutebol time = new TimeFutebol(txtNome.getText(), txtCidade.getText(), titulos);
            dao.inserir(time);
            mensagemField.setText("Time salvo com sucesso!");
            limparCampos();
            carregarDadosNaTabela();
        } catch (NumberFormatException e) {
            mostrarAlerta("Erro de Formato", "O número de títulos deve ser um valor válido.");
        } catch (SQLException e) {
            mostrarAlerta("Erro ao Salvar", "Ocorreu um erro: " + e.getMessage());
        }
    }

    @FXML
    public void atualizarTime() {
        if (!validarCampos()) return;
        try {
            int titulos = Integer.parseInt(txtTitulos.getText());
            TimeFutebol time = new TimeFutebol(txtNome.getText(), txtCidade.getText(), titulos);
            dao.atualizar(time);
            mensagemField.setText("Time atualizado com sucesso!");
            limparCampos();
            carregarDadosNaTabela();
        } catch (Exception e) {
            mostrarAlerta("Erro ao Atualizar", "Ocorreu um erro: " + e.getMessage());
        }
    }

    @FXML
    public void deletarTime() {
        if (txtNome.getText().isEmpty()) {
            mostrarAlerta("Campo Vazio", "Selecione um time na tabela ou digite um nome para deletar.");
            return;
        }
        try {
            dao.deletar(txtNome.getText());
            mensagemField.setText("Time deletado com sucesso!");
            limparCampos();
            carregarDadosNaTabela();
        } catch (SQLException e) {
            mostrarAlerta("Erro ao Deletar", "Ocorreu um erro: " + e.getMessage());
        }
    }

    private boolean validarCampos() {
        if (txtNome.getText().isEmpty() || txtCidade.getText().isEmpty() || txtTitulos.getText().isEmpty()) {
            mostrarAlerta("Campos Vazios", "Por favor, preencha todos os campos.");
            return false;
        }
        return true;
    }

    private void limparCampos() {
        txtNome.clear();
        txtCidade.clear();
        txtTitulos.clear();
        tabelaTimes.getSelectionModel().clearSelection();
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
