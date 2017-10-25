package com.sophos.semih.bean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.AssertTrue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.jsf.FacesContextUtils;

import com.sophos.semih.common.Constants;
import com.sophos.semih.model.TMihCatalogo;
import com.sophos.semih.model.TMihLineanegocio;
import com.sophos.semih.model.TMihProyecto;
import com.sophos.semih.model.TMihRol;
import com.sophos.semih.model.TMihTipocatalogo;
import com.sophos.semih.model.TMihUsuario;
import com.sophos.semih.service.CatalogoManager;
import com.sophos.semih.service.LineaNegocioManager;
import com.sophos.semih.service.ProyectoManager;
import com.sophos.semih.service.RolManager;
import com.sophos.semih.service.TipoCatalogoManager;
import com.sophos.semih.service.UsuarioManager;
import com.sophos.semih.util.Utilidades;
import javax.faces.context.ExternalContext;

@ManagedBean
@ViewScoped
public class UsuariosBean extends BaseBean implements Cloneable{

	private static final long serialVersionUID = -81269953130673094L;
	private static final Log log = LogFactory.getLog(UsuariosBean.class);
	
	//Managers
	private BeanFactory factory;
	private TMihUsuario usuario;
	private UsuarioManager usuarioManager;	
	private CatalogoManager catalogoManager;
	private LineaNegocioManager lnManager;
	private TipoCatalogoManager tipoCatalogoManager;
	private RolManager rolManager;
	private ProyectoManager pManager;
	
	//Info
	private int codProyecto;
	private int codLineaNegocio;
	private String error;
    private int page;
    private int rows;
    private boolean newRow;
    private boolean esSudo;
    private HashMap<String, String> cargoValues;
    private HashMap<String, String> areaValues;
    private List<TMihUsuario> usuariosList;
    private List<TMihProyecto> proyectos;
    private List<TMihCatalogo> cargos;
    private List<TMihRol> rol;
    private List<TMihCatalogo> tipoDocumento;
    private List<TMihCatalogo> empresa;
    private List<TMihCatalogo> areas;
    private List<TMihLineanegocio> lineasNegocio;
    
  //lista con todos los catalogos:
    
    private List<TMihCatalogo> todosLosCatalogos;
    
    //Permisos usuario
    private boolean admusuario;
    private boolean parametros;
    private boolean entidades;
    private boolean carga;
    private boolean consultapuntual;
    private boolean consultamasiva;
    private boolean auditoria;
    private boolean depuracion;
    private boolean autorizar;
    
    //Filtro tabla de usuarios
    private String codigo;
    private String nombres;
    private String apellidos;
    private String rolUsr;
	private String cargo;
    private String area;
    private String estado;
    private String fechaeliminacion;
    private String lineaNegocio;
    
    private ExternalContext ec;
    
    //Constructor
	public UsuariosBean() {
		this.usuario = new TMihUsuario();
		this.esSudo = false;
		this.page = 1;
		
		//Managers
		this.initManagers();
		
		//Usuarios
		this.initUsuarios();
			
		//Info
		this.initCatalogos2();
		
		//Catalogos
		//this.initCatalogos();
	}
	
	@AssertTrue(message = "El nombre de usuario que quiere ingresar ya estÃ¡ siendo usado. Utilize uno diferente.")
	public boolean isPasswordsEquals() {
		boolean actualizar = false;
		TMihUsuario usuarioValidacion = new TMihUsuario();
		usuarioValidacion.setCodigoempleado(usuario.getCodigoempleado());
		
		int size = usuarioManager.getUsuarios(usuarioValidacion).size();
		if (size == 1){
			if (usuarioManager.getUsuarioById(usuario.getCodigo()).getCodigoempleado().equals(usuario.getCodigoempleado())) {
				actualizar = true;
			}
		} else if (size == 0) {
			actualizar = true;
		} 
		
		return actualizar;
		
	}
	
