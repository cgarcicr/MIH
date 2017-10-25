package com.sophos.semih.bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.jsf.FacesContextUtils;

import com.sophos.semih.common.Constants;
import com.sophos.semih.model.Campo;
import com.sophos.semih.model.TMihAplicacion;
import com.sophos.semih.model.TMihEntidad;
import com.sophos.semih.model.TMihLineanegocio;
import com.sophos.semih.model.TMihProyecto;
import com.sophos.semih.model.TMihUsuario;
import com.sophos.semih.service.AplicacionManager;
import com.sophos.semih.service.EntidadManager;
import com.sophos.semih.service.LineaNegocioManager;
import com.sophos.semih.service.ProyectoManager;
import com.sophos.semih.service.UsuarioManager;

/**
 * Clase que soporta la plantilla de creacion de nueva entidad. El bean debe ser
 * llamado como 'entidadNueva'
 * 
 * @author sebastian.duque
 * 
 */
@ManagedBean(name = "entidadNueva")
@ViewScoped
public class EntidadNuevaBean extends BaseBean {
	
	private static final long serialVersionUID = -7244357647735000616L;
	// Atributos de la entidad
	private Integer codProyecto;
	private Integer codLineaNegocio;
	private Integer codAplicacion;
	private boolean vacia;

	// Listas para seleccion de aplicacion
	private List<TMihProyecto> proyectos;
	private List<TMihLineanegocio> lineasNegocio;
	private List<TMihAplicacion> aplicaciones;
	private List<String> tipoDato;
	private List<TMihEntidad> tablasExistentes;
	
	// Lista de Usuarios existentes para solicitante
	private List<TMihUsuario> solicitantes;	
	
	// Manejo de campos
	private List<Campo> campos;
	private List<Campo> camposFecha;
	private Campo campoSel;
	private Campo campoEdi;
	private List<Campo> camposEntidadBck;

	// Entidad que se esta controlando
	private TMihEntidad entidad;

	// Contiene error a desplegar al usuario
	private String error;

	// Bandera para controlar si se esta editando o creando una nueva entidad
	private boolean nueva;

	private EntidadManager eManager;
	private AplicacionManager aManager;
	private LineaNegocioManager lnManager;
	private ProyectoManager pManager;
	private UsuarioManager uManager;
	private BeanFactory factory = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
	private ExternalContext ec;
	
	private static final Log log = LogFactory.getLog(ParametrosBean.class);

	/**
	 * Constructor
	 */
	public EntidadNuevaBean() {
		// Carga los manager
		lnManager = (LineaNegocioManager) factory.getBean("lineaNegocioManager");
		pManager = (ProyectoManager) factory.getBean("proyectoManager");
		aManager = (AplicacionManager) factory.getBean("aplicacionManager");
		eManager = (EntidadManager) factory.getBean("entidadManager");
		uManager = (UsuarioManager) factory.getBean("usuarioManager");
		
		// Carga la lista de los proyectos activos.
		TMihProyecto proy = new TMihProyecto();
		proy.setEstado(Constants.ESTADO_ACTIVO);
		proyectos = pManager.getProyectos(proy);

		// Carga la lista de tipos de dato		
		tipoDato = new ArrayList<String>(Arrays.asList(Constants.VARCHAR2, Constants.NUMBER,
				Constants.DATE, Constants.TIMESTAMP));
		
		// Carga la lista de los usuarios para solicitantes (los que tienen permisos de autorizador)
		TMihUsuario usuario = new TMihUsuario();
		usuario.setEstado('A');
		usuario.setAutorizar(Constants.FLAG_AFIRMATIVO);
		setSolicitantes(uManager.getUsuarios(usuario));		

		ec = FacesContext.getCurrentInstance().getExternalContext();

		/** Obtiene la sesion */
		HttpSession session = ((HttpServletRequest) ec.getRequest())
				.getSession();

		/** Obtiene el id de la Entidad seleccionada de la sesion */
		Integer entId = (Integer) session.getAttribute("entId");

		/** Remueve la Entidad seleccionada de la sesion */
		session.removeAttribute("entId");
		
		//Tablas existentes
		tablasExistentes = eManager.getEntidades(new TMihEntidad());
		
		//Inicializa la lista de camposFecha
		camposFecha = new ArrayList<Campo>();

		/** Asigna la entidad y campos existentes */
		if (entId != null) {
			nueva = false;
			entidad = eManager.getEntidadById(entId);
			entidad.setCampos(eManager.getCampos(entidad));
			
			//Indica si la tabla esta vacia.
			vacia = entidad.getVacia().equals("S") ? true : false;
			
			//Se obtienen los campos fecha.
			this.llenarCamposFecha();
			
			codProyecto = entidad.getTMihAplicacion().getTMihLineanegocio()
					.getTMihProyecto().getCodigo();
			codLineaNegocio = entidad.getTMihAplicacion().getTMihLineanegocio()
					.getCodigo();
			codAplicacion = entidad.getTMihAplicacion().getCodigo();
			// Carga las listas de lineas de negocio y aplicacion
			TMihLineanegocio ln = new TMihLineanegocio();
			ln.setTMihProyecto(pManager.getProyectoById(codProyecto));
			ln.setEstado(Constants.ESTADO_ACTIVO);
			lineasNegocio = lnManager.getLineaNegociosProy(ln);
			TMihAplicacion a = new TMihAplicacion();
			a.setTMihLineanegocio(lnManager.getLineaNegocioById(codLineaNegocio));
			a.setEstado(Constants.ESTADO_ACTIVO);
			aplicaciones = aManager.getAplicacionesLN(a);
		} else {
			nueva = true;
			vacia = true;
			entidad = new TMihEntidad();
			entidad.setCampos(new ArrayList<Campo>());
			entidad.setTvigencia(Constants.VIGENCIA);
		}
	}


