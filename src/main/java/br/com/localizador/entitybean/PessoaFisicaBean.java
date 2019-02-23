package br.com.localizador.entitybean;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.localizador.entity.PessoaFisica;


@Named
@Stateless
public class PessoaFisicaBean extends EntityBeanAbstrato<PessoaFisica> {

	public PessoaFisica capturarPorCPF(String cpf) throws Exception {
		PessoaFisica retorno = null;
		CriteriaQuery<PessoaFisica> criteria = this.getCriteriaBuilder().createQuery(PessoaFisica.class);
		Root<PessoaFisica> rootPessoaFisica = criteria.from(PessoaFisica.class);
		Predicate predicate = this.getCriteriaBuilder().equal(rootPessoaFisica.get("cpf"), cpf);
		try {
			retorno = this.getManager().createQuery(criteria.select(rootPessoaFisica).where(predicate)).getSingleResult();
		} catch (NoResultException e) {}
		return retorno;
	}
	
}
