package br.com.localizador.entitybean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUtil;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.localizador.entity.ObjetoRastreado;
import br.com.localizador.entity.Posicao;
import br.com.localizador.util.DataHoraUtil;
import br.com.localizador.view.filtro.ObjetoRastreadoFiltro;

@Named
@Stateless
public class PosicaoBean extends EntityBeanAbstrato<Posicao> {

	public List<Posicao> recuperarPosicoesObjetoRastreado(
			ObjetoRastreado objetoRastreado, 
			ObjetoRastreadoFiltro objetoRastreadoFiltro) throws Exception {
		CriteriaQuery<Posicao> criteria = getCriteriaBuilder().createQuery(Posicao.class);
		Root<Posicao> rootPosicao = criteria.from(Posicao.class);
		Predicate[] listaPredicate = comporFiltro(criteria, rootPosicao, 
				objetoRastreado, objetoRastreadoFiltro);
		List<Posicao> listaRetorno = this.getManager().createQuery(
				criteria.select(rootPosicao).where(listaPredicate)).getResultList();
		return listaRetorno;
	}

	private Predicate[] comporFiltro(CriteriaQuery<Posicao> criteria,
			Root<Posicao> rootPosicao, ObjetoRastreado objetoRastreado, 
			ObjetoRastreadoFiltro objetoRastreadoFiltro) {
		List<Predicate> listaPredicate = new ArrayList<Predicate>();
		listaPredicate.add(getCriteriaBuilder().equal(
				rootPosicao.get("objetoRastreado"), objetoRastreado));
		if (objetoRastreadoFiltro.getDataInicial() != null &&
				objetoRastreadoFiltro.getDataFinal() != null) {
			listaPredicate.add(getCriteriaBuilder().between(rootPosicao.<Date>get("dataHora"), 
					objetoRastreadoFiltro.getDataInicial(), objetoRastreadoFiltro.getDataFinal()));
		} else {
//			if (Persistence.getPersistenceUtil().isLoaded(objetoRastreado.getListaPosicao())) {
			if (!objetoRastreado.getListaPosicao().isEmpty()) {
				Long chaveUltimaPosicao = objetoRastreado.getListaPosicao().get(
						objetoRastreado.getListaPosicao().size()-1).getChave();
				listaPredicate.add(getCriteriaBuilder().gt(
						rootPosicao.<Long>get("chave"), chaveUltimaPosicao));
			} else {
				listaPredicate.add(getCriteriaBuilder().between(rootPosicao.<Date>get("dataHora"), 
						DataHoraUtil.adicionarMinutos(new Date(), -6), new Date()));
			}
		}
		return (Predicate[]) listaPredicate.toArray(new Predicate[listaPredicate.size()]);
	}

}