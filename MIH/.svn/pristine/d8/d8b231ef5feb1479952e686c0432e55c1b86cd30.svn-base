package com.sophos.semih.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ProcessBuilder.Redirect;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sophos.semih.bean.CatalogoExistenteBean;
import com.sophos.semih.common.ApplicationSetup;

public class CommandExecuter {
	
	@SuppressWarnings("unused")
	private static String error;
	private static final Log log = LogFactory.getLog(CatalogoExistenteBean.class);
	
    
    /***
     * Funcion que ejecuta comandos del sistema operativo
     * @return 
     * @sqlldrCommand Comando a ejecutar
     * @sqlldrDirectory Directorio
     * @sqlldrVarEntorno Variables de Entorno
     * @sqlldrArgCredential Credenciales de conexi�n a oracle
     * @sqlldrArgControl Archivo de control
     * @sqlldrLogCtl Archivo de log
     */
	public static String ejecutarComandoOS(String sqlldrCommand,
			String sqlldrDirectory, HashMap<String, String> sqlldrVarEntorno,
			String sqlldrArgCredential, String sqlldrArgControl, String sqlldrArgErrors,
			String sqlldrArgLog, String sqlldrLogCtl) throws Exception{
		
		    
		    try{
			
		    	ProcessBuilder comando = new ProcessBuilder(sqlldrCommand, sqlldrArgCredential, sqlldrArgControl, sqlldrArgErrors, sqlldrArgLog);
				final Map<String, String> environment = comando.environment();
				environment.putAll(sqlldrVarEntorno);
				
				File log = new File(sqlldrLogCtl);
				
				comando.directory(new File(sqlldrDirectory));
				comando.redirectErrorStream(true);
				comando.redirectOutput(Redirect.appendTo(log));
				
				Process process = comando.start();
				
				assert comando.redirectInput() == Redirect.PIPE;
				assert comando.redirectOutput().file() == log;
				assert process.getInputStream().read() == -1;
				
		    } catch (Exception e) {
		    	log.error("Error ejecutarComandoOS()", e);
		    		error = e.getLocalizedMessage();
		    		resetSession();
		    		} 
		return null;
	}
	
	
    /***
     * Funcion que ejecuta comandos del sistema operativo para el prevalidador
     * @return 
     * @sqlldrCommand Comando a ejecutar
     * @sqlldrDirectory Directorio
     * @sqlldrVarEntorno Variables de Entorno
     * @sqlldrArgCredential Credenciales de conexi�n a oracle
     * @sqlldrArgControl Archivo de control
     * @sqlldrLogCtl Archivo de log
     */
	public static String ejecutarComandoOSPrev(String sqlldrCommand,
			String sqlldrDirectory, HashMap<String, String> sqlldrVarEntorno,
			String sqlldrArgCredential, String sqlldrArgControl, String sqlldrArgErrors,
			String sqlldrArgLog, String sqlldrLogCtl) throws Exception{
			
		    
		    try{
			
		    	ProcessBuilder comando = new ProcessBuilder(sqlldrCommand, sqlldrArgCredential, sqlldrArgControl, sqlldrArgErrors, sqlldrArgLog);
				final Map<String, String> environment = comando.environment();
				environment.putAll(sqlldrVarEntorno);
				
				File log = new File(sqlldrLogCtl);
				
				comando.redirectErrorStream(true);
				comando.directory(new File(sqlldrDirectory));
				comando.redirectOutput(Redirect.appendTo(log));
				
				Process process = comando.start();
				
				BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
                char[] buffer = new char[512];
                @SuppressWarnings("unused")
				int len;
                while((len = in.read(buffer)) != -1) {
                }
                
				setSession();
				process.waitFor();
				resetSession();
				
				in.close();

                if(process.exitValue() != 0) {
                      return null;
                }
				
				assert comando.redirectInput() == Redirect.PIPE;
				assert comando.redirectOutput().file() == log;
				assert process.getInputStream().read() == -1;
				
		    } catch (Exception e) {
		    	log.error("Error ejecutarComandoOS()", e);
		    		error = e.getLocalizedMessage();
		    		resetSession();
		    		} 
		return null;
	}
	
	/***
     * Funcion que copia a un archivo
     * @return Nombre del archivo copiado
     */
	public static String copiarArchivoOS(String archivoIn, String archivoOut, String os) throws Exception{
		
		String[]  comandoWin =  {"cmd", "/c", "copy" + " \"" + archivoIn + "\" " + archivoOut};
		String[]  comandoUnix = {"sh", "-c", "cp -f" + " " + archivoIn + " " + archivoOut};

		try {
		String[] commando;
		if (os.equals("true")){
			commando = comandoWin;
		}else{	
			commando = comandoUnix;
		}	
				
			ProcessBuilder copiarArchivo = new ProcessBuilder(commando);
			copiarArchivo.redirectErrorStream(true);
			
			Process process = copiarArchivo.start();

			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			do {
			    line = reader.readLine();
			    if (line != null) { System.out.println(line); }
			} while (line != null);
			reader.close();

			setSession();
			process.waitFor();
			resetSession();
		
		return archivoOut;
		}
	    catch ( IOException e){
	    	log.error("Error copiarArchivo()", e);
	    	error = e.getLocalizedMessage();
	    }
		return null;
	}
	
	
	/**
	 * Permite redefinir el tiempo de sessión
	 */
	public static void setSession(){
		FacesContext.getCurrentInstance().getExternalContext().setSessionMaxInactiveInterval(43200);
	}
	
	/**
	 * Permite restaurar el tiempo de sessión.
	 */
	public static void resetSession(){
		
		Integer sessionTime;
		try {
			sessionTime = new Integer(ApplicationSetup.getInstance().getParamValue("TIEMPO_SESION"));
		} catch (NumberFormatException e) {
			sessionTime = 5;
		}
		FacesContext.getCurrentInstance().getExternalContext().setSessionMaxInactiveInterval(sessionTime * 60);
		
	}
}
