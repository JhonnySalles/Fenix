package servidor.entities;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Table;

import comum.model.entities.Entidade;
import comum.model.enums.Situacao;

@Entity
@Table(name = "sub_grupos")
public class SubGrupo extends GrupoBase implements Entidade {

	// Utilizado para poder ser transformado em sequencia de bytes
	// e poder então trafegar os dados em rede ou salvar em arquivo.
	private static final long serialVersionUID = -4164176524362313995L;

	public SubGrupo() {
		super();
	}

	public SubGrupo(Long id, String descricao, String cor, Situacao situacao, Set<GupoSubGrupoImagem> imagens) {
		super(id, descricao, cor, situacao, imagens);
	}

	public SubGrupo(Long id, String descricao, String cor, Situacao situacao) {
		super(id, descricao, cor, situacao);
	}

}
