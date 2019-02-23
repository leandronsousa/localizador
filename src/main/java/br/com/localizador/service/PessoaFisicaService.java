package br.com.localizador.service;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.localizador.entitybean.PessoaFisicaBean;
import br.com.localizador.util.StringUtil;

@Named
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public @Stateless class PessoaFisicaService {
	
	private @Inject PessoaFisicaBean pessoaFisicaBean;

	public boolean isCPFCadastrado(String cpf) throws Exception {
		cpf = StringUtil.removerNaoNumericos(cpf);
		return this.pessoaFisicaBean.capturarPorCPF(cpf) != null;
	}
	
}
