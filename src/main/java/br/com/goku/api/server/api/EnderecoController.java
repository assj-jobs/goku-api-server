/**
 * 
 */
package br.com.goku.api.server.api;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.goku.api.server.AddressApi;
import br.com.goku.api.server.resource.Endereco;
import br.com.goku.api.server.service.address.IEnderecoService;

/**
 * @author Antonio Salviano Soares Jr.
 * 
 * 10 de mar de 2021
 */
@RolesAllowed(value = {"admin", "user"})
@RestController
@RequestMapping(value = "/api/address")
public class EnderecoController implements AddressApi {

	static final Logger logger = LogManager.getLogger(EnderecoController.class.getName());

	@Autowired
	private IEnderecoService service;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Endereco>> findByAddress(@RequestBody Endereco body) {
		List<Endereco> enderecos = service.findByAddress(body);
		return ResponseEntity.status(HttpStatus.OK).body(enderecos);
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, params = "cep")
	public ResponseEntity<Endereco> findByCep(@RequestParam("cep") Long cep) {
		Endereco endereco = this.service.findByCep(cep);
		return ResponseEntity.status(HttpStatus.OK).body(endereco);
	}

}
