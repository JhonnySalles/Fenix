package servidor.dao.services;

import servidor.dao.EstadoDao;
import servidor.entities.Estado;

public class EstadoService extends GenericService<Estado> {

	public EstadoService() {
		super(Estado.class);
	}

	private EstadoDao service = new EstadoDao();

	@Override
	public EstadoDao getService() {
		return service;
	}

	public Estado pesquisar(String nomeORuf) {
		return service.pesquisar(nomeORuf);
	}

}
