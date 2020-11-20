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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import servidor.entities.Contato;
import servidor.validations.ValidaContato;

public class DialogCadContatoController extends CadastroDialogPadrao {

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

	private Set<Contato> contatos;
	private Contato contato;

	@Override
	public void onConfirmarKeyPress(KeyEvent e) {
		if (e.getCode().equals(KeyCode.TAB))
			btnConfirmar.fire();
	}

	@Override
	public void onBtnConfirmarClick() {
		atualizaEntidade();
		if (validaCampos())
			salvar(contato);
	}

	@Override
	public void onCancelarKeyPress(KeyEvent e) {
		if (e.getCode().equals(KeyCode.TAB))
			btnCancelar.fire();
	}

	@Override
	public void onBtnCancelarClick() {
		limpaCampos();
	}

	private Boolean validaCampos() {
		try {
			return ValidaContato.validaContato(contato);
		} catch (ExcessaoCadastro e) {
			e.printStackTrace();
		}

		try {
			ValidaContato.validaNome(contato.getNomeSobrenome());
		} catch (ExcessaoCadastro e) {
			txtNome.setUnFocusColor(Color.RED);
		}

		try {
			ValidaContato.validaCelular(contato.getCelular());
		} catch (ExcessaoCadastro e) {
			txtCelular.setUnFocusColor(Color.RED);
		}

		try {
			ValidaContato.validaTelefone(contato.getTelefone());
		} catch (ExcessaoCadastro e) {
			txtTelefone.setUnFocusColor(Color.RED);
		}

		try {
			ValidaContato.validaEmail(contato.getEmail());
		} catch (ExcessaoCadastro e) {
			txtEmail.setUnFocusColor(Color.RED);
		}

		Notificacoes.notificacao(AlertType.INFORMATION, Mensagens.AVISO, Mensagens.CADASTRO_SALVAR);

		return false;
	}

	private void salvar(Contato contato) {
		if (contatos == null)
			contatos = new HashSet<>();

		if (!contatos.contains(contato))
			contatos.add(contato);

		limpaCampos();
	}

	public Set<Contato> getContato() {
		return contatos;
	}

	private DialogCadContatoController atualizaEntidade() {
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

	public DialogCadContatoController limpaCampos() {
		contato = new Contato();

		cbTipo.getSelectionModel().selectFirst();
		cbSituacao.getSelectionModel().selectFirst();

		txtNome.setText("");
		txtTelefone.setText("");
		txtCelular.setText("");
		txtEmail.setText("");
		txtAreaObservacao.setText("");

		return this;
	}

	public DialogCadContatoController carregaContato(Contato contato) {
		limpaCampos();

		if (contato == null)
			return this;

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
		Validadores.setTextFieldEmailExit(txtEmail);
		Validadores.setTextFieldTelefoneExit(txtTelefone);
		Validadores.setTextFieldTelefoneExit(txtCelular);

		Mascaras.foneField(txtTelefone);
		Mascaras.foneField(txtCelular);

		TecladoUtils.onEnterConfigureTab(txtNome);
		TecladoUtils.onEnterConfigureTab(txtTelefone);
		TecladoUtils.onEnterConfigureTab(txtCelular);
		TecladoUtils.onEnterConfigureTab(txtEmail);
		TecladoUtils.onEnterConfigureTab(cbTipo);
		TecladoUtils.onEnterConfigureTab(cbSituacao);

		cbSituacao.getItems().addAll(Situacao.values());
		cbSituacao.getSelectionModel().selectFirst();
		cbTipo.getItems().addAll(TipoContato.values());
		cbTipo.getSelectionModel().selectFirst();
	}

	public static URL getFxmlLocate() {
		return DialogCadContatoController.class.getResource("/cadastro/view/cadastros/DialogCadContato.fxml");
	}

}