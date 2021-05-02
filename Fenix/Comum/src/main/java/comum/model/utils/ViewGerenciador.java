package comum.model.utils;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.events.JFXDialogEvent;

import comum.form.CadastroDialogPadrao;
import comum.form.DashboardFormPadrao;
import comum.model.animation.TelaAnimation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

/**
 * <p>
 * Classe responssável por executar as funções de carregamento das telas,
 * retornará a tela quando já previamente carregada ou então irá executar como
 * tarefas o carregamento da tela solicitada. Também irá ser responsável por
 * manter uma cópia em memória das telas previamente carregada ou que foram
 * carregada por ele para melhor desempenho do sistema como um todo.
 * </p>
 * 
 * @author Jhonny de Salles Noschang
 */
public class ViewGerenciador {

	private final static Logger LOGGER = Logger.getLogger(ViewGerenciador.class.getName());

	// ********************************************************************************//
	// Classe para guardar a referencia das telas aberta e controlador
	// *******************************************************************************//
	private static class Tela {
		private Node tela;
		private Object controlador;

		public Node getTela() {
			return tela;
		}

		public Object getControlador() {
			return controlador;
		}

		public Tela(Node tela, Object controlador) {
			this.tela = tela;
			this.controlador = controlador;
		}
	}

	// A dependencia ao form do dashboard é feito na inicialização dele
	private static DashboardFormPadrao DASHBOARD_MAIN = DashboardFormPadrao.getInstancia();
	private static ViewGerenciador INSTANCIA;
	private static final HashMap<URL, Tela> TELA_PRE_CARREGADA = new HashMap<>();
	public static ObservableList<String> stylesheets = FXCollections.observableArrayList("");
	private static URL[] CAMINHO_TELAS_PRE_CARREGAMENTO = {};

	public static ViewGerenciador getInstance() {
		if (INSTANCIA == null)
			INSTANCIA = new ViewGerenciador();

		return INSTANCIA;
	}

	private static void getDashBoard() {
		DASHBOARD_MAIN = DashboardFormPadrao.getInstancia();
	}

	// ********************************************************************************//
	// Métodos do pre-carregamento
	// *******************************************************************************//

	/**
	 * <p>
	 * Função para fazer o pré carregamento das telas através de uma task, no qual é
	 * chamada assim que o dashboard inicia.
	 * </p>
	 * <p>
	 * Utiliza o <b>Array <i>telasPreCarregamento</i></b> para obter o nome dos
	 * <b>fxml</b> que devem ser carregadas.
	 * </p>
	 * 
	 * @author Jhonny de Salles Noschang
	 */
	private static void preCarregamentoTelas() {
		TELA_PRE_CARREGADA.clear();
		Task<Void> preCarregamentoTask = new Task<Void>() {
			@Override
			public Void call() throws IOException, InterruptedException {
				for (int i = 0; i < CAMINHO_TELAS_PRE_CARREGAMENTO.length; i++) {
					if (!CAMINHO_TELAS_PRE_CARREGAMENTO[i].getPath().isEmpty()) {
						FXMLLoader loader = new FXMLLoader(CAMINHO_TELAS_PRE_CARREGAMENTO[i]);
						TELA_PRE_CARREGADA.put(CAMINHO_TELAS_PRE_CARREGAMENTO[i],
								new Tela(loader.load(), loader.getController()));
					}
				}
				return null;
			}
		};

		Thread preCarregamento = new Thread(preCarregamentoTask);
		preCarregamento.start();
	}

	/**
	 * <p>
	 * Função para fazer o pré carregamento de outra tela já carregada com task,
	 * para que ao acessar novamente após fechar a tela, não carregue todos os dados
	 * na tela.</b>
	 * 
	 * @param absoluteName Endereço em <b>String</b> da tela a ser carregada.
	 * @author Jhonny de Salles Noschang
	 */
	private static void carregamentoTela(URL absoluteName) {
		Task<Void> carregamentoTelaTask = new Task<Void>() {
			@Override
			public Void call() throws IOException, InterruptedException {
				FXMLLoader loader = new FXMLLoader(absoluteName);
				TELA_PRE_CARREGADA.put(absoluteName, new Tela(loader.load(), loader.getController()));
				return null;
			}
		};

		Thread tela = new Thread(carregamentoTelaTask);
		tela.start();
	}

