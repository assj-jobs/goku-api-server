/**
 * 
 */
package br.com.goku.api.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.goku.api.server.model.UsuarioTelefone;

/**
 * @author Antonio Salviano Soares Jr.
 * 
 * 12 de mar de 2021
 */
@Repository
public interface UsuarioTelefoneRepository extends JpaRepository<UsuarioTelefone, Long> {

}
