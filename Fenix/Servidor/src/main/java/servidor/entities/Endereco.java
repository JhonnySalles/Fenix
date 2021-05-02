package servidor.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import comum.model.entities.Entidade;
import comum.model.enums.Situacao;
import comum.model.enums.TipoEndereco;
import javafx.beans.property.SimpleBooleanProperty;
import servidor.converter.BooleanPropertyConverter;

@Entity
@Table(name = "enderecos")
public class Endereco extends EntidadeBase implements Serializable, Entidade {

	// Utilizado para poder ser transformado em sequencia de bytes
	// e poder então trafegar os dados em rede ou salvar em arquivo.
	private static final long serialVersionUID = -656350964511989081L;

	@OneToOne(targetEntity = Bairro.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "IdBairro")
	@Cascade(CascadeType.ALL)
	private Bairro bairro;

	@Column(name = "Endereco", columnDefinition = "varchar(150)")
	private String endereco;

	@Column(name = "Numero", columnDefinition = "varchar(10)")
	private String numero;

	@Column(name = "CEP", columnDefinition = "varchar(10)")
	private String cep;

	@Column(name = "Complemento", columnDefinition = "varchar(150)")
	private String complemento;

	@Column(name = "Observacao", columnDefinition = "longtext")
	private String observacao;

	@Column(name = "DataCadastro", columnDefinition = "datetime")
	private Timestamp dataCadastro;

	@Column(name = "Tipo", columnDefinition = "enum('RESIDENCIAL','COMERCIAL','COBRANCA','ENTREGA','OUTROS')")
	@Enumerated(EnumType.STRING)
	private TipoEndereco tipoEndereco;

	@Column(name = "Padrao", columnDefinition = "tinyint(1)")
	@Convert(converter = BooleanPropertyConverter.class)
	private SimpleBooleanProperty padrao = new SimpleBooleanProperty(false);

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Bairro getBairro() {
		return bairro;
	}

	public void setBairro(Bairro bairro) {
		this.bairro = bairro;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Timestamp getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Timestamp dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public TipoEndereco getTipoEndereco() {
		return tipoEndereco;
	}

	public void setTipoEndereco(TipoEndereco tipoEndereco) {
		this.tipoEndereco = tipoEndereco;
	}

	public boolean isPadrao() {
		return padrao.get();
	}

	public SimpleBooleanProperty padraoProperty() {
		return padrao;
	}

	public void setPadrao(boolean padrao) {
		this.padrao.set(padrao);
	}

	@Override
	public String getDescricaoFrame() {
		return endereco + ", " + numero;
	}
	
	public String getResumeEndereco() {
		return endereco + ", " + numero + " - " + (bairro != null ? bairro.getDescricaoFrame() : "");
	}

	public Endereco() {
		super();
		this.endereco = "";
		this.numero = "";
		this.cep = "";
		this.complemento = "";
		this.observacao = "";
		this.dataCadastro = Timestamp.valueOf(LocalDateTime.now());
		this.tipoEndereco = TipoEndereco.COBRANCA;
		this.padrao.set(false);
	}
	
	public Endereco(String endereco, String numero, String cep, String complemento, String observacao,
			TipoEndereco tipoEndereco, Situacao situacao) {
		super(0L, situacao);
		this.endereco = endereco;
		this.numero = numero;
		this.cep = cep;
		this.complemento = complemento;
		this.observacao = observacao;
		this.dataCadastro = Timestamp.valueOf(LocalDateTime.now());
		this.padrao.set(false);
		this.tipoEndereco = tipoEndereco;
	}

	public Endereco(Bairro bairro, String endereco, String numero, String cep, String complemento, String observacao, Boolean padrao) {
		super(0L);
		this.bairro = bairro;
		this.endereco = endereco;
		this.numero = numero;
		this.cep = cep;
		this.complemento = complemento;
		this.observacao = observacao;
		this.dataCadastro = Timestamp.valueOf(LocalDateTime.now());
		this.padrao.set(padrao);
		this.tipoEndereco = TipoEndereco.COBRANCA;
	}

	public Endereco(Bairro bairro, String endereco, String numero, String cep, String complemento, String observacao,
			TipoEndereco tipoEndereco, Situacao situacao, Boolean padrao) {
		super(0L, situacao);
		this.bairro = bairro;
		this.endereco = endereco;
		this.numero = numero;
		this.cep = cep;
		this.complemento = complemento;
		this.observacao = observacao;
		this.dataCadastro = Timestamp.valueOf(LocalDateTime.now());
		this.padrao.set(padrao);
		this.tipoEndereco = tipoEndereco;
	}

	public Endereco(Long id, Bairro bairro, String endereco, String numero, String cep, String complemento,
			String observacao, Timestamp dataCadastro, TipoEndereco tipoEndereco, Situacao situacao, Boolean padrao) {
		super(id, situacao);
		this.bairro = bairro;
		this.endereco = endereco;
		this.numero = numero;
		this.cep = cep;
		this.complemento = complemento;
		this.observacao = observacao;
		this.dataCadastro = dataCadastro;
		this.padrao.set(padrao);
		this.tipoEndereco = tipoEndereco;
	}

	public Endereco(Bairro bairro, String endereco, String cep) {
		super();
		this.bairro = bairro;
		this.endereco = endereco;
		this.numero = "";
		this.cep = cep;
		this.complemento = "";
		this.observacao = "";
		this.tipoEndereco = TipoEndereco.COBRANCA;
		this.padrao.set(false);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((bairro == null) ? 0 : bairro.hashCode());
		result = prime * result + ((cep == null) ? 0 : cep.hashCode());
		result = prime * result + ((endereco == null) ? 0 : endereco.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		result = prime * result + ((tipoEndereco == null) ? 0 : tipoEndereco.hashCode());
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
		Endereco other = (Endereco) obj;
		if (bairro == null) {
			if (other.bairro != null)
				return false;
		} else if (!bairro.equals(other.bairro))
			return false;
		if (cep == null) {
			if (other.cep != null)
				return false;
		} else if (!cep.equals(other.cep))
			return false;
		if (endereco == null) {
			if (other.endereco != null)
				return false;
		} else if (!endereco.equals(other.endereco))
			return false;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		if (tipoEndereco != other.tipoEndereco)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Endereco [bairro=" + bairro + ", endereco=" + endereco + ", numero=" + numero + ", cep=" + cep
				+ ", complemento=" + complemento + ", observacao=" + observacao + ", dataCadastro=" + dataCadastro
				+ ", tipoEndereco=" + tipoEndereco + ", padrao=" + padrao + ", id=" + id + ", situacao=" + situacao
				+ "]";
	}
}
