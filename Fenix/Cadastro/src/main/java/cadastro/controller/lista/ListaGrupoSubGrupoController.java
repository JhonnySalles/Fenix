package cadastro.controller.lista;

import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXMasonryPane;
import com.jfoenix.controls.JFXTextField;

import cadastro.controller.cadastros.DialogCadGrupoSubGrupoController;
import cadastro.controller.componente.GrupoComponenteController;
import cadastro.controller.componente.SubGrupoComponenteController;
import comum.form.ListaFormPadrao;
import comum.model.messages.Mensagens;
import comum.model.notification.Notificacoes;
import comum.model.utils.Utils;
import comum.model.utils.ViewGerenciador;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import servidor.dao.services.GenericService;
import servidor.entities.Grupo;
import servidor.entities.GrupoBase;
import servidor.entities.SubGrupo;

public class ListaGrupoSubGrupoController extends ListaFormPadrao {

	private Map<Long, SubGrupo> listSubGrupo = new HashMap<>();

	protected final static DropShadow EFEITO_DESTAQUE = new DropShadow();

	@FXML
	private JFXButton btnConfirmar;

	@FXML
	private JFXTextField txtFiltrarGrupo;

	@FXML
	private JFXTextField txtFiltrarSubGrupo;

	@FXML
	private JFXMasonryPane mspGrupos;

	@FXML
	private HBox hbRemover;

	@FXML
	private Label lblRemover;

	@FXML
	private FlowPane flpSubGrupos;

	private DialogCadGrupoSubGrupoController controller;

	private GenericService<Grupo> serviceGrupo = new GenericService<Grupo>(Grupo.class);
	private GenericService<SubGrupo> serviceSubGrupo = new GenericService<SubGrupo>(SubGrupo.class);

	private ObservableList<GrupoComponenteController> grupo = FXCollections.observableArrayList();

	private ObservableList<SubGrupoComponenteController> subGrupo = FXCollections.observableArrayList();

	final static PseudoClass ADICIONAR = PseudoClass.getPseudoClass("Adicionar");
	final static PseudoClass REMOVER = PseudoClass.getPseudoClass("Remover");

	@Override
	protected void onBtnNovoClick() {
		abreTelaCadGrupoSubGrupo(null);
	}

	@Override
	protected void onBtnExcluirClick() {
		// Não terá botão de excluir.
	}

	@Override
	protected void onBtnEditarClick() {
		// Não terá botão de editar.
	}

	@Override
	protected void onBtnAtualizarClick() {
		grupo.clear();
		subGrupo.clear();
		listGrupo(new HashSet<>(serviceGrupo.listar()));
		listSubGrupo(new HashSet<>(serviceSubGrupo.listar()));
	}

	@FXML
	public void onBtnConfirmarClick() {
		try {
			spRoot.cursorProperty().set(Cursor.WAIT);
			this.desabilitaBotoes();

			System.out.println(grupo.toString());
			List<Grupo> grupos = grupo.stream().parallel().map(gp -> gp.getGrupo()).collect(Collectors.toList());
			serviceGrupo.salvar(grupos);

			Notificacoes.notificacao(AlertType.NONE, Mensagens.CONCLUIDO, "Grupos salvo com sucesso.");
		} finally {
			spRoot.cursorProperty().set(null);
			this.habilitaBotoes();
		}

	}

	@Override
	protected ListaGrupoSubGrupoController desabilitaBotoes() {
		super.desabilitaBotoes();
		btnConfirmar.setDisable(false);
		return this;
	}

	@Override
	protected ListaGrupoSubGrupoController habilitaBotoes() {
		super.habilitaBotoes();
		btnConfirmar.setDisable(false);
		return this;
	}

	public JFXMasonryPane getComponenteGrupo() {
		return mspGrupos;
	}

