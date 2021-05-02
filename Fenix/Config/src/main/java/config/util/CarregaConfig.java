package config.util;

import comum.model.config.ProcessaConfig;
import comum.model.entities.Configuracao;
import config.controller.TelaConfiguracaoController;

public class CarregaConfig {

	public static Configuracao dadosConexao = ProcessaConfig.getConfiguracaoSistema();

	public void processaConfig(TelaConfiguracaoController cntr) {
		cntr.setConfig(dadosConexao);
	}

}
