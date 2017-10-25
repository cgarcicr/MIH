package com.sophos.semih.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.jsf.FacesContextUtils;

import com.sophos.semih.common.Constants;
import com.sophos.semih.model.TMihControlcarga;
import com.sophos.semih.model.TMihParametro;
import com.sophos.semih.service.CargaManager;
import com.sophos.semih.service.EntidadManager;
import com.sophos.semih.service.ParametroManager;

@ManagedBean
@ViewScoped
public class ReporteCargaLazy extends LazyDataModel<HashMap<TMihControlcarga, BigDecimal>>{
	
	private static final long serialVersionUID = -3654545675897622580L;
	
	private List<TMihControlcarga> cargasxUsuario; 
	private List<String> campos;
	private EntidadManager entManager;
	private ParametroManager paramManager;
	private CargaManager managerCarga;
	private static final Log log = LogFactory.getLog(ReporteCargaLazy.class);
	private BeanFactory factory = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
	private LinkedHashMap<String, String> filtro;
	private String entidad = Constants.ENTIDAD_CARGA;
	
	public ReporteCargaLazy() throws Exception{
		
		// Se obtiene la ruta de los archivos a cargar
		setParamManager((ParametroManager) factory.getBean("parametroManager"));
	
		// Se obtiene todas las cargas
		setManagerCarga((CargaManager) factory.getBean("cargaManager"));
		setCargasxUsuario(managerCarga.getControlcargasxUsuario());

		//HashMap de filtros
		filtro = new LinkedHashMap<String, String>();
	}
	
    @Override  
    public List<HashMap<TMihControlcarga, BigDecimal>> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {  
        List<HashMap<TMihControlcarga, BigDecimal>> data = new ArrayList<HashMap<TMihControlcarga, BigDecimal>>();  
   
        try{
        	data = this.consultar(String.valueOf(first + pageSize), String.valueOf(first), filters);
        	
	        return data;  
        }catch (Exception e){
        	
        }
        return null;
    }  
	
    /**
     * Permite hacer la cunsulta de datos de la entidad en los rangos especificados por la paginaion
     * @param max	Limite maximo de filas a consultar
     * @param min	Limite minimo de filas a consultar
     * @return
     */
    public List<HashMap<TMihControlcarga, BigDecimal>> consultar(String max, String min, Map<String,Object> filters){
    	List<HashMap<TMihControlcarga, BigDecimal>> resultado = new ArrayList<HashMap<TMihControlcarga, BigDecimal>>();  
    	
		try {
			
			campos = (List<String>)this.obtenerCamposEntidad();
			String query = this.createQueryCarga(this.entidad, campos, max, min, filters);
			
			if (query != null && !query.isEmpty()) {
				//Se obtiene el usuario
				
				TMihParametro oracle_user = paramManager.getParametroById("ORACLE_USER");

				for (int i = 0; i < getCargasxUsuario().size(); i++) {
					HashMap<TMihControlcarga, BigDecimal> mapaCarga = new HashMap<TMihControlcarga, BigDecimal>();
					mapaCarga.put(getCargasxUsuario().get(i), managerCarga.consultarCarga(getCargasxUsuario().get(i).getTMihEntidad().getNombre(), oracle_user.getValor()));
					resultado.add(mapaCarga);
				}
				
				if (resultado == null || resultado.isEmpty()) {
					return null;
				}
				
				this.setRowCount(this.getTamanoResultado(this.entidad, campos, filters));
				
				//Obtiene los nombres de las columnas
				for (String string : campos) {
					filtro.put(string, "");
				}	
			}
			
		} catch (Exception e) {
			log.error("Error al paginar", e);
		}
		return resultado;
	}
    


