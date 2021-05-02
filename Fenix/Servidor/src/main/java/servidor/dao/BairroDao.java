package servidor.dao;

import javax.persistence.TypedQuery;

import servidor.entities.Bairro;

public class BairroDao extends Dao<Bairro> {

	final static String SELECT_BAIRRO = "SELECT u FROM " + Bairro.class.getName() + " u WHERE UPPER(Nome) = '%s'";

	public Bairro pesquisar(String nome) {
		TypedQuery<Bairro> query = em.createQuery(String.format(SELECT_BAIRRO, nome.toUpperCase()), Bairro.class);
		return query.getSingleResult();
	};

	public BairroDao() {
		super(Bairro.class);
	};	
}
