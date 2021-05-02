package comum.model.entities;

import comum.model.enums.DataBase;
import comum.model.enums.Tema;
import comum.model.enums.TipoLancamento;

public class Configuracao {

	DataBase serverDatabase;
	String serverHost;
	String serverPort;
	String serverBase;
	String serverUser;
	String serverPassword;

	Boolean unicodeUsar;
	String unicodeEncode;

	String logCaminho;

	Boolean hibernateMostrarSQL;

	TipoLancamento sistemaLancamento;
	Tema sistemaTema;

	public DataBase getServerDatabase() {
		return serverDatabase;
	}

	public String getServerHost() {
		return serverHost;
	}

	public String getServerPort() {
		return serverPort;
	}

	public String getServerBase() {
		return serverBase;
	}

	public String getServerUser() {
		return serverUser;
	}

	public String getServerPassword() {
		return serverPassword;
	}

	public Boolean getUnicodeUsar() {
		return unicodeUsar;
	}

	public String getUnicodeEncode() {
		return unicodeEncode;
	}

	public String getLogCaminho() {
		return logCaminho;
	}

	public Boolean getHibernateMostrarSQL() {
		return hibernateMostrarSQL;
	}

	public TipoLancamento getSistemaLancamento() {
		return sistemaLancamento;
	}

	public Tema getSistemaTema() {
		return sistemaTema;
	}

	@Override
	public String toString() {
		return "Configuracao [serverDatabase=" + serverDatabase + ", serverHost=" + serverHost + ", serverPort="
				+ serverPort + ", serverBase=" + serverBase + ", unicodeUsar=" + unicodeUsar + ", unicodeEncode="
				+ unicodeEncode + ", logCaminho=" + logCaminho + ", hibernateMostrarSQL=" + hibernateMostrarSQL
				+ ", sistemaLancamento=" + sistemaLancamento + ", sistemaTema=" + sistemaTema + "]";
	}

	public Configuracao() {

	}

	public Configuracao(DataBase serverDatabase, String serverHost, String serverPort, String serverBase,
			String serverUser, String serverPassword, String logCaminho) {
		this.serverDatabase = serverDatabase == null ? DataBase.MySQL : serverDatabase;
		this.serverHost = serverHost;
		this.serverPort = serverPort;
		this.serverBase = serverBase;
		this.serverUser = serverUser;
		this.serverPassword = serverPassword;
		this.unicodeUsar = true;
		this.unicodeEncode = "UTF-8";
		this.logCaminho = logCaminho;
		this.hibernateMostrarSQL = false;
		this.sistemaLancamento = sistemaLancamento == null ? TipoLancamento.HOMOLOGACAO : sistemaLancamento;
		this.sistemaTema = sistemaTema == null ? Tema.WHITE : sistemaTema;
	}

	public Configuracao(DataBase serverDatabase, String serverHost, String serverPort, String serverBase,
			String serverUser, String serverPassword, Boolean unicodeUsar, String unicodeEncode, String logCaminho,
			Boolean hibernateMostrarSQL, TipoLancamento sistemaLancamento, Tema sistemaTema) {
		this.serverDatabase = serverDatabase == null ? DataBase.MySQL : serverDatabase;
		this.serverHost = serverHost;
		this.serverPort = serverPort;
		this.serverBase = serverBase;
		this.serverUser = serverUser;
		this.serverPassword = serverPassword;
		this.unicodeUsar = unicodeUsar;
		this.unicodeEncode = unicodeEncode;
		this.logCaminho = logCaminho;
		this.hibernateMostrarSQL = hibernateMostrarSQL;
		this.sistemaLancamento = sistemaLancamento == null ? TipoLancamento.HOMOLOGACAO : sistemaLancamento;
		this.sistemaTema = sistemaTema == null ? Tema.WHITE : sistemaTema;
	}

}
