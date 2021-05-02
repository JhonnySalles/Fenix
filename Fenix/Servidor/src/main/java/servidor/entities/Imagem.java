package servidor.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import comum.model.enums.TamanhoImagem;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Imagem implements Serializable {

	// Utilizado para poder ser transformado em sequencia de bytes
	// e poder então trafegar os dados em rede ou salvar em arquivo.
	private static final long serialVersionUID = 6407704915654886503L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Long id;

	@Column(name = "Nome", columnDefinition = "varchar(250)")
	private String nome;

	@Column(name = "Extenssao", columnDefinition = "varchar(10)")
	private String extenssao;

	@Column(name = "Caminho", columnDefinition = "longtext")
	private String caminho;

	@Column(name = "Imagem", columnDefinition = "longblob")
	private byte[] imagem;

	@Column(name = "Tamanho", columnDefinition = "enum('ORIGINAL','PEQUENA','MEDIA')")
	@Enumerated(EnumType.STRING)
	private TamanhoImagem tamanho;

	@Transient
	private Boolean excluir;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getExtenssao() {
		return extenssao;
	}

	public void setExtenssao(String extenssao) {
		this.extenssao = extenssao;
	}

	public byte[] getImagem() {
		return imagem;
	}

	public void setImagem(byte[] imagem) {
		this.imagem = imagem;
	}

	public TamanhoImagem getTamanho() {
		return tamanho;
	}

	public void setTamanho(TamanhoImagem tamanho) {
		this.tamanho = tamanho;
	}

	public Boolean getExcluir() {
		return excluir;
	}

	public void setExcluir(Boolean excluir) {
		this.excluir = excluir;
	}

	public Imagem() {
		this.id = Long.valueOf(0);
		this.excluir = false;
		this.nome = "";
		this.extenssao = "";
		this.tamanho = TamanhoImagem.NENHUMA;
	}

	public Imagem(String nome, String extenssao, byte[] imagem, TamanhoImagem tamanho) {
		this.id = Long.valueOf(0);
		this.nome = nome;
		this.extenssao = extenssao;
		this.imagem = imagem;
		this.tamanho = tamanho;
		this.excluir = false;
	}

	public Imagem(Long id, String nome, String extenssao, byte[] imagem, TamanhoImagem tamanho) {
		this.id = id;
		this.nome = nome;
		this.extenssao = extenssao;
		this.imagem = imagem;
		this.tamanho = tamanho;
		this.excluir = false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((tamanho == null) ? 0 : tamanho.hashCode());
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
		Imagem other = (Imagem) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (tamanho != other.tamanho)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Imagem [id=" + id + ", nome=" + nome + ", extenssao=" + extenssao + ", tamanho=" + tamanho + "]";
	}

}
