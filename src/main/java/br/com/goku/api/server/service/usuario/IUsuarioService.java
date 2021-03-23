package br.com.goku.api.server.service.usuario;

import java.util.List;

import br.com.goku.api.server.resource.Usuario;

/**
 * @author Antonio Salviano Soares Jr.
 * 
 * 8 de mar de 2021
 */
public interface IUsuarioService {
	
	Usuario createUser(Usuario usuario) throws Exception;
	Usuario update(Usuario usuario);
	void 	delete(Long id);
	Usuario findById(Long id);
	List<Usuario> findByAll();
	
}
