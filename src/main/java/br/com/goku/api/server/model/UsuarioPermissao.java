package br.com.goku.api.server.model;

import java.io.Serializable;
import javax.persistence.*;



/**
 * @author Antonio Salviano Soares Jr.
 * 
 * 8 de mar de 2021
 */

@Entity
@Table(name="usuario_permissao")
@NamedQuery(name="UsuarioPermissao.findAll", query="SELECT u FROM UsuarioPermissao u")
public class UsuarioPermissao implements Serializable {
	private static final long serialVersionUID = 1L;
	private int idUsuarioPermissao;
	private Permissao permissao;
	private Usuario usuario;

	public UsuarioPermissao() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_usuario_permissao")
	public int getIdUsuarioPermissao() {
		return this.idUsuarioPermissao;
	}

	public void setIdUsuarioPermissao(int idUsuarioPermissao) {
		this.idUsuarioPermissao = idUsuarioPermissao;
	}


	//bi-directional many-to-one association to Permissao
	@ManyToOne
	@JoinColumn(name="id_permissao")
	public Permissao getPermissao() {
		return this.permissao;
	}

	public void setPermissao(Permissao permissao) {
		this.permissao = permissao;
	}


	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="id_usuario")
	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}