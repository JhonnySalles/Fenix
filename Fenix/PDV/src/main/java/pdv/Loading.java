package pdv;

import java.util.logging.Level;
import java.util.logging.Logger;

import comum.model.alerts.Alertas;
import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import loguin.Login;
import loguin.controller.LoadingController;
import servidor.entities.Usuario;

public class Loading extends Preloader {

	private final static Logger LOGGER = Logger.getLogger(Loading.class.getName());

	public static interface CredentialsConsumer {
		public void setCredential(Usuario user);
	}

	CredentialsConsumer consumer = null;

	private static Stage UPDATEDB;
	private static Stage LOGUIN;
	private static Usuario usuario = null;

	private void createLoading(Stage primaryStage) {
		UPDATEDB = primaryStage;

		try {
			FXMLLoader loader = new FXMLLoader(LoadingController.getFxmlLocate());
			AnchorPane scPnTelaPrincipal = loader.load();
			Scene tela = new Scene(scPnTelaPrincipal); // Carrega a scena
			UPDATEDB.getIcons().add(LoadingController.getIconImage());
			UPDATEDB.setScene(tela); // Seta a cena principal
			UPDATEDB.setTitle("Carregando");
			tela.setFill(Color.TRANSPARENT);
			UPDATEDB.initStyle(StageStyle.TRANSPARENT);
			UPDATEDB.centerOnScreen();
			UPDATEDB.show();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.log(Level.SEVERE, "{Erro ao iniciar o programa}", e);
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		createLoading(primaryStage);
	}

	@Override
	public void handleProgressNotification(ProgressNotification pn) {
		// bar.setProgress(pn.getProgress());
		// if (pn.getProgress() > 0 && pn.getProgress() < 1.0) {
		// bar.setVisible(true);
		// }
	}

	@Override
	public void handleStateChangeNotification(StateChangeNotification notification) {
		if (notification.getType().equals(StateChangeNotification.Type.BEFORE_START)) {
			UPDATEDB.close();

			if (Run.ERRO_BD) {
				Alertas.Alerta(AlertType.ERROR, "Erro", "Não foi possível conectar com o banco de dados.");
				Platform.exit();
			}

			/*LOGUIN = new Stage();
			Login.start(LOGUIN, action -> usuario = (Usuario) action);

			consumer = (CredentialsConsumer) notification.getApplication();

			if (LOGUIN.isShowing() && usuario != null && consumer != null) {
				consumer.setCredential(usuario);
				Platform.runLater(new Runnable() {
					public void run() {
						LOGUIN.hide();
					}
				});
			}*/
		}
	}

}
