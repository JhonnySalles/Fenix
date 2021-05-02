package cadastro.controller.pesquisas;

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
import comum.model.enums.Enquadramento;
import comum.model.enums.Situacao;
import comum.model.enums.TipoPessoa;
import comum.model.exceptions.ExcessaoBd;
import comum.model.mask.ConverterMascaras;
import comum.model.mask.Mascaras;
import comum.model.utils.Utils;
import comum.model.utils.ViewGerenciador;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import servidor.entities.Cliente;
import servidor.entities.Contato;
import servidor.entities.Endereco;

public class PsqClienteController extends PesquisaFormPadrao {

	@FXML
	private JFXTextField txtIdInicial;

	@FXML
	private JFXTextField txtIdFinal;

	@FXML
	private JFXTextField txtNome;

	@FXML
	private JFXTextField txtCpf;

	@FXML
	private JFXTextField txtCnpj;

	@FXML
	private JFXDatePicker dtPkCadastroInicial;

	@FXML
	private JFXDatePicker dtPkCadastroFinal;

	@FXML
	private JFXComboBox<Situacao> cbSituacao;

	@FXML
	private JFXComboBox<Enquadramento> cbEnquadramento;

	@FXML
	private JFXComboBox<TipoPessoa> cbPessoaTipo;

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
	private TableColumn<Cliente, String> tbClDataCadastro;

	@FXML
	private TableColumn<Cliente, String> tbClContatoPadrao;

	@FXML
	private TableColumn<Cliente, String> tbClEnderecoPadrao;

	private List<Cliente> clientes;
	private ObservableList<Cliente> obsClientes;
	private FilteredList<Cliente> filteredData;

	@FXML
	public void onConfirmarKeyPress(KeyEvent e) {
		if (e.getCode().equals(KeyCode.TAB))
			btnConfirmar.fire();
	}

	@FXML
	public void onBtnConfirmarClick() {

	}

	@FXML
	public void onCancelarKeyPress(KeyEvent e) {
		if (e.getCode().equals(KeyCode.TAB))
			btnCancelar.fire();
	}

	@FXML
	public void onBtnCancelarClick() {

	}

	@FXML
	public void onAtualizarKeyPress(KeyEvent e) {
		if (e.getCode().equals(KeyCode.TAB))
			btnAtualizar.fire();
	}

	@FXML
	public void onBtnAtualizarClick() {
		try {
			spRoot.cursorProperty().set(Cursor.WAIT);
			carregarClientes();
		} catch (ExcessaoBd e) {
			e.printStackTrace();
		} finally {
			spRoot.cursorProperty().set(null);
		}

	}

	@FXML
	public void onVoltarKeyPress(KeyEvent e) {
		if (e.getCode().equals(KeyCode.TAB))
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
		txtCpf.setText("");
		txtCnpj.setText("");
		dtPkCadastroInicial.setValue(null);
		dtPkCadastroFinal.setValue(null);
		cbPessoaTipo.getSelectionModel().clearSelection();
		cbEnquadramento.getSelectionModel().clearSelection();
		cbSituacao.getSelectionModel().clearSelection();
		if (filteredData != null)
			filteredData.setPredicate(null);
	}

	public PsqClienteController carregarClientes(List<Cliente> clientes) {
		this.clientes = clientes;
		return this;
	}

	public PsqClienteController carregarClientes() throws ExcessaoBd {
		obsClientes = FXCollections.observableArrayList(this.clientes);
		tbClientes.setItems(obsClientes);
		tbClientes.refresh();
		configuraGrid();
		return this;
	}

