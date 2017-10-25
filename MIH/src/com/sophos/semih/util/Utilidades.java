package com.sophos.semih.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mozilla.universalchardet.UniversalDetector;

import com.sophos.semih.bean.CatalogoExistenteBean;
import com.sophos.semih.common.Constants;
import com.sophos.semih.model.TMihCatalogo;

public class Utilidades {
	
	
	
	private static String error;
	private static final Log log = LogFactory.getLog(CatalogoExistenteBean.class);
	
	/**
     * Función que rellena por la izquierda una cadena cualquiera con el
     * caracter de relleno especificado, para obtener una cadena de la longitud
     * esperada
     * @param origen String a rellenar
     * @param longitud int con el tamaño final de la cadena
     * @param relleno String con el caracter a utilizar como relleno
     * @return String con la cadena rellenada
     */
    public static String padl(String strOrigen, int strLongitud, String strRelleno) {
          // Integridad
          if(strOrigen == null) {
        	  strOrigen = "";
          }
          
          // Integridad
          if(strLongitud <= strOrigen.length()) {
                return strOrigen;
          }
          
          // Crea el relleno
          final StringBuilder tmp = new StringBuilder();
          
          // Lo carga con el número especificado de signos
          for(int i = 0 ; i < strLongitud - strOrigen.length() ; i++) {
                tmp.append(strRelleno);
          }
          
          // Regresa el resultado
          return tmp + strOrigen;
    }

     /**
     * Función que rellena por la izquierda un número entero cualquiera con el
     * caracter de relleno especificado, para obtener una cadena de la longitud
     * esperada
     * @param origen número a rellenar
     * @param longitud int con el tamaño final de la cadena
     * @param relleno String con el caracter a utilizar como relleno
     * @return String con la cadena rellenada
     */
     public static String padl(int strOrigen, int strLongitud, String strRelleno) {
          return padl(String.valueOf(strOrigen), strLongitud, strRelleno);
     }

    /**
     * Función que rellena por la derecha una cadena cualquiera con el
     * caracter de relleno especificado, para obtener una cadena de la longitud
     * esperada
     * @param origen String a rellenar
     * @param longitud int con el tamaño final de la cadena
     * @param relleno String con el caracter a utilizar como relleno
     * @return String con la cadena rellenada
     */    
    public static String padr(String strOrigen, int strLongitud, String strRelleno) {
          // Integridad
          if(strOrigen == null) {
        	  strOrigen = "";
          }

          // Crea el relleno
          final StringBuilder tmp = new StringBuilder();
          
          // Lo carga con el número especificado de signos
          for(int i = strOrigen.length() ; i < strLongitud ; i++) {
                tmp.append(strRelleno);
          }
          
          // Regresa el resultado
          return strOrigen + tmp;
    }

    /**
     * Función que rellena por la derecha un número entero cualquiera con el
     * caracter de relleno especificado, para obtener una cadena de la longitud
     * esperada
     * @param origen número a rellenar
     * @param longitud int con el tamaño final de la cadena
     * @param relleno String con el caracter a utilizar como relleno
     * @return String con la cadena rellenada
     */    
    public static String padr(int origen, int longitud, String relleno) {
          return padr(String.valueOf(origen), longitud, relleno);
    }
    
    public static Connection getConnection(String strNombreDataSource) {

		 try {
			 Hashtable<String,String> env = new Hashtable<String,String>();
			 env.put(Context.INITIAL_CONTEXT_FACTORY, "com.ibm.websphere.naming.WsnInitialContextFactory");
		
			 InitialContext ctx = new InitialContext(env);
			 DataSource ds = (DataSource) ctx.lookup(strNombreDataSource);
		
			 return ds.getConnection();
		 } catch (Exception e) {
			 log.error("Error getConnection()", e);
			 error = e.getLocalizedMessage();
		 }
	 	return null;
	 }
    
    public static String getError() {
    	return error;
    }
    
    /***
     * Funcion que da los nombres de los archivos en una ruta especifica
     * @ruta Ruta de la cual se obtienen los nombres de los archivos
     */
    public static List<String> getNombresArchivos(String ruta){
    	
    	try {
    		
    		String[] archivos;
    		List<String> lstArchivos = new ArrayList<String>() ;
    		
    		FileFilterExtension fileFilterExt = new FileFilterExtension();
    		File directorio = new File(ruta);
    		archivos = directorio.list(fileFilterExt);
    		
    		for(int i = 0; i < archivos.length; i++ ){
    			lstArchivos.add(archivos[i]);    			
    		}
    		return lstArchivos;
			 
		 } catch (Exception e) {
			 log.error("Error getNombresArchivos()", e);
			 error = e.getLocalizedMessage();
		 }
	 	return null;
    }
    
 
    /***
     * Funcion que lee desde un archivo
     * @return Contenido leido desde el archivo
     */
    public static String getContenidoArchivo(String archivo) throws Exception {
    	
    	BufferedReader archivoLeido = new BufferedReader(new FileReader(archivo));
        try {
            StringBuilder archivoString = new StringBuilder();
            String line = archivoLeido.readLine();

            while (line != null) {
            	archivoString.append(line);
            	archivoString.append('\n');
                line = archivoLeido.readLine();
            }
           
            String everything = archivoString.toString();
            return everything;
        } 	
        catch ( IOException e){
        	log.error("Error getContenidoArchivo()", e);
		     error = e.getLocalizedMessage();
	    }finally {
        	try {
        		archivoLeido.close();
	    		} 
	    	catch (Exception e) {
	    		log.error("Error getContenidoArchivo()", e);
				error = e.getLocalizedMessage();
	    	} 
	    }
        return null;
    }
    
