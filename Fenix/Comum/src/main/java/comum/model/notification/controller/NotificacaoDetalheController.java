package comum.model.notification.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.jfoenix.controls.JFXButton;

import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class NotificacaoDetalheController {

	@FXML
	private HBox hbBackground;

	@FXML
	private Pane tipo;

	@FXML
	private Label lblTitulo;

	@FXML
	private HBox hbConteudo;

	@FXML
	private TextFlow container;

	@FXML
	private Text texto;

	@FXML
	private Label lbDataHora;

	@FXML
	private JFXButton btnClose;

	@FXML
	private HBox hbRodape;

	final static PseudoClass ALERTA = PseudoClass.getPseudoClass("Alerta");
	final static PseudoClass AVISO = PseudoClass.getPseudoClass("Aviso");
	final static PseudoClass ERRO = PseudoClass.getPseudoClass("Erro");
	final static PseudoClass SUCESSO = PseudoClass.getPseudoClass("Sucesso");

	public HBox getRoot() {
		return hbBackground;
	}

	public TextFlow getContainerTexto() {
		return container;
	}

	public String getTitulo() {
		return lblTitulo.getText();
	}

	public NotificacaoDetalheController setTitulo(String titulo) {
		this.lblTitulo.setText(titulo);
		return this;
	}

	public String getTexto() {
		return texto.getText();
	}

	public NotificacaoDetalheController setTexto(String texto) {
		this.texto.setText(texto);
		return this;
	}

	public String getDataHora() {
		return lbDataHora.getText();
	}

	public NotificacaoDetalheController setDataHora(LocalDateTime hora) {
		lbDataHora.setText(hora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
		return this;
	}

	public void setOnCloseAction(EventHandler<ActionEvent> value) {
		btnClose.setOnAction(value);
	}

	public HBox getRodape() {
		return hbRodape;
	}

	public void setTipo(AlertType alerta) {
		switch (alerta) {
		case WARNING:
			tipo.pseudoClassStateChanged(ALERTA, true);
			break;

		case INFORMATION:
			tipo.pseudoClassStateChanged(AVISO, true);
			break;

		case ERROR:
			tipo.pseudoClassStateChanged(ERRO, true);
			break;

		case NONE:
			tipo.pseudoClassStateChanged(SUCESSO, true);
			break;
		default:
			break;
		}
	}

	// Para criar a tela e jogar dentro da lista, esta classe irá trabalhar como uma
	// celula
	public NotificacaoDetalheController() {
		FXMLLoader fxmlLoader = new FXMLLoader(NotificacaoDetalheController.getFxmlLocate());
		fxmlLoader.setController(this);
		try {
			fxmlLoader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static URL getFxmlLocate() {
		return NotificacaoDetalheController.class.getResource("/comum/view/notification/NotificacaoDetalhe.fxml");
	}

}
