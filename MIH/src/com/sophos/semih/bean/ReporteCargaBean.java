package com.sophos.semih.bean;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.jsf.FacesContextUtils;

import com.sophos.semih.common.Constants;
import com.sophos.semih.model.TMihControlcarga;
import com.sophos.semih.model.TMihEntidad;
import com.sophos.semih.model.TMihParametro;
import com.sophos.semih.model.TMihUsuario;
import com.sophos.semih.service.CargaManager;
import com.sophos.semih.service.EntidadManager;
import com.sophos.semih.service.ParametroManager;
import com.sophos.semih.util.ParserConciliacion;
import com.sophos.semih.util.ParserRechazos;
import com.sophos.semih.util.ReportGenerator;
import com.sophos.semih.util.Utilidades;

@ManagedBean
@ViewScoped
public class ReporteCargaBean extends BaseBean{
	
	private static final long serialVersionUID = -3654545675897622580L;
	
	private int codEntidad;
	private String archivo;
	private String archivoRechazos;
	private String resultado;
	private String rechazo;
	private String rechazado;
	private List<TMihEntidad> entidades;
	private List<TMihControlcarga> cargas;
	private TMihControlcarga carga;
	private String cargaSeleccionada;
	private TMihControlcarga cargaReportada;
	private String carError;
	private ArrayList<String> columnas;
	private int pag = 1;
	private boolean cargado;
	private boolean cargadoRg = false;
	private boolean mostrarResultado;
	
	private EntidadManager entManager;
	private ParametroManager paramManager;
	private CargaManager managerCarga;
	private TMihUsuario logedUser = super.getUser();
	private static final Log log = LogFactory.getLog(ReporteCargaBean.class);
	private BeanFactory factory = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
	private Character seperadorPath = File.separatorChar;
	private ReporteCargaLazy reporteLazy;
	private LazyDataModel<HashMap<TMihControlcarga, BigDecimal>> lazyModel;
	
	public ReporteCargaBean() throws Exception{
		
		try
		{

		// Se obtiene la ruta de los archivos a cargar
		setParamManager((ParametroManager) factory.getBean("parametroManager"));
	
		// Se obtiene todas las cargas
		setManagerCarga((CargaManager) factory.getBean("cargaManager"));
		setCargas(managerCarga.getControlcargasxUsuario());
		
		//reporte Lazy
		reporteLazy = new ReporteCargaLazy();
		lazyModel = reporteLazy;
		
		}
		catch (Exception e){
			log.error("Error al inicializar la instancia de ReporteCargaBean", e);
		}
		
	}

	/**
	 * Recoje la informacion necesaria para hacer la consulta dinamica.
	 * @return
	 */
	public String consultar(){
		String resultado = "";
		try {
			
			lazyModel = new ReporteCargaLazy();

			//Campos de la tabla que apareceran en la vista.
			columnas.clear();
			List<String> camposEntidad = this.obtenerCamposEntidad();
			for (String campo : camposEntidad) {
				columnas.add(campo);
			}
			
			mostrarResultado = true;
			resultado = "exito";
		} catch (Exception e) {
			log.error("Error al hacer la consulta especifica", e);
		}
		return resultado;
	}
	
	
	
	public boolean isCargado() {
		return cargado;
	}

	public void setCargado(boolean cargado) {
		this.cargado = cargado;
	}

	public boolean isCargadoRg() {
		return cargadoRg;
	}

	public void setCargadoRg(boolean cargadoRg) {
		this.cargadoRg = cargadoRg;
	}
	
	public TMihUsuario getLogedUser() {
		return logedUser;
	}


	public void setLogedUser(TMihUsuario logedUser) {
		this.logedUser = logedUser;
	}


	public List<TMihControlcarga> getCargas() {
		return cargas;
	}


	public void setCargas(List<TMihControlcarga> cargas) {
		this.cargas = cargas;
	}


	public TMihControlcarga getCarga() {
		return carga;
	}


	public void setCarga(TMihControlcarga carga) {
		this.carga = carga;
	}


	public CargaManager getManagerCarga() {
		return managerCarga;
	}

	public void setManagerCarga(CargaManager managerCarga) {
		this.managerCarga = managerCarga;
	}

	public List<TMihEntidad> getEntidades() {
		return entidades;
	}


	public void setEntidades(List<TMihEntidad> entidades) {
		this.entidades = entidades;
	}
	
	
	public int getCodEntidad() {
		return codEntidad;
	}


