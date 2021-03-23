package br.com.goku.api.server.service.usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.keycloak.authorization.client.util.HttpResponseException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.goku.api.server.configuration.keycloak.KeycloakAdminService;
import br.com.goku.api.server.model.Endereco;
import br.com.goku.api.server.model.Permissao;
import br.com.goku.api.server.model.Telefone;
import br.com.goku.api.server.model.UserKeycloakResource;
import br.com.goku.api.server.model.Usuario;
import br.com.goku.api.server.model.UsuarioEndereco;
import br.com.goku.api.server.model.UsuarioPermissao;
import br.com.goku.api.server.model.UsuarioTelefone;
import br.com.goku.api.server.repository.EnderecoRepository;
import br.com.goku.api.server.repository.PermissaoRepository;
import br.com.goku.api.server.repository.TelefoneRepository;
import br.com.goku.api.server.repository.UsuarioEnderecoRepository;
import br.com.goku.api.server.repository.UsuarioPermissaoRepository;
import br.com.goku.api.server.repository.UsuarioRepository;
import br.com.goku.api.server.repository.UsuarioTelefoneRepository;
import io.jsonwebtoken.lang.Collections;

/**
 * @author Antonio Salviano Soares Jr.
 * 
 * 8 de mar de 2021
 */
@Service
@Transactional
public class UsuarioService implements IUsuarioService {

	private ModelMapper modelMapper = new ModelMapper();

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private TelefoneRepository telefoneRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private UsuarioPermissaoRepository usuarioPermissaoRepository;

	@Autowired
	private PermissaoRepository permissaoRepository;

	@Autowired
	private UsuarioTelefoneRepository usuarioTelefoneRepository;

	@Autowired
	private UsuarioEnderecoRepository usuarioEnderecoRepository;

	@Autowired
	private KeycloakAdminService keycloakAdminService; 

	public br.com.goku.api.server.resource.Usuario createUser(br.com.goku.api.server.resource.Usuario usuarioResource) throws Exception {

		Usuario usuarioNovo = new Usuario();
		if(usuarioResource != null) {
			Optional<Usuario> usuarioDB = this.usuarioRepository.findByEmail(usuarioResource.getEmail());
			if(!usuarioDB.isPresent()) {

				usuarioNovo = modelMapper.map(usuarioResource, Usuario.class);
				usuarioNovo = usuarioRepository.save(usuarioNovo);

				Optional<Permissao> permissao = permissaoRepository.findById(2L);
				UsuarioPermissao usuarioPermissao = new UsuarioPermissao();
				usuarioPermissao.setUsuario(usuarioNovo);	
				usuarioPermissao.setPermissao(permissao.get());
				usuarioPermissaoRepository.save(usuarioPermissao);

				List<UsuarioTelefone> usuarioTelefones = setarListaTelefones(usuarioResource, usuarioNovo);
				List<UsuarioEndereco> usuarioEnderecos = setarListaEnderecos(usuarioResource, usuarioNovo);

				usuarioNovo.setUsuarioTelefones(usuarioTelefones);
				usuarioNovo.setUsuarioEnderecos(usuarioEnderecos);
				usuarioNovo = usuarioRepository.save(usuarioNovo);

				/*
				 * CRIAR USUÁRIO KEYCLOAK
				 */
				UserKeycloakResource userKeycloak = new UserKeycloakResource();
				userKeycloak.setFirstName(usuarioNovo.getNome().split(" ")[0]);
				userKeycloak.setLastName(usuarioNovo.getNome().split(" ")[usuarioNovo.getNome().split(" ").length - 1]);
				userKeycloak.setEmail(usuarioNovo.getEmail());
				userKeycloak.setPassword(usuarioNovo.getSenha());
				keycloakAdminService.addUser(userKeycloak);
				usuarioResource = parseUserDBToUserResource(usuarioNovo);
			}else {
				throw new Exception("Usuário já existente!");
			}
		}
		return usuarioResource;
	}


	public br.com.goku.api.server.resource.Usuario update(br.com.goku.api.server.resource.Usuario usuarioResource) {
		if(usuarioResource != null) {
			Optional<Usuario> usuario = this.usuarioRepository.findByEmail(usuarioResource.getEmail());
			if(usuario.isPresent()) {
				Usuario usuarioDB = usuario.get();
				usuarioDB.setNome(usuarioResource.getNome());
				usuarioDB.setEmail(usuarioResource.getEmail());
				usuarioEnderecoRepository.deleteAll(usuarioDB.getUsuarioEnderecos());
				usuarioTelefoneRepository.deleteAll(usuarioDB.getUsuarioTelefones());
				List<UsuarioTelefone> usuarioTelefones = setarListaTelefones(usuarioResource, usuarioDB);
				List<UsuarioEndereco> usuarioEnderecos = setarListaEnderecos(usuarioResource, usuarioDB);
				usuarioDB.setUsuarioTelefones(usuarioTelefones);
				usuarioDB.setUsuarioEnderecos(usuarioEnderecos);
				usuarioDB = usuarioRepository.save(usuarioDB);
				usuarioResource = parseUserDBToUserResource(usuarioDB);
			}
		}
		return usuarioResource;
	}

