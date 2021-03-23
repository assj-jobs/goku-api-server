/**
 * 
 */
package br.com.goku.api.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.goku.api.server.model.UsuarioEndereco;

/**
 * @author Antonio Salviano Soares Jr.
 * 
 * 12 de mar de 2021
 */
public interface UsuarioEnderecoRepository extends JpaRepository<UsuarioEndereco, Long> {

}