	/**
	 * Elimina el usuario seleccionado.
	 */
	public void eliminar() {
		try {
			
			if ((usuario != null) && (usuario.getEstado() != null) && (usuario.getEstado().equals(Constants.ESTADO_INACTIVO))) {
				usuario.setFemodificacion(new SimpleDateFormat(Constants.FORMATO_FECHA).parse(new SimpleDateFormat(Constants.FORMATO_FECHA).format(new Date())));
				usuario.setFeeliminacion(new SimpleDateFormat(Constants.FORMATO_FECHA).parse(new SimpleDateFormat(Constants.FORMATO_FECHA).format(new Date())));
				usuarioManager.updateUsuario(usuario);			
			} else {
				error = "El usuario debe estar inactivo!";
			}
		} catch (DataIntegrityViolationException e) {
			error = "Existen dependencias hascia este usurio. No se puede eliminar.";
		} catch (Exception e) {
			error = "Error al eliminar el usuario. " + e.getMessage();
		}
	}
	
	/**
	 * Actualiza los detalles del proyecto seleccionado.
	 */
	public void guardar() {
		try {
			
			//Si un usuario eliminado se activa, se quita la fecha de eliminaciÃ³n.
			if ((usuario.getEstado().equals(Constants.ESTADO_ACTIVO)) && (usuario.getFeeliminacion() != null)) {
				usuario.setFeeliminacion(null);
			}
			
			//Si el usuario tiene codigo se actualiza.
			if (usuario.getCodigo() != null) {
				this.obtenerPermisosUsuario();
				usuario.setFemodificacion(new SimpleDateFormat(Constants.FORMATO_FECHA).parse(new SimpleDateFormat(Constants.FORMATO_FECHA).format(new Date())));

				//Se verifica si se puede actualizar el usuario.
//				if (this.actulizarUsuario(usuario)) {
					usuarioManager.updateUsuario(usuario);
//				} else {
//					super.setMessage("usuarios.verificacion.repetido", BaseBean.WARNING, null, null);
//				}
			} 
			
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
		} catch (Exception e) {
			error = "Error al guardar " + e.getMessage();
		} finally {
			//Se limpia el usuario creado
			super.clearMessages();
			this.nuevoUsuario();
		}
	}
	
	/**
	 * Reestablece datos del usuario seleccionados para ser mostrados.
	 */
	public void editarUsr(){
		this.refrescarValores();
		super.clearMessages();
		this.mostrarPermisosUsuario();
		this.cambioProyecto();
	}
	
	/**
	 * Setea los permisos del usuario segun su rol. 
	 * @param usuario
	 */
	public String obtenerPermisosUsuario(){
		this.refrescarValores();
		super.clearMessages();
		usuario.setAdmusuario(admusuario ? Constants.FLAG_AFIRMATIVO: Constants.FLAG_NEGATIVO);
		usuario.setParametros(parametros ? Constants.FLAG_AFIRMATIVO: Constants.FLAG_NEGATIVO);
		usuario.setEntidades(entidades ? Constants.FLAG_AFIRMATIVO: Constants.FLAG_NEGATIVO);
		usuario.setCarga(carga ? Constants.FLAG_AFIRMATIVO: Constants.FLAG_NEGATIVO);
		usuario.setConsultapuntual(consultapuntual ? Constants.FLAG_AFIRMATIVO: Constants.FLAG_NEGATIVO);
		usuario.setConsultamasiva(consultamasiva ? Constants.FLAG_AFIRMATIVO: Constants.FLAG_NEGATIVO);
		usuario.setAuditoria(auditoria ? Constants.FLAG_AFIRMATIVO: Constants.FLAG_NEGATIVO);
		usuario.setDepuracion(depuracion ? Constants.FLAG_AFIRMATIVO: Constants.FLAG_NEGATIVO);
		usuario.setAutorizar(autorizar ? Constants.FLAG_AFIRMATIVO: Constants.FLAG_NEGATIVO);
		return null;
	}
	
