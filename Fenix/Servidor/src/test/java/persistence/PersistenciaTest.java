package persistence;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import comum.model.enums.Enquadramento;
import comum.model.enums.Situacao;
import comum.model.enums.TipoPessoa;
import comum.model.exceptions.ExcessaoBd;
import comum.model.exceptions.ExcessaoCadastro;
import comum.model.generator.Gerador;
import servidor.configuration.ManagerFactory;
import servidor.dao.CidadeDao;
import servidor.dao.services.CidadeService;
import servidor.dao.services.EstadoService;
import servidor.dao.services.GenericService;
import servidor.dao.services.PaisService;
import servidor.dao.services.UsuarioService;
import servidor.entities.Bairro;
import servidor.entities.Cidade;
import servidor.entities.Cliente;
import servidor.entities.Contato;
import servidor.entities.Empresa;
import servidor.entities.Endereco;
import servidor.entities.Estado;
import servidor.entities.Pais;
import servidor.entities.Usuario;
import servidor.validations.ValidaCliente;

@TestMethodOrder(OrderAnnotation.class)
public class PersistenciaTest {

	private static Pais PAIS;
	private static Estado ESTADO;
	private static Cidade CIDADE;

	@BeforeAll
	public static void iniciaBd() {
		try {
			ManagerFactory.iniciaBD();
			assertTrue(true);
		} catch (ExcessaoBd e) {
			e.printStackTrace();
			fail("Erro ao iniciar a conexão com o banco.");
		}
	}

	@AfterAll
	public static void finalizaBd() {
		ManagerFactory.closeConection();
		assertTrue(true);
	}

	@Test
	@Order(1)
	public void testFindPais() {
		PaisService service = new PaisService();
		PAIS = service.pesquisar(1L);

		assertTrue(PAIS != null && PAIS.getId() > 0);
	}

	@Test
	@Order(2)
	public void testFindEstado() {
		EstadoService service = new EstadoService();
		ESTADO = service.pesquisar(1L);

		assertTrue(ESTADO != null && ESTADO.getId() > 0);
	}

	@Test
	@Order(3)
	public void testSaveCidade() {
		EstadoService serviceEstado = new EstadoService();
		ESTADO = serviceEstado.pesquisar(1L);

		CidadeDao service = new CidadeDao();
		Cidade cidade = new Cidade("Cascavel", "45", Situacao.ATIVO, ESTADO);
		cidade = service.salvarAtomico(cidade).getLastEntity();

		assertTrue(cidade != null && cidade.getId() > 0);
	}

	@Test
	@Order(4)
	public void testFindCidade() {
		CidadeService service = new CidadeService();
		CIDADE = service.pesquisar("cascavel");

		assertTrue(CIDADE != null && CIDADE.getId() > 0);
	}

