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
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hslf.record.Sound;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.jsf.FacesContextUtils;

import com.sophos.semih.model.Campo;
import com.sophos.semih.model.TMihEntidad;
import com.sophos.semih.model.TMihParametro;
import com.sophos.semih.model.TMihUsuario;
import com.sophos.semih.service.CargaManager;
import com.sophos.semih.service.EntidadManager;
import com.sophos.semih.service.ParametroManager;
import com.sophos.semih.util.CommandExecuter;
import com.sophos.semih.util.ParserConciliacion;
import com.sophos.semih.util.ParserRechazos;
import com.sophos.semih.util.ReportGenerator;
import com.sophos.semih.util.Utilidades;

@ManagedBean
@ViewScoped
public class PrevalidadorBean extends BaseBean {

	private static final long serialVersionUID = -162443766249729812L;
	private List<TMihEntidad> entidades;
	private List<String> archivos;
	private ArrayList<String> listaSeparadores;
	private CargaManager gManager;
	
	private String tipoCarga;
	private boolean nulo;
	private boolean encabezado;
	private String nombreEntidad;
	private String archivo;	
	private String delimiter;
	private boolean popupVisible;	
	private boolean rowsVisible;
	private String carError;
	private int codigo;
	private String lectura="";
	
	private TMihUsuario usuario = new TMihUsuario();
	private TMihEntidad entidad = new TMihEntidad();
	private List<Campo> campos;
	private Map<String, String> campoSelecionado;
	private String[] camposSeleccionado;
	
	private EntidadManager entManager;
	private ParametroManager paramManager;
	private static final Log log = LogFactory.getLog(PrevalidadorBean.class);
	private BeanFactory factory = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
	
	public PrevalidadorBean(){
		
		//Se obtiene la ruta
		setParamManager((ParametroManager) factory.getBean("parametroManager"));
		TMihParametro parametro = paramManager.getParametroById("RUTA_CARGA");
		
		//Se obtiene el delimitador
		TMihParametro separadores = paramManager.getParametroById("UPLOAD_DELIMITER");
		listaSeparadores = splitString(separadores.getValor(), "-");
		
		//**Se obtiene la lista de archivos
		this.setArchivos(Utilidades.getNombresArchivos(parametro.getValor()));
		
		//Se obtienen las entidades asociadas al usuario
		setEntManager((EntidadManager) factory.getBean("entidadManager"));
		this.setEntidades(entManager.getEntidadesxUsuario());	
		
		this.rowsVisible = false;

	}

	public String getCarError() {
		return carError;
	}

	public void setCarError(String carError) {
		this.carError = carError;
	}

	public Map<String, String> getCampoSelecionado() {
		return campoSelecionado;
	}

	public void setCampoSelecionado(Map<String, String> campoSelecionado) {
		this.campoSelecionado = campoSelecionado;
	}

	public String[] getCamposSeleccionado() {
		return camposSeleccionado;
	}

	public void setCamposSeleccionado(String[] camposSeleccionado) {
		this.camposSeleccionado = camposSeleccionado;
	}

	public Map<String,String> getCamposSeleccionadoValue() {
		return campoSelecionado;
	}
 
	public String getCamposSelecionadoInString() {
		return Arrays.toString(camposSeleccionado);
	}

	public TMihEntidad getEntidad() {
		return entidad;
	}

	public void setEntidad(TMihEntidad entidad) {
		this.entidad = entidad;
	}

	public int getCodigo() {
		return codigo;
	}
	
