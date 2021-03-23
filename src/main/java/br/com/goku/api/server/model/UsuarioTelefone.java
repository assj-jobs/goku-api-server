package br.com.goku.api.server.model;

import java.io.Serializable;
import javax.persistence.*;



/**
 * @author Antonio Salviano Soares Jr.
 * 
 * 8 de mar de 2021
 */
@Entity
@Table(name="usuario_telefone")
@NamedQuery(name="UsuarioTelefone.findAll", query="SELECT u FROM UsuarioTelefone u")
public class UsuarioTelefone implements Serializable {
	private static final long serialVersionUID = 1L;
	private int idUsuarioTelefone;
	private Usuario usuario;
	private Telefone telefone;

	public UsuarioTelefone() {
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_usuario_telefone")
	public int getIdUsuarioTelefone() {
		return this.idUsuarioTelefone;
	}

	public void setIdUsuarioTelefone(int idUsuarioTelefone) {
		this.idUsuarioTelefone = idUsuarioTelefone;
	}

	@ManyToOne
	@JoinColumn(name="id_usuario", referencedColumnName = "id")
	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@ManyToOne
	@JoinColumn(name="id_telefone", referencedColumnName = "id")
	public Telefone getTelefone() {
		return this.telefone;
	}

	public void setTelefone(Telefone telefone) {
		this.telefone = telefone;
	}

}