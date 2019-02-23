package br.com.localizador.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.localizador.entity.PerfilUsuario;
import br.com.localizador.entitybean.PerfilUsuarioBean;

@Named
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public @Stateless class PerfilUsuarioService {

	private @Inject PerfilUsuarioBean perfilUsuarioBean;

	public List<PerfilUsuario> listar() throws Exception {
		return this.perfilUsuarioBean.listar();
	}

	public PerfilUsuario recuperar(PerfilUsuario perfilUsuario) throws Exception {
		return this.perfilUsuarioBean.recuperar(perfilUsuario.getChave());
	}
	
}
