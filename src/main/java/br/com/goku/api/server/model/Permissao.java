package br.com.goku.api.server.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * @author Antonio Salviano Soares Jr.
 * 
 * 8 de mar de 2021
 */
@Entity
@NamedQuery(name="Permissao.findAll", query="SELECT p FROM Permissao p")
public class Permissao implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long idPermissao;
	private String dsPermissao;
	private List<UsuarioPermissao> usuarioPermissoes;

	public Permissao() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_permissao")
	public Long getIdPermissao() {
		return this.idPermissao;
	}

	public void setIdPermissao(Long idPermissao) {
		this.idPermissao = idPermissao;
	}


	@Column(name="ds_permissao")
	public String getDsPermissao() {
		return this.dsPermissao;
	}

	public void setDsPermissao(String dsPermissao) {
		this.dsPermissao = dsPermissao;
	}

	@OneToMany(mappedBy="permissao")
	public List<UsuarioPermissao> getUsuarioPermissoes() {
		return this.usuarioPermissoes;
	}

	public void setUsuarioPermissoes(List<UsuarioPermissao> usuarioPermissoes) {
		this.usuarioPermissoes = usuarioPermissoes;
	}

	public UsuarioPermissao addUsuarioPermissao(UsuarioPermissao usuarioPermissao) {
		getUsuarioPermissoes().add(usuarioPermissao);
		usuarioPermissao.setPermissao(this);

		return usuarioPermissao;
	}

	public UsuarioPermissao removeUsuarioPermissao(UsuarioPermissao usuarioPermissao) {
		getUsuarioPermissoes().remove(usuarioPermissao);
		usuarioPermissao.setPermissao(null);

		return usuarioPermissao;
	}

}