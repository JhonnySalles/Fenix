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
import servidor.annotation.Sugestao;

@Entity
@Table(name = "bairros")
@Sugestao(campo = "Nome")
public class Bairro extends EntidadeBase implements Serializable, Entidade {

	// Utilizado para poder ser transformado em sequencia de bytes
	// e poder então trafegar os dados em rede ou salvar em arquivo.
	private static final long serialVersionUID = -4970755261542388345L;

	@OneToOne(targetEntity = Cidade.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "IdCidade", nullable = false, foreignKey = @ForeignKey(name = "FK_BAIRRO_CIDADE"))
	@Cascade(CascadeType.SAVE_UPDATE)
	private Cidade cidade;

	@Column(name = "Nome", columnDefinition = "varchar(150)")
	private String nome;

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String getDescricaoFrame() {
		return nome + (cidade != null ? " / " + cidade.getDescricaoFrame() : "");
	}

	public Bairro() {
		super();
		this.nome = "";
	}
	
	public Bairro(String nome, Cidade cidade) {
		super();
		this.nome = nome;
		this.cidade = cidade;
	}

	public Bairro(Long id, String nome, Cidade cidade) {
		super(id);
		this.nome = nome;
		this.cidade = cidade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((cidade == null) ? 0 : cidade.hashCode());
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
		Bairro other = (Bairro) obj;
		if (cidade == null) {
			if (other.cidade != null)
				return false;
		} else if (!cidade.equals(other.cidade))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Bairro [cidade=" + cidade + ", nome=" + nome + ", id=" + id + ", situacao=" + situacao + "]";
	}

}
