package comum.model.alerts;

import java.util.logging.Level;
import java.util.logging.Logger;

import comum.model.alerts.controller.AlertaController;
import comum.model.alerts.controller.AvisoController;
import comum.model.alerts.controller.ConclusaoController;
import comum.model.alerts.controller.ConfirmacaoController;
import comum.model.alerts.controller.ErroController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * <p>
 * Classe responssável por apresentar alertas em janela, podendo ser um alerta
 * com borda (tala do windows) ou sem borda.
 * </p>
 * 
 * @author Jhonny de Salles Noschang
 */
public class Alertas {

	private final static Logger LOGGER = Logger.getLogger(Alertas.class.getName());

	private static boolean resposta;

	public static boolean TITULO_VISIVEL = false;
	public static boolean IMAGEM_VISIVEL = true;
	public static TipoTela TELA = TipoTela.WINDOWS;
	public static AlertType TIPO = AlertType.INFORMATION;

	/**
	 * <p>
	 * Enuns utilizado para definir o tipo de tela a ser mostrado, podendo ser uma
	 * tela do windows ou uma tela sem borda.
	 * </p>
	 * 
	 * <p>
	 * Pode-se ser acessado as váriaveis estáticas para definir se mostra o titulo,
	 * mostra o icone do alerta, tipo da tela e tipo da mensagem.
	 * </p>
	 * 
	 * <p>
	 * <b>WINDOWS, SEM_BORDA</b>
	 * </p>
	 * 
	 * @author Jhonny de Salles Noschang
	 */
	public enum TipoTela {
		WINDOWS, SEM_BORDA
	};

	private static void Limpa() {
		TITULO_VISIVEL = false;
		IMAGEM_VISIVEL = true;
		TELA = TipoTela.WINDOWS;
		TIPO = AlertType.INFORMATION;
	}

	/**
	 * <p>
	 * Função estática para apresentar um alerta na tela, por padrão será aberta uma
	 * tela do windows do tipo aviso.
	 * </p>
	 * 
	 * @param Titulo Titulo a ser apresentado no alerta.
	 * @param Texto  Texto a ser apresentado no alerta.
	 */
	public static boolean Alerta(String titulo, String texto) {
		return PreparaTela(titulo, texto);
	}

	/**
	 * <p>
	 * Função estática para apresentar um alerta na tela, por padrão será aberta uma
	 * tela do windows do tipo aviso.
	 * </p>
	 * 
	 * <p>
	 * Tela de alerta e erro terá um text field para facilitar a cópia do erro.
	 * </p>
	 * 
	 * @param Tipo   Tipo da mensagem a ser mostrada (AVISO, CONCLUSAO, ERRO,
	 *               ALERTA, CONFIRMACAO).
	 * @param Titulo Titulo a ser apresentado no alerta.
	 * @param Texto  Texto a ser apresentado no alerta.
	 */
	public static boolean Alerta(AlertType tipo, String titulo, String texto) {
		TIPO = tipo;
		return PreparaTela(titulo, texto);
	}

	/**
	 * <p>
	 * Função estática para apresentar um alerta na tela, por padrão será aberta uma
	 * tela do windows do tipo aviso.
	 * </p>
	 * 
	 * <p>
	 * Pode-se ser acessado as váriaveis estáticas para definir se mostra o titulo,
	 * mostra o icone do alerta, tipo da tela e tipo da mensagem.
	 * </p>
	 * 
	 * <p>
	 * Tela de alerta e erro terá um text field para facilitar a cópia do erro.
	 * </p>
	 * 
	 * @param Tela   Enum que irá modificar o tipo da tela, windows ou sem borda.
	 * @param Tipo   Tipo da mensagem a ser mostrada (AVISO, CONCLUSAO, ERRO,
	 *               ALERTA, CONFIRMACAO).
	 * @param Titulo Titulo a ser apresentado no alerta.
	 * @param Texto  Texto a ser apresentado no alerta.
	 */
	public static boolean Alerta(TipoTela tela, AlertType tipo, String titulo, String texto) {
		TELA = tela;
		TIPO = tipo;
		return PreparaTela(titulo, texto);
	}

