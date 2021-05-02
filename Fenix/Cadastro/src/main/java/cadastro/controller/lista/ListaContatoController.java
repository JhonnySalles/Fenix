package cadastro.controller.lista;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import cadastro.controller.cadastros.DialogCadContatoController;
import comum.form.ListaFormPadrao;
import comum.model.enums.Situacao;
import comum.model.notification.Notificacoes;
import comum.model.utils.ViewGerenciador;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import servidor.entities.Contato;

public class ListaContatoController extends ListaFormPadrao {

	@FXML
	private TableView<Contato> tbContatos;

	@FXML
	private TableColumn<Contato, String> tbClNome;

	@FXML
	private TableColumn<Contato, String> tbClTelefone;

	@FXML
	private TableColumn<Contato, String> tbClCelular;

	@FXML
	private TableColumn<Contato, String> tbClEmail;

	@FXML
	private TableColumn<Contato, String> tbClTipoContato;

	@FXML
	private TableColumn<Contato, String> tbClSituacao;

	@FXML
	private TableColumn<Contato, Boolean> tbClPadrao;

	@FXML
	private JFXTextField txtTitulo;

	@FXML
	private JFXButton btnVoltar;

	private DialogCadContatoController controller;

	private ObservableList<Contato> obsContatos;
	private Set<Contato> contatos;
	private ToggleGroup tgGroup;

	final PseudoClass excluido = PseudoClass.getPseudoClass("excluido");

	@Override
	public void onBtnNovoClick() {
		adicionarContato();
	}

	@Override
	public void onBtnExcluirClick() {
		if (contatos.isEmpty())
			Notificacoes.notificacao(AlertType.INFORMATION, "Não foi possível apagar item",
					"Não existe nenhum endereço cadastrado.");
		else if (tbContatos.getSelectionModel().isEmpty())
			Notificacoes.notificacao(AlertType.INFORMATION, "Não foi possível apagar item", "Nenhum item selecionado.");
		else {
			tbContatos.getSelectionModel().getSelectedItem().setSituacao(Situacao.EXCLUIDO);
			tbContatos.refresh();
		}
	}

	@Override
	public void onBtnEditarClick() {
		if (tbContatos.getSelectionModel().getSelectedItem() == null)
			return;

		abreTelaContato(tbContatos.getSelectionModel().getSelectedItem());
	}

	@Override
	public void onBtnAtualizarClick() {

	}

	@FXML
	public void onVoltarKeyPress(KeyEvent e) {
		if (e.getCode().toString().equals("ENTER"))
			btnVoltar.fire();
	}

	@FXML
	public void onBtnVoltarClick() {
		ViewGerenciador.closeTela(spRoot);
		onClose();
	}

	private void adicionarContato() {
		abreTelaContato(null);
	}

	private void abreTelaContato(Contato contato) {
		controller = (DialogCadContatoController) ViewGerenciador.loadDialog(DialogCadContatoController.getFxmlLocate(),
				spRoot, new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent t) {
						contatos.addAll(controller.getContato());
						carregaGrid();
					}
				});
		controller.carregar(contato);
	}

	private void carregaGrid() {
		if (contatos == null)
			return;

		obsContatos = FXCollections.observableArrayList(this.contatos);
		tbContatos.setItems(obsContatos);
		tbContatos.refresh();
	}

	public void initData(String pessoa, Set<Contato> contatos) {
		txtTitulo.setText(pessoa);
		this.contatos = contatos;
		carregaGrid();
	}

	public Set<Contato> getContato() {
		return contatos;
	}

	private ListaContatoController linkaCelulas() {
		tbClNome.setCellValueFactory(new PropertyValueFactory<>("nomeSobrenome"));
		tbClTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
		tbClCelular.setCellValueFactory(new PropertyValueFactory<>("celular"));
		tbClEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		tbClTipoContato.setCellValueFactory(new PropertyValueFactory<>("tipoContato"));
		tbClSituacao.setCellValueFactory(new PropertyValueFactory<>("situacao"));

		// Fará o controle dos radios button para que apenas um fique marcado
		tgGroup = new ToggleGroup();

		tbClPadrao.setCellValueFactory(new PropertyValueFactory<>("padrao"));
		tbClPadrao.setCellFactory(param -> new TableCell<>() {
			JFXRadioButton indicator = new JFXRadioButton();
			{
				indicator.selectedColorProperty().setValue(Color.BLUE);
				setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
				tgGroup.getToggles().add(indicator);

				indicator.selectedProperty().addListener((obs, wasActive, isNowActive) -> {
					if (getIndex() > -1)
						getTableView().getItems().get(getIndex()).setPadrao(isNowActive);
				});
			}

			@Override
			protected void updateItem(Boolean isPadrao, boolean empty) {
				super.updateItem(isPadrao, empty);

				if (empty || isPadrao == null) {
					setGraphic(null);
				} else {
					indicator.setSelected(isPadrao);
					setGraphic(indicator);
				}
			}
		});

		tbContatos.setRowFactory(tv -> {
			TableRow<Contato> row = new TableRow<>() {
				@Override
				public void updateItem(Contato item, boolean empty) {
					super.updateItem(item, empty);
					if (item == null) {
						setStyle("");
						pseudoClassStateChanged(excluido, false);
					} else {
						if (item.getSituacao() == Situacao.EXCLUIDO)
							pseudoClassStateChanged(excluido, true);
						else
							pseudoClassStateChanged(excluido, false);
					}
				}
			};

			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 3 && (!row.isEmpty())) {
					abreTelaContato(row.getItem());
				}
			});

			return row;
		});

		return this;
	}

	@Override
	public synchronized void inicializa(URL location, ResourceBundle resources) {
		linkaCelulas();
	}

	public static URL getFxmlLocate() {
		return ListaContatoController.class.getResource("/cadastro/view/lista/ListaContato.fxml");
	}

}