	/**
	 * <p>
	 * Função obter a tela pré carregada pela função
	 * <b><i>preCarregamentoTelas</i></b>.
	 * </p>
	 * 
	 * @param absoluteName Endereço em <b>String</b> da tela a ser carregada.
	 * @return Retorna um <b>Node</b> da tela já carregada préviamente.
	 * @author Jhonny de Salles Noschang
	 */
	public static Node getTelaPreCarregada(URL absoluteName) {
		if (TELA_PRE_CARREGADA.containsKey(absoluteName)) {
			Node telaPre = TELA_PRE_CARREGADA.get(absoluteName).getTela();
			carregamentoTela(absoluteName);
			return telaPre;
		} else
			return null;
	}

	/**
	 * <p>
	 * Função obter o controlador da tela que já foi pré carregada pela função
	 * <b><i>preCarregamentoTelas</i></b>.
	 * </p>
	 * 
	 * @param absoluteName Endereço em <b>String</b> da tela a ser carregada.
	 * @return Retorna um <b>Object</b> contendo o controlador da tela solicitada.
	 * @author Jhonny de Salles Noschang
	 */
	public static Object getControllerTelaPreCarregada(URL absoluteName) {
		if (TELA_PRE_CARREGADA.containsKey(absoluteName)) {
			return TELA_PRE_CARREGADA.get(absoluteName).getControlador();
		} else
			return null;
	}

	/**
	 * <p>
	 * Função para verificar se a tela já foi pré carregada pela função
	 * <b><i>preCarregamentoTelas</i></b>.
	 * </p>
	 * 
	 * @param absoluteName Endereço em <b>String</b> da tela a ser carregada.
	 * @return Um boleano que informa se a tela existe no pré carregaemnto.
	 * @author Jhonny de Salles Noschang
	 */
	public static Boolean verificaTelaCarregada(URL absoluteName) {
		return TELA_PRE_CARREGADA.containsKey(absoluteName);
	}

	/**
	 * <p>
	 * Função para adicionar quais telas serão carregada automaticamente.
	 * </p>
	 * 
	 * @param listaTelas Endereços em uma <b>Lista de URL</b> para carregamento
	 *                   automático.
	 * @author Jhonny de Salles Noschang
	 */
	public static void setCaminhoTelasPreCarregamento(URL[] listaTelas) {
		CAMINHO_TELAS_PRE_CARREGAMENTO = listaTelas;
		preCarregamentoTelas();
	}

	public static URL[] getCaminhoTelasPreCarregamento() {
		return CAMINHO_TELAS_PRE_CARREGAMENTO;
	}

	public static void carregaCss(String[] listaCss) {
		stylesheets.addAll(listaCss);
	}

	// ********************************************************************************//
	// Métodos staticos para sobreposição de tela
	// *******************************************************************************//
	// Irá mapear as abas abertas.
	final static private Map<Node, TelaAberta> TELA_SOBREPOSTA = new HashMap<>();

	// ********************************************************************************//
	// Classe para guardar a referencia das telas aberta e controlador
	// *******************************************************************************//
	private static class TelaAberta {
		private Pane telaPai;
		private Object controlador;

		public Pane getTelaPai() {
			return telaPai;
		}

		public Object getControlador() {
			return controlador;
		}

		public TelaAberta(Pane telaPai, Object controlador) {
			this.telaPai = telaPai;
			this.controlador = controlador;
		}
	}

