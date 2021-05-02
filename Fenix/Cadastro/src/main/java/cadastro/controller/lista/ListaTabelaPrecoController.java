package cadastro.controller.lista;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import comum.form.ListaFormPadrao;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;

public class ListaTabelaPrecoController extends ListaFormPadrao {

	@FXML
	private JFXButton btnConfirmar;
	
	@FXML
	private JFXTextField txtPsqTabelaPreco;
	
	@FXML
	private JFXDatePicker dtPkValidade;

	@FXML
	private JFXTextField txtPsqProduto;

	@FXML
	private JFXComboBox cbUnidade;

	@FXML
	private JFXButton btnAdicionar;
	
	@FXML
	private JFXButton btnRemover;


	final static PseudoClass REMOVER = PseudoClass.getPseudoClass("Remover");
	
	@Override
	protected void onBtnNovoClick() {
		
	}

	@Override
	protected void onBtnExcluirClick() {
		// Não terá botão de excluir.
	}

	@Override
	protected void onBtnEditarClick() {
		// Não terá botão de editar.
	}

	@Override
	protected void onBtnAtualizarClick() {

	}

	@FXML
	public void onBtnConfirmarClick() {

	}


	@Override
	protected void inicializa(URL arg0, ResourceBundle arg1) {

	}

	public static URL getFxmlLocate() {
		return ListaTabelaPrecoController.class.getResource("/cadastro/view/lista/ListaTabelaPreco.fxml");
	}

}
