/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sophos.semih.util;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author miguel.altamiranda
 */
public class Properties {
	
	private static final Log log = LogFactory.getLog(Properties.class);
    private java.util.Properties prop;
    
    public Properties(String configFileName) {
        prop= new java.util.Properties();
        try {
            prop.load(this.getClass().getClassLoader().getResourceAsStream("com/sophos/semih/util/"+configFileName));
        } catch (IOException ex) {
        	log.error("NO SE PUDO CARGAR ARCHIVO DE CONFIGURACION "+configFileName, ex);
        }   
    }
    public java.util.Properties getProp() {
        return prop;
    }

    public void setProp(java.util.Properties prop) {
        this.prop = prop;
    }
    
    @SuppressWarnings("unused")
	private String getProperty(String key) {
        return this.prop.getProperty(key);
    }    
}
