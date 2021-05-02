package cadastro.controller.cadastros;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import cadastro.controller.frame.PesquisaGenericaController;
import cadastro.utils.CadastroUtils;
import comum.form.CadastroFormPadrao;
import comum.model.constraints.Limitadores;
import comum.model.constraints.Validadores;
import comum.model.enums.NotificacaoCadastro;
import comum.model.enums.Situacao;
import comum.model.enums.TipoProduto;
import comum.model.exceptions.ExcessaoCadastro;
import comum.model.messages.Mensagens;
import comum.model.notification.Notificacoes;
import comum.model.utils.Utils;
import comum.model.utils.ViewGerenciador;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Spinner;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import servidor.dao.services.GenericService;
import servidor.dto.ImagemDTO;
import servidor.entities.Imagem;
import servidor.entities.Ncm;
import servidor.entities.Produto;
import servidor.entities.ProdutoImagem;
import servidor.validations.ValidaProduto;

public class CadProdutoController extends CadastroFormPadrao<Produto> {

	private final static Logger LOGGER = Logger.getLogger(CadProdutoController.class.getName());

	final static Image ImagemPadrao = new Image(
			CadUsuarioController.class.getResourceAsStream("/cadastro/imagens/white/geral/icoProdutoImage_256.png"));

	@FXML
	private JFXTabPane tpContainer;

	@FXML
	private Tab tabProduto;

	@FXML
	private Tab tabUnidade;

	// -------------------------------- Produto -------------------------------- //

	@FXML
	private JFXTextField txtId;

	@FXML
	private JFXTextField txtDescricao;

	@FXML
	private JFXTextField txtGrupo;

	@FXML
	private JFXTextField txtSubGrupo;

	@FXML
	private JFXTextField txtNCM;

	@FXML
	private JFXComboBox<Situacao> cbCategoria;

	@FXML
	private JFXComboBox<Situacao> cbOrigem;

	@FXML
	private Spinner<Double> spnPesoLiquido;

	@FXML
	private Spinner<Double> spnPesoBruto;

	@FXML
	private JFXTextArea txtAreaDescricaoComplementar;

	@FXML
	private JFXComboBox<Situacao> cbSituacao;

	@FXML
	private JFXComboBox<TipoProduto> cbTipoProduto;

	// O formato do arquivo incluido é um anchorpane "conforme criei a tela",
	// o nome aqui será o mesmo que no id do fxml incluido.
	@FXML
	private AnchorPane frameNCM;

	// Para utilizar o controlador do frame incluido, basta colocar a descrição
	// "Controller" na frente do id do fxml incluido conforme abaixo.
	@FXML
	private PesquisaGenericaController<Ncm> frameNCMController;

	@FXML
	private ImageView imgProduto;

	@FXML
	private JFXButton btnExcluirImagem;

	@FXML
	private JFXButton btnProcurarImagem;

	// -------------------------------- Unidade -------------------------------- //

	// O formato do arquivo incluido é um anchorpane "conforme criei a tela",
	// o nome aqui será o mesmo que no id do fxml incluido.
	@FXML
	private AnchorPane unidade;

	// Para utilizar o controlador do frame incluido, basta colocar a descrição
	// "Controller" na frente do id do fxml incluido conforme abaixo.
	@FXML
	private CadProdutoUnidadeController unidadeController;

	// -------------------------------- -------------------------------- //

	private Set<ProdutoImagem> imagens;
	private GenericService<Produto> service = new GenericService<Produto>(Produto.class);

	@Override
	public void onBtnVoltarClick() {
		ViewGerenciador.closeTela(spRoot);
		onClose();
	}

	@FXML
	public void onBtnProcurarImagemClick() {
		File caminhoImagem = Utils.procurarImagem();

		if (caminhoImagem != null) {
			try {
				if (imagens == null)
					imagens = new HashSet<ProdutoImagem>();

				imgProduto.setImage(new Image(caminhoImagem.toURI().toString()));
				imagens.addAll(CadastroUtils.processaImagens(caminhoImagem).stream()
						.map(imagem -> ImagemDTO.toProdutoImagem(imagem)).collect(Collectors.toList()));

			} catch (IOException e) {
				e.printStackTrace();
				LOGGER.log(Level.INFO, "{Erro ao carregar e processar a imagem}", e);
				Notificacoes.notificacao(AlertType.ERROR, Mensagens.ERRO, Mensagens.IMG_ERRO_CARREGAR);
				setImagemPadrao();
			}
		}
	}

	@FXML
	public void onBtnExcluirImagemClick() {
		for (Imagem img : imagens)
			img.setExcluir(true);

		setImagemPadrao();
	}

	@FXML
	public void onTxtIdClick() {
		txtId.getSelectedText();
	}

	@FXML
	public void onTxtIdEnter(KeyEvent e) {
		if (e.getCode().equals(KeyCode.ENTER)) {
			if (!txtId.getText().equalsIgnoreCase("0") && !txtId.getText().isEmpty())
				onTxtIdExit();
			else
				limpaCampos();

			Utils.clickTab();
		}
	}

