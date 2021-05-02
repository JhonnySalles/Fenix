package comum.form;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import comum.model.animation.TelaAnimation;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public abstract class ListaFormPadrao implements Initializable {

	static protected ListaFormPadrao INSTANCIA;

	@FXML
	protected StackPane spRoot;

	@FXML
	protected AnchorPane apContainer;

	@FXML
	protected ScrollPane spBackground;

	@FXML
	protected AnchorPane apContainerInterno;

	@FXML
	protected HBox hbTitulo;

	@FXML
	protected HBox hbTituloBotoes;

	@FXML
	protected JFXButton btnNovo;

	@FXML
	protected JFXButton btnExcluir;

	@FXML
	protected JFXButton btnEditar;

	@FXML
	protected JFXButton btnAtualizar;

	@FXML
	protected JFXTextField txtPesquisa;

	@FXML
	protected abstract void onBtnNovoClick();

	@FXML
	protected abstract void onBtnExcluirClick();

	@FXML
	protected abstract void onBtnEditarClick();

	@FXML
	protected abstract void onBtnAtualizarClick();

	protected ListaFormPadrao desabilitaBotoes() {
		spRoot.setDisable(true);
		btnNovo.setDisable(true);
		btnAtualizar.setDisable(true);
		btnExcluir.setDisable(true);
		btnEditar.setDisable(true);
		return this;
	}

	protected ListaFormPadrao habilitaBotoes() {
		spRoot.setDisable(false);
		btnNovo.setDisable(false);
		btnAtualizar.setDisable(false);
		btnExcluir.setDisable(false);
		btnEditar.setDisable(false);
		return this;
	}

	/**
	 * <p>
	 * Função para pegar a instância da lista que está iniciado.
	 * </p>
	 * 
	 * @author Jhonny de Salles Noschang
	 */
	public static ListaFormPadrao getInstancia() {
		assert INSTANCIA != null : "A instância da lista não foi injetada";
		return INSTANCIA;
	}

	/**
	 * Função a ser executada quando a tela for fechada. {@code ListaFormPadrao}.
	 *
	 * @defaultValue null
	 */
	protected ObjectProperty<EventHandler<ActionEvent>> onClose;
	protected static final EventHandler<ActionEvent> DEFAULT_ON_CLOSE = null;

	public final void setOnClose(EventHandler<ActionEvent> value) {
		if ((onClose != null) || (value != null /* DEFAULT_ON_DUPLO_CLIQUE */)) {
			onCloseProperty().set(value);
		}
	}

	public final EventHandler<ActionEvent> getOnClose() {
		return (onClose == null) ? DEFAULT_ON_CLOSE : onClose.get();
	}

	public final ObjectProperty<EventHandler<ActionEvent>> onCloseProperty() {
		if (onClose == null) {
			onClose = new SimpleObjectProperty<EventHandler<ActionEvent>>(this, "onClose", DEFAULT_ON_CLOSE);
		}
		return onClose;
	}

	protected final void onClose() {
		final EventHandler<ActionEvent> handler = getOnClose();
		if (handler != null)
			handler.handle(new ActionEvent(this, null));
	}

	private void configuraScroll() {
		new TelaAnimation().scrollTitulo(spBackground, apContainerInterno, hbTitulo, hbTituloBotoes);
	}

	protected abstract void inicializa(URL arg0, ResourceBundle arg1);

	@Override
	public synchronized void initialize(URL arg0, ResourceBundle arg1) {
		INSTANCIA = this;
		configuraScroll();

		inicializa(arg0, arg1);
	}
}
