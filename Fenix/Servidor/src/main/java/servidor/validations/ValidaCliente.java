package servidor.validations;

import comum.model.constraints.Validadores;
import comum.model.enums.TipoPessoa;
import comum.model.exceptions.ExcessaoCadastro;
import comum.model.messages.Mensagens;
import servidor.dao.services.ClienteService;
import servidor.entities.Cliente;

/**
 * <p>
 * Classe responssável por fazer a validação da classe cliente.
 * </p>
 * 
 * <p>
 * Ficou definido estar propagando uma excessão para quando o campo a ser
 * validado for inválido, pois pode-se estar tratando com mensagens de aviso ou
 * colorindo a borda do campo correspondente.
 * </p>
 * 
 * 
 * @author Jhonny de Salles Noschang
 */
public class ValidaCliente {

	private static ClienteService service = new ClienteService();

	private static Cliente entidade;

	public static boolean validaCliente(Cliente cliente) throws ExcessaoCadastro {
		if (cliente == null)
			return false;

		validaNome(cliente.getNomeSobrenome());

		entidade = cliente;
		validaPessoa(cliente.getTipoPessoa(), cliente.getCnpj(), cliente.getCpf());

		validaRazaoSocial(cliente.getTipoPessoa(), cliente.getRazaoSocial());

		return true;
	}

	public static void validaNome(String nome) throws ExcessaoCadastro {
		if (nome == null || nome.isEmpty())
			throw new ExcessaoCadastro(Mensagens.CAD_CLI_NOME_VAZIO);
	}

	public static void validaPessoa(TipoPessoa pessoa, String cnpj, String cpf) throws ExcessaoCadastro {
		switch (pessoa) {
		case FISICO:
			validaCPF(cpf);
			break;
		case JURIDICO:
			validaCNPJ(cnpj);
			break;
		case AMBOS:
			validaCPF(cpf);
			validaCNPJ(cnpj);
			break;
		default:
			throw new ExcessaoCadastro(Mensagens.CAD_CLI_TIPO_CLIENTE_VAZIO);
		}
	}

	public static void validaCPF(String cpf) throws ExcessaoCadastro {
		if (!cpf.isEmpty() && !Validadores.validaCpf(cpf))
			throw new ExcessaoCadastro(Mensagens.CAD_CLI_CPF);

		if (!cpf.isEmpty()) {
			Cliente cliente = service.existeCPF(cpf);
			if (cliente != null) {
				if (entidade != null) {
					if (!entidade.getId().equals(cliente.getId()))
						throw new ExcessaoCadastro(String.format(Mensagens.CAD_CLI_EXISTE_CPF,
								cliente.getId() + " - " + cliente.getNomeSobrenome()));
				} else
					throw new ExcessaoCadastro(String.format(Mensagens.CAD_CLI_EXISTE_CPF,
							cliente.getId() + " - " + cliente.getNomeSobrenome()));
			}
		}
	}

	public static void validaCNPJ(String cnpj) throws ExcessaoCadastro {
		if (!cnpj.isEmpty() && !Validadores.validaCnpj(cnpj))
			throw new ExcessaoCadastro(Mensagens.CAD_CLI_CNPJ);

		if (!cnpj.isEmpty()) {
			Cliente cliente = service.existeCNPJ(cnpj);
			if (cliente != null) {
				if (entidade != null) {
					if (!entidade.getId().equals(cliente.getId()))
						throw new ExcessaoCadastro(String.format(Mensagens.CAD_CLI_EXISTE_CNPJ,
								cliente.getId() + " - " + cliente.getNomeSobrenome()));
				} else
					throw new ExcessaoCadastro(String.format(Mensagens.CAD_CLI_EXISTE_CNPJ,
							cliente.getId() + " - " + cliente.getNomeSobrenome()));
			}
		}
	}

	public static void validaRazaoSocial(TipoPessoa pessoa, String razaoSocial) throws ExcessaoCadastro {
		if (!pessoa.equals(TipoPessoa.FISICO) && razaoSocial.isEmpty())
			throw new ExcessaoCadastro(Mensagens.CAD_RAZAO_SOCIAL_VAZIA);
	}

}
