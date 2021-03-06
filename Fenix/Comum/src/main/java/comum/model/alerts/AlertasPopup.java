package comum.model.alerts;

import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.events.JFXDialogEvent;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;

/**
 * <p>
 * Classe responssável por apresentar um popup de alerta diretamente do
 * <b>DashBord</b> ou então em um <b>StackPane</b> escolhido.
 * </p>
 * 
 * @author Jhonny de Salles Noschang
 */
public class AlertasPopup {

	private final static Logger LOGGER = Logger.getLogger(AlertasPopup.class.getName());
	
	public final static ImageView ALERTA = new ImageView(
			new Image(AlertasPopup.class.getResourceAsStream("/comum/imagens/alerta/icoAlerta_48.png")));
	public final static ImageView AVISO = new ImageView(
			new Image(AlertasPopup.class.getResourceAsStream("/comum/imagens/alerta/icoAviso_48.png")));
	public final static ImageView ERRO = new ImageView(
			new Image(AlertasPopup.class.getResourceAsStream("/comum/imagens/alerta/icoErro_48.png")));
	public final static ImageView CONFIRMA = new ImageView(
			new Image(AlertasPopup.class.getResourceAsStream("/comum/imagens/alerta/icoConfirma_48.png")));

	private static StackPane ROOT_STACK_PANE;
	private static Node NODE_BLUR;

	public static StackPane getRootStackPane() {
		return ROOT_STACK_PANE;
	}

	public static void setRootStackPane(StackPane rootStackPane) {
		AlertasPopup.ROOT_STACK_PANE = rootStackPane;
	}

	public static Node getNodeBlur() {
		return NODE_BLUR;
	}

	public static void setNodeBlur(Node nodeBlur) {
		AlertasPopup.NODE_BLUR = nodeBlur;
	}

	/**
	 * <p>
	 * Função para apresentar mensagem de aviso, onde irá mostrar uma caixa no topo
	 * e esmaecer o fundo.
	 * </p>
	 * 
	 * @param Primeiro  parâmetro deve-se passar a referência para o stack pane.
	 * @param Conforme  a cascata, obter o primeiro conteudo interno para que seja
	 *                  esmaecido.
	 * @param Parametro opcional, pode-se passar varios botões em uma lista, caso
	 *                  não informe por padrão irá adicionar um botão ok.
	 * @param Campo     <b>String</b> que irá conter a mensagem a ser exibida.
	 * 
	 */
	public static void AvisoModal(StackPane rootStackPane, Node nodeBlur, List<JFXButton> botoes, String titulo,
			String texto) {
		LOGGER.log(Level.INFO, "{Mensagem de aviso ou alerta: " + texto + "}");
		dialogModern(rootStackPane, nodeBlur, botoes, titulo, texto, AVISO);
	}

	/**
	 * <p>
	 * Função padrão de aviso que apenas recebe os textos, irá obter os pane global
	 * do dashboard.
	 * </p>
	 * 
	 */
	public static void AvisoModal(String titulo, String texto) {
		LOGGER.log(Level.INFO, "{Mensagem de aviso ou alerta: " + texto + "}");
		dialogModern(ROOT_STACK_PANE, NODE_BLUR, null, titulo, texto, AVISO);
	}

	/**
	 * <p>
	 * Função para apresentar mensagem de alerta, onde irá mostrar uma caixa no topo
	 * e esmaecer o fundo.
	 * </p>
	 * 
	 * @param Primeiro  parâmetro deve-se passar a referência para o stack pane.
	 * @param Conforme  a cascata, obter o primeiro conteudo interno para que seja
	 *                  esmaecido.
	 * @param Parametro opcional, pode-se passar varios botões em uma lista, caso
	 *                  não informe por padrão irá adicionar um botão ok.
	 * @param Campo     <b>String</b> que irá conter a mensagem a ser exibida.
	 * 
	 */
	public static void AlertaModal(StackPane rootStackPane, Node nodeBlur, List<JFXButton> botoes, String titulo,
			String texto) {
		dialogModern(rootStackPane, nodeBlur, botoes, titulo, texto, ALERTA);
	}

