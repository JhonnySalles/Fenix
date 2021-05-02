package pdv.controller.cadastros;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import cadastro.controller.lista.ListaClienteController;
import cadastro.controller.lista.ListaEmpresaController;
import cadastro.controller.lista.ListaGrupoSubGrupoController;
import cadastro.controller.lista.ListaProdutoController;
import cadastro.controller.lista.ListaUsuarioController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import pdv.Run;
import pdv.controller.DashboardController;

public class CadastrosController implements Initializable {

	@FXML
	JFXButton btnCadCliente;

	@FXML
	JFXButton btnCadEmpresa;

	@FXML
	JFXButton btnCadUsuario;

	@FXML
	JFXButton btnCadProduto;

	@FXML
	JFXButton btnCadGrupoSubGrupo;

	final DashboardController main = Run.getMainController();

	@FXML
	public void onBtnCadClienteAction() {
		main.loadAbas(ListaClienteController.getFxmlLocate(), "Cadastros de clientes", "", (controller) -> {
			// ((ListaClienteController) controller) // chamar a função de carregar a grid.o
		});
	}

	@FXML
	public void onBtnCadEmpresaAction() {
		main.loadAbas(ListaEmpresaController.getFxmlLocate(), "Cadastros de empresas", "");
	}

	@FXML
	public void onBtnCadUsuarioAction() {
		main.loadAbas(ListaUsuarioController.getFxmlLocate(), "Cadastros de usuários", "");
	}

	@FXML
	public void onBtnCadProdutoAction() {
		main.loadAbas(ListaProdutoController.getFxmlLocate(), "Cadastros de produtos", "");
	}

	@FXML
	public void onBtnCadGrupoSubGrupoAction() {
		main.loadAbas(ListaGrupoSubGrupoController.getFxmlLocate(), "Cadastros de grupo/sub grupo", "");
	}

	@Override
	public synchronized void initialize(URL location, ResourceBundle resources) {

	}

	public static URL getFxmlLocate() {
		return CadastrosController.class.getResource("/pdv/view/cadastros/Cadastros.fxml");
	}
}