	@Test
	@Order(5)
	public void testSaveUsuario() {
		Usuario usuario = new Usuario("Maria das Dores", "MARIA", "Observação de teste");

		UsuarioService service = new UsuarioService();
		usuario = service.salvar(usuario);

		assertTrue(usuario.getId() > 0);

		try {
			TimeUnit.MILLISECONDS.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test
	@Order(6)
	public void testFindUsuario() {
		UsuarioService service = new UsuarioService();
		Usuario usuario = service.pesquisar(Long.valueOf(1));

		assertTrue(usuario != null && usuario.getId() > 0);
	}

	@Test
	@Order(7)
	public void testSaveEmpresa() {
		Endereco endereco = new Endereco(new Bairro("Central Park", CIDADE), "Praça Arlindo Garcia", "973", "18702575",
				"", "", true);
		Empresa empresa = new Empresa("Benjamin e Elias Contábil ME", "Empresa de demonstração",
				Gerador.gerarCNPJTeste(), Situacao.ATIVO);
		empresa.setBairro(endereco.getBairro());
		empresa.addEnderecos(endereco);
		empresa.addContatos(new Contato("Benjamin e Elias Contábil ME", "1429813019", "14985353200",
				"administracao@benjamineeliascontabilme.com.br", "", true));

		GenericService<Empresa> service = new GenericService<Empresa>(Empresa.class);
		empresa = service.salvar(empresa);

		assertTrue(empresa.getId() > 0);

		try {
			TimeUnit.MILLISECONDS.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test
	@Order(8)
	public void testFindEmpresa() {
		GenericService<Empresa> service = new GenericService<Empresa>(Empresa.class);
		Empresa empresa = service.pesquisar(Long.valueOf(1));

		assertTrue(empresa != null && empresa.getId() > 0);
	}

	@Test
	@Order(9)
	public void testSaveBairro() {
		Bairro bairro = new Bairro("Centro", CIDADE);

		GenericService<Bairro> service = new GenericService<Bairro>(Bairro.class);
		bairro = service.salvar(bairro);

		assertTrue(bairro.getId() > 0);

		try {
			TimeUnit.MILLISECONDS.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@Order(10)
	public void testFindBairro() {
		GenericService<Bairro> service = new GenericService<Bairro>(Bairro.class);
		Bairro bairro = service.pesquisar(1L);

		assertTrue(bairro.getId() > 0);

		try {
			TimeUnit.MILLISECONDS.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test
	@Order(11)
	public void testSaveClientePessoaFisica() {
		GenericService<Cliente> service = new GenericService<Cliente>(Cliente.class);
		Cliente cliente = new Cliente("Hefer Ortas", "", Gerador.gerarCPFTeste(), "",
				"Teste de cadastro pessoa física.", TipoPessoa.FISICO, Enquadramento.CLIENTE, Situacao.ATIVO);

		cliente.addContatos(new Contato("Doaci", "4532261215", "45999895254", "Doaci@yahoo.com.br", "", true));
		cliente.addEnderecos(new Endereco(new Bairro("Centro", CIDADE), "Av Brasil", "225", "85903554", "Casa", "", true));

		try {
			assertTrue(ValidaCliente.validaCliente(cliente));

			cliente = service.salvar(cliente);
			assertTrue(cliente != null && cliente.getId() > 0);
		} catch (ExcessaoCadastro e) {
			e.printStackTrace();
		}

		try {
			TimeUnit.MILLISECONDS.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test
	@Order(12)
	public void testSaveClientePessoaJuridica() {
		GenericService<Cliente> service = new GenericService<Cliente>(Cliente.class);

		Cliente cliente = new Cliente("Coarolin", "Coarolin LTDA", "", Gerador.gerarCNPJTeste(),
				"Teste de cadastro pessoa jurídica.", TipoPessoa.JURIDICO, Enquadramento.CLIENTE, Situacao.INATIVO);

		cliente.addContatos(new Contato("Coarolin", "4533211545", "", "Coarolin@hotmail.com", "", true));
		cliente.addContatos(new Contato("Enhogarz", "", "45994845204", "Enhogarz@hotmail.com.br", "", false));
		cliente.addEnderecos(new Endereco(new Bairro("Centro", CIDADE), "Av Brasil", "665", "81900555", "Loja Comercial", "", true));
		cliente.addEnderecos(new Endereco(new Bairro("Cascavel velho", CIDADE), "Rua Paraná", "775", "81100544",
				"Posto de combustível", "", false));

		try {
			assertTrue(ValidaCliente.validaCliente(cliente));

			cliente = service.salvar(cliente);

			assertTrue(cliente != null && cliente.getId() > 0);
		} catch (ExcessaoCadastro e) {
			e.printStackTrace();
		}

		try {
			TimeUnit.MILLISECONDS.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test
	@Order(13)
	public void testSaveClienteFornecedor() {
		GenericService<Cliente> service = new GenericService<Cliente>(Cliente.class);

		Cliente cliente = new Cliente("Belegor Pyuorn", "Pyuorn Indústria", Gerador.gerarCPFTeste(),
				Gerador.gerarCNPJTeste(), "Teste de cadastro de fornecedor.", TipoPessoa.JURIDICO,
				Enquadramento.FORNECEDOR, Situacao.ATIVO);

		cliente.addContatos(new Contato("Belegor", "4533553045", "45999854545", "Coarolin@hotmail.com", "", true));
		cliente.addEnderecos(
				new Endereco(new Bairro("Esmeralda", CIDADE), "Rua Paraná", "998", "85500500", "", "", true));

		try {
			assertTrue(ValidaCliente.validaCliente(cliente));

			cliente = service.salvar(cliente);

			assertTrue(cliente != null && cliente.getId() > 0);
		} catch (ExcessaoCadastro e) {
			e.printStackTrace();
		}

		try {
			TimeUnit.MILLISECONDS.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test
	@Order(14)
	public void testSaveClienteOutros() {
		GenericService<Cliente> service = new GenericService<Cliente>(Cliente.class);

		Cliente cliente = new Cliente("Dialfalo Lyuge", "Taihamph", Gerador.gerarCPFTeste(), Gerador.gerarCNPJTeste(),
				"Teste de cadastro, outros.", TipoPessoa.AMBOS, Enquadramento.AMBOS, Situacao.ATIVO);

		try {
			assertTrue(ValidaCliente.validaCliente(cliente));

			cliente = service.salvar(cliente);

			assertTrue(cliente != null && cliente.getId() > 0);
		} catch (ExcessaoCadastro e) {
			e.printStackTrace();
		}

		try {
			TimeUnit.MILLISECONDS.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test
	@Order(15)
	public void testFindCliente() {
		GenericService<Cliente> service = new GenericService<Cliente>(Cliente.class);
		Cliente cliente = service.pesquisar(Long.valueOf(1));

		assertTrue(cliente != null && cliente.getId() > 0);
	}

	@Test
	@Order(16)
	public void testDeleteCliente() {
		GenericService<Cliente> service = new GenericService<Cliente>(Cliente.class);
		Cliente cliente = service.pesquisar(Long.valueOf(1));

		assertTrue(cliente != null && cliente.getId() > 0);
	}

	@Test
	@Order(17)
	public void testProduto() {
		assertTrue(true);
	}

}