	/**
	 * <p>
	 * Função padrão de alerta que apenas recebe os textos, irá obter os pane global
	 * do dashboard.
	 * </p>
	 * 
	 */
	public static void AlertaModal(String titulo, String texto) {
		dialogModern(ROOT_STACK_PANE, NODE_BLUR, null, titulo, texto, ALERTA);
	}

	/**
	 * <p>
	 * Função para apresentar mensagem de erro, onde irá mostrar uma caixa no topo e
	 * esmaecer o fundo.
	 * </p>
	 * 
	 * @param Primeiro  parâmetro deve-se passar a referência para o stack pane.
	 * @param Conforme  a cascata, obter o primeiro conteudo interno para que seja
	 *                  esmaecido.
	 * @param Parametro opcional, pode-se passar varios botões em uma lista, caso
	 *                  não informe por padrão irá adicionar um botão ok.
	 * @param Campo     <b>String</b> que irá conter a mensagem a ser exibida.
	 * 
	 */
	public static void ErroModal(StackPane rootStackPane, Node nodeBlur, List<JFXButton> botoes, String titulo,
			String texto) {
		dialogModern(rootStackPane, nodeBlur, botoes, titulo, texto, ERRO);
	}

	/**
	 * <p>
	 * Função padrão de alerta que apenas recebe os textos, irá obter os pane global
	 * do dashboard.
	 * </p>
	 * 
	 */
	public static void ErroModal(String titulo, String texto) {
		dialogModern(ROOT_STACK_PANE, NODE_BLUR, null, titulo, texto, ERRO);
	}

	/**
	 * <p>
	 * Função para apresentar mensagem com confirmação, onde irá mostrar uma caixa
	 * no topo e esmaecer o fundo.
	 * </p>
	 * 
	 * @param Primeiro  parâmetro deve-se passar a referência para o stack pane.
	 * @param Conforme  a cascata, obter o primeiro conteudo interno para que seja
	 *                  esmaecido.
	 * @param Parametro opcional, pode-se passar varios botões em uma lista, caso
	 *                  não informe por padrão irá adicionar um botão ok.
	 * @param Campo     <b>String</b> que irá conter a mensagem a ser exibida.
	 * @return Resulta o valor referente ao botão cancelar ou confirmar.
	 * 
	 */
	public static boolean ConfirmacaoModal(StackPane rootStackPane, Node nodeBlur, String titulo, String texto) {
		return alertModern(rootStackPane, nodeBlur, titulo, texto, CONFIRMA);
	}

	/**
	 * <p>
	 * Função padrão para apresentar mensagem de confirmação que apenas recebe os
	 * textos, irá obter os pane global do dashboard.
	 * </p>
	 * 
	 */
	public static boolean ConfirmacaoModal(String titulo, String texto) {
		return alertModern(ROOT_STACK_PANE, NODE_BLUR, titulo, texto, CONFIRMA);
	}

	static Boolean RESULTADO = false;

	private static boolean alertModern(StackPane rootStackPane, Node nodeBlur, String titulo, String texto,
			ImageView imagem) {
		RESULTADO = false;
		BoxBlur blur = new BoxBlur(3, 3, 3);

		JFXAlert<String> alert = new JFXAlert<>(rootStackPane.getScene().getWindow());
		alert.initModality(Modality.APPLICATION_MODAL);
		alert.setOverlayClose(false);

		JFXDialogLayout layout = new JFXDialogLayout();
		layout.setHeading(new Label(titulo));
		HBox conteudo = new HBox(imagem, new VBox(new Label(texto)));
		conteudo.setSpacing(10);
		layout.setBody(conteudo);
		layout.getStylesheets()
				.add(AlertasPopup.class.getResource("/comum/css/White_Alertas.css").toExternalForm());

		JFXButton confirmButton = new JFXButton("Confirmar");
		confirmButton.setDefaultButton(true);
		confirmButton.setOnAction(ConfirmarEvent -> {
			RESULTADO = true;
			alert.hideWithAnimation();
		});
		confirmButton.getStyleClass().add("btnConfirma");

		JFXButton cancelButton = new JFXButton("Cancelar");
		cancelButton.setCancelButton(true);
		cancelButton.setOnAction(CancelarEvent -> {
			RESULTADO = false;
			alert.hideWithAnimation();
		});
		cancelButton.getStyleClass().add("btnCancela");

		layout.setActions(confirmButton, cancelButton);
		alert.setContent(layout);

		alert.onCloseRequestProperty().set(event1 -> nodeBlur.setEffect(null));
		nodeBlur.setEffect(blur);

		// Devido a um erro no componente, não funciona o retorno padrão, será feito
		// pela variável resultado.
		alert.setResultConverter(buttonType -> {
			return null;
		});
		Optional<String> result = alert.showAndWait();
		if (result.isPresent())
			alert.setResult(null);

		return RESULTADO;
	}