	/**
	 * Obtiene los permisos propios del usuario para ser mostrados
	 */
	public String mostrarPermisosUsuario(){
		this.refrescarValores();
		super.clearMessages();
		esSudo = (usuario.getTMihRol().getCodigo() == Constants.COD_USR_SUPERUSR || usuario.getTMihRol().getCodigo() == Constants.COD_USR_CONSULTA) ? true : false;
		admusuario = usuario.getAdmusuario() != null && usuario.getAdmusuario().equals(Constants.FLAG_AFIRMATIVO) ? true:false;
		parametros = usuario.getParametros() != null && usuario.getParametros().equals(Constants.FLAG_AFIRMATIVO) ? true:false;
		entidades = usuario.getEntidades() != null && usuario.getEntidades().equals(Constants.FLAG_AFIRMATIVO) ? true:false;
		carga = usuario.getCarga() != null && usuario.getCarga().equals(Constants.FLAG_AFIRMATIVO) ? true:false;
		consultapuntual = usuario.getConsultapuntual() != null && usuario.getConsultapuntual().equals(Constants.FLAG_AFIRMATIVO) ? true:false;
		consultamasiva = usuario.getConsultamasiva() != null && usuario.getConsultamasiva().equals(Constants.FLAG_AFIRMATIVO) ? true:false;
		auditoria = usuario.getAuditoria() != null && usuario.getAuditoria().equals(Constants.FLAG_AFIRMATIVO) ? true:false;
		depuracion = usuario.getDepuracion() != null && usuario.getDepuracion().equals(Constants.FLAG_AFIRMATIVO) ? true:false;
		autorizar = usuario.getAutorizar() != null && usuario.getAutorizar().equals(Constants.FLAG_AFIRMATIVO) ? true:false;
		return null;
	}

	/**
	 * Obtiene los permisos del rol. 
	 * @param usuario
	 */
	public String obtenerPermisosRol(){
		this.refrescarValores();
		super.clearMessages();
		TMihRol rol = rolManager.gerRolById(this.usuario.getTMihRol().getCodigo());
		esSudo = (rol.getCodigo() == Constants.COD_USR_SUPERUSR || rol.getCodigo() == Constants.COD_USR_CONSULTA) ? true : false;
		usuario.getTMihRol().setNombre(rol.getNombre());
		admusuario = rol.getAdmusuario().equals(Constants.FLAG_AFIRMATIVO) ? true:false;
		parametros = rol.getParametros().equals(Constants.FLAG_AFIRMATIVO) ? true:false;
		entidades = rol.getEntidades().equals(Constants.FLAG_AFIRMATIVO) ? true:false;
		carga = rol.getCarga().equals(Constants.FLAG_AFIRMATIVO) ? true:false;
		consultapuntual = rol.getConsultapuntual().equals(Constants.FLAG_AFIRMATIVO) ? true:false;
		consultamasiva = rol.getConsultamasiva().equals(Constants.FLAG_AFIRMATIVO) ? true:false;
		auditoria = rol.getAuditoria().equals(Constants.FLAG_AFIRMATIVO) ? true:false;
		depuracion = rol.getDepuracion().equals(Constants.FLAG_AFIRMATIVO) ? true:false;
		autorizar = rol.getAutorizar().equals(Constants.FLAG_AFIRMATIVO) ? true:false;
		return "";
	}
	
	
	/**
	 * Obtiene un nuevo usuario desde la vista.
	 * @return
	 */
	public TMihUsuario nuevoUsuario(){
		this.refrescarValores();
		super.clearMessages();
		this.usuario = new TMihUsuario();
		usuario.setTMihLineanegocio(new TMihLineanegocio());
		usuario.setTMihRol(new TMihRol());
		return this.usuario;
	}
	
	/**
	 * Metodo que controla el cambio de proyecto, para cargar las lineas de
	 * negocio que estan atadas al nuevo proyecto seleccionado.
	 * 
	 * @param e evento de cambio en la lista de proyectos
	 */
	public void cambioProyecto(ValueChangeEvent e) {
		try {
//			this.refrescarValores();
			super.clearMessages();
			TMihLineanegocio ln = new TMihLineanegocio();
			ln.setTMihProyecto(pManager.getProyectoById(Integer.parseInt(e
					.getNewValue().toString())));
			ln.setEstado(Constants.ESTADO_ACTIVO);
			setLineasNegocio(lnManager.getLineaNegociosProy(ln));
		} catch (NumberFormatException e1) {
			log.error("No se pudo cambiar de proyecto", e1);
		}
	}

