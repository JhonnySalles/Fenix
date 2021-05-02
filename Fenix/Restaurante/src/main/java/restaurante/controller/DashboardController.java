package restaurante.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXScrollPane;
import com.jfoenix.svg.SVGGlyph;

import cadastro.controller.cadastros.CadClienteController;
import comum.form.DashboardFormPadrao;
import comum.model.alerts.AlertasPopup;
import comum.model.entities.Configuracao;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.control.Tab;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class DashboardController extends DashboardFormPadrao {

	private final static Logger LOGGER = Logger.getLogger(DashboardController.class.getName());

	private static Configuracao conexao;

	@FXML
	private Tab tbDashBoardGraficos;

	@FXML
	private AnchorPane apDashBoardGraficos;

	@FXML
	private JFXScrollPane scPaneDashGraficos;

	@FXML
	private HBox hBoxTopo;

	@FXML
	private ImageView imgLogo;

	@FXML
	private JFXButton btnCadastros;

	@FXML
	private JFXButton btnLancamentos;

	@FXML
	private JFXButton btnPesquisas;

	@FXML
	private JFXButton btnConfiguracoes;

	@FXML
	private Tooltip tootBd;

	@FXML
	private ImageView imgBd;

	@FXML
	private void onBtnCadastrosAction() {
		loadBotoes(getClass().getResource("/restaurante/view/cadastros/Cadastros.fxml"));
	}

	@FXML
	private void onBtnLancamentosAction() {

	}

	@FXML
	private void onBtnPesquisasAction() {
		loadBotoes(getClass().getResource("/restaurante/view/pesquisas/Pesquisas.fxml"));
	}

	@FXML
	private void onBtnConfiguracaoAction() {

	}

	@FXML
	private void onMouseEnterApBotoes() {
		tmLineAbrir.play();
	}

	@FXML
	private void onMouseExitApBotoes() {
		tmLineAbrir.stop();
	}

	/**
	 * <p>
	 * Chama o método de verificar conexão animando o icone do dashboard, também
	 * obtem os dados da conexão.
	 * </p>
	 * 
	 * @author Jhonny de Salles Noschang
	 */
	public void verificaConexao() {
		// conexao = DBConnection.testaConexaoMySQL(imgBd, tootBd);
		// ***************************************************************
	}

	public Configuracao getConexao() {
		return conexao;
	}

	// Irá inicializar a parte do dashboard grafico
	private void inicializaGraficos() {

		JFXButton btnAtualiza = new JFXButton("");
		SVGGlyph glpIcone = new SVGGlyph(0, "FULLSCREEN",
				"M356.577,20.47C244.17-27.11,114.517,10.696,45.89,109.82L23.99,93.4C14.28,86.06,0,92.97,0,105.4v150    c0,11.04,11.57,18.37,21.71,13.42l120-60c9.91-4.97,11.22-18.71,2.28-25.42l-25.6-19.21c48.82-74.16,140.467-87.34,203.137-60.84    c134.21,56.85,134.01,248.701-0.02,305.421c-85.863,36.352-183.214-4.356-218.557-87.82c-9.67-22.91-36.04-33.62-58.95-23.93    c-22.95,9.67-33.62,36.12-23.94,58.94c54.26,128.62,205.407,191.23,336.488,135.69C563.958,404.081,563.667,107.95,356.577,20.47z",
				Color.WHITE);
		glpIcone.setSize(30, 30);
		btnAtualiza.setGraphic(glpIcone);
		btnAtualiza.setRipplerFill(Color.WHITE);
		scPaneDashGraficos.getTopBar().getChildren().add(btnAtualiza);
		StackPane.setAlignment(btnAtualiza, Pos.CENTER_LEFT);
		StackPane.setMargin(btnAtualiza, new Insets(20, 0, 0, 10));

		try {
			FXMLLoader loaderMeio = new FXMLLoader(
					getClass().getResource("/restaurante/view/metricas/DashBoardGraficosTituloMeio.fxml"));
			StackPane spMeio = loaderMeio.load();
			scPaneDashGraficos.getMidBar().getChildren().add(spMeio);
			StackPane.setMargin(spMeio, new Insets(0, 0, 0, 80));
			StackPane.setAlignment(spMeio, Pos.CENTER_LEFT);
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.log(Level.SEVERE, "{Erro ao carregar os gráficos do dashboard}", e);
		}

		try {
			FXMLLoader loaderBase = new FXMLLoader(
					getClass().getResource("/restaurante/view/metricas/DashBoardGraficosTituloBase.fxml"));
			StackPane spBase = loaderBase.load();
			scPaneDashGraficos.getBottomBar().getChildren().add(spBase);
			StackPane.setMargin(spBase, new Insets(0, 0, 0, 80));
			StackPane.setAlignment(spBase, Pos.CENTER_LEFT);
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.log(Level.SEVERE, "{Erro ao carregar os gráficos do dashboard}", e);
		}

		try {
			FXMLLoader loaderConteudo = new FXMLLoader(
					getClass().getResource("/restaurante/view/metricas/DashBoardGraficos.fxml"));
			StackPane spConteudo = loaderConteudo.load();
			spConteudo.setPadding(new Insets(24));
			scPaneDashGraficos.setContent(spConteudo);
			StackPane.setAlignment(spConteudo, Pos.TOP_CENTER);
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.log(Level.SEVERE, "{Erro ao carregar os gráficos do dashboard}", e);
		}
	}

	@Override
	public synchronized void inicializa(URL arg0, ResourceBundle arg1) {
		verificaConexao();
		inicializaGraficos();

		/* Popup de descricao dos botoes */
		btnCadastros.setTooltip(new Tooltip("Cadastros"));
		btnLancamentos.setTooltip(new Tooltip("Lançamentos"));
		btnPesquisas.setTooltip(new Tooltip("Pesquisas"));
		btnConfiguracoes.setTooltip(new Tooltip("Configurações"));

		/* Setando as variáveis para o alerta padrão. */
		AlertasPopup.setNodeBlur(rootStackPane);
		AlertasPopup.setNodeBlur(splPane);

		rootStackPane.setCache(true);
		rootStackPane.setCacheHint(CacheHint.SPEED);
		apGlobal.setCache(true);
		apGlobal.setCacheHint(CacheHint.SPEED);
	}
	
	public static URL getFxmlLocate() {
		return CadClienteController.class.getResource("/restaurante/view/Dashboard.fxml");
	}

}
