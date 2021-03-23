package br.com.goku.api.server.configuration.keycloak;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import br.com.goku.api.server.model.UserKeycloakResource;

/**
 * @author Antonio Salviano Soares Jr.
 * 
 * 8 de mar de 2021
 */
@Service
public class KeycloakAdminService {
	
	private static final String REALM_NAME = "goku";
	private static final String CLIENT_ID = "goku-web-client";
	private static final String DEFAULT_ROLE = "user";
	
	public void addUser(UserKeycloakResource user) {
		
				
		Keycloak kc = KeycloakConfig.getInstance();
		UsersResource usersResource = kc.realm(KeycloakConfig.getRealm()).users();
		CredentialRepresentation credentialRepresentation = createPasswordCredentials(user.getPassword());
		
		UserRepresentation kcUser = new UserRepresentation();
		kcUser.setUsername(user.getEmail());
		kcUser.setCredentials(Collections.singletonList(credentialRepresentation));
		kcUser.setFirstName(user.getFirstName());
		kcUser.setLastName(user.getLastName());
		kcUser.setEmail(user.getEmail());
		kcUser.setEnabled(true);
		kcUser.setEmailVerified(user.isEmailVerified());
		kcUser.setClientRoles(null);
		Response response =  usersResource.create(kcUser);
		String userId = getCreatedId(response);
		
		
		for (ClientRepresentation clientRepresentation : kc.realm(REALM_NAME).clients().findByClientId(CLIENT_ID)) {
			System.out.print(clientRepresentation.getId());
			RoleRepresentation savedRoleRepresentation = kc.realm(REALM_NAME).clients().get(clientRepresentation.getId()).roles().get(DEFAULT_ROLE).toRepresentation();
		    List<RoleRepresentation> roles = new ArrayList<RoleRepresentation>();
		    roles.add(savedRoleRepresentation);
		    kc.realm(REALM_NAME).users().get(userId).roles().clientLevel(clientRepresentation.getId()).add(roles);
		}
		kc.close();
	}

	private String getCreatedId(Response response) {
	    URI location = response.getLocation();
	    if (!response.getStatusInfo().equals(Response.Status.CREATED)) {
	        Response.StatusType statusInfo = response.getStatusInfo();
	        throw new WebApplicationException("Create method returned status " +
	                statusInfo.getReasonPhrase() + " (Code: " + statusInfo.getStatusCode() + "); expected status: Created (201)", response);
	    }
	    if (location == null) {
	        return null;
	    }
	    String path = location.getPath();
	    return path.substring(path.lastIndexOf('/') + 1);
	}

	private static CredentialRepresentation  createPasswordCredentials(String password) {
		CredentialRepresentation passwordCredentials = new CredentialRepresentation();
		passwordCredentials.setTemporary(false);
		passwordCredentials.setType(CredentialRepresentation.PASSWORD);
		passwordCredentials.setValue(password);
		return passwordCredentials;
	}
}
