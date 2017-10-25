package com.sophos.semih.bean;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import com.sophos.semih.util.Utilidades;

public class ConsultaDinamicaLazy extends LazyDataModel<LinkedHashMap<String, String>>  {
	

	private static final long serialVersionUID = 7151622417875284751L;
	 
	private EntidadManager eManager;
	private BeanFactory factory;
	private ConsultaDinamicaBean consultaDinamicaBean;
	private List<TMihEntidad> entidades;
	private TMihEntidad entidad1;
	private TMihEntidad entidad2;
	private List<Campo> camposSeleccionadosE1;
	private List<Campo> camposSeleccionadosE2;
	private String foranea;
	private List<Campo> camposE1;
	private List<Campo> camposE2;
	private String clavePrimaria;
	private String claveForanea;
	private static final Log log = LogFactory.getLog(ConsultaDinamicaLazy.class);
	
	
	/**
	 * Constructor
	 * 
	 * @param entidad1
	 * @param entidad2
	 * @param camposSeleccionadosE1
	 * @param camposSeleccionadosE2
	 * @param foranea
	 * @param consultaDinamicaBean
	 */
    public ConsultaDinamicaLazy(TMihEntidad entidad1 , TMihEntidad entidad2, 
    		List<Campo> camposSeleccionadosE1, List<Campo> camposSeleccionadosE2, String foranea, ConsultaDinamicaBean consultaDinamicaBean) {  
        this.factory = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
		this.eManager = (EntidadManager) factory.getBean(Constants.MANAGER_ENTIDAD);
		setEntidades(eManager.getEntidadesxUsuario());
		this.entidad1 = new TMihEntidad();
		this.entidad1 = entidad1;
		this.entidad2 = entidad2;
		this.camposSeleccionadosE1 = camposSeleccionadosE1;
		this.camposSeleccionadosE2 = camposSeleccionadosE2;
		this.foranea = foranea;
		this.camposE1 = obtenerCampose1();
		this.camposE2 = obtenerCampose2();
		this.consultaDinamicaBean = consultaDinamicaBean;
		
    }  
    
    /**
     * Metodo que ejecuta la consulta y la paginacion del lado del servidor
     */
    @Override  
  
