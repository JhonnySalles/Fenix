package administrador.controller.metricas;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class DashBoardGraficosBase implements Initializable {

	@FXML
	private StackPane spRootBase;
	
	@FXML
	private Label lblCompras;
	
	@FXML
	private Label lblVendas;
	
	@FXML
	private Label lblTotal;

	
	public void setCompras(Double valor) {
		DecimalFormat decFormat = new DecimalFormat("'R$ ' 0.##");
		lblCompras.setText(decFormat.format(valor));
	}
	
	public void setVendas(Double valor) {
		DecimalFormat decFormat = new DecimalFormat("'R$ ' 0.##");
		lblCompras.setText(decFormat.format(valor));
	}
	
	public void setTotal(Double valor) {
		DecimalFormat decFormat = new DecimalFormat("'R$ ' 0.##");
		lblCompras.setText(decFormat.format(valor));
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {


	}
}
