package administrador;

import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import administrador.controller.DashboardController;
import cadastro.controller.cadastros.CadClienteController;
import cadastro.controller.cadastros.CadEmpresaController;
import cadastro.controller.cadastros.CadProdutoController;
import cadastro.controller.cadastros.CadUsuarioController;
import comum.model.utils.ViewGerenciador;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class App extends Application {

	private final static Logger LOGGER = Logger.getLogger(App.class.getName());

	private static Scene mainScene;
	private static DashboardController mainController;

	@Override
	public synchronized void start(Stage primaryStage) {
		try {
			// Classe inicial
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/administrador/view/Dashboard.fxml"));
			AnchorPane scPnTelaPrincipal = loader.load();
			mainController = loader.getController();

			mainScene = new Scene(scPnTelaPrincipal); // Carrega a scena
			mainScene.setFill(Color.BLACK);

			primaryStage.setScene(mainScene); // Seta a cena principal
			primaryStage.setTitle("Ponto de venda");
			primaryStage.getIcons()
					.add(new Image(getClass().getResourceAsStream("/administrador/imagens/icon/icoPDV_400.png")));
			primaryStage.setMinHeight(600);
			primaryStage.setMinWidth(600);
			primaryStage.initStyle(StageStyle.DECORATED);
			primaryStage.centerOnScreen();
			primaryStage.setMaximized(true);

			primaryStage.show(); // Mostra a tela.

			Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
			primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
			primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);

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

		String[] css = { App.class.getResource("/administrador/css/Paleta_Cores.css").toExternalForm(),
				App.class.getResource("/administrador/css/White_Dashboard.css").toExternalForm(),
				App.class.getResource("/administrador/css/White_Dashboard_Botoes.css").toExternalForm(),
				App.class.getResource("/administrador/css/White_Dashboard_Graficos.css").toExternalForm() };
		ViewGerenciador.carregaCss(css);
	}

	public static DashboardController getMainController() {
		return mainController;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