    public List<LinkedHashMap<String, String>> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {	
        //super.load(first, pageSize, sortField, sortOrder, filters);
    	
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
    	super.setRowCount(Integer.parseInt(max));
    	ArrayList<LinkedHashMap<String, String>> resultado = new ArrayList<LinkedHashMap<String, String>>(); 
		
		try {
			this.setCamposSeleccionados();
			String query = this.createQuery(entidad1.getNombre(), entidad2.getNombre(), camposSeleccionadosE1, camposSeleccionadosE2, foranea, max, min, filters);
			
			if (query != null && !query.isEmpty()) {
				ArrayList<Campo> camposConsolidado = new ArrayList<Campo>();
				camposConsolidado = this.setPrefijosCampos(camposSeleccionadosE1, camposSeleccionadosE2);
				if (camposConsolidado != null && !camposConsolidado.isEmpty()) {
					resultado = eManager.consultaDinamica(query, camposConsolidado);
					if ((resultado != null) && (resultado.size() > 0) ){
						this.setRowCount(this.getTamanoResultado(entidad1.getNombre(), entidad2.getNombre(), camposConsolidado, clavePrimaria, claveForanea, filters));
						consultaDinamicaBean.setGetConsulta(true);
					}
				}
				
				// Verifica que hallan resultados.
				if (resultado != null && resultado.isEmpty()) {
					consultaDinamicaBean.setMessage("consultas.dinamicas.no_resultados", BaseBean.WARNING, null, null);
					consultaDinamicaBean.setGetConsulta(false);
					return null;
				}
			}
			
		} catch (Exception e) {
			consultaDinamicaBean.setMessage("consultas.dinamicas.no_resultados", BaseBean.WARNING, null, null);
			return null;
		}		
		return resultado;
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
     * Obtiene la lista de campos seleccionado de cada un ade las tablas
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
     * Crea el query que se utilizara para la consulta, filtro y paginacion
     * 
     * @param estructura1
     * @param estructura2
     * @param campose1
     * @param campose2
     * @param claveForanea
     * @param max
     * @param min
     * @param filters
     * @return
     */
    public String createQuery(String estructura1, String estructura2, List<Campo> campose1, List<Campo> campose2, 
    		String claveForanea, String max, String min, Map<String,Object> filters){
		String e1 = "E1";
		String e2 = "E2";
		
		boolean control = true;
		String query = "";
		try {
			
			if ((estructura1 != null && estructura2 != null && campose1 != null && campose2 != null && claveForanea != null) 
					&& (!estructura1.isEmpty() && !estructura2.isEmpty() && !campose1.isEmpty() && !campose2.isEmpty() && !claveForanea.isEmpty() )) {
			
				if (Utilidades.isAlfaNumerico(estructura1) && Utilidades.isAlfaNumerico(estructura2)
						&& Utilidades.isAlfaNumerico(claveForanea)) {
					
					query = "select * from (Select {1} ROWNUM rnum from {2} join {3} on {4} = {5} where {6} ROWNUM <= {MAX} ) where rnum  > {MIN}";
					
					//Clave primaria primera entidad
					String clavePrimariaE1 = "";
					
					//Campos
					StringBuffer campos = new StringBuffer("");
					for (Campo campo : campose1) {
						
						if (campo.getTipoDato().equals(Constants.DATE))	{
							campos.append(" NVL ( TO_CHAR ( " + e1 + "." + campo.getNombreCorto() + ", '" + Constants.FORMATO_FECHA + "' ), ' ') AS \"T1_" + campo.getNombreCorto() + "\", ");
						} else if (campo.getTipoDato().equals(Constants.TIMESTAMP)){ 
							campos.append(" NVL ( TO_CHAR ( " +  e1 + "." + campo.getNombreCorto() + ", '" + Constants.FORMATO_TIMESTAMP + "' ), ' ') AS \"T1_" + campo.getNombreCorto() + "\", ");
						} else {
							campos.append(e1 + "." + campo.getNombreCorto() + " AS T1_" + campo.getNombreCorto() + ", ");
						}
						
						if ((campo.getClave() != null) && (campo.getClave().equals(Constants.FLAG_ES_CLAVE))) {
							clavePrimariaE1 = campo.getNombreCorto();
						}
					}
					
					if (control) {
						for (int i = 0; i < campose2.size(); i++) {
							
							if (campose2.get(i).getTipoDato().equals(Constants.DATE))	{
								campos.append(" NVL ( TO_CHAR ( " + e2 + "." + campose2.get(i).getNombreCorto() + ", '" + Constants.FORMATO_FECHA + "' ), ' ') AS \"T2_" + campose2.get(i).getNombreCorto() + "\", ");
							} else if (campose2.get(i).getTipoDato().equals(Constants.TIMESTAMP)){ 
								campos.append(" NVL ( TO_CHAR ( " +  e2 + "." + campose2.get(i).getNombreCorto() + ", '" + Constants.FORMATO_TIMESTAMP + "' ), ' ') AS \"T2_" + campose2.get(i).getNombreCorto() + "\", ");
							} else {
								campos.append(e2 + "." + campose2.get(i).getNombreCorto() + " AS T2_" + campose2.get(i).getNombreCorto() + ", ");
							}
						}
						query = query.replace("{1}", campos.toString());
						
						//Entidades
						query = query.replace("{2}", estructura1 + " " + e1);
						query = query.replace("{3}", estructura2 + " " + e2);
						
						//Condiciones
						this.clavePrimaria = clavePrimariaE1; 
						this.claveForanea = claveForanea;
						query = query.replace("{4}", e1 + "." + clavePrimariaE1);
						query = query.replace("{5}", e2 + "." + claveForanea);
					}
				}
				
				//Limite superior
				query = query.replace("{MAX}", max);
				
				//Limite inferior
				query = query.replace("{MIN}", min);
				
				//Filtro
				List<Campo> camposTabla = this.setPrefijosCampos(campose1, campose2);
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
					query = query.replace("{6}", this.organizarFiltro(filtro));
				}
				
				if (query.contains("{6}")) {
					query = query.replace("{6}", "");
				}
				
			} else {
				//Verifica campos tabla uno.
//				if (campose1.isEmpty()) {
//					consultaDinamicaBean.setMessage("consultas.dinamicas.slct_campostabla", BaseBean.ERROR, null, estructura1);
//					control = false;
//				}

				//Verifica campos tabla dos.
//				if (campose2.isEmpty()) {
//					consultaDinamicaBean.setMessage("consultas.dinamicas.slct_campostabla", BaseBean.ERROR, null, estructura2);
//					control = false;
//				}
				
				//Verifica clave foranea.
//				if (claveForanea.isEmpty() && !campose1.isEmpty() && !campose2.isEmpty()) {
//					consultaDinamicaBean.setMessage("consultas.dinamicas.no_clave_foranea", BaseBean.ERROR, null, estructura2);
//					control = false;
//				}
			}
			
		} catch (Exception e) {
			log.error("Error creando el query para la consulta dinamica", e);
		}
		
		return query;
	}
    
    /**
     * Organiza el filtro
     * @param filtro
     */
    private String organizarFiltro(StringBuffer filtro){
    	String nuevofiltro = filtro.toString();
    	nuevofiltro = nuevofiltro.replace("T1_", "e1.");
    	nuevofiltro = nuevofiltro.replace("T2_", "e2.");
    	return nuevofiltro;
    }
    
	/**
	 * Obtiene la cantidad de registros que arroja la consulta. Se utiliza para la paginacion.
	 * 
	 * @param entidad1
	 * @param entidad2
	 * @param consolidadoCampos
	 * @param clavePrimaria
	 * @param claveForanea
	 * @param filters
	 * @return
	 */
	private int getTamanoResultado(String entidad1, String entidad2, List<Campo> consolidadoCampos, String clavePrimaria, String claveForanea, Map<String,Object> filters){
		StringBuffer query;
		try{
			if ((consolidadoCampos != null) && (!consolidadoCampos.isEmpty())) {
				
				query = new StringBuffer("Select count(*) as count from " + entidad1 + " e1 join " + entidad2 + " e2 on e1." + clavePrimaria + " = e2." + claveForanea);
				if (filters != null && !filters.isEmpty()) {
					StringBuffer filtro = new StringBuffer(" where ");
					for (String campo : filters.keySet()) {
						for (Campo campoTabla : consolidadoCampos) {
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
					query.append(this.organizarFiltro(filtro));
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
		return this.eManager.getCampos(entidad1);
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
	
	
	//GETTERS & SETTERS
	public TMihEntidad getOentidad() {
		return entidad1;
	}

	public void setOentidad(TMihEntidad oentidad) {
		this.entidad1 = oentidad;
	}

	public List<TMihEntidad> getEntidades() {
		return entidades;
	}

	public void setEntidades(List<TMihEntidad> entidades) {
		this.entidades = entidades;
	}
	
}
