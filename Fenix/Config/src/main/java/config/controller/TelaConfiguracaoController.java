package config.controller;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import comum.model.alerts.Alertas;
import comum.model.config.ProcessaConfig;
import comum.model.constraints.Validadores;
import comum.model.entities.Configuracao;
import comum.model.enums.DataBase;
import config.App;
import config.util.Animacao;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import servidor.util.DBUtil;

public class TelaConfiguracaoController implements Initializable {

	@FXML
	AnchorPane background;

	@FXML
	Button btnExit;

	@FXML
	Button btnTestarConexao;

	@FXML
	Button btnConfirmar;

	@FXML
	Button btnCancelar;

	@FXML
	JFXComboBox<String> cbBase;

	@FXML
	JFXComboBox<DataBase> cbDataBase;

	@FXML
	JFXTextField txtIP;

	@FXML
	JFXTextField txtPorta;

	@FXML
	JFXTextField txtUsuario;

	@FXML
	JFXPasswordField pswSenha;

	@FXML
	JFXTextField txtCaminhoLog;

	@FXML
	Pane pnImgAviso;

	@FXML
	Label lblAviso;

	@FXML
	ImageView imgViewConexao;

	Configuracao dadosConexao;

	static String arquivo;

	@FXML
	public void onBtExitAction() {
		System.exit(0);
	}

	@FXML
	public void onBtnTesteClick() {
		lblAviso.setText("");
		lblAviso.setVisible(false);
		pnImgAviso.setVisible(false);
		cbBase.getItems().clear();

		if (validaCampos() && Validadores.validaIp(txtIP.getText())) {

			background.getScene().getRoot().setCursor(Cursor.WAIT);
			Animacao.inicia(imgViewConexao);

			// Criacao da thread para que esteja validando a conexao e nao trave a tela.
			Task<Boolean> verificaConexao = new Task<Boolean>() {

				@Override
				protected Boolean call() throws Exception {
					return DBUtil.testaConexaoMySQL(txtIP.getText().toString(), txtPorta.getText().toString(),
							cbBase.getSelectionModel().getSelectedItem().toString(), txtUsuario.getText().toString(),
							pswSenha.getText().toString());
				}

				@Override
				protected void succeeded() {
					Animacao.timeline.stop();
					Boolean conectado = getValue();

					if (conectado) {
						TelaConfiguracaoController.this.aviso("", "Conectado com sucesso!");

						imgViewConexao.setImage(Animacao.BD_CONECTADO);
						imgViewConexao.setFitWidth(App.imgBancoWidth);
						imgViewConexao.setFitHeight(App.imgBancoHeight);

						cbBase.getItems().addAll(DBUtil.bases);
						cbBase.setDisable(false);
						cbBase.getSelectionModel().select(0);

					} else {
						TelaConfiguracaoController.this.aviso("",
								"Não foi possivel conectar ao banco, verifique os dados de conexão!");

						imgViewConexao.setImage(Animacao.BD_ERRO);
						imgViewConexao.setFitWidth(App.imgBancoWidth);
						imgViewConexao.setFitHeight(App.imgBancoHeight);
					}
					background.getScene().getRoot().setCursor(null);
				}
			};
			Thread t = new Thread(verificaConexao);
			t.setDaemon(true);
			// t.start();
		}
	}

	@FXML
	public void onBtnCancelarClick() {
		if (Alertas.Alerta(AlertType.CONFIRMATION, "Sair",
				"Deseja realmente cancelar? \nToda a alteração será descartada.")) {
			System.exit(0);
		}
	}

	@FXML
	public void onBtnConfirmarClick() {
		if (validaCampos()) {
			processaDados();
			ProcessaConfig.gravaConfig(dadosConexao);
		}
	}

	@FXML
	public void onTxtPortaEventAction() {

	}

