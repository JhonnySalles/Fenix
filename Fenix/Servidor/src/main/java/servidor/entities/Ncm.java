package servidor.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import comum.model.entities.Entidade;
import servidor.annotation.Sugestao;

@Entity
@Table(name = "ncm")
@Sugestao(campo = "Descricao")
public class Ncm extends EntidadeBase implements Serializable, Entidade  {

	private static final long serialVersionUID = 1778071323939610003L;

	@Column(name = "ncm", columnDefinition = "Varchar(8)")
	private String ncm;

	@Column(name = "Descricao", columnDefinition = "LongText")
	private String descricao;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getNcm() {
		return ncm;
	}

	public void setNcm(String ncm) {
		this.ncm = ncm;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Override
	public String getDescricaoFrame() {
		return ncm + " - " + descricao;
	}

	public Ncm() {
		this.ncm = "";
		this.descricao = "";
	}

	public Ncm(String ncm, String descricao) {
		this.ncm = ncm;
		this.descricao = descricao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ncm == null) ? 0 : ncm.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ncm other = (Ncm) obj;
		if (ncm == null) {
			if (other.ncm != null)
				return false;
		} else if (!ncm.equals(other.ncm))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Ncm [ncm=" + ncm + ", descricao=" + descricao + "]";
	}

}
