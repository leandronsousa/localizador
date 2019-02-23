package br.com.localizador.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.localizador.entity.ObjetoRastreado;
import br.com.localizador.entity.Proprietario;
import br.com.localizador.entitybean.ObjetoRastreadoBean;

@Named
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public @Stateless class ObjetoRastreadoService {

	private @Inject ObjetoRastreadoBean objetoRastreadoBean;
	
	public ObjetoRastreado recuperar(ObjetoRastreado objetoRastreado) throws Exception {
		return this.objetoRastreadoBean.recuperar(objetoRastreado.getImei());
	}

//	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<ObjetoRastreado> recuperarObjetosRastreadosProprietario(
			Proprietario proprietario) throws Exception {
		return this.objetoRastreadoBean.recuperarObjetosRastreadosProprietario(proprietario);
	}
	
}