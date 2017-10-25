package com.sophos.semih.bean;

import java.io.File;


import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
 
import org.primefaces.event.TransferEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DualListModel;
//

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.sound.midi.Soundbank;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.model.DualListModel;
import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.jsf.FacesContextUtils;

import com.sophos.semih.common.Constants;
import com.sophos.semih.model.Campo;
import com.sophos.semih.model.TMihConsfld;
import com.sophos.semih.model.TMihConsfldId;
import com.sophos.semih.model.TMihConstbl;
import com.sophos.semih.model.TMihConstblId;
import com.sophos.semih.model.TMihConsulta;
import com.sophos.semih.model.TMihEntidad;
import com.sophos.semih.model.TMihParametro;
import com.sophos.semih.service.ConsultaManager;
import com.sophos.semih.service.EntidadManager;
import com.sophos.semih.service.ParametroManager;
import com.sophos.semih.util.CommandExecuter;
import com.sophos.semih.util.ExcelUtilities;
import com.sophos.semih.util.Utilidades;
import com.sun.rowset.CachedRowSetImpl;

@ManagedBean
@ViewScoped
public class ConsultaDinamicaBean extends BaseBean{

	private static final long serialVersionUID = -6038817284932277349L;
	private EntidadManager eManager;
	private ParametroManager paramManager;
	private ConsultaManager cManager;
	private List<TMihEntidad> entidades;
	private List<TMihConsulta> consultas;
	private int consultadoId;
	private String constaDesc;
	private BeanFactory factory;
	private TMihConsulta consultado; 
	private List<TMihConstbl> conEntidad;
	private List<TMihConsfld> conCampo1;
	private List<TMihConsfld> conCampo2;
	private TMihEntidad entidad1;
	private TMihEntidad entidad2;
	private ArrayList<SelectItem> nombreEntidades;
	private boolean getConsulta;
	
	//PickList Primera columna
	private List<Campo> camposE1;
	
	//PickList Segunda columna	
	private List<Campo> camposE2;
		
	private List<Campo> camposSeleccionadosE1;
	private List<Campo> camposSeleccionadosE2;
	private String foranea;
	private ArrayList<String> columnas;
	private ConsultaDinamicaLazy consultaLazy;
	private LazyDataModel<LinkedHashMap<String, String>> lazyModel;
	private ArrayList<LinkedHashMap<String, String>> tablaDinamica;
	private boolean verificarTamano;
	private boolean buttonRender;
	private boolean mostrarGuardar = false;
	private static final Log log = LogFactory.getLog(ConsultaDinamicaBean.class);
	

	
	//Tercer picklist
	
	 private DualListModel<Campo> campos;
	 List<Campo> camposSource;
	 List<Campo> camposTarget;

	 private DualListModel<Campo> campos2;
	 List<Campo> camposSource2;
	 List<Campo> camposTarget2;	
	 
	 
	@ManagedProperty(value="#{entidadBean}")
	private EntidadBean entidadBean; 
	
	/**
	 * Constructor
	 */
	public ConsultaDinamicaBean() {
		this.verificarTamano = false;
		this.consultaLazy = new ConsultaDinamicaLazy(entidad1, entidad2, camposSeleccionadosE1, camposSeleccionadosE2, foranea, this);
		this.factory = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
		this.paramManager = (ParametroManager) factory.getBean("parametroManager");
		this.cManager = (ConsultaManager) factory.getBean("consultaManager");
		this.eManager = (EntidadManager) factory.getBean("entidadManager");
		this.entidades = eManager.getEntidadesxUsuario();
		try {
			this.consultas = cManager.getConsultasxusuario();
		} catch (Exception e) {
			log.error("Error al hacer la consulta especifica. Constructor", e);
		}
		this.columnas = new ArrayList<String>();
		this.entidad1 = new TMihEntidad();
		this.entidad2 = new TMihEntidad();
		this.lazyModel = consultaLazy;
		this.getConsulta = false;
		this.buttonRender = false;
		


//		camposSource=new ArrayList<Campo>();
//		camposTarget=new ArrayList<Campo>();
//		
//		camposSource2=new ArrayList<Campo>();
//		camposTarget2=new ArrayList<Campo>();
//		
		camposSeleccionadosE1=new ArrayList<Campo>();
		camposSeleccionadosE2=new ArrayList<Campo>();
		
		camposE1=new ArrayList<Campo>();
		camposE2=new ArrayList<Campo>();
		
		campos = new DualListModel<Campo>(camposE1, camposSeleccionadosE1);
		campos2 = new DualListModel<Campo>(camposE2, camposSeleccionadosE2);

  
	}
	
