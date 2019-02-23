package br.com.localizador.infra;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.localizador.entity.PerfilUsuario;
import br.com.localizador.service.PerfilUsuarioService;

@Named
@Stateless
public class CombosBean implements Serializable {

	private static final long serialVersionUID = -4678172064688374791L;

	private @Inject PerfilUsuarioService perfilUsuarioService;
	
	public List<PerfilUsuario> getListaPerfilUsuario() throws Exception {
		return this.perfilUsuarioService.listar();
	}
	
	public List<PerfilUsuario> getListaPerfilUsuarioSemAdmin() throws Exception {
		List<PerfilUsuario> listaRetorno = this.perfilUsuarioService.listar();
		listaRetorno.remove(0);
		return listaRetorno;
	}
	
}
