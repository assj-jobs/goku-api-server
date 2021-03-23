/**
 * 
 */
package br.com.goku.api.server.service.address;
import java.io.IOException;
import java.util.List;

import org.springframework.boot.configurationprocessor.json.JSONException;

import br.com.goku.api.server.resource.Endereco;


/**
 * @author Antonio Salviano Soares Jr.
 * 
 * 10 de mar de 2021
 */
public interface IAddressService {
	
	List<Endereco> findByAddress(br.com.goku.api.server.resource.Endereco endereco) throws JSONException, IOException;
	Endereco findByCep(Long cep) throws JSONException, IOException;

}
