package config.util;

import config.App;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Animacao {

	final public static Image BD_CONECTADO = new Image(Animacao.class.getResourceAsStream("/config/resources/imagens/icoDataConectado_48.png"));
	final public static Image BD_ERRO = new Image(Animacao.class.getResourceAsStream("/config/resources/imagens/icoDataSemConexao_48.png"));
	final public static Image BD_NORMAL = new Image(Animacao.class.getResourceAsStream("/config/resources/imagens/icoDataBase_48.png"));
	final public static Image BD_CONECTANDO = new Image(
			Animacao.class.getResourceAsStream("/config/resources/imagens/icoDataEspera_48.png"));

	final public static Timeline timeline = new Timeline();

	static Boolean reproduzir = false;

	synchronized public static void inicia(ImageView img) {

		timeline.getKeyFrames().addAll(new KeyFrame(Duration.millis(250), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				img.setImage(BD_NORMAL);
				img.setFitWidth(App.imgBancoWidth);
				img.setFitHeight(App.imgBancoHeight);
			}
		}), new KeyFrame(Duration.millis(500), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				img.setImage(BD_CONECTANDO);
				img.setFitWidth(App.imgBancoWidth);
				img.setFitHeight(App.imgBancoHeight);
			}
		}));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();

	}
}