	private void abreTelaCadGrupoSubGrupo(GrupoBase grupoSubGrupo) {
		controller = (DialogCadGrupoSubGrupoController) ViewGerenciador
				.loadDialog(DialogCadGrupoSubGrupoController.getFxmlLocate(), spRoot, new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent t) {
						if (controller.getGrupoSubGrupo() == null)
							return;

						if (controller.getRecarregar() || controller.getGrupoSubGrupo().getId() == 0) {
							onBtnAtualizarClick();
						} else {
							if (controller.getGrupoSubGrupo() instanceof SubGrupo)
								listSubGrupo((SubGrupo) controller.getGrupoSubGrupo());
							else
								addGrupo((Grupo) controller.getGrupoSubGrupo());
						}
					}
				});

		controller.carregar(grupoSubGrupo);
	}

	private void aplicarEfeitoGrupos(Boolean aplicar) {
		for (GrupoComponenteController gp : grupo)
			gp.getRoot().setEffect(aplicar ? EFEITO_DESTAQUE : null);
	}

	public void setTextoAvisoCampoRemover(String texto, Boolean tipoRemover) {
		if (texto.isEmpty()) {
			lblRemover.setText("Remover");
			hbRemover.pseudoClassStateChanged(REMOVER, false);
			hbRemover.pseudoClassStateChanged(ADICIONAR, false);
			hbRemover.setEffect(null);
			aplicarEfeitoGrupos(false);
		} else {
			lblRemover.setText(texto);

			if (tipoRemover) {
				hbRemover.pseudoClassStateChanged(REMOVER, true);
				hbRemover.setEffect(EFEITO_DESTAQUE);
			} else {
				hbRemover.pseudoClassStateChanged(ADICIONAR, true);
				aplicarEfeitoGrupos(true);
			}
		}
	}

	/**
	 * <p>
	 * Função será utilizada para listar os subGrupos no panel dos subGrupos.
	 * </p>
	 * 
	 * @param subGrupos Uma lista <b>Set</b> de subGrupos.
	 * 
	 * @author Jhonny de Salles Noschang
	 */
	public void listGrupo(Set<Grupo> grupos) {
		for (Grupo gp : grupos)
			addGrupo(gp);
	}

	/**
	 * <p>
	 * Função será utilizada para criar e adicionar um grupo no pane de grupos.
	 * </p>
	 * 
	 * <p>
	 * Irá criar uma função onDragDropped, no qual será utilizado para realizar a
	 * inserção so subGrupo. O valor aplicado no evento terá as tag idSubGrupo, no
	 * qual pode-se identificar qual subGrupo a ser adicionado.
	 * </p>
	 * 
	 * @param grupo Um <b>Grupo</b> a ser adicionado.
	 * 
	 * @author Jhonny de Salles Noschang
	 */
	public void addGrupo(Grupo grupo) {
		Optional<GrupoComponenteController> obj = this.grupo.stream().filter(sb -> sb.getGrupo().equals(grupo))
				.findFirst();

		if (obj.isPresent())
			this.grupo.remove(obj.get());

		GrupoComponenteController grup = new GrupoComponenteController(this, grupo);
		this.grupo.add(grup);

		grup.getRoot().setOnDragOver(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				if (event.getGestureSource() != grup.getRoot() && event.getDragboard().hasString())
					event.acceptTransferModes(TransferMode.COPY_OR_MOVE);

				event.consume();
			}
		});

		grup.getRoot().setOnDragDropped((DragEvent event) -> {
			Dragboard db = event.getDragboard();
			if (db.hasString() && db.getString().contains("idSubGrupo:")) {
				Long subGrupo = Utils.getId("idSubGrupo:", db.getString(), "");

				grup.addSubGrupo(grupo.getId(), listSubGrupo.get(subGrupo));
				event.setDropCompleted(true);
			} else
				event.setDropCompleted(false);
			event.consume();

			spBackground.requestLayout();
		});

		// Gera um evento de duplo clique para edição.
		grup.getRoot().setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
					if (mouseEvent.getClickCount() == 2)
						abreTelaCadGrupoSubGrupo(grup.getGrupo());
				}
			}
		});
	}

	/**
	 * <p>
	 * Função será utilizada para adicionar um subGrupo no panel dos subGrupos,
	 * também irá criar o componente do subGrupo e criar uma função onDrag no
	 * componente para poder arrastar para dentro do grupo.
	 * </p>
	 * 
	 * @param subGrupo Um <b>SubGrupo</b> a ser adicionado.
	 * 
	 * @author Jhonny de Salles Noschang
	 */
	public void listSubGrupo(SubGrupo subGrupo) {
		listSubGrupo.put(subGrupo.getId(), subGrupo);

		SubGrupoComponenteController sub = new SubGrupoComponenteController(subGrupo);
		sub.getRoot().setOnDragDetected(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				sub.getRoot().startFullDrag();
				Dragboard db = sub.getRoot().startDragAndDrop(TransferMode.ANY);
				ClipboardContent content = new ClipboardContent();
				content.putString("idSubGrupo:" + sub.getSubGrupo().getId());
				db.setContent(content);

				// A função cria uma imagem a ser arrastada do componente.
				SnapshotParameters snapshotParameters = new SnapshotParameters();
				db.setDragView(sub.getRoot().snapshot(snapshotParameters, null), event.getX(), event.getY());

				setTextoAvisoCampoRemover("Arraste até algum grupo para adiciona-lo.", false);
			}
		});

		sub.getRoot().setOnMouseDragged((MouseEvent event) -> {
			event.setDragDetect(true);
		});

		sub.getRoot().setOnDragDone(e -> setTextoAvisoCampoRemover("", false));

		sub.getRoot().setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
					if (mouseEvent.getClickCount() == 2)
						abreTelaCadGrupoSubGrupo(sub.getSubGrupo());
				}
			}
		});

		this.subGrupo.add(sub);
	}

	/**
	 * <p>
	 * Função será utilizada para listar os subGrupos no panel dos subGrupos.
	 * </p>
	 * 
	 * @param subGrupos Uma lista <b>Set</b> de subGrupos.
	 * 
	 * @author Jhonny de Salles Noschang
	 */
	public void listSubGrupo(Set<SubGrupo> subGrupos) {
		for (SubGrupo sb : subGrupos)
			listSubGrupo(sb);
	}

	/**
	 * <p>
	 * Função para encontrar e remover o subGrupo do grupo.
	 * </p>
	 * 
	 * @param idGrupo    Um id <b>Long</b> do grupo que contém o subGrupo a ser
	 *                   removido.
	 * @param idSubGrupo Um id <b>Long</b> do subGrupo a ser removido.
	 * 
	 * @author Jhonny de Salles Noschang
	 */
	private void removeSubGrupo(Long idGrupo, Long idSubGrupo) {
		Optional<GrupoComponenteController> obj = this.grupo.stream()
				.filter(sb -> sb.getGrupo().getId().equals(idGrupo)).findFirst();

		if (obj.isPresent())
			obj.get().removeSubGrupo(idSubGrupo);
	}

	/**
	 * <p>
	 * Função principal que faz a criação dos listeners dos <b>ObservableList</b> no
	 * qual fará o trabalho de criar os componentes visuais e adicionar dentro do
	 * pane de grupo ou subGrupo.
	 * </p>
	 * 
	 * <p>
	 * Irá criar uma função no campo remover para que pode-se identificar e remover
	 * o subGrupo do gruop. O valor aplicado no evento deverá ter a tag idGrupo e
	 * idSubGrupo, no qual pode-se identificar qual subGrupo a ser removido.
	 * </p>
	 * 
	 * @author Jhonny de Salles Noschang
	 */
	private void createListener() {
		grupo.addListener(new ListChangeListener<GrupoComponenteController>() {
			@Override
			public void onChanged(Change<? extends GrupoComponenteController> c) {
				while (c.next()) {
					if (c.wasPermutated()) {
						for (int i = c.getFrom(); i < c.getTo(); ++i) {
							// permutate
						}
					} else if (c.wasUpdated()) {
						// update item
					} else {
						for (GrupoComponenteController remitem : c.getRemoved())
							mspGrupos.getChildren().remove(remitem.getRoot());

						for (GrupoComponenteController additem : c.getAddedSubList())
							mspGrupos.getChildren().add(additem.getRoot());

						mspGrupos.requestLayout();
					}
				}

			}
		});

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
						for (SubGrupoComponenteController remitem : c.getRemoved())
							flpSubGrupos.getChildren().remove(remitem.getRoot());

						for (SubGrupoComponenteController additem : c.getAddedSubList())
							flpSubGrupos.getChildren().add(additem.getRoot());

						flpSubGrupos.requestLayout();
					}
				}

			}
		});

		hbRemover.setOnDragOver(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				if (event.getGestureSource() != hbRemover && event.getDragboard().hasString())
					event.acceptTransferModes(TransferMode.COPY_OR_MOVE);

				event.consume();
			}
		});

		hbRemover.setOnDragDropped((DragEvent event) -> {
			Dragboard db = event.getDragboard();
			if (db.hasString() && db.getString().contains("idGrupo:") && db.getString().contains("idSubGrupo:")) {
				Long grupo = Utils.getId("idGrupo:", db.getString(), "-");
				Long subGrupo = Utils.getId("idSubGrupo:", db.getString(), "");
				removeSubGrupo(grupo, subGrupo);

				event.setDropCompleted(true);
			} else
				event.setDropCompleted(false);
			event.consume();
		});
	}

	private ListaGrupoSubGrupoController setEfeito() {
		EFEITO_DESTAQUE.setWidth(25.0);
		EFEITO_DESTAQUE.setHeight(25.0);
		EFEITO_DESTAQUE.setRadius(10.0);
		return this;
	}

	@Override
	protected void inicializa(URL arg0, ResourceBundle arg1) {
		createListener();
		setEfeito();

	}

	public static URL getFxmlLocate() {
		return ListaGrupoSubGrupoController.class.getResource("/cadastro/view/lista/ListaGrupoSubGrupo.fxml");
	}

}