	private static boolean PreparaTela(String titulo, String texto) {

		if (TIPO == AlertType.INFORMATION)
			LOGGER.log(Level.INFO, "{Mensagem de aviso ou alerta: " + texto + "}");

		resposta = true;
		switch ((AlertType) TIPO) {
		case ERROR:
			Tela_Erro(titulo, texto);
			break;
		case WARNING:
			Tela_Alerta(titulo, texto);
			break;
		case INFORMATION:
			Tela_Aviso(titulo, texto);
			break;
		case CONFIRMATION:
			resposta = false;
			Tela_Confirmacao(titulo, texto);
			break;
		case NONE:
			Tela_Aviso(titulo, texto);
			break;
		default:
			Tela_Aviso(titulo, texto);
		}

		Limpa();
		return resposta;
	}

	public static void Concluido(String titulo, String texto) {
		Tela_Concluido(titulo, texto);
		Limpa();
	}

	private static boolean Tela_Confirmacao(String titulo, String texto) {
		try {
			FXMLLoader loader = new FXMLLoader(ConfirmacaoController.getFxmlLocate());
			AnchorPane scPnTelaPrincipal = loader.load();

			// Obtem a referencia do controller para editar as label.
			ConfirmacaoController controller = loader.getController();

			controller.setTexto(titulo, texto);

			Scene tela = new Scene(scPnTelaPrincipal); // Carrega a scena

			Stage stageTela = new Stage();
			stageTela.setScene(tela); // Seta a cena principal

			controller.setEventosBotoes(cancelar -> {
				resposta = false;
				stageTela.close();
			}, confirmar -> {
				resposta = true;
				stageTela.close();
			});

			// Faz a tela ser obrigatoria para voltar ao voltar a tela anterior
			stageTela.setTitle(titulo);
			stageTela.getIcons().add(ConfirmacaoController.IMG_CONFIRMACAO);
			stageTela.initModality(Modality.APPLICATION_MODAL);

			if (TELA == TipoTela.SEM_BORDA) {
				tela.setFill(Color.TRANSPARENT);
				stageTela.initStyle(StageStyle.TRANSPARENT);
				stageTela.focusedProperty().addListener((ov, onHidden, onShown) -> {
					if (!stageTela.isFocused())
						Platform.runLater(() -> stageTela.close());
				});
			}

			controller.setVisivel(TITULO_VISIVEL, IMAGEM_VISIVEL);

			stageTela.showAndWait(); // Mostra a tela.
		} catch (Exception e) {
			System.out.println("Erro ao tentar carregar o alerta.");
			e.printStackTrace();
			LOGGER.log(Level.FINE, "{Erro ao tentar carregar o alerta. }", e);
		}
		return resposta;
	}

	private static void Tela_Alerta(String titulo, String texto) {
		try {
			FXMLLoader loader = new FXMLLoader(AlertaController.getFxmlLocate());
			AnchorPane scPnTelaPrincipal = loader.load();

			// Obtem a referencia do controller para editar as label.
			AlertaController controller = loader.getController();

			controller.setTexto(titulo, texto);

			Scene tela = new Scene(scPnTelaPrincipal); // Carrega a scena

			Stage stageTela = new Stage();
			stageTela.setScene(tela); // Seta a cena principal

			controller.setEventosBotoes(ok -> {
				stageTela.close();
			});

			// Faz a tela ser obrigatoria para voltar ao voltar a tela anterior
			stageTela.setTitle(titulo);
			stageTela.getIcons().add(AlertaController.IMG_ALERTA);
			stageTela.initModality(Modality.APPLICATION_MODAL);

			if (TELA == TipoTela.SEM_BORDA) {
				tela.setFill(Color.TRANSPARENT);
				stageTela.initStyle(StageStyle.TRANSPARENT);
				stageTela.focusedProperty().addListener((ov, onHidden, onShown) -> {
					if (!stageTela.isFocused())
						Platform.runLater(() -> stageTela.close());
				});
			}

			controller.setVisivel(TITULO_VISIVEL, IMAGEM_VISIVEL);

			stageTela.showAndWait(); // Mostra a tela.
		} catch (Exception e) {
			System.out.println("Erro ao tentar carregar o alerta.");
			e.printStackTrace();
		}
	}

