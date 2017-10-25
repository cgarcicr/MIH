package com.sophos.semih.bean;


import java.security.GeneralSecurityException;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.AssertTrue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.jsf.FacesContextUtils;

import com.sophos.semih.common.ApplicationSetup;
import com.sophos.semih.common.Constants;
import com.sophos.semih.model.TMihParametro;
import com.sophos.semih.service.ParametroManager;
import com.sophos.semih.util.Utilidades;

@ManagedBean
@ViewScoped
public class ParametrosBean extends BaseBean implements Cloneable{

	private static final long serialVersionUID = -81269953130673094L;
	private static final Log log = LogFactory.getLog(ParametrosBean.class);
	
	//Managers
	private BeanFactory factory;
	private TMihParametro parametro;
	private ParametroManager parametroManager;
	
	//Info
	private List<TMihParametro> listaParametros;
	
	//Filtro
	private String codigo;
	private String valor;
	private String descripcion;

	


	//String
	private String pss1;
	private String pss2;
	
    
    //Constructor
	public ParametrosBean() {
		factory = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
		parametroManager = (ParametroManager) factory.getBean(Constants.MANAGER_PARAMETRO);
		listaParametros = parametroManager.getParametros(new TMihParametro());
		pss1 = Constants.EMPTY_STRING;
		pss2 = Constants.EMPTY_STRING;
		this.decryptPss();
	}

//	@AssertTrue(message = "#{msg['parametros.title.pss_diferentes']}")
	@AssertTrue(message = "Ambas valores son diferentes. Verifique.")
	public boolean isPasswordsEquals() {
		return pss1.equals(pss2);
	}
	