	private Boolean validaPredicate(Cliente obj) {
		if ((txtIdInicial.getText().isEmpty() || obj.getId() >= Long.valueOf(txtIdInicial.getText()))
				&& (txtIdFinal.getText().isEmpty() || obj.getId() <= Long.valueOf(txtIdFinal.getText()))
				&& (txtNome.getText().isEmpty()
						|| obj.getNomeSobrenome().toString().toLowerCase().contains(txtNome.getText().toLowerCase()))
				&& (txtCnpj.getText().isEmpty() || obj.getCnpj().contains(Utils.removeMascaras(txtCnpj.getText())))
				&& (txtCpf.getText().isEmpty() || obj.getCpf().contains(Utils.removeMascaras(txtCpf.getText())))

				&& (dtPkCadastroInicial.getValue() == null || obj.getDataCadastro()
						.after(Timestamp.valueOf(dtPkCadastroInicial.getValue().atStartOfDay())))
				&& (dtPkCadastroFinal.getValue() == null
						|| obj.getDataCadastro().after(Timestamp.valueOf(dtPkCadastroFinal.getValue().atStartOfDay())))

				&& (cbPessoaTipo.getSelectionModel().getSelectedIndex() < 0
						|| obj.getTipoPessoa() == cbPessoaTipo.getSelectionModel().getSelectedItem())
				&& (cbEnquadramento.getSelectionModel().getSelectedIndex() < 0
						|| obj.getEnquadramento() == cbEnquadramento.getSelectionModel().getSelectedItem())
				&& (cbSituacao.getSelectionModel().getSelectedIndex() < 0
						|| obj.getSituacao() == cbSituacao.getSelectionModel().getSelectedItem()))
			return true;
		else
			return false;

	}

