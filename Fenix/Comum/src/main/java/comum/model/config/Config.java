package comum.model.config;

import java.io.File;

public class Config {

	public String getCaminhoConfig() {
		String caminho = System.getProperty("user.dir");
		File currentDirFile = new File(caminho);
		caminho = currentDirFile.getParent();
		return caminho;
	}

	public static String verificaConfig() {
		Config config = new Config();
		String caminho = config.getCaminhoConfig();
		File arquivo = new File(caminho + "\\Config.properties");

		if (arquivo.exists()) {
			return arquivo.toString();
		} else {

			CriaConfig cria = new CriaConfig();
			cria.criaConfig(arquivo);

			return arquivo.toString();
		}
	}

	public static String getCaminhoLog() {
		return ProcessaConfig.getCaminhoLog();
	}

}
