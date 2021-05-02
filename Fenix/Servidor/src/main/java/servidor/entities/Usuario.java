package servidor.entities;

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
import javax.persistence.Table;

import comum.model.entities.Entidade;
import comum.model.enums.Situacao;
import comum.model.enums.UsuarioNivel;

@Entity
@Table(name = "usuarios")
public class Usuario extends Pessoa implements Entidade {

	// Utilizado para poder ser transformado em sequencia de bytes
	// e poder então trafegar os dados em rede ou salvar em arquivo.
	private static final long serialVersionUID = -1829885748257026644L;

	@Column(name = "Login", columnDefinition = "char(20)")
	private String login;

	@Column(name = "Senha", columnDefinition = "varchar(250)")
	private String senha;

	@Column(name = "Observacao", columnDefinition = "longtext")
	private String observacao;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "IdUsuario", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_USUARIO_IMAGEM"))
	private Set<UsuarioImagem> imagens;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "IdUsuario", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_USUARIO_CONTATO"))
	private Set<Contato> contatos;

	@Column(name = "Nivel", columnDefinition = "enum('USUARIO','ADMINISTRADOR','TOTAL')")
	@Enumerated(EnumType.STRING)
	private UsuarioNivel nivel;

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

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Set<UsuarioImagem> getImagens() {
		return imagens;
	}

	public void setImagens(Set<UsuarioImagem> imagens) {
		this.imagens = imagens;
	}

	public UsuarioNivel getNivel() {
		return nivel;
	}

	public void setNivel(UsuarioNivel nivel) {
		this.nivel = nivel;
	}

	public Set<Contato> getContatos() {
		return contatos;
	}

	public void setContatos(Set<Contato> contatos) {
		this.contatos = contatos;
	}

	public void addContatos(Contato contatos) {
		this.contatos.add(contatos);
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String getDescricaoFrame() {
		return nomeSobrenome;
	}

	public Usuario() {
		super();
		this.login = "";
		this.senha = "";
		this.nivel = UsuarioNivel.USUARIO;
		this.observacao = "";
		this.contatos = new HashSet<>();
		this.imagens = new HashSet<>();
	}

	public Usuario(Long id) {
		super(id);
		this.login = "";
		this.senha = "";
		this.nivel = UsuarioNivel.USUARIO;
		this.observacao = "";
		this.contatos = new HashSet<>();
		this.imagens = new HashSet<>();
	}

	public Usuario(String login, String senha) {
		super();
		this.login = login;
		this.senha = senha;
		this.nivel = UsuarioNivel.USUARIO;
		this.observacao = "";
		this.contatos = new HashSet<>();
		this.imagens = new HashSet<>();
	}

	public Usuario(Long id, String nomeSobrenome, Timestamp dataCadastro, Timestamp dataUltimaAlteracao, String login,
			String observacao, UsuarioNivel nivel, Situacao situacao) {
		super(id, nomeSobrenome, dataCadastro, dataUltimaAlteracao, situacao);
		this.login = login;
		this.senha = "";
		this.nivel = nivel;
		this.observacao = observacao;
		this.contatos = new HashSet<>();
		this.imagens = new HashSet<>();
	}

	public Usuario(String nomeSobrenome, String login, String observacao) {
		super(nomeSobrenome, Timestamp.valueOf(LocalDateTime.now()), Situacao.ATIVO);
		this.login = login;
		this.senha = "";
		this.nivel = UsuarioNivel.USUARIO;
		this.observacao = observacao;
		this.contatos = new HashSet<>();
		this.imagens = new HashSet<>();
	}

	public Usuario(Long id, String nomeSobrenome, Timestamp dataCadastro, Timestamp dataUltimaAlteracao, String login,
			String observacao, UsuarioNivel nivel, Situacao situacao, Set<UsuarioImagem> imagens) {
		super(id, nomeSobrenome, dataCadastro, dataUltimaAlteracao, situacao);
		this.login = login;
		this.senha = "";
		this.observacao = observacao;
		this.imagens = imagens;
		this.nivel = nivel;
		this.contatos = new HashSet<>();
	}

	public Usuario(Long id, String nomeSobrenome, Timestamp dataCadastro, Timestamp dataUltimaAlteracao, String login,
			String observacao, UsuarioNivel nivel, Situacao situacao, Set<Contato> contatos,
			Set<UsuarioImagem> imagens) {
		super(id, nomeSobrenome, dataCadastro, dataUltimaAlteracao, situacao);
		this.login = login;
		this.senha = "";
		this.observacao = observacao;
		this.imagens = imagens;
		this.nivel = nivel;
		this.contatos = contatos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((login == null) ? 0 : login.hashCode());
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
		Usuario other = (Usuario) obj;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Usuario [login=" + login + ", senha=" + senha + ", observacao=" + observacao + ", imagens=" + imagens
				+ ", contatos=" + contatos + ", nivel=" + nivel + "]";
	}

}