	/**
	 * Metodo que controla el cambio de proyecto, para cargar las lineas de
	 * negocio que estan atadas al nuevo proyecto seleccionado.
	 * 
	 */
	public void cambioProyecto() {
		try {
//			this.refrescarValores();
			super.clearMessages();
			TMihLineanegocio ln = new TMihLineanegocio();
			usuario = usuarioManager.getUsuarioById(usuario.getCodigo());
			ln.setTMihProyecto(pManager.getProyectoById(usuario.getTMihLineanegocio().getTMihProyecto().getCodigo()));
			setLineasNegocio(lnManager.getLineaNegociosProy(ln));
		} catch (NumberFormatException e1) {
			log.error("No se pudo cambiar de proyecto", e1);
		}
	}
	
	/**
	 * Permite refrescar los valores de la vista.
	 */
	public void refrescarValores(){
		ArrayList<String> ids = new ArrayList<String>();
		ids.add("usuarios:codigoEmpleado");
		ids.add("usuarios:nombresUsuario");
		ids.add("usuarios:apellidosUsuario");
		ids.add("usuarios:tipoDoc");
		ids.add("usuarios:numeroDoc");
		ids.add("usuarios:empresaUsuario");
		ids.add("usuarios:cargoUsuario");
		ids.add("usuarios:areaUsuario");
		ids.add("usuarios:rolUsuario");
		ids.add("usuarios:estadoUsuario");
		ids.add("usuarios:proyUsuario");
		ids.add("usuarios:lnUsuario");
		
		ids.add("usuarios:admUsuario");
		ids.add("usuarios:parametros");
		ids.add("usuarios:entidades");
		ids.add("usuarios:carga");
		ids.add("usuarios:consultaPuntual");
		ids.add("usuarios:consultaMasiva");
		ids.add("usuarios:auditoria");
		ids.add("usuarios:depuracion");
		ids.add("usuarios:autorizar");
		super.resetValues(ids);
	}
	
	/**
	 * Permite verificar que no se esta actualizando un usario utilizando un
	 * codigo de empleado repetido.
	 * @param usuario
	 * @return
	 */
	private boolean actulizarUsuario(TMihUsuario usuario){
		if (usuarioManager.getUsuarioById(usuario.getCodigo()).getCodigoempleado().equals(usuario.getCodigoempleado())) {
			return true;
		}
		return false;
	}
	
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
	//GETTERS Y SETTERS
	public TMihUsuario getUsuario() {
		return usuario;
	}

	public void setUsuario(TMihUsuario usuario) {
		this.usuario = usuario;
	}
	
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public boolean isNewRow() {
		return newRow;
	}

