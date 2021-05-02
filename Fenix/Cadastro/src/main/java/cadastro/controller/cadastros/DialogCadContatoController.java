package cadastro.controller.cadastros;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import comum.form.CadastroDialogPadrao;
import comum.model.constraints.TecladoUtils;
import comum.model.constraints.Validadores;
import comum.model.enums.Situacao;
import comum.model.enums.TipoContato;
import comum.model.exceptions.ExcessaoCadastro;
import comum.model.mask.Mascaras;
import comum.model.messages.Mensagens;
import comum.model.notification.Notificacoes;
import comum.model.utils.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import servidor.entities.Contato;
import servidor.validations.ValidaContato;

public class DialogCadContatoController extends CadastroDialogPadrao<Contato> {

	@FXML
	private JFXComboBox<TipoContato> cbTipo;

	@FXML
	private JFXComboBox<Situacao> cbSituacao;

	@FXML
	private JFXTextField txtNome;

	@FXML
	private JFXTextField txtTelefone;

	@FXML
	private JFXTextField txtCelular;

	@FXML
	private JFXTextField txtEmail;

	@FXML
	private JFXTextArea txtAreaObservacao;

	private Set<Contato> contatos = new HashSet<>();
	private Contato contato;

	@Override
	public void onBtnConfirmarClick() {
		atualizaEntidade();
		if (validaCampos())
			salvar(contato);
	}

	@Override
	public void onBtnCancelarClick() {
		limpaCampos();
	}

	@Override
	protected void salvar(Contato entidade) {
		if (contatos.isEmpty())
			entidade.setPadrao(true);

		if (!contatos.contains(entidade))
			contatos.add(entidade);

		limpaCampos();
	}

	@Override
	public void carregar(Contato entidade) {
		if (entidade == null)
			limpaCampos();
		else
			atualizaTela(entidade);
	}

	@Override
	protected boolean validaCampos() {
		try {
			return ValidaContato.validaContato(contato);
		} catch (ExcessaoCadastro e) {
			e.printStackTrace();
			Notificacoes.notificacao(AlertType.INFORMATION, Mensagens.AVISO,
					e.getMessage().isEmpty() ? Mensagens.CADASTRO_SALVAR : e.getMessage());
		}

		txtNome.validate();

		if (txtCelular.getText().isEmpty() && txtTelefone.getText().isEmpty() && txtEmail.getText().isEmpty()) {
			txtCelular.validate();
			txtTelefone.validate();
			txtEmail.validate();
		} else {
			if (!txtCelular.getText().isEmpty())
				txtCelular.validate();

			if (!txtTelefone.getText().isEmpty())
				txtTelefone.validate();

			if (!txtEmail.getText().isEmpty())
				txtEmail.validate();
		}

		return false;
	}

	@Override
	public void onClose() {
		// TODO Auto-generated method stub
	}

	public void limpaCampos() {
		contato = new Contato();
		txtNome.setText("");
		txtTelefone.setText("");
		txtCelular.setText("");
		txtEmail.setText("");
		txtAreaObservacao.setText("");
		cbTipo.getSelectionModel().selectFirst();
		cbSituacao.getSelectionModel().selectFirst();

		txtNome.resetValidation();
		txtCelular.resetValidation();
		txtTelefone.resetValidation();
		txtEmail.resetValidation();
	}

	public Set<Contato> getContato() {
		return contatos;
	}

	@Override
	public DialogCadContatoController atualizaEntidade() {
		if (contato == null)
			contato = new Contato();

		contato.setNomeSobrenome(txtNome.getText());
		contato.setTelefone(Utils.removeMascaras(txtTelefone.getText()));
		contato.setCelular(Utils.removeMascaras(txtCelular.getText()));
		contato.setEmail(txtEmail.getText());
		contato.setObservacao(txtAreaObservacao.getText());
		contato.setSituacao(cbSituacao.getSelectionModel().getSelectedItem());
		contato.setTipoContato(cbTipo.getSelectionModel().getSelectedItem());

		return this;
	}

	public DialogCadContatoController atualizaTela(Contato contato) {
		limpaCampos();

		this.contato = contato;

		if (contato.getNomeSobrenome() != null && !contato.getNomeSobrenome().isEmpty())
			txtNome.setText(contato.getNomeSobrenome());

		if (contato.getTelefone() != null && !contato.getTelefone().isEmpty())
			txtTelefone.setText(contato.getTelefone());

		if (contato.getCelular() != null && !contato.getCelular().isEmpty())
			txtCelular.setText(contato.getCelular());

		if (contato.getEmail() != null && !contato.getEmail().isEmpty())
			txtEmail.setText(contato.getEmail());

		if (contato.getObservacao() != null && !contato.getObservacao().isEmpty())
			txtAreaObservacao.setText(contato.getObservacao());

		cbSituacao.getSelectionModel().select(contato.getSituacao().ordinal());
		cbTipo.getSelectionModel().select(contato.getTipoContato().ordinal());

		return this;
	}

	@Override
	public synchronized void inicializa(URL location, ResourceBundle resources) {
		Validadores.setTextFieldNotEmpty(txtNome);
		Validadores.setTextFieldEmailValidate(txtEmail, false);
		Validadores.setTextFieldTelefoneValidate(txtTelefone, false);
		Validadores.setTextFieldTelefoneValidate(txtCelular, false);

		Mascaras.foneField(txtTelefone);
		Mascaras.foneField(txtCelular);

		TecladoUtils.onEnterConfigureTab(txtNome);
		TecladoUtils.onEnterConfigureTab(txtTelefone);
		TecladoUtils.onEnterConfigureTab(txtCelular);
		TecladoUtils.onEnterConfigureTab(txtEmail);
		TecladoUtils.onEnterConfigureTab(cbTipo);
		TecladoUtils.onEnterConfigureTab(cbSituacao);

		cbSituacao.getItems().addAll(Situacao.values());
		cbSituacao.getItems().remove(Situacao.EXCLUIDO);
		cbTipo.getItems().addAll(TipoContato.values());

		limpaCampos();
	}

	public static URL getFxmlLocate() {
		return DialogCadContatoController.class.getResource("/cadastro/view/cadastros/DialogCadContato.fxml");
	}

}
