package servidor.entities;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Where;

import comum.model.entities.Entidade;
import comum.model.enums.Enquadramento;
import comum.model.enums.Situacao;
import comum.model.enums.TipoPessoa;

@Entity
@Table(name = "clientes", uniqueConstraints = { @UniqueConstraint(columnNames = { "CPF" }, name = "UK_CLIENTE_CPF"),
		@UniqueConstraint(columnNames = { "CNPJ" }, name = "UK_CLIENTE_CNPJ") })
public class Cliente extends Pessoa implements Entidade {

	public static final String TABELA = "clientes";

	// Utilizado para poder ser transformado em sequencia de bytes
	// e poder então trafegar os dados em rede ou salvar em arquivo.
	private static final long serialVersionUID = 6989181117327049412L;

	@Column(name = "RazaoSocial", nullable = true)
	private String razaoSocial;

	@Column(name = "CPF", unique = true, nullable = true, insertable = true, updatable = true, length = 15, columnDefinition = "varchar(15)")
	private String cpf;

	@Column(name = "CNPJ", unique = true, nullable = true, insertable = true, updatable = true, length = 15, columnDefinition = "varchar(15)")
	private String cnpj;

	@Column(name = "Observacao", columnDefinition = "longtext")
	private String observacao;

	@Column(name = "Tipo", columnDefinition = "enum('FISICO','JURIDICO','AMBOS')")
	@Enumerated(EnumType.STRING)
	private TipoPessoa tipoPessoa;

	@Column(name = "Enquadramento", columnDefinition = "enum('CLIENTE','FORNECEDOR','AMBOS')")
	@Enumerated(EnumType.STRING)
	private Enquadramento enquadramento;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "IdCliente", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_CLIENTE_CONTATO"))
	@Where(clause = "Situacao <> 'EXCLUIDO'")
	@Cascade(CascadeType.ALL)
	private Set<Contato> contatos = new HashSet<Contato>();

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "IdCliente", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_CLIENTE_ENDERECO"))
	@Where(clause = "Situacao <> 'EXCLUIDO'")
	@Cascade(CascadeType.ALL)
	private Set<Endereco> enderecos = new HashSet<Endereco>();

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = (cpf != null && !cpf.isEmpty()) ? cpf : null;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = (cnpj != null && !cnpj.isEmpty()) ? cnpj : null;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public TipoPessoa getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(TipoPessoa tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public Enquadramento getEnquadramento() {
		return enquadramento;
	}

	public void setEnquadramento(Enquadramento enquadramento) {
		this.enquadramento = enquadramento;
	}

	public Set<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(Set<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public void addEnderecos(Endereco enderecos) {
		this.enderecos.add(enderecos);
	}

	public Set<Contato> getContatos() {
		return contatos;
	}

	public void setContatos(Set<Contato> contatos) {
		this.contatos = contatos;
	}

	public void addContatos(Contato contatos) {
		this.contatos.add(contatos);
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String getDescricaoFrame() {
		return nomeSobrenome;
	}

	public Cliente() {
		super();
		this.cpf = null;
		this.cnpj = null;
		this.tipoPessoa = TipoPessoa.FISICO;
		this.enquadramento = Enquadramento.CLIENTE;
		this.enderecos = new HashSet<>();
		this.contatos = new HashSet<>();
	}

	public Cliente(Long id) {
		super(id);
		this.cpf = null;
		this.cnpj = null;
		this.tipoPessoa = TipoPessoa.FISICO;
		this.enquadramento = Enquadramento.CLIENTE;
		this.enderecos = new HashSet<>();
		this.contatos = new HashSet<>();
	}

	public Cliente(String nomeSobrenome, String razaoSocial, String cpf, String cnpj, String observacao,
			TipoPessoa tipoPessoa, Enquadramento enquadramento, Situacao situacao) {
		super(nomeSobrenome, Timestamp.valueOf(LocalDateTime.now()), situacao);
		this.razaoSocial = razaoSocial;
		this.cpf = (cpf != null && !cpf.isEmpty()) ? cpf : null;
		this.cnpj = (cnpj != null && !cnpj.isEmpty()) ? cnpj : null;
		this.observacao = observacao;
		this.tipoPessoa = tipoPessoa;
		this.enquadramento = enquadramento;
		this.enderecos = new HashSet<>();
		this.contatos = new HashSet<>();
	}

	public Cliente(String nomeSobrenome, String razaoSocial, String cpf, String cnpj, String observacao,
			TipoPessoa tipoPessoa, Enquadramento enquadramento, Situacao situacao, Set<Contato> contatos,
			Set<Endereco> enderecos) {
		super(nomeSobrenome, Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()), situacao);
		this.razaoSocial = razaoSocial;
		this.cpf = (cpf != null && !cpf.isEmpty()) ? cpf : null;
		this.cnpj = (cnpj != null && !cnpj.isEmpty()) ? cnpj : null;
		this.observacao = observacao;
		this.tipoPessoa = tipoPessoa;
		this.enquadramento = enquadramento;
		this.contatos = contatos;
		this.enderecos = enderecos;
	}

	public Cliente(Long id, String nomeSobrenome, String razaoSocial, String cpf, String cnpj, String observacao,
			Timestamp dataCadastro, Timestamp dataUltimaAlteracao, TipoPessoa tipoPessoa, Enquadramento enquadramento,
			Situacao situacao, Set<Contato> contatos, Set<Endereco> enderecos) {
		super(id, nomeSobrenome, dataCadastro, dataUltimaAlteracao, situacao);
		this.razaoSocial = razaoSocial;
		this.cpf = (cpf != null && !cpf.isEmpty()) ? cpf : null;
		this.cnpj = (cnpj != null && !cnpj.isEmpty()) ? cnpj : null;
		this.observacao = observacao;
		this.tipoPessoa = tipoPessoa;
		this.enquadramento = enquadramento;
		this.contatos = contatos;
		this.enderecos = enderecos;
	}

	@Override
	public String toString() {
		return "Cliente [razaoSocial=" + razaoSocial + ", cpf=" + cpf + ", cnpj=" + cnpj + ", observacao=" + observacao
				+ ", tipoPessoa=" + tipoPessoa + ", enquadramento=" + enquadramento + ", contatos=" + contatos
				+ ", enderecos=" + enderecos + "]";
	}

}