	/**
	 * Recoje la informacion necesaria para hacer la consulta dinamica.
	 * @return
	 */
	public String consultar() {
		String resultado = "";
		try {

			this.validaciones();
			this.verificarExportar();
			lazyModel = new ConsultaDinamicaLazy(entidad1, entidad2, camposSeleccionadosE1, camposSeleccionadosE2, foranea, this);
			columnas.clear();
			List<Campo> camposEntidad = this.setPrefijosCampos(camposSeleccionadosE1, camposSeleccionadosE2);

			for (Campo campo2 : camposEntidad) {
				campo2.hashCode();
				columnas.add(campo2.getNombreCorto());
			}

			this.getConsulta = true;
			this.buttonRender = false;
		} catch (Exception e) {
			log.error("Error al hacer la consulta especifica", e);
		}
		return resultado;
	}

	
	/**
	 * Selecciona la nueva consulta
	 * 
	 * @return
	 */
	public String consultarConsulta() {

		
		try {

			this.camposSeleccionadosE1 = new ArrayList<Campo>();
			this.camposSeleccionadosE2 = new ArrayList<Campo>();
	
			//Recibe la lista de entidades
			conEntidad = cManager.getEntidades(this.consultadoId);
			this.constaDesc = cManager.getConsultaById(this.consultadoId).getDescripcion();
	
			// Se consultan las tablas involucradas.
			this.entidad1 = eManager.getEntidadById(conEntidad.get(0).getTMihEntidad().getCodigo());
			this.entidad2 = eManager.getEntidadById(conEntidad.get(1).getTMihEntidad().getCodigo());
	
			// Se consultan los campos de la primera tabla
			this.camposE1 = eManager.getCampos(this.entidad1);
			conCampo1 = cManager.getCampos(this.entidad1.getCodigo(), this.consultadoId, conEntidad.get(0).getId().getSec());
			
			// Se llenan los campos selecionados de la primera estructura
			for (int i = 0; i < camposE1.size(); i++) {
				for (int j = 0; j < conCampo1.size(); j++) {
					if (camposE1.get(i).getNombreCorto().equals(conCampo1.get(j).getId().getCampo())) {
						this.camposSeleccionadosE1.add(camposE1.get(i));
					}
				}
			}
			System.out.println("El tamaño de la lista es: "+camposE1);
			// Se consultan los campos de la segunda tabla
			this.camposE2 = eManager.getCampos(this.entidad2);
			conCampo2 = cManager.getCampos(this.entidad2.getCodigo(), this.consultadoId, conEntidad.get(1).getId().getSec());
	
			// Se llenan los campos selecionados de la segunda estructura
			for (int i = 0; i < camposE2.size(); i++) {
				for (int j = 0; j < conCampo2.size(); j++) {
					if (camposE2.get(i).getNombreCorto().equals(conCampo2.get(j).getId().getCampo())) {
						this.camposSeleccionadosE2.add(camposE2.get(i));
						if (conCampo2.get(j).getLlave() != null && conCampo2.get(j).getLlave().equals(Constants.FLAG_AFIRMATIVO)) {
							this.foranea = camposE2.get(i).getNombreCorto();
						}
					}
				}
			}
			this.buttonRender = false;
		} catch (Exception e) {
			log.error("Error al generar el archivo de Excel", e);
		}
		return null;
	}
	
	
	/**
	 * Guarda la consulta
	 * @return
	 */
	public String guardarConsulta() {

		try {
			
			if(!this.getConstaDesc().trim().equals("")){
				
				this.setMostrarGuardar(false);
				TMihConsulta consultarealizada = cManager.getConsultaById(this.consultadoId);
				Calendar c = new GregorianCalendar();
				Date date = c.getTime();
				String keyUno = "";
				TMihEntidad entidadUno = eManager.getEntidadById(eManager.rowCount("SELECT CODIGO FROM T_MIH_ENTIDAD WHERE NOMBRE ='" + this.entidad1.getNombre() + "'"));
				TMihEntidad entidadDos = eManager.getEntidadById(eManager.rowCount("SELECT CODIGO FROM T_MIH_ENTIDAD WHERE NOMBRE ='" + this.entidad2.getNombre() + "'"));
				
				List<Campo> camposEntidadUno = this.eManager.getCampos(entidadUno);
				for (Campo campo : camposEntidadUno) {
					if (campo.getClave().equals(Constants.FLAG_ES_CLAVE)) {
						keyUno = campo.getNombreCorto();
					}
				}
				
				if (!(consultarealizada == null)) {
					this.constaDesc = cManager.getConsultaById(this.consultadoId).getDescripcion();
					cManager.deleteConsulta(consultarealizada);
				}
		
				TMihConsulta nuevaConsulta = new TMihConsulta();
				int nextval = cManager.nextVal("select SEQ_MIH_CONSULTAS.nextval from dual");
				nuevaConsulta.setCodigo(nextval);
				nuevaConsulta.setDescripcion(this.constaDesc);
				nuevaConsulta.setTMihUsuario(getUser());
				nuevaConsulta.setFechacreacion(date);
				cManager.insertarConsulta(nuevaConsulta);
		
				// Entidad 1
				byte entUno = 1;
				TMihConstbl nuevaTabla1 = new TMihConstbl();
				TMihConstblId id1 = new TMihConstblId();
				id1.setCodconsulta(nextval);
				id1.setCodentidad(entidadUno.getCodigo());
				id1.setSec(entUno);
				nuevaTabla1.setId(id1);
				nuevaTabla1.setTMihConsulta(nuevaConsulta);
				nuevaTabla1.setTMihEntidad(entidadUno);
				cManager.insertarConstbl(nuevaTabla1);
		
				// Entidad 2
				byte entDos = 2;
				TMihConstbl nuevaTabla2 = new TMihConstbl();
				TMihConstblId id2 = new TMihConstblId();
				id2.setCodconsulta(nextval);
				id2.setCodentidad(entidadDos.getCodigo());
				id2.setSec(entDos);
				nuevaTabla2.setId(id2);
				nuevaTabla2.setTMihConsulta(nuevaConsulta);
				nuevaTabla2.setTMihEntidad(entidadDos);
				cManager.insertarConstbl(nuevaTabla2);
		
				
				for (short i = 0; i < camposSeleccionadosE1.size(); i++) {
					TMihConsfld fld1 = new TMihConsfld();
					TMihConsfldId fldId1 = new TMihConsfldId();
					fldId1.setCodconsulta(nextval);
					fldId1.setCodentidad(entidadUno.getCodigo());
					fldId1.setSectabla(entUno);
					fldId1.setSec(i);
					fldId1.setCampo(camposSeleccionadosE1.get(i).getNombreCorto());
					fld1.setId(fldId1);
					fld1.setTMihConsulta(nuevaConsulta);
					fld1.setTMihEntidad(entidadUno);
					if (keyUno.equals(camposSeleccionadosE1.get(i).getNombreCorto())) {
						fld1.setLlave('S');
					}
					cManager.insertarConsfld(fld1);
				}
				
				
		
				for (short i = 0; i < camposSeleccionadosE2.size(); i++) {
					TMihConsfld fld2 = new TMihConsfld();
					TMihConsfldId fldId2 = new TMihConsfldId();
					fldId2.setCodconsulta(nextval);
					fldId2.setCodentidad(entidadDos.getCodigo());
					fldId2.setSectabla(entDos);
					fldId2.setSec(i);
					fldId2.setCampo(camposSeleccionadosE2.get(i).getNombreCorto());
					fld2.setId(fldId2);
					fld2.setTMihConsulta(nuevaConsulta);
					fld2.setTMihEntidad(entidadDos);
					if (camposSeleccionadosE2.get(i).getNombreCorto().equals(this.foranea)) {
						fld2.setLlave('S');
					}
					cManager.insertarConsfld(fld2);
				}
				
				this.buttonRender = true;
				
			}else{
				super.setMessage("consultas.dinamicas.requerido.nombre_consulta", BaseBean.ERROR, "formEntidad:fs1:confirmGuardarCON:mensajes2", null);
				this.buttonRender = false;
			}
			
			
		} catch (Exception e) {
			log.error("Error al generar el archivo de Excel", e);
		}
		return null;
	}
	
