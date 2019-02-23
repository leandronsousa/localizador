package br.com.localizador.view.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;

import org.jboss.seam.international.status.Messages;
import org.jboss.seam.international.status.builder.BundleKey;

import br.com.localizador.enums.ConstantesMensagem;
import br.com.localizador.service.PessoaFisicaService;
import br.com.localizador.util.StringUtil;

@FacesValidator("CPFCadastroValidator")
public class CPFCadastroValidator implements Validator {

	private @Inject Messages messages;
	private @Inject PessoaFisicaService pessoaFisicaService;
	
	@Override
	public void validate(FacesContext context, UIComponent component, Object value)
			throws ValidatorException {
		if (value == null) {
			return;
		}
		if (!StringUtil.isCPFValido(value.toString())) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Validation Erro", this.messages.info(new BundleKey(
							"resources", ConstantesMensagem.CPF_INVALIDO.getKey())).build().toString()));
		}
		try {
			if (pessoaFisicaService.isCPFCadastrado(value.toString())) {
				throw new Exception();
			}
		} catch (Exception e) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Validation Erro", this.messages.info(new BundleKey(
							"resources", ConstantesMensagem.CPF_JA_CADASTRADO.getKey())).build().toString()));
		}
	}

}