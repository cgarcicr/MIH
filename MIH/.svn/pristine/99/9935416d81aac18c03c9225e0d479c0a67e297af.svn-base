package com.sophos.semih.bean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.jsf.FacesContextUtils;

import com.sophos.semih.common.Constants;
import com.sophos.semih.model.TMihCatalogo;
import com.sophos.semih.model.TMihCatalogoId;
import com.sophos.semih.model.TMihTipocatalogo;
import com.sophos.semih.service.CatalogoManager;
import com.sophos.semih.service.TipoCatalogoManager;

/**
 * Clase que soporta la tabla de catalogos existentes. El bean debe ser llamado
 * como 'catalogoExistente'
 * 
 * @author sebastian.duque
 * 
 */
@ManagedBean(name = "catalogoExistente")
@ViewScoped
public class CatalogoExistenteBean extends BaseBean {
	private static final long serialVersionUID = 3210145412268726354L;
	// Lista para cargar la tabla de catalogos
	private List<TMihCatalogo> catalogos;
	// Lista para dar la opcion del tipo de catalogo a administrar
	private List<TMihTipocatalogo> tiposCatalogo;
	private String error;
	private CatalogoManager cManager;
	private TipoCatalogoManager tcManager;
	private TMihTipocatalogo tipoCatalogo;
	private TMihCatalogo catalogo;
	private TMihCatalogo cat;
	private String nombreNuevo;
	private int pag = 1;
	transient BeanFactory factory = FacesContextUtils
			.getWebApplicationContext(FacesContext.getCurrentInstance());
	private ExternalContext ec;
	private boolean eliminado;
	private boolean eliminarRg = false;
	private static final Log log = LogFactory.getLog(CatalogoExistenteBean.class);

	
	
	/**
	 * Constructor
	 */
	public CatalogoExistenteBean() {
		setTcManager((TipoCatalogoManager) factory
				.getBean("tipoCatalogoManager"));
		setcManager((CatalogoManager) factory.getBean("catalogoManager"));
		setTiposCatalogo(tcManager.getTiposCatalogo(new TMihTipocatalogo()));

		ec = FacesContext.getCurrentInstance().getExternalContext();

		/** Obtiene la sesion */
		HttpSession session = ((HttpServletRequest) ec.getRequest())
				.getSession();

		/** Obtiene el id del TipoCatalogo seleccionado de la sesion */
		Integer catId = (Integer) session.getAttribute("catId");

		/** Remueve el TipoCatalogo seleccionado de la sesion */
		session.removeAttribute("catId");

		/** Asigna el TipoCatalogo y Catalogos existentes */
		if (catId != null) {
			cat = new TMihCatalogo();
			tipoCatalogo = tcManager.getTipoCatalogoById(catId);
			cat.setTMihTipocatalogo(tipoCatalogo);
			setCatalogos(cManager.getCatalogosTipo(cat));
		}
	}

	/**
	 * Crea un nuevo catalogo, segun el tipo seleccionado
	 */
	public void guardar() {
		cat = new TMihCatalogo();
		try {
			// Valida que se haya seleccionado un tipo de catalogo
			if(tipoCatalogo == null){
				error = "No se seleccionó ningún tipo de catálogo";
			}
			cat.setTMihTipocatalogo(tipoCatalogo);
			List<TMihCatalogo> itemsCatalogo = cManager.getCatalogosTipo(cat);
			boolean repetido = false;
			for (TMihCatalogo item : itemsCatalogo) {
				if (nombreNuevo.equals(item.getDescripcion())) {
					repetido = true;
					break;
				}
			}
			
			if (!repetido) {
				super.clearMessages();
				TMihCatalogoId id = new TMihCatalogoId(
						tipoCatalogo.getCodmaestro(), cManager.getNuevoCodigo(cat));
				
				cat.setId(id);
				cat.setDescripcion(nombreNuevo);
				cat.setFeregistro(new Date());
				cManager.insertCatalogo(cat);
				catalogos.add(cat);
				super.setMessage("catalogos.exito.creacion", BaseBean.INFO, null, null);
				
				/** Guarda el id del TipoCatalogo seleccionado en la sesion */
				ec = FacesContext.getCurrentInstance().getExternalContext();
				ec.getSessionMap().put("catId",
						(int) (tipoCatalogo.getCodmaestro()));
				ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
				
//				FacesContext context = FacesContext.getCurrentInstance();
//		        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Exito", "Nuevo registro almacenado") );
		        
			} else {
				super.setMessage("catalogos.error.repetido", BaseBean.ERROR, null, null);
			}
			
		} catch (Exception e) {
			error = "Error al guardar " + e.getMessage();
			log.error(error, e);
		}
		
		
		
	}

