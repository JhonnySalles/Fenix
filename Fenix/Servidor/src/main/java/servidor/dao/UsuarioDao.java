package servidor.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import servidor.entities.Usuario;

public class UsuarioDao extends Dao<Usuario> {

	final static String SELECT_LISTA_LOGIN = "SELECT login FROM " + Usuario.class.getName()
			+ " u WHERE SITUACAO = 'ATIVO' AND Login <> 'ADMINISTRATOR' ";
	final static String SELECT_LOGIN = "SELECT u FROM " + Usuario.class.getName()
			+ " u WHERE Login = '%s' AND SITUACAO = 'ATIVO' ";
	final static String SELECT_ALL = "SELECT u FROM " + Usuario.class.getName()
			+ " u WHERE Situacao = 'ATIVO' AND Login <> 'ADMINISTRATOR'";
	final static String SELECT_EXISTE_LOGIN = "SELECT count(*) FROM " + Usuario.class.getName()
			+ " u WHERE Login = '%s' AND ID <> %d";
	final static String SELECT_PRIMEIRO_USUARIO = "SELECT count(*) FROM " + Usuario.class.getName()
			+ " u WHERE Login <> 'ADMINISTRATOR'";

	public Boolean validaLogin(Long id, String login) {
		String query = String.format(SELECT_EXISTE_LOGIN, login.toUpperCase(), id);
		TypedQuery<Long> result = em.createQuery(query, Long.class);
		result.setMaxResults(1);
		return result.getSingleResult().compareTo(0L) > 0;
	}

	public Usuario pesquisar(String login) {
		String query = String.format(SELECT_LOGIN, login.toUpperCase());
		TypedQuery<Usuario> result = em.createQuery(query, Usuario.class);
		return result.getSingleResult();
	};

	public List<Usuario> pesquisarTodos() {
		return em.createQuery(SELECT_ALL, Usuario.class).getResultList();
	};

	public List<String> carregaLogins() {
		TypedQuery<String> query = em.createQuery(SELECT_LISTA_LOGIN, String.class);
		return query.getResultList();
	};
	
	public Boolean isPrimeiroRegistro() {
		TypedQuery<Long> query = em.createQuery(SELECT_PRIMEIRO_USUARIO, Long.class);
		return query.getSingleResult().compareTo(0L) == 0;
	}

	public UsuarioDao() {
		super(Usuario.class);
	};
}
