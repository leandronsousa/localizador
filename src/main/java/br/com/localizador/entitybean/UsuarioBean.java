package br.com.localizador.entitybean;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.localizador.entity.Usuario;

@Named
@Stateless
public class UsuarioBean extends EntityBeanAbstrato<Usuario> {

	public Object capturarPorLogin(String login) {
		Usuario retorno = null;
		CriteriaQuery<Usuario> criteria = getCriteriaBuilder().createQuery(Usuario.class);
		Root<Usuario> rootUsuario = criteria.from(Usuario.class); 
		Predicate predicate = getCriteriaBuilder().equal(rootUsuario.get("login"), login);
		try {
			retorno = this.getManager().createQuery(criteria.select(rootUsuario).where(predicate)).getSingleResult();
		} catch (NoResultException e) {}
		return retorno;
	}

	public Usuario login(String login, String senha) throws Exception {
		Usuario retorno = null;
		CriteriaQuery<Usuario> criteria = getCriteriaBuilder().createQuery(Usuario.class);
		Root<Usuario> rootUsuario = criteria.from(Usuario.class);
		rootUsuario.fetch("perfilUsuario");
		rootUsuario.fetch("proprietario");
		Predicate[] listaPredicate = {this.getCriteriaBuilder().equal(rootUsuario.get("login"), login),
				this.getCriteriaBuilder().equal(rootUsuario.get("senha"), senha)};
		try {
			retorno = this.getManager().createQuery(criteria.select(rootUsuario).where(listaPredicate)).getSingleResult();
		} catch (NoResultException e) {}
		return retorno;
	}
	
}