	public void setCodEntidad(int codEntidad) {
		this.codEntidad = codEntidad;
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
	
	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
	
	public String getRechazo() {
		return rechazo;
	}

	public void setRechazo(String rechazo) {
		this.rechazo = rechazo;
	}

	public String getRechazado() {
		return rechazado;
	}

	public void setRechazado(String rechazado) {
		this.rechazado = rechazado;
	}
	
	public String getArchivoRechazos() {
		return archivoRechazos;
	}

	public void setArchivoRechazos(String archivoRechazos) {
		this.archivoRechazos = archivoRechazos;
	}
	

	public String getCargaSeleccionada() {
		return cargaSeleccionada;
	}


	public void setCargaSeleccionada(String cargaSeleccionada) {
		this.cargaSeleccionada = cargaSeleccionada;
	}
	
	public ArrayList<String> getColumnas() {
		return columnas;
	}

	public void setColumnas(ArrayList<String> columnas) {
		this.columnas = columnas;
	}
	
	public boolean getMostrarResultado() {
		return mostrarResultado;
	}

	public void setMostrarResultado(boolean mostrarResultado) {
		this.mostrarResultado = mostrarResultado;
	}
	
	public ReporteCargaLazy getReporteLazy() {
		return reporteLazy;
	}

	public void setReporteLazy(ReporteCargaLazy reporteLazy) {
		this.reporteLazy = reporteLazy;
	}

	public LazyDataModel<HashMap<TMihControlcarga, BigDecimal>> getLazyModel() {
		return lazyModel;
	}

	public void setLazyModel(
			LazyDataModel<HashMap<TMihControlcarga, BigDecimal>> lazyModel) {
		this.lazyModel = lazyModel;
	}

	public int getPag() {
		return pag;
	}

	public void setPag(int pag) {
		this.pag = pag;
	}
	
	/**
	 * Obtiene los campos que tiene la primera entidad seleccionada
	 * @return
	 */	
    private List<String> obtenerCamposEntidad() {
    	List<String> listaCampos = new ArrayList<String>();	
    		
    	listaCampos.add(Constants.CODIGO_CARGA);
    	listaCampos.add(Constants.CODIGO_ENTIDAD);
    	listaCampos.add(Constants.CODIGO_USUARIO);
    	listaCampos.add(Constants.FECHA_CARGA);
    	
		return listaCampos;
	}	
	
	public void generarReporteConciliacion() throws Exception{
		
		try {
			
			
//			if(this.cargado){
//				
				ParserConciliacion parserConciliacion = new ParserConciliacion();
				
				TMihParametro rutaSalida = paramManager.getParametroById("RUTA_SALIDA");
				cargaReportada = managerCarga.getControlcargaById(cargaSeleccionada);
				
				String conciliacion = FilenameUtils.removeExtension(FilenameUtils.getName(cargaReportada.getArchivolog()));
				
				String conciliacionSalida = rutaSalida.getValor() + seperadorPath + conciliacion + "CONCILIACION.csv";
				
				String resultado = ReportGenerator.getReporte(cargaReportada.getArchivolog(), conciliacionSalida);		
				
				log.info(resultado);
				log.info(conciliacionSalida);
				
				parserConciliacion.setTxtInName(resultado);
				parserConciliacion.setLineaNegocio(cargaReportada.getTMihEntidad().getTMihAplicacion().getTMihLineanegocio().getNombre());
				parserConciliacion.setAplicativo(cargaReportada.getTMihEntidad().getTMihAplicacion().getNombre());
				parserConciliacion.setTabla(cargaReportada.getTMihEntidad().getNombre());
				
				parserConciliacion.start();
				
//				this.cargadoRg = false;
//			} else{
//				this.cargadoRg = true;
//			}
		}
		catch ( IOException e){
			log.error("Error al generar el reporte de conciliación", e);
		}
	}

	public void generarReporteRechazos() throws Exception{
		
		try {
			
//			if(this.cargado){
				ParserRechazos parserRechazos = new ParserRechazos();
				
				TMihParametro rutaSalida = paramManager.getParametroById("RUTA_SALIDA");
				cargaReportada = managerCarga.getControlcargaById(cargaSeleccionada);
				
				String rechazos = FilenameUtils.removeExtension(FilenameUtils.getName(cargaReportada.getArchivolog()));
				
				String rechazosSalida = rutaSalida.getValor() + seperadorPath + rechazos + "RECHAZOS.csv"; 
						
				String rechazo = ReportGenerator.getRechazos(cargaReportada.getArchivolog(), rechazosSalida);
	
				parserRechazos.setTxtInName(rechazo);
				
				parserRechazos.start();			
				
//				this.cargadoRg = false;
//			} else{
//				this.cargadoRg = true;
//			}	
		}
		catch ( IOException e){
			log.error("Error al generar el reporte de rechazos", e);
		}
	}
	
	public void validarCarga() {
		
		this.cargado = false; 
		BigDecimal cargar = new BigDecimal(0);
		
		try{
			TMihParametro oracle_user = paramManager.getParametroById("ORACLE_USER");
			cargaReportada = managerCarga.getControlcargaById(cargaSeleccionada);
			
			cargar = managerCarga.consultarCarga(this.cargaReportada.getTMihEntidad().getNombre(), oracle_user.getValor());
	
			if(cargar.intValue() == 0){
				this.cargado = true;
			}
	
		} catch (Exception e) {
			log.error("Error al generar el reporte", e);
		}
	}
	
	public void eliminarRegistroRechazos() throws Exception{

		setCarError("");
		try {
			
			cargaReportada = managerCarga.getControlcargaById(cargaSeleccionada);	
				
			Utilidades.borrarArchivo(cargaReportada.getArchivocarga());
			Utilidades.borrarArchivo(cargaReportada.getArchivolog());
			Utilidades.borrarArchivo(cargaReportada.getArchivorechazo());
			Utilidades.borrarArchivo(cargaReportada.getArchivocarga().substring(0,cargaReportada.getArchivocarga().length() -4 )+".dsc");
			Utilidades.borrarArchivo(cargaReportada.getArchivocarga().substring(0,cargaReportada.getArchivocarga().length() -4 )+".ctl");
			
			managerCarga.deleteControlcarga(cargaReportada);	
			
			setCargas(managerCarga.getControlcargas(new TMihControlcarga()));
			
		} catch (Exception e) {
			setCarError("Error al eliminar " + e.getMessage());
			log.error("Error al eliminar los registros de rechazos", e);
		}
	}

	public String getCarError() {
		return carError;
	}

	public void setCarError(String carError) {
		this.carError = carError;
	}
	
}