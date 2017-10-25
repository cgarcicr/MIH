package com.sophos.semih.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.jsf.FacesContextUtils;

import com.sophos.semih.model.Campo;
import com.sophos.semih.model.TMihEntidad;
import com.sophos.semih.service.EntidadManager;

@ManagedBean
@ViewScoped
public class ConsultaEspecificaBean extends BaseBean{

	private static final long serialVersionUID = -6038817284932277349L;

	private BeanFactory factory;
	private EntidadManager eManager;
	
	private boolean mostrarResultado;
	private List<TMihEntidad> entidades;
	private TMihEntidad oentidad;
	private ArrayList<Campo> camposoEntidad;
	private ArrayList<SelectItem> nombreEntidades;
	private ArrayList<LinkedHashMap<String, String>> tablaDinamica;
	private ArrayList<String> columnas;

	private LinkedHashMap<String, String> filtro;
	
	private LazyDataModel<LinkedHashMap<String, String>> lazyModel;
	private ConsultaEspecificaLazy consultaLazy;
	private static final Log log = LogFactory.getLog(ConsultaEspecificaBean.class);
	
	
	//Constructor
	public ConsultaEspecificaBean() {
		//Manager
		factory = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
		eManager = (EntidadManager) factory.getBean("entidadManager");
		
		//Info
		entidades = eManager.getEntidadesxUsuario();
		filtro = new LinkedHashMap<String, String>();
		oentidad = new TMihEntidad();
		mostrarResultado = false;
		consultaLazy = new ConsultaEspecificaLazy(oentidad);
		lazyModel = consultaLazy;
		
		//test
		columnas = new ArrayList<String>();
	}
	
	
	/**
	 * Recoje la informacion necesaria para hacer la consulta dinamica.
	 * @return
	 */
	public String consultar(){
		String resultado = "";
		try {
			lazyModel = new ConsultaEspecificaLazy(oentidad);

			//Campos de la tabla que apareceran en la vista.
			columnas.clear();
			List<Campo> camposEntidad = this.obtenerCamposEntidad();
			for (Campo campo : camposEntidad) {
				columnas.add(campo.getNombreCorto());
			}
			
			mostrarResultado = true;
			resultado = "exito";
		} catch (Exception e) {
			log.error("Error al hacer la consulta especifica", e);
		}
		return resultado;
	}
	
	/**
	 * Obtiene los campos que tiene la primera entidad seleccionada
	 * @return
	 */
	public List<Campo> obtenerCamposEntidad() {
		return this.eManager.getCampos(oentidad);
	}
	
	/**
	 * Permite crear el query con el que se realizara la busqueda dinamica.
	 * 
	 * @param entidad	Nombre de la primera estructura seleccionada
	 * @param camposEntidad	Campos seleccionados de la primera estructura seleccionada
	 * foranea de la primera clase. 
	 * @return
	 */
	public String createQueryEntidad(String entidad, List<Campo> camposEntidad){
		String query = "Select {1} from {2} ";
		try {
			//Campos
			StringBuffer campos = new StringBuffer("");
			for (int i = 0; i < camposEntidad.size() - 1; i++) {
				campos.append(entidad + "." + camposEntidad.get(i).getNombreCorto() + ", ");
			}
			campos.append(entidad + "." + camposEntidad.get(camposEntidad.size() - 1));
			query = query.replace("{1}", campos);
			
			//Entidad
			query = query.replace("{2}", entidad + " " + entidad);
			
		} catch (Exception e) {
			log.error("Error al crear el query", e);
		}
		
		return query;
	}
	
	
	/**
	 * Obtiene el nombre de las entidades que se van a mostrar en la lista
	 * de entidades a seleccionar en la vista de consulta din�mica.
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
	
	
	
	//GETTERS & SETTERS.
	public BeanFactory getFactory() {
		return factory;
	}

	public void setFactory(BeanFactory factory) {
		this.factory = factory;
	}

	public ArrayList<LinkedHashMap<String, String>> getTablaDinamica() {
		return tablaDinamica;
	}

	public void setTablaDinamica(ArrayList<LinkedHashMap<String, String>> tablaDinamica) {
		this.tablaDinamica = tablaDinamica;
	}


	public TMihEntidad getOentidad() {
		return oentidad;
	}

	public void setOentidad(TMihEntidad oentidad) {
		this.oentidad = oentidad;
	}

	public List<Campo> getCamposoEntidad() {
		return camposoEntidad;
	}

	public void setCamposoEntidad(ArrayList<Campo> camposoEntidad) {
		this.camposoEntidad = camposoEntidad;
	}

	public List<TMihEntidad> getEntidades() {
		return entidades;
	}

	public void setEntidades(List<TMihEntidad> entidades) {
		this.entidades = entidades;
	}

	public ArrayList<SelectItem> getNombreEntidades() {
		nombreEntidades = this.obtenerNombreEntidades();
		return nombreEntidades;
	}

	public void setNombreEntidades(ArrayList<SelectItem> nombreEntidades) {
		this.nombreEntidades = nombreEntidades;
	}

	public boolean getMostrarResultado() {
		return mostrarResultado;
	}

	public void setMostrarResultado(boolean mostrarResultado) {
		this.mostrarResultado = mostrarResultado;
	}

	public LinkedHashMap<String, String> getFiltro() {
		return filtro;
	}

	public void setFiltro(LinkedHashMap<String, String> filtro) {
		this.filtro = filtro;
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




	/**
	 * Clase interna que permite manejar un número dinamico de columnas en la tabla de resultados.
	 * @author Juan.Toro
	 *
	 */
	static public class ColumnModel implements Serializable {

		private static final long serialVersionUID = -351721461470087379L;
		private String header;
		private String property;

		public ColumnModel(String header, String property) {
			this.header = header;
			this.property = property;
		}

		public String getHeader() {
			return header;
		}

		public String getProperty() {
			return property;
		}
	}
	
}
