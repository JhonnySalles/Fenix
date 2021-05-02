package servidor.configuration;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.flywaydb.core.Flyway;

import comum.model.config.ProcessaConfig;
import comum.model.entities.Configuracao;
import comum.model.exceptions.ExcessaoBd;

public class ManagerFactory {

	private final static Logger LOGGER = Logger.getLogger(ManagerFactory.class.getName());

	private static Properties PROPERTIE_BD;
	private static Configuracao CONFIG_BD;
	private static String URL_DB;
	private static EntityManagerFactory EMF;

	public static EntityManager getEtityManager() {
		return EMF.createEntityManager();
	}

	/* Substitui a configuração do arquivo de persistencia pelo arquivo de config */
	private static Properties getConfigBD() throws ExcessaoBd {
		CONFIG_BD = ProcessaConfig.getConfiguracaoSistema();

		if (CONFIG_BD == null || CONFIG_BD.getServerHost().isEmpty())
			throw new ExcessaoBd(
					"Arquivo de configuração do banco não encontrado ou caminho do servidor não informado.");

		Properties props = new Properties();
		props.setProperty("javax.persistence.jdbc.driver", "com.mysql.cj.jdbc.Driver");
		props.setProperty("javax.persistence.jdbc.user", CONFIG_BD.getServerUser());
		props.setProperty("javax.persistence.jdbc.password", CONFIG_BD.getServerPassword());

		URL_DB = "jdbc:mysql://" + CONFIG_BD.getServerHost() + ":" + CONFIG_BD.getServerPort() + "/"
				+ CONFIG_BD.getServerBase() + "?useTimezone=true&serverTimezone=UTC&useUnicode=true";

		props.setProperty("javax.persistence.jdbc.url", URL_DB);

		/* Configurações específicas do Hibernate */
		props.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
		// Quem irá cuidar da migração do banco é o flyway. Será usado o update somente
		// em teste.
		props.setProperty("hibernate.hbm2ddl.auto", "none");
		props.setProperty("hibernate.show_sql", CONFIG_BD.getHibernateMostrarSQL() ? "true" : "false");
		props.setProperty("hibernate.format_sql", "false");
		return props;
	}

	public static void closeConection() {
		EMF.close();
	}

	public static void iniciaBD() throws ExcessaoBd {
		try {
			PROPERTIE_BD = getConfigBD();

			Flyway flyway = Flyway.configure()
					.dataSource(URL_DB, CONFIG_BD.getServerUser(), CONFIG_BD.getServerPassword())
					.load();
			flyway.migrate();

			EMF = Persistence.createEntityManagerFactory("PersistenciaServidor", PROPERTIE_BD);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "{Erro ao carregar o EntityManager}", e);
			e.printStackTrace();
			throw new ExcessaoBd(e.toString());
		}
	}

}
