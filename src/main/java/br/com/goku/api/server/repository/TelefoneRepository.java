package br.com.goku.api.server.repository;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.goku.api.server.model.Telefone;

/**
 * @author Antonio Salviano Soares Jr.
 * 
 * 8 de mar de 2021
 */
public interface TelefoneRepository extends JpaRepository<Telefone, Long>{

    public Telefone findTelefoneByNumero(BigInteger telefone);

}
