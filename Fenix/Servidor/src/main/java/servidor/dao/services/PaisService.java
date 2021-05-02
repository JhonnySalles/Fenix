package servidor.dao.services;

import servidor.dao.PaisDao;
import servidor.entities.Pais;

public class PaisService extends GenericService<Pais> {

	public PaisService() {
		super(Pais.class);
	}

	private PaisDao service = new PaisDao();

	@Override
	public PaisDao getService() {
		return service;
	}

	public Pais pesquisar(String nome) {
		return service.pesquisar(nome);
	}

	public Pais brasil() {
		return service.brasil();
	}
}
