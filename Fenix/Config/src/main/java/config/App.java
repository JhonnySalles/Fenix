package config;

import config.controller.TelaConfiguracaoController;
import config.util.CarregaConfig;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class App extends Application {

	final public static double imgBancoWidth = 35;
	final public static double imgBancoHeight = 35;

	public static String arquivo;

	private static class Delta {
		double x, y;
	}

	final Delta dragDelta = new Delta();
	final BooleanProperty inDrag = new SimpleBooleanProperty(false);
	// Fim do metodo.

	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(TelaConfiguracaoController.getFxmlLocate());
			AnchorPane scPnTelaPrincipal = loader.load();

			Scene mainScene = new Scene(scPnTelaPrincipal); // Carrega a scena
			mainScene.setFill(Color.TRANSPARENT);

			// Eventos de clique do mouse realizando a movimentacao da tela.
			mainScene.setOnMousePressed(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					dragDelta.x = primaryStage.getX() - event.getScreenX();
					dragDelta.y = primaryStage.getY() - event.getScreenY();
				}
			});

			mainScene.setOnMouseDragged(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					primaryStage.setX(event.getScreenX() + dragDelta.x);
					primaryStage.setY(event.getScreenY() + dragDelta.y);
					primaryStage.getWidth();
					primaryStage.getHeight();
					primaryStage.getX();
					primaryStage.getY();
					inDrag.set(true);

				}
			}); // Fim do evento.

			primaryStage.setScene(mainScene); // Seta a cena principal
			primaryStage.setTitle("Config");
			primaryStage.getIcons()
					.add(new Image(getClass().getResourceAsStream("/config/imagens/icoConfiguracao.png")));
			primaryStage.initStyle(StageStyle.TRANSPARENT);
			primaryStage.show(); // Mostra a tela.

			TelaConfiguracaoController mainController = loader.getController();
			CarregaConfig processa = new CarregaConfig();
			processa.processaConfig(mainController); // Faz a leitura e carrega as config.

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	public static String getArquivo() {
		return arquivo;
	}

}
