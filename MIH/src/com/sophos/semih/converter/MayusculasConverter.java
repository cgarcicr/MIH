package com.sophos.semih.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.sophos.semih.util.Utilidades;

@FacesConverter("mayusculasConverter")
public class MayusculasConverter implements Converter  {	

	    @Override
	    public String getAsString(FacesContext context, UIComponent component, Object value) {
	        return (value != null) ? Utilidades.quitarAcentos(value.toString().toUpperCase()) : null;
	    }

	    @Override
	    public Object getAsObject(FacesContext context, UIComponent component, String value) {
	        return (value != null) ? Utilidades.quitarAcentos(value.toUpperCase()) : null;
	    }

	}
