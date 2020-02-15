package administrador.model.dao.services;

import java.util.List;

import administrador.model.dao.ClienteDao;
import administrador.model.dao.DaoFactory;
import administrador.model.entities.Cliente;
import comum.model.exceptions.ExcessaoBd;

public class ClienteServices {

	private ClienteDao clienteDao = DaoFactory.createClienteDao();

	public void salvar(Cliente cliente) throws ExcessaoBd {
		if (cliente.getId() != null && cliente.getId() != 0)
			clienteDao.update(cliente);
		else
			clienteDao.insert(cliente);
	}

	public void deletar(Long id) throws ExcessaoBd {
		clienteDao.delete(id);
	};

	public Cliente pesquisar(Long id) throws ExcessaoBd {
		return clienteDao.find(id);
	};

	public List<Cliente> pesquisarTodos() throws ExcessaoBd {
		return clienteDao.findAll();
	};

}