	public void onTxtIdExit() {
		if (!txtId.getText().isEmpty())
			carregar(pesquisar(new Produto(Long.valueOf(txtId.getText()))));
		else if (txtId.getText().isEmpty())
			txtId.setText("0");
	}

	@Override
	protected void salvar(Produto entidade) {
		service.salvar(entidade);
		limpaCampos();
	}

	@Override
	protected void excluir(Produto entidade) {
		service.deletar(entidade.getId());
		limpaCampos();
	}

	@Override
	protected Produto pesquisar(Produto entidade) {
		return service.pesquisar(entidade.getId());
	}

	@Override
	public void carregar(Produto entidade) {
		if (entidade == null)
			limpaCampos();
		else
			atualizaTela(entidade);

	}

	@Override
	protected boolean validaCampos() {

		try {
			return ValidaProduto.validaProduto(entidade);
		} catch (ExcessaoCadastro e) {
			e.printStackTrace();
		}

		txtDescricao.validate();
		
		Notificacoes.notificacao(AlertType.INFORMATION, Mensagens.AVISO, Mensagens.CADASTRO_SALVAR);
		return false;
	}

	@Override
	protected void limpaCampos() {
		this.edicao = false;
		this.entidade = new Produto();

		// ----------------- Produto ----------------- //
		if (service != null)
			txtId.setText(service.proximoId());
		else
			txtId.setText("0");

		txtDescricao.setText("");
		txtGrupo.setText("");
		txtSubGrupo.setText("");
		txtNCM.setText("");
		spnPesoLiquido.getEditor().setText("0");
		spnPesoBruto.getEditor().setText("0");
		txtAreaDescricaoComplementar.setText("");
		cbSituacao.getSelectionModel().selectFirst();
		cbTipoProduto.getSelectionModel().selectFirst();

		// ----------------- Unidade ----------------- //
	

		// ----------------- Imagem ----------------- //
		setImagemPadrao();
	}

	private CadProdutoController setImagemPadrao() {
		imagens = null;
		imgProduto.setImage(ImagemPadrao);
		return this;
	}

	@Override
	public CadProdutoController atualizaEntidade() {
		entidade = new Produto();

		if (txtId.getText().isEmpty() || txtId.getText().equalsIgnoreCase("0"))
			entidade.setId(Long.valueOf(0));
		else
			entidade.setId(Long.valueOf(txtId.getText()));

		entidade.setDescricao(txtDescricao.getText());


		entidade.setTipoProduto(cbTipoProduto.getSelectionModel().getSelectedItem());
		entidade.setSituacao(cbSituacao.getSelectionModel().getSelectedItem());
		entidade.setImagens(imagens);

		return this;
	}

	private CadProdutoController atualizaTela(Produto produto) {
		limpaCampos();

		this.entidade = produto;

		txtId.setText(produto.getId().toString());

		if (produto.getDescricao() != null && !produto.getDescricao().isEmpty())
			txtDescricao.setText(produto.getDescricao());


		cbSituacao.getSelectionModel().select(produto.getSituacao().ordinal());
		cbTipoProduto.getSelectionModel().select(produto.getTipoProduto().ordinal());

		if (produto.getImagens() != null && produto.getImagens().size() > 0) {
			imagens = produto.getImagens();
			imgProduto
					.setImage(new Image(new ByteArrayInputStream(produto.getImagens().iterator().next().getImagem())));
		} else
			setImagemPadrao();

		return this;
	}

	@Override
	protected String messagens(NotificacaoCadastro notificacao) {
		switch (notificacao) {
		case SalvoComSucesso:
			return "Produto salvo com sucesso.";
		case ExcluidoComSucesso:
			return "Produto excluído com sucesso.";
		case EntidadeVazia:
			return "Não foi possível salvar, nenhum produto informado.";
		case ErroDuplicidade:
			return "Produto informado já cadastrado.";
		default:
			return "";
		}
	}

	@Override
	public synchronized void inicializa(URL location, ResourceBundle resources) {
		frameNCMController.setService(new GenericService<Ncm>(Ncm.class));
		frameNCMController.setPromptText("NCM");

		Validadores.setTextFieldNotEmpty(frameNCMController.txtFraPesquisa);
		Limitadores.setTextFieldInteger(txtId);
		Validadores.setTextFieldNotEmpty(txtDescricao);

		cbSituacao.getItems().addAll(Situacao.values());
		cbTipoProduto.getItems().addAll(TipoProduto.values());
		cbSituacao.getSelectionModel().select(Situacao.ATIVO);
		cbTipoProduto.getSelectionModel().select(TipoProduto.PRODUTOFINAL);

		txtId.setText("0");
		
		tpContainer.getSelectionModel().selectFirst();
	}

	public static URL getFxmlLocate() {
		return DialogCadContatoController.class.getResource("/cadastro/view/cadastros/CadProduto.fxml");
	}

}