	public void setNewRow(boolean newRow) {
		this.newRow = newRow;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
	
	public List<TMihUsuario> getUsuariosList() {
		return usuariosList;
	}

	public void setUsuariosList(List<TMihUsuario> usuariosList) {
		this.usuariosList = usuariosList;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getFechaeliminacion() {
		return fechaeliminacion;
	}

	public void setFechaeliminacion(String fechaeliminacion) {
		this.fechaeliminacion = fechaeliminacion;
	}

	public List<TMihCatalogo> getCargos() {
		return cargos;
	}

	public void setCargos(List<TMihCatalogo> cargos) {
		this.cargos = cargos;
	}

	public List<TMihRol> getRol() {
		return rol;
	}

	public void setRol(List<TMihRol> rol) {
		this.rol = rol;
	}

	public List<TMihCatalogo> getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(List<TMihCatalogo> tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public List<TMihCatalogo> getEmpresa() {
		return empresa;
	}

	public void setEmpresa(List<TMihCatalogo> empresa) {
		this.empresa = empresa;
	}

	public List<TMihCatalogo> getAreas() {
		return areas;
	}

	public void setAreas(List<TMihCatalogo> areas) {
		this.areas = areas;
	}

	public List<TMihLineanegocio> getLineasNegocio() {
		return lineasNegocio;
	}

	public void setLineasNegocio(List<TMihLineanegocio> lineasNegocio) {
		this.lineasNegocio = lineasNegocio;
	}

	public HashMap<String, String> getCargoValues() {
		return cargoValues;
	}

	public void setCargoValues(HashMap<String, String> cargoValues) {
		this.cargoValues = cargoValues;
	}

	public HashMap<String, String> getAreaValues() {
		return areaValues;
	}

	public void setAreaValues(HashMap<String, String> areaValues) {
		this.areaValues = areaValues;
	}
	
	public String getRolUsr() {
		return rolUsr;
	}

	public void setRolUsr(String rolUsr) {
		this.rolUsr = rolUsr;
	}

	public List<TMihProyecto> getProyectos() {
		return proyectos;
	}

	public void setProyectos(List<TMihProyecto> proyectos) {
		this.proyectos = proyectos;
	}

	public int getCodProyecto() {
		return codProyecto;
	}

	public void setCodProyecto(int codProyecto) {
		this.codProyecto = codProyecto;
	}

	public boolean isAdmusuario() {
		return admusuario;
	}

	public void setAdmusuario(boolean admusuario) {
		this.admusuario = admusuario;
	}

	public boolean isParametros() {
		return parametros;
	}

	public void setParametros(boolean parametros) {
		this.parametros = parametros;
	}

	public boolean isEntidades() {
		return entidades;
	}

	public void setEntidades(boolean entidades) {
		this.entidades = entidades;
	}

	public boolean isCarga() {
		return carga;
	}

	public void setCarga(boolean carga) {
		this.carga = carga;
	}

	public boolean isConsultapuntual() {
		return consultapuntual;
	}

	public void setConsultapuntual(boolean consultapuntual) {
		this.consultapuntual = consultapuntual;
	}

	public boolean isConsultamasiva() {
		return consultamasiva;
	}

	public void setConsultamasiva(boolean consultamasiva) {
		this.consultamasiva = consultamasiva;
	}

	public boolean isAuditoria() {
		return auditoria;
	}

	public void setAuditoria(boolean auditoria) {
		this.auditoria = auditoria;
	}

	public boolean isDepuracion() {
		return depuracion;
	}

	public void setDepuracion(boolean depuracion) {
		this.depuracion = depuracion;
	}

	public int getCodLineaNegocio() {
		return codLineaNegocio;
	}

	public void setCodLineaNegocio(int codLineaNegocio) {
		this.codLineaNegocio = codLineaNegocio;
	}

	public boolean isAutorizar() {
		return autorizar;
	}

	public void setAutorizar(boolean autorizar) {
		this.autorizar = autorizar;
	}

	public String getLineaNegocio() {
		return lineaNegocio;
	}

	public void setLineaNegocio(String lineaNegocio) {
		this.lineaNegocio = lineaNegocio;
	}

	public boolean getEsSudo() {
		return esSudo;
	}

	public void setEsSudo(boolean esSudo) {
		this.esSudo = esSudo;
	}
	
	/*
	/**
	 * Inicializa los managers
	 */
	private void initManagers(){
		factory = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
		usuarioManager = (UsuarioManager) factory.getBean(Constants.MANAGER_USUARIO);
		catalogoManager = (CatalogoManager) factory.getBean(Constants.MANAGER_CATALOGO);
		tipoCatalogoManager = (TipoCatalogoManager) factory.getBean(Constants.MANAGER_TIPO_CATALOGO);
		lnManager = (LineaNegocioManager) factory.getBean(Constants.MANAGER_LINEA_NEGOCIO);
		rolManager = (RolManager) factory.getBean(Constants.MANAGER_ROL);
		pManager = (ProyectoManager) factory.getBean(Constants.MANAGER_PROYECTO);
	}
	
	/**
	 * Obtiene la informacion de los usuarios.
	 */
	private void initUsuarios(){
		try {
			this.usuariosList = new ArrayList<TMihUsuario>();
			usuariosList = usuarioManager.getUsuarios(new TMihUsuario());
			
			//Se elimina el usuario logueado de la lista de usuarios a modificar
			TMihUsuario usuario = super.getUser();
			for (TMihUsuario user : usuariosList) {
				if (user.getCodigo() == usuario.getCodigo()) {
					usuario = user;
					break;
				}
			}
			usuariosList.remove(usuario);
		} catch (Exception e) {
			
		}
	}
	
	/**
	 * Obtiene la informacion de los catalogos.
	 */
	private void initCatalogos(){
		TMihCatalogo catalogo = new TMihCatalogo();
		TMihTipocatalogo tipoCatalogo = tipoCatalogoManager.getTipoCatalogoById(Constants.CAT_CARGO);
		catalogo.setTMihTipocatalogo(tipoCatalogo);
		cargos = catalogoManager.getCatalogosTipo(catalogo);
		
		tipoCatalogo = tipoCatalogoManager.getTipoCatalogoById(Constants.CAT_TIPO_DOC);
		catalogo.setTMihTipocatalogo(tipoCatalogo);
		tipoDocumento = catalogoManager.getCatalogosTipo(catalogo);
		
		tipoCatalogo = tipoCatalogoManager.getTipoCatalogoById(Constants.CAT_EMPRESAS);
		catalogo.setTMihTipocatalogo(tipoCatalogo);
		empresa = catalogoManager.getCatalogosTipo(catalogo);
		
		tipoCatalogo = tipoCatalogoManager.getTipoCatalogoById(Constants.CAT_AREA);
		catalogo.setTMihTipocatalogo(tipoCatalogo);
		areas = catalogoManager.getCatalogosTipo(catalogo);
		
		rol = rolManager.getRoles(new TMihRol());
		
		TMihProyecto tmihProyecto = new TMihProyecto();
		tmihProyecto.setEstado(Constants.ESTADO_ACTIVO);
		proyectos = pManager.getProyectos(tmihProyecto);
		
		TMihLineanegocio tmihLineaNegocio = new TMihLineanegocio();
		tmihLineaNegocio.setEstado(Constants.ESTADO_ACTIVO);
		lineasNegocio = lnManager.getLineaNegocios(tmihLineaNegocio);
		
		//Valores
		this.areaValues = new HashMap<>();
		this.cargoValues = new HashMap<>();
		
		for (TMihCatalogo areaValue : areas) {
			areaValues.put(areaValue.getId().getCodigo(), areaValue.getDescripcion());
		}
		for (TMihCatalogo cargoValue : cargos) {
			cargoValues.put(cargoValue.getId().getCodigo(), cargoValue.getDescripcion());
		}
	}
	
	
	/**
	 * Obtiene la informacion de los catalogos.
	 */
	private void initCatalogos2(){
		todosLosCatalogos = catalogoManager.getCatalogos(new TMihCatalogo());

		cargos = Utilidades.obtenerListadoCatalogos(todosLosCatalogos, Constants.CAT_CARGO);
		
		tipoDocumento = Utilidades.obtenerListadoCatalogos(todosLosCatalogos, Constants.CAT_TIPO_DOC);
		empresa = Utilidades.obtenerListadoCatalogos(todosLosCatalogos, Constants.CAT_EMPRESAS);
		areas = Utilidades.obtenerListadoCatalogos(todosLosCatalogos, Constants.CAT_AREA);
		
		rol = rolManager.getRoles(new TMihRol());
		
		TMihProyecto tmihProyecto = new TMihProyecto();
		tmihProyecto.setEstado(Constants.ESTADO_ACTIVO);
		proyectos = pManager.getProyectos(tmihProyecto);
		
		TMihLineanegocio tmihLineaNegocio = new TMihLineanegocio();
		tmihLineaNegocio.setEstado(Constants.ESTADO_ACTIVO);
		lineasNegocio = lnManager.getLineaNegocios(tmihLineaNegocio);
		
		//Valores
		this.areaValues = new HashMap<>();
		this.cargoValues = new HashMap<>();
		
		for (TMihCatalogo areaValue : areas) {
			areaValues.put(areaValue.getId().getCodigo(), areaValue.getDescripcion());
		}
		for (TMihCatalogo cargoValue : cargos) {
			cargoValues.put(cargoValue.getId().getCodigo(), cargoValue.getDescripcion());
		}
		
		//-------poner listas en session------------------
		
		ec = FacesContext.getCurrentInstance().getExternalContext();

		/** Obtiene la session */
		HttpSession session = ((HttpServletRequest) ec.getRequest())
				.getSession();
		
		/** Pone las listas en session */
		session.setAttribute("ListaCargos", cargos);
		session.setAttribute("ListaTipoDocumento", tipoDocumento);
		session.setAttribute("ListaEmpresa", empresa);
		session.setAttribute("ListaAreas", areas);
		session.setAttribute("ListaRol", rol);
		session.setAttribute("ListaProyectos", proyectos);
		session.setAttribute("ListaLineasNegocio", lineasNegocio);

	}
	
	
	
}
