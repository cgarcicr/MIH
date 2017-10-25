package com.sophos.semih.bean;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.jsf.FacesContextUtils;

import com.sophos.semih.common.Constants;
import com.sophos.semih.model.TMihEntidad;
import com.sophos.semih.model.TMihEntxusr;
import com.sophos.semih.model.TMihEntxusrId;
import com.sophos.semih.model.TMihLineanegocio;
import com.sophos.semih.model.TMihUsuario;
import com.sophos.semih.service.EntidadManager;
import com.sophos.semih.service.EntxusrManager;
import com.sophos.semih.service.UsuarioManager;

@ManagedBean
@ViewScoped
public class AccesoEntidadesBean extends BaseBean{
	
	private static final long serialVersionUID = -1522555894034803240L;
	
	//Managers
	private BeanFactory factory;
	private UsuarioManager usuarioManager;
	private EntidadManager entidadManager;
	private EntxusrManager entxusrManager;
	
	//Propiedades
	private List<TMihUsuario> usuarios;
	private TMihUsuario usuario;
	private List<TMihEntidad> entidadesAutorizador;
	private List<TMihEntidad> entidadesUsuario;
	private LinkedHashMap<TMihEntidad, String> entidadesDisponibles;
	private LinkedHashMap<Integer, Boolean> entidadesSeleccionadas;
	private TMihLineanegocio lnAutorizador;
	
	//Filtro
    private String codigo;
    private String nombres;
    private String apellidos;
    private String rolUsr;
	private String cargo;
    private String area;
    private String estado;
    private String fechaeliminacion;
    private String lineaNegocio;
	

	public AccesoEntidadesBean() {
		factory = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
		usuarioManager = (UsuarioManager) factory.getBean("usuarioManager");
		entidadManager = (EntidadManager) factory.getBean("entidadManager");
		entxusrManager = (EntxusrManager) factory.getBean("entxusrManager");
		
		entidadesAutorizador = new ArrayList<TMihEntidad>();
		entidadesUsuario = new ArrayList<TMihEntidad>();
		entidadesDisponibles = new LinkedHashMap<TMihEntidad,String>();
		entidadesSeleccionadas = new LinkedHashMap<Integer, Boolean>();
		
		usuarios = usuarioManager.getUsuarios(new TMihUsuario());
		
		//Se elimina el usuario logueado de la lista de usuarios a modificar
		TMihUsuario usuario = super.getUser();
		lnAutorizador = usuario.getTMihLineanegocio();
		ArrayList<TMihUsuario> usuariosInactivos = new ArrayList<TMihUsuario>();
		ArrayList<TMihUsuario> usuariosMismaLN = new ArrayList<TMihUsuario>();
		
		for (TMihUsuario user : usuarios) {
			//Mismo usuario
			if (user.getCodigo() == usuario.getCodigo()) {
				usuario = user;
			}
			
			//Usuario inactivo
			if (user.getEstado().equals(Constants.ESTADO_INACTIVO)) {
				usuariosInactivos.add(user);
			}

			//Usuario misma linea de negocio
			if (user.getTMihLineanegocio().getCodigo() == usuario.getTMihLineanegocio().getCodigo()) {
				usuariosMismaLN.add(user);
			}
		}
		
		//Se elimina mismo usuario
		usuarios.remove(usuario);
		
		//Se eliminan usuarios inactivos
		for (TMihUsuario tMihUsuario : usuariosInactivos) {
			usuarios.remove(tMihUsuario);
		}

		//Se eliminan usuarios de la misma linea de negocio.
		for (TMihUsuario tMihUsuario : usuariosMismaLN) {
			usuarios.remove(tMihUsuario);
		}
	}

	
	/**
	 * Permite consultar las entidades de la linea de negocio del autorizador 
	 * que no estan asignadas al usuario. 
	 */
	public String consultarEntidadesAsignar(){
		
		if (entidadesAutorizador != null) {
			entidadesAutorizador.clear();
		}
		if (entidadesUsuario != null) {
			entidadesUsuario.clear();
		}
		if (entidadesDisponibles != null) {
			entidadesDisponibles.clear();
		}
		if (entidadesSeleccionadas != null) {
			entidadesSeleccionadas.clear();
		}
		
		entidadesAutorizador = entidadManager.getEntidadesxUsuario(1);
		entidadesUsuario = entidadManager.getEntidadesxUsuario(usuario);
		
		int flag = 0;
		if (entidadesUsuario != null) {
			for (TMihEntidad entidad_auto : entidadesAutorizador) {
				for (TMihEntidad entidad : entidadesUsuario) {
					if (entidad.getCodigo() == entidad_auto.getCodigo()) {
						entidadesDisponibles.put(entidad_auto, "A");
						entidadesSeleccionadas.put(entidad_auto.getCodigo(), true);
						flag = 1;
						break;
					} 
				}
				if (flag == 0) {
					entidadesSeleccionadas.put(entidad_auto.getCodigo(), false);
					entidadesDisponibles.put(entidad_auto, "I");
				} else {
					flag = 0;
				}
			}
		}
		return null;
	}
	
