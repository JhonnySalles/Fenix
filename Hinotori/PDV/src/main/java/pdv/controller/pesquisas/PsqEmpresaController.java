package pdv.controller.pesquisas;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Transform;
import javafx.util.Callback;
import pdv.App;
import servidor.dao.services.EmpresaServices;
import servidor.entities.Contato;
import servidor.entities.Empresa;
import servidor.entities.Endereco;
import servidor.entities.Imagem;

public class PsqEmpresaController implements Initializable {

	private Map<KeyCodeCombination, Runnable> atalhosTecla = new HashMap<>();

	@FXML
	private StackPane spRoot;

	@FXML
	private ScrollPane background;

	@FXML
	private HBox titulo;

	@FXML
	private HBox fundoBotoes;

	@FXML
	private AnchorPane rootEmpresa;

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

	@FXML
	private JFXButton btnAtualizar;

	@FXML
	private JFXButton btnConfirmar;

	@FXML
	private JFXButton btnCancelar;

	@FXML
	private JFXButton btnVoltar;

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
			App.getMainController().atualizaTabPane();
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
		App.getMainController().atualizaTabPane();
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
		// Necessário por um bug na tela ao carregar ela.
		App.getMainController().atualizaTabPane();
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

	// Será necessário verificar uma forma de configurar o scene após a exibição,
	// pois é ele que adiciona os atalhos do teclado, porém na construção a scene
	// não existe, somente na exibição.
	public void ativaAtalhos() {
		rootEmpresa.getScene().getAccelerators().clear();
		rootEmpresa.getScene().getAccelerators().putAll(atalhosTecla);
	}

	private PsqEmpresaController configuraAtalhosTeclado() {
		atalhosTecla.put(new KeyCodeCombination(KeyCode.F2), new Runnable() {
			@FXML
			public void run() {
				btnConfirmar.fire();
			}
		});
		atalhosTecla.put(new KeyCodeCombination(KeyCode.F3), new Runnable() {
			@FXML
			public void run() {
				btnCancelar.fire();
			}
		});
		atalhosTecla.put(new KeyCodeCombination(KeyCode.BACK_SPACE), new Runnable() {
			@FXML
			public void run() {
				btnVoltar.fire();
			}
		});
		atalhosTecla.put(new KeyCodeCombination(KeyCode.BACK_SPACE), new Runnable() {
			@FXML
			public void run() {
				btnVoltar.fire();
			}
		});
		atalhosTecla.put(new KeyCodeCombination(KeyCode.F5), new Runnable() {
			@FXML
			public void run() {
				btnAtualizar.fire();
			}
		});
		return this;
	}

	private PsqEmpresaController setEmpresaServices(EmpresaServices empresaService) {
		this.empresaService = empresaService;
		return this;
	}

	// Função responsável pela transição de opacidade do fundo do cadastro e dos
	// botões.
	private double initY = -1;
	private final Scale scale = new Scale(1, 1, 0, 0);
	private Transform oldSceneTransform = null;

	private PsqEmpresaController configureScroll() {

		fundoBotoes.localToSceneTransformProperty().addListener((o, oldVal, newVal) -> oldSceneTransform = oldVal);
		background.vvalueProperty().addListener((o, oldVal, newVal) -> {
			if (initY == -1) {
				initY = oldSceneTransform.getTy();
			}

			// translation
			double ty = rootEmpresa.getLocalToSceneTransform().getTy();
			double opacity = Math.abs(ty - initY) / 100;
			opacity = opacity > 1 ? 1 : (opacity < 0) ? 0 : opacity;

			titulo.setOpacity(1 - opacity);
			fundoBotoes.setOpacity(opacity);

			// scale
			scale.setX(map(opacity, 0, 1, 1, 0.75));
			scale.setY(map(opacity, 0, 1, 1, 0.75));
		});
		return this;
	}

	private double map(double val, double min1, double max1, double min2, double max2) {
		return min2 + (max2 - min2) * ((val - min1) / (max1 - min1));
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
		setEmpresaServices(new EmpresaServices());

		Limitadores.setTextFieldInteger(txtIdInicial);
		Limitadores.setTextFieldInteger(txtIdFinal);

		Mascaras.cnpjField(txtCnpj);

		TecladoUtils.onEnterConfigureTab(txtRazaoSocial);
		TecladoUtils.onEnterConfigureTab(txtNomeFantasia);
		TecladoUtils.onEnterConfigureTab(txtCnpj);
		TecladoUtils.onEnterConfigureTab(cbSituacao);

		configuraAtalhosTeclado().configureScroll();
		linkaCelulas();

		cbSituacao.getItems().addAll(Situacao.values());
	}
}
