package com.sophos.semih.bean;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.jsf.FacesContextUtils;

import com.sophos.semih.common.Constants;
import com.sophos.semih.model.Campo;
import com.sophos.semih.model.TMihEntidad;
import com.sophos.semih.service.EntidadManager;

public class ConsultaEspecificaLazy extends LazyDataModel<LinkedHashMap<String, String>> {
	

	private static final long serialVersionUID = 7151622417875284751L;
	 
	private EntidadManager eManager;
	private BeanFactory factory;
		
	private List<TMihEntidad> entidades;
	private TMihEntidad oentidad;
	private ArrayList<Campo> camposoEntidad;
	
	private LinkedHashMap<String, String> filtro;
	
	private static final Log log = LogFactory.getLog(ConsultaEspecificaLazy.class);
	
	
    public ConsultaEspecificaLazy(TMihEntidad oentidad) {  
        factory = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
		eManager = (EntidadManager) factory.getBean(Constants.MANAGER_ENTIDAD);
		setEntidades(eManager.getEntidadesxUsuario());
		this.oentidad = new TMihEntidad();
		filtro = new LinkedHashMap<String, String>();
		this.oentidad = oentidad;
        
    }  
      
    @Override  
    public List<LinkedHashMap<String, String>> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {  
        List<LinkedHashMap<String, String>> data = new ArrayList<LinkedHashMap<String, String>>();  
  
        
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
    public List<LinkedHashMap<String, String>> consultar(String max, String min, Map<String,Object> filters){
		ArrayList<LinkedHashMap<String, String>> resultado = new ArrayList<LinkedHashMap<String, String>>(); 
		
		try {
			camposoEntidad = (ArrayList<Campo>)this.obtenerCamposEntidad();
			String query = this.createQueryEntidad(oentidad.getNombre(), camposoEntidad, max, min, filters);
			
			if (query != null && !query.isEmpty()) {
				resultado = eManager.consultaDinamica(query, camposoEntidad);
				
				if (resultado == null || resultado.isEmpty()) {
					return null;
				}
				
				this.setRowCount(this.getTamanoResultado(oentidad.getNombre(), camposoEntidad, filters));
				
				//Obtiene los nombres de las columnas
				Set<String> columnNames = resultado.get(0).keySet();
				for (String string : columnNames) {
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
	public String createQueryEntidad(String entidad, List<Campo> camposTabla, String max, String min, Map<String,Object> filters){
		String query = "";
		
		try {
			//Campos
			if ((entidad != null && camposTabla != null && max != null && min != null) 
					&& (!entidad.isEmpty() && !camposTabla.isEmpty() && !max.isEmpty() && !min.isEmpty())) {
				
				query = "select * from ( select {1} ROWNUM rnum from {2} where {3} ROWNUM <= {MAX} ) where rnum  > {MIN}";
				
				StringBuffer campos = new StringBuffer("");
				for (int i = 0; i < camposTabla.size(); i++) {		
					if (camposTabla.get(i).getTipoDato().equals(Constants.DATE))	{
						campos.append(" NVL ( TO_CHAR ( " + entidad + "." + camposTabla.get(i).getNombreCorto() + ", '" + Constants.FORMATO_FECHA + "' ), ' ') AS \"" + camposTabla.get(i).getNombreCorto() + "\", ");
					} else if (camposTabla.get(i).getTipoDato().equals(Constants.TIMESTAMP)){ 
						campos.append(" NVL ( TO_CHAR ( " +  entidad + "." + camposTabla.get(i).getNombreCorto() + ", '" + Constants.FORMATO_TIMESTAMP + "' ), ' ') AS \"" + camposTabla.get(i).getNombreCorto() + "\", ");
					} else {
						campos.append(entidad + "." + camposTabla.get(i).getNombreCorto() + ", ");
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
						for (Campo campoTabla : camposTabla) {
							if (campoTabla.getNombreCorto().equals(campo)) {
								if (campoTabla.getTipoDato().equals(Constants.NUMBER)) {
									filtro.append(campo + " LIKE " + filters.get(campo) + " AND ");
								} else if (campoTabla.getTipoDato().equals(Constants.DATE))	{
									filtro.append(" NVL ( TO_CHAR ( " + campo + ", '" + Constants.FORMATO_FECHA + "' ), ' ') LIKE '%" + filters.get(campo) + "%'" + " AND ");
								} else if (campoTabla.getTipoDato().equals(Constants.TIMESTAMP)){ 
									filtro.append(" NVL ( TO_CHAR ( " + campo + ", '" + Constants.FORMATO_TIMESTAMP + "' ), ' ') LIKE '%" + filters.get(campo) + "%'" + " AND ");
								} else {
									filtro.append(campo + " LIKE '%" + filters.get(campo) + "%'" + " AND ");
								}
							}
						}
					}
					query = query.replace("{3}", filtro);
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
	
	
	private int getTamanoResultado(String entidad, List<Campo> camposTabla, Map<String,Object> filters){
		StringBuffer query;
		try{
			if ((camposTabla != null) && (!camposTabla.isEmpty())) {
				
				query = new StringBuffer("select count(*) as count from " +  entidad);
				if (filters != null && !filters.isEmpty()) {
					StringBuffer filtro = new StringBuffer(" where ");
					for (String campo : filters.keySet()) {
						for (Campo campoTabla : camposTabla) {
							if (campoTabla.getNombreCorto().equals(campo)) {
								if (campoTabla.getTipoDato().equals(Constants.NUMBER)) {
									filtro.append(campo + " LIKE " + filters.get(campo) + " AND ");
								} else if (campoTabla.getTipoDato().equals(Constants.DATE))	{
									filtro.append(" NVL ( TO_CHAR ( " + campo + ", '" + Constants.FORMATO_FECHA + "' ), ' ') LIKE '%" + filters.get(campo) + "%'" + " AND ");
								} else if (campoTabla.getTipoDato().equals(Constants.TIMESTAMP)){ 
									filtro.append(" NVL ( TO_CHAR ( " + campo + ", '" + Constants.FORMATO_TIMESTAMP + "' ), ' ') LIKE '%" + filters.get(campo) + "%'" + " AND ");
								} else {
									filtro.append(campo + " LIKE '%" + filters.get(campo) + "%'" + " AND ");
								}
							}
						}
					}
					
					if (filtro.toString().endsWith(" AND ")) {
						filtro = new StringBuffer(filtro.substring(0, filtro.lastIndexOf(" AND ")));
					}
					query.append(filtro);
				}
				
				return eManager.rowCount(query.toString());  
			}
			
		} catch(Exception e){
			log.error("Error al ejecutar getTamanoResultado()", e);
			
		}
		return 1;
	}
	
	
	/**
	 * Obtiene los campos de la entidad especificada.
	 * @return
	 */
	public List<Campo> obtenerCamposEntidad() {
		return this.eManager.getCampos(oentidad);
	}

	public TMihEntidad getOentidad() {
		return oentidad;
	}

	public void setOentidad(TMihEntidad oentidad) {
		this.oentidad = oentidad;
	}

	public List<TMihEntidad> getEntidades() {
		return entidades;
	}

	public void setEntidades(List<TMihEntidad> entidades) {
		this.entidades = entidades;
	}
	
}
