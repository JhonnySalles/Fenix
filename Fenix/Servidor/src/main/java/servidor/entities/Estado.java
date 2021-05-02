package servidor.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import comum.model.entities.Entidade;

@Entity
@Table(name = "estados")
public class Estado extends EntidadeBase implements Serializable, Entidade {

	// Utilizado para poder ser transformado em sequencia de bytes
	// e poder então trafegar os dados em rede ou salvar em arquivo.
	private static final long serialVersionUID = -1793279158274831382L;

	@Column(name = "Nome", columnDefinition = "varchar(150)")
	private String nome;

	@Column(name = "Sigla", columnDefinition = "varchar(2)")
	private String sigla;

	@Column(name = "CodigoIBGE", columnDefinition = "int(3)")
	private Integer codigoIBGE;

	@OneToOne(targetEntity = Pais.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "IdPais", nullable = false, foreignKey = @ForeignKey(name = "FK_ESTADO_PAIS"))
	@Cascade(CascadeType.SAVE_UPDATE)
	private Pais pais;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public Integer getCodigoIBGE() {
		return codigoIBGE;
	}

	public void setCodigoIBGE(Integer codigoIBGE) {
		this.codigoIBGE = codigoIBGE;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	@Override
	public String getDescricaoFrame() {
		return nome;
	}

	public Estado() {
		super();
		this.nome = "";
		this.sigla = "";
		this.codigoIBGE = 0;
	}

	public Estado(String nome, String sigla, Pais pais) {
		super();
		this.nome = "";
		this.sigla = "";
		this.codigoIBGE = 0;
	}

	public Estado(Long id, String nome, String sigla, Integer codigoIBGE, Pais pais) {
		super(id);
		this.nome = nome;
		this.sigla = sigla;
		this.codigoIBGE = codigoIBGE;
		this.pais = pais;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((codigoIBGE == null) ? 0 : codigoIBGE.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((sigla == null) ? 0 : sigla.hashCode());
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
		Estado other = (Estado) obj;
		if (codigoIBGE == null) {
			if (other.codigoIBGE != null)
				return false;
		} else if (!codigoIBGE.equals(other.codigoIBGE))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (sigla == null) {
			if (other.sigla != null)
				return false;
		} else if (!sigla.equals(other.sigla))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Estado [nome=" + nome + ", sigla=" + sigla + ", codigoIBGE=" + codigoIBGE + ", pais=" + pais + ", id="
				+ id + ", situacao=" + situacao + "]";
	}
}
