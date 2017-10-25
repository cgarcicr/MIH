package com.sophos.semih.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.jsf.FacesContextUtils;

import com.sophos.semih.model.TMihTipocatalogo;
import com.sophos.semih.service.TipoCatalogoManager;

@FacesConverter("TipoCatalogoConverter")
public class TipoCatalogoConverter implements Converter {

	transient BeanFactory factory = FacesContextUtils
			.getWebApplicationContext(FacesContext.getCurrentInstance());

	@Override
	public Object getAsObject(FacesContext facesContext,
			UIComponent uiComponent, String value) {
		TipoCatalogoManager tcManager = (TipoCatalogoManager) factory
				.getBean("tipoCatalogoManager");
		try {
			return tcManager.getTipoCatalogoById(Integer.parseInt(value));
		} catch (NumberFormatException e) {
			return null;
		}
		
	}

	@Override
	public String getAsString(FacesContext facesContext,
			UIComponent uiComponent, Object obj) {
		if (obj == null) {
			return null;
		} else {
			return ((TMihTipocatalogo) obj).toString();
		}
	}

}