	@FXML
	public void onIpEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER"))
			txtPorta.requestFocus();
	}

	@FXML
	public void onPortaEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER"))
			cbDataBase.requestFocus();
	}

	@FXML
	public void onDataBaseEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER"))
			txtUsuario.requestFocus();
	}

	@FXML
	public void onUsuarioEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER"))
			pswSenha.requestFocus();
	}

	@FXML
	public void onSenhaEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER"))
			btnTestarConexao.requestFocus();
	}

	public void onTxtIPExitAction() {
		if (txtIP.textProperty().get().toString().isEmpty()) {
			txtIP.setStyle("");
		} else {
			if (Validadores.validaIp(txtIP.getText()))
				txtIP.setUnFocusColor(Color.GREEN);
			else
				txtIP.setUnFocusColor(Color.RED);
		}
	}

	public static String getArquivo() {
		return arquivo;
	}

	public static void setArquivo(String arquivos) {
		arquivo = arquivos;
	}

	public void aviso(String imagem, String texto) {
		lblAviso.setVisible(false);
		pnImgAviso.setVisible(false);

		if (!imagem.isEmpty()) {
			ImageView img = new ImageView(
					new Image(getClass().getResourceAsStream("/config/resources/imagens/" + imagem)));
			img.setFitWidth(20);
			img.setFitHeight(20);

			pnImgAviso.getChildren().clear();
			pnImgAviso.getChildren().add(img);
			pnImgAviso.setVisible(true);
		}

		if (!texto.isEmpty()) {
			lblAviso.setText(texto);
			lblAviso.setVisible(true);
		}
	}

	public void setConfig(Configuracao dadosConexao) {
		this.dadosConexao = dadosConexao;

		txtIP.setText(dadosConexao.getServerHost());
		txtPorta.setText(dadosConexao.getServerPort());
		txtUsuario.setText(dadosConexao.getServerUser());
		pswSenha.setText(dadosConexao.getServerPassword());
		cbDataBase.getSelectionModel().select(dadosConexao.getServerDatabase());
		txtCaminhoLog.setText(dadosConexao.getLogCaminho());
		validaCampos();
	}

	public void processaDados() {
		dadosConexao = new Configuracao(cbDataBase.getSelectionModel().getSelectedItem(), txtIP.getText().trim(),
				txtPorta.getText().trim(), cbBase.getSelectionModel().getSelectedItem(), txtUsuario.getText(),
				pswSenha.getText(), txtCaminhoLog.getText().trim());
	}

	public Boolean validaCampos() {
		Boolean result = false;
		Boolean informe = true; // Utilizado para apenas apresentar uma descrição.

		if (!txtIP.getText().isEmpty() && !txtPorta.getText().isEmpty() && !txtUsuario.getText().isEmpty()
				&& !pswSenha.getText().isEmpty()) {
			txtIP.setUnFocusColor(Color.GREEN);
			txtPorta.setUnFocusColor(Color.GREEN);
			txtUsuario.setUnFocusColor(Color.GREEN);
			pswSenha.setUnFocusColor(Color.GREEN);
			result = true;
		} else {
			if (txtIP.getText().isEmpty()) {
				txtIP.setUnFocusColor(Color.RED);
				if (informe) {
					TelaConfiguracaoController.this.aviso("icoSinaliza_48.png", "Porfavor, informe um ip válido!");
					informe = false;
				}
			}

			if (txtPorta.getText().isEmpty()) {
				txtPorta.setUnFocusColor(Color.RED);
				if (informe) {
					TelaConfiguracaoController.this.aviso("icoSinaliza_48.png", "Porfavor, informe a porta!");
					informe = false;
				}
			}

			if (txtUsuario.getText().isEmpty()) {
				txtUsuario.setUnFocusColor(Color.RED);
				if (informe) {

					TelaConfiguracaoController.this.aviso("icoSinaliza_48.png", "Porfavor, informe o usuário!");
					informe = false;
				}
			}

			if (pswSenha.getText().isEmpty()) {
				pswSenha.setUnFocusColor(Color.RED);
				if (informe) {
					TelaConfiguracaoController.this.aviso("icoSinaliza_48.png", "Porfavor, informe a senha!");
					informe = false;
				}
			}
		}
		return result;
	}

	@Override
	public synchronized void initialize(URL arg0, ResourceBundle arg1) {
		Validadores.setTextFieldNumberValidate(txtIP, true);

		txtIP.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (!newPropertyValue)
					onTxtIPExitAction();
				else
					txtIP.setUnFocusColor(Color.valueOf("#4d4d4d"));
			}
		});

		txtPorta.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (newPropertyValue) // Usado para limpar o stilo para que pinte quando entra
					txtPorta.setUnFocusColor(Color.valueOf("#4d4d4d"));
				else { // Após, na saida faz então a validacao.
					if (txtPorta.textProperty().get().toString().isEmpty())
						txtPorta.setUnFocusColor(Color.valueOf("#4d4d4d"));
					else
						txtPorta.setUnFocusColor(Color.GREEN);

				}
			}
		});

		txtUsuario.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (newPropertyValue)
					txtUsuario.setUnFocusColor(Color.valueOf("#4d4d4d"));
				else {
					if (txtUsuario.textProperty().get().toString().isEmpty())
						txtUsuario.setUnFocusColor(Color.valueOf("#4d4d4d"));
					else
						txtUsuario.setUnFocusColor(Color.GREEN);

				}
			}
		});

		pswSenha.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (newPropertyValue)
					pswSenha.setUnFocusColor(Color.valueOf("#4d4d4d"));
				else {
					if (pswSenha.textProperty().get().toString().isEmpty())
						pswSenha.setUnFocusColor(Color.valueOf("#4d4d4d"));
					else
						pswSenha.setUnFocusColor(Color.GREEN);
				}
			}
		});

		cbDataBase.getItems().addAll(DataBase.values());
		cbDataBase.getSelectionModel().select(DataBase.MySQL);
	}

	public static URL getFxmlLocate() {
		return TelaConfiguracaoController.class.getResource("/config/view/TelaConfiguracao.fxml");
	}

}
