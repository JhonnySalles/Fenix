package servidor.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

import comum.model.enums.TamanhoImagem;

@Entity
@Table(name = "empresas_imagens")
public class EmpresaImagem extends Imagem {

	private static final long serialVersionUID = 5107953301444621796L;

	public EmpresaImagem() {
		super();
	}

	public EmpresaImagem(Long id, String nome, String extenssao, byte[] imagem, TamanhoImagem tamanho) {
		super(id, nome, extenssao, imagem, tamanho);
	}

	public EmpresaImagem(String nome, String extenssao, byte[] imagem, TamanhoImagem tamanho) {
		super(nome, extenssao, imagem, tamanho);
	}

}
