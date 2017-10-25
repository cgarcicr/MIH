package com.sophos.semih.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sophos.semih.bean.CatalogoExistenteBean;

public class ReportGenerator {
	
	private static final Log log = LogFactory.getLog(CatalogoExistenteBean.class);
	
	public static String getReporte(String archivoLectura, String archivoSalida) throws Exception {
    	
    	Pattern head 	  = Pattern.compile("SQL", Pattern.CASE_INSENSITIVE); 
    	
    	Pattern entity	  = Pattern.compile("loaded from every logical record.",  Pattern.CASE_INSENSITIVE);
    	Pattern skipped   = Pattern.compile("Total logical records skipped:",   Pattern.CASE_INSENSITIVE); 
    	Pattern read      = Pattern.compile("Total logical records read:", 	  Pattern.CASE_INSENSITIVE); 
    	Pattern rejected  = Pattern.compile("Total logical records rejected:",  Pattern.CASE_INSENSITIVE); 
    	Pattern discarded = Pattern.compile("Total logical records discarded:", Pattern.CASE_INSENSITIVE); 
    	
    	Pattern entityEs	= Pattern.compile("cargada de cada registro lógico.",  Pattern.CASE_INSENSITIVE);
    	Pattern skippedEs	= Pattern.compile("Total de registros lógicos ignorados:",   Pattern.CASE_INSENSITIVE); 
    	Pattern readEs      = Pattern.compile("Total de registros lógicos leídos:", 	  Pattern.CASE_INSENSITIVE); 
    	Pattern rejectedEs  = Pattern.compile("Total de registros lógicos rechazados:",  Pattern.CASE_INSENSITIVE); 
    	Pattern discardedEs = Pattern.compile("Total de registros lógicos desechados:", Pattern.CASE_INSENSITIVE); 
    	
    	String line = "";
        PrintWriter writer = null;
        BufferedReader archivoLeido = null;
    	
    	try {
    	
    	archivoLeido = new BufferedReader(new FileReader(archivoLectura));	
    	writer = new PrintWriter(archivoSalida, "UTF-8");	
    		
    	while ( (line = archivoLeido.readLine()) != null) {
    		
    		Matcher matchHead		 = head.matcher(line);
    		Matcher matchEntity		 = entity.matcher(line);
    		Matcher matchEntityEs	 = entityEs.matcher(line);
    	    Matcher matchSkipped 	 = skipped.matcher(line);
    		Matcher matchSkippedEs   = skippedEs.matcher(line);
    	    Matcher matchRead		 = read.matcher(line);
    		Matcher matchReadEs		 = readEs.matcher(line);
    	    Matcher matchRejected	 = rejected.matcher(line);
    		Matcher matchRejectedEs  = rejectedEs.matcher(line);
    	    Matcher matchDiscarded	 = discarded.matcher(line);
    		Matcher matchDiscardedEs = discardedEs.matcher(line);
    	    

    	    // indicate all matches on the line
    	    while (matchHead.find() 		|| 
    	    		matchEntity.find() 		||
    	    		matchEntityEs.find()	||
    	    		matchSkipped.find() 	||
    	    		matchSkippedEs.find()	||
    	    		matchRead.find() 		||
    	    		matchReadEs.find()		||
    	    		matchRejected.find()	|| 
    	    		matchRejectedEs.find()	||
    	    		matchDiscarded.find()	||
    	    		matchDiscardedEs.find() ) 	{

    	    			writer.write(line + "\n");
    	    }
    	}
    	return archivoSalida;
    	}
    	catch ( IOException e){
    		log.error("Error obtener la información del reporte de conciliación", e);
	    }finally {
       	try {
       		archivoLeido.close();
       		writer.close();
	    		} 
	    	catch (Exception e) {
	    		log.error("Error obtener la información del reporte de conciliación", e);
	    	} 
	    }
       return null;
    	 
    }
	
	public static String getRechazos(String archivoLectura, String archivoSalida) throws Exception {
    	
    	Pattern rejected = Pattern.compile("Rejected ", Pattern.CASE_INSENSITIVE);
    	Pattern rejectedEs = Pattern.compile("Rechazado ", Pattern.CASE_INSENSITIVE);
    	Pattern discarded = Pattern.compile("Discarded ", Pattern.CASE_INSENSITIVE);
    	Pattern discardedEs = Pattern.compile("Desechado ", Pattern.CASE_INSENSITIVE);
        String line = "";
        BufferedReader archivoLeido = null;
        PrintWriter writer = null; 	
        boolean controlFlag = false;
    	
    	try {
    	
    	archivoLeido = new BufferedReader(new FileReader(archivoLectura));	
    	writer = new PrintWriter(archivoSalida, "UTF-8");	
    	
    	while ( (line = archivoLeido.readLine()) != null) {

    		Matcher matchRejected = rejected.matcher(line);
    		Matcher matchrejectedEs = rejectedEs.matcher(line);
    		Matcher matchDiscarded = discarded.matcher(line);
    		Matcher matchdiscardedEs = discardedEs.matcher(line);
    		
    	    // indicate all matches on the line
    			
    		if (controlFlag == true){
	    	    writer.write(line + "\n");
	    	    controlFlag = false;
    		}
    		while (matchRejected.find()){
    	    	controlFlag = true;
    	    	writer.write(line);
    	    }  
    		while (matchrejectedEs.find()){
    	    	controlFlag = true;
    	    	writer.write(line);
    	    }
    	    while (matchDiscarded.find()){
    	    	writer.write(line + "\n");
    	    }
    	    while (matchdiscardedEs.find()){
    	    	writer.write(line + "\n");
    	    }
    	}
    	
    	return archivoSalida;
    	}
    	catch ( IOException e){
    		log.error("Error al obtener la información de rechazos", e);
	    }finally {
       	try {
       		archivoLeido.close();
       		writer.close();
	    		} 
	    	catch (Exception e) {
	    		log.error("Error al obtener la información de rechazos", e);
	    	} 
	    }
       return null;
    	 
    }
	
	@SuppressWarnings("unused")
	public static String getRechazados(String archivoLectura, String archivoSalida) throws Exception {
	
		String archivoCopiado = Utilidades.copiarArchivo(archivoLectura, archivoSalida);
		return null;
	}
	
}

