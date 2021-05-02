package cadastro.controller.cadastros;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import cadastro.controller.lista.ListaContatoController;
import cadastro.controller.lista.ListaEnderecoController;
import comum.form.CadastroFormPadrao;
import comum.model.constraints.Limitadores;
import comum.model.constraints.TecladoUtils;
import comum.model.constraints.Validadores;
import comum.model.enums.Enquadramento;
import comum.model.enums.NotificacaoCadastro;
import comum.model.enums.Situacao;
import comum.model.enums.TipoPessoa;
import comum.model.exceptions.ExcessaoCadastro;
import comum.model.mask.Mascaras;
import comum.model.messages.Mensagens;
import comum.model.notification.Notificacoes;
import comum.model.utils.Utils;
import comum.model.utils.ViewGerenciador;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import servidor.dao.services.GenericService;
import servidor.entities.Cliente;
import servidor.validations.ValidaCliente;

public class CadClienteController extends CadastroFormPadrao<Cliente> {

	@FXML
	private JFXTextField txtId;

	@FXML
	private JFXTextField txtNome;

	@FXML
	private JFXTextField txtRazaoSocial;

	@FXML
	private JFXTextField txtCpf;

	@FXML
	private JFXTextField txtCnpj;

	@FXML
	private JFXTextArea txtAreaObservacao;

	@FXML
	private JFXDatePicker dtPkCadastro;

	@FXML
	private JFXComboBox<Situacao> cbSituacao;

	@FXML
	private JFXComboBox<Enquadramento> cbEnquadramento;

	@FXML
	private JFXComboBox<TipoPessoa> cbPessoaTipo;

	@FXML
	private JFXButton btnContato;

	@FXML
	private JFXButton btnEndereco;

	private GenericService<Cliente> service = new GenericService<Cliente>(Cliente.class);

	@Override
	public void onBtnVoltarClick() {
		ViewGerenciador.closeTela(spRoot);
		onClose();
	}

	@FXML
	public void onBtnEnderecoEnter(KeyEvent e) {
		if (e.getCode().equals(KeyCode.ENTER))
			btnEndereco.fire();
	}

