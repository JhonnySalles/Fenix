package servidor.dto;

import servidor.entities.EmpresaImagem;
import servidor.entities.GupoSubGrupoImagem;
import servidor.entities.Imagem;
import servidor.entities.ProdutoImagem;
import servidor.entities.UsuarioImagem;

public class ImagemDTO {

	public static EmpresaImagem toEmpresaImagem(Imagem imagem) {
		return new EmpresaImagem(imagem.getId(), imagem.getNome(), imagem.getExtenssao(), imagem.getImagem(),
				imagem.getTamanho());
	}

	public static ProdutoImagem toProdutoImagem(Imagem imagem) {
		return new ProdutoImagem(imagem.getId(), imagem.getNome(), imagem.getExtenssao(), imagem.getImagem(),
				imagem.getTamanho());
	}

	public static UsuarioImagem toUsuarioImagem(Imagem imagem) {
		return new UsuarioImagem(imagem.getId(), imagem.getNome(), imagem.getExtenssao(), imagem.getImagem(),
				imagem.getTamanho());
	}
	
	public static GupoSubGrupoImagem toGrupoSubGrupoImagem(Imagem imagem) {
		return new GupoSubGrupoImagem(imagem.getId(), imagem.getNome(), imagem.getExtenssao(), imagem.getImagem(),
				imagem.getTamanho());
	}

}
