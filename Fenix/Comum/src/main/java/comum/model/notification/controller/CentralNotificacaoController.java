package comum.model.notification.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import comum.model.notification.Notificacoes;
import comum.model.notification.Notificacoes.Menssagem;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

public class CentralNotificacaoController implements Initializable {

	@FXML
	private AnchorPane apBackground;

	@FXML
	private ListView<Menssagem> lvLista;

	@FXML
	private JFXButton btnOrdenarHora;

	@FXML
	private JFXButton btnOrdenarTipo;

	@FXML
	private JFXButton btnLimpar;

	@FXML
	public void onBtnOrdenaHoraClick() {
		if (btnOrdenarHora.accessibleTextProperty().getValue().equalsIgnoreCase("ASC")) {
			btnOrdenarHora.accessibleTextProperty().set("DESC");
			lvLista.getItems().sort((o1, o2) -> o1.getHora().compareTo(o2.getHora()) * -1);
		} else {
			btnOrdenarHora.accessibleTextProperty().set("ASC");
			lvLista.getItems().sort((o1, o2) -> o1.getHora().compareTo(o2.getHora()));
		}

	}

	@FXML
	public void onBtnOrdenaTipoClick() {
		lvLista.getItems().sort((o1, o2) -> o1.getTipo().compareTo(o2.getTipo()));
	}

	@FXML
	public void onBtnLimpaClick() {
		Notificacoes.limpaNotificacoes();
	}

	// Cria a forma de exibição das menssagens
	private void configureListView() {
		lvLista.setCellFactory(new Callback<ListView<Menssagem>, ListCell<Menssagem>>() {
			@Override
			public ListCell<Menssagem> call(ListView<Menssagem> listView) {
				return new NotificacaoDetalheCell();
			}
		});

		lvLista.setItems(Notificacoes.getListaMenssagens());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		configureListView();
	}

	public static URL getFxmlLocate() {
		return CentralNotificacaoController.class.getResource("/comum/view/notification/CentralNotificacao.fxml");
	}

}
