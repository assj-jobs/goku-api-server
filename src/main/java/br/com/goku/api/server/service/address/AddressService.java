/**
 * 
 */
package br.com.goku.api.server.service.address;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import br.com.goku.api.server.resource.Endereco;

/**
 * @author Antonio Salviano Soares Jr.
 * 
 * 10 de mar de 2021
 */

@Service
public class AddressService implements IAddressService {

	public Endereco findByCep(Long cep) throws JSONException, IOException {
		String url = "http://viacep.com.br/ws/" + cep + "/json/";
		Endereco endereco = new Endereco();
		JSONObject obj;
		obj = new JSONObject(getHttpGET(url));
		if (!obj.has("erro")) {
			endereco.setCep(Integer.valueOf(obj.getString("cep").replace("-", "")));
			endereco.setLogradouro(obj.getString("logradouro"));
			endereco.setComplemento(obj.getString("complemento"));
			endereco.setBairro(obj.getString("bairro"));
			endereco.setLocalidade(obj.getString("localidade"));
			endereco.setEstado(obj.getString("uf"));
		}
		return endereco;
	}

	public List<Endereco> findByAddress(Endereco endereco) throws JSONException, IOException {
		String url = "http://viacep.com.br/ws/" + endereco.getEstado().toUpperCase() + "/" + endereco.getLocalidade() + "/" + endereco.getLogradouro() + "/json/";
		JSONArray enderecos;
		List<Endereco> result = new ArrayList<Endereco>();
		enderecos = new JSONArray(getHttpGET(url));
		if (enderecos.length() > 0) {
			for (int i = 0; i < enderecos.length(); i++) {
				JSONObject obj = enderecos.getJSONObject(i);
				if (!obj.has("erro")) {
					Endereco end = new Endereco();
					end.setCep(Integer.valueOf(obj.getString("cep").replace("-", "")));
					end.setLogradouro(obj.getString("logradouro"));
					end.setComplemento(obj.getString("complemento"));
					end.setBairro(obj.getString("bairro"));
					end.setLocalidade(obj.getString("localidade"));
					end.setEstado(obj.getString("uf"));
					result.add(end);
				}
			}
		}
		return result;
	}

	public final String getHttpGET(String urlToRead) throws IOException {
		StringBuilder result = new StringBuilder();

		URL url = new URL(urlToRead);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");

		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line;
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		return result.toString();
	}

}
