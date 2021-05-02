package comum.model.enums;

/**
 * <p>
 * Enuns para identificar um componente padrão.
 * </p>
 * 
 * <p>
 * <b>SIM, NAO</b>
 * </p>
 * 
 * @author Jhonny de Salles Noschang
 */
public enum Padrao {

	SIM("Sim"), NAO("Não");

	private String padrao;

	Padrao(String padrao) {
		this.padrao = padrao;
	}

	public String getDescricao() {
		return padrao;
	}

	@Override
	public String toString() {
		return this.padrao;
	}

}
