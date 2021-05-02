package comum.model.notification.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class NotificacaoController implements Initializable {

	@FXML
	private AnchorPane apBackground;

	@FXML
	private ImageView imgImagem;

	@FXML
	private Label lblTitulo;

	@FXML
	private Label lblTexto;

	public Double wheight;

	public Image getImagem() {
		return imgImagem.getImage();
	}

	public NotificacaoController setImagem(ImageView imagem) {
		this.imgImagem = imagem;
		return this;
	}

	public String getTitulo() {
		return lblTitulo.getText();
	}

	public NotificacaoController setTitulo(String titulo) {
		this.lblTitulo.setText(titulo);
		return this;
	}

	public String getTexto() {
		return lblTexto.getText();
	}

	public NotificacaoController setTexto(String texto) {
		lblTexto.setText(texto);

		if (texto.length() <= 80) {
			apBackground.setPrefSize(500, 47);
			apBackground.setMaxSize(500, 47);
			imgImagem.setFitHeight(35);
			imgImagem.setFitWidth(35);
			wheight = 60.0;
		} else {
			if (texto.length() <= 225) {
				apBackground.setPrefSize(500, 80);
				apBackground.setMaxSize(500, 80);
				imgImagem.setFitHeight(35);
				imgImagem.setFitWidth(35);
				wheight = 80.0;
			} else {
				apBackground.setPrefSize(650, 100);
				apBackground.setMaxSize(650, 100);
				imgImagem.setFitHeight(45);
				imgImagem.setFitWidth(45);
				wheight = 100.0;
			}
		}
		return this;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	public static URL getFxmlLocate() {
		return NotificacaoController.class.getResource("/comum/view/notification/Notificacao.fxml");
	}

}
