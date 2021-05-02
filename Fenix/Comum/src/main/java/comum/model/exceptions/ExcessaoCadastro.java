package comum.model.exceptions;

/**
 * <p>
 * Classe responssável por gerar excessões personalizadas aos cadastros.
 * </p>
 * 
 * @author Jhonny de Salles Noschang
 */
public class ExcessaoCadastro extends Exception {

	private static final long serialVersionUID = 1L;

	public ExcessaoCadastro(String mensagem) {
		super(mensagem);
	}
}
