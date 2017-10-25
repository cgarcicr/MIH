package com.sophos.semih.bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.jsf.FacesContextUtils;

import com.sophos.semih.model.TMihEntidad;
import com.sophos.semih.model.TMihParametro;
import com.sophos.semih.service.EntidadManager;
import com.sophos.semih.util.CommandExecuter;
import com.sophos.semih.util.ExcelUtilities;
import com.sun.rowset.CachedRowSetImpl;
import com.sophos.semih.common.Constants;
import com.sophos.semih.service.ParametroManager;

@ManagedBean
@ViewScoped
public class ConsultaMasivaBean extends BaseBean {

	private static final long serialVersionUID = -7530962214269147940L;

	private List<TMihEntidad> entidades;
	private Integer codEntidad;
	private Date fechaInicio;
	private Date fechaFin;
	private String tipoSalida;
	private boolean sinDatos = false;
	private boolean conConfirmacion = false;
	private EntidadManager entManager;
	private ParametroManager paramManager;
	private static final Log log = LogFactory.getLog(ConsultaMasivaBean.class);
	private BeanFactory factory = FacesContextUtils
			.getWebApplicationContext(FacesContext.getCurrentInstance());

	public ConsultaMasivaBean() {

		setEntManager((EntidadManager) factory.getBean("entidadManager"));
		this.setEntidades(entManager.getEntidadesxUsuario(0, 1)); // 0 - No se va a autorizar y 1 - S�lo cargue entidades con fecha
		this.paramManager = (ParametroManager) factory.getBean("parametroManager");

	}

	public String consultar() {
		try {

			TMihEntidad entidad = entManager.getEntidadById(this.getCodEntidad());
			String nombreArchivo = "";
			String rutaArchivo = "";
			
			// Se formatean las fechas para pasarlas a la consulta
			SimpleDateFormat formateador = new SimpleDateFormat(Constants.FORMATO_FECHA);
			String fecha1 = "to_date('"+ formateador.format(this.getFechaInicio()) + "', '"
					+ Constants.FORMATO_FECHA + "')";
			String fecha2 = "to_date('"+ formateador.format(this.getFechaFin()) + "', '"
					+ Constants.FORMATO_FECHA + "')";
			
			if(this.isMayorNumReg(entidad, fecha1, fecha2) && !this.isConConfirmacion()){
				
				this.setConConfirmacion(true);
				
			}else{
				
				CommandExecuter.setSession();
				
				//Se crea el archivo de excel inicial.
				nombreArchivo = "Consulta_" + entidad.getNombre();
				String query = this.createQuery(entidad, fecha1, fecha2, 0, Constants.TAMANO_PAG);
				if (query != null && !query.isEmpty()) {
					CachedRowSetImpl registros = entManager.consultaDinamica(query);
					this.setSinDatos((new ExcelUtilities()).generarExcelDisco(registros, nombreArchivo));
				}
				
				if(!this.isSinDatos()){
					
					//Se agregan los demas registros
					FileInputStream fileInputStream = null;
					FileOutputStream elFichero = null;
					
					TMihParametro rutaSalida = paramManager.getParametroById("RUTA_SALIDA");
					rutaArchivo = rutaSalida.getValor() + File.separatorChar + nombreArchivo + ".xlsx";
								
					fileInputStream = new FileInputStream(rutaArchivo);
					XSSFWorkbook wb_template = new XSSFWorkbook(fileInputStream);
					
					SXSSFWorkbook workbook = new SXSSFWorkbook(wb_template);
					
					int paginas = new Double(Constants.NUMERO_MAX_REG_XLSX / Constants.TAMANO_PAG).intValue();
					if ((Constants.NUMERO_MAX_REG_XLSX % Constants.TAMANO_PAG) > 0) {
						paginas++;
					}
					int inicio = Constants.TAMANO_PAG + 1;
					
					for (int i = 0; i < paginas - 1; i++) {
						int fin = Constants.TAMANO_PAG;
						query = this.createQuery(entidad, fecha1, fecha2, inicio - 1, fin);
						
						if (query != null && !query.isEmpty()) {
							CachedRowSetImpl registros = entManager.consultaDinamica(query);
							ResultSet resultSet = registros.getOriginal();
							resultSet.last();
							int size = resultSet.getRow();
							resultSet.beforeFirst();
							resultSet.close();
							if (size != 0) {
								workbook = (new ExcelUtilities()).llenarExcel3(registros, nombreArchivo, inicio, workbook, rutaArchivo);
							} else {
								break;
							}
						}
						inicio += Constants.TAMANO_PAG;
					}
					
					// Se salva el libro.
		            elFichero = new FileOutputStream(rutaArchivo);
		            workbook.write(elFichero);
					(new ExcelUtilities()).descargarArchivo(nombreArchivo, rutaArchivo, true);
					
				}
							
				CommandExecuter.resetSession();
				this.setConConfirmacion(false);
			}
			
		} catch (Exception e) {
			log.error("Error al ejecutar la consulta masiva", e);
			this.setConConfirmacion(false);
		}

		return null;
	}
	