	/**
	 * <p>
	 * Função estatica apenas para fazer o carregamento de um panel em cima de
	 * outro, com a animação de movimentação dos panels para a esquerda e fechamento
	 * para a direita.
	 * </p>
	 * 
	 * @param absoluteName Endereço em <b>String</b> da tela a ser carregada.
	 * @param rootPane     O campo deverá ser o root pane de algum componente que
	 *                     herde de <b>Pane</b>, como <b>StackPane</b>,
	 *                     <b>AnchorPane</b>, entre outros.
	 * @return Retorna um objeto no formato do controlador da tela aberta, caso
	 *         ocorra algum erro erro irá retornar null.
	 * 
	 * @author Jhonny de Salles Noschang
	 */
	public synchronized static Object loadTela(URL absoluteName, Pane rootPane) {
		Node apFilho = null;
		Object controlador = null;
		if (ViewGerenciador.verificaTelaCarregada(absoluteName)) {
			apFilho = ViewGerenciador.getTelaPreCarregada(absoluteName);
			controlador = ViewGerenciador.getControllerTelaPreCarregada(absoluteName);
		} else {

			FXMLLoader loader = new FXMLLoader(absoluteName);
			try {
				apFilho = loader.load();
				controlador = loader.getController();
			} catch (IOException e) {
				e.printStackTrace();
				LOGGER.log(Level.SEVERE, "{Erro ao carregar a tela: " + absoluteName + "}", e);
			}
		}

		if (apFilho != null) {
			rootPane.getChildren().add(apFilho);
			TELA_SOBREPOSTA.put(apFilho, new TelaAberta(rootPane, controlador));
			new TelaAnimation().abrirPane(rootPane, apFilho);

			if (DASHBOARD_MAIN != null)
				DASHBOARD_MAIN.atualizaTabPane();
			else
				getDashBoard();
		}

		return controlador;
	}

	/**
	 * <p>
	 * Função estatica que faz o fechamento da tela e a troca para a tela que está
	 * logo acima da cadeia de tela. Ou seja, troca a tela atual pela tela pai.
	 * </p>
	 * 
	 * @param rootPane O rootPane da tela em que deseja que seja fechada, caso não
	 *                 encontrado o sistema retorna sem fazer nada.
	 * 
	 * @author Jhonny de Salles Noschang
	 */
	public static void closeTela(Pane rootPane) {
		if (!TELA_SOBREPOSTA.containsKey(rootPane))
			return;

		new TelaAnimation().fecharPane(TELA_SOBREPOSTA.get(rootPane).getTelaPai());
		TELA_SOBREPOSTA.remove(rootPane);
	}

	/**
	 * <p>
	 * Função que retorna o Pane da tela em que a tela está sobreposto, ou seja
	 * retorna o pane da tela pai.
	 * </p>
	 * 
	 * @param rootPane O rootPane da tela em que a tela está em cima.
	 * @return Retorna um <b>Pane</b> da tela superior a ela.
	 * 
	 * @author Jhonny de Salles Noschang
	 */
	public static Pane getPaneRoot(Node rootPane) {
		if (!TELA_SOBREPOSTA.containsKey(rootPane))
			return null;

		return TELA_SOBREPOSTA.get(rootPane).getTelaPai();
	}

	/**
	 * <p>
	 * Função que retorna o controlador da tela filho, ou seja, o controlador da
	 * tela que foi solicitada a abertura.
	 * </p>
	 * 
	 * @param rootPane O root pane da tela que se solicita o controlador.
	 * @return Retorna um <b>Pane</b> da tela superior a ela.
	 * 
	 * @author Jhonny de Salles Noschang
	 */
	public static Object getController(Node rootPane) {
		if (!TELA_SOBREPOSTA.containsKey(rootPane))
			return null;

		return TELA_SOBREPOSTA.get(rootPane).getControlador();
	}

