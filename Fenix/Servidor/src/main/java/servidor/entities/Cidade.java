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
import comum.model.enums.Situacao;
import servidor.annotation.Sugestao;

@Entity
@Table(name = "cidades")
@Sugestao(campo = "Nome")
public class Cidade extends EntidadeBase implements Serializable, Entidade {

	// Utilizado para poder ser transformado em sequencia de bytes
	// e poder então trafegar os dados em rede ou salvar em arquivo.
	private static final long serialVersionUID = 1106722678002102808L;

	@OneToOne(targetEntity = Estado.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "IdEstado", nullable = false, foreignKey = @ForeignKey(name = "FK_CIDADE_ESTADO"))
	@Cascade(CascadeType.SAVE_UPDATE)
	private Estado estado;

	@Column(name = "Nome", columnDefinition = "varchar(150)")
	private String nome;

	@Column(name = "Ddd", columnDefinition = "varchar(3)")
	private String ddd;

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDdd() {
		return ddd;
	}

	public void setDdd(String ddd) {
		this.ddd = ddd;
	}

	@Override
	public String getDescricaoFrame() {
		return nome + " - " + estado.getSigla();
	}

	public Cidade() {
		super();
		this.nome = "";
		this.ddd = "";
	}

	public Cidade(String nome, String ddd, Estado estado) {
		super();
		this.estado = estado;
		this.nome = nome;
		this.ddd = ddd;
	}

	public Cidade(String nome, String ddd, Situacao situacao, Estado estado) {
		super();
		this.estado = estado;
		this.nome = nome;
		this.ddd = ddd;
		this.situacao = situacao;
	}
	
	public Cidade(Long id, String nome, String ddd, Situacao situacao, Estado estado) {
		super(id);
		this.estado = estado;
		this.nome = nome;
		this.ddd = ddd;
		this.situacao = situacao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		Cidade other = (Cidade) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Cidade [estado=" + estado + ", nome=" + nome + ", ddd=" + ddd + ", id=" + id + ", situacao=" + situacao
				+ "]";
	}
}
