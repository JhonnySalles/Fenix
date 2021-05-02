package cadastro.controller.lista;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import cadastro.controller.cadastros.CadProdutoController;
import comum.form.ListaFormPadrao;
import comum.model.utils.ViewGerenciador;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import servidor.entities.Imagem;
import servidor.entities.Produto;

public class ListaProdutoController extends ListaFormPadrao {

	@FXML
	private TableView<Produto> tbProdutos;

	@FXML
	private TableColumn<Produto, String> tbClId;

	@FXML
	private TableColumn<Produto, List<Imagem>> tbClImagem;

	@FXML
	private TableColumn<Produto, String> tbClDescricao;

	@FXML
	private TableColumn<Produto, String> tbClCodigoBarras;

	@FXML
	private TableColumn<Produto, String> tbClUnidade;

	@FXML
	private TableColumn<Produto, String> tbClMarca;

	@FXML
	private TableColumn<Produto, Double> tbClPeso;

	@FXML
	private TableColumn<Produto, Double> tbClVolume;

	@FXML
	private TableColumn<Produto, String> tbClObservacao;

	@FXML
	private TableColumn<Produto, String> tbClDataCadastro;

	private List<Produto> produtos;
	private ObservableList<Produto> obsProdutos;
	private FilteredList<Produto> filteredData;

	@Override
	public void onBtnNovoClick() {
		CadProdutoController ctn = (CadProdutoController) ViewGerenciador.loadTela(CadProdutoController.getFxmlLocate(),
				spRoot);
		ctn.setOnClose(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				atualizar();
			}
		});
	}

	@Override
	public void onBtnExcluirClick() {

	}

	@Override
	public void onBtnEditarClick() {

	}

	@Override
	public void onBtnAtualizarClick() {
		atualizar();
	}

	public void atualizar() {

	}

	@Override
	public synchronized void inicializa(URL location, ResourceBundle resources) {

	}

	public static URL getFxmlLocate() {
		return ListaProdutoController.class.getResource("/cadastro/view/lista/ListaProduto.fxml");
	}

}
