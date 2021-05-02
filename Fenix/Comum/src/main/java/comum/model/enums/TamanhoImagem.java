package comum.model.enums;

/**
 * <p>
 * Enuns para identificar os tamanhos de imagem salvo no banco.
 * </p>
 * 
 * <p>
 * <b>ORIGINAL, 100, 600</b>
 * </p>
 * 
 * @author Jhonny de Salles Noschang
 */
public enum TamanhoImagem {

	NENHUMA("Nenhuma"), TODOS("Todos"), ORIGINAL("Original"), PEQUENA("Pequena (100x100)"), MEDIA("Média (500x500)");

	private String tamanhoImagem;

	TamanhoImagem(String tamanhoImagem) {
		this.tamanhoImagem = tamanhoImagem;
	}

	public String getDescricao() {
		return tamanhoImagem;
	}

	@Override
	public String toString() {
		return this.tamanhoImagem;
	}

}
