package comum.model.alerts.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextArea;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ErroController implements Initializable {

	public final static Image IMG_ERRO = new Image(
			ErroController.class.getResourceAsStream("/comum/resources/imagens/alerta/icoErro_48.png"));

	@FXML
	private Button btnOk;

	@FXML
	private Label lblTitulo;

	@FXML
	private ImageView imgIcone;

	@FXML
	private JFXTextArea txtTexto;

	private Integer topo, esquerda;

	public void setEventosBotoes(EventHandler<ActionEvent> ok) {
		btnOk.setOnAction(ok);
	}

	public void setTexto(String titulo, String texto) {
		if (!titulo.equals(""))
			lblTitulo.setText(titulo);

		if (!texto.equals(""))
			txtTexto.setText(texto);
	}

	public void setVisivel(Boolean titulo, Boolean imagem) {
		topo = 0;
		esquerda = 0;

		if (titulo)
			topo = 20;

		if (imagem)
			esquerda = 65;

		lblTitulo.setVisible(titulo);
		imgIcone.setVisible(imagem);
		txtTexto.setPadding(new Insets(topo, 0, 0, esquerda));
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	public static URL getFxmlLocate() {
		return ErroController.class.getResource("/comum/model/alerts/view/Erro.fxml");
	}
}
