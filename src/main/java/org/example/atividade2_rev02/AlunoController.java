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

public class AlunoController implements Initializable {

    // --- Elementos da Tela ---
    @FXML private TextField txtNome;
    @FXML private TextField txtMatricula;
    @FXML private TextField txtCurso;
    @FXML private TextField mensagemField;
    @FXML private TableView<Aluno> tabelaAlunos;
    @FXML private TableColumn<Aluno, String> colunaNome;
    @FXML private TableColumn<Aluno, String> colunaMatricula;
    @FXML private TableColumn<Aluno, String> colunaCurso;

    private final AlunoDAO dao = new AlunoDAO();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Configura as colunas da tabela
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        colunaCurso.setCellValueFactory(new PropertyValueFactory<>("curso"));

        // Adiciona um "ouvinte" para o clique nas linhas da tabela
        tabelaAlunos.getSelectionModel().selectedItemProperty().addListener(
                (obs, antigo, novo) -> {
                    if (novo != null) {
                        txtNome.setText(novo.getNome());
                        txtMatricula.setText(novo.getMatricula());
                        txtCurso.setText(novo.getCurso());
                    }
                }
        );
        carregarDadosNaTabela();
    }

    private void carregarDadosNaTabela() {
        try {
            List<Aluno> lista = dao.listarTodos();
            ObservableList<Aluno> observableList = FXCollections.observableArrayList(lista);
            tabelaAlunos.setItems(observableList);
        } catch (SQLException e) {
            mostrarAlerta("Erro de Banco de Dados", "Não foi possível carregar os alunos.");
        }
    }

    @FXML
    public void salvarAluno() {
        if (!validarCampos()) return;
        try {
            if (dao.existeMatricula(txtMatricula.getText())) {
                mostrarAlerta("Matrícula Duplicada", "Esta matrícula já está cadastrada.");
                return;
            }
            Aluno aluno = new Aluno(txtNome.getText(), txtMatricula.getText(), txtCurso.getText());
            dao.inserir(aluno);
            mensagemField.setText("Aluno salvo com sucesso!");
            limparCampos();
            carregarDadosNaTabela();
        } catch (SQLException e) {
            mostrarAlerta("Erro ao Salvar", "Ocorreu um erro: " + e.getMessage());
        }
    }

    @FXML
    public void atualizarAluno() {
        if (!validarCampos()) return;
        try {
            Aluno aluno = new Aluno(txtNome.getText(), txtMatricula.getText(), txtCurso.getText());
            dao.atualizar(aluno);
            mensagemField.setText("Aluno atualizado com sucesso!");
            limparCampos();
            carregarDadosNaTabela();
        } catch (SQLException e) {
            mostrarAlerta("Erro ao Atualizar", "Ocorreu um erro: " + e.getMessage());
        }
    }

    @FXML
    public void deletarAluno() {
        if (txtMatricula.getText().isEmpty()) {
            mostrarAlerta("Campo Vazio", "Selecione um aluno na tabela ou digite uma matrícula para deletar.");
            return;
        }
        try {
            dao.deletar(txtMatricula.getText());
            mensagemField.setText("Aluno deletado com sucesso!");
            limparCampos();
            carregarDadosNaTabela();
        } catch (SQLException e) {
            mostrarAlerta("Erro ao Deletar", "Ocorreu um erro: " + e.getMessage());
        }
    }

    private boolean validarCampos() {
        if (txtNome.getText().isEmpty() || txtMatricula.getText().isEmpty() || txtCurso.getText().isEmpty()) {
            mostrarAlerta("Campos Vazios", "Por favor, preencha todos os campos.");
            return false;
        }
        return true;
    }

    private void limparCampos() {
        txtNome.clear();
        txtMatricula.clear();
        txtCurso.clear();
        tabelaAlunos.getSelectionModel().clearSelection();
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
