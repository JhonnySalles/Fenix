package servidor.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;

import comum.model.enums.Situacao;
import comum.model.enums.UsuarioNivel;

@Entity
public class Usuario extends Pessoa implements Serializable {

	// Utilizado para poder ser transformado em sequencia de bytes
	// e poder então trafegar os dados em rede ou salvar em arquivo.
	private static final long serialVersionUID = -1829885748257026644L;

	@Column(name = "Login")
	private String login;

	@Column(name = "Senha")
	private String senha;

	@Column(name = "Observacao")
	private String observacao;
	private List<Imagem> imagens;

	@Column(name = "Nivel")
	private Enum<UsuarioNivel> nivel;

	@Column(name = "Situacao")
	private Enum<Situacao> situacao;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Enum<Situacao> getSituacao() {
		return situacao;
	}

	public void setSituacao(Enum<Situacao> situacao) {
		this.situacao = situacao;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public List<Imagem> getImagens() {
		return imagens;
	}

	public void setImagens(List<Imagem> imagens) {
		this.imagens = imagens;
	}

	public Enum<UsuarioNivel> getNivel() {
		return nivel;
	}

	public void setNivel(Enum<UsuarioNivel> nivel) {
		this.nivel = nivel;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Usuario() {
		super();
	}

	public Usuario(Long id, String nomeSobrenome, Timestamp dataCadastro, String login, String observacao,
			Enum<UsuarioNivel> nivel, Enum<Situacao> situacao) {
		super(id, nomeSobrenome, dataCadastro);
		this.login = login;
		this.nivel = nivel;
		this.observacao = observacao;
		this.situacao = situacao;
	}

	public Usuario(Long id, String nomeSobrenome, Timestamp dataCadastro, String login, String observacao,
			Enum<UsuarioNivel> nivel, Enum<Situacao> situacao, List<Imagem> imagens) {
		super(id, nomeSobrenome, dataCadastro);
		this.login = login;
		this.situacao = situacao;
		this.observacao = observacao;
		this.imagens = imagens;
		this.nivel = nivel;
	}

}
