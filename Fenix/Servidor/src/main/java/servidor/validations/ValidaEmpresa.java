package servidor.validations;

import comum.model.constraints.Validadores;
import comum.model.exceptions.ExcessaoCadastro;
import comum.model.messages.Mensagens;
import servidor.entities.Empresa;

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
public class ValidaEmpresa {

	public static boolean validaEmpresa(Empresa empresa) throws ExcessaoCadastro {

		if (empresa == null)
			return false;

		validaRazaoSocial(empresa.getRazaoSocial());
		
		validaNomeFantasia(empresa.getNomeFantasia());

		validaCNPJ(empresa.getCnpj());

		return true;
	}

	public static void validaRazaoSocial(String nome) throws ExcessaoCadastro {
		if (nome == null || nome.isEmpty())
			throw new ExcessaoCadastro(Mensagens.CAD_EMP_RAZAO_VAZIO);
	}

	public static void validaNomeFantasia(String nome) throws ExcessaoCadastro {
		if (nome == null || nome.isEmpty())
			throw new ExcessaoCadastro(Mensagens.CAD_EMP_FANTASIA_VAZIO);
	}

	public static void validaCNPJ(String cnpj) throws ExcessaoCadastro {
		if (cnpj != null && !cnpj.isEmpty())
			if (!Validadores.validaCnpj(cnpj))
				throw new ExcessaoCadastro(Mensagens.CAD_CLI_CNPJ);
	}

}