	/**
	 * Elimina la consulta
	 * @return
	 */	
	
	public String eliminarConsulta(){
		
		try{
			cManager.deleteConsulta(cManager.getConsultaById(this.consultadoId));
			@SuppressWarnings("unused")
			String limpiar = limpiarCampos();
		} catch (Exception e) {
			log.error("Error al generar el archivo de Excel", e);
		}
		
		return null;
	}	
	
	/**
	 * Verifica si la cantidad de filas de del resultado es mayour al tope maximo.
	 * 
	 * @return
	 */
	public boolean verificarExportar(){
		try {
			this.validaciones();
			String query = this.createQueryRowCount(entidad1.getNombre(), entidad2.getNombre(), camposSeleccionadosE1, foranea);
			
			if (eManager.rowCount(query.toString()) >= Constants.NUMERO_MAX_REG_XLSX) {
				this.verificarTamano = true;
				return true;
			}
		} catch (Exception e) {
			log.error("Error al generar el archivo de Excel", e);
		}
		
		this.verificarTamano = false;
		return false;
	}
	
	
	/**
	 * Recoje la informacion necesaria para hacer la consulta dinamica.
	 * @return
	 */
	public String exportar(){
		ResultSet resultSet = null;
		FileInputStream fis = null;
		FileOutputStream fos = null;

		ExcelUtilities exelUtils = new ExcelUtilities();
		try {
			this.validaciones();
			entidadBean.setCampos(new ArrayList<Campo>());
			this.setCamposSeleccionados();
			
			//Se crea el archivo de excel inicial.
			String nombreArchivo = "Consulta_" + entidad1.getNombre() + "_" + entidad2.getNombre();
			String query = this.createQueryExportar(entidad1.getNombre(), entidad2.getNombre(), camposSeleccionadosE1, camposSeleccionadosE2, foranea, 0, Constants.TAMANO_PAG);
			
			if (Utilidades.nvl(query)) {
				CachedRowSetImpl registros = eManager.consultaDinamica(query);
				exelUtils.generarExcelDisco(registros, nombreArchivo);
			}
			
			//Se agregan los demas registros
			TMihParametro rutaSalida = paramManager.getParametroById("RUTA_SALIDA");
			String rutaArchivo = rutaSalida.getValor() + File.separatorChar + nombreArchivo + ".xlsx";
			
			//TEST_INICIO
			rutaArchivo = rutaArchivo.replace("/", "\\");
			//TEST_FIN
			
			fis = new FileInputStream(rutaArchivo);
			this.escribirPaginas(query, resultSet, fis, fos, nombreArchivo, rutaArchivo);
			
			//Se descarga el archivo generado
			exelUtils.descargarArchivo(nombreArchivo, rutaArchivo, true);
			
			CommandExecuter.resetSession();
			this.verificarTamano = false;
			
		} catch (Exception e) {
			this.verificarTamano = false;
			super.setMessage("consultas.dinamicas.no_resultados", BaseBean.WARNING, null, null);
			return null;
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
			} catch (SQLException e) {
				log.error("Error al generar el archivo de Excel", e);
			}
			try {
				if (fos != null) {
					fos.flush();
					fos.close();
				}
			} catch (Exception e) {
				log.error("Error al generar el archivo de Excel", e);
			}
			try {
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				log.error("Error al generar el archivo de Excel", e);
			}
		}
		