	/**
	 * Elimina el catalogo seleccionado.
	 */
	public void validarEliminar() {
		String tipo; 
		int tipoVal;
		this.eliminado = false; 
		BigDecimal eliminar = new BigDecimal(0);
		
		try {
			tipoVal = catalogo.getTMihTipocatalogo().getCodmaestro();
			
			if (tipoVal == Constants.CAT_TIPO_DOC.intValue()){
				tipo = "TDOC";
			} else if (tipoVal == Constants.CAT_EMPRESAS.intValue()){
				tipo = "EMPR";
		    } else if (tipoVal == Constants.CAT_AREA.intValue()){
		    	tipo = "AREA";
		    } else if (tipoVal == Constants.CAT_CARGO.intValue()){
		    	tipo = "CARG";
		    } else {
		    	tipo = " ";	
		    }
			eliminar = cManager.eliminarCatalogo(catalogo.getTMihTipocatalogo().getCodmaestro(), catalogo.getId().getCodigo(), tipo);
			System.out.println(eliminar.intValue());
			if(eliminar.intValue() == 0){
				this.eliminado = true;
			}
			
		} catch (Exception e) {
			error = "Error al eliminar " + e.getMessage();
		}
	}

	public void eliminar() {
		try {
			
			if (this.eliminado){
				cManager.deleteCatalogo(catalogo);
				this.eliminarRg = false;
				FacesContext context = FacesContext.getCurrentInstance();
		        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,null, "Registro eliminado") );
			}else {
				this.eliminarRg = true;	
				FacesContext context = FacesContext.getCurrentInstance();
		        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,null, "No se pudo eliminar este catálogo debido a que ya está siendo utilizado.") );
			}
			
			cat = new TMihCatalogo();
			cat.setTMihTipocatalogo(tipoCatalogo);
			setCatalogos(cManager.getCatalogosTipo(cat));
			
			
		} catch (Exception e) {
			error = "Error al eliminar " + e.getMessage();
			
	    }
	}
	
	
	/**
	 * Metodo que controla el cambio de tipo de catalogo, para cargar los
	 * catalogos que existen de este tipo.
	 * 
	 * @param e	evento de cambio en la lista de tipo catalogo
	 */
	public void cambioTipoCatalogo(ValueChangeEvent e) {
		tipoCatalogo = (TMihTipocatalogo) e.getNewValue();
		cat = new TMihCatalogo();
		cat.setTMihTipocatalogo(tipoCatalogo);
		setCatalogos(cManager.getCatalogosTipo(cat));
	}

	// Getters y Setters
	public List<TMihCatalogo> getCatalogos() {
		return catalogos;
	}

	public void setCatalogos(List<TMihCatalogo> catalogos) {
		this.catalogos = catalogos;
	}

	public List<TMihTipocatalogo> getTiposCatalogo() {
		return tiposCatalogo;
	}

	public void setTiposCatalogo(List<TMihTipocatalogo> tiposCatalogo) {
		this.tiposCatalogo = tiposCatalogo;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public CatalogoManager getcManager() {
		return cManager;
	}

	public void setcManager(CatalogoManager cManager) {
		this.cManager = cManager;
	}

	public TipoCatalogoManager getTcManager() {
		return tcManager;
	}

	public TMihTipocatalogo getTipoCatalogo() {
		return tipoCatalogo;
	}

	public void setTipoCatalogo(TMihTipocatalogo tipoCatalogo) {
		this.tipoCatalogo = tipoCatalogo;
	}

	public BeanFactory getFactory() {
		return factory;
	}

	public void setFactory(BeanFactory factory) {
		this.factory = factory;
	}

	public void setTcManager(TipoCatalogoManager tcManager) {
		this.tcManager = tcManager;
	}

	public TMihCatalogo getCatalogo() {
		return catalogo;
	}

	public void setCatalogo(TMihCatalogo catalogo) {
		this.catalogo = catalogo;
	}

	public String getNombreNuevo() {
		return nombreNuevo;
	}

	public void setNombreNuevo(String nombreNuevo) {
		this.nombreNuevo = nombreNuevo;
	}

	public TMihCatalogo getCat() {
		return cat;
	}

	public void setCat(TMihCatalogo cat) {
		this.cat = cat;
	}

	public int getPag() {
		return pag;
	}

	public void setPag(int pag) {
		this.pag = pag;
	}

	public ExternalContext getEc() {
		return ec;
	}

	public void setEc(ExternalContext ec) {
		this.ec = ec;
	}

	public boolean isEliminado() {
		return eliminado;
	}

	public void setEliminado(boolean eliminado) {
		this.eliminado = eliminado;
	}


	public boolean isEliminarRg() {
		return eliminarRg;
	}

	public void setEliminarRg(boolean eliminarRg) {
		this.eliminarRg = eliminarRg;
	}

}
