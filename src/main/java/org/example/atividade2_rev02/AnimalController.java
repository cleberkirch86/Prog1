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

public class AnimalController implements Initializable {

    @FXML private TextField txtNome;
    @FXML private TextField txtEspecie;
    @FXML private TextField txtIdade;
    @FXML private TextField mensagemField;
    @FXML private TableView<Animal> tabelaAnimais;
    @FXML private TableColumn<Animal, String> colunaNome;
    @FXML private TableColumn<Animal, String> colunaEspecie;
    @FXML private TableColumn<Animal, Integer> colunaIdade;

    private final AnimalDAO dao = new AnimalDAO();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaEspecie.setCellValueFactory(new PropertyValueFactory<>("especie"));
        colunaIdade.setCellValueFactory(new PropertyValueFactory<>("idade"));

        tabelaAnimais.getSelectionModel().selectedItemProperty().addListener(
                (obs, antigo, novo) -> {
                    if (novo != null) {
                        txtNome.setText(novo.getNome());
                        txtEspecie.setText(novo.getEspecie());
                        txtIdade.setText(String.valueOf(novo.getIdade()));
                    }
                }
        );
        carregarDadosNaTabela();
    }

    private void carregarDadosNaTabela() {
        try {
            List<Animal> lista = dao.listarTodos();
            ObservableList<Animal> observableList = FXCollections.observableArrayList(lista);
            tabelaAnimais.setItems(observableList);
        } catch (SQLException e) {
            mostrarAlerta("Erro de Banco de Dados", "Não foi possível carregar os animais.");
        }
    }

    @FXML
    public void salvarAnimal() {
        if (!validarCampos()) return;
        try {
            String nome = txtNome.getText();
            String especie = txtEspecie.getText();
            int idade = Integer.parseInt(txtIdade.getText());

            if (dao.existeAnimal(nome, especie)) {
                mostrarAlerta("Animal Duplicado", "Um animal com este nome e espécie já existe.");
                return;
            }

            Animal animal = new Animal(especie, idade, nome);
            dao.inserir(animal);
            mensagemField.setText("Animal salvo com sucesso!");
            limparCampos();
            carregarDadosNaTabela();
        } catch (NumberFormatException e) {
            mostrarAlerta("Erro de Formato", "A idade deve ser um número válido.");
        } catch (SQLException e) {
            mostrarAlerta("Erro ao Salvar", "Ocorreu um erro: " + e.getMessage());
        }
    }

    @FXML
    public void atualizarAnimal() {
        if (!validarCampos()) return;
        try {
            int idade = Integer.parseInt(txtIdade.getText());
            Animal animal = new Animal(txtEspecie.getText(), idade, txtNome.getText());
            dao.atualizar(animal);
            mensagemField.setText("Animal atualizado com sucesso!");
            limparCampos();
            carregarDadosNaTabela();
        } catch (Exception e) {
            mostrarAlerta("Erro ao Atualizar", "Ocorreu um erro: " + e.getMessage());
        }
    }

    @FXML
    public void deletarAnimal() {
        if (txtNome.getText().isEmpty()) {
            mostrarAlerta("Campo Vazio", "Selecione um animal na tabela ou digite um nome para deletar.");
            return;
        }
        try {
            dao.deletar(txtNome.getText());
            mensagemField.setText("Animal deletado com sucesso!");
            limparCampos();
            carregarDadosNaTabela();
        } catch (SQLException e) {
            mostrarAlerta("Erro ao Deletar", "Ocorreu um erro: " + e.getMessage());
        }
    }

    private boolean validarCampos() {
        if (txtNome.getText().isEmpty() || txtEspecie.getText().isEmpty() || txtIdade.getText().isEmpty()) {
            mostrarAlerta("Campos Vazios", "Por favor, preencha todos os campos.");
            return false;
        }
        return true;
    }

    private void limparCampos() {
        txtNome.clear();
        txtEspecie.clear();
        txtIdade.clear();
        tabelaAnimais.getSelectionModel().clearSelection();
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
