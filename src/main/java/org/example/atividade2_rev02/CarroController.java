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
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

// A interface "Initializable" nos dá um método especial chamado "initialize"
// que roda automaticamente assim que a tela é criada.
public class CarroController implements Initializable {

    // --- CAMPOS DA TELA (existentes) ---
    @FXML private TextField marcaField;
    @FXML private TextField modeloField;
    @FXML private TextField anoField;
    @FXML private TextField mensagemField;

    // --- NOVOS CAMPOS PARA A TABELA ---
    @FXML private TableView<Carros> tabelaCarros;
    @FXML private TableColumn<Carros, String> colunaMarca;
    @FXML private TableColumn<Carros, String> colunaModelo;
    @FXML private TableColumn<Carros, Integer> colunaAno;

    private final CarrosDAO dao = new CarrosDAO();

    /**
     * Este método é chamado automaticamente pelo JavaFX depois que a tela FXML é carregada.
     * É o lugar perfeito para configurar nossa tabela e carregar os dados iniciais.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // 1. Configurar as colunas da tabela:
        // Dizemos a cada coluna de onde ela deve pegar o dado dentro do objeto "Carros".
        // O nome "marca", "modelo", "ano" deve ser EXATAMENTE igual ao nome da variável na classe Carros.
        colunaMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colunaModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        colunaAno.setCellValueFactory(new PropertyValueFactory<>("ano"));

        // 2. Adicionar um "ouvinte" de seleção:
        // Este código vai rodar toda vez que o usuário clicar em uma linha diferente da tabela.
        tabelaCarros.getSelectionModel().selectedItemProperty().addListener(
                (observador, valorAntigo, carroSelecionado) -> {
                    if (carroSelecionado != null) {
                        // Se um carro foi selecionado, preenchemos os campos de texto.
                        marcaField.setText(carroSelecionado.getMarca());
                        modeloField.setText(carroSelecionado.getModelo());
                        anoField.setText(String.valueOf(carroSelecionado.getAno()));
                    }
                }
        );

        // 3. Carregar os dados do banco na tabela pela primeira vez.
        carregarDadosNaTabela();
    }

    /**
     * Este método busca os dados no banco e atualiza a tabela na tela.
     */
    private void carregarDadosNaTabela() {
        try {
            // Pega a lista de carros do DAO.
            List<Carros> listaDeCarros = dao.listarTodos();
            // Converte a lista normal para uma "ObservableList", que a tabela do JavaFX entende.
            ObservableList<Carros> observableListCarros = FXCollections.observableArrayList(listaDeCarros);
            // Coloca os dados na tabela.
            tabelaCarros.setItems(observableListCarros);
        } catch (SQLException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro ao Carregar", "Não foi possível carregar os dados da tabela.");
            e.printStackTrace();
        }
    }


    @FXML
    public void salvarCarro() {
        if (!validarCampos("todos")) return;
        try {
            int ano = Integer.parseInt(anoField.getText());
            String marca = marcaField.getText();
            String modelo = modeloField.getText();

            if (dao.existeCarro(marca, modelo, ano)) {
                mostrarAlerta(Alert.AlertType.ERROR, "Carro Duplicado", "Este carro já está cadastrado!");
                return;
            }

            Carros carroNovo = new Carros(marca, modelo, ano);
            dao.inserir(carroNovo);
            mensagemField.setText("Carro salvo com sucesso!");
            limparCampos();
            carregarDadosNaTabela(); // ATUALIZA A TABELA
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro ao Salvar", "Ocorreu um erro inesperado.");
            e.printStackTrace();
        }
    }

    @FXML
    public void procurarCarro() {
        if (!validarCampos("modelo")) return;
        try {
            Carros carroEncontrado = dao.buscarPorModelo(modeloField.getText());
            if (carroEncontrado != null) {
                // A maneira de comparar objetos em uma lista é mais complexa,
                // então vamos percorrer a lista para encontrar o objeto correspondente.
                for(Carros carro : tabelaCarros.getItems()){
                    if(carro.getModelo().equals(carroEncontrado.getModelo())){
                        tabelaCarros.getSelectionModel().select(carro);
                        tabelaCarros.scrollTo(carro);
                        break; // Para o loop assim que encontrar
                    }
                }
                mensagemField.setText("Carro encontrado e selecionado na tabela.");
            } else {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Não Encontrado", "Nenhum carro com este modelo foi encontrado.");
            }
        } catch (SQLException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro de Busca", "Ocorreu um erro ao buscar no banco de dados.");
            e.printStackTrace();
        }
    }

    @FXML
    public void atualizarCarro() {
        if (!validarCampos("todos")) return;
        try {
            int ano = Integer.parseInt(anoField.getText());
            Carros carroAtualizado = new Carros(marcaField.getText(), modeloField.getText(), ano);
            dao.atualizar(carroAtualizado);
            mensagemField.setText("Carro atualizado com sucesso!");
            limparCampos();
            carregarDadosNaTabela(); // ATUALIZA A TABELA
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro ao Atualizar", "Não foi possível atualizar o carro.");
            e.printStackTrace();
        }
    }

    @FXML
    public void deletarCarro() {
        if (!validarCampos("modelo")) return;
        try {
            dao.deletar(modeloField.getText());
            mensagemField.setText("Carro deletado com sucesso!");
            limparCampos();
            carregarDadosNaTabela(); // ATUALIZA A TABELA
        } catch (SQLException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro ao Deletar", "Não foi possível deletar o carro.");
            e.printStackTrace();
        }
    }

    // --- Métodos Ajudantes (sem alteração) ---

    private boolean validarCampos(String tipo) {
        if (tipo.equals("todos") && (marcaField.getText().isEmpty() || modeloField.getText().isEmpty() || anoField.getText().isEmpty())) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos Vazios", "Por favor, preencha todos os campos.");
            return false;
        }
        if (tipo.equals("modelo") && modeloField.getText().isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campo Vazio", "Por favor, preencha o campo 'modelo'.");
            return false;
        }
        return true;
    }

    private void limparCampos() {
        marcaField.clear();
        modeloField.clear();
        anoField.clear();
        tabelaCarros.getSelectionModel().clearSelection(); // Limpa a seleção da tabela também
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }

    @FXML
    public void fechar() {
        Stage palco = (Stage) mensagemField.getScene().getWindow();
        palco.close();
    }
}
