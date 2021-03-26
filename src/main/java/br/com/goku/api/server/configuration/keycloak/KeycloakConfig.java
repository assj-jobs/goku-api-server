package br.com.goku.api.server.configuration.keycloak;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;


/**
 * @author Antonio Salviano Soares Jr.
 * 
 * 8 de mar de 2021
 */
public class KeycloakConfig {
	static Keycloak keycloak = null;
    final static String serverUrl = "https://goku-keycloak.herokuapp.com/auth";
	final static String realm = "goku";
    final static String clientId = "goku-web-client";
    final static String clientSecret = "5e7ba75f-cc8f-4064-8f0f-26d63e0fd249";
    final static String userName = "administrator";
    final static String password = "qwerty1234";

	public KeycloakConfig() {
    }

    public static Keycloak getInstance(){
        if(keycloak == null || keycloak.isClosed()){
            keycloak = KeycloakBuilder.builder()
                    .serverUrl(serverUrl)
                    .realm(realm)
                    .grantType(OAuth2Constants.PASSWORD)
                    .username(userName)
                    .password(password)
                    .clientId(clientId)
                    .clientSecret(clientSecret)
                    .resteasyClient(new ResteasyClientBuilder()
                    		.connectionPoolSize(10)
                    		.build()
                    )
                    .build();
        }
        return keycloak;
    }
    
    public static String getRealm() {
    	return realm;
    }
}
