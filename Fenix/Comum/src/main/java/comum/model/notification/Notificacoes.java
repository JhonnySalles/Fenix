package comum.model.notification;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.controlsfx.control.Notifications;

import animatefx.animation.FadeIn;
import animatefx.animation.FadeOut;
import comum.form.DashboardFormPadrao;
import comum.model.alerts.AlertasPopup;
import comum.model.notification.controller.CentralNotificacaoController;
import comum.model.notification.controller.NotificacaoController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class Notificacoes {

	private final static Logger LOGGER = Logger.getLogger(Notificacoes.class.getName());

	public static Boolean TESTE = false;

	private final static ObservableList<Menssagem> LISTA_MENSSAGENS = FXCollections.observableArrayList();

	public final static ImageView IMG_ALERTA = new ImageView(
			new Image(AlertasPopup.class.getResourceAsStream("/comum/imagens/alerta/icoAlerta_48.png")));
	public final static ImageView IMG_AVISO = new ImageView(
			new Image(AlertasPopup.class.getResourceAsStream("/comum/imagens/alerta/icoAviso_48.png")));
	public final static ImageView IMG_ERRO = new ImageView(
			new Image(AlertasPopup.class.getResourceAsStream("/comum/imagens/alerta/icoErro_48.png")));
	public final static ImageView IMG_CONFIRMA = new ImageView(
			new Image(AlertasPopup.class.getResourceAsStream("/comum/imagens/alerta/icoConfirma_48.png")));
	public final static ImageView IMG_SUCESSO = new ImageView(
			new Image(AlertasPopup.class.getResourceAsStream("/comum/imagens/alerta/btnConfirma_48.png")));

	private static DashboardFormPadrao DASHBOARD_MAIN;
	private static AnchorPane CONTAINER_CENTRAL_NOTIFICACAO;
	private static CentralNotificacaoController CENTRAL_NOTIFICACAO_CONTROLLER;
	private static NotificacaoController NOTIFICACAO_CONTROLLER;
	private static AnchorPane NOTIFICACAO;
	private static AnchorPane ROOT_ANCHOR_PANE;
	private static Timeline TM_LINE_CLOSE;
	private static TranslateTransition TT_AP_NOTIFICACOES;
	private static Boolean NOTIFICACAO_OPENED = false;

	// ********************************************************************************//
	// Classe para guardar a referencia das telas aberta e controlador
	// *******************************************************************************//
	public static class Menssagem {
		private String titulo;
		private String menssagem;
		private LocalDateTime hora;
		private AlertType tipo;

		public String getTitulo() {
			return titulo;
		}

		public String getMenssagem() {
			return menssagem;
		}

		public LocalDateTime getHora() {
			return hora;
		}

		public AlertType getTipo() {
			return tipo;
		}

		public Menssagem(String titulo, String menssagem, AlertType tipo) {
			this.titulo = titulo;
			this.menssagem = menssagem;
			this.tipo = tipo;
			this.hora = LocalDateTime.now();
		}
	}

	public static void limpaNotificacoes() {
		LISTA_MENSSAGENS.clear();
		DASHBOARD_MAIN.atualizaIcoCentralNotificacao(!LISTA_MENSSAGENS.isEmpty());
	}

	public static void addListaMenssagem(Menssagem menssagem) {
		LISTA_MENSSAGENS.add(menssagem);
		DASHBOARD_MAIN.atualizaIcoCentralNotificacao(!LISTA_MENSSAGENS.isEmpty());
	}

	public static void remListaMenssagem(Menssagem menssagem) {
		LISTA_MENSSAGENS.remove(menssagem);
		DASHBOARD_MAIN.atualizaIcoCentralNotificacao(!LISTA_MENSSAGENS.isEmpty());
	}

	public static ObservableList<Menssagem> getListaMenssagens() {
		return LISTA_MENSSAGENS;
	}

	public static void atualizaCentralMenssagem() {
		if ((CONTAINER_CENTRAL_NOTIFICACAO == null) || (CENTRAL_NOTIFICACAO_CONTROLLER == null))
			createCentralMenssagem();
	}

	public static void createCentralMenssagem() {
		if (CONTAINER_CENTRAL_NOTIFICACAO == null) {
			DASHBOARD_MAIN = DashboardFormPadrao.getInstancia();
			CONTAINER_CENTRAL_NOTIFICACAO = DASHBOARD_MAIN.getContainerCentralNotificacoes();
		}

		try {
			FXMLLoader loaderConteudo = new FXMLLoader(CentralNotificacaoController.getFxmlLocate());
			AnchorPane spConteudo = loaderConteudo.load();
			CENTRAL_NOTIFICACAO_CONTROLLER = loaderConteudo.getController();
			CONTAINER_CENTRAL_NOTIFICACAO.getChildren().add(spConteudo);
			AnchorPane.setTopAnchor(spConteudo, 0.0);
			AnchorPane.setBottomAnchor(spConteudo, 0.0);
			AnchorPane.setLeftAnchor(spConteudo, 0.0);
			AnchorPane.setRightAnchor(spConteudo, 0.0);
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.log(Level.SEVERE, "{Erro ao carregar a central de menssagem do dashboard}", e);
		}
	}

	private static void setTipo(AlertType tipo, NotificacaoController controller, AnchorPane root) {
		root.getStyleClass().clear();
		switch (tipo) {
		case WARNING:
			root.getStyleClass().add("Notificacao_AlertaBackground");
			controller.setImagem(IMG_ALERTA);
			break;

		case INFORMATION:
			root.getStyleClass().add("Notificacao_AvisoBackground");
			controller.setImagem(IMG_AVISO);
			break;

		case ERROR:
			root.getStyleClass().add("Notificacao_ErroBackground");
			controller.setImagem(IMG_ERRO);
			break;

		case NONE:
			root.getStyleClass().add("Notificacao_SucessoBackground");
			controller.setImagem(IMG_SUCESSO);
			break;

		default:
			root.getStyleClass().add("Notificacao_AvisoBackground");
			controller.setImagem(IMG_AVISO);
		}
	}

	private static void abreNotificacao() {
		TT_AP_NOTIFICACOES.stop();
		TT_AP_NOTIFICACOES.setToY(0);
		TT_AP_NOTIFICACOES.play();
	}

	public static void closeNotificacao() {
		if (TT_AP_NOTIFICACOES == null)
			return;

		TT_AP_NOTIFICACOES.stop();
		TT_AP_NOTIFICACOES.setToY(NOTIFICACAO_CONTROLLER.wheight + 5);
		TT_AP_NOTIFICACOES.play();
	}

	private static void clear() {
		NOTIFICACAO_OPENED = NOTIFICACAO.getTranslateY() == 0;
		if (!(NOTIFICACAO.getTranslateY() == 0)) {
			TM_LINE_CLOSE.stop();
			NOTIFICACAO.toBack();
		}
	}

	private static void create() {
		try {
			if (ROOT_ANCHOR_PANE == null) {
				DASHBOARD_MAIN = DashboardFormPadrao.getInstancia();
				ROOT_ANCHOR_PANE = DASHBOARD_MAIN.getRootStackPane();
			}

			FXMLLoader loader = new FXMLLoader(NotificacaoController.getFxmlLocate());
			NOTIFICACAO = loader.load();
			NOTIFICACAO_CONTROLLER = loader.getController();
			ROOT_ANCHOR_PANE.getChildren().add(NOTIFICACAO);
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.log(Level.SEVERE, "{Erro ao criar campo de aviso}", e);
		}

		TT_AP_NOTIFICACOES = new TranslateTransition(new Duration(500), NOTIFICACAO);
		TT_AP_NOTIFICACOES.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				clear();
			}
		});
		TM_LINE_CLOSE = new Timeline(new KeyFrame(Duration.millis(10000), close -> closeNotificacao()));
		NOTIFICACAO_OPENED = false;
	}

	/**
	 * <p>
	 * Função para apresentar um popup com algumas informações como aviso, alertas
	 * etc.
	 * </p>
	 * 
	 * <p>
	 * A referência para o controle é obrigatóriamente recebida apenas uma vez na
	 * inicialização.
	 * </p>
	 * 
	 * @param tipo   Tipo da notificação solicitada, podendo ser ALERTA (Alert),
	 *               AVISO (Warning), ERRO (Error), SUCESSO (None).
	 * @param titulo Titulo da notificação.
	 * @param texto  Texto da notificação.
	 * 
	 * @author Jhonny de Salles Noschang
	 */
	public static void notificacao(AlertType tipo, String titulo, String texto) {

		// Validação para teste
		if (TESTE)
			return;

		addListaMenssagem(new Menssagem(titulo, texto, tipo));

		if (DashboardFormPadrao.CENTRAL_OPENED)
			return;

		if ((NOTIFICACAO == null) || (NOTIFICACAO_CONTROLLER == null))
			create();

		if (tipo == AlertType.INFORMATION)
			LOGGER.log(Level.INFO, "{Mensagem de aviso ou alerta: " + texto + "}");

		TM_LINE_CLOSE.stop();
		if (!NOTIFICACAO_OPENED) {
			setTipo(tipo, NOTIFICACAO_CONTROLLER, NOTIFICACAO);

			NOTIFICACAO.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
				public void handle(MouseEvent e) {
					closeNotificacao();
				}
			});

			NOTIFICACAO.toFront();
			NOTIFICACAO_CONTROLLER.setTitulo(titulo).setTexto(texto);
			AnchorPane.setBottomAnchor(NOTIFICACAO, 5.0);
			AnchorPane.setRightAnchor(NOTIFICACAO, 5.0);
			NOTIFICACAO.setTranslateY(NOTIFICACAO_CONTROLLER.wheight + 5);

			abreNotificacao();
		} else {
			FadeOut fade = new FadeOut(NOTIFICACAO);
			fade.setOnFinished(fadeOut -> {
				setTipo(tipo, NOTIFICACAO_CONTROLLER, NOTIFICACAO);
				NOTIFICACAO_CONTROLLER.setTitulo(titulo).setTexto(texto);
				new FadeIn(NOTIFICACAO).play();
			});
			fade.setSpeed(100);
			fade.play();
		}

		TM_LINE_CLOSE.play();
	}

	/**
	 * <p>
	 * Notificação indepentende do sistema, onde é apresentado através da biblioteca
	 * <b>Notificatiions</b>.
	 * </p>
	 * 
	 * @param titulo  Titulo que será apresentado na notificação.
	 * @param texto   Texto a ser apresentado.
	 * @param duracao Duração da sua exibição.
	 * @param posicao Sua posição na tela.
	 * 
	 * @author Jhonny de Salles Noschang
	 */
	public static void windowsDark(String titulo, String texto, Double duracao, Pos posicao) {
		addListaMenssagem(new Menssagem(titulo, texto, AlertType.INFORMATION));
		Notifications notificacao = Notifications.create().title(titulo).text(texto)
				.hideAfter(Duration.seconds(duracao)).position(posicao).darkStyle();
		notificacao.show();
	}

	/**
	 * <p>
	 * Notificação indepentende do sistema, onde é apresentado através da biblioteca
	 * <b>Notificatiions</b>.
	 * </p>
	 * 
	 * @param titulo  Titulo que será apresentado na notificação.
	 * @param texto   Texto a ser apresentado.
	 * @param duracao Duração da sua exibição.
	 * @param posicao Sua posição na tela.
	 * 
	 * @author Jhonny de Salles Noschang
	 */
	public static void windowsWhite(String titulo, String texto, Double duracao, Pos posicao) {
		addListaMenssagem(new Menssagem(titulo, texto, AlertType.INFORMATION));
		Notifications notificacao = Notifications.create().title(titulo).text(texto)
				.hideAfter(Duration.seconds(duracao)).position(posicao);
		notificacao.show();
	}

}
