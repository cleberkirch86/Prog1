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

public class FilmeController implements Initializable {

    @FXML private TextField txtTitulo;
    @FXML private TextField txtGenero;
    @FXML private TextField txtDuracao;
    @FXML private TextField mensagemField;
    @FXML private TableView<Filme> tabelaFilmes;
    @FXML private TableColumn<Filme, String> colunaTitulo;
    @FXML private TableColumn<Filme, String> colunaGenero;
    @FXML private TableColumn<Filme, Integer> colunaDuracao;

    private final FilmeDAO dao = new FilmeDAO();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colunaTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colunaGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        colunaDuracao.setCellValueFactory(new PropertyValueFactory<>("duracao"));

        tabelaFilmes.getSelectionModel().selectedItemProperty().addListener(
                (obs, antigo, novo) -> {
                    if (novo != null) {
                        txtTitulo.setText(novo.getTitulo());
                        txtGenero.setText(novo.getGenero());
                        txtDuracao.setText(String.valueOf(novo.getDuracao()));
                    }
                }
        );
        carregarDadosNaTabela();
    }

    private void carregarDadosNaTabela() {
        try {
            List<Filme> lista = dao.listarTodos();
            ObservableList<Filme> observableList = FXCollections.observableArrayList(lista);
            tabelaFilmes.setItems(observableList);
        } catch (SQLException e) {
            mostrarAlerta("Erro de Banco de Dados", "Não foi possível carregar os filmes.");
        }
    }

    @FXML
    public void salvarFilme() {
        if (!validarCampos()) return;
        try {
            if (dao.existeFilme(txtTitulo.getText())) {
                mostrarAlerta("Filme Duplicado", "Um filme com este título já está cadastrado.");
                return;
            }
            int duracao = Integer.parseInt(txtDuracao.getText());
            Filme filme = new Filme(txtTitulo.getText(), txtGenero.getText(), duracao);
            dao.inserir(filme);
            mensagemField.setText("Filme salvo com sucesso!");
            limparCampos();
            carregarDadosNaTabela();
        } catch (NumberFormatException e) {
            mostrarAlerta("Erro de Formato", "A duração deve ser um número válido.");
        } catch (SQLException e) {
            mostrarAlerta("Erro ao Salvar", "Ocorreu um erro: " + e.getMessage());
        }
    }

    @FXML
    public void atualizarFilme() {
        if (!validarCampos()) return;
        try {
            int duracao = Integer.parseInt(txtDuracao.getText());
            Filme filme = new Filme(txtTitulo.getText(), txtGenero.getText(), duracao);
            dao.atualizar(filme);
            mensagemField.setText("Filme atualizado com sucesso!");
            limparCampos();
            carregarDadosNaTabela();
        } catch (Exception e) {
            mostrarAlerta("Erro ao Atualizar", "Ocorreu um erro: " + e.getMessage());
        }
    }

    @FXML
    public void deletarFilme() {
        if (txtTitulo.getText().isEmpty()) {
            mostrarAlerta("Campo Vazio", "Selecione um filme na tabela ou digite um título para deletar.");
            return;
        }
        try {
            dao.deletar(txtTitulo.getText());
            mensagemField.setText("Filme deletado com sucesso!");
            limparCampos();
            carregarDadosNaTabela();
        } catch (SQLException e) {
            mostrarAlerta("Erro ao Deletar", "Ocorreu um erro: " + e.getMessage());
        }
    }

    private boolean validarCampos() {
        if (txtTitulo.getText().isEmpty() || txtGenero.getText().isEmpty() || txtDuracao.getText().isEmpty()) {
            mostrarAlerta("Campos Vazios", "Por favor, preencha todos os campos.");
            return false;
        }
        return true;
    }

    private void limparCampos() {
        txtTitulo.clear();
        txtGenero.clear();
        txtDuracao.clear();
        tabelaFilmes.getSelectionModel().clearSelection();
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
