package servidor.validations;

import comum.model.exceptions.ExcessaoBd;
import comum.model.exceptions.ExcessaoCadastro;
import comum.model.messages.Mensagens;
import servidor.dao.services.UsuarioService;
import servidor.entities.Usuario;

/**
 * <p>
 * Classe responssável por fazer a validação da classe usuária.
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
public class ValidaUsuario {

	private static UsuarioService service = new UsuarioService();

	public static boolean validaUsuario(Usuario usuario, Boolean mudouSenha) throws ExcessaoCadastro, ExcessaoBd {

		if (usuario == null)
			return false;

		validaNome(usuario.getNomeSobrenome());

		validaLogin(usuario);

		if (mudouSenha)
			validaSenha(usuario.getSenha());

		return true;
	}

	public static void validaNome(String nome) throws ExcessaoCadastro {
		if (nome == null || nome.isEmpty())
			throw new ExcessaoCadastro(Mensagens.CAD_USR_NOME_VAZIO);
	}

	public static void validaLogin(Usuario login) throws ExcessaoCadastro, ExcessaoBd {
		if (login.getLogin() == null || login.getLogin().isEmpty())
			throw new ExcessaoCadastro(Mensagens.CAD_USR_LOGIN_VAZIO);
		
		if (service.validaLogin(login.getId(), login.getLogin()))
			throw new ExcessaoCadastro(Mensagens.CAD_USR_LOGIN_UTILLIZADO);
	}

	public static void validaSenha(String senha) throws ExcessaoCadastro {
		if (senha == null || senha.isEmpty())
			throw new ExcessaoCadastro(Mensagens.CAD_USR_SENHA_VAZIA);
	}

}
