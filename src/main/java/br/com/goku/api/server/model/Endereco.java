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
@NamedQuery(name="Endereco.findAll", query="SELECT e FROM Endereco e")
public class Endereco implements Serializable {
	private static final long serialVersionUID = 1L;
	private int idEndereco;
	private String logradouro; 
	private int numero;
	private String complemento;
	private String bairro;
	private String localidade;
	private String estado;
	private int cep;
	private List<UsuarioEndereco> usuarioEnderecos;

	public Endereco() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_endereco")
	public int getIdEndereco() {
		return this.idEndereco;
	}

	public void setIdEndereco(int idEndereco) {
		this.idEndereco = idEndereco;
	}


	public int getCep() {
		return this.cep;
	}

	public void setCep(int cep) {
		this.cep = cep;
	}


	public String getComplemento() {
		return this.complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}


	public int getNumero() {
		return this.numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	@OneToMany(mappedBy="endereco")
	public List<UsuarioEndereco> getUsuarioEnderecos() {
		return this.usuarioEnderecos;
	}

	public void setUsuarioEnderecos(List<UsuarioEndereco> usuarioEnderecos) {
		this.usuarioEnderecos = usuarioEnderecos;
	}

	public UsuarioEndereco addUsuarioEndereco(UsuarioEndereco usuarioEndereco) {
		getUsuarioEnderecos().add(usuarioEndereco);
		usuarioEndereco.setEndereco(this);

		return usuarioEndereco;
	}

	public UsuarioEndereco removeUsuarioEndereco(UsuarioEndereco usuarioEndereco) {
		getUsuarioEnderecos().remove(usuarioEndereco);
		usuarioEndereco.setEndereco(null);

		return usuarioEndereco;
	}


	public String getLogradouro() {
		return logradouro;
	}


	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}


	public String getBairro() {
		return bairro;
	}


	public void setBairro(String bairro) {
		this.bairro = bairro;
	}


	public String getLocalidade() {
		return localidade;
	}


	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}


	public String getEstado() {
		return estado;
	}


	public void setEstado(String estado) {
		this.estado = estado;
	}

}