/**
 * 
 */
package br.com.goku.api.server.api;

import java.io.IOException;
import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
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
import br.com.goku.api.server.service.address.AddressService;

/**
 * @author Antonio Salviano Soares Jr.
 * 
 * 10 de mar de 2021
 */
@RolesAllowed(value = {"admin", "user"})
@RestController
@RequestMapping(value = "/api/address")
public class AddressController implements AddressApi {
	
	@Autowired
	private AddressService addressService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Endereco>> findByAddress(@RequestBody Endereco body) {
		List<Endereco> enderecos;
		try {
			enderecos = addressService.findByAddress(body);
		} catch (JSONException | IOException e) {
			return ResponseEntity.status(HttpStatus.OK).body(null);
		}
		return ResponseEntity.status(HttpStatus.OK).body(enderecos);
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, params = "cep")
	public ResponseEntity<Endereco> findByCep(@RequestParam("cep") Long cep) {
		Endereco endereco;
		try {
			endereco = this.addressService.findByCep(cep);
		} catch (JSONException | IOException e) {
			return ResponseEntity.status(HttpStatus.OK).body(null);
		}
		return ResponseEntity.status(HttpStatus.OK).body(endereco);
	}

}
