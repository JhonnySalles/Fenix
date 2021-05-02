package servidor.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

import comum.model.enums.Situacao;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Pessoa extends EntidadeBase implements Serializable {

	// Utilizado para poder ser transformado em sequencia de bytes
	// e poder então trafegar os dados em rede ou salvar em arquivo.
	private static final long serialVersionUID = -3970512560770719706L;

	@Column(name = "NomeSobrenome", nullable = true, insertable = true, updatable = true, length = 250)
	protected String nomeSobrenome;

	@Column(name = "DataCadastro", columnDefinition = "datetime")
	protected Timestamp dataCadastro;

	@Column(name = "DataUltimaAlteracao", columnDefinition = "datetime")
	protected Timestamp dataUltimaAlteracao;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getNomeSobrenome() {
		return nomeSobrenome;
	}

	public void setNomeSobrenome(String nomeSobrenome) {
		this.nomeSobrenome = nomeSobrenome;
	}

	public Timestamp getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Timestamp dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Timestamp getDataUltimaAlteracao() {
		return dataUltimaAlteracao;
	}

	public void setDataUltimaAlteracao(Timestamp dataUltimaAlteracao) {
		this.dataUltimaAlteracao = dataUltimaAlteracao;
	}

	public Pessoa() {
		super();
		this.nomeSobrenome = "";
		this.dataCadastro = Timestamp.from(Instant.now());
		this.dataUltimaAlteracao = Timestamp.from(Instant.now());
	}
	
	public Pessoa(String nomeSobrenome, Timestamp dataCadastro, Timestamp dataUltimaAlteracao,
			Situacao situacao) {
		super(0L);
		this.nomeSobrenome = nomeSobrenome;
		this.dataCadastro = dataCadastro;
		this.dataUltimaAlteracao = dataUltimaAlteracao;
		this.situacao = situacao;
	}

	public Pessoa(Long id) {
		super(id);
		this.nomeSobrenome = "";
		this.dataCadastro = Timestamp.from(Instant.now());
		this.dataUltimaAlteracao = Timestamp.from(Instant.now());
	}
	
	public Pessoa(String nomeSobrenome, Timestamp dataCadastro, Situacao situacao) {
		super(0L);
		this.nomeSobrenome = nomeSobrenome;
		this.dataCadastro = dataCadastro;
		this.dataUltimaAlteracao = Timestamp.from(Instant.now());
		this.situacao = situacao;
	}

	public Pessoa(Long id, String nomeSobrenome, Timestamp dataCadastro, Situacao situacao) {
		super(id);
		this.nomeSobrenome = nomeSobrenome;
		this.dataCadastro = dataCadastro;
		this.dataUltimaAlteracao = Timestamp.from(Instant.now());
		this.situacao = situacao;
	}

	public Pessoa(Long id, String nomeSobrenome, Timestamp dataCadastro, Timestamp dataUltimaAlteracao,
			Situacao situacao) {
		super(id);
		this.nomeSobrenome = nomeSobrenome;
		this.dataCadastro = dataCadastro;
		this.dataUltimaAlteracao = dataUltimaAlteracao;
		this.situacao = situacao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((nomeSobrenome == null) ? 0 : nomeSobrenome.hashCode());
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
		Pessoa other = (Pessoa) obj;
		if (nomeSobrenome == null) {
			if (other.nomeSobrenome != null)
				return false;
		} else if (!nomeSobrenome.equals(other.nomeSobrenome))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Pessoa [nomeSobrenome=" + nomeSobrenome + ", dataCadastro=" + dataCadastro + ", dataUltimaAlteracao="
				+ dataUltimaAlteracao + ", id=" + id + ", situacao=" + situacao + "]";
	}
}
