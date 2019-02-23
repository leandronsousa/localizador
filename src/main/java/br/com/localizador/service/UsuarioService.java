package br.com.localizador.service;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.codec.digest.DigestUtils;

import br.com.localizador.entity.Usuario;
import br.com.localizador.entitybean.UsuarioBean;

@Named
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public @Stateless class UsuarioService {

	private @Inject UsuarioBean usuarioBean;
	
	public boolean isLoginCadastrado(String login) throws Exception {
		return this.usuarioBean.capturarPorLogin(login) != null;
	}

	public Usuario login(String login, String senha) throws Exception {
		senha = DigestUtils.shaHex(senha);
		return this.usuarioBean.login(login, senha);
	}
	
}