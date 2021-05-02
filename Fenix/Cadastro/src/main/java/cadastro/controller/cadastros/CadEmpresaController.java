package cadastro.controller.cadastros;

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
import com.jfoenix.controls.JFXTextField;

import cadastro.controller.lista.ListaContatoController;
import cadastro.controller.lista.ListaEnderecoController;
import cadastro.utils.CadastroUtils;
import comum.form.CadastroFormPadrao;
import comum.model.constraints.Limitadores;
import comum.model.constraints.TecladoUtils;
import comum.model.constraints.Validadores;
import comum.model.enums.NotificacaoCadastro;
import comum.model.enums.Situacao;
import comum.model.exceptions.ExcessaoCadastro;
import comum.model.mask.Mascaras;
import comum.model.messages.Mensagens;
import comum.model.notification.Notificacoes;
import comum.model.utils.Utils;
import comum.model.utils.ViewGerenciador;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import servidor.dao.services.GenericService;
import servidor.dto.ImagemDTO;
import servidor.entities.Empresa;
import servidor.entities.EmpresaImagem;
import servidor.validations.ValidaEmpresa;

public class CadEmpresaController extends CadastroFormPadrao<Empresa> {

	private final static Logger LOGGER = Logger.getLogger(CadEmpresaController.class.getName());

	final static Image LogoPadrao = new Image(
			CadEmpresaController.class.getResourceAsStream("/cadastro/imagens/icon/icoPrincipal_100.png"));

	@FXML
	private JFXTextField txtId;

	@FXML
	private JFXTextField txtRazaoSocial;

	@FXML
	private JFXTextField txtNomeFantasia;

	@FXML
	private JFXTextField txtCnpj;

	@FXML
	private JFXComboBox<Situacao> cbSituacao;

	@FXML
	private JFXButton btnContato;

	@FXML
	private JFXButton btnEndereco;

	@FXML
	private ImageView imgLogo;

	@FXML
	private JFXButton btnExcluirImagem;

	@FXML
	private JFXButton btnProcurarImagem;

	private Set<EmpresaImagem> imagens;
	private GenericService<Empresa> service = new GenericService<Empresa>(Empresa.class);
	private String id;

	@Override
	public void onBtnVoltarClick() {
		ViewGerenciador.closeTela(spRoot);
		onClose();
	}

	@FXML
	public void onBtnEnderecoClick() {
		ListaEnderecoController ctn = (ListaEnderecoController) ViewGerenciador
				.loadTela(ListaEnderecoController.getFxmlLocate(), spRoot);
		ctn.initData(txtNomeFantasia.getText(), entidade.getEnderecos());
	}

	@FXML
	public void onBtnContatoClick() {
		ListaContatoController ctn = (ListaContatoController) ViewGerenciador
				.loadTela(ListaContatoController.getFxmlLocate(), spRoot);
		ctn.initData(txtRazaoSocial.getText(), entidade.getContatos());
	}

