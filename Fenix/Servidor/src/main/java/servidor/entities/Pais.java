package servidor.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import comum.model.entities.Entidade;

@Entity
@Table(name = "paises")
public class Pais extends EntidadeBase implements Serializable, Entidade {

	// Utilizado para poder ser transformado em sequencia de bytes
	// e poder então trafegar os dados em rede ou salvar em arquivo.
	private static final long serialVersionUID = -1964662824826388057L;

	@Column(name = "Nome", columnDefinition = "varchar(150)")
	private String nome;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return nome;
	}

	public Pais() {
		super();
		this.nome = "";
	}

	public Pais(Long id, String nome) {
		super(id);
		this.nome = nome;
	}
	
	@Override
	public String getDescricaoFrame() {
		return nome;
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
		Pais other = (Pais) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Pais [nome=" + nome + ", id=" + id + ", situacao=" + situacao + "]";
	}
}
