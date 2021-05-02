package comum.model.enums;

/**
 * <p>Enuns de tipos nível de usuário.</p>
 * 
 * <p><b>USUARIO, ADMINISTRADOR, TOTAL</b></p>
 * 
 * @author Jhonny de Salles Noschang
 */
public enum UsuarioNivel {

	USUARIO("Usuário"), ADMINISTRADOR("Administrador"), TOTAL("");

	private String nivel;

	UsuarioNivel(String nivel) {
		this.nivel = nivel;
	}

	public String getDescricao() {
		return nivel;
	}
	
	@Override      
	public String toString(){
	    return this.nivel;
	} 
}
