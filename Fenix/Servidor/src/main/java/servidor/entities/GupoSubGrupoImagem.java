package servidor.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

import comum.model.enums.TamanhoImagem;

@Entity
@Table(name = "grupo_subgrupo_imagens")
public class GupoSubGrupoImagem extends Imagem {

	private static final long serialVersionUID = 2820959226817240910L;

	public GupoSubGrupoImagem() {
		super();
	}

	public GupoSubGrupoImagem(Long id, String nome, String extenssao, byte[] imagem, TamanhoImagem tamanho) {
		super(id, nome, extenssao, imagem, tamanho);
	}

	public GupoSubGrupoImagem(String nome, String extenssao, byte[] imagem, TamanhoImagem tamanho) {
		super(nome, extenssao, imagem, tamanho);
	}

}
