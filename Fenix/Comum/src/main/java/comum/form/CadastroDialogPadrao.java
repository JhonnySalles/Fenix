package comum.form;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

public abstract class CadastroDialogPadrao<T> implements Initializable {

	static protected CadastroDialogPadrao<?> INSTANCIA;

	@FXML
	protected AnchorPane apContainer;

	@FXML
	protected JFXButton btnConfirmar;

	@FXML
	protected JFXButton btnCancelar;

	@FXML
	protected abstract void onBtnConfirmarClick();

	@FXML
	protected abstract void onBtnCancelarClick();

	protected abstract void salvar(T entidade);

	public abstract void carregar(T entidade);

	protected abstract boolean validaCampos();

	protected abstract void limpaCampos();

	public abstract void onClose();

	public abstract CadastroDialogPadrao<T> atualizaEntidade();

	public Boolean edicao;

	protected T entidade;

	public T getEntidade() {
		return entidade;
	}

	/**
	 * <p>
	 * Função para pegar a instância do cadastro que está iniciado.
	 * </p>
	 * 
	 * @author Jhonny de Salles Noschang
	 */
	public static CadastroDialogPadrao<?> getInstancia() {
		assert INSTANCIA != null : "A instância do cadastro não foi injetada";
		return INSTANCIA;
	}

	protected abstract void inicializa(URL arg0, ResourceBundle arg1);

	@Override
	public synchronized void initialize(URL arg0, ResourceBundle arg1) {
		INSTANCIA = this;
		inicializa(arg0, arg1);
	}

}
