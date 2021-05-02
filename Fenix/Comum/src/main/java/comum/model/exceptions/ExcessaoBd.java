package comum.model.exceptions;

/**
 * <p>
 * Classe responssável por gerar excessões personalizadas aos eventos do banco.
 * </p>
 * 
 * @author Jhonny de Salles Noschang
 */
public class ExcessaoBd extends Exception {

	private static final long serialVersionUID = 1L;

	public ExcessaoBd(String mensagem) {
		super(mensagem);
	}
}
