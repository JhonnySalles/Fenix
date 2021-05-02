package comum.model.enums;

/**
 * <p>Enuns de tipos de grupo ou sub grupo.</p>
 * 
 * <table border="1">
 * <tr><th scope="col">ENUM</th><th scope="col">Descrição</th></tr>
 * <tr>
 * <td>GRUPO</td>
 * <td>Grupo dos produtos</td>
 * </tr>
 * <tr>
 * <td>SUBGRUPO</td>
 * <td>Sub grupo dos produtos</td>
 * </tr>
 * </table>
 * @author Jhonny de Salles Noschang
 */
public enum TipoGrupo {

	GRUPO("Grupo"), SUBGRUPO("Sub Grupo");

	private String tipoGrupo;

	TipoGrupo(String tipoGrupo) {
		this.tipoGrupo = tipoGrupo;
	}

	public String getDescricao() {
		return tipoGrupo;
	}
	
	@Override      
	public String toString(){
	    return this.tipoGrupo;
	} 
}
