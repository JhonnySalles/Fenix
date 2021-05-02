package servidor.validations;

import comum.model.constraints.Validadores;
import comum.model.exceptions.ExcessaoCadastro;
import comum.model.messages.Mensagens;
import servidor.entities.Contato;

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
public class ValidaContato {

	public static boolean validaContato(Contato contato) throws ExcessaoCadastro {

		if (contato == null)
			return false;

		validaNome(contato.getNomeSobrenome());

		validaCelular(contato.getCelular());

		validaTelefone(contato.getTelefone());

		validaEmail(contato.getEmail());

		if (contato.getCelular().isEmpty() && contato.getTelefone().isEmpty() && contato.getEmail().isEmpty())
			throw new ExcessaoCadastro(Mensagens.CAD_CONTATO_VAZIO);

		return true;
	}

	public static void validaNome(String nome) throws ExcessaoCadastro {
		if (nome == null || nome.isEmpty())
			throw new ExcessaoCadastro(Mensagens.CAD_CONTATO_NOME_VAZIO);
	}

	public static void validaCelular(String fone) throws ExcessaoCadastro {
		if (fone != null && !fone.isEmpty())
			if (!Validadores.validaTelefone(fone))
				throw new ExcessaoCadastro(Mensagens.CAD_CONTATO_CELULAR);
	}

	public static void validaTelefone(String fone) throws ExcessaoCadastro {
		if (fone != null && !fone.isEmpty())
			if (!Validadores.validaTelefone(fone))
				throw new ExcessaoCadastro(Mensagens.CAD_CONTATO_TELEFONE);
	}

	public static void validaEmail(String email) throws ExcessaoCadastro {
		if (email != null && !email.isEmpty())
			if (!Validadores.validaEmail(email))
				throw new ExcessaoCadastro(Mensagens.CAD_CONTATO_EMAIL);
	}

}
