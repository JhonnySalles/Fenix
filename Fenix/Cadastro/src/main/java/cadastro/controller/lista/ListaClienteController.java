package cadastro.controller.lista;

import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;

import cadastro.controller.cadastros.CadClienteController;
import comum.form.ListaFormPadrao;
import comum.model.alerts.AlertasPopup;
import comum.model.enums.Situacao;
import comum.model.mask.Mascaras;
import comum.model.notification.Notificacoes;
import comum.model.utils.ViewGerenciador;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import servidor.dao.services.GenericService;
import servidor.entities.Cliente;
import servidor.entities.Contato;
import servidor.entities.Endereco;

public class ListaClienteController extends ListaFormPadrao {

	@FXML
	private TableView<Cliente> tbClientes;

	@FXML
	private TableColumn<Cliente, String> tbClId;

	@FXML
	private TableColumn<Cliente, String> tbClNome;

	@FXML
	private TableColumn<Cliente, String> tbClCpf;

	@FXML
	private TableColumn<Cliente, String> tbClCnpj;

	@FXML
	private TableColumn<Cliente, Timestamp> tbClDataCadastro;

	@FXML
	private TableColumn<Cliente, Set<Contato>> tbClContatoPadrao;

	@FXML
	private TableColumn<Cliente, Set<Endereco>> tbClEnderecoPadrao;

	final PseudoClass excluido = PseudoClass.getPseudoClass("excluido");

	private GenericService<Cliente> service = new GenericService<Cliente>(Cliente.class);

	public void teste(String teste) {
		txtPesquisa.setText(teste);
	}

	@Override
	public void onBtnNovoClick() {
		CadClienteController ctn = (CadClienteController) ViewGerenciador.loadTela(CadClienteController.getFxmlLocate(),
				spRoot);
		ctn.setOnClose(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				atualizar();
			}
		});
	}

	@Override
	public void onBtnExcluirClick() {
		if (tbClientes.getItems().isEmpty())
			Notificacoes.notificacao(AlertType.INFORMATION, "Não foi possível apagar item",
					"Não existe nenhum cliente cadastrado.");
		else if (tbClientes.getSelectionModel().isEmpty())
			Notificacoes.notificacao(AlertType.INFORMATION, "Não foi possível apagar item", "Nenhum item selecionado.");
		else if (AlertasPopup.ConfirmacaoModal(spRoot, apContainer, "Exclusão",
				"Deseja realmente excluir o cliente?")) {
			service.deletar(tbClientes.getSelectionModel().getSelectedItem().getId());
			atualizar();
		}
	}

	@Override
	public void onBtnEditarClick() {
		editar(tbClientes.getSelectionModel().getSelectedItem());
	}

	@Override
	public void onBtnAtualizarClick() {
		atualizar();
	}

	private void editar(Cliente editar) {
		if (editar == null)
			return;

		CadClienteController ctn = (CadClienteController) ViewGerenciador.loadTela(CadClienteController.getFxmlLocate(),
				spRoot);
		ctn.setOnClose(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				atualizar();
			}
		});
		ctn.carregar(editar);
	}

	private void atualizar() {
		ObservableList<Cliente> obsClientes = FXCollections.observableArrayList(service.listar());
		tbClientes.setItems(obsClientes);
		tbClientes.refresh();
	}

	private ListaClienteController linkaCelulas() {
		tbClId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tbClNome.setCellValueFactory(new PropertyValueFactory<>("nomeSobrenome"));
		tbClCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
		tbClCnpj.setCellValueFactory(new PropertyValueFactory<>("cnpj"));
		tbClDataCadastro.setCellValueFactory(new PropertyValueFactory<>("dataCadastro"));
		tbClContatoPadrao.setCellValueFactory(new PropertyValueFactory<>("contatos"));
		tbClEnderecoPadrao.setCellValueFactory(new PropertyValueFactory<>("enderecos"));

		tbClContatoPadrao.setCellFactory(column -> {
			TableCell<Cliente, Set<Contato>> cell = new TableCell<Cliente, Set<Contato>>() {

				@Override
				protected void updateItem(Set<Contato> item, boolean empty) {
					super.updateItem(item, empty);
					setText(null);
					if (item != null) {
						Optional<Contato> contato = item.stream().parallel().filter(entidade -> entidade.isPadrao())
								.findFirst();
						if (contato.isPresent())
							setText(contato.get().getResumeContato());
					}
				}
			};

			return cell;
		});

		tbClEnderecoPadrao.setCellFactory(column -> {
			TableCell<Cliente, Set<Endereco>> cell = new TableCell<Cliente, Set<Endereco>>() {
				@Override
				protected void updateItem(Set<Endereco> item, boolean empty) {
					super.updateItem(item, empty);
					setText(null);
					if (item != null) {
						Optional<Endereco> endereco = item.stream().parallel().filter(entidade -> entidade.isPadrao())
								.findFirst();
						if (endereco.isPresent())
							setText(endereco.get().getResumeEndereco());
					}
				}
			};

			return cell;
		});

		tbClCpf.setCellFactory(column -> {
			TableCell<Cliente, String> cell = new TableCell<Cliente, String>() {
				@Override
				protected void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					if (empty || item == null)
						setText(null);
					else
						setText(Mascaras.formatCpf(item));
				}
			};

			return cell;
		});

		tbClCnpj.setCellFactory(column -> {
			TableCell<Cliente, String> cell = new TableCell<Cliente, String>() {
				@Override
				protected void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					if (empty || item == null)
						setText(null);
					else
						setText(Mascaras.formatCnpj(item));
				}
			};

			return cell;
		});

		tbClDataCadastro.setCellFactory(column -> {
			TableCell<Cliente, Timestamp> cell = new TableCell<Cliente, Timestamp>() {
				private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

				@Override
				protected void updateItem(Timestamp item, boolean empty) {
					super.updateItem(item, empty);
					if (empty || item == null)
						setText(null);
					else
						setText(format.format(item));
				}
			};

			return cell;
		});

		tbClientes.setRowFactory(tv -> {
			TableRow<Cliente> row = new TableRow<>() {
				@Override
				public void updateItem(Cliente item, boolean empty) {
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
				if (event.getClickCount() == 2 && (!row.isEmpty()))
					editar(row.getItem());
			});

			return row;
		});

		return this;
	}

	private void filtrarLista(String nome) {
		tbClientes.getItems().filtered(cliente -> {
			if (nome == null || nome.isEmpty())
				return true;

			String lowerCaseFilter = nome.toLowerCase();
			if (cliente.getNomeSobrenome().toLowerCase().contains(lowerCaseFilter))
				return true;
			else
				return false;
		});
	}

	@Override
	public synchronized void inicializa(URL location, ResourceBundle resources) {
		linkaCelulas();
		txtPesquisa.textProperty().addListener((observable, oldValue, newValue) -> filtrarLista(newValue));
	}

	public static URL getFxmlLocate() {
		return ListaClienteController.class.getResource("/cadastro/view/lista/ListaCliente.fxml");
	}

}