    /***
     * Funcion que escribe a un archivo
     * @return 
     * @contenido Contenido del archivo en bytes
     * @archivo Nombre del archivo
     */
    
    public static String setContenidoArchivo(String archivo, byte[] contenido) throws Exception {
    	
    	BufferedOutputStream bos = null;
    	try{
    		
    		FileOutputStream fos = new FileOutputStream(new File(archivo));
	    	bos = new BufferedOutputStream(fos);
	    	bos.write(contenido);
    		
    	}catch(Exception e){
    		
    		log.error("Error setContenidoArchivo()", e);
    		error = e.getLocalizedMessage();
    	}
    	finally{
    		
    		if(bos != null){
    			try{
    				
    				bos.flush();
    				bos.close();    				
    			
    			}catch(Exception e){
    				
    				log.error("Error setContenidoArchivo()", e);
    	    		error = e.getLocalizedMessage();
    			}
    		}
    		
    	}
    	
	    return archivo; 
    }

	/***
     * Funcion que escribe a un archivo
     * @return Nombre del archivo copiado
     */
	public static String copiarArchivo(String archivoIn, String archivoOut) {
		
		try {
		File source = new File(archivoIn);
		File destination = new File(archivoOut);
		FileUtils.copyFile(source, destination);
		return archivoOut;
		}
	    catch ( IOException e){
	    	log.error("Error copiarArchivo()", e);
	    	error = e.getLocalizedMessage();
	    }
		return null;
	}

	public static String borrarArchivo(String archivoIn) throws IOException{
		
		try{
		File file = new File(archivoIn);
		if(file.delete()){	
			archivoIn = file.getName() + " deleted";
		}else{
			archivoIn = "File deletion failed.";
		}	
		return archivoIn;
		}
		catch ( Exception e){
			log.error("Error borrarArchivo()", e);
	    	error = e.getLocalizedMessage();
	    }
		return null;
	}
	
	/**
	 * @author Ricardo J. Ramírez
	 * Procedimiento que elimina los acentos y los símbolos no estándar de una 
	 * cadena de texto
	 * @param str la cadena a procesar
	 * @return el resultado
	 */
	public static String quitarAcentos(String str) {
		// Reemplaza
		str = str.replaceAll("[áàâãª]", "a");
		str = str.replaceAll("[ÁÀÂÃ]", "A");
		str = str.replaceAll("[éèê]", "e");
		str = str.replaceAll("[ÉÈÊ]", "E");
		str = str.replaceAll("[íìî]", "i");
		str = str.replaceAll("[ÍÌÎ]", "I");
		str = str.replaceAll("[óòôõº]", "o");
		str = str.replaceAll("[ÓÒÔÕ]", "O");
		str = str.replaceAll("[úùû]", "u");
		str = str.replaceAll("[ÚÙÛ]", "U");
		str = str.replace("ñ", "n");
		str = str.replace("Ñ", "N");

		return str;
	}
	
	/**
	 * Permite encriptar el String suminstrado.
	 * @param key
	 * @param value
	 * @return
	 * @throws GeneralSecurityException
	 */
	public static String encrypt(String value) throws GeneralSecurityException {

		byte[] raw = Constants.ENCRY.getBytes(Charsets.US_ASCII);
		if (raw.length != 16) {
			throw new IllegalArgumentException("Invalid key size.");
		}

		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(new byte[16]));

