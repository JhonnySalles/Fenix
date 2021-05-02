package cadastro.controller.pesquisas;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import comum.form.PesquisaFormPadrao;
import comum.model.constraints.Limitadores;
import comum.model.constraints.TecladoUtils;
import comum.model.enums.Situacao;
import comum.model.enums.UsuarioNivel;
import comum.model.exceptions.ExcessaoBd;
import comum.model.mask.ConverterMascaras;
import comum.model.utils.ViewGerenciador;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;
import servidor.entities.Contato;
import servidor.entities.Imagem;
import servidor.entities.Usuario;

public class PsqUsuarioController extends PesquisaFormPadrao {

	@FXML
	private JFXTextField txtIdInicial;

	@FXML
	private JFXTextField txtIdFinal;

	@FXML
	private JFXTextField txtNome;

	@FXML
	private JFXTextField txtLogin;

	@FXML
	private JFXDatePicker dtPkCadastroInicial;

	@FXML
	private JFXDatePicker dtPkCadastroFinal;

	@FXML
	private JFXComboBox<Situacao> cbSituacao;

	@FXML
	private JFXComboBox<UsuarioNivel> cbNivel;

	@FXML
	private TableView<Usuario> tbUsuarios;

	@FXML
	private TableColumn<Usuario, String> tbClId;

	@FXML
	private TableColumn<Usuario, List<Imagem>> tbClImagem;

	@FXML
	private TableColumn<Usuario, String> tbClNome;

	@FXML
	private TableColumn<Usuario, String> tbClLogin;

	@FXML
	private TableColumn<Usuario, String> tbClContatoPadrao;

	@FXML
	private TableColumn<Usuario, String> tbClObservacao;

	@FXML
	private TableColumn<Usuario, UsuarioNivel> tbClNivel;

	@FXML
	private TableColumn<Usuario, String> tbClDataCadastro;

	private List<Usuario> usuarios;
	private ObservableList<Usuario> obsUsuarios;
	private FilteredList<Usuario> filteredData;

	@FXML
	public void onConfirmarKeyPress(KeyEvent e) {
		if (e.getCode().equals(KeyCode.ENTER))
			btnConfirmar.fire();
	}

	@FXML
	public void onBtnConfirmarClick() {

	}

	@FXML
	public void onCancelarKeyPress(KeyEvent e) {
		if (e.getCode().equals(KeyCode.ENTER))
			btnCancelar.fire();
	}

	@FXML
	public void onBtnCancelarClick() {

	}

	@FXML
	public void onAtualizarKeyPress(KeyEvent e) {
		if (e.getCode().equals(KeyCode.ENTER))
			btnAtualizar.fire();
	}

	@FXML
	public void onBtnAtualizarClick() {
		try {
			spRoot.cursorProperty().set(Cursor.WAIT);
			carregarUsuarios();
		} catch (ExcessaoBd e) {
			e.printStackTrace();
		} finally {
			spRoot.cursorProperty().set(null);
		}

	}

	@FXML
	public void onVoltarKeyPress(KeyEvent e) {
		if (e.getCode().equals(KeyCode.ENTER))
			btnVoltar.fire();
	}

	@FXML
	public void onBtnVoltarClick() {
		ViewGerenciador.closeTela(spRoot);
		onClose();
	}

	@FXML
	public void onBtnLimparClick() {
		txtIdInicial.setText("0");
		txtIdFinal.setText("0");
		txtNome.setText("");
		txtLogin.setText("");
		dtPkCadastroInicial.setValue(null);
		dtPkCadastroFinal.setValue(null);
		cbNivel.getSelectionModel().clearSelection();
		cbSituacao.getSelectionModel().clearSelection();
		if (filteredData != null)
			filteredData.setPredicate(null);
	}

