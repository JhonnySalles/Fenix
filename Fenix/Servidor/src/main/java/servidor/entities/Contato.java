package servidor.entities;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import comum.model.entities.Entidade;
import comum.model.enums.Situacao;
import comum.model.enums.TipoContato;
import comum.model.mask.Mascaras;
import javafx.beans.property.SimpleBooleanProperty;
import servidor.converter.BooleanPropertyConverter;

@Entity
@Table(name = "contatos")
public class Contato extends Pessoa implements Entidade {

	// Utilizado para poder ser transformado em sequencia de bytes
	// e poder então trafegar os dados em rede ou salvar em arquivo.
	private static final long serialVersionUID = -1562578455627883930L;

	@Column(name = "Telefone", columnDefinition = "varchar(15)")
	private String telefone;

	@Column(name = "Celular", columnDefinition = "varchar(15)")
	private String celular;

	@Column(name = "Email", columnDefinition = "varchar(250)")
	private String email;

	@Column(name = "Observacao", columnDefinition = "longtext")
	private String observacao;

	@Column(name = "Tipo", columnDefinition = "enum('RESIDENCIAL','COMERCIAL')")
	@Enumerated(EnumType.STRING)
	private TipoContato tipoContato;

	@Column(name = "Padrao", columnDefinition = "tinyint(1)")
	@Convert(converter = BooleanPropertyConverter.class)
	private SimpleBooleanProperty padrao = new SimpleBooleanProperty(false);

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public TipoContato getTipoContato() {
		return tipoContato;
	}

	public void setTipoContato(TipoContato tipo) {
		this.tipoContato = tipo;
	}

	public void setPadrao(boolean padrao) {
		this.padrao.set(padrao);
	}

	public boolean isPadrao() {
		return padrao.get();
	}

	public SimpleBooleanProperty padraoProperty() {
		return padrao;
	}

	@Override
	public String getDescricaoFrame() {
		return nomeSobrenome;
	}

	public String getResumeContato() {
		return (nomeSobrenome.indexOf(" ") > 0 ? nomeSobrenome.substring(0, nomeSobrenome.indexOf(" ")) : nomeSobrenome)
				+ " - " + (!telefone.isEmpty() ? Mascaras.formatFone(telefone)
						: (!celular.isEmpty() ? Mascaras.formatFone(celular) : email));
	}

	public Contato() {
		super();
		this.telefone = "";
		this.celular = "";
		this.email = "";
		this.observacao = "";
		this.tipoContato = TipoContato.RESIDENCIAL;
		this.padrao.set(false);
	}

	public Contato(String nomeSobrenome, String telefone, String celular, String email, String observacao,
			Boolean padrao) {
		super(0L, nomeSobrenome, Timestamp.valueOf(LocalDateTime.now()), Situacao.ATIVO);
		this.telefone = telefone;
		this.celular = celular;
		this.email = email;
		this.observacao = observacao;
		this.tipoContato = TipoContato.RESIDENCIAL;
		this.padrao.set(padrao);
	}

	public Contato(Long id, String nomeSobrenome, String telefone, String celular, String email, String observacao,
			Timestamp dataCadastro, TipoContato tipoContato, Situacao situacao, Boolean padrao) {
		super(id, nomeSobrenome, dataCadastro, situacao);
		this.telefone = telefone;
		this.celular = celular;
		this.email = email;
		this.observacao = observacao;
		this.tipoContato = tipoContato;
		this.padrao.set(padrao);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((celular == null) ? 0 : celular.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((telefone == null) ? 0 : telefone.hashCode());
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
		Contato other = (Contato) obj;
		if (celular == null) {
			if (other.celular != null)
				return false;
		} else if (!celular.equals(other.celular))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (telefone == null) {
			if (other.telefone != null)
				return false;
		} else if (!telefone.equals(other.telefone))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Contato [telefone=" + telefone + ", celular=" + celular + ", email=" + email + ", observacao="
				+ observacao + ", tipoContato=" + tipoContato + ", padrao=" + padrao + "]";
	}

}
