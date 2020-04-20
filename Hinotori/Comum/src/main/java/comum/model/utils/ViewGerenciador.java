package comum.model.utils;

import java.io.IOException;
import java.util.HashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;

public class ViewGerenciador {

	private static ViewGerenciador instancia;
	private static final HashMap<String, Node> TELA_PRE_CARREGADA = new HashMap<>();

	public static ObservableList<String> stylesheets = FXCollections.observableArrayList("");
	private static String[] PRE_CARREGAMENTO = {};

	public static ViewGerenciador getInstance() {
		if (instancia == null) {
			instancia = new ViewGerenciador();
		}
		return instancia;
	}

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
	public static void preCarregamentoTelas() {
		TELA_PRE_CARREGADA.clear();
		Task<Void> preCarregamentoTask = new Task<Void>() {
			@Override
			public Void call() throws IOException, InterruptedException {
				for (int i = 0; i < PRE_CARREGAMENTO.length; i++) {
					if (!PRE_CARREGAMENTO[i].isEmpty()) {
						FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(PRE_CARREGAMENTO[i]));
						Parent novaTela = loader.load();
						TELA_PRE_CARREGADA.put(PRE_CARREGAMENTO[i], novaTela);
					}
				}
				return null;
			}
		};

		Thread thread = new Thread(preCarregamentoTask);
		thread.start();
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
	private static void preCarregamentoTelas(String absoluteName) {
		Task<Void> preCarregamentoTask = new Task<Void>() {
			@Override
			public Void call() throws IOException, InterruptedException {
				FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
				Parent novaTela = loader.load();
				TELA_PRE_CARREGADA.put(absoluteName, novaTela);
				return null;
			}
		};

		Thread thread = new Thread(preCarregamentoTask);
		thread.start();
	}

	/**
	 * <p>
	 * Função verificar se a tela já foi pré carregada pela função
	 * <b><i>preCarregamentoTelas</i></b>.
	 * </p>
	 * 
	 * @param absoluteName Endereço em <b>String</b> da tela a ser carregada.
	 * @return Retorna o <b>Parent</b> da tela já carregada préviamente.
	 * @author Jhonny de Salles Noschang
	 */
	public static Node getTelaPreCarregada(String absoluteName) {
		if (TELA_PRE_CARREGADA.containsKey(absoluteName)) {
			Node telaPre = TELA_PRE_CARREGADA.get(absoluteName);
			preCarregamentoTelas(absoluteName);
			return telaPre;

		} else
			return null;
	}

	public static String[] getPre_Ccarregamento() {
		return PRE_CARREGAMENTO;
	}

	/**
	 * <p>
	 * Função para adicionar quais telas serão carregada automaticamente.
	 * </p>
	 * 
	 * @param Pre_Carregamento Enreços em uma <b>Lista de String</b> para carregamento automático.
	 * @author Jhonny de Salles Noschang
	 */
	public static void setPre_Carregamento(String[] Pre_Carregamento) {
		PRE_CARREGAMENTO = Pre_Carregamento;
	}

	public static Boolean verificaTelaCarregada(String absoluteName) {
		System.out.println(absoluteName);
		System.out.println(TELA_PRE_CARREGADA.containsKey(absoluteName));
		return TELA_PRE_CARREGADA.containsKey(absoluteName);
	}

	public static void carregaCss(String[] listaCss) {
		stylesheets.addAll(listaCss);
	}

}
