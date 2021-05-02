package comum.cep.postmon;

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

public class PostmonClienteWS {
	
	private final static Logger LOGGER = Logger.getLogger(PostmonClienteWS.class.getName());

	/**
	 * Obtem as informações de endereço através de um cep na api Postmon
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
						object.getString("complemento"), object.getString("bairro"), object.getString("cidade"),
						object.getString("estado"), object.getJsonObject("cidade_info").getString("codigo_ibge"), "");
			else
				throw new ExcessaoCep(Mensagens.CEP_SERVICO_INDISPONIVEL);
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.log(Level.WARNING, "{Erro ao consultar o CEP}", e);
			throw new ExcessaoCep(Mensagens.CEP_POSTMON_ERRO);
		}
		return endereco;
	}

	private static JsonObject getCepResponse(String cep) throws IOException, ExcessaoCep, NumberFormatException {

		if (!Validadores.validaCep(cep))
			throw new NumberFormatException("Cep em formato inválido, informar apenas números.");

		JsonObject json = null;
		CloseableHttpClient client = HttpClients.createDefault();
		try {
			HttpGet get = new HttpGet("http://api.postmon.com.br/v1/cep/" + cep);
			HttpResponse response = client.execute(get);

			if (response.getStatusLine().getStatusCode() != 200)
				throw new ExcessaoCep(Mensagens.CEP_SERVICO_INDISPONIVEL);

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
