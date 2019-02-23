package br.com.localizador.entitybean;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.localizador.entity.ObjetoRastreado;
import br.com.localizador.entity.Proprietario;

@Named
@Stateless
public class ObjetoRastreadoBean extends EntityBeanAbstrato<ObjetoRastreado> {

	public List<ObjetoRastreado> recuperarObjetosRastreadosProprietario(
			Proprietario proprietario) throws Exception {
		List<ObjetoRastreado> listaRetorno = new ArrayList<ObjetoRastreado>();
		CriteriaQuery<ObjetoRastreado> criteria = this.getCriteriaBuilder().createQuery(ObjetoRastreado.class);
		Root<ObjetoRastreado> rootObjetoRastreado = criteria.from(ObjetoRastreado.class);
		Predicate predicate = this.getCriteriaBuilder().equal(rootObjetoRastreado.get("proprietario"), proprietario);
		listaRetorno = this.getManager().createQuery(criteria.select(rootObjetoRastreado).where(predicate)).getResultList(); 
//		comporListaObjetoRastreado(listaRetorno);
		return listaRetorno;
	}

	private void comporListaObjetoRastreado(List<ObjetoRastreado> listaObjetoRastreado) {
		for (ObjetoRastreado objetoRastreado : listaObjetoRastreado) {
			objetoRastreado.getListaPosicao().size();
		}
	}

}