	public void delete(Long id) {
		Optional<Usuario> user = this.usuarioRepository.findById(id);
		if(user.isPresent()) {
			this.usuarioRepository.delete(user.get());
		}
	}

	public br.com.goku.api.server.resource.Usuario findById(Long id) {
		Optional<Usuario> user = this.usuarioRepository.findById(id);
		if(user.isPresent()) {
			Usuario usuario = user.get();
			br.com.goku.api.server.resource.Usuario usuarioResource = parseUserDBToUserResource(usuario);
			return usuarioResource;
		}
		return null;
	}
	

	public List<br.com.goku.api.server.resource.Usuario> findByAll() {
		List<Usuario> usuarios = new ArrayList<>();
		usuarioRepository.findAll().forEach(usuarios::add);
		List<br.com.goku.api.server.resource.Usuario> usuariosResources = new ArrayList<br.com.goku.api.server.resource.Usuario>();
		for (Usuario usuario : usuarios) {
			usuariosResources.add(parseUserDBToUserResource(usuario));
		}
		return usuariosResources;
	}

	private br.com.goku.api.server.resource.Usuario parseUserDBToUserResource(Usuario usuario) {
		List<br.com.goku.api.server.resource.Endereco> enderecos = converterEndereco(usuario);
		List<br.com.goku.api.server.resource.Telefone> telefones = converterTelefone(usuario);
		br.com.goku.api.server.resource.Usuario usuarioResource = modelMapper.map(usuario, br.com.goku.api.server.resource.Usuario.class);
		usuarioResource.setEnderecos(enderecos);
		usuarioResource.setTelefones(telefones);
		return usuarioResource;
	}

	private List<br.com.goku.api.server.resource.Telefone> converterTelefone(Usuario usuario) {
		List<br.com.goku.api.server.resource.Telefone> telefones = usuario.getUsuarioTelefones()
				.stream()
				.map(usuarioTelefone -> 
					modelMapper.map(usuarioTelefone.getTelefone(), 
									br.com.goku.api.server.resource.Telefone.class))
				.collect(Collectors.toList());
		return telefones;
	}
	
	private List<br.com.goku.api.server.resource.Endereco> converterEndereco(Usuario usuario) {
		List<br.com.goku.api.server.resource.Endereco> enderecos = usuario.getUsuarioEnderecos()
				.stream()
				.map(usuarioEndereco -> 
					modelMapper.map(usuarioEndereco.getEndereco(), br.com.goku.api.server.resource.Endereco.class))
				.collect(Collectors.toList());
		return enderecos;
	}

	private List<UsuarioEndereco> setarListaEnderecos(br.com.goku.api.server.resource.Usuario usuarioResource,
			Usuario usuario) {
		List<UsuarioEndereco> usuarioEnderecos = new ArrayList<UsuarioEndereco>();
		for (br.com.goku.api.server.resource.Endereco end : usuarioResource.getEnderecos()) {
			UsuarioEndereco ue = new UsuarioEndereco();
			Endereco endereco = modelMapper.map(end, Endereco.class);
			endereco = enderecoRepository.save(endereco);
			ue.setUsuario(usuario);
			ue.setEndereco(endereco);
			usuarioEnderecoRepository.save(ue);
			usuarioEnderecos.add(ue);
		}
		return usuarioEnderecos;
	}

	private List<UsuarioTelefone> setarListaTelefones(br.com.goku.api.server.resource.Usuario usuarioResource,
			Usuario usuario) {
		List<UsuarioTelefone> usuarioTelefones = new ArrayList<UsuarioTelefone>();
		for (br.com.goku.api.server.resource.Telefone tel : usuarioResource.getTelefones()) {
			UsuarioTelefone usuarioTelefone = new UsuarioTelefone();
			Telefone telefone = modelMapper.map(tel, Telefone.class);
			telefone.setSnAtivo("S"); 
			telefone = telefoneRepository.save(telefone);
			usuarioTelefone.setUsuario(usuario);
			usuarioTelefone.setTelefone(telefone);
			usuarioTelefoneRepository.save(usuarioTelefone);
			usuarioTelefones.add(usuarioTelefone);
		}
		return usuarioTelefones;
	}


}
