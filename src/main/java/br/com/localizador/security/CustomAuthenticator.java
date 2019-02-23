package br.com.localizador.security;

import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.seam.international.status.Messages;
import org.jboss.seam.international.status.builder.BundleKey;
import org.jboss.seam.security.Authenticator;
import org.jboss.seam.security.BaseAuthenticator;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;
import org.picketlink.idm.impl.api.PasswordCredential;
import org.picketlink.idm.impl.api.model.SimpleUser;

import br.com.localizador.entity.Usuario;
import br.com.localizador.enums.ConstantesMensagem;
import br.com.localizador.enums.PerfilTypeEnum;
import br.com.localizador.service.UsuarioService;

@Named
public class CustomAuthenticator extends BaseAuthenticator implements Authenticator {
	
	private @Inject Identity identity;
	private @Inject Credentials credentials;
	private @Inject CustomIdentity customIdentity;
	private @Inject UsuarioService usuarioService;
	private @Inject Messages messages;

	@Override
	public void authenticate() {
		try {
			this.login();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String login() throws Exception {
		Usuario usuario = this.usuarioService.login(this.credentials.getUsername(),
				((PasswordCredential)this.credentials.getCredential()).getValue());
		if (usuario != null) {
			setStatus(AuthenticationStatus.SUCCESS);
			setUser(new SimpleUser(usuario.getLogin()));
			this.identity.addRole(usuario.getPerfilUsuario().getChave().toString(),
					PerfilTypeEnum.USUARIO_GRUPO.getGroup(),
					PerfilTypeEnum.USUARIO_GRUPO.getRoleType());
			this.customIdentity.setUsuario(usuario);
			return "success";
		} else {
			setStatus(AuthenticationStatus.FAILURE);
			this.messages.info(new BundleKey("resources",
					ConstantesMensagem.LOGIN_ERRO.getKey())).build();
			return "fail";
		}
	}
	
	public String logout() throws Exception {
		this.identity.logout();
		this.customIdentity = null;
		return "logout";
	}

}