	/**
	 * Inicializa los campos fecha para la tabla seleccionada.
	 */
	private void llenarCamposFecha(){
		List<Campo> camposEntidad = entidad.getCampos();
		for (Campo campo : camposEntidad) {
			if (campo.getTipoDato().equals(Constants.DATE)) {
				camposFecha.add(campo);
			}
		}
	}
	
	private void resetCamposFecha(){
		List<Campo> camposEntidad = entidad.getCampos();
		camposFecha.clear();
		for (Campo campo : camposEntidad) {
			if (campo.getTipoDato().equals(Constants.DATE)) {
				camposFecha.add(campo);
			}
		}
	}
	
	/**
	 * Metodo ejecutado despues de cargar el bean.
	 */
	@PostConstruct
	public void init() {
		// Inicializa el nuevoCampo
		campoSel = new Campo();
		campoSel.setTipoDato("NUMBER");
		campoSel.setPrecision("0");
	}

	/**
	 * Carga las listas de lineas negocio y aplicaciones al seleccionar un nuevo
	 * proyecto
	 * 
	 * @param e
	 *            evento
	 */
	public void cambioProyecto(AjaxBehaviorEvent e) {
		HtmlSelectOneMenu selProyecto = (HtmlSelectOneMenu) e.getComponent();
		Integer nuevoProyId = (Integer) selProyecto.getValue();
		TMihLineanegocio ln = new TMihLineanegocio();
		try {
			ln.setTMihProyecto(pManager.getProyectoById(nuevoProyId));
			ln.setEstado(Constants.ESTADO_ACTIVO);
			lineasNegocio = lnManager.getLineaNegociosProy(ln);
			aplicaciones = null;
			UIViewRoot viewRoot = FacesContext.getCurrentInstance()
					.getViewRoot();
			if (selProyecto.getClientId().equals("formEntidad:epList")) {
				HtmlSelectOneMenu selLn = (HtmlSelectOneMenu) viewRoot
						.findComponent("formEntidad:elnList");
				Integer lnVal = (Integer) selLn.getValue();
				ln = lnManager.getLineaNegocioById(lnVal);
				for (TMihLineanegocio lnl : lineasNegocio) {
					if (lnl.getCodigo() == ln.getCodigo()) {
						TMihAplicacion a = new TMihAplicacion();
						a.setTMihLineanegocio(lnManager.getLineaNegocioById(lnVal));
						a.setEstado(Constants.ESTADO_ACTIVO);
						aplicaciones = aManager.getAplicacionesLN(a);
						break;
					}
				}
			}
		} catch (Exception e2) {
			error = e2.getMessage();
		}
	}

