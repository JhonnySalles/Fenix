package comum.model.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import comum.model.alerts.Alertas;
import comum.model.encode.Encryption;
import comum.model.entities.Configuracao;
import comum.model.enums.DataBase;
import comum.model.enums.Tema;
import comum.model.enums.TipoLancamento;
import javafx.scene.control.Alert.AlertType;

public class ProcessaConfig {

	private final static Logger LOGGER = Logger.getLogger(ProcessaConfig.class.getName());

	public static Configuracao CONFIGURACAOSYSTEMA;
	public final static File f = new File(Config.verificaConfig());

	// O bloco static é chamado na inicialização para
	// criar os métodos staticos.
	static {
		leituraConfig(f);
	}

	private static void leituraConfig(File arquivo) {
		Properties prop = new Properties();

		if (prop.isEmpty()) {
			CriaConfig cria = new CriaConfig();
			cria.criaConfig(arquivo);
		}

		try {
			// Carrega o arquivo proprierts para ser manipulado
			prop.load(new FileInputStream(arquivo));

			CONFIGURACAOSYSTEMA = new Configuracao(DataBase.valueOf(prop.getProperty("prop.server.database")),
					prop.getProperty("prop.server.host"), prop.getProperty("prop.server.port"),
					prop.getProperty("prop.server.base"), prop.getProperty("prop.server.user"),
					Encryption.decodifica(prop.getProperty("prop.server.password")),
					(prop.getProperty("prop.unicode.usar") == "1" ? true : false),
					(prop.getProperty("prop.unicode.encode").isEmpty() ? "UTF-8"
							: prop.getProperty("prop.unicode.encode")),
					prop.getProperty("prop.log.caminho"),
					(prop.getProperty("prop.hibernate.mostrarSql") == "1" ? true : false),
					TipoLancamento.valueOf(prop.getProperty("prop.sistema.lancamento")),
					Tema.valueOf(prop.getProperty("prop.sistema.tema")));

			validaConfig();

		} catch (FileNotFoundException e) {
			Alertas.Alerta(AlertType.ERROR, "Erro",
					"Não foi possivel ler o arquivo de configuração,  verifique o caminho: " + f.toString());
			e.printStackTrace();
			LOGGER.log(Level.SEVERE, "{Erro ao carregar o arquivo config: " + f.toString() + "}", e);
		} catch (IOException e) {
			Alertas.Alerta(AlertType.ERROR, "Erro", "Não foi possivel ler o arquivo de configuração.");
			e.printStackTrace();
			LOGGER.log(Level.SEVERE, "{Erro ao carregar o arquivo config: " + f.toString() + "}", e);
		}
	}

	private static void validaConfig() throws IOException {
		if (CONFIGURACAOSYSTEMA == null) {
			Alertas.Alerta(AlertType.WARNING, "Aviso", "Arquivo de configuração não localizado.");
			throw new IOException("Arquivo de configuração não localizado.");
		}

		if (CONFIGURACAOSYSTEMA.getServerHost().isEmpty())
			Alertas.Alerta(AlertType.WARNING, "Aviso",
					"Arquivo de configuração com informações faltando, não localizado ip do servidor.");

		if (CONFIGURACAOSYSTEMA.getServerPort().isEmpty())
			Alertas.Alerta(AlertType.WARNING, "Aviso",
					"Arquivo de configuração com informações faltando, não localizado a porta do servidor.");

		if (CONFIGURACAOSYSTEMA.getServerBase().isEmpty())
			Alertas.Alerta(AlertType.WARNING, "Aviso",
					"Arquivo de configuração com informações faltando, não localizado a base de dados.");

		if (CONFIGURACAOSYSTEMA.getServerUser().isEmpty() || CONFIGURACAOSYSTEMA.getServerPassword().isEmpty())
			Alertas.Alerta(AlertType.WARNING, "Aviso",
					"Arquivo de configuração com informações faltando, não usuário ou senha de acesso.");
	}

	public static void gravaConfig(Configuracao configuracao) {
		Properties prop = new Properties();

		try {
			// Carrega o arquivo proprierts para ser manipulado
			prop.load(new FileInputStream(f));

			// Carrega as informacoes para a entidade
			prop.setProperty("prop.server.database", configuracao.getServerDatabase().toString());
			prop.setProperty("prop.server.host", configuracao.getServerHost().trim());
			prop.setProperty("prop.server.port", configuracao.getServerPort().trim());
			prop.setProperty("prop.server.base", configuracao.getServerBase().trim());
			prop.setProperty("prop.server.user", configuracao.getServerUser().trim());
			prop.setProperty("prop.server.password", Encryption.codifica(configuracao.getServerPassword()));

			prop.setProperty("prop.unicode.usar", configuracao.getUnicodeUsar() ? "1" : "0");
			prop.setProperty("prop.unicode.encode", configuracao.getUnicodeEncode().trim());

			prop.setProperty("prop.log.caminho", configuracao.getLogCaminho().trim());

			prop.setProperty("prop.sistema.tema", configuracao.getSistemaTema().toString());
			prop.setProperty("prop.sistema.lancamento", configuracao.getSistemaLancamento().toString());

			FileOutputStream arquivoOut = new FileOutputStream(f);
			prop.store(arquivoOut, null);

			Alertas.Concluido("Arquivo salvo", "Arquivo de configuração salvo com sucesso.");
		} catch (FileNotFoundException e) {
			Alertas.Alerta(AlertType.ERROR, "Erro",
					"Não foi possivel salvar o arquivo de configuração, verifique o caminho: " + f.toString());
			e.printStackTrace();
		} catch (IOException e) {
			Alertas.Alerta(AlertType.ERROR, "Erro", "Não foi possivel salvar o arquivo de configuração.");
			e.printStackTrace();
		}
	}

	public static Configuracao getConfiguracaoSistema() {
		return CONFIGURACAOSYSTEMA;
	}

	public static String getCaminhoLog() {
		return CONFIGURACAOSYSTEMA.getLogCaminho();
	}

}
