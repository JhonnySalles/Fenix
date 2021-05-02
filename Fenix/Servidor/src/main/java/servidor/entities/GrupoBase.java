package servidor.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import comum.model.enums.Situacao;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class GrupoBase extends EntidadeBase implements Serializable {

	// Utilizado para poder ser transformado em sequencia de bytes
	// e poder então trafegar os dados em rede ou salvar em arquivo.
	private static final long serialVersionUID = 560772010212263047L;

	@Column(name = "Descricao", columnDefinition = "varchar(250)")
	private String descricao;

	@Column(name = "Cor", columnDefinition = "varchar(10)")
	private String cor;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "IdSubGrupo", nullable = true, foreignKey = @ForeignKey(name = "FK_SUBGRUPO_IMAGEM"))
	@Cascade(CascadeType.ALL)
	private Set<GupoSubGrupoImagem> imagens;

	public Set<GupoSubGrupoImagem> getImagens() {
		return imagens;
	}

	public void setImagens(Set<GupoSubGrupoImagem> imagens) {
		this.imagens = imagens;
	}

	public void addImagens(GupoSubGrupoImagem imagem) {
		this.imagens.add(imagem);
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		if (cor.isEmpty())
			this.cor = "#000000";
		else
			this.cor = cor;
	}
	
	public String getDescricaoFrame() {
		return descricao;
	}

	public GrupoBase() {
		this.id = Long.valueOf(0);
		this.descricao = "";
		this.cor = "#000000";
		this.situacao = Situacao.ATIVO;
		this.imagens = new HashSet<>();
	}

	public GrupoBase(Long id, String descricao, String cor, Situacao situacao) {
		this.id = id;
		this.descricao = descricao;
		this.situacao = situacao;
		this.imagens = new HashSet<>();

		setCor(cor);
	}

	public GrupoBase(Long id, String descricao, String cor, Situacao situacao, Set<GupoSubGrupoImagem> imagens) {
		this.id = id;
		this.descricao = descricao;
		this.situacao = situacao;
		this.imagens = imagens;

		setCor(cor);
	}

	@Override
	public String toString() {
		return "GrupoBase [descricao=" + descricao + ", cor=" + cor + ", imagens=" + imagens + "]";
	}
}
