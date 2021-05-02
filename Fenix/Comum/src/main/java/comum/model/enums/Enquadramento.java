package comum.model.enums;

/**
 * <p>Enuns de tipos de clientes ou fornecedores.</p>
 * 
 * <p><b>CLIENTE, FORNECEDOR, AMBOS</b></p>
 * 
 * @author Jhonny de Salles Noschang
 */
public enum Enquadramento {
	
	CLIENTE("Cliente"), FORNECEDOR("Fornecedor"), AMBOS("Ambos");

	private String enquadramento;

	Enquadramento(String enquadramento) {
		this.enquadramento = enquadramento;
	}

	public String getDescricao() {
		return enquadramento;
	}
	
	@Override      
	public String toString(){
	    return this.enquadramento;
	}  
}
