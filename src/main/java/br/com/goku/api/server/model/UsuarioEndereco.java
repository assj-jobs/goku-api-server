package br.com.goku.api.server.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * @author Antonio Salviano Soares Jr.
 * 
 * 8 de mar de 2021
 */
@Entity
@Table(name="usuario_endereco")
@NamedQuery(name="UsuarioEndereco.findAll", query="SELECT u FROM UsuarioEndereco u")
public class UsuarioEndereco implements Serializable {
	private static final long serialVersionUID = 1L;
	private int idUsuarioEndereco;
	private Endereco endereco;
	private Usuario usuario;

	public UsuarioEndereco() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_usuario_endereco")
	public int getIdUsuarioEndereco() {
		return this.idUsuarioEndereco;
	}

	public void setIdUsuarioEndereco(int idUsuarioEndereco) {
		this.idUsuarioEndereco = idUsuarioEndereco;
	}


	//bi-directional many-to-one association to Endereco
	@ManyToOne
	@JoinColumn(name="id_endereco", referencedColumnName = "id_endereco")
	public Endereco getEndereco() {
		return this.endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}


	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="id_usuario", referencedColumnName = "id")
	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}