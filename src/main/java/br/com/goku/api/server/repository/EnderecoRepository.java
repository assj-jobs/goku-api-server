package br.com.goku.api.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.goku.api.server.model.Endereco;

/**
 * @author Antonio Salviano Soares Jr.
 * 
 * 8 de mar de 2021
 */
@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

}
