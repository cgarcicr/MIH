package com.sophos.semih.model;

// Generated 9/10/2013 04:13:43 PM by Hibernate Tools 4.0.0

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * TMihControlcarga generated by hbm2java
 */
@Entity
@Table(name = "T_MIH_CONTROLCARGA")
public class TMihControlcarga implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 690688554274226674L;
	private String codigo;
	private TMihEntidad TMihEntidad;
	private TMihUsuario TMihUsuario;
	private String archivocarga;
	private String archivolog;
	private String archivorechazo;
	private Date fecarga;
	private static final Log log = LogFactory.getLog(TMihControlcarga.class);

	public TMihControlcarga() {
	}

	public TMihControlcarga(String codigo) {
		this.codigo = codigo;
	}

	public TMihControlcarga(String codigo, TMihEntidad TMihEntidad,
			TMihUsuario TMihUsuario, String archivocarga, String archivolog,
			String archivorechazo, Date fecarga) {
		this.codigo = codigo;
		this.TMihEntidad = TMihEntidad;
		this.TMihUsuario = TMihUsuario;
		this.archivocarga = archivocarga;
		this.archivolog = archivolog;
		this.archivorechazo = archivorechazo;
		this.fecarga = fecarga;
	}

	@Id
	@Column(name = "CODIGO", unique = true, nullable = false, length = 8)
	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CODENTIDAD")
	public TMihEntidad getTMihEntidad() {
		return this.TMihEntidad;
	}

	public void setTMihEntidad(TMihEntidad TMihEntidad) {
		this.TMihEntidad = TMihEntidad;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CODUSUARIO")
	public TMihUsuario getTMihUsuario() {
		return this.TMihUsuario;
	}

	public void setTMihUsuario(TMihUsuario TMihUsuario) {
		this.TMihUsuario = TMihUsuario;
	}

	@Column(name = "ARCHIVOCARGA", length = 256)
	public String getArchivocarga() {
		return this.archivocarga;
	}

	public void setArchivocarga(String archivocarga) {
		this.archivocarga = archivocarga;
	}

	@Column(name = "ARCHIVOLOG", length = 256)
	public String getArchivolog() {
		return this.archivolog;
	}

	public void setArchivolog(String archivolog) {
		this.archivolog = archivolog;
	}

	@Column(name = "ARCHIVORECHAZO", length = 256)
	public String getArchivorechazo() {
		return this.archivorechazo;
	}

	public void setArchivorechazo(String archivorechazo) {
		this.archivorechazo = archivorechazo;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "FECARGA", length = 7)
	public Date getFecarga() {
		return this.fecarga;
	}

	public void setFecarga(Date fecarga) {
		this.fecarga = fecarga;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		try {
			return getTMihEntidad().toString();
		} catch (Exception e) {
			log.error("Error en toString()", e);
		}
		return null;
	}
}