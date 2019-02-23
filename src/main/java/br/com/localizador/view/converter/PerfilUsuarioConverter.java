package br.com.localizador.view.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import br.com.localizador.entity.PerfilUsuario;
import br.com.localizador.service.PerfilUsuarioService;

@FacesConverter("perfilUsuario")
public class PerfilUsuarioConverter implements Converter{

	private @Inject PerfilUsuarioService perfilUsuarioService;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		PerfilUsuario perfilUsuario = null;
		if (StringUtils.isBlank(value)) {
			return null;
		} else {
			try {
				perfilUsuario = new PerfilUsuario();
				perfilUsuario.setChave(new Long(value));
				perfilUsuario = this.perfilUsuarioService.recuperar(perfilUsuario);
			} catch (Exception e) {
				throw new ConverterException();
			}
		}
		return perfilUsuario;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null && value != "" && value instanceof PerfilUsuario && ((PerfilUsuario)value).getChave() != null) {
			return ((PerfilUsuario)value).getChave().toString();
		} else {
			return "";
		}
	}
	
}
