package br.com.localizador.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.seam.international.status.MessageFactory;
import org.jboss.seam.international.status.builder.BundleKey;

import br.com.localizador.entity.Proprietario;
import br.com.localizador.entitybean.ProprietarioBean;
import br.com.localizador.util.StringUtil;

@Named
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public @Stateless class ProprietarioService {

	private @Inject ProprietarioBean proprietarioBean;
	private @Inject MessageFactory factory;
	
	public void cadastrar(Proprietario proprietario) throws Exception {
		removeNaoNumericosCPFOuCNPJ(proprietario);
		this.proprietarioBean.incluir(proprietario);
	}
	
	private void removeNaoNumericosCPFOuCNPJ(Proprietario proprietario) {
		if (proprietario.getTipoPessoa().equals(factory.info(
				new BundleKey("resources", "label.geral.pessoaFisica")).build().getText())) {
			proprietario.getPessoaFisica().setCpf(StringUtil.removerNaoNumericos(
					proprietario.getPessoaFisica().getCpf()));
		} else if (proprietario.getTipoPessoa().equals(factory.info(
				new BundleKey("resources", "label.geral.pessoaJuridica")).build().getText())) {
			proprietario.getPessoaJuridica().setCnpj(StringUtil.removerNaoNumericos(
					proprietario.getPessoaJuridica().getCnpj()));
		}
	}

	public void alterar(Proprietario proprietario) throws Exception {
		this.proprietarioBean.alterar(proprietario);
	}
	
	public List<Proprietario> pesquisar(Proprietario proprietario) throws Exception {
		return this.proprietarioBean.pesquisar(proprietario);
	}
	
	public Proprietario recuperar (Proprietario proprietario) throws Exception {
		return this.proprietarioBean.recuperar(proprietario.getChave());
	}

}