	@FXML
	public void onBtnProcurarImagemClick() {
		File caminhoImagem = Utils.procurarImagem();

		if (caminhoImagem != null) {
			try {
				if (imagens == null)
					imagens = new HashSet<EmpresaImagem>();

				imgLogo.setImage(new Image(caminhoImagem.toURI().toString()));
				imagens.addAll(CadastroUtils.processaImagens(caminhoImagem).stream()
						.map(imagem -> ImagemDTO.toEmpresaImagem(imagem)).collect(Collectors.toList()));
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
		setImagemPadrao();
	}

	public void onTxtIdExit() {
		if (!txtId.getText().isEmpty())
			carregar(pesquisar(new Empresa(Long.valueOf(txtId.getText()))));
		else if (txtId.getText().isEmpty() || txtId.getText().equalsIgnoreCase("0"))
			limpaCampos();
	}

	@Override
	protected void salvar(Empresa entidade) {
		service.salvar(entidade);
		limpaCampos();
	}

	@Override
	protected void excluir(Empresa entidade) {
		service.deletar(entidade.getId());
		limpaCampos();
	}

	@Override
	protected Empresa pesquisar(Empresa entidade) {
		return service.pesquisar(entidade.getId());
	}

	@Override
	public void carregar(Empresa entidade) {
		if (entidade == null)
			limpaCampos();
		else
			atualizaTela(entidade);
	}

	@Override
	protected boolean validaCampos() {
		try {
			return ValidaEmpresa.validaEmpresa(entidade);
		} catch (ExcessaoCadastro e) {
			e.printStackTrace();
		}

		txtNomeFantasia.validate();
		txtRazaoSocial.validate();
		txtCnpj.validate();

		Notificacoes.notificacao(AlertType.INFORMATION, Mensagens.AVISO, Mensagens.CADASTRO_SALVAR);
		return false;
	}

	@Override
	protected void limpaCampos() {
		entidade = new Empresa();
		txtId.setText("0");
		txtNomeFantasia.setText("");
		txtRazaoSocial.setText("");
		txtCnpj.setText("");
		cbSituacao.getSelectionModel().selectFirst();
		setImagemPadrao();
	}

	private CadEmpresaController setImagemPadrao() {
		imagens = null;
		imgLogo.setImage(LogoPadrao);
		return this;
	}

	private CadEmpresaController atualizaTela(Empresa empresa) {
		limpaCampos();

		this.edicao = true;
		this.entidade = empresa;

		txtId.setText(empresa.getId().toString());

		if (empresa.getRazaoSocial() != null && !empresa.getRazaoSocial().isEmpty())
			txtRazaoSocial.setText(empresa.getRazaoSocial());

		if (empresa.getNomeFantasia() != null && !empresa.getNomeFantasia().isEmpty())
			txtNomeFantasia.setText(empresa.getNomeFantasia());

		if (empresa.getCnpj() != null && !empresa.getCnpj().isEmpty())
			txtCnpj.setText(empresa.getCnpj());

		cbSituacao.getSelectionModel().select(empresa.getSituacao().ordinal());

		return this;
	}

	@Override
	public CadEmpresaController atualizaEntidade() {
		entidade.setRazaoSocial(txtRazaoSocial.getText());
		entidade.setNomeFantasia(txtNomeFantasia.getText());
		entidade.setCnpj(Utils.removeMascaras(txtCnpj.getText()));
		entidade.setSituacao(cbSituacao.getSelectionModel().getSelectedItem());
		entidade.setImagens(imagens);
		return this;
	}

	@Override
	protected String messagens(NotificacaoCadastro notificacao) {
		switch (notificacao) {
		case SalvoComSucesso:
			return "Empresa salva com sucesso.";
		case ExcluidoComSucesso:
			return "Empresa excluída com sucesso.";
		case EntidadeVazia:
			return "Não foi possível salvar, nenhuma empresa informada.";
		case ErroDuplicidade:
			return "Empresa informada já cadastrada.";
		default:
			return "";
		}
	}

	private CadEmpresaController configuraExitId() {
		txtId.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {

				if (newPropertyValue)
					id = txtId.getText();

				if (oldPropertyValue && !id.equalsIgnoreCase(txtId.getText()))
					onTxtIdExit();
			}
		});

		return this;
	}

	@Override
	public synchronized void inicializa(URL location, ResourceBundle resources) {
		Limitadores.setTextFieldInteger(txtId);

		Validadores.setTextFieldNotEmpty(txtNomeFantasia);
		Validadores.setTextFieldNotEmpty(txtRazaoSocial);
		Validadores.setTextFieldCNPJValidate(txtCnpj, true);

		Mascaras.cnpjField(txtCnpj);

		TecladoUtils.onEnterConfigureTab(txtNomeFantasia);
		TecladoUtils.onEnterConfigureTab(txtRazaoSocial);
		TecladoUtils.onEnterConfigureTab(txtCnpj);
		TecladoUtils.onEnterConfigureTab(cbSituacao);

		configuraExitId();

		cbSituacao.getItems().addAll(Situacao.values());
		cbSituacao.getSelectionModel().select(Situacao.ATIVO);

		txtId.setText("0");
	}

	public static URL getFxmlLocate() {
		return CadEmpresaController.class.getResource("/cadastro/view/cadastros/CadEmpresa.fxml");
	}

}
