package servidor.validations;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import comum.model.encode.DecodeHash;
import comum.model.exceptions.ExcessaoBd;
import comum.model.exceptions.ExcessaoLogin;
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
public class ValidaLogin {

	private static UsuarioService service = new UsuarioService();
	private static Usuario localizado;

	public static Usuario validaLogin(Usuario usuario) throws ExcessaoLogin, ExcessaoBd {
		localizado = null;

		if (usuario.getSenha().isEmpty())
			throw new ExcessaoLogin(Mensagens.LOGIN_ERRO_USER_SENHA_VAZIA);

		validaLogin(usuario.getLogin());

		validaSenha(usuario);

		return localizado;
	}

	public static void validaLogin(String login) throws ExcessaoLogin {
		if (login.isEmpty())
			throw new ExcessaoLogin(Mensagens.LOGIN_ERRO_USER_LOGIN);

		localizado = service.pesquisar(login);

		if (localizado == null)
			throw new ExcessaoLogin(Mensagens.LOGIN_ERRO_USER_LOGIN_NAO_ENCONTRADO);
	}

	public static void validaSenha(Usuario usuario) throws ExcessaoBd, ExcessaoLogin {
		try {
			usuario.setSenha(DecodeHash.CriptografaSenha(usuario.getSenha().trim()));
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			localizado = null;
			e.printStackTrace();
			throw new ExcessaoLogin(Mensagens.LOGIN_ERRO_USER_SENHA);
		}

		if (!usuario.getSenha().equalsIgnoreCase(localizado.getSenha())) {
			localizado = null;
			throw new ExcessaoLogin(Mensagens.LOGIN_ERRO_USER_SENHA);
		}
	}

}
