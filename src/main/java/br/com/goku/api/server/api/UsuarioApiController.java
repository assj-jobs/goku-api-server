package br.com.goku.api.server.api;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.goku.api.server.UserApi;
import br.com.goku.api.server.resource.Usuario;
import br.com.goku.api.server.service.usuario.IUsuarioService;


/**
 * @author Antonio Salviano Soares Jr.
 * 
 * 8 de mar de 2021
 */


@RolesAllowed("admin")
@RestController
@RequestMapping(value = "/api/user")
public class UsuarioApiController implements UserApi {
	
	static final String USER_REMOVE_SUCCESS = "Usuário removido com sucesso";
	static final String USER_REMOVE_FAIL = "Não foi possível remover o usuário";
	static final Logger logger = LogManager.getLogger(UsuarioApiController.class.getName());
	
	@Autowired
	private IUsuarioService service;

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Usuario> create(@RequestBody Usuario usuario) {
		try {
			Usuario novoUsuario = this.service.createUser(usuario);
			return ResponseEntity.status(HttpStatus.OK).body(novoUsuario);			
		}catch(Exception e){
			logger.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
		}
	}

	@PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Usuario> update(@RequestBody Usuario usuario) {
		try {
			Usuario usuarioAlterado = this.service.update(usuario);
			return ResponseEntity.status(HttpStatus.OK).body(usuarioAlterado);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
		 
	}
	
	@DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE, params = "id")
	public ResponseEntity<String> delete(@RequestParam Long id) {
		try {
			this.service.delete(id);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(UsuarioApiController.USER_REMOVE_FAIL); 
		}
		return ResponseEntity.status(HttpStatus.OK).body(UsuarioApiController.USER_REMOVE_SUCCESS); 
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, params = "id")
	public ResponseEntity<Usuario> finById(@RequestParam Long id) {
		try {
			Usuario usuario = this.service.findById(id);
			return ResponseEntity.status(HttpStatus.OK).body(usuario);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
		 
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Usuario>> findAll() {
		try {
			List<Usuario> usuarios =  this.service.findByAll();
			return ResponseEntity.status(HttpStatus.OK).body(usuarios); 
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
}
