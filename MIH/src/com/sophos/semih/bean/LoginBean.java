/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sophos.semih.bean;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hwpf.sprm.SprmUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.jsf.FacesContextUtils;

import com.sophos.semih.model.TMihSesion;
import com.sophos.semih.model.TMihUsuario;
import com.sophos.semih.service.LoginManager;
import com.sophos.semih.util.Security;

/**
 *
 * @author miguel.altamiranda
 */
@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 4869448663356257068L;
	private BeanFactory factory = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
	private LoginManager loginSessionBean;	
    private String userName,password;
    private TMihUsuario logedUser;
    private Security security;
    private static final Log log = LogFactory.getLog(LoginBean.class);
    
    /**
     * Creates a new instance of LoginManagedBean
     */
    public LoginBean() {
    	loginSessionBean = (LoginManager) factory.getBean("loginManager");
    	security=new Security();
    }
    
    /**
     * Inicio de sesión.
     * @return
     */
    public String login(){
    	String redirect = null;
        try {
			this.logedUser=loginSessionBean.login(userName, password);
		    security.setAttributeInSession("logedUser", this.logedUser);
			redirect = "main";
		} catch (Exception e) {
			setMessage("login.usr_no_ldap", BaseBean.ERROR);
		}
        return redirect;
    }
    
    /**
     * Cierra la sesión actual.
     * @return
     */
    public String logout(){
        loginSessionBean.logout();
        try{
        	security.removeAttributeFromSession("logedUser");
        	security.closeSession();
        } catch(Exception e){
        	log.error("Error cerrando sesion", e);
        }
        return "login";
    }
    
    /**
     * Redirecciona a la pagina de inicio.
     * @return
     */
    public String main(){
    	return "main";
    }
    
    public void setMessage(String message, Severity severity){
		message = getMessage(message, null);
		log.error("Error Login: " + message);
		
		FacesMessage facesMessage = new FacesMessage(message);  
		facesMessage.setSeverity(severity); 
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	}
    
    public String getMessage(String key, String[] params) {

		String msg = "";
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public TMihUsuario getLogedUser() {
        return logedUser;
    }

    public void setLogedUser(TMihUsuario logedUser) {
        this.logedUser = logedUser;
    }
    
}