	private static void dialogModern(StackPane rootStackPane, Node nodeBlur, List<JFXButton> botoes, String titulo,
			String texto, ImageView imagem) {		
		BoxBlur blur = new BoxBlur(3, 3, 3);

		if (botoes == null)
			botoes = new ArrayList<JFXButton>();

		if (botoes.isEmpty())
			botoes.add(new JFXButton("Ok"));

		JFXDialogLayout dialogLayout = new JFXDialogLayout();
		JFXDialog dialog = new JFXDialog(rootStackPane, dialogLayout, JFXDialog.DialogTransition.CENTER);

		dialog.getStylesheets()
				.add(AlertasPopup.class.getResource("/comum/css/White_Alertas.css").toExternalForm());

		botoes.forEach(controlButton -> {
			controlButton.getStyleClass().add("btnAlerta");
			controlButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) -> {
				dialog.close();
			});
		});

		dialogLayout.setHeading(new Label(titulo));

		dialogLayout.setBody(new HBox(imagem, new Label(texto)));
		dialogLayout.setActions(botoes);
		dialog.setOnDialogClosed((JFXDialogEvent event1) -> {
			nodeBlur.setEffect(null);
		});
		nodeBlur.setEffect(blur);
		dialog.show();
	}

	public static void dialogLogin(StackPane rootStackPane, Node nodeBlur, String titulo, String texto) {
		BoxBlur blur = new BoxBlur(3, 3, 3);

		List<JFXButton> botoes = new ArrayList<JFXButton>();
		botoes.add(new JFXButton("Ok"));

		JFXDialogLayout dialogLayout = new JFXDialogLayout();
		JFXDialog dialog = new JFXDialog(rootStackPane, dialogLayout, JFXDialog.DialogTransition.CENTER);

		dialog.setPadding(new Insets(0, 20, 0, 70));

		dialog.getStylesheets()
				.add(AlertasPopup.class.getResource("/comum/css/White_Alertas.css").toExternalForm());

		botoes.forEach(controlButton -> {
			controlButton.getStyleClass().add("btnAlerta");
			controlButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) -> {
				dialog.close();
			});
		});

		dialogLayout.setHeading(new Label(titulo));

		dialogLayout.setBody(new HBox(AVISO, new Label(texto)));
		dialogLayout.setActions(botoes);
		dialog.setOnDialogClosed((JFXDialogEvent event1) -> {
			nodeBlur.setEffect(null);
		});
		nodeBlur.setEffect(blur);
		dialog.show();
	}

	// Ver
	public static final String CAMINHO_ICONE = "/comum/imagens/bd/icoDataBase_48.png";

	public static void showTrayMessage(String title, String message) {
		try {
			LOGGER.log(Level.FINE, "{Mensagem do showTray: " + message + "}");
			SystemTray tray = SystemTray.getSystemTray();
			BufferedImage image = ImageIO.read(AlertasPopup.class.getResource(CAMINHO_ICONE));
			TrayIcon trayIcon = new TrayIcon(image, "Teste");
			trayIcon.setImageAutoSize(true);
			trayIcon.setToolTip("Teste");
			tray.add(trayIcon);
			trayIcon.displayMessage(title, message, MessageType.INFO);
			tray.remove(trayIcon);
		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}
}
