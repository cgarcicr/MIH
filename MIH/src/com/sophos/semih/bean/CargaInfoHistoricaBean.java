package com.sophos.semih.bean;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.jsf.FacesContextUtils;

import com.sophos.semih.model.TMihEntidad;
import com.sophos.semih.model.TMihParametro;
import com.sophos.semih.model.TMihUsuario;
import com.sophos.semih.service.CargaManager;
import com.sophos.semih.service.EntidadManager;
import com.sophos.semih.service.ParametroManager;
import com.sophos.semih.util.CommandExecuter;
import com.sophos.semih.util.Utilidades;

@ManagedBean
@ViewScoped
public class CargaInfoHistoricaBean extends BaseBean {
	
	private static final long serialVersionUID = -162443766249729812L;
	private List<TMihEntidad> entidades;
	private List<String> archivos;
	private ArrayList<String> listaSeparadores;
	private CargaManager gManager;
	
	private String tipoCarga;
	private boolean encabezado;
	private String nombreEntidad;
	private String archivo;	
	private String delimiter;
	private boolean popupVisible;

	private TMihUsuario usuario;
	private TMihEntidad entidad = new TMihEntidad();
	
	private EntidadManager entManager;
	private ParametroManager paramManager;
	private BeanFactory factory;
	private static final Log log = LogFactory.getLog(CargaInfoHistoricaBean.class);
	
	
	public CargaInfoHistoricaBean(){
		
		setUsuario(new TMihUsuario());
		factory = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
		
		//Se obtiene la ruta
		setParamManager((ParametroManager) factory.getBean("parametroManager"));
		TMihParametro parametro = paramManager.getParametroById("RUTA_CARGA");
		
		//Se obtiene el delimitador
		TMihParametro separadores = paramManager.getParametroById("UPLOAD_DELIMITER");
		listaSeparadores = splitString(separadores);
		
		//**Se obtiene la lista de archivos
		this.setArchivos(Utilidades.getNombresArchivos(parametro.getValor()));
		
		//Se obtienen las entidades asociadas al usuario
		setEntManager((EntidadManager) factory.getBean("entidadManager"));
		this.setEntidades(entManager.getEntidadesxUsuario());				
		
	}


	public ArrayList<String> getListaSeparadores() {
		return listaSeparadores;
	}


	public void setListaSeparadores(ArrayList<String> listaSeparadores) {
		this.listaSeparadores = listaSeparadores;
	}


	public List<TMihEntidad> getEntidades() {
		return entidades;
	}


	public void setEntidades(List<TMihEntidad> entidades) {
		this.entidades = entidades;
	}
	
		
	public String getTipoCarga() {
		return tipoCarga;
	}


	public void setTipoCarga(String tipoCarga) {
		this.tipoCarga = tipoCarga;
	}


