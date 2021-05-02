package cadastro.controller.frame;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import com.jfoenix.controls.JFXAutoCompletePopup;
import com.jfoenix.controls.JFXTextField;

import comum.model.entities.Entidade;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.robot.Robot;
import javafx.util.Callback;
import servidor.dao.services.GenericService;

/**
 * <p>
 * Classe controladora de frame genérico, utilizado para pesquisas padrões,
 * conforme for escrevendo irá filtrando a pesquisa e mostrando em um popup o
 * que foi encontrado.
 * </p>
 * <p>
 * É necessário estar iniciando o serviço de consulta, utilizando a função
 * <b>setService</b> na inicialização.
 * </p>
 * <p>
 * A classe também deve possuir um campo SITUACAO no banco [Recomenda-se ser
 * extendida de <b>EntidadeBase</b>] e estar implementando a interface
 * <i>Entidade</i>.
 * </p>
 * 
 * <p>
 * Caso alguma entidade foi selecionada, pode-se a função <b>getEntidade</b> irá
 * retornar ela, caso contrário retornará <i>null</i>.
 * </p>
 * 
 * @author Jhonny de Salles Noschang
 */
public class PesquisaGenericaController<E extends Entidade> implements Initializable {

	@FXML
	private AnchorPane frameBackground;

	@FXML
	public JFXTextField txtFraPesquisa;

	private E entidade;
	private List<E> lista = new ArrayList<E>();

	private GenericService<E> service;

	private JFXAutoCompletePopup<E> autocomplete;

	public String getDescricao() {
		return txtFraPesquisa.getText();
	}

	public E getEntidade() {
		return entidade;
	}

	public void setEntidade(E entidade) {
		this.entidade = entidade;
		txtFraPesquisa.setText(entidade.getDescricaoFrame());
	}
	
	public void limpaEntidade() {
		this.entidade = null;
	}

	private static List<KeyCode> IGNORE_KEY_CODES = new ArrayList<>();
	static {
		Collections.addAll(IGNORE_KEY_CODES,
				new KeyCode[] { KeyCode.TAB, KeyCode.SHIFT, KeyCode.CONTROL, KeyCode.ALT });
	}

	@FXML
	private void onTxtKeyPress(KeyEvent e) {
		if (IGNORE_KEY_CODES.contains(e.getCode()))
			return;

		if (e.getCode().toString().equals("ENTER")) {
			Robot robot = new Robot();
			robot.keyPress(KeyCode.TAB);
		}
	}

	public void limpaCampos() {
		txtFraPesquisa.setText("");
		lista.clear();
		entidade = null;
	}

	private Consumer<Object> ACTION = null;

	/*
	 * <p> Função que fará a chamada de uma ação quando for selecionado algum item.
	 * </p>
	 * 
	 * @param action Função a ser executada quando um item for selecionado.
	 * 
	 * @author Jhonny de Salles Noschang
	 */
	public void setSelectionAction(Consumer<Object> action) {
		this.ACTION = action;
	}

	/*
	 * <p> Necessário a inicialização do serviço do frame, pois o service precisa de
	 * uma classe para o hibernate conseguir pesquisar corretamente. </p>
	 * 
	 * @param service Serviço generico para pesquisas pelo hibernate.
	 * 
	 * @author Jhonny de Salles Noschang
	 */
	public void setService(GenericService<E> service) {
		this.service = service;
	}

	public void setPromptText(String label) {
		txtFraPesquisa.setPromptText(label);
	}

	private void configuraSugestao() {
		this.autocomplete = new JFXAutoCompletePopup<E>();

		txtFraPesquisa.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue == null || newValue.isEmpty())
				autocomplete.hide();
			else {
				if (newValue.length() == 1) {
					limpaEntidade();
					if (service == null)
						throw new IllegalStateException(
								"Não foi iniciado o service no frame. Favor utilizar a função setService no método initialize.");

					lista = service.sugestao(newValue);
					autocomplete.getSuggestions().clear();
					autocomplete.getSuggestions().addAll(lista);
				}

				autocomplete.filter(e -> e.getDescricaoFrame().toUpperCase().contains(newValue.toUpperCase()));

				if (autocomplete.getFilteredSuggestions().isEmpty())
					autocomplete.hide();
				else if (!autocomplete.isShowing())
					autocomplete.show(txtFraPesquisa);
			}
		});

		txtFraPesquisa.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
			autocomplete.hide();
		});

		// Altera a descrição pela descrição da classe. Caso contrario ele lista o
		// toString.
		autocomplete.setSuggestionsCellFactory(new Callback<ListView<E>, ListCell<E>>() {
			@Override
			public ListCell<E> call(ListView<E> l) {
				return new ListCell<E>() {
					@Override
					protected void updateItem(E item, boolean empty) {
						super.updateItem(item, empty);
						if (item == null || empty)
							setGraphic(null);
						else
							setText(item.getDescricaoFrame());
					}
				};
			}
		});

		autocomplete.setSelectionHandler(e -> {
			setEntidade(e.getObject());

			if (ACTION != null)
				ACTION.accept(e.getObject());
		});
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		configuraSugestao();
	}

	public static URL getFxmlLocate() {
		return PesquisaGenericaController.class.getResource("/cadastro/view/frame/PesquisaGenerica.fxml");
	}

}
