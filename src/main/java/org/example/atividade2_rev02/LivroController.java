package org.example.atividade2_rev02;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class LivroController implements Initializable {

    // --- Elementos da Tela ---
    @FXML private TextField txtTitulo;
    @FXML private TextField txtAutor;
    @FXML private TextField txtPaginas;
    @FXML private TextField mensagemField;
    @FXML private TableView<Livro> tabelaLivros;
    @FXML private TableColumn<Livro, String> colunaTitulo;
    @FXML private TableColumn<Livro, String> colunaAutor;
    @FXML private TableColumn<Livro, Integer> colunaPaginas;

    private final LivroDAO dao = new LivroDAO();

    /**
     * Roda uma vez, assim que a tela é criada.
     * Perfeito para configurar a tabela.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Configura as colunas para saberem de onde pegar os dados ("titulo", "autor", "paginas")
        colunaTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colunaAutor.setCellValueFactory(new PropertyValueFactory<>("autor"));
        colunaPaginas.setCellValueFactory(new PropertyValueFactory<>("paginas"));

        // Adiciona um "ouvinte" para o clique nas linhas da tabela
        tabelaLivros.getSelectionModel().selectedItemProperty().addListener(
                (obs, antigo, novo) -> {
                    if (novo != null) {
                        // Preenche os campos quando um livro é selecionado
                        txtTitulo.setText(novo.getTitulo());
                        txtAutor.setText(novo.getAutor());
                        txtPaginas.setText(String.valueOf(novo.getPaginas()));
                    }
                }
        );

        // Carrega os dados na tabela ao iniciar
        carregarDadosNaTabela();
    }

    private void carregarDadosNaTabela() {
        try {
            List<Livro> listaDeLivros = dao.listarTodos();
            ObservableList<Livro> observableList = FXCollections.observableArrayList(listaDeLivros);
            tabelaLivros.setItems(observableList);
        } catch (SQLException e) {
            mostrarAlerta("Erro de Banco de Dados", "Não foi possível carregar os livros.");
            e.printStackTrace();
        }
    }

    @FXML
    public void salvarLivro() {
        if (!validarCampos()) return;
        try {
            int paginas = Integer.parseInt(txtPaginas.getText());
            String titulo = txtTitulo.getText();
            String autor = txtAutor.getText();

            if (dao.existeLivro(titulo, autor)) {
                mostrarAlerta("Livro Duplicado", "Um livro com este título e autor já existe.");
                return;
            }

            Livro novoLivro = new Livro(titulo, autor, paginas);
            dao.inserir(novoLivro);
            mensagemField.setText("Livro salvo com sucesso!");
            limparCampos();
            carregarDadosNaTabela();
        } catch (Exception e) {
            mostrarAlerta("Erro", "Verifique se o número de páginas é um valor válido.");
            e.printStackTrace();
        }
    }

    @FXML
    public void atualizarLivro() {
        if (!validarCampos()) return;
        try {
            int paginas = Integer.parseInt(txtPaginas.getText());
            Livro livroAtualizado = new Livro(txtTitulo.getText(), txtAutor.getText(), paginas);
            dao.atualizar(livroAtualizado);
            mensagemField.setText("Livro atualizado com sucesso!");
            limparCampos();
            carregarDadosNaTabela();
        } catch (Exception e) {
            mostrarAlerta("Erro", "Não foi possível atualizar o livro.");
            e.printStackTrace();
        }
    }

    @FXML
    public void deletarLivro() {
        if (txtTitulo.getText().isEmpty()) {
            mostrarAlerta("Campo Vazio", "Selecione um livro na tabela ou digite um título para deletar.");
            return;
        }
        try {
            dao.deletar(txtTitulo.getText());
            mensagemField.setText("Livro deletado com sucesso!");
            limparCampos();
            carregarDadosNaTabela();
        } catch (SQLException e) {
            mostrarAlerta("Erro", "Não foi possível deletar o livro.");
            e.printStackTrace();
        }
    }

    // --- Métodos Ajudantes ---

    private boolean validarCampos() {
        if (txtTitulo.getText().isEmpty() || txtAutor.getText().isEmpty() || txtPaginas.getText().isEmpty()) {
            mostrarAlerta("Campos Vazios", "Por favor, preencha todos os campos.");
            return false;
        }
        return true;
    }

    private void limparCampos() {
        txtTitulo.clear();
        txtAutor.clear();
        txtPaginas.clear();
        tabelaLivros.getSelectionModel().clearSelection();
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
