package br.com.localizador.view.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import br.com.localizador.entity.ObjetoRastreado;
import br.com.localizador.service.ObjetoRastreadoService;

@FacesConverter("objetoRastreado")
public class ObjetoRastreadoConverter implements Converter {

	private @Inject ObjetoRastreadoService objetoRastreadoService;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		ObjetoRastreado objetoRastreado = null;
		if (StringUtils.isBlank(value)) {
			return null;
		} else {
			try {
				objetoRastreado = new ObjetoRastreado();
				objetoRastreado.setImei(value);
				objetoRastreado = this.objetoRastreadoService.recuperar(objetoRastreado);
			} catch (Exception e) {
				throw new ConverterException();
			}
		}
		return objetoRastreado;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value == null || value.equals("")) {
			return "";
		} else {
			return ((ObjetoRastreado)value).getNome();
		}
	}

}