	/**
	 * <p>
	 * Função estatica para abrir uma caixa de dialogo, onde o conteudo atras ficará
	 * esmaecido.
	 * </p>
	 * 
	 * @param absoluteName Endereço em <b>String</b> da tela a ser carregada.
	 * @param spRoot       Obrigatóriamente a tela deverá ter um <b>StackPane</b>,
	 *                     para que a função consiga fazer a abertura.
	 * @param onOpen       Função a ser chamada quando o dialog for aberto.
	 * @param spRoot       Função a ser chamada quando o dialog for fechado.
	 * @return Retorna um objeto no formato do controlador da tela aberta, caso
	 *         apresente erro irá retornar null.
	 * 
	 * @author Jhonny de Salles Noschang
	 */
	public synchronized static Object loadDialog(URL absoluteName, StackPane spRoot, EventHandler<ActionEvent> onOpen,
			EventHandler<ActionEvent> onClose) {
		StackPane root = null;

		if (TELA_SOBREPOSTA.containsKey(spRoot))
			root = (StackPane) TELA_SOBREPOSTA.get(spRoot).getTelaPai();
		else
			root = spRoot;

		FXMLLoader loader = new FXMLLoader(absoluteName);
		try {
			Node apRoot = spRoot.getChildren().get(0);
			AnchorPane tela = loader.load();
			final Object controller = loader.getController();

			BoxBlur blur = new BoxBlur(3, 3, 3);
			JFXDialog dialog = new JFXDialog(root, tela, JFXDialog.DialogTransition.CENTER);

			apRoot.setDisable(true);
			if (onOpen != null)
				dialog.setOnDialogOpened((JFXDialogEvent eventOpen) -> {
					onOpen.handle(new ActionEvent(onOpen, null));
				});

			dialog.setOnDialogClosed((JFXDialogEvent eventClose) -> {
				apRoot.setEffect(null);
				apRoot.setDisable(false);

				// Os componentes de cadastro que extender de cadastro dialog podem utilizar a
				// função onClose para eventual validação.
				if (controller instanceof CadastroDialogPadrao)
					((CadastroDialogPadrao<?>) controller).onClose();

				if (onClose != null)
					onClose.handle(new ActionEvent(onClose, null));
			});

			apRoot.setEffect(blur);

			if (DASHBOARD_MAIN != null)
				DASHBOARD_MAIN.atualizaTabPane();
			else
				getDashBoard();

			dialog.show();

			return controller;

		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.log(Level.SEVERE, "{Erro ao carregar a tela: " + absoluteName + "}", e);
		}
		return null;
	}

	/**
	 * <p>
	 * Função estatica para abrir uma caixa de dialogo, onde o conteudo atras ficará
	 * esmaecido.
	 * </p>
	 * 
	 * @param absoluteName Endereço em <b>String</b> da tela a ser carregada.
	 * @param spRoot       Obrigatóriamente a tela deverá ter um <b>StackPane</b>,
	 *                     para que a função consiga fazer a abertura.
	 * @return Retorna um objeto no formato do controlador da tela aberta, caso
	 *         apresente erro irá retornar null.
	 * 
	 * @author Jhonny de Salles Noschang
	 */
	public static Object loadDialog(URL absoluteName, StackPane spRoot) {
		return loadDialog(absoluteName, spRoot, null, null);
	}

	/**
	 * <p>
	 * Função estatica para abrir uma caixa de dialogo, onde o conteudo atras ficará
	 * esmaecido.
	 * </p>
	 * 
	 * @param absoluteName Endereço em <b>String</b> da tela a ser carregada.
	 * @param spRoot       Obrigatóriamente a tela deverá ter um <b>StackPane</b>,
	 *                     para que a função consiga fazer a abertura.
	 * @param spRoot       Função a ser chamada quando o dialog for fechado.
	 * @return Retorna um objeto no formato do controlador da tela aberta, caso
	 *         apresente erro irá retornar null.
	 * 
	 * @author Jhonny de Salles Noschang
	 */
	public static Object loadDialog(URL absoluteName, StackPane spRoot, EventHandler<ActionEvent> onClose) {
		return loadDialog(absoluteName, spRoot, null, onClose);
	}

}
