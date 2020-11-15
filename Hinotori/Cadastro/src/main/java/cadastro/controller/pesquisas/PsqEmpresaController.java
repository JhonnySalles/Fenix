package cadastro.controller.pesquisas;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import comum.form.DashboardFormPadrao;
import comum.form.PesquisaFormPadrao;
import comum.model.constraints.Limitadores;
import comum.model.constraints.TecladoUtils;
import comum.model.enums.Situacao;
import comum.model.enums.TamanhoImagem;
import comum.model.exceptions.ExcessaoBd;
import comum.model.mask.ConverterMascaras;
import comum.model.mask.Mascaras;
import comum.model.utils.Utils;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;
import servidor.dao.services.EmpresaServices;
import servidor.entities.Contato;
import servidor.entities.Empresa;
import servidor.entities.Endereco;
import servidor.entities.Imagem;

public class PsqEmpresaController extends PesquisaFormPadrao implements Initializable {

	@FXML
	private JFXTextField txtIdInicial;

	@FXML
	private JFXTextField txtIdFinal;

	@FXML
	private JFXTextField txtRazaoSocial;

	@FXML
	private JFXTextField txtNomeFantasia;

	@FXML
	private JFXTextField txtCnpj;

	@FXML
	private JFXDatePicker dtPkCadastroInicial;

	@FXML
	private JFXDatePicker dtPkCadastroFinal;

	@FXML
	private JFXComboBox<Situacao> cbSituacao;

	@FXML
	private TableView<Empresa> tbEmpresas;

	@FXML
	private TableColumn<Empresa, String> tbClId;

	@FXML
	private TableColumn<Empresa, List<Imagem>> tbClLogo;

	@FXML
	private TableColumn<Empresa, String> tbClRazaoSocial;

	@FXML
	private TableColumn<Empresa, String> tbClNomeFantasia;

	@FXML
	private TableColumn<Empresa, String> tbClCnpj;

	@FXML
	private TableColumn<Empresa, String> tbClDataCadastro;

	@FXML
	private TableColumn<Empresa, String> tbClContatoPadrao;
	
	@FXML
	private TableColumn<Empresa, String> tbClEnderecoPadrao;

	private List<Empresa> empresas;
	private ObservableList<Empresa> obsEmpresas;
	private FilteredList<Empresa> filteredData;
	private EmpresaServices empresaService;

