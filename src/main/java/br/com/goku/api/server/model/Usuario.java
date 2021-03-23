package br.com.goku.api.server.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 * @author Antonio Salviano Soares Jr.
 * 
 * 8 de mar de 2021
 */
@Entity
@NamedQuery(name="Usuario.findAll", query="SELECT u FROM Usuario u")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String email;
	private String nome;
	private String senha;
	private int    snAtivo;
	private List<UsuarioPermissao> usuarioPermissaos;
	private List<UsuarioEndereco> usuarioEnderecos;
	private List<UsuarioTelefone> usuarioTelefones;

	public Usuario() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getSenha() {
		return this.senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@OneToMany(mappedBy="usuario")
	public List<UsuarioPermissao> getUsuarioPermissaos() {
		return this.usuarioPermissaos;
	}

	public void setUsuarioPermissaos(List<UsuarioPermissao> usuarioPermissaos) {
		this.usuarioPermissaos = usuarioPermissaos;
	}

	public UsuarioPermissao addUsuarioPermissao(UsuarioPermissao usuarioPermissao) {
		getUsuarioPermissaos().add(usuarioPermissao);
		usuarioPermissao.setUsuario(this);

		return usuarioPermissao;
	}

	public UsuarioPermissao removeUsuarioPermissao(UsuarioPermissao usuarioPermissao) {
		getUsuarioPermissaos().remove(usuarioPermissao);
		usuarioPermissao.setUsuario(null);

		return usuarioPermissao;
	}

	@OneToMany(mappedBy="usuario")
	public List<UsuarioEndereco> getUsuarioEnderecos() {
		return this.usuarioEnderecos;
	}

	public void setUsuarioEnderecos(List<UsuarioEndereco> usuarioEnderecos) {
		this.usuarioEnderecos = usuarioEnderecos;
	}

	public UsuarioEndereco addUsuarioEndereco(UsuarioEndereco usuarioEndereco) {
		getUsuarioEnderecos().add(usuarioEndereco);
		usuarioEndereco.setUsuario(this);

		return usuarioEndereco;
	}

	public UsuarioEndereco removeUsuarioEndereco(UsuarioEndereco usuarioEndereco) {
		getUsuarioEnderecos().remove(usuarioEndereco);
		usuarioEndereco.setUsuario(null);

		return usuarioEndereco;
	}

	@OneToMany(mappedBy="usuario")
	public List<UsuarioTelefone> getUsuarioTelefones() {
		return this.usuarioTelefones;
	}

	public void setUsuarioTelefones(List<UsuarioTelefone> usuarioTelefones) {
		this.usuarioTelefones = usuarioTelefones;
	}

	public UsuarioTelefone addUsuarioTelefone(UsuarioTelefone usuarioTelefone) {
		getUsuarioTelefones().add(usuarioTelefone);
		usuarioTelefone.setUsuario(this);

		return usuarioTelefone;
	}

	public UsuarioTelefone removeUsuarioTelefone(UsuarioTelefone usuarioTelefone) {
		getUsuarioTelefones().remove(usuarioTelefone);
		usuarioTelefone.setUsuario(null);

		return usuarioTelefone;
	}


	public int getSnAtivo() {
		return snAtivo;
	}


	public void setSnAtivo(int snAtivo) {
		this.snAtivo = snAtivo;
	}

}