	/**
	 * Carga la lista de aplicaciones al seleccionar una nueva lï¿½nea de negocio
	 * 
	 * @param e
	 */
	public void cambioLineaNegocio(AjaxBehaviorEvent e) {
		HtmlSelectOneMenu selLn = (HtmlSelectOneMenu) e.getComponent();
		Integer nuevaLnId = (Integer) selLn.getValue();
		TMihAplicacion a = new TMihAplicacion();
		try {
			a.setTMihLineanegocio(lnManager.getLineaNegocioById(nuevaLnId));
			a.setEstado(Constants.ESTADO_ACTIVO);
			setAplicaciones(aManager.getAplicacionesLN(a));
		} catch (Exception e2) {
			error = e2.getMessage();
		}
	}

	/**
	 * Agrega un campo a la entidad activa
	 */
	public void agregarCampo() {
		List<Campo> camposEntidad = entidad.getCampos();
		
		
		System.out.println("Tamaño de camposEntidad:"+camposEntidad.size());
		
		boolean repetido = false;
		boolean longitudNum = true;
		boolean longitudVar = true;
		for (Campo campo : camposEntidad) {
			if (campo.getNombreCorto().equals(campoSel.getNombreCorto())) {
				repetido = true;
			}
		}
		
			
		
			System.out.println("Nombre corto: "+campoSel.getNombreCorto()+
			"\nNombre completo: "+campoSel.getNombreCompleto()+
			"\nTipo de dato: "+campoSel.getTipoDato()+
			"\nlongitud: "+campoSel.getLongitud()+
			"\nDescripción: "+campoSel.getDescripcion());
			
		
		if (campoSel.getTipoDato().equals(Constants.NUMBER) 
				&& Integer.parseInt(campoSel.getLongitud()) > 38) {
			longitudNum = false;
		}

		if (campoSel.getTipoDato().equals(Constants.VARCHAR2) 
				&& Integer.parseInt(campoSel.getLongitud()) > 4000) {
			longitudVar = false;
		}
		
		if (!repetido) {
			if (longitudNum) {
				if (longitudVar) {
					log.info(campoSel.getNombreCorto());
					entidad.getCampos().add(campoSel);
					
					//Si es un campo fecha se agrega a la lista camposFecha
					if (campoSel.getTipoDato().equals(Constants.DATE)) {
						camposFecha.add(campoSel);
					}
					
					log.info(entidad.getCampos().size());
					campoSel = new Campo();
				} else {
					super.setMessage("entidades.longitud.var.error", BaseBean.ERROR, "campoNuevo:inPl", null);
				}
			} else {
				super.setMessage("entidades.longitud.num.error", BaseBean.ERROR, "campoNuevo:inPl", null);
			}
		} else {
			super.setMessage("entidades.campos.repetido", BaseBean.WARNING, "campoNuevo:nombreCorto", null);
		}
	}
	
	/**
	 * Permite editar el campo seleccionado.
	 */
	public void editarCampos(){
		List<Campo> camposEntidad = entidad.getCampos();
		
		int repetido = 0;
		boolean longitudNum = true;
		boolean longitudVar = true;
		
		if (campoEdi.getTipoDato().equals(Constants.NUMBER) 
				&& Integer.parseInt(campoEdi.getLongitud()) > 38) {
			longitudNum = false;
		}
		
		if (campoEdi.getTipoDato().equals(Constants.VARCHAR2) 
				&& Integer.parseInt(campoEdi.getLongitud()) > 4000) {
			longitudVar = false;
		}
		
		if (camposEntidad != null) {
			if (longitudNum) {
				if (longitudVar) {
					log.info(campoEdi.getNombreCorto());

					// Se modifica el campo seleccionado.
					for (Campo campo : camposEntidad) {
						if (campo.getNombreCorto().equals(campoEdi.getNombreCorto())) {
							campo.setClave(campoEdi.getClave());
							campo.setClaveForanea(campoEdi.getClaveForanea());
							campo.setDescripcion(campoEdi.getDescripcion());
							campo.setLongitud(campoEdi.getLongitud());
							campo.setNombreCompleto(campoEdi.getNombreCompleto());
							campo.setNombreCorto(campoEdi.getNombreCorto());
							campo.setPrecision(campoEdi.getPrecision());
							campo.setTipoDato(campoEdi.getTipoDato());
							campo.setTipoDatoCompleto(campoEdi.getTipoDatoCompleto());
							break;
						}
					}
					
					//Si existen dos con el mismo nombre se devuelven los cambios.
					for (Campo campo : camposEntidad) {
						if (campo.getNombreCorto().equals(campoEdi.getNombreCorto())) {
							repetido++;
						}
					}
					
					if (repetido <= 1) {
						//Se verifican los cambios de datos con respecto al tipo fecha.
						verificarCambioDesdeFecha(campoEdi);
						verificarCambioAFecha(campoEdi);
						
						// Se reestablece variable de campo seleccionado.
						log.info(entidad.getCampos().size());
						campoEdi = new Campo();
					} else {
						//Se devuelven los cambios.
						entidad.setCampos(eManager.getCampos(entidad));
						this.resetCamposFecha();
						super.setMessage("entidades.campos.repetido", BaseBean.ERROR, null, null);
					}
				} else {
					super.setMessage("entidades.longitud.var.error", BaseBean.ERROR, null, null);
				}
			} else {
				super.setMessage("entidades.longitud.num.error", BaseBean.ERROR, null, null);
			}
		}
	}
	
