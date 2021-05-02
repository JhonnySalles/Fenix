package pdv.controller.pesquisas;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import cadastro.controller.pesquisas.PsqClienteController;
import cadastro.controller.pesquisas.PsqEmpresaController;
import cadastro.controller.pesquisas.PsqProdutoController;
import cadastro.controller.pesquisas.PsqUsuarioController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import pdv.Run;
import pdv.controller.DashboardController;

public class PesquisasController implements Initializable {

	@FXML
	JFXButton btnPsqCliente;

	@FXML
	JFXButton btnPsqEmpresa;

	@FXML
	JFXButton btnPsqUsuario;

	@FXML
	JFXButton btnPsqProduto;

	final DashboardController main = Run.getMainController();

	@FXML
	public void onBtnPsqClienteAction() {
		main.loadAbas(PsqClienteController.getFxmlLocate(), "Pesquisa de clientes", "");
	}

	@FXML
	public void onBtnPsqEmpresaAction() {
		main.loadAbas(PsqEmpresaController.getFxmlLocate(), "Pesquisa de empresas", "");
	}

	@FXML
	public void onBtnPsqUsuarioAction() {
		main.loadAbas(PsqUsuarioController.getFxmlLocate(), "Pesquisa de usuários", "");
	}

	@FXML
	public void onBtnPsqProdutoAction() {
		main.loadAbas(PsqProdutoController.getFxmlLocate(), "Pesquisa de produtos", "");
	}

	@Override
	public synchronized void initialize(URL location, ResourceBundle resources) {

	}

	public static URL getFxmlLocate() {
		return PesquisasController.class.getResource("/pdv/view/pesquisas/Pesquisas.fxml");
	}
}
