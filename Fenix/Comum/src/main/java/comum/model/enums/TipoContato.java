package comum.model.enums;

/**
 * <p>Enuns de tipos de telefone de contato.</p>
 * 
 * <p><b>TELEFONE, CELULAR, FAX</b></p>
 * 
 * @author Jhonny de Salles Noschang
 */
public enum TipoContato {

	RESIDENCIAL("Residencial"), COMERCIAL("Comercial");

	private String tipoTelefone;

	TipoContato(String tipoTelefone) {
		this.tipoTelefone = tipoTelefone;
	}

	public String getDescricao() {
		return tipoTelefone;
	}
	
	@Override      
	public String toString(){
	    return this.tipoTelefone;
	} 

}
