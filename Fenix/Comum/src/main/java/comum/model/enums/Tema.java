package comum.model.enums;

/**
 * <p>
 * Enuns utilizado para definir o tema do sistema.
 * </p>
 * 
 * <p>
 * <b>WHITE, BLACK</b>
 * </p>
 * 
 * @author Jhonny de Salles Noschang
 */
public enum Tema {

	WHITE("White"), BLACK("Black");

	private String notificacao;

	Tema(String notificacao) {
		this.notificacao = notificacao;
	}

	public String getDescricao() {
		return notificacao;
	}

	// Necessário para que a escrita do combo seja Ativo e não ATIVO (nome do enum)
	@Override
	public String toString() {
		return this.notificacao;
	}
}
