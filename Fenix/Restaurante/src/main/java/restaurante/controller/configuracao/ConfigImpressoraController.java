package restaurante.controller.configuracao;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class ConfigImpressoraController implements Initializable {

	// private Map<URL, Stage> telas = new HashMap<>();

	@FXML
	Label lblImpressoraPadrao;

	@FXML
	ImageView imgViewImpPadrao;

	@FXML
	HBox hBoxImpPadrao;

	@FXML
	Label lblImpressoraDocumentos;

	@FXML
	ImageView imgViewImpDocumento;

	@FXML
	HBox hBoxImpDocumentos;

	@FXML
	Pane paneBackground;

	@FXML
	ScrollPane background;

	@FXML
	private Button btnConfirmar;
	@FXML
	private Button btnCancelar;
	@FXML
	private Button btnVoltar;

	@FXML
	public void onImpressoraPadraoAction() {

	}

	@FXML
	public void onImpressoraDocumentoAction() {

	}

	public void carregaImpressora() {

	}

	public void initialize(URL arg0, ResourceBundle arg1) {
		background.setFitToHeight(true);
		background.setFitToWidth(true);

	}
}