	/**
	 * Verifica si el campo cambia a tipo fecha y efectua las acciones necesarias. 
	 * @param campos
	 * @param campo
	 */
	private void verificarCambioAFecha(Campo campoEdi){
		// Si es un campo fecha se modifica en la lista camposFecha
		if (campoEdi.getTipoDato().equals(Constants.DATE)) {
			boolean editado = false;
			for (Campo campo : camposFecha) {
				if (campo.getNombreCorto().equals(campoEdi.getNombreCorto())) {
					campo.setClave(campoEdi.getClave());
					campo.setClaveForanea(campoEdi.getClaveForanea());
					campo.setDescripcion(campoEdi.getDescripcion());
					campo.setLongitud(campoEdi.getLongitud());
					campo.setNombreCompleto(campoEdi.getNombreCompleto());
					campo.setNombreCorto(campoEdi.getNombreCorto());
					campo.setPrecision(campoEdi.getPrecision());
					campo.setTipoDato(campoEdi.getTipoDato());
					campo.setTipoDatoCompleto(campoEdi.getTipoDatoCompleto());
					editado = true;
					break;
				}
			}
			//Si es tipo fecha, pero no se edito, es porque es nuevo.
			if (!editado) {
				camposFecha.add(campoEdi);
			}
		}
	}
	
	/**
	 * Verifica si el campo fecha cambia a otro tipo y se retira de la lista de campos fecha.
	 * @param campos
	 * @param campo
	 */
	private void verificarCambioDesdeFecha(Campo campo){
		//Recorre el arreglo de campos tipo fecha para verificar que no exista uno con el mismo nombre
		if (camposFecha != null && !camposFecha.isEmpty()) {
			if (!campo.getTipoDato().equals(Constants.DATE)) {
				for (int i = 0; i < camposFecha.size(); i++) {
					
					//Si existe otro campo con el mismo nombre en la lista de fecha se retira.
					if (camposFecha.get(i).getNombreCorto().equals(campo.getNombreCorto())) {
						camposFecha.remove(i);
						break;
					}
				}
			}
		}
	}
	
	/**
	 * Elimina un campo a la entidad activa
	 */
	public void eliminarCampo(){
		log.info(campoSel.getNombreCorto());
		entidad.getCampos().remove(campoSel);
		camposFecha.remove(campoSel);
		log.info(entidad.getCampos().size());
		campoSel = new Campo();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Aviso","Para terminar el proceso debe guardar los cambios."));
	}

	/**
	 * Permite mostrar los valores del campo seleccionado para editar en 
	 * el popup "Editar Campo"
	 */
	public void refreshEditarCampo(){
//		this.refrescarValores();
		super.clearMessages();
		
		if (campoEdi != null) {
			setValoresVista(campoEdi);
		}
	}
	
	/**
	 * Crea una copia del array de campos que permite determinar mas adelante si
	 * es existen campos duplicados.
	 * En caso afirmativo permite reestablecer el array de campos modificado.
	 */
	public void crearHistoricoCampos(){
		if (campoEdi != null && entidad.getCampos() != null) {
//			ArrayList<Campo> camposTabla = (ArrayList<Campo>)entidad.getCampos();
//			camposEntidadBck = (ArrayList<Campo>)camposTabla.clone();
		}
	}
	
