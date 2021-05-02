package servidor.validations;

import comum.model.constraints.Validadores;
import comum.model.exceptions.ExcessaoCadastro;
import comum.model.messages.Mensagens;
import servidor.entities.Produto;

/**
 * <p>
 * Classe responssável por fazer a validação da classe cliente.
 * </p>
 * 
 * <p>
 * Ficou definido estar propagando uma excessão para quando o campo a ser
 * validado for inválido, pois pode-se estar tratando com mensagens de aviso ou
 * colorindo a borda do campo correspondente.
 * </p>
 * 
 * 
 * @author Jhonny de Salles Noschang
 */
public class ValidaProduto {

	public static boolean validaProduto(Produto produto) throws ExcessaoCadastro {

		if (produto == null)
			return false;

		validaDescricao(produto.getDescricao());

		validaCodigoBarras(produto.getCodigoBarras());

		return true;
	}

	public static void validaDescricao(String nome) throws ExcessaoCadastro {
		if (nome == null || nome.isEmpty())
			throw new ExcessaoCadastro(Mensagens.CAD_PROD_DESCRICAO_VAZIO);
	}

	public static void validaCodigoBarras(String codigoBarras) throws ExcessaoCadastro {
		if (codigoBarras != null && codigoBarras.isEmpty())
			if (!Validadores.validaEAN(codigoBarras))
				throw new ExcessaoCadastro(Mensagens.CAD_PROD_CODIGO_BARRAS);
	}

}