	@FXML
	public void onBtnEnderecoClick() {
		ListaEnderecoController ctn = (ListaEnderecoController) ViewGerenciador
				.loadTela(ListaEnderecoController.getFxmlLocate(), spRoot);
		ctn.initData(txtNome.getText(), entidade.getEnderecos());
		ctn.setOnClose(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				entidade.setEnderecos(ctn.getEndereco());
			}
		});
	}

	@FXML
	public void onBtnContatoClick() {
		ListaContatoController ctn = (ListaContatoController) ViewGerenciador
				.loadTela(ListaContatoController.getFxmlLocate(), spRoot);
		ctn.initData(txtNome.getText(), entidade.getContatos());
		ctn.setOnClose(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				entidade.setContatos(ctn.getContato());
			}
		});
	}

	@Override
	protected void salvar(Cliente entidade) {
		service.salvar(entidade);
		limpaCampos();
	}

	@Override
	protected void excluir(Cliente entidade) {
		service.deletar(entidade.getId());
		limpaCampos();
	}

	@Override
	public void carregar(Cliente entidade) {
		if (entidade == null)
			limpaCampos();
		else
			atualizaTela(entidade);
	}

	@Override
	protected Cliente pesquisar(Cliente entidade) {
		return service.pesquisar(entidade.getId());
	}

	@Override
	protected boolean validaCampos() {
		try {
			return ValidaCliente.validaCliente(entidade);
		} catch (ExcessaoCadastro e) {
			e.printStackTrace();
			Notificacoes.notificacao(AlertType.INFORMATION, Mensagens.AVISO,
					e.getMessage().isEmpty() ? Mensagens.CADASTRO_SALVAR : e.getMessage());
		}

		txtNome.validate();

		if (!txtCpf.getText().isEmpty())
			if (!cbPessoaTipo.getValue().equals(TipoPessoa.JURIDICO))
				txtCpf.validate();
			else
				txtCpf.resetValidation();

		if (!txtCnpj.getText().isEmpty())
			if (!cbPessoaTipo.getValue().equals(TipoPessoa.FISICO))
				txtCnpj.validate();
			else
				txtCnpj.resetValidation();

		txtRazaoSocial.validate();

		return false;
	}

	@Override
	protected void limpaCampos() {
		this.edicao = false;
		this.entidade = new Cliente();

		if (service != null)
			txtId.setText(service.proximoId());
		else
			txtId.setText("0");

		txtNome.setText("");
		txtRazaoSocial.setText("");
		txtCpf.setText("");
		txtCnpj.setText("");
		txtAreaObservacao.setText("");
		cbPessoaTipo.getSelectionModel().selectFirst();
		cbSituacao.getSelectionModel().selectFirst();
		cbEnquadramento.getSelectionModel().selectFirst();
		dtPkCadastro.setValue(LocalDate.now());
	}

	@Override
	public CadClienteController atualizaEntidade() {
		entidade.setNomeSobrenome(txtNome.getText());
		entidade.setRazaoSocial(txtRazaoSocial.getText());
		entidade.setCpf(Utils.removeMascaras(txtCpf.getText()));
		entidade.setCnpj(Utils.removeMascaras(txtCnpj.getText()));
		entidade.setObservacao(txtAreaObservacao.getText());
		entidade.setTipoPessoa(cbPessoaTipo.getSelectionModel().getSelectedItem());
		entidade.setEnquadramento(cbEnquadramento.getSelectionModel().getSelectedItem());
		entidade.setSituacao(cbSituacao.getSelectionModel().getSelectedItem());
		return this;
	}

	// Devido a um erro no componente, caso venha do banco o valor null, estoura
	// erro na edição do campo.
	// Necessário a validação para casos em que ouve problemas com o registro no
	// banco.
	private CadClienteController atualizaTela(Cliente cliente) {
		limpaCampos();

		this.edicao = true;
		this.entidade = cliente;

		txtId.setText(cliente.getId().toString());

		if (cliente.getNomeSobrenome() != null && !cliente.getNomeSobrenome().isEmpty())
			txtNome.setText(cliente.getNomeSobrenome());

		if (cliente.getRazaoSocial() != null && !cliente.getRazaoSocial().isEmpty())
			txtRazaoSocial.setText(cliente.getRazaoSocial());

		if (cliente.getCpf() != null && !cliente.getCpf().isEmpty())
			txtCpf.setText(cliente.getCpf());

		if (cliente.getCnpj() != null && !cliente.getCnpj().isEmpty())
			txtCnpj.setText(cliente.getCnpj());

		if (cliente.getObservacao() != null && !cliente.getObservacao().isEmpty())
			txtAreaObservacao.setText(cliente.getObservacao());

		dtPkCadastro.setValue(cliente.getDataCadastro().toLocalDateTime().toLocalDate());

		cbSituacao.getSelectionModel().select(cliente.getSituacao().ordinal());
		cbEnquadramento.getSelectionModel().select(cliente.getTipoPessoa().ordinal());
		cbPessoaTipo.getSelectionModel().select(cliente.getEnquadramento().ordinal());

		return this;
	}

	private CadClienteController configuraExitCampos() {
		cbPessoaTipo.valueProperty().addListener(new ChangeListener<TipoPessoa>() {
			@Override
			public void changed(ObservableValue<? extends TipoPessoa> observable, TipoPessoa oldValue,
					TipoPessoa newValue) {
				if (newValue != null)
					txtRazaoSocial.setDisable(newValue.equals(TipoPessoa.FISICO));

				txtRazaoSocial.resetValidation();
				txtCpf.resetValidation();
				txtCnpj.resetValidation();
			}
		});

		return this;
	}

	@Override
	protected String messagens(NotificacaoCadastro notificacao) {
		switch (notificacao) {
		case SalvoComSucesso:
			return "Cliente salvo com sucesso.";
		case ExcluidoComSucesso:
			return "Cliente excluído com sucesso.";
		case EntidadeVazia:
			return "Não foi possível salvar, nenhum cliente informado.";
		case ErroDuplicidade:
			return "Cliente informado já cadastrado.";
		default:
			return "";
		}
	}

	private CadClienteController configuraValidate() {
		Validadores.setTextFieldNotEmpty(txtNome);
		Validadores.setTextFieldCPFValidate(cbPessoaTipo, txtCpf);
		Validadores.setTextFieldCNPJValidate(cbPessoaTipo, txtCnpj);
		Validadores.setTextFieldRazaoSocialValidate(cbPessoaTipo, txtRazaoSocial);
		return this;
	}

	@Override
	public synchronized void inicializa(URL arg0, ResourceBundle arg1) {
		Limitadores.setTextFieldInteger(txtId);

		configuraValidate();

		Mascaras.cpfField(txtCpf);
		Mascaras.cnpjField(txtCnpj);

		TecladoUtils.onEnterConfigureTab(txtCpf);
		TecladoUtils.onEnterConfigureTab(txtCnpj);
		TecladoUtils.onEnterConfigureTab(txtRazaoSocial);
		TecladoUtils.onEnterConfigureTab(cbSituacao);
		TecladoUtils.onEnterConfigureTab(cbEnquadramento);
		TecladoUtils.onEnterConfigureTab(cbPessoaTipo);

		configuraExitCampos();

		cbSituacao.getItems().addAll(Situacao.values());
		cbSituacao.getItems().remove(Situacao.EXCLUIDO);
		cbPessoaTipo.getItems().addAll(TipoPessoa.values());
		cbEnquadramento.getItems().addAll(Enquadramento.values());

		limpaCampos();
	}

	public static URL getFxmlLocate() {
		return CadClienteController.class.getResource("/cadastro/view/cadastros/CadCliente.fxml");
	}
}