	/**
	 * Permite refrescar los valores de la vista.
	 */
	public void refrescarValores(){
		ArrayList<String> ids = new ArrayList<String>();
		ids.add("editarCampo:nombreCorto_e");
		ids.add("editarCampo:nombreCompleto_e");
		ids.add("editarCampo:selTipoDato_e");
		ids.add("editarCampo:inPl_e");
		ids.add("editarCampo:inPp_e");
		ids.add("editarCampo:descripcionCampo_e");
		super.resetValues(ids);
	}
	
	/**
	 * Permite refrescar los valores de la vista.
	 */
	public void setValoresVista(Campo campo){
		super.setUIValue("editarCampo:nombreCorto_e", campo.getNombreCorto());
		super.setUIValue("editarCampo:nombreCompleto_e", campo.getNombreCompleto());
		super.setUIValue("editarCampo:selTipoDato_e", campo.getTipoDato());
		super.setUIValue("editarCampo:inPl_e", campo.getLongitud());
		super.setUIValue("editarCampo:inPp_e", campo.getPrecision());
		super.setUIValue("editarCampo:descripcionCampo_e", campo.getDescripcion());
	}
	
	/**
	 * Guarda una nueva entidad en el maestro de entidades
	 */
	public String guardar() {
		
		entidad.setTMihAplicacion(aManager.getAplicacionById(codAplicacion));		
		entidad.setVacia("S");
		try {
			if(nueva){
				if (!this.verificar()) {
					entidad.setFecreacion(new Date());
					eManager.insertEntidad(entidad);
					entidad = new TMihEntidad();
					codProyecto = null;
					codLineaNegocio = null;
					codAplicacion = null;
					
					try {
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Aviso","Tabla creada con exito."));
						return "adm_entidad.xhtml";
					} catch (Exception e) {
						log.error("Error cargando la pagina", e);
						
					}
				} 
			}else{
				eManager.updateEntidad(entidad);
				entidad = new TMihEntidad();
				codProyecto = null;
				codLineaNegocio = null;
				codAplicacion = null;
				
				try {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Aviso","Se han aplicado los cambios correctamente."));
					return "admin_entidad.xhtml";
				} catch (Exception e) {
					log.error("Error cargando la pagina", e);
				}
			}
		} catch (Exception e) {
			error = "Error al guardar " + e.getMessage();
		}
		entidad.setTvigencia(Constants.VIGENCIA);
		return "";
	}

	/**
	 * Permite verificar si una tabla a crear ya existe en la BD
	 * @return
	 */
	public boolean verificar() {
		try {
			
			if (entidad.getNombre().length() > Constants.TAMANO_NOMBRE_TABLA) {
				super.setMessage("entidades.tamano.nombre.tabla", BaseBean.ERROR,"formEntidad:tablaDuplicada", null);
			}
			
			if(nueva){
				boolean repetida = false;
				
				//Se busca si es una entidad repetida
				for (TMihEntidad entidadGuardar : tablasExistentes) {
					if (entidadGuardar.getNombre().equals(entidad.getNombre())) {
						repetida = true;
						break;
					}
				}
				
				//Si es repetida se pone el mensaje.
				if (repetida) {
					super.setMessage("entidades.repetida", BaseBean.ERROR,"formEntidad:tablaDuplicada", null);
				}
				
				return repetida;
			}
		} catch (Exception e) {
			log.error("Error verificando la existencia de la tabla", e);
		}
		return false;
	}

	
	/**
	 * Permite borrar los datos presentados en la vista.
	 * @return
	 */
	public String limpiar(){
		try {
			System.out.println("El código es: "+entidad.getCodigo());
			if (!(this.entidad.getCodigo() == 0)){
				FacesContext.getCurrentInstance().getExternalContext().redirect("admin_entidad.xhtml");
			} else {
			
				super.clearMessages();
				this.limpiarValores();
				
				this.entidad.setNombre(null);
				this.entidad.setTvigencia(Constants.VIGENCIA);
				this.entidad.setUsrsolcreacion(null);
				this.codProyecto = 0;
				this.codLineaNegocio = 0;
				this.codAplicacion = null;
				this.entidad.setDepurar(null);
				this.entidad.setCampoLlave(null);
				this.entidad.setCampoFecha(null);
				this.entidad.setDescripcion(null);
				
				try {
					FacesContext.getCurrentInstance().getExternalContext().redirect("adm_entidad.xhtml");
				} catch (IOException e) {
					log.error("Error cargando la pagina", e);
				}
			}
				
			return "";
		} catch (Exception e) {
			log.error("Error cargando la pagina", e);
		}
		return null;
	}
	