	/**
	 * Actualiza el valor del parametro seleccionado.
	 */
	public void actualizar() {
		try {
			if (parametro.getCodigo() != null) {
				if (parametro.getEspss().equals(Constants.FLAG_AFIRMATIVO)) {
					
					
					if ((this.pss1 != null) && (this.pss2 != null) && (this.pss1.equals(this.pss2))){
						parametro.setValor(Utilidades.encrypt(this.pss1));
						parametroManager.updateParametro(parametro);
						pss1 = null;
						pss2 = null;
						FacesContext context = FacesContext.getCurrentInstance();
				        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,null, "Registro actualizado") );
				        super.setMessage("parametros.pss_verification", BaseBean.ERROR, "parametros:valorParametro", null);
					} else {
						super.setMessage("parametros.pss_verification", BaseBean.ERROR, "parametros:valorParametro", null);

					}
				} else {
					
					//NUMERICO
					if (parametro.getCodigo().equals("ALERT_DAYS")) {
						if (!Utilidades.isNumericoNCero(parametro.getValor())) {
							super.setMessage("parametros.num_n_cero_verification", BaseBean.ERROR, "parametros:valorParametro", null);
							return;
						}
					}
					//NUMERICO
					else if (parametro.getCodigo().equals("INICIO_DEPURACION_INFO")) {
						if (!Utilidades.isNumericoNCero(parametro.getValor())) {
							super.setMessage("parametros.num_n_cero_verification", BaseBean.ERROR, "parametros:valorParametro", null);
							return;
						}
					}
					//NUMERICO
					else if (parametro.getCodigo().equals("INICIO_DEPURACION_LOGS")) {
						if (!Utilidades.isNumericoNCero(parametro.getValor())) {
							super.setMessage("parametros.num_n_cero_verification", BaseBean.ERROR, "parametros:valorParametro", null);
							return;
						}
					}
					//NUMERICO
					else if (parametro.getCodigo().equals("INICIO_DEPURACION_LOGS")) {
						if (!Utilidades.isNumericoNCero(parametro.getValor())) {
							super.setMessage("parametros.num_n_cero_verification", BaseBean.ERROR, "parametros:valorParametro", null);
							return;
						}
					}
					//NUMERICO
					else if (parametro.getCodigo().equals("LOG_LEVEL_ID")) {
						if (!Utilidades.isNumerico(parametro.getValor())) {
							super.setMessage("parametros.num_n_cero_verification", BaseBean.ERROR, "parametros:valorParametro", null);
							return;
						}
					}
					//NUMERICO
					else if (parametro.getCodigo().equals("LOG_EXP_DAYS")) {
						if (!Utilidades.isNumericoNCero(parametro.getValor())) {
							super.setMessage("parametros.num_n_cero_verification", BaseBean.ERROR, "parametros:valorParametro", null);
							return;
						}
					}
					//NUMERICO
					else if (parametro.getCodigo().equals("MAX_UPLOAD_ERRORS")) {
						if (!Utilidades.isNumericoNCero(parametro.getValor())) {
							super.setMessage("parametros.num_n_cero_verification", BaseBean.ERROR, "parametros:valorParametro", null);
							return;
						}
					}
					//NUMERICO
					else if (parametro.getCodigo().equals("SMTP_PUERTO")) {
						if (!Utilidades.isNumericoNCero(parametro.getValor())) {
							super.setMessage("parametros.num_n_cero_verification", BaseBean.ERROR, "parametros:valorParametro", null);
							return;
						}
					}
					//NUMERICO
					else if (parametro.getCodigo().equals("TIEMPO_SESION")) {
						if (!Utilidades.isNumericoNCero(parametro.getValor())) {
							super.setMessage("parametros.num_n_cero_verification", BaseBean.ERROR, "parametros:valorParametro", null);
							return;
						}
					}
					//NUMERICO
					else if (parametro.getCodigo().equals("TIME_INFO_JOB")) {
						if (!Utilidades.isNumericoNCero(parametro.getValor())) {
							super.setMessage("parametros.num_n_cero_verification", BaseBean.ERROR, "parametros:valorParametro", null);
							return;
						}
					}
					//NUMERICO
					else if (parametro.getCodigo().equals("TIME_LOG_JOB")) {
						if (!Utilidades.isNumericoNCero(parametro.getValor())) {
							super.setMessage("parametros.num_n_cero_verification", BaseBean.ERROR, "parametros:valorParametro", null);
							return;
						}
					}
					//CORREO
					else if (parametro.getCodigo().equals("ALERT_EMAIL_TO")) {
						if (!Utilidades.isCorreo(parametro.getValor())) {
							super.setMessage("parametros.correo_verification", BaseBean.ERROR, "parametros:valorParametro", null);
							return;
						}
					}
					//CORREO
					else if (parametro.getCodigo().equals("ALERT_EMAIL")) {
						if (!Utilidades.isCorreo(parametro.getValor())) {
							super.setMessage("parametros.correo_verification", BaseBean.ERROR, "parametros:valorParametro", null);
							return;
						}
					}
					
					//S/N
					else if (parametro.getCodigo().equals("DEPURACION_ACTIVA")) {
						if ((!parametro.getValor().equals("S")) && (!parametro.getValor().equals("N"))) {
							super.setMessage("parametros.sn_verification", BaseBean.ERROR, "parametros:valorParametro", null);
							return;
						}
					}
					
					//true/false
					else if (parametro.getCodigo().equals("USE_DB_LOG")) {
						if ((!parametro.getValor().equals("true")) && (!parametro.getValor().equals("false"))) {
							super.setMessage("parametros.truefalse_verification", BaseBean.ERROR, "parametros:valorParametro", null);
							return;
						}
					}
					
					//true/false
					else if (parametro.getCodigo().equals("USE_WINDOWS_LOG")) {
						if ((!parametro.getValor().equals("true")) && (!parametro.getValor().equals("false"))) {
							super.setMessage("parametros.truefalse_verification", BaseBean.ERROR, "parametros:valorParametro", null);
							return;
						}
					}
					
					//true/false
					else if (parametro.getCodigo().equals("WIN_OS_SERVER")) {
						if ((!parametro.getValor().equals("true")) && (!parametro.getValor().equals("false"))) {
							super.setMessage("parametros.truefalse_verification", BaseBean.ERROR, "parametros:valorParametro", null);
							return;
						}
					}
					
					
					//formato DATE
					else if (parametro.getCodigo().equals("NLS_DATE_FORMAT")) {
						if (!Utilidades.isDate(parametro.getValor())) {
							super.setMessage("parametros.date_verification", BaseBean.ERROR, "parametros:valorParametro", null);
							return;
						}
					}
					
					//formato TIMESTAMP
					else if (parametro.getCodigo().equals("NLS_TIME_FORMAT")) {
						if (!Utilidades.isTimestamp(parametro.getValor())) {
							super.setMessage("parametros.timestamp_verification", BaseBean.ERROR, "parametros:valorParametro", null);
							return;
						}
					}
					
					
					//NLS_LANG
					else if (parametro.getCodigo().equals("NLS_LANG")) {
						if (!parametro.getValor().equals("AMERICAN_AMERICA.UTF8")
								&&!parametro.getValor().equals("AMERICAN_AMERICA.WE8MSWIN1252")
								&&!parametro.getValor().equals("SPANISH_SPAIN.WE8MSWIN1252")
								&&!parametro.getValor().equals("MEXICAN SPANISH_MEXICO.WE8MSWIN1252")
								&&!parametro.getValor().equals("LATIN AMERICAN SPANISH_VENEZUELA.WE8MSWIN1252")
								&&!parametro.getValor().equals("LATIN AMERICAN SPANISH_AMERICA.WE8ISO8859P1")) {
							super.setMessage("parametros.nls_lang_verification", BaseBean.ERROR, "parametros:valorParametro", null);
							return;
						}
					}
					
					//SIN ESPACIOS AL INICIO Y AL FINAL (TODOS)
					else {
						if (!Utilidades.isAlfaNumericoNBE(parametro.getValor())) {
							super.setMessage("parametros.espacios_be_verification", BaseBean.ERROR, "parametros:valorParametro", null);
							return;
						}
					}
					
					parametroManager.updateParametro(parametro);
					ApplicationSetup.refreshParams(parametroManager);
				}
			} 
			
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
		} catch (Exception e) {
			log.error("No se pudo actualizar el parámetro", e);
		}
	}
	
	public boolean verificarPssIguales(){
		boolean iguales = (this.pss1.equals(this.pss2));
		if (!iguales) {
			super.setMessage("parametros.pss_verification", BaseBean.ERROR, "parametros:valorParametroPss1", null);
		}
		return iguales;
	}
	
	/**
	 * Cambia el valor de las contraseñas para no ser mostradas en la vista.
	 */
	private void decryptPss(){
		try {
			for (int i = 0; i < listaParametros.size(); i++) {
				if (listaParametros.get(i).getEspss().equals(Constants.FLAG_AFIRMATIVO)) {
					listaParametros.get(i).setValor(Utilidades.decrypt(listaParametros.get(i).getValor()));
				}
			}
		} catch (GeneralSecurityException e) {
			log.error("Error decryptPss()", e);
		} catch (Exception e) {
			log.error("Error decryptPss()", e);
		}
	}


	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	//GETTERS & SETTERS
	public TMihParametro getParametro() {
		return parametro;
	}

	public void setParametro(TMihParametro parametro) {
		this.parametro = parametro;
	}

	public List<TMihParametro> getListaParametros() {
		return listaParametros;
	}

	public void setListaParametros(List<TMihParametro> listaParametros) {
		this.listaParametros = listaParametros;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getPss1() {
		return pss1;
	}


	public void setPss1(String pss1) {
		this.pss1 = pss1;
	}

	public String getPss2() {
		return pss2;
	}

	public void setPss2(String pss2) {
		this.pss2 = pss2;
	}
	
	

	
   
	
}