		return null;
	}
	
	/**
	 * Permite determinar en cuantos bloques se hara el proceso de exportar
	 * el archivo excel. 
	 * @return
	 */
	private int obtenerPaginas(int numeroMaximoRegistros, int tamanoPagina){
		int paginas = new Double(numeroMaximoRegistros / tamanoPagina).intValue();
		if ((numeroMaximoRegistros % tamanoPagina) > 0) {
			paginas++;
		}
		
		return paginas;
	}
	
	/**
	 * Permite escribir el resto de la consulta en bloques iguales.
	 * 
	 * @param query
	 * @param resultSet
	 * @param fis
	 * @param fos
	 * @param nombreArchivo
	 * @param rutaArchivo
	 */
	private void escribirPaginas(String query, ResultSet resultSet,FileInputStream fis, FileOutputStream fos, String nombreArchivo, String rutaArchivo){
		ExcelUtilities exelUtils = new ExcelUtilities();
		
		try {
			//Se abre archivo excel
			XSSFWorkbook wb_template = new XSSFWorkbook(fis);
			SXSSFWorkbook workbook = new SXSSFWorkbook(wb_template);
			
			//Se calculan las condiciones iniciales
			int inicio = Constants.TAMANO_PAG + 1;
			int paginas = this.obtenerPaginas(Constants.NUMERO_MAX_REG_XLSX, Constants.TAMANO_PAG);
			
			//Se escriben los bloques de informcion en el archivo de excel abierto.
			for (int i = 0; i < paginas - 1; i++) {
				int fin = Constants.TAMANO_PAG;
				query = this.createQueryExportar(entidad1.getNombre(), entidad2.getNombre(), camposSeleccionadosE1, camposSeleccionadosE2, foranea, inicio - 1, fin);
				
				//Se realiza la consulta
				if (Utilidades.nvl(query)) {
					ArrayList<Campo> camposConsolidado = this.setPrefijosCampos(camposSeleccionadosE1, camposSeleccionadosE2);
					if (Utilidades.nvl(camposConsolidado)) {
						CachedRowSetImpl registros = eManager.consultaDinamica(query);	//***
						resultSet = registros.getOriginal();
						if (this.tamanoResulset(resultSet) != 0) {
							workbook = exelUtils.llenarExcel3(registros, nombreArchivo, inicio, workbook, rutaArchivo);
						} else {
							break;
						}
					}
				}
				inicio += Constants.TAMANO_PAG;
				CommandExecuter.setSession();
			}
			
			// Se salva el libro.
            fos = new FileOutputStream(rutaArchivo);
            workbook.write(fos);
            
		} catch (Exception e) {
			log.error("Error escribiendo paginas a excel", e);
		} finally {
			try {
				fos.flush();
				fos.close();
			} catch (IOException e) {
				log.error("Error escribiendo paginas a excel", e);
			}
		}
	}
	
	public int tamanoResulset(ResultSet resultSet){
		int size = 0;
		try {
			resultSet.last();
			size = resultSet.getRow();
			resultSet.beforeFirst();
		} catch (SQLException e) {
			log.error("Error creando el query para exportar a excel", e);
		}
		return size;
	}
	
	/**
	 * Permite crear el query con el que se exportara a excel.
	 * 
	 * @param estructura1	Nombre de la primera estructura seleccionada
	 * @param estructura2	Nombre de la segunda estructura seleccionada
	 * @param campose1	Campos seleccionados de la primera estructura seleccionada
	 * @param campose2	Campos seleccionados de la segunda estructura seleccionada
	 * @param claveForanea	Nombre del campo de la segunda entidad que corresponderia a la clave 
	 * foranea de la primera clase. 
	 * @return
	 */
	public String createQueryExportar(String estructura1, String estructura2, List<Campo> campose1, List<Campo> campose2, String claveForanea, int inicio, int intervalo){
		String e1 = "E1";
		String e2 = "E2";
		
		String query = "";
		try {
			if ((estructura1 != null && estructura2 != null && campose1 != null && campose2 != null && claveForanea != null) 
					&& (!estructura1.isEmpty() && !estructura2.isEmpty() && !campose1.isEmpty() && !campose2.isEmpty() && !claveForanea.isEmpty() )) {
			
				if (Utilidades.isAlfaNumerico(estructura1) && Utilidades.isAlfaNumerico(estructura2)
						&& Utilidades.isAlfaNumerico(claveForanea)) {
					
					query = "SELECT * FROM (SELECT {1}, ROWNUM rnum FROM {2} JOIN {3} ON {4} = {5} WHERE ROWNUM <= {MAX} ) WHERE rnum  > {MIN}";
					
					//Clave primaria primera entidad
					String clavePrimariaE1 = "";
					for (int i = 0; i < campose1.size(); i++) {
						if (campose1.get(i).getClave().equals(Constants.FLAG_ES_CLAVE)) {
							clavePrimariaE1 = campose1.get(i).getNombreCorto();
						}
					}
					
					//Campos
					StringBuffer campos = new StringBuffer("");
					for (Campo campo : campose1) {
						campos.append(e1 + "." + campo.getNombreCorto() + " as T1_" + campo.getNombreCorto() +", ");
					}
					
					for (int i = 0; i < campose2.size(); i++) {
						if (i == campose2.size() - 1) {
							campos.append(e2 + "." + campose2.get(i) + " as T2_" + campose2.get(i));
							break;
						}
						campos.append(e2 + "." + campose2.get(i) + " as T2_" + campose2.get(i) + ", ");
					}
					query = query.replace("{1}", campos.toString());
					
					//Entidades
					query = query.replace("{2}", estructura1 + " " + e1);
					query = query.replace("{3}", estructura2 + " " + e2);
					
					//Condiciones
					query = query.replace("{4}", e1 + "." + clavePrimariaE1);
					query = query.replace("{5}", e2 + "." + claveForanea);
					
					//Limite superior
					query = query.replace("{MAX}", String.valueOf(inicio + intervalo));
					
					//Limite inferior
					query = query.replace("{MIN}", String.valueOf(inicio));
				}
			} else {
				throw new Exception();
			}
			
		} catch (Exception e) {
			super.setMessage("consultas.dinamicas.err_query_exportacion", BaseBean.ERROR, null, estructura1);
			log.error("Error creando el query para exportar a excel", e);
		}
		return query;
	}

	/**
	 * Permite crear el query con el que se exportara a excel.
	 * 
	 * @param estructura1	Nombre de la primera estructura seleccionada
	 * @param estructura2	Nombre de la segunda estructura seleccionada
	 * @param campose1	Campos seleccionados de la primera estructura seleccionada
	 * @param campose2	Campos seleccionados de la segunda estructura seleccionada
	 * @param claveForanea	Nombre del campo de la segunda entidad que corresponderia a la clave 
	 * foranea de la primera clase. 
	 * @return
	 */
	public String createQueryRowCount(String estructura1, String estructura2, List<Campo> campose1, String claveForanea){
		String e1 = "E1";
		String e2 = "E2";
		
		String query = "";
		try {
			if ((estructura1 != null && estructura2 != null && campose1 != null && claveForanea != null) 
					&& (!estructura1.isEmpty() && !estructura2.isEmpty() && !campose1.isEmpty() && !claveForanea.isEmpty() )) {
				
				if (Utilidades.isAlfaNumerico(estructura1) && Utilidades.isAlfaNumerico(estructura2)
						&& Utilidades.isAlfaNumerico(claveForanea)) {
					
					query = "SELECT count(*) FROM {2} JOIN {3} ON {4} = {5}";
					
					//Clave primaria primera entidad
					String clavePrimariaE1 = "";
					for (int i = 0; i < campose1.size(); i++) {
						if (campose1.get(i).getClave().equals(Constants.FLAG_ES_CLAVE)) {
							clavePrimariaE1 = campose1.get(i).getNombreCorto();
						}
					}
					
					//Entidades
					query = query.replace("{2}", estructura1 + " " + e1);
					query = query.replace("{3}", estructura2 + " " + e2);
					
					//Condiciones
					query = query.replace("{4}", e1 + "." + clavePrimariaE1);
					query = query.replace("{5}", e2 + "." + claveForanea);
				}
			} else {
				throw new Exception();
			}
			
		} catch (Exception e) {
			super.setMessage("consultas.dinamicas.err_query_exportacion", BaseBean.ERROR, null, estructura1);
			log.error("Error creando el query para exportar a excel", e);
		}
		return query;
	}
	
	
	/**
	 * Permite validar que exitan las condiciones adecuadas para realizar la consulta.
	 */
	public void validaciones(){
		//Verifica que se halla seleccionado la clave primaria.
		boolean clavePrimaria = false;
		if (camposSeleccionadosE1 != null && !camposSeleccionadosE1.isEmpty()) {
			for (int i = 0; i < camposSeleccionadosE1.size(); i++) {
				if (camposSeleccionadosE1.get(i).getClave().equals(Constants.FLAG_ES_CLAVE)) {
					clavePrimaria = true;
				}
			}
		}
		
		//Verifica clave primaria de tabla uno.
		if (!clavePrimaria) {
			this.setMessage("consultas.dinamicas.no_clave_primaria", BaseBean.ERROR, null, entidad1.getNombre());
		}
		
		//Verifica campos tabla uno.
		if (camposSeleccionadosE1.isEmpty()) {
			super.setMessage("consultas.dinamicas.slct_campostabla", BaseBean.ERROR, null, entidad1.getNombre());
		}

		//Verifica campos tabla dos.
		if (camposSeleccionadosE2.isEmpty()) {
			super.setMessage("consultas.dinamicas.slct_campostabla", BaseBean.ERROR, null, entidad2.getNombre());
		}
		
		//Verifica clave foranea.
		if (foranea.isEmpty() && !camposSeleccionadosE1.isEmpty() && !camposSeleccionadosE2.isEmpty()) {
			super.setMessage("consultas.dinamicas.no_clave_foranea", BaseBean.ERROR, null, entidad2.getNombre());
		}
		
	}
	
	public String validarGuardar(){
		
		//Verifica que se halla seleccionado la clave primaria.
		boolean clavePrimaria = false;
		if (camposSeleccionadosE1 != null && !camposSeleccionadosE1.isEmpty()) {
			for (int i = 0; i < camposSeleccionadosE1.size(); i++) {
				if (camposSeleccionadosE1.get(i).getClave().equals(Constants.FLAG_ES_CLAVE)) {
					clavePrimaria = true;
				}
			}
		}
		
		boolean mostrar = true;
		
		//Verifica clave primaria de tabla uno.
		if (!clavePrimaria) {
			this.setMessage("consultas.dinamicas.no_clave_primaria", BaseBean.ERROR, null, entidad1.getNombre());
			mostrar = false;
		}
		
		//Verifica campos tabla uno.
		if (camposSeleccionadosE1.isEmpty()) {
			super.setMessage("consultas.dinamicas.slct_campostabla", BaseBean.ERROR, null, entidad1.getNombre());
			mostrar = false;
		}

		//Verifica campos tabla dos.
		if (camposSeleccionadosE2.isEmpty()) {
			super.setMessage("consultas.dinamicas.slct_campostabla", BaseBean.ERROR, null, entidad2.getNombre());
			mostrar = false;
		}
		
		//Verifica clave foranea.
		if (foranea.isEmpty() && !camposSeleccionadosE1.isEmpty() && !camposSeleccionadosE2.isEmpty()) {
			super.setMessage("consultas.dinamicas.no_clave_foranea", BaseBean.ERROR, null, entidad2.getNombre());
			mostrar = false;
		}
		
		this.setMostrarGuardar(mostrar);
		
		return "";
	}
	
	public String ocultarGuardarConsulta(){
		this.setMostrarGuardar(false);
		return "";
	}
	
	/**
     * Anade prefijos a los nombres de los campos de las tablas especificadas.
     * @param camposTabla1
     * @param camposTabla2
     * @return
     */
    public ArrayList<Campo> setPrefijosCampos(List<Campo> camposTabla1, List<Campo> camposTabla2){
    	ArrayList<Campo> camposConsolidado = new ArrayList<Campo>();
    	
    	List<Campo> camposTabla1Aux = new ArrayList<Campo>();
		List<Campo> camposTabla2Aux = new ArrayList<Campo>();
		Campo campo;
		
		//Se anaden alias a los campos consultados.
		if (camposTabla1 != null && !camposTabla1.isEmpty()) {
			for (int i = 0; i < camposTabla1.size(); i++) {
				campo = new Campo("T1_" + camposTabla1.get(i).getNombreCorto(), camposTabla1.get(i).getNombreCompleto(), camposTabla1.get(i).getDescripcion(), 
						camposTabla1.get(i).getTipoDatoCompleto(), camposTabla1.get(i).getTipoDato(), camposTabla1.get(i).getLongitud(), camposTabla1.get(i).getPrecision(), 
						camposTabla1.get(i).getClave(), camposTabla1.get(i).getClaveForanea());
				camposTabla1Aux.add(campo);
			}
		}
		if (camposTabla2 != null && !camposTabla2.isEmpty()) {
			for (int i = 0; i < camposTabla2.size(); i++) {
				campo = new Campo("T2_" + camposTabla2.get(i).getNombreCorto(), camposTabla2.get(i).getNombreCompleto(), camposTabla2.get(i).getDescripcion(), 
						camposTabla2.get(i).getTipoDatoCompleto(), camposTabla2.get(i).getTipoDato(), camposTabla2.get(i).getLongitud(), camposTabla2.get(i).getPrecision(), 
						camposTabla2.get(i).getClave(), camposTabla2.get(i).getClaveForanea());
				camposTabla2Aux.add(campo);
			}
		}
		
		camposConsolidado.addAll(camposTabla1Aux);
		camposConsolidado.addAll(camposTabla2Aux);
		
    	return camposConsolidado;
    }
	
	/**
	 * Obtiene los campos seleccionados de cada tabla.
	 */
	public void setCamposSeleccionados(){
		if ((camposSeleccionadosE1 != null) && (camposSeleccionadosE2 != null)){
			List<Campo> camposSE1 = new ArrayList<Campo>(camposSeleccionadosE1);
			camposSeleccionadosE1.clear();
			for (Campo campo : camposSE1) {
				for (Campo campoE1 : camposE1) {
					if (campo.getNombreCorto().equals(campoE1.getNombreCorto())) {
						camposSeleccionadosE1.add(campoE1);
					}
				}
			}
			
			List<Campo> camposSE2 =  new ArrayList<Campo>(camposSeleccionadosE2);
			camposSeleccionadosE2.clear();
			for (Campo campo : camposSE2) {
				for (Campo campoE2 : camposE2) {
					if (campo.getNombreCorto().equals(campoE2.getNombreCorto())) {
						camposSeleccionadosE2.add(campoE2);
					}
				}
			}
		}
	}
	
	/**
	 * Limpia la informacion del bean.
	 * @return
	 */
	public String limpiarCampos(){
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("consulta_dinamica.xhtml");
		} catch (IOException e) {
			log.error("Error cargando la pagina", e);
		}
		return null;
	}
	
	/**
	 * Obtiene los campos que tiene la primera entidad seleccionada
	 * @return
	 */
	public List<Campo> obtenerCampose1() {
		return this.eManager.getCampos(entidad1);
	}

	/**
	 * Obtiene los campos que tiene la segunda entidad seleccionada
	 * @return
	 */
	public List<Campo> obtenerCampose2() {
		return this.eManager.getCampos(entidad2);
	}
	
	/**
	 * Activa los botones de control
	 * @return
	 */
	public String activaBotones() {
		this.buttonRender = true;
		return null;
	}
	
	/**
	 * Obtiene el nombre de las entidades que se van a mostrar en la lista
	 * de entidades a seleccionar en la vista de consulta dinamica.
	 * @return
	 */
	public ArrayList<SelectItem> obtenerNombreEntidades(){
		ArrayList<SelectItem> items = new ArrayList<SelectItem>();
		
		if (entidades != null) {
			for(TMihEntidad entidad : entidades){
				items.add(new SelectItem(entidad.getNombre()));
			}
		}
		return items;
	}

	/**
	 * Cambia el estado de verificarTamano para que no se vuelva
	 * a verificar 
	 */
	public void ocultarConConfirmacion(){
		this.verificarTamano = false;
	}

	
	
	
	
		
	
	
	
	
	
	// GETTERS & SETTERS.
	public BeanFactory getFactory() {
		return factory;
	}

	public void setFactory(BeanFactory factory) {
		this.factory = factory;
	}

	public List<TMihEntidad> getEntidades() {
		return entidades;
	}

	public void setEntidades(List<TMihEntidad> entidades) {
		this.entidades = entidades;
	}

	public TMihEntidad getEntidad1() {
		return entidad1;
	}

	public void setEntidad1(TMihEntidad entidad1) {
		this.entidad1 = entidad1;
	}

	public TMihEntidad getEntidad2() {
		return entidad2;
	}

	public void setEntidad2(TMihEntidad entidad2) {
		this.entidad2 = entidad2;
	}

	public EntidadManager geteManager() {
		return eManager;
	}

	public void seteManager(EntidadManager eManager) {
		this.eManager = eManager;
	}

	public List<Campo> getCamposSeleccionadosE1() {
		return camposSeleccionadosE1;
	}

	public void setCamposSeleccionadosE1(List<Campo> camposSeleccionadosE1) {
		this.camposSeleccionadosE1 = camposSeleccionadosE1;
	}

	public List<Campo> getCamposSeleccionadosE2() {
		return camposSeleccionadosE2;
	}

	public void setCamposSeleccionadosE2(List<Campo> camposSeleccionadosE2) {
		this.camposSeleccionadosE2 = camposSeleccionadosE2;
	}

	public List<Campo> getCamposE1() {
		camposE1 = this.obtenerCampose1();
			return camposE1;
	}
	
	public void setCamposE1(List<Campo> camposE1) {
		this.camposE1 = camposE1;
	}
	
	public List<Campo> getCamposE2() {
		camposE2 = this.obtenerCampose2();
		return camposE2;
	}

	public void setCamposE2(List<Campo> camposE2) {
		this.camposE2 = camposE2;
	}

	public ArrayList<SelectItem> getNombreEntidades() {
		nombreEntidades = this.obtenerNombreEntidades();
		return nombreEntidades;
	}

	public void setNombreEntidades(ArrayList<SelectItem> nomrbeEntidades) {
		this.nombreEntidades = nomrbeEntidades;
	}

	public String getForanea() {
		return foranea;
	}

	public void setForanea(String foranea) {
		this.foranea = foranea;
	}

	public ArrayList<LinkedHashMap<String, String>> getTablaDinamica() {
		return tablaDinamica;
	}

	public void setTablaDinamica(ArrayList<LinkedHashMap<String, String>> tablaDinamica) {
		this.tablaDinamica = tablaDinamica;
	}

	public boolean getGetConsulta() {
		return getConsulta;
	}

	public void setGetConsulta(boolean getConsulta) {
		this.getConsulta = getConsulta;
	}

	public ConsultaManager getcManager() {
		return cManager;
	}

	public void setcManager(ConsultaManager cManager) {
		this.cManager = cManager;
	}

	public List<TMihConsulta> getConsultas() {
		return consultas;
	}

	public void setConsultas(List<TMihConsulta> consultas) {
		this.consultas = consultas;
	}

	public int getConsultadoId() {
		return consultadoId;
	}

	public void setConsultadoId(int consultadoId) {
		this.consultadoId = consultadoId;
	}

	public TMihConsulta getConsultado() {
		return consultado;
	}

	public void setConsultado(TMihConsulta consultado) {
		this.consultado = consultado;
	}

	public List<TMihConstbl> getConEntidad() {
		return conEntidad;
	}

	public void setConEntidad(List<TMihConstbl> conEntidad) {
		this.conEntidad = conEntidad;
	}
	
	public String getConstaDesc() {
		return constaDesc;
	}

	public void setConstaDesc(String constaDesc) {
		this.constaDesc = constaDesc;
	}

	public List<TMihConsfld> getConCampo1() {
		return conCampo1;
	}

	public void setConCampo1(List<TMihConsfld> conCampo1) {
		this.conCampo1 = conCampo1;
	}

	public List<TMihConsfld> getConCampo2() {
		return conCampo2;
	}

	public void setConCampo2(List<TMihConsfld> conCampo2) {
		this.conCampo2 = conCampo2;
	}

	public boolean isButtonRender() {
		return buttonRender;
	}

	public void setButtonRender(boolean buttonRender) {
		this.buttonRender = buttonRender;
	}

	public EntidadBean getEntidadBean() {
		return entidadBean;
	}

	public void setEntidadBean(EntidadBean entidadBean) {
		this.entidadBean = entidadBean;
	}

	public ConsultaDinamicaLazy getConsultaLazy() {
		return consultaLazy;
	}

	public void setConsultaLazy(ConsultaDinamicaLazy consultaLazy) {
		this.consultaLazy = consultaLazy;
	}

	public LazyDataModel<LinkedHashMap<String, String>> getLazyModel() {
		return lazyModel;
	}

	public void setLazyModel(LazyDataModel<LinkedHashMap<String, String>> lazyModel) {
		this.lazyModel = lazyModel;
	}

	public ArrayList<String> getColumnas() {
		return columnas;
	}

	public void setColumnas(ArrayList<String> columnas) {
		this.columnas = columnas;
	}

	public boolean isVerificarTamano() {
		return verificarTamano;
	}

	public void setVerificarTamano(boolean verificarTamano) {
		this.verificarTamano = verificarTamano;
	}

	public boolean isMostrarGuardar() {
		return mostrarGuardar;
	}

	public void setMostrarGuardar(boolean mostrarGuardar) {
		this.mostrarGuardar = mostrarGuardar;
	}

	public DualListModel<Campo> getCampos() {
		return campos;
	}

	public void setCampos(DualListModel<Campo> campos) {
		this.campos = campos;
	}
		
	public DualListModel<Campo> getCampos2() {
		return campos2;
	}

	public void setCampos2(DualListModel<Campo> campos2) {
		this.campos2 = campos2;
	}

	public void llenarLista1(){
		camposE1= this.eManager.getCampos(entidad1);
//		campos = new DualListModel<Campo>(camposE1, camposSeleccionadosE1);
		campos.setSource(camposE1);
		campos.setTarget(camposSeleccionadosE1);
	}

	public void llenarLista2(){
		camposE2= this.eManager.getCampos(entidad2);
//		campos2 = new DualListModel<Campo>(camposE2, camposSeleccionadosE2);
		campos2.setSource(camposE2);
		campos2.setTarget(camposSeleccionadosE2);
	}
	
	public void onTransfer1() {	
		camposSeleccionadosE1.clear();
		camposSeleccionadosE1.addAll(campos.getTarget());	
		
	}
	
	public void onTransfer2() {	
		camposSeleccionadosE2.clear();
		camposSeleccionadosE2.addAll(campos2.getTarget());	
		
	}
		
	
}