	@FXML
	public void onBtnConfirmarEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {
			btnConfirmar.fire();
		}
	}

	@FXML
	public void onBtnConfirmarClick() {

	}

	@FXML
	public void onBtnCancelarEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {
			btnCancelar.fire();
		}
	}

	@FXML
	public void onBtnCancelarClick() {

	}

	@FXML
	public void onBtnAtualizarEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {
			btnAtualizar.fire();
		}
	}

	@FXML
	public void onBtnAtualizarClick() {
		try {
			spRoot.cursorProperty().set(Cursor.WAIT);
			carregarEmpresas();
			// Necessário por um bug na tela ao carregar ela.
			//dashBoard.atualizaTabPane();
		} catch (ExcessaoBd e) {
			e.printStackTrace();
		} finally {
			spRoot.cursorProperty().set(null);
		}

	}

	@FXML
	public void onBtnVoltarEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {
			btnVoltar.fire();
		}
	}

	@FXML
	public void onBtnVoltarClick() {

	}

	@FXML
	public void onBtnLimparClick() {
		txtIdInicial.setText("0");
		txtIdFinal.setText("0");
		txtRazaoSocial.setText("");
		txtNomeFantasia.setText("");
		txtCnpj.setText("");
		dtPkCadastroInicial.setValue(null);
		dtPkCadastroFinal.setValue(null);
		cbSituacao.getSelectionModel().clearSelection();
		if (filteredData != null)
			filteredData.setPredicate(null);
	}

	public PsqEmpresaController carregarEmpresas(List<Empresa> empresas) {
		this.empresas = empresas;
		return this;
	}

	public PsqEmpresaController carregarEmpresas() throws ExcessaoBd {
		this.empresas = empresaService.pesquisarTodos(TamanhoImagem.PEQUENA);
		obsEmpresas = FXCollections.observableArrayList(this.empresas);
		tbEmpresas.setItems(obsEmpresas);
		tbEmpresas.refresh();
		configuraGrid();
		return this;
	}

	private Boolean validaPredicate(Empresa obj) {
		if ((txtIdInicial.getText().isEmpty() || obj.getId() >= Long.valueOf(txtIdInicial.getText()))
				&& (txtIdFinal.getText().isEmpty() || obj.getId() <= Long.valueOf(txtIdFinal.getText()))
				&& (txtRazaoSocial.getText().isEmpty() || obj.getRazaoSocial().toString().toLowerCase()
						.contains(txtRazaoSocial.getText().toLowerCase()))
				&& (txtNomeFantasia.getText().isEmpty() || obj.getNomeFantasia().contains(txtNomeFantasia.getText()))
				&& (txtCnpj.getText().isEmpty() || obj.getCnpj().contains(Utils.removeMascaras(txtCnpj.getText())))

				&& (dtPkCadastroInicial.getValue() == null || obj.getDataCadastro()
						.after(Timestamp.valueOf(dtPkCadastroInicial.getValue().atStartOfDay())))
				&& (dtPkCadastroFinal.getValue() == null
						|| obj.getDataCadastro().after(Timestamp.valueOf(dtPkCadastroFinal.getValue().atStartOfDay())))

				&& (cbSituacao.getSelectionModel().getSelectedIndex() < 0
						|| obj.getSituacao() == cbSituacao.getSelectionModel().getSelectedItem()))
			return true;
		else
			return false;

	}

	private PsqEmpresaController configuraGrid() {
		filteredData = new FilteredList<>(obsEmpresas, p -> true);

		txtIdInicial.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(person -> validaPredicate(person));
		});

		txtIdFinal.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(person -> validaPredicate(person));
		});

		txtRazaoSocial.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(person -> validaPredicate(person));
		});

		txtNomeFantasia.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(person -> validaPredicate(person));
		});

		txtCnpj.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(person -> validaPredicate(person));
		});

		dtPkCadastroInicial.valueProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(person -> validaPredicate(person));
		});

		dtPkCadastroFinal.valueProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(person -> validaPredicate(person));
		});

		cbSituacao.setOnAction(filtrar -> {
			filteredData.setPredicate(person -> validaPredicate(person));
		});

		SortedList<Empresa> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(tbEmpresas.comparatorProperty());
		tbEmpresas.setItems(sortedData);

		return this;
	}

	private PsqEmpresaController setEmpresaServices(EmpresaServices empresaService) {
		this.empresaService = empresaService;
		return this;
	}

	private PsqEmpresaController linkaCelulas() {
		tbClId.setCellValueFactory(new PropertyValueFactory<>("id"));

		tbClLogo.setCellValueFactory(new PropertyValueFactory<>("imagens"));
		tbClLogo.setCellFactory(new Callback<TableColumn<Empresa, List<Imagem>>, TableCell<Empresa, List<Imagem>>>() {
			@Override
			public TableCell<Empresa, List<Imagem>> call(TableColumn<Empresa, List<Imagem>> param) {
				TableCell<Empresa, List<Imagem>> cell = new TableCell<Empresa, List<Imagem>>() {
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
		tbClLogo.setPrefWidth(60);

		tbClRazaoSocial.setCellValueFactory(new PropertyValueFactory<>("nomeFantasia"));
		tbClNomeFantasia.setCellValueFactory(new PropertyValueFactory<>("razaoSocial"));

		tbClCnpj.setCellValueFactory(data -> {
			return new SimpleStringProperty(ConverterMascaras.formataCNPJ(data.getValue().getCnpj()));
		});

		tbClDataCadastro.setCellValueFactory(data -> {
			SimpleStringProperty property = new SimpleStringProperty();
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			property.setValue(dateFormat.format(data.getValue().getDataCadastro()));
			return property;
		});
		
		tbClContatoPadrao.setCellValueFactory(data -> {
			List<Contato> psq = data.getValue().getContatos().stream().filter(item -> item.isPadrao())
					.collect(Collectors.toList());
			SimpleStringProperty contato = new SimpleStringProperty();
			if (psq != null && psq.size() > 0)
				contato.setValue(ConverterMascaras.formataFone(psq.get(0).getTelefone()) + " / " + ConverterMascaras.formataFone(psq.get(0).getCelular()));
			else
				contato.setValue("");
			
			return contato;
		});

		tbClEnderecoPadrao.setCellValueFactory(data -> {
			List<Endereco> psq = data.getValue().getEnderecos().stream().filter(item -> item.isPadrao())
					.collect(Collectors.toList());
			SimpleStringProperty endereco = new SimpleStringProperty();
			if (psq != null && psq.size() > 0)
				endereco.setValue(psq.get(0).getEndereco() + ", " + psq.get(0).getNumero());
			else
				endereco.setValue("");

			return endereco;
		});

		return this;
	}

	@Override
	public synchronized void initialize(URL arg0, ResourceBundle arg1) {
		inicializaHeranca();
		setEmpresaServices(new EmpresaServices());

		Limitadores.setTextFieldInteger(txtIdInicial);
		Limitadores.setTextFieldInteger(txtIdFinal);

		Mascaras.cnpjField(txtCnpj);

		TecladoUtils.onEnterConfigureTab(txtRazaoSocial);
		TecladoUtils.onEnterConfigureTab(txtNomeFantasia);
		TecladoUtils.onEnterConfigureTab(txtCnpj);
		TecladoUtils.onEnterConfigureTab(cbSituacao);

		linkaCelulas();

		cbSituacao.getItems().addAll(Situacao.values());
	}
	
	public static URL getFxmlLocate() {
		return PsqEmpresaController.class.getResource("/cadastro/view/pesquisas/PsqEmpresa.fxml");
	}
}
