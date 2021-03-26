/**
 * 
 */
package br.com.goku.api.server.service.address;
import java.util.List;

import br.com.goku.api.server.resource.Endereco;


/**
 * @author Antonio Salviano Soares Jr.
 * 
 * 10 de mar de 2021
 */
public interface IEnderecoService {
	
	List<Endereco> findByAddress(br.com.goku.api.server.resource.Endereco endereco);
	Endereco findByCep(Long cep);

}
