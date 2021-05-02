package servidor.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import comum.model.entities.Entidade;
import comum.model.enums.Situacao;
import comum.model.enums.TipoProduto;

@Entity
@Table(name = "produtos")
public class Produto extends EntidadeBase implements Serializable, Entidade {

	// Utilizado para poder ser transformado em sequencia de bytes
	// e poder então trafegar os dados em rede ou salvar em arquivo.
	private static final long serialVersionUID = 1736586839536047759L;

	private Long idGrupo;

	@Column(name = "Descricao", nullable = false, insertable = true, updatable = true, length = 250, columnDefinition = "varchar(250)")
	private String descricao;

	@Column(name = "CodigoBarras", length = 35, columnDefinition = "varchar(35)")
	private String codigoBarras;

	@Column(name = "Marca", length = 250, columnDefinition = "varchar(250)")
	private String marca;

	@Column(name = "Unidade", length = 4, columnDefinition = "varchar(4)")
	private String unidade;

	@Column(name = "Peso", precision = 4)
	private Double peso;

	@Column(name = "Volume", precision = 4)
	private Double volume;

	@Column(name = "Observacao", columnDefinition = "longtext")
	private String observacao;

	@Column(name = "DataCadastro", columnDefinition = "datetime")
	private Timestamp dataCadastro;

	@Column(name = "DataUltimaAlteracao", columnDefinition = "datetime")
	private Timestamp dataUltimaAlteracao;

	@Column(name = "TipoProduto", columnDefinition = "enum('PRODUZIDO','MATERIAPRIMA','SERVICO','PRODUTOFINAL')", length = 20)
	@Enumerated(EnumType.STRING)
	private TipoProduto tipoProduto;

	@OneToOne(targetEntity = Ncm.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "NCM", nullable = true, foreignKey = @ForeignKey(name = "FK_PRODUTO_NCM"))
	private Ncm ncm;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "IdProduto", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_PRODUTO_IMAGEM"))
	private Set<ProdutoImagem> imagens;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(Long idGrupo) {
		this.idGrupo = idGrupo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getCodigoBarras() {
		return codigoBarras;
	}

	public void setCodigoBarras(String codigoBarras) {
		this.codigoBarras = codigoBarras;
	}

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
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

	public TipoProduto getTipoProduto() {
		return tipoProduto;
	}

	public void setTipoProduto(TipoProduto tipoProduto) {
		this.tipoProduto = tipoProduto;
	}

	public Double getPeso() {
		return peso;
	}

	public void setPeso(Double peso) {
		this.peso = peso;
	}

	public Double getVolume() {
		return volume;
	}

	public void setVolume(Double volume) {
		this.volume = volume;
	}

	public Ncm getNcm() {
		return ncm;
	}

	public void setNcm(Ncm ncm) {
		this.ncm = ncm;
	}

	public Set<ProdutoImagem> getImagens() {
		return imagens;
	}

	public void setImagens(Set<ProdutoImagem> imagens) {
		this.imagens = imagens;
	}

	public void addImagens(ProdutoImagem imagens) {
		this.imagens.add(imagens);
	}
	
	@Override
	public String getDescricaoFrame() {
		return descricao;
	}

	public Produto() {
		super();
		this.descricao = "";
		this.observacao = "";
		this.codigoBarras = "";
		this.unidade = "";
		this.marca = "";
		this.peso = 0.0;
		this.dataCadastro = Timestamp.valueOf(LocalDateTime.now());
		this.dataUltimaAlteracao = Timestamp.valueOf(LocalDateTime.now());
		this.tipoProduto = TipoProduto.PRODUTOFINAL;
		this.situacao = Situacao.ATIVO;
		this.imagens = new HashSet<>();
	}

	public Produto(Long id) {
		super(id);
		this.descricao = "";
		this.observacao = "";
		this.codigoBarras = "";
		this.unidade = "";
		this.marca = "";
		this.peso = 0.0;
		this.dataCadastro = Timestamp.valueOf(LocalDateTime.now());
		this.dataUltimaAlteracao = Timestamp.valueOf(LocalDateTime.now());
		this.tipoProduto = TipoProduto.PRODUTOFINAL;
		this.situacao = Situacao.ATIVO;
		this.imagens = new HashSet<>();
	}

	public Produto(String descricao, String observacao, String codigoBarras, String unidade, String marca, Double peso,
			Double volume, Timestamp dataCadastro, Timestamp dataUltimaAlteracao, TipoProduto tipoProduto,
			Situacao situacao) {
		super(0L, situacao);
		this.descricao = descricao;
		this.observacao = observacao;
		this.codigoBarras = codigoBarras;
		this.unidade = unidade;
		this.marca = marca;
		this.peso = peso;
		this.volume = volume;
		this.dataCadastro = dataCadastro;
		this.dataUltimaAlteracao = dataUltimaAlteracao;
		this.tipoProduto = tipoProduto;
		this.imagens = new HashSet<>();
	}

	public Produto(Long id, Long idGrupo, String descricao, String observacao, String codigoBarras, String unidade,
			String marca, Double peso, Double volume, Timestamp dataCadastro, Timestamp dataUltimaAlteracao,
			TipoProduto tipoProduto, Situacao situacao) {
		super(id, situacao);
		this.idGrupo = idGrupo;
		this.descricao = descricao;
		this.observacao = observacao;
		this.codigoBarras = codigoBarras;
		this.unidade = unidade;
		this.marca = marca;
		this.peso = peso;
		this.volume = volume;
		this.dataCadastro = dataCadastro;
		this.dataUltimaAlteracao = dataUltimaAlteracao;
		this.tipoProduto = tipoProduto;
		this.imagens = new HashSet<>();
	}

	public Produto(Long id, Long idGrupo, String descricao, String observacao, String codigoBarras, String unidade,
			String marca, Double peso, Double volume, Timestamp dataCadastro, Timestamp dataUltimaAlteracao,
			TipoProduto tipoProduto, Situacao situacao, Ncm ncm, Set<ProdutoImagem> imagens) {
		super(id, situacao);
		this.idGrupo = idGrupo;
		this.descricao = descricao;
		this.observacao = observacao;
		this.codigoBarras = codigoBarras;
		this.unidade = unidade;
		this.marca = marca;
		this.peso = peso;
		this.volume = volume;
		this.dataCadastro = dataCadastro;
		this.dataUltimaAlteracao = dataUltimaAlteracao;
		this.tipoProduto = tipoProduto;
		this.ncm = ncm;
		this.imagens = imagens;
	}

	@Override
	public String toString() {
		return "Produto [idGrupo=" + idGrupo + ", descricao=" + descricao + ", codigoBarras=" + codigoBarras
				+ ", marca=" + marca + ", unidade=" + unidade + ", peso=" + peso + ", volume=" + volume
				+ ", observacao=" + observacao + ", dataCadastro=" + dataCadastro + ", dataUltimaAlteracao="
				+ dataUltimaAlteracao + ", tipoProduto=" + tipoProduto + ", ncm=" + ncm + ", imagens=" + imagens
				+ ", id=" + id + ", situacao=" + situacao + "]";
	}
}
