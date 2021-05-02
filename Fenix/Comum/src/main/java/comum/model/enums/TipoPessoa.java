package comum.model.enums;

/**
 * <p>Enuns de tipos de pessoa (Físico/Jurídico).</p>
 * 
 * <p><b>FISICO, JURIDICO, FISICO_JURIDICO</b></p>
 * 
 * @author Jhonny de Salles Noschang
 */
public enum TipoPessoa {
	
	FISICO("Físico"), JURIDICO("Júridico"), AMBOS("Ambos");

	private String tipoPessoa;

	TipoPessoa(String tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public String getDescricao() {
		return tipoPessoa;
	}
	
	@Override      
	public String toString(){
	    return this.tipoPessoa;
	}  
}
