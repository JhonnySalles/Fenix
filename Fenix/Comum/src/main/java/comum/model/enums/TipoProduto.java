package comum.model.enums;

/**
 * <p>Enuns de tipos de produtos.</p>
 * 
 * <table border="1">
 * <tr><th scope="col">ENUM</th><th scope="col">Descrição</th></tr>
 * <tr>
 * <td>PRODUTO</td>
 * <td>Produto final</td>
 * </tr>
 * <tr>
 * <td>PRODUZIDO</td>
 * <td>Produzido</td>
 * </tr>
 * <tr>
 * <td>MATERIAPRIMA</td>
 * <td>Matéria prima</td>
 * </tr>
 * <tr>
 * <td>SERVICO</td>
 * <td>Serviço</td>
 * </tr>
 * </table>
 * @author Jhonny de Salles Noschang
 */
public enum TipoProduto {

	PRODUTOFINAL("Produto"), PRODUZIDO("Produzido"), MATERIAPRIMA("Matéria prima"), SERVICO("Serviço");

	private String tipoProduto;

	TipoProduto(String tipoProduto) {
		this.tipoProduto = tipoProduto;
	}

	public String getDescricao() {
		return tipoProduto;
	}
	
	@Override      
	public String toString(){
	    return this.tipoProduto;
	} 
}
