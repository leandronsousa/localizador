package br.com.localizador.entitybean;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import br.com.localizador.entity.Proprietario;

@Named
@Stateless
public class ProprietarioBean extends EntityBeanAbstrato<Proprietario> {

	public List<Proprietario> pesquisar(Proprietario proprietario) throws Exception {
		CriteriaQuery<Proprietario> criteria = getCriteriaBuilder().createQuery(Proprietario.class);
		Root<Proprietario> rootProprietario = criteria.from(Proprietario.class); 
		Predicate[] listaPredicate = comporFiltroProprietario(proprietario,rootProprietario);
		return this.getManager().createQuery(criteria.select(
				rootProprietario).where(listaPredicate)).getResultList();
	}

	private Predicate[] comporFiltroProprietario(Proprietario proprietario,
			Root<Proprietario> rootProprietario) {
		List<Predicate> listaPredicate = new ArrayList<Predicate>();
		if (proprietario.getPessoaFisica() != null
				&& !StringUtils.isEmpty(proprietario.getPessoaFisica().getNome())) {
			this.montarFiltroNome(proprietario, rootProprietario);
		}
		return (Predicate[]) listaPredicate.toArray(new Predicate[listaPredicate.size()]);
	}

	private void montarFiltroNome(Proprietario proprietario,
			Root<Proprietario> rootProprietario) {
		
	}

}