	public PsqUsuarioController carregarUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
		return this;
	}

	public PsqUsuarioController carregarUsuarios() throws ExcessaoBd {
		// this.usuarios = usuarioService.pesquisarTodos(TamanhoImagem.PEQUENA);
		obsUsuarios = FXCollections.observableArrayList(this.usuarios);
		tbUsuarios.setItems(obsUsuarios);
		tbUsuarios.refresh();
		configuraGrid();
		return this;
	}

	private Boolean validaPredicate(Usuario obj) {
		if ((txtIdInicial.getText().isEmpty() || obj.getId() >= Long.valueOf(txtIdInicial.getText()))
				&& (txtIdFinal.getText().isEmpty() || obj.getId() <= Long.valueOf(txtIdFinal.getText()))
				&& (txtNome.getText().isEmpty()
						|| obj.getNomeSobrenome().toString().toLowerCase().contains(txtNome.getText().toLowerCase()))
				&& (txtLogin.getText().isEmpty() || obj.getLogin().contains(txtLogin.getText()))

				&& (dtPkCadastroInicial.getValue() == null || obj.getDataCadastro()
						.after(Timestamp.valueOf(dtPkCadastroInicial.getValue().atStartOfDay())))
				&& (dtPkCadastroFinal.getValue() == null
						|| obj.getDataCadastro().after(Timestamp.valueOf(dtPkCadastroFinal.getValue().atStartOfDay())))

				&& (cbNivel.getSelectionModel().getSelectedIndex() < 0
						|| obj.getNivel() == cbNivel.getSelectionModel().getSelectedItem())
				&& (cbSituacao.getSelectionModel().getSelectedIndex() < 0
						|| obj.getSituacao() == cbSituacao.getSelectionModel().getSelectedItem()))
			return true;
		else
			return false;

	}

	private PsqUsuarioController configuraGrid() {
		filteredData = new FilteredList<>(obsUsuarios, p -> true);

		txtIdInicial.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(person -> validaPredicate(person));
		});

		txtIdFinal.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(person -> validaPredicate(person));
		});

		txtNome.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(person -> validaPredicate(person));
		});

		txtLogin.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(person -> validaPredicate(person));
		});

		dtPkCadastroInicial.valueProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(person -> validaPredicate(person));
		});

		dtPkCadastroFinal.valueProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(person -> validaPredicate(person));
		});

		cbNivel.setOnAction(filtrar -> {
			filteredData.setPredicate(person -> validaPredicate(person));
		});

		cbSituacao.setOnAction(filtrar -> {
			filteredData.setPredicate(person -> validaPredicate(person));
		});

		SortedList<Usuario> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(tbUsuarios.comparatorProperty());
		tbUsuarios.setItems(sortedData);

		return this;
	}

	private PsqUsuarioController linkaCelulas() {
		tbClId.setCellValueFactory(new PropertyValueFactory<>("id"));

		tbClImagem.setCellValueFactory(new PropertyValueFactory<>("imagens"));
		tbClImagem.setCellFactory(new Callback<TableColumn<Usuario, List<Imagem>>, TableCell<Usuario, List<Imagem>>>() {
			@Override
			public TableCell<Usuario, List<Imagem>> call(TableColumn<Usuario, List<Imagem>> param) {
				TableCell<Usuario, List<Imagem>> cell = new TableCell<Usuario, List<Imagem>>() {
					final ImageView img = new ImageView();

					@Override
					public void updateItem(List<Imagem> item, boolean empty) {
						if (item != null && item.size() > 0) {
							img.setImage(new Image(new ByteArrayInputStream(item.get(0).getImagem())));
							img.setFitHeight(25);
							img.setFitWidth(25);
							setGraphic(img);
						}
					}
				};
				return cell;
			}
		});

		tbClImagem.setPrefWidth(60);

		tbClNome.setCellValueFactory(new PropertyValueFactory<>("nomeSobrenome"));
		tbClLogin.setCellValueFactory(new PropertyValueFactory<>("login"));

		tbClContatoPadrao.setCellValueFactory(data -> {
			List<Contato> psq = data.getValue().getContatos().stream().filter(item -> item.isPadrao())
					.collect(Collectors.toList());
			SimpleStringProperty contato = new SimpleStringProperty();
			if (psq != null && psq.size() > 0)
				contato.setValue(ConverterMascaras.formataFone(psq.get(0).getTelefone()) + " / "
						+ ConverterMascaras.formataFone(psq.get(0).getCelular()));
			else
				contato.setValue("");

			return contato;
		});

		tbClObservacao.setCellValueFactory(new PropertyValueFactory<>("observacao"));
		tbClNivel.setCellValueFactory(new PropertyValueFactory<>("nivel"));

		tbClDataCadastro.setCellValueFactory(data -> {
			SimpleStringProperty property = new SimpleStringProperty();
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			property.setValue(dateFormat.format(data.getValue().getDataCadastro()));
			return property;
		});

		return this;
	}

	@Override
	public synchronized void inicializa(URL arg0, ResourceBundle arg1) {
		// setUsuarioServices(new UsuarioServices());

		Limitadores.setTextFieldInteger(txtIdInicial);
		Limitadores.setTextFieldInteger(txtIdFinal);

		TecladoUtils.onEnterConfigureTab(txtNome);
		TecladoUtils.onEnterConfigureTab(txtLogin);
		TecladoUtils.onEnterConfigureTab(cbSituacao);
		TecladoUtils.onEnterConfigureTab(cbNivel);

		linkaCelulas();

		cbNivel.getItems().addAll(UsuarioNivel.values());
		cbSituacao.getItems().addAll(Situacao.values());
	}

	public static URL getFxmlLocate() {
		return PsqUsuarioController.class.getResource("/cadastro/view/pesquisas/PsqUsuario.fxml");
	}
}
