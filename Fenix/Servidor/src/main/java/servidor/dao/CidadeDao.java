package servidor.dao;

import javax.persistence.TypedQuery;

import servidor.entities.Cidade;

public class CidadeDao extends Dao<Cidade> {

	final static String SELECT_CIDADE = "SELECT u FROM " + Cidade.class.getName() + " u WHERE UPPER(Nome) = '%s'";

	public Cidade pesquisar(String nome) {
		TypedQuery<Cidade> query = em.createQuery(String.format(SELECT_CIDADE, nome.toUpperCase()), Cidade.class);
		return query.getResultList().get(0);
	};
	
	public CidadeDao() {
		super(Cidade.class);
	};

}