		byte[] encripted = cipher.doFinal(value.getBytes(Charsets.US_ASCII));

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < encripted.length; i++) {
			sb.append(Integer.toString((encripted[i] & 0xff) + 0x100, 16).substring(1));
		}

		return sb.toString();
	}

	/**
	 * Permite desencriptar el String suminstrado.
	 * @param key
	 * @param encrypted
	 * @return
	 * @throws GeneralSecurityException
	 */
	public static String decrypt(String encrypted) throws GeneralSecurityException {

		byte[] original = null;
		try {
			byte[] raw = Constants.ENCRY.getBytes(Charsets.US_ASCII);
			if (raw.length != 16) {
				throw new IllegalArgumentException("Invalid key size.");
			}
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(new byte[16]));
			original = cipher.doFinal(hexStringToByteArray(encrypted));
		} catch (Exception e) {
			log.error("Error al desifrar", e);
		}

		String pss;
		try {
			pss = new String(original, Charsets.US_ASCII);
		} catch (Exception e) {
			pss = "";
		}
		
		return  pss != null ? pss : "";
	}
	
	/**
	 * Se convierte el String ingresado en un byte[]. Este string corresponde a 
	 * un hexadesimal.
	 * @param s		String de un hexadesimal.
	 * @return		
	 */
	public static byte[] hexStringToByteArray(String s) {
		byte[] data = null;
	    try {
	    	int len = s.length();
	    	data = new byte[len / 2];
	    	for (int i = 0; i < len; i += 2) {
	    		data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
	    				+ Character.digit(s.charAt(i+1), 16));
	    	}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	    return data;
	}
	
	
	/**
	 * Permite validar si el String dado es alfanumerico.
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isAlfaNumerico(String str){
		return str.matches(Constants.REX_ALFANUMERIC_ONLY);
	}
	
	/**
	 * Permite validar si el String dado es alfanumerico.
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isAlfaNumericoNBE(String str){
		return str.matches(Constants.REX_ALFANUMERIC_NSPACES_BE);
	}
	
	/**
	 * Permite validar si el String dado es numerico.
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumerico(String str){
		return str.matches(Constants.REX_NUMERIC);
	}
	
	/**
	 * Permite validar si el String dado es numerico sin ceros al inicio.
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumericoNCero(String str){
		return str.matches(Constants.REX_NUMERIC_NCERO);
	}
	
	/**
	 * Permite validar si el String es un correo.
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isCorreo(String str){
		return str.matches(Constants.REX_CORREO);
	}
	
	/**
	 * Permite validar si el String es un formato para una fecha (DATE).
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isDate(String str){
		return str.matches(Constants.REX_DATE_FORMAT);
	}
	
	/**
	 * Permite validar si el String es un formato para una fecha (TIMESTAMP).
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isTimestamp(String str){
		return str.matches(Constants.REX_TIMESTAMP_FORMAT);
	}

	
	
	
	/**
	 * Permite validar si el String dado es alfanumerico con espacios.
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isAlfaNumericoEspacios(String str){
		return str.matches(Constants.REX_ALFANUMERIC_SPACES);
	}
	
	
	/**
	 * Verifica si un String esta nulo o vacio
	 * @param cadena
	 * @return
	 */
	public static boolean nvl(String cadena){
		return (cadena != null) && (!cadena.isEmpty());
	}

	/**
	 * Verifica si un objeto es nulo
	 * @param objeto
	 * @return
	 */
	public static boolean nvl(Object objeto){
		return (objeto != null);
	}

	/**
	 * Verifica si un array es nulo o vacio
	 * @param objeto
	 * @return
	 */
	public static boolean nvl(List objeto){
		return (objeto != null) && (!objeto.isEmpty());
	}
	
	/**
	 * Permite verificar si el archivo ingresado tiene codificacion UTF8.
	 * 
	 * @param archivo
	 */
	public static boolean detectarCharsetUTF8Charset(String archivo) {  
//	    String encoding = "";
//	    File file = new File(archivo);
//	  
//	    try {  
//	        final FileInputStream fis = new FileInputStream(file.getAbsolutePath());  
//	        final UniversalDetector detector = new UniversalDetector(null);  
//	        handleData(fis, detector);  
//	        encoding = detector.getDetectedCharset();  
//	        detector.reset();  
//	          
//	        fis.close();  
//	  
//	    } catch (Exception e) {  
//	    	log.error("Ocurrió un error al intentar determinar el juego de aractéres del archivo " + archivo, e);  
//	    }  
	  
//	    return encoding.equals(Constants.ENCODING_UTF8) ? true : false;  
	    return true;  
	}
	
	/**
	 * Analiza el input stream archivo especificado.
	 * 
	 * @param fis
	 * @param detector
	 * @throws IOException
	 */
	private static void handleData(FileInputStream fis, UniversalDetector detector) throws IOException {  
	    int nread;  
	    final byte[] buf = new byte[4096];  
	    while ((nread = fis.read(buf)) > 0 && !detector.isDone()) {  
	        detector.handleData(buf, 0, nread);  
	    }  
	    detector.dataEnd();  
	} 
	
	public static List<TMihCatalogo> obtenerListadoCatalogos(List<TMihCatalogo> catalogos, short codMaestro){
		
		//List<TMihCatalogo> catalogos = catalogoManager.getCatalogos(new TMihCatalogo());
		List<TMihCatalogo> catalogoFiltrado = new ArrayList<TMihCatalogo>();
		for(TMihCatalogo cat:catalogos){
			if(cat.getTMihTipocatalogo().getCodmaestro()==codMaestro){
				catalogoFiltrado.add(cat);
			}
		}
		return catalogoFiltrado;
	}
	
	
}
 

//INNER CLASS
class FileFilterExtension implements FilenameFilter
{

    public boolean accept(File dir, String name)
    {
        return name.endsWith(".txt") || name.endsWith(".xml") ;
    }

}
