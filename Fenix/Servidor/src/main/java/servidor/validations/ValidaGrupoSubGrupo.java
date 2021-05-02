package servidor.validations;

import comum.model.exceptions.ExcessaoCadastro;
import comum.model.messages.Mensagens;
import servidor.entities.GrupoBase;

/**
 * <p>
 * Classe responssável por fazer a validação da classe grupo ou sub grupo.
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
public class ValidaGrupoSubGrupo {

	public static boolean validaGrupoSubGrupo(GrupoBase grupo) throws ExcessaoCadastro {

		if (grupo == null)
			return false;

		validaDescricao(grupo.getDescricao());

		return true;
	}

	public static void validaDescricao(String nome) throws ExcessaoCadastro {
		if (nome == null || nome.isEmpty())
			throw new ExcessaoCadastro(Mensagens.CAD_GRUPO_DESCRICAO_VAZIO);
	}

}