	public void setCodigo(int codigo) {
		this.codigo = codigo;
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


	public boolean isNulo() {
		return nulo;
	}


	public void setNulo(boolean nulo) {
		this.nulo = nulo;
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


	public void setParamManager(ParametroManager paramManager) {
		this.paramManager = paramManager;
	}
	
	public TMihUsuario getUsuario() {
		return usuario;
	}

	public void setUsuario(TMihUsuario usuario) {
		this.usuario = usuario;
	}

	public List<Campo> getCampos() {
		return campos;
	}

	public void setCampos(List<Campo> campos) {
		this.campos = campos;
	}
	
	public boolean isRowsVisible() {
		return rowsVisible;
	}

	public void setRowsVisible(boolean rowsVisible) {
		this.rowsVisible = rowsVisible;
	}

	public boolean isPopupVisible() {
		return popupVisible;
	}


	public void setPopupVisible(boolean popupVisible) {
		this.popupVisible = popupVisible;
	}
	
	public CargaManager getgManager() {
		return gManager;
	}

	public void setgManager(CargaManager gManager) {
		this.gManager = gManager;
	}
	
	public String getLectura() {
		return lectura;
	}

	public void setLectura(String lectura) {
		this.lectura = lectura;
	}

	/************
	* 
	* Método para generar el reporte de conciliación
	* 
	*/
	
	public void generarReporteConciliacion(String archivoLog, String archivoSalida, TMihEntidad entidad, HashMap<String, String> hashSumatoria ) throws Exception{
		
		try {
			
			ParserConciliacion parserConciliacion = new ParserConciliacion();			
			String resultado = ReportGenerator.getReporte(archivoLog, archivoSalida);				
			log.info(resultado);
			log.info(archivoSalida);
			parserConciliacion.setTxtInName(resultado);
			parserConciliacion.setLineaNegocio(entidad.getTMihAplicacion().getTMihLineanegocio().getNombre());
			parserConciliacion.setAplicativo(entidad.getTMihAplicacion().getNombre());
			parserConciliacion.setTabla(entidad.getNombre());
			parserConciliacion.setHashCampos(hashSumatoria);
			
			parserConciliacion.start();
			
		}
		catch ( IOException e){
			log.error("Error al generar el reporte de conciliación", e);
		}
	}


	/************
	* 
	* Método para generar el reporte de rechazos
	* 
	*/
	
	public void generarReporteRechazos(String archivoLog, String archivoSalida) throws Exception{
		
		try {
			
			ParserRechazos parserRechazos = new ParserRechazos();
			String rechazo = ReportGenerator.getRechazos(archivoLog, archivoSalida);
			parserRechazos.setTxtInName(rechazo);
			parserRechazos.start();			
			
		}
		catch ( IOException e){
			log.error("Error al generar el reporte de rechazos", e);
		}
	}
	
	

	/************
	* 
	* Método para validar archivos
	* 
	*/
	public String validarArchivo(){

		String originalFile;
		String encabezado = (this.isEncabezado()? "S" : "N");


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
			this.setTipoCarga("R");
			
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
			entidadTemporal.setCampos(entManager.getCampos(this.entidad));			
				
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
			
								
			//Definicion del directorio donde estan los binarios
			String sqlDirectoryt = sqlldrDirectory.getValor();			
					
			//Crea la tabla temporal
			@SuppressWarnings("unused")
			String creaTablaTemporal = gManager.generaTablaTemporal(this.getNombreEntidad(), this.isNulo());	
			
			//Se define el nombre del archivo de control
			String nombreArchivo = directorioCtl + separadorRuta + "TEMP_" + this.getNombreEntidad() + stamp + ".ctl";
						
			//Se realiza la consulta de los par�metros del archivo de control
			String consulta = gManager.generaArchivoControl("TEMP_" + this.getNombreEntidad(), this.getArchivo(), stamp, this.getTipoCarga(), encabezado, this.getDelimiter(), separadorRuta, contenido);
			
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
	        	String temp="TEMP_"+nombreEntidad;
	        	
	            bw = new BufferedWriter(new FileWriter(file));	            
	            if(this.encabezado) {
	            	bw.write("OPTIONS (SKIP=1)\n");
	            	}
	            bw.write("LOAD DATA APPEND\nINTO TABLE "+oracle_schema.getValor()+"."+temp+ "(");	 
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
	        
	        
	        //Lectura del archivo log
	        String log_text=directorioSal+"\\"+"log_"+stamp+".log";
	         try(BufferedReader br=new BufferedReader(new FileReader(log_text));
		            ){
		        		        	       	
	            
		           //Leemos el fichero y lo mostramos por pantalla
		          String linea=br.readLine();		          
		           while(linea!=null){
		              System.out.println(linea);
		              this.lectura=this.lectura+linea+"\n";
		              linea=br.readLine();
		            }
		           
		        }catch(IOException e){
		            System.out.println("Error E/S: "+e);
		        }	        
	         setMessage("mantenimiento.exito", BaseBean.INFO, null, null);         
		} catch (Exception e) {
			log.error("Error generado en la carga del archivo", e);
		}finally{
			
			this.popupVisible = false;
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("prevalidador.xhtml");
			} catch (IOException e) {
				log.error("Error generado en la carga del archivo", e);
			}
		}
		
		return null;		
	}
	
