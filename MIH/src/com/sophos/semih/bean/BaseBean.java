package com.sophos.semih.bean;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sophos.semih.model.TMihUsuario;

@ManagedBean
@ViewScoped
public class BaseBean implements Serializable {
    	
	
	private static final long serialVersionUID = -1032719831373742287L;
	private static final Log log = LogFactory.getLog(BaseBean.class);
	
	public static final Severity ERROR = FacesMessage.SEVERITY_ERROR;
	public static final Severity INFO = FacesMessage.SEVERITY_INFO;
	public static final Severity WARNING = FacesMessage.SEVERITY_WARN;
	
	private TMihUsuario sessionUser;
	private TMihUsuario user;

	public BaseBean() {
		
	}

	/**
	 * @return the user
	 */
	public TMihUsuario getUser() {
		if(FacesContext.getCurrentInstance() != null)
			user = (TMihUsuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("logedUser");
		return user;
	}
	
	/**
	 * Permite obtener un mensaje del archivo de internacionalizacion.
	 * @param key
	 * @param params
	 * @return
	 */
	public static String getMessage(String key, String[] params) {

		String msg;
        ResourceBundle bundle = ResourceBundle.getBundle("messages_es");

        try {
    	    msg = bundle.getString(key);
        } catch (MissingResourceException e) {
    	    msg = "?? key " + key + " not found ??";
        }

        if (params != null) {
            MessageFormat mf = new MessageFormat(msg);
            msg = mf.format(params, new StringBuffer(), null).toString();
        }
        return msg;
    }
	
	/**
	 * Pone unmensaje en el contexto de Faces.
	 * 
	 * @param message	Codigo del mensaje configurado en messages_es.properties
	 * @param severity	Severidad del mensaje. Puede ser de informacion, advertencia o error.
	 * 					Se utilizan las constantes ERROR, INFO, WARNING de esta clase.
	 * @param forId		Id del componente al que se le va a adicionar el mensaje o null en caso contrario.
	 * @param append	Texto a adicionar al final del mensaje o null en caso que no.
	 */
	public void setMessage(String message, Severity severity, String forId, String append){
		message = getMessage(message, null);
		StringBuffer sbMessage = new StringBuffer(message);
		
		if (append != null && !append.isEmpty()) {
			sbMessage.append(" ");
			sbMessage.append(append);
		}
		
		FacesMessage facesMessage = new FacesMessage(sbMessage.toString());  
		facesMessage.setSeverity(severity); 
		if (forId != null && !forId.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(forId, facesMessage);
		} else {
			FacesContext.getCurrentInstance().addMessage("", facesMessage);
		}
		
	}
	
	/**
	 * Refresca los valores locales de los formularios.
	 * @param ids
	 */
	public void resetValues(ArrayList<String> ids){
		try{
			for (String string : ids) {
				UIInput field = (UIInput) FacesContext.getCurrentInstance().getViewRoot().findComponent(string);
				field.resetValue();
				
			}
		} catch (Exception e){
			log.error("Error resetValues()", e);
		}
	}
	
	/**
	 * Asigna un valo a un elemento de la vista.
	 * @param id		id del componente a editar
	 * @param value		valor que se asiganara
	 */
	public void setUIValue(String id, Object value){
		try{
			UIInput field = (UIInput) FacesContext.getCurrentInstance().getViewRoot().findComponent(id);
			field.setValue(value.toString());
		} catch (Exception e){
			log.error("Error setUIValue()", e);
		}
	}

	/**
	 * Limpia los mensajes enviados a la vista desde un bean.
	 */
	public void clearMessages(){
		Iterator<FacesMessage> messages = FacesContext.getCurrentInstance().getMessages();
		while(messages.hasNext()){
            ((FacesMessage)messages.next()).setDetail("");
        }
		
	}

	/**
	 * @return the user
	 */
	public TMihUsuario getSessionUser() {
		return sessionUser;
	}

	/**
	 * @param user the user to set
	 */
	public void setSessionUser(TMihUsuario sessionUser) {
		this.sessionUser = sessionUser;
	}
	
	
}
