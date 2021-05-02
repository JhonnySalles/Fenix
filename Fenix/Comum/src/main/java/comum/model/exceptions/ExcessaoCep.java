package comum.model.exceptions;

/**
 * <p>
 * Classe responssável por gerar excessões personalizadas aos eventos do banco.
 * </p>
 * 
 * @author Jhonny de Salles Noschang
 */
public class ExcessaoCep extends Exception {

	private static final long serialVersionUID = 1L;

	public ExcessaoCep(String mensagem) {
		super(mensagem);
	}
}
