package br.com.localizador.security;

import org.jboss.seam.security.Identity;
import org.jboss.seam.security.annotations.Secures;

import br.com.localizador.enums.PerfilEnum;

public class SecurityRules {

	public @Secures @Administrador boolean administradorChecker(Identity identity) {
		return identity.hasRole(PerfilEnum.ADMINISTRADOR.getChave().toString(),
				PerfilEnum.ADMINISTRADOR.getGroup(), 
				PerfilEnum.ADMINISTRADOR.getRoleType());
	}
	
	public @Secures @UsuarioMaster boolean usuarioMasterChecker(Identity identity) {
		return identity.hasRole(PerfilEnum.USUARIO_MASTER.getChave().toString(),
				PerfilEnum.USUARIO_MASTER.getGroup(), 
				PerfilEnum.USUARIO_MASTER.getRoleType());
	}
	
	public @Secures @Usuario boolean usuarioChecker(Identity identity) {
		return identity.hasRole(PerfilEnum.USUARIO.getChave().toString(),
				PerfilEnum.USUARIO.getGroup(), 
				PerfilEnum.USUARIO.getRoleType());
	}
	
	public @Secures @UsuarioCadastrado boolean usuarioCadastradoChecker(Identity identity) {
		return (identity.hasRole(PerfilEnum.USUARIO.getChave().toString(),
				PerfilEnum.USUARIO.getGroup(), 
				PerfilEnum.USUARIO.getRoleType()))
				|| (identity.hasRole(PerfilEnum.USUARIO_MASTER.getChave().toString(),
						PerfilEnum.USUARIO_MASTER.getGroup(), 
						PerfilEnum.USUARIO_MASTER.getRoleType()))
						|| (identity.hasRole(PerfilEnum.ADMINISTRADOR.getChave().toString(),
								PerfilEnum.ADMINISTRADOR.getGroup(), 
								PerfilEnum.ADMINISTRADOR.getRoleType()));
	}
	
	public @Secures @Cadastro boolean cadastroChecker(Identity identity) {
		return (identity.hasRole(PerfilEnum.USUARIO_MASTER.getChave().toString(),
						PerfilEnum.USUARIO_MASTER.getGroup(), 
						PerfilEnum.USUARIO_MASTER.getRoleType()))
						|| (identity.hasRole(PerfilEnum.ADMINISTRADOR.getChave().toString(),
								PerfilEnum.ADMINISTRADOR.getGroup(), 
								PerfilEnum.ADMINISTRADOR.getRoleType()));
	}
	
}