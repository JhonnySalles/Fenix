package cadastro.controller.componente;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import cadastro.controller.lista.ListaGrupoSubGrupoController;
import comum.model.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import servidor.entities.Grupo;
import servidor.entities.SubGrupo;

public class GrupoComponenteController implements Initializable {

	@FXML
	private StackPane root;

	@FXML
	private Label lblDescricao;

	@FXML
	private FlowPane fpSubGrupoContainer;

	private Grupo grupo;

	private ObservableList<SubGrupoComponenteController> subGrupo = FXCollections.observableArrayList();

	final ListaGrupoSubGrupoController InstanciaListaGrupo;

	public StackPane getRoot() {
		return root;
	}

	/**
	 * <p>
	 * Irá criar uma função onDrag, na qual poderá ser utilizado para realizar a
	 * exclusão. O valor aplicado no evento terá as tag idGrupo e idSubGrupo, no
	 * qual pode-se identificar qual subGrupo remover.
	 * </p>
	 * 
	 * @param subGrupo SubGrupo a ser adicionado.
	 * 
	 * @return Retorna o componente <b>SubGrupoComponenteController</b>.
	 * 
	 * @author Jhonny de Salles Noschang
	 */
	private SubGrupoComponenteController createComponentSubGrupo(Long idGrupo, SubGrupo subGrupo) {
		SubGrupoComponenteController sub = new SubGrupoComponenteController(idGrupo, subGrupo);
		sub.getRoot().setOnDragDetected(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				sub.getRoot().startFullDrag();
				Dragboard db = sub.getRoot().startDragAndDrop(TransferMode.ANY);
				ClipboardContent content = new ClipboardContent();
				content.putString("idGrupo:" + sub.getIdGrupo() + " - idSubGrupo:" + sub.getSubGrupo().getId());
				db.setContent(content);
				InstanciaListaGrupo.setTextoAvisoCampoRemover("Arraste até aqui para remove-lo.", true);

				// A função cria uma imagem a ser arrastada do componente.
				SnapshotParameters snapshotParameters = new SnapshotParameters();
				db.setDragView(sub.getRoot().snapshot(snapshotParameters, null), event.getX(), event.getY());
			}
		});

		sub.getRoot().setOnMouseDragged((MouseEvent event) -> {
			event.setDragDetect(true);
		});

		sub.getRoot().setOnDragDone(e -> InstanciaListaGrupo.setTextoAvisoCampoRemover("", true));

		return sub;
	}

	/**
	 * <p>
	 * Função para adicionar o subgrupo, onde alem de adicionar no grupo, também
	 * cria o componente dentro dele.
	 * </p>
	 * 
	 * <p>
	 * 
	 * @param subGrupo SubGrupo a ser adicionado.
	 * 
	 * @author Jhonny de Salles Noschang
	 */
	public void addSubGrupo(Long idGrupo, SubGrupo subGrupo) {
		if (grupo.addSubGrupo(subGrupo))
			this.subGrupo.add(createComponentSubGrupo(grupo.getId(), subGrupo));
	}

	/**
	 * <p>
	 * Função para remover o subGrupo informado, já irá encontrar e remover o
	 * componente visual do sub grupo.
	 * </p>
	 * 
	 * @param subGrupo SubGrupo a ser removido.
	 * 
	 * @author Jhonny de Salles Noschang
	 */
	public void removeSubGrupo(SubGrupo subGrupo) {
		if (grupo.removeSubGrupo(subGrupo)) {
			Optional<SubGrupoComponenteController> component = this.subGrupo.stream()
					.filter(sb -> sb.getSubGrupo().equals(subGrupo)).findFirst();

			if (component.isPresent())
				this.subGrupo.remove(component.get());
		}
	}

	/**
	 * <p>
	 * Função para remover o subGrupo informado, já irá encontrar e remover o
	 * componente visual do sub grupo.
	 * </p>
	 * 
	 * @param idSubGrupo Um id <b>Long</b> do subGrupo a ser removido.
	 * 
	 * @author Jhonny de Salles Noschang
	 */
	public void removeSubGrupo(Long idSubGrupo) {
		Optional<SubGrupo> obj = this.grupo.getSubGrupos().stream().filter(sb -> sb.getId().equals(idSubGrupo))
				.findFirst();

		if (obj.isPresent()) {
			this.grupo.removeSubGrupo(obj.get());

			Optional<SubGrupoComponenteController> component = this.subGrupo.stream()
					.filter(sb -> sb.getSubGrupo().getId().equals(idSubGrupo)).findFirst();

			if (component.isPresent())
				this.subGrupo.remove(component.get());
		}
	}

	public Grupo getGrupo() {
		return grupo;
	}

	private void setGrupo(Grupo grupo) {
		this.grupo = grupo;
		this.lblDescricao.setText(grupo.getDescricao());

		for (SubGrupo subGrupo : grupo.getSubGrupos())
			this.subGrupo.add(createComponentSubGrupo(grupo.getId(), subGrupo));

		root.setStyle("-fx-background-radius: 5; -fx-background-color: " + grupo.getCor() + ";");
		lblDescricao.setTextFill(Utils.getContrastColor(grupo.getCor()));
	}

	private void createListener() {
		subGrupo.addListener(new ListChangeListener<SubGrupoComponenteController>() {
			@Override
			public void onChanged(Change<? extends SubGrupoComponenteController> c) {
				while (c.next()) {
					if (c.wasPermutated()) {
						for (int i = c.getFrom(); i < c.getTo(); ++i) {
							// permutate
						}
					} else if (c.wasUpdated()) {
						// update item
					} else {
						for (SubGrupoComponenteController remitem : c.getRemoved()) {
							fpSubGrupoContainer.getChildren().remove(remitem.getRoot());
						}
						for (SubGrupoComponenteController additem : c.getAddedSubList()) {
							fpSubGrupoContainer.getChildren().add(additem.getRoot());
						}

						// Devido ao efeito aplicado no root do grupo, será subtraido o valor do 
						// efeito no tamanho minimo.
						if (root.getBoundsInLocal().getHeight() == 0.0)
							root.setPrefHeight(50);
						else
							root.setPrefHeight(root.getBoundsInLocal().getHeight() - 15);

						InstanciaListaGrupo.getComponenteGrupo().clearLayout();
						InstanciaListaGrupo.getComponenteGrupo().requestLayout();
					}
				}

			}
		});
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		createListener();
	}

	/**
	 * <p>
	 * Construtor padrão do componente, no qual recebe um grupo principal que será
	 * criado o componente visual e o vinculado seus subGrupo.
	 * </p>
	 * 
	 * @param grupo O grupo base do componente.
	 * 
	 * @author Jhonny de Salles Noschang
	 */
	public GrupoComponenteController(ListaGrupoSubGrupoController instancia, Grupo grupo) {
		this.InstanciaListaGrupo = instancia;
		FXMLLoader fxmlLoader = new FXMLLoader(GrupoComponenteController.getFxmlLocate());
		fxmlLoader.setController(this);
		try {
			fxmlLoader.load();
			setGrupo(grupo);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static URL getFxmlLocate() {
		return GrupoComponenteController.class.getResource("/cadastro/view/componente/GrupoComponente.fxml");
	}

}
