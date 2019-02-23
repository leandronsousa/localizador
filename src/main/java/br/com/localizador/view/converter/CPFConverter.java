package br.com.localizador.view.converter;

import java.text.ParseException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.jboss.seam.international.status.Messages;
import org.jboss.seam.international.status.builder.BundleKey;

import br.com.localizador.enums.ConstantesMensagem;
import br.com.localizador.util.StringUtil;

@FacesConverter("CPFConverter")
public class CPFConverter implements Converter {
	
	private @Inject Messages messages;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		String cpf = null;
		if (StringUtils.isBlank(value)) {
			return null;
		} else {
			try {
				cpf = StringUtil.removerNaoNumericos(value); 
			} catch (Exception e) {
				throw new ConverterException(this.messages.info(new BundleKey(
						"resources", ConstantesMensagem.CPF_INVALIDO.getKey())).build().toString());
			}
		}
		return cpf;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value == null || value.equals("")) {
			return "";
		} else {
			try {
				return StringUtil.formatarCPF(value.toString());
			} catch (ParseException e) {
				throw new ConverterException(this.messages.info(new BundleKey(
						"resources", ConstantesMensagem.CPF_INVALIDO.getKey())).build().toString());
			}
		}
	}

}
