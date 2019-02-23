package br.com.localizador.entitybean;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.localizador.entity.PessoaJuridica;

@Named
@Stateless
public class PessoaJuridicaBean extends EntityBeanAbstrato<PessoaJuridica> {

	public PessoaJuridica capturarPorCNPJ(String cnpj) throws Exception {
		PessoaJuridica retorno = null;
		CriteriaQuery<PessoaJuridica> criteria = this.getCriteriaBuilder().createQuery(PessoaJuridica.class);
		Root<PessoaJuridica> rootPessoaJuridica = criteria.from(PessoaJuridica.class);
		Predicate predicate = this.getCriteriaBuilder().equal(rootPessoaJuridica.get("cnpj"), cnpj);
		try {
			retorno = this.getManager().createQuery(criteria.select(rootPessoaJuridica).where(predicate)).getSingleResult();
		} catch (NoResultException e) {}
		return retorno;
	}
	
}