	private static void Tela_Erro(String titulo, String texto) {
		try {
			FXMLLoader loader = new FXMLLoader(ErroController.getFxmlLocate());
			AnchorPane scPnTelaPrincipal = loader.load();

			// Obtem a referencia do controller para editar as label.
			ErroController controller = loader.getController();

			controller.setTexto(titulo, texto);

			Scene tela = new Scene(scPnTelaPrincipal); // Carrega a scena

			Stage stageTela = new Stage();
			stageTela.setScene(tela); // Seta a cena principal

			controller.setEventosBotoes(ok -> {
				stageTela.close();
			});

			// Faz a tela ser obrigatoria para voltar ao voltar a tela anterior
			stageTela.setTitle(titulo);
			stageTela.getIcons().add(ErroController.IMG_ERRO);
			stageTela.initModality(Modality.APPLICATION_MODAL);

			if (TELA == TipoTela.SEM_BORDA) {
				tela.setFill(Color.TRANSPARENT);
				stageTela.initStyle(StageStyle.TRANSPARENT);
				stageTela.focusedProperty().addListener((ov, onHidden, onShown) -> {
					if (!stageTela.isFocused())
						Platform.runLater(() -> stageTela.close());
				});
			}

			controller.setVisivel(TITULO_VISIVEL, IMAGEM_VISIVEL);

			stageTela.showAndWait(); // Mostra a tela.
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.log(Level.FINE, "{Erro ao tentar carregar o alerta. }", e);
		}
	}

	private static void Tela_Concluido(String titulo, String texto) {
		try {
			FXMLLoader loader = new FXMLLoader(ConclusaoController.getFxmlLocate());
			AnchorPane scPnTelaPrincipal = loader.load();

			// Obtem a referencia do controller para editar as label.
			ConclusaoController controller = loader.getController();

			controller.setTexto(titulo, texto);

			Scene tela = new Scene(scPnTelaPrincipal); // Carrega a scena

			Stage stageTela = new Stage();
			stageTela.setScene(tela); // Seta a cena principal

			controller.setEventosBotoes(ok -> {
				stageTela.close();
			});

			// Faz a tela ser obrigatoria para voltar ao voltar a tela anterior
			stageTela.setTitle(titulo);
			stageTela.getIcons().add(ConclusaoController.IMG_CONCLUSAO);
			stageTela.initModality(Modality.APPLICATION_MODAL);

			if (TELA == TipoTela.SEM_BORDA) {
				tela.setFill(Color.TRANSPARENT);
				stageTela.initStyle(StageStyle.TRANSPARENT);
				stageTela.focusedProperty().addListener((ov, onHidden, onShown) -> {
					if (!stageTela.isFocused())
						Platform.runLater(() -> stageTela.close());
				});
			}

			controller.setVisivel(TITULO_VISIVEL, IMAGEM_VISIVEL);

			stageTela.showAndWait(); // Mostra a tela.
		} catch (Exception e) {
			System.out.println("Erro ao tentar carregar o alerta.");
			e.printStackTrace();
			LOGGER.log(Level.FINE, "{Erro ao tentar carregar o alerta. }", e);
		}
	}

	private static void Tela_Aviso(String titulo, String texto) {
		try {
			FXMLLoader loader = new FXMLLoader(AvisoController.getFxmlLocate());
			AnchorPane scPnTelaPrincipal = loader.load();

			// Obtem a referencia do controller para editar as label.
			AvisoController controller = loader.getController();

			controller.setTexto(titulo, texto);

			Scene tela = new Scene(scPnTelaPrincipal); // Carrega a scena

			Stage stageTela = new Stage();
			stageTela.setScene(tela); // Seta a cena principal

			controller.setEventosBotoes(cancelar -> {
				stageTela.close();
			});

			// Faz a tela ser obrigatoria para voltar ao voltar a tela anterior
			stageTela.setTitle(titulo);
			stageTela.getIcons().add(AvisoController.IMG_AVISO);
			stageTela.initModality(Modality.APPLICATION_MODAL);

			if (TELA == TipoTela.SEM_BORDA) {
				tela.setFill(Color.TRANSPARENT);
				stageTela.initStyle(StageStyle.TRANSPARENT);
				stageTela.focusedProperty().addListener((ov, onHidden, onShown) -> {
					if (!stageTela.isFocused())
						Platform.runLater(() -> stageTela.close());
				});
			}

			controller.setVisivel(TITULO_VISIVEL, IMAGEM_VISIVEL);

			stageTela.showAndWait(); // Mostra a tela.
		} catch (Exception e) {
			System.out.println("Erro ao tentar carregar o alerta.");
			e.printStackTrace();
			LOGGER.log(Level.FINE, "{Erro ao tentar carregar o alerta. }", e);
		}
	}

}
