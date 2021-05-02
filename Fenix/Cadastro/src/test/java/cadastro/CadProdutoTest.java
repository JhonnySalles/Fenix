package cadastro;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import cadastro.controller.cadastros.CadProdutoController;
import comum.model.notification.Notificacoes;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CadProdutoTest extends ApplicationTest {

	public Scene criaTela() {
		Notificacoes.TESTE = true;
		FXMLLoader loader = new FXMLLoader(CadProdutoController.getFxmlLocate());
		Parent scPnTelaPrincipal;
		try {
			scPnTelaPrincipal = loader.load();
			return new Scene(scPnTelaPrincipal); // Carrega a scena
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setScene(criaTela());
		stage.show();
		stage.toFront();
	}

	@Test
	public void testProdutoInput() {
		clickOn("#txtDescricao");
		write("Maçã");

		clickOn("#txtCodigoBarras");
		write("98514303074");

		clickOn("#txtUnidade");
		write("UN");

		clickOn("#txtMarca");
		write("Maçã nanica.");
		
		clickOn("#spnPeso");
		write(",5");
		
		clickOn("#spnVolume");
		write("2");
		
		clickOn("#txtAreaObservacao");
		write("Informação opcional do produto.");

		clickOn("#btnConfirmar");
	}

}