	/**
	 * Permite crear el query con el que se exportara a excel.
	 * 
	 * @param tabla	Nombre de la estructura que será exportada
	 * @param entidad Tabla sobre la cual se ejecutará la consulta
	 * @param inicio Registro en el que se encuentra la consulta
	 * @param intervalo Número de registros a consultar
	 * @return
	 */
	public String createQuery(TMihEntidad tabla, String fechaInicio, String fechaFin, int inicio, int intervalo){
		//Se construye la consulta
		String query = "SELECT * "
						+ "FROM (SELECT {1}.*, ROWNUM rnum "
								+ "FROM {1} "
								+ "WHERE {2} >= {3} "
								+ "AND {2} <= {4} "
								+ "AND ROWNUM <= {MAX} )"
						+ " WHERE rnum  > {MIN}";

		//Nombre de la tabla
		query = query.replace("{1}", tabla.getNombre());
		
		//Campo Fecha
		query = query.replace("{2}", tabla.getCampoFecha());
		
		//Fecha Inicio
		query = query.replace("{3}", fechaInicio);
		
		//Fecha Fin
		query = query.replace("{4}", fechaFin);
		
		//Limite superior
		query = query.replace("{MAX}", String.valueOf(inicio + intervalo));
		
		//Limite inferior
		query = query.replace("{MIN}", String.valueOf(inicio));
		
		return query;
	}
	
	public boolean isMayorNumReg(TMihEntidad tabla, String fechaInicio, String fechaFin){
		
		CachedRowSetImpl registros = null;
		ResultSet resultSet;
		String query;
		
		try {			
			query = "SELECT COUNT(1) "
						+ " FROM {1} "
						+ "	WHERE {2} >= {3} "
						+ "	AND {2} <= {4} ";
			
			//Nombre de la tabla
			query = query.replace("{1}", tabla.getNombre());
			
			//Campo Fecha
			query = query.replace("{2}", tabla.getCampoFecha());
			
			//Fecha Inicio
			query = query.replace("{3}", fechaInicio);
			
			//Fecha Fin
			query = query.replace("{4}", fechaFin);
			
			//Se ejecuta la consulta
			registros = entManager.consultaDinamica(query);
			resultSet = registros.getOriginal();
			resultSet.first();
			Long numRegistros = resultSet.getLong(1);
			
			if(numRegistros >= Constants.NUMERO_MAX_REG_XLSX)
				return true;

		} catch (Exception e) {
			log.error("Error al obtener el número de registros de la consulta", e);
		}
		
		return false;
	}
		
	public String limpiar() {
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("consulta_masiva.xhtml");
		} catch (IOException e) {
			log.error("Error al ejecutar la consulta masiva", e);
		}

		return null;
	}
	
	public void ocultarConConfirmacion(){
		this.conConfirmacion = false;
	}
	
	// GETTERS & SETTERS
	public List<TMihEntidad> getEntidades() {
		return entidades;
	}

	public void setEntidades(List<TMihEntidad> entidades) {
		this.entidades = entidades;
	}

	public Integer getCodEntidad() {
		return codEntidad;
	}

	public void setCodEntidad(Integer codEntidad) {
		this.codEntidad = codEntidad;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getTipoSalida() {
		return tipoSalida;
	}

	public void setTipoSalida(String tipoSalida) {
		this.tipoSalida = tipoSalida;
	}

	public EntidadManager getEntManager() {
		return entManager;
	}

	public void setEntManager(EntidadManager entManager) {
		this.entManager = entManager;
	}

	public boolean isSinDatos() {
		return sinDatos;
	}

	public void setSinDatos(boolean sinDatos) {
		this.sinDatos = sinDatos;
	}

	public ParametroManager getParamManager() {
		return paramManager;
	}

	public void setParamManager(ParametroManager paramManager) {
		this.paramManager = paramManager;
	}

	public boolean isConConfirmacion() {
		return conConfirmacion;
	}

	public void setConConfirmacion(boolean conConfirmacion) {
		this.conConfirmacion = conConfirmacion;
	}
}
