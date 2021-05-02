package comum.model.notification.controller;

import comum.model.notification.Notificacoes;
import comum.model.notification.Notificacoes.Menssagem;
import javafx.scene.control.ListCell;

/**
 * <p>
 * Classe criada para poder retornar a fabrica a construção do formulário de
 * notificação personalizado, onde ele será criado automáticamente atraves do
 * fxml e injetado seu controlador, que será quem fará a criação do fxml.
 * </p>
 * 
 * <p>
 * <b>Criação personalizada da celula através de um fxml</b>
 * </p>
 * 
 * @author Jhonny de Salles Noschang
 */
public class NotificacaoDetalheCell extends ListCell<Menssagem> {

	@Override
	public void updateItem(Menssagem msg, boolean empty) {
		super.updateItem(msg, empty);
		if (msg != null) {
			NotificacaoDetalheController data = new NotificacaoDetalheController();
			data.setTitulo(msg.getTitulo());
			data.setTexto(msg.getMenssagem());
			data.setTipo(msg.getTipo());
			data.setDataHora(msg.getHora());
			data.setOnCloseAction(evento -> Notificacoes.remListaMenssagem(msg));
			setGraphic(data.getRoot());
		} else
			setGraphic(null);
	}
}
