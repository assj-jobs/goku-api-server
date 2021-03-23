package br.com.goku.api.server.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigInteger;
import java.util.List;

/**
 * @author Antonio Salviano Soares Jr.
 * 
 * 8 de mar de 2021
 */
@Entity
@NamedQuery(name="Telefone.findAll", query="SELECT t FROM Telefone t")
public class Telefone implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private Integer ddd;
	private Long numero;
	private String snAtivo;
	private List<UsuarioTelefone> usuarioTelefones;

	public Telefone() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id= id;
	}
	
	@Column(name="ddd")
	public Integer getDdd() {
		return this.ddd;
	}

	public void setDdd(Integer ddd) {
		this.ddd = ddd;
	}
	


	@Column(name="numero")
	public Long getNumero() {
		return this.numero;
	}
	
	public void setNumero(Long numero) {
		this.numero = numero;
	}
	
	
	@Column(name="sn_ativo")
	public String getSnAtivo() { return this.snAtivo; }

	public void setSnAtivo(String snAtivo) { this.snAtivo = snAtivo; }

	//bi-directional many-to-one association to UsuarioTelefone
	@OneToMany(mappedBy="telefone")
	public List<UsuarioTelefone> getUsuarioTelefones() {
		return this.usuarioTelefones;
	}

	public void setUsuarioTelefones(List<UsuarioTelefone> usuarioTelefones) {
		this.usuarioTelefones = usuarioTelefones;
	}

	

	public UsuarioTelefone addUsuarioTelefone(UsuarioTelefone usuarioTelefone) {
		getUsuarioTelefones().add(usuarioTelefone);
		usuarioTelefone.setTelefone(this);

		return usuarioTelefone;
	}

	public UsuarioTelefone removeUsuarioTelefone(UsuarioTelefone usuarioTelefone) {
		getUsuarioTelefones().remove(usuarioTelefone);
		usuarioTelefone.setTelefone(null);

		return usuarioTelefone;
	}

}