package comum.model.exceptions;

/**
 * <p>
 * Classe responssável por gerar excessões personalizadas aos login.
 * </p>
 * 
 * @author Jhonny de Salles Noschang
 */
public class ExcessaoLogin extends Exception {

	private static final long serialVersionUID = 1L;

	public ExcessaoLogin(String mensagem) {
		super(mensagem);
	}
}
