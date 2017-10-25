package com.sophos.semih.bean;

import java.io.File;
import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.jsf.FacesContextUtils;

import com.sophos.semih.model.TMihUsuario;
import com.sophos.semih.service.ParametroManager;
import com.sophos.semih.util.ExplorarDirectorio;

/**
 * Clase que soporta la pantalla de generar índice de data no estructurada
 * 
 * @author sebastian.duque
 * 
 */
@ManagedBean
@ViewScoped
public class GenerarIndiceBean extends BaseBean {

	private static final long serialVersionUID = -3552844314481786689L;
	ParametroManager paramManager;
	TMihUsuario logedUser = super.getUser();
	BeanFactory factory = FacesContextUtils
			.getWebApplicationContext(FacesContext.getCurrentInstance());
	private static final Log log = LogFactory.getLog(GenerarIndiceBean.class);
	private String ruta;
	private String error;

	/**
	 * Constructor
	 * 
	 * @throws Exception
	 */
	public GenerarIndiceBean() throws Exception {
		try {
			// Se obtiene la ruta de los archivos a cargar
			paramManager = (ParametroManager) factory.getBean("parametroManager");
			ruta = paramManager.getParametroById("RUTA_DATA_NO_ESTRUCTURADA").getValor();
			System.out.println("La ruta es: "+ruta);

		} catch (Exception e) {
			log.error("Error al iniciar la instancia de GenerarIndiceBean",
					e);
		}
	}

	/**
	 * ActionMethod encargado de generar el índice de los archivos contenidos en
	 * la ruta paramétrica que es desplegada a el usuario
	 */
	public String generar() {
		// Nombre del archivo que descargará el usuario
		String nombreArchivo = "Indice.xls";
		try {
			// Crea un objeto archivo de la ruta paramétrica
			File f = new File(ruta);

			// Crea la instancia de ExplorarDirectorio
			ExplorarDirectorio ed = new ExplorarDirectorio();

			// Genera el indice
			Workbook wb = ed.generarIndice(f);

			// Integridad
			if(wb == null){
//				error = "Ocurrió un error al generar el índice";
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Aviso","Ocurrio un error al generar el índice."));
				return null;
			}

			// Obtiene los contextos
			FacesContext fc = FacesContext.getCurrentInstance();
			ExternalContext ec = fc.getExternalContext();

			// Reinicia la respuesta
			ec.responseReset();

			// Establece el tipo de contenido
			ec.setResponseContentType("application/vnd.ms-excel");

			// Establece en el header el adjunto e indica el nombre del reporte
			ec.setResponseHeader("Content-Disposition",
					"attachment; filename=\"" + nombreArchivo + "\"");
			// Envía el índice al usuario
			wb.write(ec.getResponseOutputStream());

			// Fin de la respuesta, no debe recargar la página
			fc.responseComplete();
		} catch (IOException e) {
//			error = "Ocurrió un error al generar el índice";
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Aviso","Ocurrio un error al generar el índice."));
		}
		
		return null;
	}

	// Getters y Setters
	public ParametroManager getParamManager() {
		return paramManager;
	}

	public void setParamManager(ParametroManager paramManager) {
		this.paramManager = paramManager;
	}

	public TMihUsuario getLogedUser() {
		return logedUser;
	}

	public void setLogedUser(TMihUsuario logedUser) {
		this.logedUser = logedUser;
	}

	public BeanFactory getFactory() {
		return factory;
	}

	public void setFactory(BeanFactory factory) {
		this.factory = factory;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}
