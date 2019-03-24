package Utilitarios;

import Utilitarios.gui.DashboardController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class App extends Application {

	private static Scene mainScene;
	private static DashboardController mainController;
	
	final static String imgUtilitarios = "/Utilitarios/resources/images/icoEncode_48.png";

	@Override
	public void start(@SuppressWarnings("exports") Stage primaryStage) {
		try {
			// Classe inicial
			FXMLLoader loader = new FXMLLoader(getClass().getResource("gui/Dashboard.fxml"));
			AnchorPane scPnTelaPrincipal = loader.load();
			mainController = loader.getController();

			mainScene = new Scene(scPnTelaPrincipal); // Carrega a scena
			mainScene.setFill(Color.BLACK);

			primaryStage.setScene(mainScene); // Seta a cena principal
			primaryStage.setTitle("Utilitarios");
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/Utilitarios/resources/images/icoUtilitario_48.png")));
			primaryStage.initStyle(StageStyle.DECORATED);
			primaryStage.setMaximized(true);
			
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			    @Override
			    public void handle(WindowEvent arg0) {
			    	System.exit(0);
			    }
			});

			primaryStage.show(); // Mostra a tela.

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static DashboardController getMainController() {
		return mainController;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
