package administrador.controller.pesquisas;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import administrador.App;
import administrador.controller.DashboardController;
import cadastro.controller.pesquisas.PsqClienteController;
import cadastro.controller.pesquisas.PsqEmpresaController;
import cadastro.controller.pesquisas.PsqProdutoController;
import cadastro.controller.pesquisas.PsqUsuarioController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class PesquisasController implements Initializable {
	
	@FXML
	JFXButton btnPsqCliente;
	
	@FXML
	JFXButton btnPsqEmpresa;
	
	@FXML
	JFXButton btnPsqUsuario;
	
	@FXML
	JFXButton btnPsqProduto;
	
	final DashboardController main = App.getMainController();

	
	@FXML
	public void onBtnPsqClienteAction() {
		main.loadView(PsqClienteController.getFxmlLocate(), "Pesquisa de clientes", "");
	}
	
	@FXML
	public void onBtnPsqEmpresaAction() {
		main.loadView(PsqEmpresaController.getFxmlLocate(), "Pesquisa de empresas", "");
	}
	
	@FXML
	public void onBtnPsqUsuarioAction() {
		main.loadView(PsqUsuarioController.getFxmlLocate(), "Pesquisa de usuários", "");
	}

	@FXML
	public void onBtnPsqProdutoAction() {
		main.loadView(PsqProdutoController.getFxmlLocate(), "Pesquisa de produtos", "");
	}
	
	
	@Override
	public synchronized void initialize(URL location, ResourceBundle resources) {

	}
	
	public static URL getFxmlLocate() {
		return PesquisasController.class.getResource("/administrador/view/pesquisas/Pesquisas.fxml");
	}
}
