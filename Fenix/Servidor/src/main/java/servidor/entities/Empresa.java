package servidor.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import comum.model.entities.Entidade;
import comum.model.enums.Situacao;
import servidor.annotation.Sugestao;

@Entity
@Table(name = "empresas")
@Sugestao(campo = "NomeFantasia")
public class Empresa extends EntidadeBase implements Serializable, Entidade {

	// Utilizado para poder ser transformado em sequencia de bytes
	// e poder então trafegar os dados em rede ou salvar em arquivo.
	private static final long serialVersionUID = -721892433851496543L;

	@Column(name = "NomeFantasia")
	private String nomeFantasia;

	@Column(name = "RazaoSocial")
	private String razaoSocial;

	@Column(name = "CNPJ")
	private String cnpj;

	@Column(name = "DataCadastro")
	private Timestamp dataCadastro;

	@OneToOne(targetEntity = Bairro.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "IdBairro")
	@Cascade(CascadeType.ALL)
	private Bairro bairro;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "IdEmpresa", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_EMPRESA_ENDERECO"))
	@Cascade(CascadeType.ALL)
	private Set<Endereco> enderecos;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "IdEmpresa", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_EMPRESA_CONTATO"))
	@Cascade(CascadeType.ALL)
	private Set<Contato> contatos;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "IdEmpresa", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_EMPRESA_IMAGEM"))
	@Cascade(CascadeType.ALL)
	private Set<EmpresaImagem> imagens;

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Timestamp dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Bairro getBairro() {
		return bairro;
	}

	public void setBairro(Bairro bairro) {
		this.bairro = bairro;
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

	public Set<EmpresaImagem> getImagens() {
		return imagens;
	}

	public void setImagens(Set<EmpresaImagem> imagens) {
		this.imagens = imagens;
	}

	public void addImagens(EmpresaImagem imagens) {
		this.imagens.add(imagens);
	}

	@Override
	public String getDescricaoFrame() {
		return nomeFantasia;
	}

	public Empresa() {
		super();
		this.nomeFantasia = "";
		this.razaoSocial = "";
		this.cnpj = "";
		this.dataCadastro = Timestamp.valueOf(LocalDateTime.now());
		this.enderecos = new HashSet<>();
		this.contatos = new HashSet<>();
		this.imagens = new HashSet<>();
	}

	public Empresa(Long id) {
		super(id);
		this.nomeFantasia = "";
		this.razaoSocial = "";
		this.cnpj = "";
		this.dataCadastro = Timestamp.valueOf(LocalDateTime.now());
		this.enderecos = new HashSet<>();
		this.contatos = new HashSet<>();
		this.imagens = new HashSet<>();
	}

	public Empresa(String nomeFantasia, String razaoSocial, String cnpj, Situacao situacao) {
		super(0L, situacao);
		this.nomeFantasia = nomeFantasia;
		this.razaoSocial = razaoSocial;
		this.cnpj = cnpj;
		this.dataCadastro = Timestamp.valueOf(LocalDateTime.now());
		this.enderecos = new HashSet<>();
		this.contatos = new HashSet<>();
		this.imagens = new HashSet<>();
	}

	public Empresa(Long id, String nomeFantasia, String razaoSocial, String cnpj, Timestamp dataCadastro,
			Situacao situacao, Bairro bairro) {
		super(id, situacao);
		this.nomeFantasia = nomeFantasia;
		this.razaoSocial = razaoSocial;
		this.cnpj = cnpj;
		this.dataCadastro = dataCadastro;
		this.bairro = bairro;
		this.enderecos = new HashSet<>();
		this.contatos = new HashSet<>();
		this.imagens = new HashSet<>();
	}

	public Empresa(Long id, String nomeFantasia, String razaoSocial, String cnpj, Timestamp dataCadastro,
			Situacao situacao, Bairro bairro, Set<Endereco> enderecos, Set<Contato> contatos,
			Set<EmpresaImagem> imagens) {
		super(id, situacao);
		this.nomeFantasia = nomeFantasia;
		this.razaoSocial = razaoSocial;
		this.cnpj = cnpj;
		this.dataCadastro = dataCadastro;
		this.bairro = bairro;
		this.enderecos = enderecos;
		this.contatos = contatos;
		this.imagens = imagens;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((cnpj == null) ? 0 : cnpj.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Empresa other = (Empresa) obj;
		if (cnpj == null) {
			if (other.cnpj != null)
				return false;
		} else if (!cnpj.equals(other.cnpj))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Empresa [nomeFantasia=" + nomeFantasia + ", razaoSocial=" + razaoSocial + ", cnpj=" + cnpj
				+ ", dataCadastro=" + dataCadastro + ", bairro=" + bairro + ", enderecos=" + enderecos + ", contatos="
				+ contatos + ", imagens=" + imagens + ", id=" + id + ", situacao=" + situacao + "]";
	}
}
