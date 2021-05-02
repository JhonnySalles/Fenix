package cadastro;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import cadastro.controller.cadastros.CadUsuarioController;
import comum.model.notification.Notificacoes;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CadUsuarioTest extends ApplicationTest {

	public Scene criaTela() {
		Notificacoes.TESTE = true;
		FXMLLoader loader = new FXMLLoader(CadUsuarioController.getFxmlLocate());
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
	public void testClienteInput() {
		clickOn("#txtNome");
		write("Thozoinit Vuimibin Linor");

		clickOn("#txtLogin");
		write("thozoinit");

		clickOn("#pswSenha");
		write("SenhaTeste@1225");

		clickOn("#txtObservacao");
		write("Observação de teste.");

		clickOn("#btnConfirmar");
	}

}
