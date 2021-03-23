package br.com.goku.api.server.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.goku.api.server.model.Usuario;

/**
 * @author Antonio Salviano Soares Jr.
 * 
 * 8 de mar de 2021
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	public Optional<Usuario> findByEmail(String email);

}
