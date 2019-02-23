package br.com.localizador.security;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.com.localizador.entity.Usuario;
import br.com.localizador.enums.PerfilEnum;

@Named
@SessionScoped
public class CustomIdentity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2952157905009830550L;
		
	private Usuario usuario;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public boolean isUsuarioAdmin() {
		return getUsuario() != null && 
				getUsuario().getPerfilUsuario().getChave().longValue()== PerfilEnum.ADMINISTRADOR.getChave().longValue();
	}
	
	public boolean isUsuarioMaster() {
		return getUsuario() != null && 
				getUsuario().getPerfilUsuario().getChave().longValue() == PerfilEnum.USUARIO_MASTER.getChave().longValue();
	}
	
}