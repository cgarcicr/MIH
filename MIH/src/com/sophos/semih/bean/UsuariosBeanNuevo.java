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

import org.springframework.beans.factory.BeanFactory;
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
public class UsuariosBeanNuevo extends BaseBean implements Cloneable{
	
	private static final long serialVersionUID = -8722885608145173756L;

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
	private int codRol;
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
    
    private ExternalContext ec;
    
	//Constructor
    public UsuariosBeanNuevo() {
		usuario = new TMihUsuario();
		page = 1;
		
		//Managers
		this.initManagers();
		
		//Info
		this.initCatalogos2();

	}
	
    
    @AssertTrue(message = "El nombre de usuario que quiere ingresar ya estÃ¡ siendo usado. Utilize uno diferente.")
	public boolean isPasswordsEquals() {
		boolean actualizar = false;
		TMihUsuario usuarioValidacion = new TMihUsuario();
		usuarioValidacion.setCodigoempleado(usuario.getCodigoempleado());
		
		int size = usuarioManager.getUsuarios(usuarioValidacion).size();
		if (size == 0) {
			actualizar = true;
		} 
		
		return actualizar;
		
	}
    
	
    /**
	 * Reestablece los valores a mostrar en la ventana de creaciÃ³n de usuario.
	 * @return 
	 */
	public void nuevoUsr(){
		this.usuario = this.nuevoUsuario();
	}

	/**
	 * Actualiza los detalles del proyecto seleccionado.
	 */
	public void guardar() {
		try {
			this.obtenerPermisosUsuario();
			usuario.setFeregistro(new SimpleDateFormat(Constants.FORMATO_FECHA).parse(new SimpleDateFormat(Constants.FORMATO_FECHA).format(new Date())));
			usuario.setFemodificacion(new SimpleDateFormat(Constants.FORMATO_FECHA).parse(new SimpleDateFormat(Constants.FORMATO_FECHA).format(new Date())));
			usuario.setTMihLineanegocio(lnManager.getLineaNegocioById(codLineaNegocio));
			usuario.setCodigo(usuarioManager.getNuevoCodigo(usuario));
			usuarioManager.insertUsuario(usuario);
			
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
		} catch (Exception e) {
			error = "Error al guardar " + e.getMessage();
		}
	}
	
	/**
	 * Setea los permisos del usuario segun su rol. 
	 */
	public String obtenerPermisosUsuario(){
		this.refrescarValores();
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
	 */
	public String obtenerPermisosRol(){
		this.refrescarValores();
		TMihRol rol = rolManager.gerRolById(this.codRol);
		esSudo = (rol.getCodigo() == Constants.COD_USR_SUPERUSR || rol.getCodigo() == Constants.COD_USR_CONSULTA) ? true : false;
		usuario.setTMihRol(rol);
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
	 * Reestablece los valores a mostrar en la ventana de creaciÃ³n de usuario.
	 * @return 
	 */
	public TMihUsuario nuevoUsuario(){
		this.refrescarValores();
		this.codRol = 0;
		this.codLineaNegocio = 0;
		this.codProyecto = 0;
		this.lineasNegocio = null;
		this.usuario = new TMihUsuario();
		usuario.setTMihLineanegocio(new TMihLineanegocio());
		usuario.setTMihRol(new TMihRol());
		return this.usuario;
	}
	
	/**
	 * Metodo que controla el cambio de proyecto, para cargar las lï¿½neas de
	 * negocio que estan atadas al nuevo proyecto seleccionado.
	 * 
	 * @param e Evento de cambio en la lista de proyectos
	 */
	public void cambioProyecto(ValueChangeEvent e) {
		try {
			//this.refrescarValores();
			TMihLineanegocio ln = new TMihLineanegocio();
			ln.setTMihProyecto(pManager.getProyectoById(Integer.parseInt(e
					.getNewValue().toString())));
			ln.setEstado(Constants.ESTADO_ACTIVO);
			setLineasNegocio(lnManager.getLineaNegociosProy(ln));
		} catch (NumberFormatException e1) {
			System.out.println("hola desde el catch");
		}
	}

	public void cambioProyecto() {
		try {
			//this.refrescarValores();
			TMihLineanegocio ln = new TMihLineanegocio();
			usuario = usuarioManager.getUsuarioById(usuario.getCodigo());
			ln.setTMihProyecto(pManager.getProyectoById(usuario.getTMihLineanegocio().getTMihProyecto().getCodigo()));
			setLineasNegocio(lnManager.getLineaNegociosProy(ln));
		} catch (NumberFormatException e1) {

		}
	}
	
	public void refrescarValores(){
		ArrayList<String> ids = new ArrayList<String>();
		ids.add("usuarios:n_codigoEmpleado");
		ids.add("usuarios:n_nombresUsuario");
		ids.add("usuarios:n_apellidosUsuario");
		ids.add("usuarios:n_tipoDoc");
		ids.add("usuarios:n_numeroDoc");
		ids.add("usuarios:n_empresaUsuario");
		ids.add("usuarios:n_cargoUsuario");
		ids.add("usuarios:n_areaUsuario");
		ids.add("usuarios:n_rolUsuario");
		ids.add("usuarios:n_estadoUsuario");
		ids.add("usuarios:n_proyUsuario");
		ids.add("usuarios:n_lnUsuario");
		
		ids.add("usuarios:n_admUsuario");
		ids.add("usuarios:n_parametros");
		ids.add("usuarios:n_entidades");
		ids.add("usuarios:n_carga");
		ids.add("usuarios:n_consultaPuntual");
		ids.add("usuarios:n_consultaMasiva");
		ids.add("usuarios:n_auditoria");
		ids.add("usuarios:n_depuracion");
		ids.add("usuarios:n_autorizar");
		super.resetValues(ids);
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

	public int getCodRol() {
		return codRol;
	}

	public void setCodRol(int codRol) {
		this.codRol = codRol;
	}

	public boolean isAutorizar() {
		return autorizar;
	}

	public void setAutorizar(boolean autorizar) {
		this.autorizar = autorizar;
	}

	public boolean isEsSudo() {
		return esSudo;
	}

	public void setEsSudo(boolean esSudo) {
		this.esSudo = esSudo;
	}	
	
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
		areaValues = new HashMap<>();
		cargoValues = new HashMap<>();
		
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
		
		ec = FacesContext.getCurrentInstance().getExternalContext();

		/** Obtiene la session */
		HttpSession session = ((HttpServletRequest) ec.getRequest())
				.getSession();
		
		/** Obtiene las listas de session */
		cargos = (List<TMihCatalogo>)session.getAttribute("ListaCargos");
		tipoDocumento= (List<TMihCatalogo>) session.getAttribute("ListaTipoDocumento");
		empresa = (List<TMihCatalogo>)session.getAttribute("ListaEmpresa");
		areas = (List<TMihCatalogo>)session.getAttribute("ListaAreas");
		rol = (List<TMihRol>)session.getAttribute("ListaRol");
		proyectos = (List<TMihProyecto>)session.getAttribute("ListaProyectos");
		lineasNegocio = (List<TMihLineanegocio>)session.getAttribute("ListaLineasNegocio");
		
		
		//Valores
		areaValues = new HashMap<>();
		cargoValues = new HashMap<>();
		
		for (TMihCatalogo areaValue : areas) {
			areaValues.put(areaValue.getId().getCodigo(), areaValue.getDescripcion());
		}
		for (TMihCatalogo cargoValue : cargos) {
			cargoValues.put(cargoValue.getId().getCodigo(), cargoValue.getDescripcion());
		}
		
	}
	
	

}

