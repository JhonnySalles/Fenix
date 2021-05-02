package cadastro.controller.cadastros;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import cadastro.utils.CadastroUtils;
import comum.form.CadastroDialogPadrao;
import comum.model.constraints.TecladoUtils;
import comum.model.constraints.Validadores;
import comum.model.enums.Situacao;
import comum.model.enums.TipoGrupo;
import comum.model.exceptions.ExcessaoCadastro;
import comum.model.messages.Mensagens;
import comum.model.notification.Notificacoes;
import comum.model.utils.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import servidor.dao.services.GenericService;
import servidor.dto.ImagemDTO;
import servidor.entities.Grupo;
import servidor.entities.GrupoBase;
import servidor.entities.GupoSubGrupoImagem;
import servidor.entities.Imagem;
import servidor.entities.SubGrupo;
import servidor.validations.ValidaGrupoSubGrupo;

public class DialogCadGrupoSubGrupoController extends CadastroDialogPadrao<GrupoBase> {

	private final static Logger LOGGER = Logger.getLogger(CadProdutoController.class.getName());

	final static Image ImagemPadrao = new Image(DialogCadGrupoSubGrupoController.class
			.getResourceAsStream("/cadastro/imagens/white/geral/icoProdutoImage_256.png"));

	@FXML
	private JFXTextField txtDescricao;

	@FXML
	private JFXColorPicker cpCorFundo;

	@FXML
	private JFXComboBox<TipoGrupo> cbTipo;

	@FXML
	private JFXCheckBox cbAtivo;

	@FXML
	private ImageView imgGrupo;

	@FXML
	private JFXButton btnExcluirImagem;

	@FXML
	private JFXButton btnProcurarImagem;

	private GrupoBase grupoSubGrupo;
	private Set<GupoSubGrupoImagem> imagens;
	private GenericService<Grupo> serviceGrupo = new GenericService<Grupo>(Grupo.class);
	private GenericService<SubGrupo> serviceSubGrupo = new GenericService<SubGrupo>(SubGrupo.class);

	private Boolean recarregar;

	@Override
	public void onBtnConfirmarClick() {
		atualizaEntidade();
		if (validaCampos())
			salvar(grupoSubGrupo);
	}

	@Override
	public void onBtnCancelarClick() {
		limpaCampos();
	}

	@Override
	protected void salvar(GrupoBase entidade) {
		if (grupoSubGrupo instanceof Grupo)
			serviceGrupo.salvar((Grupo) entidade);
		else if (grupoSubGrupo instanceof SubGrupo)
			serviceSubGrupo.salvar((SubGrupo) entidade);
		
		limpaCampos();
		recarregar = true;
	}

	@Override
	public void carregar(GrupoBase entidade) {
		limpaCampos();
		recarregar = false;

		if (entidade != null) {
			this.edicao = true;
			atualizaTela(entidade);
		}
	}

	@Override
	protected boolean validaCampos() {
		try {
			return ValidaGrupoSubGrupo.validaGrupoSubGrupo(grupoSubGrupo);
		} catch (ExcessaoCadastro e) {
			e.printStackTrace();
			Notificacoes.notificacao(AlertType.INFORMATION, Mensagens.AVISO,
					e.getMessage().isEmpty() ? Mensagens.CADASTRO_SALVAR : e.getMessage());
		}

		txtDescricao.validate();
		return false;
	}

	@Override
	public DialogCadGrupoSubGrupoController atualizaEntidade() {
		if (!edicao) {
			if (cbTipo.getSelectionModel().getSelectedItem().equals(TipoGrupo.SUBGRUPO))
				grupoSubGrupo = new SubGrupo();
			else
				grupoSubGrupo = new Grupo();
		}

		grupoSubGrupo.setDescricao(txtDescricao.getText());
		grupoSubGrupo.setCor(Utils.colorToHexString(cpCorFundo.getValue()));
		grupoSubGrupo.setSituacao(cbAtivo.selectedProperty().get() ? Situacao.ATIVO : Situacao.INATIVO);
		grupoSubGrupo.setImagens(imagens);

		return this;
	}

	@Override
	public void onClose() {

	}

	@FXML
	public void onBtnProcurarImagemClick() {
		File caminhoImagem = Utils.procurarImagem();

		if (caminhoImagem != null) {
			try {
				if (imagens == null)
					imagens = new HashSet<GupoSubGrupoImagem>();

				imgGrupo.setImage(new Image(caminhoImagem.toURI().toString()));
				imagens.addAll(CadastroUtils.processaImagens(caminhoImagem).stream()
						.map(imagem -> ImagemDTO.toGrupoSubGrupoImagem(imagem)).collect(Collectors.toList()));
			} catch (IOException e) {
				e.printStackTrace();
				LOGGER.log(Level.INFO, "{Erro ao carregar e processar a imagem}", e);
				Notificacoes.notificacao(AlertType.ERROR, Mensagens.ERRO, Mensagens.IMG_ERRO_CARREGAR);
				setImagemPadrao();
			}
		}
	}

	private DialogCadGrupoSubGrupoController setImagemPadrao() {
		imagens = null;
		imgGrupo.setImage(ImagemPadrao);
		return this;
	}

	@FXML
	public void onBtnExcluirImagemClick() {
		for (Imagem img : imagens)
			img.setExcluir(true);

		setImagemPadrao();
	}

	public Boolean getRecarregar() {
		return recarregar;
	}

	public GrupoBase getGrupoSubGrupo() {
		return this.grupoSubGrupo;
	}

	public void limpaCampos() {
		this.edicao = false;
		grupoSubGrupo = new GrupoBase();
		txtDescricao.setText("");
		cpCorFundo.setValue(Color.WHITE);
		cbAtivo.setSelected(true);
		cbTipo.setDisable(false);
	}

	public DialogCadGrupoSubGrupoController atualizaTela(GrupoBase grupoSubGrupo) {
		limpaCampos();
		this.edicao = true;

		if (grupoSubGrupo instanceof Grupo)
			cbTipo.getSelectionModel().select(TipoGrupo.GRUPO);
		else if (grupoSubGrupo instanceof SubGrupo)
			cbTipo.getSelectionModel().select(TipoGrupo.SUBGRUPO);

		this.grupoSubGrupo = grupoSubGrupo;

		if (!grupoSubGrupo.getDescricao().isEmpty())
			txtDescricao.setText(grupoSubGrupo.getDescricao());

		if (!grupoSubGrupo.getCor().isEmpty())
			cpCorFundo.setValue(Color.web(grupoSubGrupo.getCor()));

		cbAtivo.setSelected(grupoSubGrupo.getSituacao().equals(Situacao.ATIVO));

		if (edicao)
			cbTipo.setDisable(true);

		return this;
	}

	@Override
	public synchronized void inicializa(URL location, ResourceBundle resources) {
		Validadores.setTextFieldNotEmpty(txtDescricao);

		TecladoUtils.onEnterConfigureTab(txtDescricao);
		TecladoUtils.onEnterConfigureTab(cbTipo);

		cbTipo.getItems().addAll(TipoGrupo.values());
		cbTipo.getSelectionModel().selectFirst();
	}

	public static URL getFxmlLocate() {
		return DialogCadContatoController.class.getResource("/cadastro/view/cadastros/DialogCadGrupoSubGrupo.fxml");
	}

}