	private void limpiarValores(){
		ArrayList<String> ids = new ArrayList<String>();
		ids.add("formEntidad:nuevoNombre");
		ids.add("formEntidad:nuevoTiempoVigencia");
		ids.add("formEntidad:solicitante");
		ids.add("formEntidad:cpList");
		ids.add("formEntidad:clnList");
		ids.add("formEntidad:caList");
		ids.add("formEntidad:cLlave");
		ids.add("formEntidad:cFecha");
		ids.add("formEntidad:descripcion");
		
		super.resetValues(ids);
		
		
	}
	
	
	public void camposEditar(){
		System.out.println("El nombre corto es: "+campoEdi.getNombreCorto());
		
	}
	
	// Getters y Setters
	public Integer getCodProyecto() {
		return codProyecto;
	}

	public void setCodProyecto(Integer codProyecto) {
		this.codProyecto = codProyecto;
	}

	public Integer getCodLineaNegocio() {
		return codLineaNegocio;
	}

	public void setCodLineaNegocio(Integer codLineaNegocio) {
		this.codLineaNegocio = codLineaNegocio;
	}

	public Integer getCodAplicacion() {
		return codAplicacion;
	}

	public void setCodAplicacion(Integer codAplicacion) {
		this.codAplicacion = codAplicacion;
	}

	public List<TMihProyecto> getProyectos() {
		return proyectos;
	}

	public void setProyectos(List<TMihProyecto> proyectos) {
		this.proyectos = proyectos;
	}

	public List<TMihLineanegocio> getLineasNegocio() {
		return lineasNegocio;
	}

	public void setLineasNegocio(List<TMihLineanegocio> lineasNegocio) {
		this.lineasNegocio = lineasNegocio;
	}

	public List<TMihAplicacion> getAplicaciones() {
		return aplicaciones;
	}

	public void setAplicaciones(List<TMihAplicacion> aplicaciones) {
		this.aplicaciones = aplicaciones;
	}

	public List<String> getTipoDato() {
		return tipoDato;
	}

	public void setTipoDato(List<String> tipoDato) {
		this.tipoDato = tipoDato;
	}

	public List<Campo> getCampos() {
		return campos;
	}

	public void setCampos(List<Campo> campos) {
		this.campos = campos;
	}

	public Campo getCampoSel() {
		return campoSel;
	}

	public void setCampoSel(Campo campoSel) {
		this.campoSel = campoSel;
	}

	public Campo getCampoEdi() {
		return campoEdi;
	}

	public void setCampoEdi(Campo campoEdi) {
		this.campoEdi = campoEdi;
	}

	public TMihEntidad getEntidad() {
		return entidad;
	}

	public void setEntidad(TMihEntidad entidad) {
		this.entidad = entidad;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public EntidadManager geteManager() {
		return eManager;
	}

	public void seteManager(EntidadManager eManager) {
		this.eManager = eManager;
	}

	public AplicacionManager getaManager() {
		return aManager;
	}

	public void setaManager(AplicacionManager aManager) {
		this.aManager = aManager;
	}

	public LineaNegocioManager getLnManager() {
		return lnManager;
	}

	public void setLnManager(LineaNegocioManager lnManager) {
		this.lnManager = lnManager;
	}

	public ProyectoManager getpManager() {
		return pManager;
	}

	public void setpManager(ProyectoManager pManager) {
		this.pManager = pManager;
	}

	public BeanFactory getFactory() {
		return factory;
	}

	public void setFactory(BeanFactory factory) {
		this.factory = factory;
	}

	public ExternalContext getEc() {
		return ec;
	}

	public void setEc(ExternalContext ec) {
		this.ec = ec;
	}

	public boolean isNueva() {
		return nueva;
	}

	public void setNueva(boolean nueva) {
		this.nueva = nueva;
	}

	public List<TMihUsuario> getSolicitantes() {
		return solicitantes;
	}

	public void setSolicitantes(List<TMihUsuario> solicitantes) {
		this.solicitantes = solicitantes;
	}

	public List<Campo> getCamposFecha() {
		return camposFecha;
	}

	public void setCamposFecha(List<Campo> camposFecha) {
		this.camposFecha = camposFecha;
	}

	public boolean isVacia() {
		return vacia;
	}

	public void setVacia(boolean vacia) {
		this.vacia = vacia;
	}
}
