/**
 * 
 */
package com.sophos.semih.common;

import java.io.Serializable;
import java.util.List;

import com.sophos.semih.model.TMihParametro;
import com.sophos.semih.service.ParametroManager;

/**
 * @author fmaillane
 *
 */
public class ApplicationSetup implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9086419856107608446L;
	private static ApplicationSetup INSTANCE = null;
	private List<TMihParametro> params;

	// Private constructor suppresses 
    private ApplicationSetup() {}
 
    private static void createInstance() {
        if (INSTANCE == null) {
            // Solo se accede a la zona sincronizada
            // cuando la instancia no esta creada
            synchronized(ApplicationSetup.class) {
                // En la zona sincronizada sera necesario volver
                // a comprobar que no se ha creado la instancia
                if (INSTANCE == null) { 
                    INSTANCE = new ApplicationSetup();
                }
            }
        }
    }
    
    /**
     * @return
     */
    public static ApplicationSetup getInstance() {
        createInstance();
        return INSTANCE;
    }

    /**
     * @param parametroManager
     */
    public static void refreshParams(ParametroManager parametroManager) {
        if (INSTANCE != null) {
        	INSTANCE.params = parametroManager.getParametros(new TMihParametro());
        }
    }
    
	/**
	 * @return the params
	 */
	public List<TMihParametro> getParams() {
		return params;
	}

	/**
	 * @param params the params to set
	 */
	public void setParams(List< TMihParametro> params) {
		this.params = params;
	}

	/**
	 * @param code
	 * @return
	 */
	public String getParamValue(String code) {
		if(this != null && getParams() != null && getParams().size() > 0){
			for(TMihParametro p:getParams()){
				if(code.equals(p.getCodigo())){
					return p.getValor();
				}
			}
		}
		return null;
	}

	/**
	 * @param code
	 * @return
	 */
	public TMihParametro getParam(String code) {
		if(this != null && getParams() != null && getParams().size() > 0){
			for(TMihParametro p:getParams()){
				if(code.equals(p.getCodigo())){
					return p;
				}
			}
		}
		return null;
	}
	
}
