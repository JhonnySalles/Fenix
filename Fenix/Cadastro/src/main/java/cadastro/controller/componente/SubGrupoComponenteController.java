package cadastro.controller.componente;

import com.jfoenix.controls.JFXButton;

import comum.model.utils.Utils;
import javafx.fxml.FXML;
import servidor.entities.GrupoBase;
import servidor.entities.SubGrupo;

public class SubGrupoComponenteController {

	@FXML
	private JFXButton root = new JFXButton();

	private Long idGrupo;
	private SubGrupo subGrupo;

	public JFXButton getRoot() {
		return root;
	}

	public void createSubGrupo(GrupoBase subGrupo) {
		root.setText(subGrupo.getDescricao());

		if (!subGrupo.getCor().isEmpty()) {
			root.setStyle("-fx-background-color: " + subGrupo.getCor());
			root.setTextFill(Utils.getContrastColor(subGrupo.getCor()));
		}
	}

	public Long getIdGrupo() {
		return idGrupo;
	}

	public GrupoBase getSubGrupo() {
		return subGrupo;
	}

	public SubGrupoComponenteController(SubGrupo subGrupo) {
		this.idGrupo = null;
		this.subGrupo = subGrupo;
		createSubGrupo(subGrupo);
	}

	public SubGrupoComponenteController(Long idGrupo, SubGrupo subGrupo) {
		this.idGrupo = idGrupo;
		this.subGrupo = subGrupo;
		createSubGrupo(subGrupo);
	}

}
