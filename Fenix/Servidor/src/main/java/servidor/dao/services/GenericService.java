package servidor.dao.services;

import java.util.List;

import comum.model.entities.Entidade;
import servidor.dao.Dao;

public class GenericService<E extends Entidade> {

	private Dao<E> service;

	public Dao<E> getService() {
		return service;
	}

	public GenericService(Class<E> classe) {
		this.service = new Dao<E>(classe);
	}

	public E salvar(E entidade) {
		return service.salvarAtomico(entidade).getLastEntity();
	}
	
	public List<E> salvar(List<E> entidade) {
		return service.salvarAtomico(entidade).getLastList();
	}

	public void deletar(Long id) {
		service.removerAtomico(id);
	}

	public E pesquisar(Long id) {
		return service.pesquisar(id);
	}

	public List<E> listar() {
		return service.listar();
	}

	public List<E> sugestao(String texto) {
		return service.suegestao(texto);
	}

	public String proximoId() {
		return service.proximoId();
	}

}
