/**
 * 
 */
package br.com.goku.api.server.service.address;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.goku.api.server.resource.Endereco;
import gherkin.deps.com.google.gson.Gson;
import gherkin.deps.com.google.gson.reflect.TypeToken;

/**
 * @author Antonio Salviano Soares Jr.
 * 
 * 10 de mar de 2021
 */
@Service
@Transactional
public class EnderecoService implements IEnderecoService {
	static final Logger logger = LogManager.getLogger(EnderecoService.class.getName());

	public Endereco findByCep(Long cep) {
		String url = "http://viacep.com.br/ws/" + cep + "/json/";
		Gson gson = new Gson();
		Endereco endereco = gson.fromJson(getHttpGET(url), Endereco.class);
		return endereco;
	}

	public List<Endereco> findByAddress(Endereco endereco)  {
		String url = "http://viacep.com.br/ws/" + endereco.getEstado().toUpperCase() + "/" + endereco.getLocalidade() + "/" + endereco.getLogradouro() + "/json/";
		Gson gson = new Gson();
		Type enderecoListType = new TypeToken<ArrayList<Endereco>>(){}.getType();
		List<Endereco> result = gson.fromJson(getHttpGET(url), enderecoListType);
		return result;
	}

	public final String getHttpGET(String urlToRead) {
		String enderecos = "";
		StringBuilder result = new StringBuilder();
		URL url;
		try {
			url = new URL(urlToRead);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			enderecos = result.toString().replaceAll("-", "").replaceAll("uf", "estado");
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return enderecos;
	}

}
