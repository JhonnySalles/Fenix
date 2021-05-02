package loguin.controller;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import comum.model.alerts.Alertas;
import comum.model.constraints.Validadores;
import comum.model.encode.DecodeHash;
import comum.model.enums.UsuarioNivel;
import comum.model.exceptions.ExcessaoBd;
import comum.model.exceptions.ExcessaoCadastro;
import comum.model.exceptions.ExcessaoLogin;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import loguin.Login;
import servidor.dao.services.UsuarioService;
import servidor.entities.Usuario;
import servidor.validations.ValidaLogin;
import servidor.validations.ValidaUsuario;

public class LoginController implements Initializable {

	private final static Logger LOGGER = Logger.getLogger(LoginController.class.getName());
	
	@FXML
	private AnchorPane background;

	@FXML
	private AnchorPane apLogo;

	@FXML
	private AnchorPane apLoginCadastro;

	@FXML
	private VBox vbLogin;

	@FXML
	private VBox vbCadastro;

	@FXML
	private JFXComboBox<String> cbBoxUsuario;

	@FXML
	private JFXPasswordField pswSenha;

	@FXML
	private JFXButton btnLogin;

	@FXML
	private JFXTextField txtNome;

	@FXML
	private JFXTextField txtLoguin;

	@FXML
	private JFXPasswordField pswPassword;

	@FXML
	private JFXButton btnCadastrar;

	@FXML
	private JFXButton btnEscolha;
	
	@FXML
	private Label lblAviso;

	private UsuarioService service = new UsuarioService();

	@FXML
	private void onLoginClick() {
		ValidaLogin();
	}

	@FXML
	public void onCadastrarClick() {
		cadastraUsuario();
	}

	@FXML
	public void onSairClick() {
		System.exit(0);
	}

	@FXML
	public void onEscolhaClick() {
		if (btnEscolha.getAccessibleText().equalsIgnoreCase("CADASTRAR")) {
			btnEscolha.setAccessibleText("LOGAR");
			btnEscolha.setText("Logar");
			moveDireita();
		} else {
			btnEscolha.setAccessibleText("CADASTRAR");
			btnEscolha.setText("Cadastrar");
			moveEsquerda();
		}
	}

	private void moveDireita() {
		vbCadastro.setDisable(false);
		vbCadastro.setVisible(true);

		TranslateTransition trLogin = new TranslateTransition(Duration.millis(500), vbLogin);
		trLogin.setToX(-(vbLogin.getWidth() / 2));

		vbCadastro.setTranslateX(vbCadastro.getWidth() / 2);
		TranslateTransition trCadastro = new TranslateTransition(Duration.millis(500), vbCadastro);
		trCadastro.setToX(0);

		TranslateTransition trLogo = new TranslateTransition(Duration.millis(500), apLogo);
		trLogo.setToX(apLoginCadastro.getLayoutX() + (apLoginCadastro.getWidth() - apLogo.getWidth()) + 1);
		trLogo.setOnFinished(event -> {
			vbLogin.setDisable(true);
			vbLogin.setVisible(true);
		});

		trLogo.play();
		trLogin.play();
		trCadastro.play();
	}

	private void moveEsquerda() {
		vbLogin.setDisable(false);
		vbLogin.setVisible(true);

		TranslateTransition trLogin = new TranslateTransition(Duration.millis(500), vbLogin);
		trLogin.setToX(0);

		TranslateTransition trCadastro = new TranslateTransition(Duration.millis(500), vbCadastro);
		trCadastro.setToX(vbCadastro.getWidth() / 2);

		TranslateTransition trLogo = new TranslateTransition(Duration.millis(500), apLogo);
		trLogo.setToX(0);
		trLogo.setOnFinished(evento -> {
			vbCadastro.setDisable(true);
			vbCadastro.setVisible(false);
		});

		trLogo.play();
		trLogin.play();
		trCadastro.play();
	}

	private Consumer<Object> loguinAction;

	public void setLoguinAction(Consumer<Object> action) {
		loguinAction = action;
	}

	public void cadastraUsuario() {
		if (!txtNome.validate())
			return;

		if (!txtLoguin.validate())
			return;

		if (!pswPassword.validate())
			return;

		try {
			Usuario usuario = new Usuario(txtNome.getText(), txtLoguin.getText().toUpperCase(),
					"Cadastrado pelo Loguin.");
			usuario.setSenha(DecodeHash.CriptografaSenha(pswPassword.getText().trim()));

			ValidaUsuario.validaUsuario(usuario, true);

			UsuarioService service = new UsuarioService();
			usuario = service.cadastro(usuario);

			if (usuario.getNivel().equals(UsuarioNivel.ADMINISTRADOR))
				Alertas.Alerta(AlertType.INFORMATION, "Sucesso",
						"Cadastro realizado com sucesso, primeiro registro identificado, usuario cadastrado está apto a ser utilizado.");
			else
				Alertas.Alerta(AlertType.INFORMATION, "Sucesso",
						"Cadastro realizado com sucesso, favor solicitar ao administrador a ativação do cadastro.");
		
			limpaCampos();
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			Alertas.Alerta(AlertType.ERROR, "Erro de senha", "Erro ao criptografar a senha.");
			e.printStackTrace();
			LOGGER.log(Level.WARNING, "{Erro ao codificar a senha}", e);
		} catch (ExcessaoBd e) {
			Alertas.Alerta(AlertType.ERROR, "Erro ao conectar ao banco",
					"Erro ao tentar conectar ao banco, tente novamente.");
			e.printStackTrace();
		} catch (ExcessaoCadastro e) {
			Alertas.Alerta(AlertType.ERROR, "Erro ao efetuar o cadastro", e.getMessage());
			e.printStackTrace();
		}
	}

	public void ValidaLogin() {
		if (!cbBoxUsuario.validate())
			return;

		if (!pswSenha.validate())
			return;

		Usuario usuario = new Usuario(cbBoxUsuario.getSelectionModel().getSelectedItem().toString(), pswSenha.getText());

		try {
			usuario = ValidaLogin.validaLogin(usuario);

			if (usuario != null && loguinAction != null)
				loguinAction.accept(usuario);

			Login.getMain().close();
		} catch (ExcessaoLogin e) {
			lblAviso.setText( e.getMessage());
			e.printStackTrace();
		} catch (ExcessaoBd e) {
			Alertas.Alerta(AlertType.ERROR, "Erro ao efetuar o login",
					"Erro ao tentar conectar ao banco, tente novamente.");
			e.printStackTrace();
		}
	}
	
	public void limpaCampos() {
		txtLoguin.setText("");
		pswSenha.setText("");
		txtNome.setText("");
		pswPassword.setText("");
		pswSenha.setText("");
		cbBoxUsuario.getSelectionModel().clearSelection();
		cbBoxUsuario.getItems().clear();
		cbBoxUsuario.setItems(FXCollections.observableArrayList(service.carregaLogins()));
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		vbCadastro.setDisable(true);

		Validadores.setPasswordFieldNotEmpty(pswSenha);
		Validadores.setPasswordFieldNotEmpty(pswPassword);
		Validadores.setTextFieldNotEmpty(txtLoguin);
		Validadores.setTextFieldNotEmpty(txtNome);
		Validadores.setComboBoxNotEmpty(cbBoxUsuario);

		cbBoxUsuario.setItems(FXCollections.observableArrayList(service.carregaLogins()));
	}

	public static URL getFxmlLocate() {
		return LoginController.class.getResource("/loguin/view/Login.fxml");
	}

	public static String getIcon() {
		return "/loguin/images/icon/icoPrincipal_300.png";
	}

}
