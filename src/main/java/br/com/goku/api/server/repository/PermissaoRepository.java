package br.com.goku.api.server.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.goku.api.server.model.Permissao;

/**
 * @author Antonio Salviano Soares Jr.
 * 
 * 8 de mar de 2021
 */
public interface PermissaoRepository extends JpaRepository<Permissao, Long> {
	Optional<Permissao> findByIdPermissao(String id);
}