	public String getDelimiter() {
		return delimiter;
	}


	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}


	public boolean isEncabezado() {
		return encabezado;
	}


	public void setEncabezado(boolean encabezado) {
		this.encabezado = encabezado;
	}


	public String getNombreEntidad() {
		return nombreEntidad;
	}


	public void setNombreEntidad(String nombreEntidad) {
		this.nombreEntidad = nombreEntidad;
	}


	public List<String> getArchivos() {
		return archivos;
	}


	public void setArchivos(List<String> archivos) {
		this.archivos = archivos;
	}


	public String getArchivo() {
		return archivo;
	}


	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}


	public EntidadManager getEntManager() {
		return entManager;
	}


	public void setEntManager(EntidadManager entManager) {
		this.entManager = entManager;
	}
	

	public ParametroManager getParamManager() {
		return paramManager;
	}	

	public TMihEntidad getEntidad() {
		return entidad;
	}


	public void setEntidad(TMihEntidad entidad) {
		this.entidad = entidad;
	}


	public void setParamManager(ParametroManager paramManager) {
		this.paramManager = paramManager;
	}
	
	
	public boolean isPopupVisible() {
		return popupVisible;
	}


	public void setPopupVisible(boolean popupVisible) {
		this.popupVisible = popupVisible;
	}


	@SuppressWarnings("unused")
	public String cargarArchivo(){

		String originalFile;
		String encabezado = (this.isEncabezado()? "S" : "N");
		
		if(tipoCarga.equalsIgnoreCase("A")) {
			this.tipoCarga="APPEND";
		}else if(this.tipoCarga.equalsIgnoreCase("R")) {
			this.tipoCarga="REPLACE";
		}		
		
		try{

			/************
			* 
			* Parámetros iniciales
			* 
			*/
			
			factory = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			gManager = (CargaManager)factory.getBean("cargaManager");
			HashMap<String, String> sqlVarEntorno = new HashMap<String, String>();
			String sqlldrCommand = "./sqlldr";	
			DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			Date date = new Date();			
			String stamp = dateFormat.format(date);
			String contenido = null;
			Character separatorS = File.separatorChar; 
			String separadorRuta = Character.toString(separatorS);
			setEntManager((EntidadManager) factory.getBean("entidadManager"));
			this.setEntidades(entManager.getEntidadesxUsuario());	
//			this.setTipoCarga("R");
			
			//Se define la ruta de carga
			String directorioCrg = paramManager.getParametroById("RUTA_CARGA").getValor();
			
			//Se define la ruta de control
			String directorioCtl = paramManager.getParametroById("RUTA_CONTROL").getValor();

			//Se define la ruta de salida
			String directorioSal = paramManager.getParametroById("RUTA_SALIDA").getValor();
			
			//Variable del sistema operativo
			TMihParametro os = paramManager.getParametroById("WIN_OS_SERVER");
			
			//Se toman los campos de la entidad
			TMihEntidad entidadTemporal = new TMihEntidad();
			TMihEntidad obj=new TMihEntidad();
			obj.setNombre(this.nombreEntidad);			
			entidadTemporal.setCampos(entManager.getCampos(obj));
			
			/************
			* 
			* Parametros de oracle
			* 
			*/
			//Variable del oracle home 
			TMihParametro sqlldrDirectory = paramManager.getParametroById("ORACLE_HOME");
			sqlVarEntorno.put("ORACLE_HOME",sqlldrDirectory.getValor());
			
			//Variable del oracle sid
			TMihParametro oracle_sid = paramManager.getParametroById("ORACLE_SID");
			sqlVarEntorno.put("ORACLE_SID",oracle_sid.getValor());
		
			//Variables de entorno
			TMihParametro oracle_user = paramManager.getParametroById("ORACLE_USER");
			TMihParametro oracle_password = paramManager.getParametroById("ORACLE_PASSWORD");
			TMihParametro oracle_schema=paramManager.getParametroById("ORACLE_SCHEMA");
			
			//Se define el nombre del archivo de control
			String nombreArchivo = directorioCtl + separadorRuta + this.getNombreEntidad() + stamp + ".ctl";
						
			//Se realiza la consulta de los par�metros del archivo de control
			String consulta = gManager.generaArchivoControl(this.getNombreEntidad(), this.getArchivo(), stamp, this.getTipoCarga(), encabezado, this.getDelimiter(), separadorRuta, contenido);
			
			//Se genera el archivo de control
//			String nombreArchivoCtl = Utilidades.setContenidoArchivo(nombreArchivo, consulta.getBytes());

			//Creación del archivo .CTL
		    File file = new File(nombreArchivo);
	        BufferedWriter bw;
	        if(file.exists()) {
	            bw = new BufferedWriter(new FileWriter(file));	                       
	            bw.write("El fichero de texto ya estaba creado.");
	            System.out.println("El fichero ya existe en la ruta");
	        } else {	        	   	
	        	String temp=nombreEntidad;
	        	
	            bw = new BufferedWriter(new FileWriter(file));	            
	            if(this.encabezado) {
	            	bw.write("OPTIONS (SKIP=1)\n");
	            	}
	            bw.write("LOAD DATA "+this.tipoCarga+"\nINTO TABLE "+oracle_schema.getValor()+"."+temp+ "(");	 
	            String tipoDato;
	            String auxDato="";
	            
	            for (int i = 0; i <entidadTemporal.getCampos().size() ; i++) {
	            	tipoDato=entidadTemporal.getCampos().get(i).getTipoDato();
	            	if(tipoDato.equals("NUMBER")) {
	            		auxDato=" INTEGER EXTERNAL";	            		
	            	}else if(tipoDato.equals("VARCHAR2")||tipoDato.equals("VARCHAR")){
	            		auxDato=" CHAR";
	            	}else if(tipoDato.equals("DATE")){
	            		auxDato=" DATE";
	            	}
	            	
	            	if(i+1!=entidadTemporal.getCampos().size()) {	            		
	            		bw.write("\n"+entidadTemporal.getCampos().get(i).getNombreCompleto()+auxDato+" TERMINATED BY "+"\""+delimiter+"\",");
	            	}else{
	            		bw.write("\n"+entidadTemporal.getCampos().get(i).getNombreCompleto()+auxDato+" TERMINATED BY "+"\""+delimiter+"\"");
	            	}					
				}
	            bw.write("\n)");	            	      
	            System.out.println("Se acaba de crear el fichero en la ruta"+directorioCtl);
	        }
	        bw.close();
	        
			
			/************
			* 
			* Parametros para la ejecucion del sql loader
			* 
			*/
	        try {  	        	
	        	String comando="sqlldr "+oracle_user.getValor()+"/"+Utilidades.decrypt(oracle_password.getValor())+"@"+oracle_sid.getValor()+" control=\'"+nombreArchivo+"\'"+" data=\'"+directorioCrg+"\\"+archivo+"\'"+" log=\'"+directorioSal+"\\"+"log_"+stamp+".log\'";
		        System.out.println(".... Comando a ejecutar ..."+comando);
		        
		        // Se lanza el ejecutable.
	            Process p=Runtime.getRuntime().exec ("cmd /c "+comando);
	            
	            // Se obtiene el stream de salida del programa
	            InputStream is = p.getInputStream();
	            
	            /* Se prepara un bufferedReader para poder leer la salida más comodamente. */
	            BufferedReader br = new BufferedReader (new InputStreamReader (is));
	            
	            // Se lee la primera linea
	            String aux = br.readLine();
	            
	            // Mientras se haya leido alguna linea
	            while (aux!=null)
	            {
	                // Se escribe la linea en pantalla
	                System.out.println (aux);
	                
	                // y se lee la siguiente.
	                aux = br.readLine();
	            }
				
			} catch (Exception e) {
				System.out.println("No es posible realiza la carga del archivo ctrl con SQLLDR "+e);
			}	        	        
	           
		} catch (Exception e) {
			log.error("Error generado en la carga del archivo", e);
		}finally{
			
			this.popupVisible = false;
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("carga_infoHistorica.xhtml");
			} catch (IOException e) {
				log.error("Error generado en la carga del archivo", e);
			}
		}
		
		return null;		
	}
	
	public ArrayList<String> splitString(TMihParametro separadores){
		
		ArrayList<String> listaSeparador = new ArrayList<String>();
		String delimiter = "-";
		String[] temporal;
		
		try {
			temporal = separadores.getValor().split(delimiter);
			for(int i =0; i < temporal.length ; i++)
				listaSeparador.add(temporal[i]);
			return listaSeparador;
		} catch (Exception e){
			log.error("Error en splitString", e);
		}
		return listaSeparadores;
	}
	
	public String limpiarFormulario(){
		this.refrescarValores();
		this.nombreEntidad = null;
		this.tipoCarga = null;
		this.encabezado = false;
		this.delimiter = null;
		this.archivo = null;
		super.clearMessages();
		return "";
		
//		try {
//			FacesContext.getCurrentInstance().getExternalContext().redirect("carga_infoHistorica.xhtml");
//		} catch (IOException e) {
//			log.error("Error al cargar la página", e);
//		}

	}
	
	public void refrescarValores(){
		ArrayList<String> ids = new ArrayList<String>();
		ids.add("cargaDatosform:codEntidad");
		ids.add("cargaDatosform:somModoCarga");
		ids.add("cargaDatosform:encabezado");
		ids.add("cargaDatosform:codSeparador");
		ids.add("cargaDatosform:somArchivo");
		super.resetValues(ids);
	}
	
	public String mostrarPopup(){
		this.popupVisible = true;
		System.out.println("Datos: "+delimiter+ encabezado+ archivo);
				        
//		Character separatorS = File.separatorChar; 
//		String separadorRuta = Character.toString(separatorS);
//		String directorioCrg = paramManager.getParametroById("RUTA_CARGA").getValor();
//		String originalFile = directorioCrg + separadorRuta + this.getArchivo();
		
//		boolean isUtf8 = Utilidades.detectarCharsetUTF8Charset(originalFile);
//		if (!isUtf8) {
//			super.setMessage("historicos.prevalidador.encodign", BaseBean.WARNING, "cargaDatosform:btnCargar", null);
//		}
		
        return null;
	}
	
	public String ocultarPopup(){
		this.popupVisible = false;
        
        return null;
	}


	public TMihUsuario getUsuario() {
		return usuario;
	}


	public void setUsuario(TMihUsuario usuario) {
		this.usuario = usuario;
	}
	
}
