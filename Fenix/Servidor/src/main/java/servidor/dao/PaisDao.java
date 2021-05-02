package servidor.dao;

import javax.persistence.TypedQuery;

import servidor.entities.Pais;

public class PaisDao extends Dao<Pais> {

	final static String SELECT_PAIS = "SELECT u FROM " + Pais.class.getName() + " u WHERE UPPER(Nome) = '%s'";
	final static String SELECT_BRASIL = "SELECT u FROM " + Pais.class.getName() + " u WHERE UPPER(Nome) = 'BRASIL'";

	public Pais pesquisar(String nome) {
		TypedQuery<Pais> query = em.createQuery(String.format(SELECT_PAIS, nome.toUpperCase()), Pais.class);
		return query.getSingleResult();
	};

	public Pais brasil() {
		TypedQuery<Pais> query = em.createQuery(SELECT_BRASIL, Pais.class);
		return query.getSingleResult();
	}

	public PaisDao() {
		super(Pais.class);
	};
}
