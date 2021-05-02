package servidor.dao;

import javax.persistence.TypedQuery;

import servidor.entities.Estado;
import servidor.entities.Pais;

public class EstadoDao extends Dao<Estado> {

	final static String SELECT_ESTADO = "SELECT u FROM " + Pais.class.getName()
			+ " u WHERE UPPER(Nome) = '%s' OR UPPER(Sigla) = '%s'";

	/**
	 * <p>
	 * A pesquisa pode ser realizado por nome ou pela UF.
	 * </p>
	 * 
	 * @author Jhonny de Salles Noschang
	 */
	public Estado pesquisar(String nomeORuf) {
		TypedQuery<Estado> query = em.createQuery(
				String.format(SELECT_ESTADO, nomeORuf.toUpperCase(), nomeORuf.toUpperCase()), Estado.class);
		return query.getSingleResult();
	};

	public EstadoDao() {
		super(Estado.class);
	};

}
