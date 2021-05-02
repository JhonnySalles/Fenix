package cadastro.controller.lista;

import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;

import cadastro.controller.cadastros.CadEmpresaController;
import cadastro.utils.CadastroUtils;
import comum.form.ListaFormPadrao;
import comum.model.enums.Situacao;
import comum.model.enums.TamanhoImagem;
import comum.model.mask.Mascaras;
import comum.model.utils.ViewGerenciador;
import javafx.collections.FXCollections;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import servidor.dao.services.GenericService;
import servidor.entities.Contato;
import servidor.entities.Empresa;
import servidor.entities.EmpresaImagem;
import servidor.entities.Endereco;

public class ListaEmpresaController extends ListaFormPadrao {

	@FXML
	private TableView<Empresa> tbEmpresas;

	@FXML
	private TableColumn<Empresa, String> tbClId;

	@FXML
	private TableColumn<Empresa, Set<EmpresaImagem>> tbClLogo;

	@FXML
	private TableColumn<Empresa, String> tbClRazaoSocial;

	@FXML
	private TableColumn<Empresa, String> tbClNomeFantasia;

	@FXML
	private TableColumn<Empresa, String> tbClCnpj;

	@FXML
	private TableColumn<Empresa, Timestamp> tbClDataCadastro;

	@FXML
	private TableColumn<Empresa, Set<Contato>> tbClContatoPadrao;

	@FXML
	private TableColumn<Empresa, Set<Endereco>> tbClEnderecoPadrao;

	final PseudoClass excluido = PseudoClass.getPseudoClass("excluido");

	private GenericService<Empresa> service = new GenericService<Empresa>(Empresa.class);

	@Override
	public void onBtnNovoClick() {
		CadEmpresaController ctn = (CadEmpresaController) ViewGerenciador.loadTela(CadEmpresaController.getFxmlLocate(),
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

	}

	@Override
	public void onBtnEditarClick() {

	}

	@Override
	public void onBtnAtualizarClick() {
		atualizar();
	}

	private void editar(Empresa editar) {
		if (editar == null)
			return;

		CadEmpresaController ctn = (CadEmpresaController) ViewGerenciador.loadTela(CadEmpresaController.getFxmlLocate(),
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
		tbEmpresas.setItems(FXCollections.observableArrayList(service.listar()));
		tbEmpresas.refresh();
	}

	private ListaEmpresaController linkaCelulas() {
		tbClId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tbClRazaoSocial.setCellValueFactory(new PropertyValueFactory<>("razaoSocial"));
		tbClNomeFantasia.setCellValueFactory(new PropertyValueFactory<>("nomeFantasia"));
		tbClCnpj.setCellValueFactory(new PropertyValueFactory<>("cnpj"));
		tbClDataCadastro.setCellValueFactory(new PropertyValueFactory<>("dataCadastro"));
		tbClLogo.setCellValueFactory(new PropertyValueFactory<>("imagens"));
		tbClContatoPadrao.setCellValueFactory(new PropertyValueFactory<>("contatos"));
		tbClEnderecoPadrao.setCellValueFactory(new PropertyValueFactory<>("enderecos"));

		tbClLogo.setStyle( "-fx-alignment: CENTER;");
		tbClLogo.setCellFactory(column -> {
			TableCell<Empresa, Set<EmpresaImagem>> cell = new TableCell<Empresa, Set<EmpresaImagem>>() {
				@Override
				protected void updateItem(Set<EmpresaImagem> item, boolean empty) {
					super.updateItem(item, empty);
					setGraphic(null);

					if (item != null) {
						Optional<EmpresaImagem> imagem = item.stream().parallel()
								.filter(entidade -> entidade.getTamanho().equals(TamanhoImagem.PEQUENA)).findFirst();
						if (imagem.isPresent()) {
							ImageView img = CadastroUtils.processaByteToImagemView(imagem.get().getImagem());
							img.setFitHeight(50);
							img.setFitWidth(50);
							setGraphic(img);
						}
					}
				}
			};

			return cell;
		});

		tbClContatoPadrao.setCellFactory(column -> {
			TableCell<Empresa, Set<Contato>> cell = new TableCell<Empresa, Set<Contato>>() {

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
			TableCell<Empresa, Set<Endereco>> cell = new TableCell<Empresa, Set<Endereco>>() {
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

		tbClCnpj.setCellFactory(column -> {
			TableCell<Empresa, String> cell = new TableCell<Empresa, String>() {
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
			TableCell<Empresa, Timestamp> cell = new TableCell<Empresa, Timestamp>() {
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

		tbEmpresas.setRowFactory(tv -> {
			TableRow<Empresa> row = new TableRow<>() {
				@Override
				public void updateItem(Empresa item, boolean empty) {
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

	private void filtrarLista(String texto) {
		tbEmpresas.getItems().filtered(empresa -> {
			if (texto == null || texto.isEmpty())
				return true;

			String lowerCaseFilter = texto.toLowerCase();
			if (empresa.getNomeFantasia().toLowerCase().contains(lowerCaseFilter)
					|| empresa.getRazaoSocial().toLowerCase().contains(lowerCaseFilter))
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
		return ListaEmpresaController.class.getResource("/cadastro/view/lista/ListaEmpresa.fxml");
	}

}
