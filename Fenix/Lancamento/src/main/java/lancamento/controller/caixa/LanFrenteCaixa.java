package lancamento.controller.caixa;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class LanFrenteCaixa {

	@FXML
	private StackPane spRoot;

	@FXML
	private AnchorPane apContainer;

	/* ---------------------------- Carrinho ---------------------------- */
	@FXML
	private Label lblPedido;

	@FXML
	private Label lblDataHoraPedido;

	@FXML
	private JFXButton btnPesquisaCliente;

	@FXML
	private JFXTextField txtCpfCnpj;

	@FXML
	private JFXTextField txtCliente;

	@FXML
	private TableView<?> tbProdutos;

	@FXML
	private TableColumn<?, ?> tcCodigo;

	@FXML
	private TableColumn<?, ?> tcProduto;

	@FXML
	private TableColumn<?, ?> tcQuantidade;

	@FXML
	private TableColumn<?, ?> tcVlrUnitario;

	@FXML
	private TableColumn<?, ?> tcVlrTotal;

	@FXML
	private TableColumn<?, ?> tcAcoes;

	@FXML
	private Label lblVlrDesconto;

	@FXML
	private Label lblVlrMercadoria;

	@FXML
	private Label lblVlrTotal;

	/* ---------------------------- Produto ---------------------------- */
	@FXML
	private VBox vbContainerDireito;

	@FXML
	private VBox vbContainerProduto;

	@FXML
	private JFXButton btnPesquisaProduto;

	@FXML
	private JFXCheckBox cbCodBarras;

	@FXML
	private JFXTextField txtCodProduto;

	@FXML
	private Spinner<Double> spnQuantidade;

	@FXML
	private Spinner<Double> spnVlrUnitario;

	@FXML
	private Spinner<Double> spnPercDesconto;

	@FXML
	private Spinner<Double> spnVlrDesconto;

	@FXML
	private Spinner<Double> spnVlrTotal;

	@FXML
	private JFXButton btnAdicionarProduto;

	@FXML
	private JFXTextArea txaObservacao;

	/* ------------------------- Cabecalho / Rodape ------------------------- */
	@FXML
	private Label lblCaixa;

	@FXML
	private JFXButton btnConfiguracao;

	@FXML
	private JFXButton btnTelaCheia;

	@FXML
	private Label lblOperador;

	@FXML
	private JFXButton btnSangriaReforco;

	@FXML
	private JFXButton btnCancelar;

	@FXML
	private JFXButton btnPagamento;

}
