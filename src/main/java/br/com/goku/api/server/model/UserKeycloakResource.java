package br.com.goku.api.server.model;

import org.keycloak.representations.idm.CredentialRepresentation;

/**
 * @author Antonio Salviano Soares Jr.
 * 
 * 8 de mar de 2021
 */
public class UserKeycloakResource {
	private String id;
	private String firstName;
    private String lastName;
    private String password;
    private String email;
    private boolean emailVerified;
    private CredentialRepresentation credentialRepresentation;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

	public boolean isEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(boolean emailVerified) {
		this.emailVerified = emailVerified;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public CredentialRepresentation getCredentialRepresentation() {
		return credentialRepresentation;
	}

	public void setCredentialRepresentation(CredentialRepresentation credentialRepresentation) {
		this.credentialRepresentation = credentialRepresentation;
	}
}
