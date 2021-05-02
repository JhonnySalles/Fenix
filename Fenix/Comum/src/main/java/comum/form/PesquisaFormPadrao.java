package comum.form;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import comum.model.animation.TelaAnimation;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public abstract class PesquisaFormPadrao implements Initializable {

	private Map<KeyCodeCombination, Runnable> atalhosTecla = new HashMap<>();

	static protected PesquisaFormPadrao INSTANCIA;

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
	protected JFXButton btnConfirmar;

	@FXML
	protected JFXButton btnCancelar;

	@FXML
	protected JFXButton btnAtualizar;

	@FXML
	protected JFXButton btnVoltar;

	@FXML
	protected abstract void onBtnConfirmarClick();

	@FXML
	protected abstract void onBtnCancelarClick();

	@FXML
	protected abstract void onBtnAtualizarClick();

	@FXML
	protected abstract void onBtnVoltarClick();

	/**
	 * <p>
	 * Função para pegar a instância da lista que está iniciado.
	 * </p>
	 * 
	 * @author Jhonny de Salles Noschang
	 */
	public static PesquisaFormPadrao getInstancia() {
		assert INSTANCIA != null : "A instância da pesquisa não foi injetada";
		return INSTANCIA;
	}

	
	/**
	 * Função a ser executada quando a tela for fechada. {@code PesquisaFormPadrao}.
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

	// Será necessário verificar uma forma de configurar o scene após a exibição,
	// pois é ele que adiciona os atalhos do teclado, porém na construção a scene
	// não existe, somente na exibição.
	public void ativaAtalhos() {
		apContainer.getScene().getAccelerators().clear();
		apContainer.getScene().getAccelerators().putAll(atalhosTecla);
	}

	private void configuraAtalhosTeclado() {
		atalhosTecla.put(new KeyCodeCombination(KeyCode.F2), new Runnable() {
			@FXML
			public void run() {
				btnConfirmar.fire();
			}
		});
		atalhosTecla.put(new KeyCodeCombination(KeyCode.F3), new Runnable() {
			@FXML
			public void run() {
				btnCancelar.fire();
			}
		});
		atalhosTecla.put(new KeyCodeCombination(KeyCode.BACK_SPACE), new Runnable() {
			@FXML
			public void run() {
				btnVoltar.fire();
			}
		});
		atalhosTecla.put(new KeyCodeCombination(KeyCode.BACK_SPACE), new Runnable() {
			@FXML
			public void run() {
				btnVoltar.fire();
			}
		});
		atalhosTecla.put(new KeyCodeCombination(KeyCode.F5), new Runnable() {
			@FXML
			public void run() {
				btnAtualizar.fire();
			}
		});
	}

	protected abstract void inicializa(URL arg0, ResourceBundle arg1);

	@Override
	public synchronized void initialize(URL arg0, ResourceBundle arg1) {
		INSTANCIA = this;

		configuraScroll();
		configuraAtalhosTeclado();

		// Por padrão os botões não estão visiveis.
		btnConfirmar.setDisable(true);
		btnConfirmar.setVisible(false);
		btnCancelar.setDisable(true);
		btnCancelar.setVisible(false);
		btnVoltar.setDisable(true);
		btnVoltar.setVisible(false);

		inicializa(arg0, arg1);
	}

}
