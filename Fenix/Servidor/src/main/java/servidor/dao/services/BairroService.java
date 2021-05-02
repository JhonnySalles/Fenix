package servidor.dao.services;

import servidor.dao.BairroDao;
import servidor.entities.Bairro;

public class BairroService extends GenericService<Bairro> {

	public BairroService() {
		super(Bairro.class);
	}

	private BairroDao service = new BairroDao();

	@Override
	public BairroDao getService() {
		return service;
	}

	public Bairro pesquisar(String nome) {
		return service.pesquisar(nome);
	}

}