	public List<TMihEntidad> getKeyAsList(){
		this.consultarEntidadesAsignar();
	    return new ArrayList<TMihEntidad>(entidadesDisponibles.keySet());
	}
	
	/**
	 * Permite guardar las entidades seleccionadas 
	 */
	public void guardar() {

        for (TMihEntidad entidad : new ArrayList<TMihEntidad>(entidadesDisponibles.keySet())) {
        	TMihEntxusrId entxusrid = new TMihEntxusrId(usuario.getCodigo(), entidad.getCodigo());
        	TMihEntxusr entxusr = entxusrManager.findById(entxusrid);
        	
            //Si esta seleccionada
        	if (entidadesSeleccionadas.get(entidad.getCodigo())) {
        		//si esta seleccionada y no existe en bd... se agrega
                if (entxusr == null) {
                	entxusr = new TMihEntxusr(entxusrid, usuario);
					entxusrManager.insertEntxusr(entxusr);
				}
            }
        	//Si no esta seleccionada
        	else {
        		//Si la entidad no esta seleccionada y existe en bd... se elimina
        		if (entxusr != null) {
					entxusrManager.deleteEntxusr(entxusr);
				}
        	}
        }

        super.setMessage("acceso.exito.permisos", BaseBean.INFO, null, null);
        entidadesSeleccionadas.clear();
    }
	 
	
	//GETTERS & SETTERS
	public List<TMihUsuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<TMihUsuario> usuarios) {
		this.usuarios = usuarios;
	}

	public BeanFactory getFactory() {
		return factory;
	}

	public void setFactory(BeanFactory factory) {
		this.factory = factory;
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

	public String getRolUsr() {
		return rolUsr;
	}

	public void setRolUsr(String rolUsr) {
		this.rolUsr = rolUsr;
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

	public TMihUsuario getUsuario() {
		return usuario;
	}

	public void setUsuario(TMihUsuario usuario) {
		this.usuario = usuario;
	}

	public List<TMihEntidad> getEntidadesAutorizador() {
		return entidadesAutorizador;
	}

	public void setEntidadesAutorizador(List<TMihEntidad> entidadesAutorizador) {
		this.entidadesAutorizador = entidadesAutorizador;
	}

	public List<TMihEntidad> getEntidadesUsuario() {
		return entidadesUsuario;
	}

	public void setEntidadesUsuario(List<TMihEntidad> entidadesUsuario) {
		this.entidadesUsuario = entidadesUsuario;
	}

	public LinkedHashMap<TMihEntidad, String> getEntidadesDisponibles() {
		return entidadesDisponibles;
	}

	public void setEntidadesDisponibles(LinkedHashMap<TMihEntidad, String> entidadesDisponibles) {
		this.entidadesDisponibles = entidadesDisponibles;
	}

	public LinkedHashMap<Integer, Boolean> getEntidadesSeleccionadas() {
		return entidadesSeleccionadas;
	}

	public void setEntidadesSeleccionadas(LinkedHashMap<Integer, Boolean> entidadesSeleccionadas) {
		this.entidadesSeleccionadas = entidadesSeleccionadas;
	}

	public String getLineaNegocio() {
		return lineaNegocio;
	}

	public void setLineaNegocio(String lineaNegocio) {
		this.lineaNegocio = lineaNegocio;
	}

	public TMihLineanegocio getLnAutorizador() {
		return lnAutorizador;
	}

	public void setLnAutorizador(TMihLineanegocio lnAutorizador) {
		this.lnAutorizador = lnAutorizador;
	}
}
