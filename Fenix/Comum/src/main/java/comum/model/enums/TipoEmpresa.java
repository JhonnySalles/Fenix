package comum.model.enums;

/**
 * <p>
 * Enuns de tipos de empresas.
 * </p>
 * 
 * <table border="1">
 * <tr><th scope="col">Sigla</th><th scope="col">Descrição</th></tr>
 * <tr>
 * <td>MEI</td>
 * <td>"Microempreendedor Individual"</td>
 * </tr>
 * <tr>
 * <td>ME</td>
 * <td>"Microempresa"</td>
 * </tr>
 * <tr>
 * <td>EPP</td>
 * <td>"Empresas de Pequeno Porte"</td>
 * </tr>
 * <tr>
 * <td>EI</td>
 * <td>"Empresário Individual"</td>
 * </tr>
 * <tr>
 * <td>EIRELI</td>
 * <td>"Empresa Individual de Responsabilidade Limitada"</td>
 * </tr>
 * <tr>
 * <td>Sl</td>
 * <td>"Sociedade Limitada"</td>
 * </tr>
 * <tr>
 * <td>SA</td>
 * <td>"Sociedade Anônima"</td>
 * </tr>
 * </table>
 * 
 * @author Jhonny de Salles Noschang
 */
public enum TipoEmpresa {

	MEI("Microempreendedor Individual"), ME("Microempresa"), EPP("Empresas de Pequeno Porte"),
	EI("Empresário Individual"), EIRELI("Empresa Individual de Responsabilidade Limitada"), Sl("Sociedade Limitada"),
	SA("Sociedade Anônima");

	private String tipoEmpresa;

	TipoEmpresa(String tipoEmpresa) {
		this.tipoEmpresa = tipoEmpresa;
	}

	public String getDescricao() {
		return tipoEmpresa;
	}

	@Override
	public String toString() {
		return this.tipoEmpresa;
	}

}
