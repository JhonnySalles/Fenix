package servidor.dao.services;

import java.util.List;

import comum.model.enums.Situacao;
import comum.model.enums.UsuarioNivel;
import servidor.dao.UsuarioDao;
import servidor.entities.Usuario;

public class UsuarioService extends GenericService<Usuario> {

	public UsuarioService() {
		super(Usuario.class);
	}

	private UsuarioDao service = new UsuarioDao();

	@Override
	public UsuarioDao getService() {
		return service;
	}
	
	public Usuario cadastro(Usuario entidade) {
		if (service.isPrimeiroRegistro()) {
			entidade.setSituacao(Situacao.ATIVO);
			entidade.setNivel(UsuarioNivel.ADMINISTRADOR);
		} else {
			entidade.setSituacao(Situacao.INATIVO);
			entidade.setNivel(UsuarioNivel.USUARIO);
		}
		
		service.salvarAtomico(entidade);
		return entidade;
	}

	public Boolean validaLogin(Long id, String login) {
		return service.validaLogin(id, login);
	}

	public Usuario pesquisar(String login) {
		return service.pesquisar(login);
	}

	@Override
	public List<Usuario> listar() {
		return service.pesquisarTodos();
	}

	public List<String> carregaLogins() {
		return service.carregaLogins();
	}

}
