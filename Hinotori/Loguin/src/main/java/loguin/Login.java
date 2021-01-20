package loguin;

import java.util.function.Consumer;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import loguin.controller.LoginController;

public class Login {

	private static Scene mainScene;

	// Metodo para fazer a tela se movimentar
	public static double xOffset = 0;
	public static double yOffset = 0;

	private static class Delta {
		double x, y;
	}

	final static Delta dragDelta = new Delta();
	final static BooleanProperty inDrag = new SimpleBooleanProperty(false);
	// Fim do metodo.

	public static LoginController start(Stage primaryStage, Consumer<Object> action) {
		LoginController controller = null;
		try {
			// Comentar esta linha para teste.
			FXMLLoader loader = new FXMLLoader(LoginController.getFxmlLocate());
			Parent scPnTelaPrincipal = loader.load();
			controller = loader.getController();
			controller.setLoguinAction(action);

			mainScene = new Scene(scPnTelaPrincipal); // Carrega a scena
			mainScene.setFill(Color.TRANSPARENT);

			// Eventos de clique do mouse realizando a movimenta��o da tela.
			mainScene.setOnMousePressed(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					dragDelta.x = primaryStage.getX() - event.getScreenX();
					dragDelta.y = primaryStage.getY() - event.getScreenY();
					xOffset = event.getSceneX();
					yOffset = event.getSceneY();
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
			primaryStage.setTitle("Hinotori");
			primaryStage.getIcons().add(new Image(Login.class.getResourceAsStream(LoginController.getIcon())));
			primaryStage.initStyle(StageStyle.TRANSPARENT);
			primaryStage.centerOnScreen();
			primaryStage.showAndWait();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return controller;
	}
}
