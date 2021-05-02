package restaurante.controller.cadastros;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import cadastro.controller.cadastros.CadClienteController;
import cadastro.controller.cadastros.CadEmpresaController;
import cadastro.controller.cadastros.CadProdutoController;
import cadastro.controller.cadastros.CadUsuarioController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import restaurante.App;
import restaurante.controller.DashboardController;

public class CadastrosController implements Initializable {
	
	@FXML
	JFXButton btnCadCliente;
	
	@FXML
	JFXButton btnCadEmpresa;
	
	@FXML
	JFXButton btnCadUsuario;
	
	@FXML
	JFXButton btnCadProduto;
	
	final DashboardController main = App.getMainController();

	
	@FXML
	public void onBtnCadClienteAction() {
		main.loadAbas(CadClienteController.getFxmlLocate(), "Cadastros de clientes", "");
	}
	
	@FXML
	public void onBtnCadEmpresaAction() {
		main.loadAbas(CadEmpresaController.getFxmlLocate(), "Cadastros de empresas", "");
	}
	
	@FXML
	public void onBtnCadUsuarioAction() {
		main.loadAbas(CadUsuarioController.getFxmlLocate(), "Cadastros de usuários", "");
	}

	@FXML
	public void onBtnCadProdutoAction() {
		main.loadAbas(CadProdutoController.getFxmlLocate(), "Cadastros de produtos", "");
	}	
	
	@Override
	public synchronized void initialize(URL location, ResourceBundle resources) {


	}
}
