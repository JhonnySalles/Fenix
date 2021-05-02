package cadastro.controller.cadastros;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;

import cadastro.controller.frame.PesquisaGenericaController;
import comum.model.constraints.Validadores;
import comum.model.exceptions.ExcessaoCadastro;
import comum.model.messages.Mensagens;
import comum.model.notification.Notificacoes;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import servidor.dao.services.GenericService;
import servidor.entities.Empresa;
import servidor.entities.Produto;
import servidor.validations.ValidaProduto;

public class CadProdutoUnidadeController implements Initializable {

	private final static Logger LOGGER = Logger.getLogger(CadProdutoUnidadeController.class.getName());

	// O formato do arquivo incluido é um anchorpane "conforme criei a tela",
	// o nome aqui será o mesmo que no id do fxml incluido.
	@FXML
	private AnchorPane frameEmpresa;

	// Para utilizar o controlador do frame incluido, basta colocar a descrição
	// "Controller" na frente do id do fxml incluido conforme abaixo.
	@FXML
	private PesquisaGenericaController<Empresa> frameEmpresaController;

	@FXML
	private JFXTextField txtUnidade;

	@FXML
	private JFXTextField txtCodigoBarras;

	@FXML
	private JFXCheckBox cbUnidadePadrao;

	@FXML
	private JFXCheckBox cbEmbalagemPadrao;

	@FXML
	private Spinner<Double> spnQuantidade;

	@FXML
	private Spinner<Double> spnFatorConversao;

	@FXML
	private JFXButton btnAdicionar;

	@FXML
	private JFXButton btnRemover;

	@FXML
	private TableView<Produto> tbUnidade;

	@FXML
	private TableColumn<Produto, String> tbClFilial;

	@FXML
	private TableColumn<Produto, String> tbClUnidade;

	@FXML
	private TableColumn<Produto, String> tbClCodigoBarras;

	@FXML
	private TableColumn<Produto, String> tbClQuantidade;

	@FXML
	private TableColumn<Produto, String> tbClFatorConversao;

	@FXML
	private TableColumn<Produto, String> tbClUnidadePadrao;

	@FXML
	private TableColumn<Produto, String> tbClEmbalagemPadrao;

	protected void salvar(Produto entidade) {
		limpaCampos();
	}

	protected void excluir(Produto entidade) {
		limpaCampos();
	}

	public void carregar(Produto entidade) {
		if (entidade == null)
			limpaCampos();
		else
			atualizaTela(entidade);
	}

	protected boolean validaCampos() {

		try {
			return ValidaProduto.validaProduto(null);
		} catch (ExcessaoCadastro e) {
			e.printStackTrace();
		}

		frameEmpresaController.txtFraPesquisa.validate();

		Notificacoes.notificacao(AlertType.INFORMATION, Mensagens.AVISO, Mensagens.CADASTRO_SALVAR);
		return false;
	}

	protected void limpaCampos() {
		
		frameEmpresaController.limpaCampos();
		txtUnidade.setText("");
		txtCodigoBarras.setText("");
		cbUnidadePadrao.setSelected(true);
		cbEmbalagemPadrao.setSelected(false);
		spnQuantidade.getEditor().setText("0");
		spnFatorConversao.getEditor().setText("0");

	}

	public CadProdutoUnidadeController atualizaEntidade() {


		return this;
	}

	private CadProdutoUnidadeController atualizaTela(Produto produto) {
		limpaCampos();


		return this;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		frameEmpresaController.setService(new GenericService<Empresa>(Empresa.class));
		frameEmpresaController.setPromptText("Empresa");

		Validadores.setTextFieldNotEmpty(frameEmpresaController.txtFraPesquisa);
		Validadores.setTextFieldNotEmpty(txtUnidade);

		cbUnidadePadrao.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
			if (isNowSelected)
				cbEmbalagemPadrao.setSelected(false);
		});
		
		cbEmbalagemPadrao.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
			if (isNowSelected)
				cbUnidadePadrao.setSelected(false);
		});

	}

	public static URL getFxmlLocate() {
		return DialogCadContatoController.class.getResource("/cadastro/view/cadastros/CadProduto.fxml");
	}

}