	/**
    * Permite crear el query con el que se realizara la busqueda dinamica.
    * 
    * @param entidad		Nombre de la primera estructura seleccionada
    * @param camposTabla	Campos de la tabla foranea de la primera clase.
    * @param max			Limite maximo de filas a consultar
    * @param min			Limite minimo de filas a consultar
    * @return	Query que permite consultar el rango de filas especificado por los limites inferior y superior.
    */
	public String createQueryCarga(String entidad, List<String> camposTabla, String max, String min, Map<String,Object> filters){
		String query = "";
		
		try {
			//Campos
			if ((entidad != null && camposTabla != null && max != null && min != null) 
					&& (!entidad.isEmpty() && !camposTabla.isEmpty() && !max.isEmpty() && !min.isEmpty())) {
				
				query = "select * from ( select {1} ROWNUM rnum from {2} where {3} ROWNUM <= {MAX} ) where rnum  > {MIN}";
				
				StringBuffer campos = new StringBuffer("");
				for (int i = 0; i < camposTabla.size(); i++) {		
					if (camposTabla.get(i).equals(Constants.DATE))	{
						campos.append(" NVL ( TO_CHAR ( " + entidad + "." + camposTabla.get(i) + ", '" + Constants.FORMATO_FECHA + "' ), ' ') AS \"" + camposTabla.get(i) + "\", ");
					} else if (camposTabla.get(i).equals(Constants.TIMESTAMP)){ 
						campos.append(" NVL ( TO_CHAR ( " +  entidad + "." + camposTabla.get(i) + ", '" + Constants.FORMATO_TIMESTAMP + "' ), ' ') AS \"" + camposTabla.get(i) + "\", ");
					} else {
						campos.append(entidad + "." + camposTabla.get(i) + ", ");
					}
				}
				//campos += entidad + "." + camposTabla.get(camposTabla.size() - 1);
				query = query.replace("{1}", campos.toString());
				
				//Entidad
				query = query.replace("{2}", entidad + " " + entidad);
				
				//Limite superior
				query = query.replace("{MAX}", max);
				
				//Limite inferior
				query = query.replace("{MIN}", min);
				
				/**
				 * TODO:
				 * Si hay filtro se debe consultar primero y luego paginar 
				 */
				
				//Filtro
				StringBuffer filtro = new StringBuffer("");
				if (filters != null && !filters.isEmpty()) {
					for (String campo : filters.keySet()) {
						for (String campoTabla : camposTabla) {
							if (campoTabla.equals(campo)) {
								if ((campoTabla.equals(Constants.CODIGO_ENTIDAD)) || (campoTabla.equals(Constants.CODIGO_USUARIO))) {
									filtro.append(campo + " LIKE " + filters.get(campo) + " AND ");
								} else if ( campoTabla.equals(Constants.FECHA_CARGA)) {
									filtro.append(" NVL ( TO_CHAR ( " + campo + ", '" + Constants.FORMATO_FECHA + "' ), ' ') LIKE '%" + filters.get(campo) + "%'" + " AND ");
								} else {
									filtro.append(campo + " LIKE '%" + filters.get(campo) + "%'" + " AND ");
								}
							}
						}
					}
					query = query.replace("{3}", filtro.toString());
				}
			}
			
			if (query.contains("{3}")) {
				query = query.replace("{3}", "");
			}
			
			
			
		} catch (Exception e) {
			log.error("Error creando el query para paginar", e);
		}
		
		return query;
	}
	
	
	private int getTamanoResultado(String carga, List<String> camposTabla, Map<String,Object> filters){
		
		try{
			if ((camposTabla != null) && (!camposTabla.isEmpty())) {
				
				String query = "select count(*) as count from " +  carga;
				if (filters != null && !filters.isEmpty()) {
					String filtro = " where ";
					for (String campo : filters.keySet()) {
						for (String campoTabla : camposTabla) {
							if (campoTabla.equals(campo)) {
								if ((campoTabla.equals(Constants.CODIGO_ENTIDAD)) || (campoTabla.equals(Constants.CODIGO_USUARIO))) {
									filtro = filtro + campo + " LIKE " + filters.get(campo) + " AND ";
								} else if ( campoTabla.equals(Constants.FECHA_CARGA)) {
									filtro = filtro + " NVL ( TO_CHAR ( " + campo + ", '" + Constants.FORMATO_FECHA + "' ), ' ') LIKE '%" + filters.get(campo) + "%'" + " AND ";
								} else {
									filtro = filtro + campo + " LIKE '%" + filters.get(campo) + "%'" + " AND ";
								}
							}
						}
					}
					
					if (filtro.endsWith(" AND ")) {
						filtro = filtro.substring(0, filtro.lastIndexOf(" AND "));
					}
					query = query + filtro;
				}
				
				return managerCarga.rowCount(query);  
			}
			
		} catch(Exception e){
			log.error("Error al consultar", e);
		}
		return 1;
	}
    
    
    private List<String> obtenerCamposEntidad() {
    	List<String> listaCampos = new ArrayList<String>();	
    		
    	listaCampos.add(Constants.CODIGO_CARGA);
    	listaCampos.add(Constants.CODIGO_ENTIDAD);
    	listaCampos.add(Constants.CODIGO_USUARIO);
    	listaCampos.add(Constants.FECHA_CARGA);
    	
		return listaCampos;
	}
    
	public List<TMihControlcarga> getCargasxUsuario() {
		return cargasxUsuario;
	}

	public void setCargasxUsuario(List<TMihControlcarga> cargasxUsuario) {
		this.cargasxUsuario = cargasxUsuario;
	}

	public CargaManager getManagerCarga() {
		return managerCarga;
	}

	public void setManagerCarga(CargaManager managerCarga) {
		this.managerCarga = managerCarga;
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






}