	/************
	* 
	* Esta función hace split a un string
	* 
	*/
	public ArrayList<String> splitString(String separadores, String delimiter){
		
		ArrayList<String> listaSeparador = new ArrayList<String>();
		String[] temporal;
		
		try {
			temporal = separadores.split(delimiter);
			for(int i =0; i < temporal.length ; i++)
				listaSeparador.add(temporal[i]);
			return listaSeparador;
		} catch (Exception e){
			log.error("Error en splitString()", e);
		}
		return listaSeparadores;
	}

	/************
	* 
	* Esta función consulta los campos de una tabla y los filtra
	* 
	*/
	public String consultarCampos(){
		
		campoSelecionado = new LinkedHashMap<String, String>();
		List<Campo> iteradorCampos = new ArrayList<Campo>();
		
		if (!this.campoSelecionado.isEmpty()){
			this.campoSelecionado.clear();
		}
		this.setEntidad(entManager.getEntidadById(Integer.parseInt(this.nombreEntidad)));
		this.entidad.setCampos(entManager.getCampos(this.entidad));
		
		//Se recorre todos los campos, se hace filtrado y se llena el arreglo de campos posibles para la sumatoria
		for(int campo=1;campo<=this.entidad.getCampos().size();campo=campo+1){
			if (this.entidad.getCampos().get(campo-1).getTipoDato().toString().equals("NUMBER")) {
				if (!(this.entidad.getCampoLlave().equals(this.entidad.getCampos().get(campo-1).toString())||
				    this.entidad.getCampoFecha().equals(this.entidad.getCampos().get(campo-1).toString()))){
					iteradorCampos.add(this.entidad.getCampos().get(campo-1));
					this.campoSelecionado.put(this.entidad.getCampos().get(campo-1).toString(), 
							                  this.entidad.getCampos().get(campo-1).getNombreCorto());
				}
			}
		}
		this.setCampos(iteradorCampos);
		
		
		this.rowsVisible = true;

		return null;
	}
	
	/************
	* 
	* Esta función limpia los campos seleccionados de una tabla
	* 
	*/
	public void limpiar(){
		
		this.refrescarValores();
		super.clearMessages();
		
		this.nombreEntidad = null;
		this.encabezado = false;
		this.nulo = false;
		this.delimiter = null;
		this.archivo = null;
		
		if (this.entidad != null && this.entidad.getNombre() != null && !this.entidad.getNombre().isEmpty()){
			this.entidad.setCampos(null);
		}
		
		if (this.campoSelecionado != null && !this.campoSelecionado.isEmpty() ){
			this.campoSelecionado.clear();
		}
		this.rowsVisible = false;
	}
	
	public String mostrarPopup(){
		
		this.setEntidad(entManager.getEntidadById(Integer.parseInt(this.nombreEntidad)));
		this.setNombreEntidad(this.entidad.getNombre());
		
		this.popupVisible = true;
		this.rowsVisible = true;
    
		Character separatorS = File.separatorChar; 
		String separadorRuta = Character.toString(separatorS);
		String directorioCrg = paramManager.getParametroById("RUTA_CARGA").getValor();
		String originalFile = directorioCrg + separadorRuta + this.getArchivo();
		
				
		boolean isUtf8 = Utilidades.detectarCharsetUTF8Charset(originalFile);
		if (!isUtf8) {
			super.setMessage("historicos.prevalidador.encodign", BaseBean.WARNING, "cargaDatosform:btnCargar", null);
		}
		
		return null;
	}
	
	public String ocultarPopup(){
		
		this.popupVisible = false;
		this.rowsVisible = true;
        
        return null;
	}
	
	
	public String getFormEntidad(){
		return null;
	}
	
	public void refrescarValores(){
		ArrayList<String> ids = new ArrayList<String>();
		ids.add("cargaDatosform:codEntidad");
		ids.add("cargaDatosform:encabezado");
		ids.add("cargaDatosform:datosNulos");
		ids.add("cargaDatosform:codSeparador");
		ids.add("cargaDatosform:somArchivo");
		super.resetValues(ids);
	}
	

}
