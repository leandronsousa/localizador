package br.com.localizador.view.validator;

import java.util.regex.Pattern;

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

@FacesValidator("emailValidator")
public class EmailValidator implements Validator{
	
	private Pattern pattern;
	private @Inject Messages messages;
	
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"  
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	public EmailValidator() {
		this.pattern = Pattern.compile(EMAIL_PATTERN);
	}

	@Override
	public void validate(FacesContext context, UIComponent component, Object value)
			throws ValidatorException {
		if (value == null) {
			return;
		}
		if (!this.pattern.matcher(value.toString()).matches()) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Validation Erro", this.messages.info(new BundleKey(
							"resources", ConstantesMensagem.CADASTRO_INVALIDO.getKey())).build().toString()));
		}
	}

}