package servidor.dao.services;

import java.util.List;

import servidor.dao.CidadeDao;
import servidor.entities.Cidade;

public class CidadeService extends GenericService<Cidade> {

	public CidadeService() {
		super(Cidade.class);
	}

	private CidadeDao service = new CidadeDao();

	@Override
	public CidadeDao getService() {
		return service;
	}

	public Cidade salvar(Cidade cidade) {
		service.salvarAtomico(cidade);
		return cidade;
	}

	public void deletar(Long id) {
		service.removerAtomico(id);
	}

	public Cidade pesquisar(String nome) {
		return service.pesquisar(nome);
	}

	public Cidade pesquisar(Long id) {
		return service.pesquisar(id);
	}

	public List<Cidade> listar() {
		return service.listar();
	}

}
