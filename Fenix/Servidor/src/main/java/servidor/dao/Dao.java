package servidor.dao;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.persistence.TypedQuery;

import comum.model.entities.Entidade;
import servidor.annotation.Sugestao;
import servidor.configuration.ManagerFactory;

public class Dao<E extends Entidade> {

	final static String DELETE = "UPDATE %s SET Situacao = 'EXCLUIDO' WHERE id = %d";
	final static String SELECT = "SELECT e FROM %s e WHERE Situacao <> 'EXCLUIDO' ";
	final static String SUGESTAO = "SELECT e FROM %s e WHERE Situacao <> 'EXCLUIDO' AND %s LIKE '%s' ";
	final static String PROXIMO_ID = "SELECT IFNULL(MAX(id), 0) + 1 AS id FROM %s e ";

	protected EntityManager em;
	private Class<E> classe;
	private E lastEntity;
	private List<E> lastList;

	public EntityManager getEntityManager() {
		return em;
	}

	public E getLastEntity() {
		return lastEntity;
	}

	public List<E> getLastList() {
		return lastList;
	}

	protected String getTabela() {
		// Para pesquisa deve-se retornar o nome da classe.
		return classe.getName();
	}

	protected String getTabelaNome() {
		return classe.getAnnotation(Table.class).name();
	}

	public Dao() {
		this(null);
	}

	public Dao(Class<E> classe) {
		this.classe = classe;
		em = ManagerFactory.getEtityManager();
	}

	public Dao<E> begin() {
		// Caso estorou algum erro na transação, ao tentar abrir uma nova ele
		// apresenta erro, neste caso se estiver aberta assume que algum problema
		// ocorreu
		// e efetua um rollback para a próxima transação.
		if (em.getTransaction().isActive())
			em.getTransaction().rollback();

		em.getTransaction().begin();
		return this;
	}

	public Dao<E> commit() {
		em.getTransaction().commit();
		return this;
	}

	public Dao<E> rolback() {
		em.getTransaction().rollback();
		return this;
	}

	public Dao<E> salvar(E entidade) throws EntityExistsException {
		if (entidade.getId().compareTo(0L) == 0)
			em.persist(entidade);
		else
			em.merge(entidade);

		em.detach(entidade);
		lastEntity = entidade;
		return this;
	}

	public Dao<E> salvar(List<E> lista) throws EntityExistsException {
		for (E entidade : lista) {
			if (entidade.getId().compareTo(0L) == 0)
				em.persist(entidade);
			else
				em.merge(entidade);
			em.detach(entidade);
		}
		lastList = lista;
		return this;
	}

	public Dao<E> salvarAtomico(E entidade) throws EntityExistsException {
		return this.begin().salvar(entidade).commit();
	}

	public Dao<E> salvarAtomico(List<E> lista) throws EntityExistsException {
		return this.begin().salvar(lista).commit();
	}

	public Dao<E> remover(Long id) {
		Query querry = em.createNativeQuery(String.format(DELETE, getTabelaNome(), id));
		querry.executeUpdate();
		return this;
	}

	public Dao<E> removerAtomico(Long id) {
		return this.begin().remover(id).commit();
	}

	public Dao<E> remover(E entidade) {
		lastEntity = entidade;
		remover(entidade.getId());
		return this;
	}

	public Dao<E> removerAtomico(E entidade) {
		return this.begin().remover(entidade).commit();
	}

	public E pesquisar(Long id) {
		if (classe == null)
			throw new UnsupportedOperationException("Classe nula, deve-se informar a classe no construtor.");

		E entidade = em.find(classe, id);

		if (entidade == null)
			return entidade;

		// Desfaz o vinculo do hibernate para que não seja refletido ao banco a
		// alteração na classe.
		em.detach(entidade);
		return entidade;
	};

	public List<E> listar() {
		return listar(-1, -1);
	}

	public List<E> listar(int registros) {
		return listar(0, registros);
	}

	public List<E> listar(int primeiroRegistro, int registros) {
		if (classe == null)
			throw new UnsupportedOperationException("Classe nula, deve-se informar a classe no construtor.");

		em.clear();
		TypedQuery<E> query = em.createQuery(String.format(SELECT, getTabela()), classe);

		if (registros >= 0)
			query.setMaxResults(registros);

		if (primeiroRegistro >= 0)
			query.setFirstResult(primeiroRegistro);

		return query.getResultList();
	}

	public List<E> suegestao(String texto) {
		if (classe == null)
			throw new UnsupportedOperationException("Classe nula, deve-se informar a classe no construtor.");

		if (classe.getAnnotation(Sugestao.class) == null || classe.getAnnotation(Sugestao.class).campo().isEmpty())
			throw new UnsupportedOperationException("Não informado a anotação de sugestão para filtrar a classe.");

		em.clear();
		String pesquisa = String.format(SUGESTAO, getTabela(), classe.getAnnotation(Sugestao.class).campo(),
				texto + "%");
		TypedQuery<E> query = em.createQuery(pesquisa, classe);

		return query.getResultList();
	}

	public String proximoId() {
		if (classe == null)
			throw new UnsupportedOperationException("Classe nula, deve-se informar a classe no construtor.");

		em.clear();
		String pesquisa = String.format(PROXIMO_ID, getTabela());
		TypedQuery<Integer> query = em.createQuery(pesquisa, Integer.class);

		return query.getSingleResult().toString();
	}

	public void close() {
		em.close();
	}

}