	private PsqClienteController configuraGrid() {
		filteredData = new FilteredList<>(obsClientes, p -> true);

		txtIdInicial.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(person -> validaPredicate(person));
		});

		txtIdFinal.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(person -> validaPredicate(person));
		});

		txtNome.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(person -> validaPredicate(person));
		});

		txtCnpj.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(person -> validaPredicate(person));
		});

		txtCpf.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(person -> validaPredicate(person));
		});

		dtPkCadastroInicial.valueProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(person -> validaPredicate(person));
		});

		dtPkCadastroFinal.valueProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(person -> validaPredicate(person));
		});

		cbPessoaTipo.setOnAction(filtrar -> {
			filteredData.setPredicate(person -> validaPredicate(person));
		});

		cbEnquadramento.setOnAction(filtrar -> {
			filteredData.setPredicate(person -> validaPredicate(person));
		});

		cbSituacao.setOnAction(filtrar -> {
			filteredData.setPredicate(person -> validaPredicate(person));
		});

		SortedList<Cliente> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(tbClientes.comparatorProperty());
		tbClientes.setItems(sortedData);

		return this;
	}

	/*
	 * private PsqClienteController configuraGrid() { FilteredList<Cliente>
	 * filteredData = new FilteredList<>(obsClientes, p -> true);
	 * 
	 * txtIdInicial.textProperty().addListener((observable, oldValue, newValue) -> {
	 * filteredData.setPredicate(person -> { if (newValue == null ||
	 * newValue.isEmpty()) { return true; }
	 * 
	 * if (person.getId() >= Long.valueOf(newValue)) return true; else return false;
	 * }); });
	 * 
	 * txtIdFinal.textProperty().addListener((observable, oldValue, newValue) -> {
	 * filteredData.setPredicate(person -> { if (newValue == null ||
	 * newValue.isEmpty()) { return true; }
	 * 
	 * if (person.getId() <= Long.valueOf(newValue)) return true; else return false;
	 * }); });
	 * 
	 * txtNome.textProperty().addListener((observable, oldValue, newValue) -> {
	 * filteredData.setPredicate(person -> { if (newValue == null ||
	 * newValue.isEmpty()) { return true; }
	 * 
	 * if (person.getNomeSobrenome().toString().toLowerCase().contains(newValue.
	 * toLowerCase())) return true; else return false; }); });
	 * 
	 * txtCnpj.textProperty().addListener((observable, oldValue, newValue) -> {
	 * filteredData.setPredicate(person -> { if (newValue == null ||
	 * newValue.isEmpty()) { return true; }
	 * 
	 * if (person.getCnpj().toString().toLowerCase().contains(Utils.removeMascaras(
	 * newValue.toLowerCase()))) return true; else return false; }); });
	 * 
	 * txtCpf.textProperty().addListener((observable, oldValue, newValue) -> {
	 * filteredData.setPredicate(person -> { if (newValue == null ||
	 * newValue.isEmpty()) { return true; }
	 * 
	 * if (person.getCpf().toString().toLowerCase().contains(Utils.removeMascaras(
	 * newValue.toLowerCase()))) return true; else return false; }); });
	 * 
	 * dtPkCadastroInicial.valueProperty().addListener((observable, oldValue,
	 * newValue) -> { filteredData.setPredicate(person -> { if (newValue == null) {
	 * return true; }
	 * 
	 * Timestamp timestamp = Timestamp.valueOf(newValue.atStartOfDay()); if
	 * (person.getDataCadastro().after(timestamp)) return true; else return false;
	 * });
	 * 
	 * });
	 * 
	 * dtPkCadastroFinal.valueProperty().addListener((observable, oldValue,
	 * newValue) -> { filteredData.setPredicate(person -> { if (newValue == null) {
	 * return true; }
	 * 
	 * Timestamp timestamp = Timestamp.valueOf(newValue.atStartOfDay()); if
	 * (person.getDataCadastro().before(timestamp)) { return true; } else return
	 * false; });
	 * 
	 * });
	 * 
	 * cbPessoaTipo.setOnAction(filtrar -> { filteredData.setPredicate(person -> {
	 * if (cbPessoaTipo.getSelectionModel().getSelectedIndex() > 0) { if
	 * (person.getSituacao().equals(cbPessoaTipo.getSelectionModel().getSelectedItem
	 * ())) return true; else return false; } else return true; });
	 * 
	 * });
	 * 
	 * cbClienteTipo.setOnAction(filtrar -> { filteredData.setPredicate(person -> {
	 * if (cbClienteTipo.getSelectionModel().getSelectedIndex() > 0) { if
	 * (person.getSituacao().equals(cbClienteTipo.getSelectionModel().
	 * getSelectedItem())) return true; else return false; } else return true; });
	 * 
	 * });
	 * 
	 * cbSituacao.setOnAction(filtrar -> { filteredData.setPredicate(person -> { if
	 * (cbSituacao.getSelectionModel().getSelectedIndex() > 0) { if
	 * (person.getSituacao().equals(cbSituacao.getSelectionModel().getSelectedItem()
	 * )) return true; else return false; } else return true; });
	 * 
	 * });
	 * 
	 * SortedList<Cliente> sortedData = new SortedList<>(filteredData);
	 * sortedData.comparatorProperty().bind(tbClientes.comparatorProperty());
	 * tbClientes.setItems(sortedData);
	 * 
	 * return this; }
	 */

	private PsqClienteController linkaCelulas() {
		tbClId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tbClNome.setCellValueFactory(new PropertyValueFactory<>("nomeSobrenome"));

		tbClCpf.setCellValueFactory(data -> {
			return new SimpleStringProperty(ConverterMascaras.formataCPF(data.getValue().getCpf()));
		});

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
				contato.setValue(ConverterMascaras.formataFone(psq.get(0).getTelefone()) + " / "
						+ ConverterMascaras.formataFone(psq.get(0).getCelular()));
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
	public synchronized void inicializa(URL arg0, ResourceBundle arg1) {
		// setClienteServices(new ClienteServices());

		Limitadores.setTextFieldInteger(txtIdInicial);
		Limitadores.setTextFieldInteger(txtIdFinal);

		Mascaras.cpfField(txtCpf);
		Mascaras.cnpjField(txtCnpj);

		TecladoUtils.onEnterConfigureTab(txtCpf);
		TecladoUtils.onEnterConfigureTab(txtCnpj);
		TecladoUtils.onEnterConfigureTab(cbSituacao);
		TecladoUtils.onEnterConfigureTab(cbEnquadramento);
		TecladoUtils.onEnterConfigureTab(cbPessoaTipo);

		linkaCelulas();

		cbSituacao.getItems().addAll(Situacao.values());
		cbPessoaTipo.getItems().addAll(TipoPessoa.values());
		cbEnquadramento.getItems().addAll(Enquadramento.values());
	}

	public static URL getFxmlLocate() {
		return PsqClienteController.class.getResource("/cadastro/view/pesquisas/PsqCliente.fxml");
	}
}
