package com.sophos.semih.converter;

import java.util.List;

import javax.el.ELContext;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;

import com.sophos.semih.bean.ConsultaDinamicaBean;
import com.sophos.semih.model.Campo;

@FacesConverter("ForaneaConverter")
@Named
public class ForaneaConverter implements Converter{

	@ManagedProperty(value="#{consultaDinamicaBean}")
	private ConsultaDinamicaBean consultaDinamicaBean;
	 
	@Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String s) {
        for (Campo campo : getCamposList(facesContext)) {
            if (campo.getNombreCorto().equals(s)) {
                return campo;
            }
        }
        return null;
    }
 
	@Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object o) {
        if (o == null) {return null;}
        return o.toString();
    }
 
	/**
	 * Obtiene la lista de campos disponibles.
	 * @param facesContext
	 * @return
	 */
	private List<Campo> getCamposList(FacesContext facesContext) {       
        return getConsultaDinamicaBean(facesContext).getCamposE2();
    }
	
	/**
	 * Obtiene el bean de las consultas dinamicas.
	 * @param facesContext
	 * @return
	 */
	private ConsultaDinamicaBean getConsultaDinamicaBean(FacesContext facesContext) {
		if (consultaDinamicaBean == null) {
			ELContext elContext = facesContext.getELContext();
			consultaDinamicaBean = (ConsultaDinamicaBean) elContext.getELResolver().getValue(elContext, null, "consultaDinamicaBean");
		}
		return consultaDinamicaBean;
	}
    
	/**
	 * Set objeto ConsultaDinamicaBean
	 * @param consultaDinamicaBean
	 */
	public void setConsultaDinamicaBean(ConsultaDinamicaBean consultaDinamicaBean) {
		this.consultaDinamicaBean = consultaDinamicaBean;
	}
    
    
    
}
