package servidor.dto;

import comum.model.entities.Cep;
import comum.model.exceptions.ExcessaoCep;
import comum.model.messages.Mensagens;
import servidor.dao.BairroDao;
import servidor.dao.CidadeDao;
import servidor.dao.EstadoDao;
import servidor.dao.PaisDao;
import servidor.entities.Bairro;
import servidor.entities.Cidade;
import servidor.entities.Endereco;
import servidor.entities.Estado;
import servidor.entities.Pais;

public class EnderecoDTO {

	private static PaisDao servicePais = new PaisDao();
	private static EstadoDao serviceEstado = new EstadoDao();
	private static CidadeDao serviceCidade = new CidadeDao();
	private static BairroDao serviceBairro = new BairroDao();

	public static Endereco toEndereco(Cep cep) throws ExcessaoCep {
		if (cep == null)
			throw new ExcessaoCep(Mensagens.CEP_VAZIO);

		Bairro bairro = serviceBairro.pesquisar(cep.getBairro().trim());

		if (bairro == null) {
			Cidade cidade = serviceCidade.pesquisar(cep.getCidade().trim());

			if (cidade == null) {
				Estado estado = serviceEstado.pesquisar(cep.getEstado().trim());

				if (estado == null) {
					Pais pais = servicePais.brasil();

					estado = new Estado(cep.getEstado().trim(), cep.getUf().trim(), pais);
					serviceEstado.salvarAtomico(estado);
				}

				cidade = new Cidade(cep.getCidade().trim(), "", estado);
				cidade.setNome(cep.getCidade().trim());
				serviceCidade.salvarAtomico(cidade);
			}

			bairro = new Bairro(0L, cep.getBairro().trim(), cidade);
			serviceBairro.salvarAtomico(bairro);
		}

		return new Endereco(bairro, cep.getLogradouro().trim(), "");
	}

}
