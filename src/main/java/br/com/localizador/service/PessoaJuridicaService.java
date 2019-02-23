package br.com.localizador.service;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.localizador.entitybean.PessoaJuridicaBean;
import br.com.localizador.util.StringUtil;

@Named
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public @Stateless class PessoaJuridicaService {

	private @Inject PessoaJuridicaBean pessoaJuridicaBean;
	
	public boolean isCNPJCadastrado(String cnpj) throws Exception {
		cnpj = StringUtil.removerNaoNumericos(cnpj);
		return this.pessoaJuridicaBean.capturarPorCNPJ(cnpj) != null;
	}
	
}
