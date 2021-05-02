package comum.model.alerts.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ConclusaoController implements Initializable {

	public final static Image IMG_CONCLUSAO = new Image(
			ConclusaoController.class.getResourceAsStream("/comum/imagens/alerta/icoConfirma_48.png"));

	@FXML
	private Button btnOk;

	@FXML
	private Label lblTitulo;

	@FXML
	private ImageView imgIcone;

	@FXML
	private Label lblTexto;

	private Integer topo, esquerda;

	public void setEventosBotoes(EventHandler<ActionEvent> ok) {
		btnOk.setOnAction(ok);
	}

	public void setTexto(String titulo, String texto) {
		if (!titulo.equals(""))
			lblTitulo.setText(titulo);

		if (!texto.equals(""))
			lblTexto.setText(texto);
	}

	public void setVisivel(Boolean titulo, Boolean imagem) {
		topo = 0;
		esquerda = 0;

		if (titulo)
			topo = 15;

		if (imagem)
			esquerda = 65;

		lblTitulo.setVisible(titulo);
		imgIcone.setVisible(imagem);
		lblTexto.setPadding(new Insets(topo, 0, 0, esquerda));
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	public static URL getFxmlLocate() {
		return ConclusaoController.class.getResource("/comum/view/alerts/Conclusao.fxml");
	}
}
