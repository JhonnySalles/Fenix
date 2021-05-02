package comum.form;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.persistence.EntityExistsException;

import com.jfoenix.controls.JFXButton;

import comum.model.animation.TelaAnimation;
import comum.model.entities.Entidade;
import comum.model.enums.NotificacaoCadastro;
import comum.model.messages.Mensagens;
import comum.model.notification.Notificacoes;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

/**
 * <p>
 * Classe abstrata para gerênciar as funções padrões do cadastro.
 * </p>
 * 
 * <p>
 * Esta classe será responsável por conter a base do layout, com os atributos de
 * <b>StackPane</b> e <b>AnchorPane</b>, como também a execução das funções de
 * <i>Confirmação</i> e <i>Exclusão</i> .
 * </p>
 * <p>
 * Possuirá uma função <i>onClose</i> que será implementado caso necessário para
 * ser executado quando efetuar a chamada do fechamento da tela.
 * </p>
 * <p>
 * Também possuirá o objeto <b>entidade</b> com o tipo específico da classe que
 * servirá de objeto a ser editado no momento.
 * </p>
 * 
 * 
 * @author Jhonny de Salles Noschang
 */
public abstract class CadastroFormPadrao<T extends Entidade> implements Initializable {

	private Map<KeyCodeCombination, Runnable> atalhosTecla = new HashMap<>();

	static protected CadastroFormPadrao<?> INSTANCIA;

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
	protected JFXButton btnExcluir;

	@FXML
	protected JFXButton btnVoltar;

	protected T entidade;

	public T getEntidade() {
		return entidade;
	}

	/**
	 * <p>
	 * A função deverá retornar as menssagens padrões para os popups apresentados
	 * pelo tipo da menssagem.
	 * </p>
	 * 
	 * @author Jhonny de Salles Noschang
	 */
	protected abstract String messagens(NotificacaoCadastro notificacao);

	@FXML
	protected void onBtnConfirmarClick() {
		atualizaEntidade();
		if (validaCampos()) {
			if (entidade == null) {
				Notificacoes.notificacao(AlertType.INFORMATION, Mensagens.AVISO,
						messagens(NotificacaoCadastro.EntidadeVazia));
				return;
			}
			try {
				spRoot.cursorProperty().set(Cursor.WAIT);
				desabilitaBotoes().salvar(entidade);
			} catch (EntityExistsException e) {
				Notificacoes.notificacao(AlertType.ERROR, Mensagens.ERRO,
						messagens(NotificacaoCadastro.ErroDuplicidade));
				return;
			} finally {
				spRoot.cursorProperty().set(null);
				habilitaBotoes();
			}
			Notificacoes.notificacao(AlertType.NONE, Mensagens.CONCLUIDO,
					messagens(NotificacaoCadastro.SalvoComSucesso));
		}
	};

	@FXML
	protected void onBtnCancelarClick() {
		limpaCampos();
	};

	@FXML
	protected void onBtnExcluirClick() {
		if (entidade == null || entidade.getId().compareTo(0L) == 0) {
			Notificacoes.notificacao(AlertType.INFORMATION, Mensagens.AVISO,
					Mensagens.CADASTRO_EXCLUIR + " " + messagens(NotificacaoCadastro.EntidadeVazia));
			return;
		}
		try {
			spRoot.cursorProperty().set(Cursor.WAIT);
			desabilitaBotoes().atualizaEntidade().excluir(entidade);
		} finally {
			spRoot.cursorProperty().set(null);
			habilitaBotoes();
		}
		Notificacoes.notificacao(AlertType.NONE, Mensagens.CONCLUIDO,
				messagens(NotificacaoCadastro.ExcluidoComSucesso));
	};

	@FXML
	protected abstract void onBtnVoltarClick();

	protected abstract void salvar(T entidade);

	protected abstract void excluir(T entidade);

	protected abstract T pesquisar(T entidade);

	public abstract void carregar(T entidade);

	protected abstract boolean validaCampos();

	protected abstract void limpaCampos();

	public abstract CadastroFormPadrao<T> atualizaEntidade();

	public Boolean edicao;

	protected CadastroFormPadrao<T> desabilitaBotoes() {
		spRoot.setDisable(true);
		btnConfirmar.setDisable(true);
		btnCancelar.setDisable(true);
		btnExcluir.setDisable(true);
		return this;
	}

	protected CadastroFormPadrao<T> habilitaBotoes() {
		spRoot.setDisable(false);
		btnConfirmar.setDisable(false);
		btnCancelar.setDisable(false);
		btnExcluir.setDisable(false);
		return this;
	}

	/**
	 * <p>
	 * Função para pegar a instância do cadastro que está iniciado.
	 * </p>
	 * 
	 * @author Jhonny de Salles Noschang
	 */
	public static CadastroFormPadrao<?> getInstancia() {
		assert INSTANCIA != null : "A instância do cadastro não foi injetada";
		return INSTANCIA;
	}

	/**
	 * Função a ser executada quando a tela for fechada.
	 *
	 * @defaultValue null
	 */
	protected ObjectProperty<EventHandler<ActionEvent>> onClose;
	protected static final EventHandler<ActionEvent> DEFAULT_ON_CLOSE = null;

	public final void setOnClose(EventHandler<ActionEvent> value) {
		if ((onClose != null) || (value != null /* DEFAULT_ON_CLOSE */))
			onCloseProperty().set(value);
	}

	public final EventHandler<ActionEvent> getOnClose() {
		return (onClose == null) ? DEFAULT_ON_CLOSE : onClose.get();
	}

	public final ObjectProperty<EventHandler<ActionEvent>> onCloseProperty() {
		if (onClose == null)
			onClose = new SimpleObjectProperty<EventHandler<ActionEvent>>(this, "onClose", DEFAULT_ON_CLOSE);
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
		atalhosTecla.put(new KeyCodeCombination(KeyCode.F4), new Runnable() {
			@FXML
			public void run() {
				btnExcluir.fire();
			}
		});
	}

	protected abstract void inicializa(URL arg0, ResourceBundle arg1);

	@Override
	public synchronized void initialize(URL arg0, ResourceBundle arg1) {
		INSTANCIA = this;
		configuraScroll();
		configuraAtalhosTeclado();
		inicializa(arg0, arg1);
	}

}
