package comum.cep.viacep;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import comum.model.constraints.Validadores;
import comum.model.entities.Cep;
import comum.model.exceptions.ExcessaoCep;
import comum.model.messages.Mensagens;

public class ViaCepClienteWS {

	private final static Logger LOGGER = Logger.getLogger(ViaCepClienteWS.class.getName());

	/**
	 * Obtem as informações de endereço através de um cep na api ViaCep
	 * 
	 * @param Cep <b>String</b> no formato 00000000
	 * @return Cep
	 * @throws ExcessaoCep
	 */
	public static Cep getEnderecoPorCep(String cep) throws ExcessaoCep, NumberFormatException {
		JsonObject object;
		Cep endereco = null;
		try {
			object = getCepResponse(cep);
			if (object.get("erro") == null)
				endereco = new Cep(object.getString("cep"), object.getString("logradouro"),
						object.getString("complemento"), object.getString("bairro"), object.getString("localidade"),
						object.getString("uf"), object.getString("ibge"), object.getString("gia"));
			else
				throw new ExcessaoCep(Mensagens.CEP_SERVICO_INDISPONIVEL);
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.log(Level.WARNING, "{Erro ao consultar o CEP}", e);
			throw new ExcessaoCep(Mensagens.CEP_VIACEP_ERRO);
		}
		return endereco;
	}

	private static JsonObject getCepResponse(String cep) throws IOException, ExcessaoCep, NumberFormatException {

		if (!Validadores.validaCep(cep))
			throw new NumberFormatException("Cep em formato inválido, informar apenas números.");

		JsonObject json = null;
		CloseableHttpClient client = HttpClients.createDefault();
		try {
			HttpGet get = new HttpGet("https://viacep.com.br/ws/" + cep + "/json");
			HttpResponse response = client.execute(get);

			HttpEntity entity = response.getEntity();

			json = Json.createReader(entity.getContent()).readObject();

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.log(Level.WARNING, "{Erro ao consultar o CEP}", e);
			throw new ExcessaoCep(Mensagens.CEP_SERVICO_INDISPONIVEL);
		} finally {
			client.close();
		}

		return json;
	}

}
