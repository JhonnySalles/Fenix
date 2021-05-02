package cadastro.controller.lista;

import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;

import cadastro.controller.cadastros.CadUsuarioController;
import cadastro.utils.CadastroUtils;
import comum.form.ListaFormPadrao;
import comum.model.enums.Situacao;
import comum.model.enums.TamanhoImagem;
import comum.model.enums.UsuarioNivel;
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
import servidor.dao.services.UsuarioService;
import servidor.entities.Contato;
import servidor.entities.Usuario;
import servidor.entities.UsuarioImagem;

public class ListaUsuarioController extends ListaFormPadrao {

	@FXML
	private TableView<Usuario> tbUsuarios;

	@FXML
	private TableColumn<Usuario, String> tbClId;

	@FXML
	private TableColumn<Usuario, Set<UsuarioImagem>> tbClImagem;

	@FXML
	private TableColumn<Usuario, String> tbClNome;

	@FXML
	private TableColumn<Usuario, String> tbClLogin;

	@FXML
	private TableColumn<Usuario, Set<Contato>> tbClContatoPadrao;

	@FXML
	private TableColumn<Usuario, String> tbClObservacao;

	@FXML
	private TableColumn<Usuario, UsuarioNivel> tbClNivel;

	@FXML
	private TableColumn<Usuario, Timestamp> tbClDataCadastro;

	final PseudoClass excluido = PseudoClass.getPseudoClass("excluido");

	private UsuarioService service = new UsuarioService();

	@Override
	public void onBtnNovoClick() {
		CadUsuarioController ctn = (CadUsuarioController) ViewGerenciador.loadTela(CadUsuarioController.getFxmlLocate(),
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

	private void editar(Usuario editar) {
		if (editar == null)
			return;

		CadUsuarioController ctn = (CadUsuarioController) ViewGerenciador.loadTela(CadUsuarioController.getFxmlLocate(),
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
		tbUsuarios.setItems(FXCollections.observableArrayList(service.listar()));
		tbUsuarios.refresh();
	}

	private ListaUsuarioController linkaCelulas() {
		tbClId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tbClNome.setCellValueFactory(new PropertyValueFactory<>("nomeSobrenome"));
		tbClLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
		tbClObservacao.setCellValueFactory(new PropertyValueFactory<>("observacao"));
		tbClDataCadastro.setCellValueFactory(new PropertyValueFactory<>("dataCadastro"));
		tbClNivel.setCellValueFactory(new PropertyValueFactory<>("nivel"));
		tbClImagem.setCellValueFactory(new PropertyValueFactory<>("imagens"));
		tbClContatoPadrao.setCellValueFactory(new PropertyValueFactory<>("contatos"));

		tbClImagem.setStyle( "-fx-alignment: CENTER;");
		tbClImagem.setCellFactory(column -> {
			TableCell<Usuario, Set<UsuarioImagem>> cell = new TableCell<Usuario, Set<UsuarioImagem>>() {
				@Override
				protected void updateItem(Set<UsuarioImagem> item, boolean empty) {
					super.updateItem(item, empty);
					setGraphic(null);

					if (item != null) {
						Optional<UsuarioImagem> imagem = item.stream().parallel()
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
			TableCell<Usuario, Set<Contato>> cell = new TableCell<Usuario, Set<Contato>>() {

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
		
		tbClDataCadastro.setCellFactory(column -> {
			TableCell<Usuario, Timestamp> cell = new TableCell<Usuario, Timestamp>() {
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

		tbUsuarios.setRowFactory(tv -> {
			TableRow<Usuario> row = new TableRow<>() {
				@Override
				public void updateItem(Usuario item, boolean empty) {
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
		tbUsuarios.getItems().filtered(usuario -> {
			if (nome == null || nome.isEmpty())
				return true;

			String lowerCaseFilter = nome.toLowerCase();
			if (usuario.getNomeSobrenome().toLowerCase().contains(lowerCaseFilter))
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
		return ListaUsuarioController.class.getResource("/cadastro/view/lista/ListaUsuario.fxml");
	}

}
