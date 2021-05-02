package loguin.controller;

import java.net.URL;
import java.util.ResourceBundle;

import animatefx.animation.BounceOut;
import animatefx.animation.SlideInRight;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class LoadingController implements Initializable {

	@FXML
	private AnchorPane background;

	@FXML
	private ImageView logo;

	@FXML
	private Label lblLog;

	@FXML
	private AnchorPane paneEsquerda;

	@FXML
	private AnchorPane paneDireita;

	@FXML
	private Circle Circulo1;

	@FXML
	private Circle Circulo2;

	@FXML
	private Circle Circulo3;

	@FXML
	private Circle Circulo4;

	@FXML
	private Circle Circulo5;
	
	public void setLog(String menssagem) {
		lblLog.setText(menssagem);
	}

	private void animacao() {
		new BounceOut(Circulo1).setCycleCount(-1).setDelay(Duration.valueOf("800ms")).play();
		new BounceOut(Circulo2).setCycleCount(-1).setDelay(Duration.valueOf("900ms")).play();
		new BounceOut(Circulo3).setCycleCount(-1).setDelay(Duration.valueOf("1000ms")).play();
		new BounceOut(Circulo4).setCycleCount(-1).setDelay(Duration.valueOf("1100ms")).play();
		new BounceOut(Circulo5).setCycleCount(-1).setDelay(Duration.valueOf("1200ms")).play();
	}

	@Override
	public synchronized void initialize(URL arg0, ResourceBundle arg1) {
		SlideInRight slide = new SlideInRight(paneDireita);
		slide.setCycleCount(1).setDelay(Duration.valueOf("300ms")).setOnFinished(evento -> {
			animacao();
		});
		slide.play();
	}

	public static URL getFxmlLocate() {
		return LoadingController.class.getResource("/loguin/view/Loading.fxml");
	}

	public static Image getIconImage() {
		return new Image(LoadingController.class.getResourceAsStream("/loguin/images/icon/icoPrincipal_300.png"));
	}
}
