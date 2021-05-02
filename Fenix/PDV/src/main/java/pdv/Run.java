package pdv;

import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import cadastro.controller.cadastros.CadClienteController;
import cadastro.controller.cadastros.CadEmpresaController;
import cadastro.controller.cadastros.CadProdutoController;
import cadastro.controller.cadastros.CadUsuarioController;
import comum.model.exceptions.ExcessaoBd;
import comum.model.utils.ViewGerenciador;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.application.Preloader.ProgressNotification;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pdv.Loading.CredentialsConsumer;
import pdv.controller.DashboardController;
import servidor.configuration.ManagerFactory;
import servidor.entities.Usuario;

public class Run extends Application implements CredentialsConsumer {

	private final static Logger LOGGER = Logger.getLogger(Run.class.getName());

	private static Scene mainScene;
	private static Stage stage;
	private static DashboardController mainController;
	private static Usuario usuario = null;
	public static Boolean ERRO_BD = false;

	@Override
	public void init() throws InterruptedException {
		notifyPreloader(new ProgressNotification(0.10));
		try {
			ManagerFactory.iniciaBD();
		} catch (ExcessaoBd e) {
			e.printStackTrace();
			ERRO_BD = true;
		}
		notifyPreloader(new ProgressNotification(0.50));
		Thread.sleep(1000);
		notifyPreloader(new ProgressNotification(1));
	}

	private void inicia() {
		if (usuario != null && stage != null) {
			Platform.runLater(new Runnable() {
				public void run() {
					stage.show(); // Mostra a tela.
					preCarregamentoTelas();
				}
			});
		}
	}

	@Override
	public synchronized void start(Stage primaryStage) {
		stage = primaryStage;
		try {
			// Classe inicial
			FXMLLoader loader = new FXMLLoader(DashboardController.getFxmlLocate());
			AnchorPane scPnTelaPrincipal = loader.load();
			mainController = loader.getController();

			mainScene = new Scene(scPnTelaPrincipal); // Carrega a scena
			mainScene.setFill(Color.BLACK);

			primaryStage.setScene(mainScene); // Seta a cena principal
			primaryStage.setTitle("Ponto de venda");
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/pdv/imagens/icon/icoPDV_400.png")));
			primaryStage.setMinHeight(600);
			primaryStage.setMinWidth(600);
			primaryStage.initStyle(StageStyle.DECORATED);
			primaryStage.centerOnScreen();
			primaryStage.setMaximized(true);

			Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
			primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
			primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
			
			//*********
			stage.show(); // Mostra a tela.
			preCarregamentoTelas();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.log(Level.SEVERE, "{Erro ao iniciar o programa}", e);
		}
	}

	private static void preCarregamentoTelas() {
		URL[] listaTelas = { CadClienteController.getFxmlLocate(), CadEmpresaController.getFxmlLocate(),
				CadProdutoController.getFxmlLocate(), CadUsuarioController.getFxmlLocate() };
		ViewGerenciador.setCaminhoTelasPreCarregamento(listaTelas);

		String[] css = { Run.class.getResource("/pdv/css/Paleta_Cores.css").toExternalForm(),
				Run.class.getResource("/pdv/css/White_Dashboard.css").toExternalForm(),
				Run.class.getResource("/pdv/css/White_Dashboard_Botoes.css").toExternalForm(),
				Run.class.getResource("/pdv/css/White_Dashboard_Graficos.css").toExternalForm(),
				Run.class.getResource("/pdv/css/White_Geral.css").toExternalForm(),
				Run.class.getResource("/pdv/css/White_SpinnerHorizontal.css").toExternalForm() };
		ViewGerenciador.carregaCss(css);
	}

	public static DashboardController getMainController() {
		return mainController;
	}

	public static void main(String[] args) {
		// Faz a chamada do preloader para carregamento do banco.
		System.setProperty("javafx.preloader", Loading.class.getCanonicalName());
		launch(args);
	}

	@Override
	public void setCredential(Usuario user) {
		usuario = user;
		inicia();
	}
}
