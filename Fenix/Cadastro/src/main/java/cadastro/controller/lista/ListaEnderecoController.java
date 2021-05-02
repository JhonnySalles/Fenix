package cadastro.controller.lista;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import cadastro.controller.cadastros.DialogCadEnderecoController;
import comum.form.ListaFormPadrao;
import comum.model.enums.Situacao;
import comum.model.notification.Notificacoes;
import comum.model.utils.ViewGerenciador;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import servidor.entities.Endereco;

public class ListaEnderecoController extends ListaFormPadrao {

	@FXML
	private TableView<Endereco> tbEnderecos;

	@FXML
	private TableColumn<Endereco, String> tbClCidade;

	@FXML
	private TableColumn<Endereco, String> tbClBairro;

	@FXML
	private TableColumn<Endereco, String> tbClEndereco;

	@FXML
	private TableColumn<Endereco, String> tbClNumero;

	@FXML
	private TableColumn<Endereco, String> tbClCep;

	@FXML
	private TableColumn<Endereco, Boolean> tbClPadrao;

	@FXML
	private JFXTextField txtTitulo;

	@FXML
	private JFXButton btnVoltar;

	private DialogCadEnderecoController controller;

	private Set<Endereco> enderecos;
	private ToggleGroup tgGroup;

	final PseudoClass excluido = PseudoClass.getPseudoClass("excluido");

	@Override
	public void onBtnNovoClick() {
		adicionarEndereco();
	}

	@Override
	public void onBtnExcluirClick() {
		if (enderecos.isEmpty() || tbEnderecos.getSelectionModel().isEmpty())
			if (enderecos.isEmpty())
				Notificacoes.notificacao(AlertType.INFORMATION, "Não foi possível apagar item",
						"Não existe nenhum endereço cadastrado.");
			else
				Notificacoes.notificacao(AlertType.INFORMATION, "Não foi possível apagar item",
						"Não foi possível apagar o endereço, nenhum item selecionado.");
		else
			tbEnderecos.getSelectionModel().getSelectedItem().setSituacao(Situacao.EXCLUIDO);
	}

	@Override
	public void onBtnEditarClick() {

	}

	@Override
	public void onBtnAtualizarClick() {

	}

	@FXML
	public void onBtnVoltarClick() {
		ViewGerenciador.closeTela(spRoot);
		onClose();
	}

	private void adicionarEndereco() {
		abreTelaEndereco(null);
	}

	private void abreTelaEndereco(Endereco endereco) {
		controller = (DialogCadEnderecoController) ViewGerenciador
				.loadDialog(DialogCadEnderecoController.getFxmlLocate(), spRoot, new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent t) {
						enderecos.addAll(controller.getEndereco());
						carregaGrid();
					}
				});
		controller.carregar(endereco);
	}

	private void carregaGrid() {
		if (enderecos == null)
			return;
		tbEnderecos.setItems(FXCollections.observableArrayList(this.enderecos));
		tbEnderecos.refresh();
	}

	public void initData(String pessoa, Set<Endereco> enderecos) {
		txtTitulo.setText(pessoa);
		this.enderecos = enderecos;
		carregaGrid();
	}

	public Set<Endereco> getEndereco() {
		return enderecos;
	}

	private ListaEnderecoController linkaCelulas() {
		tbClCidade.setCellValueFactory(new Callback<CellDataFeatures<Endereco, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Endereco, String> p) {
				if (p.getValue() != null && p.getValue().getBairro() != null
						&& p.getValue().getBairro().getCidade() != null) {
					return new SimpleStringProperty(p.getValue().getBairro().getCidade().getNome() + " - "
							+ p.getValue().getBairro().getCidade().getEstado().getSigla());
				} else
					return new SimpleStringProperty("");
			}
		});

		tbClBairro.setCellValueFactory(new Callback<CellDataFeatures<Endereco, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Endereco, String> p) {
				// p.getValue() returns the PersonType instance for a particular TableView row
				if (p.getValue() != null && p.getValue().getBairro() != null) {
					return new SimpleStringProperty(p.getValue().getBairro().getNome());
				} else {
					return new SimpleStringProperty("");
				}
			}
		});

		tbClEndereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
		tbClNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
		tbClCep.setCellValueFactory(new PropertyValueFactory<>("cep"));

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

		tbEnderecos.setRowFactory(tv -> {
			TableRow<Endereco> row = new TableRow<>() {
				@Override
				public void updateItem(Endereco item, boolean empty) {
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
				if (event.getClickCount() == 2 && (!row.isEmpty())) {
					abreTelaEndereco(row.getItem());
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
		return ListaEnderecoController.class.getResource("/cadastro/view/lista/ListaEndereco.fxml");
	}
}
