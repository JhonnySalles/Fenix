package comum.model.enums;

/**
 * <p>
 * <b>Homologação, Produção</b>
 * </p>
 * 
 * @author Jhonny de Salles Noschang
 */
public enum TipoLancamento {

	HOMOLOGACAO("Homologação"), PRODUCAO("Produção");

	private String lancamento;

	TipoLancamento(String lancamento) {
		this.lancamento = lancamento;
	}

	public String getDescricao() {
		return lancamento;
	}

	@Override
	public String toString() {
		return this.lancamento;
	}

}
