package comum.model.enums;

/**
 * <p>
 * Enuns de situações.
 * </p>
 * 
 * <p>
 * <b>ATIVO, INATIVO</b>
 * </p>
 * 
 * @author Jhonny de Salles Noschang
 */
public enum Situacao {

	ATIVO("Ativo"), INATIVO("Inativo"), EXCLUIDO("Excluido");

	private String situacao;

	Situacao(String situacao) {
		this.situacao = situacao;
	}

	public String getDescricao() {
		return situacao;
	}
	
	// Necessário para que a escrita do combo seja Ativo e não ATIVO (nome do enum)
	@Override
	public String toString() {
		return this.situacao;
	}

}
