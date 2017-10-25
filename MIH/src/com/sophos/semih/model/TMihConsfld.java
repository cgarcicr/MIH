package com.sophos.semih.model;

// Generated Dec 11, 2013 4:35:18 PM by Hibernate Tools 4.0.0

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * TMihConsfld generated by hbm2java
 */
@Entity
@Table(name = "T_MIH_CONSFLD")
public class TMihConsfld implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1588427747029061721L;
	private TMihConsfldId id;
	private TMihConsulta TMihConsulta;
	private TMihEntidad TMihEntidad;
	private Character llave;
	private static final Log log = LogFactory.getLog(TMihConsfld.class);

	public TMihConsfld() {
	}

	public TMihConsfld(TMihConsfldId id) {
		this.id = id;
	}

	public TMihConsfld(TMihConsfldId id, TMihConsulta TMihConsulta, TMihEntidad TMihEntidad,
			Character llave) {
		this.id = id;
		this.TMihConsulta = TMihConsulta;
		this.TMihEntidad = TMihEntidad;
		this.llave = llave;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "codentidad", column = @Column(name = "CODENTIDAD", nullable = false, precision = 8, scale = 0)),
			@AttributeOverride(name = "codconsulta", column = @Column(name = "CODCONSULTA", nullable = false, precision = 8, scale = 0)),
			@AttributeOverride(name = "campo", column = @Column(name = "CAMPO", nullable = false, length = 128)),
			@AttributeOverride(name = "sec", column = @Column(name = "SEC", nullable = false, precision = 4, scale = 0)),
			@AttributeOverride(name = "sectabla", column = @Column(name = "SECTABLA", nullable = false, precision = 4, scale = 0)) })
	public TMihConsfldId getId() {
		return this.id;
	}

	public void setId(TMihConsfldId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CODCONSULTA", nullable = false, insertable = false, updatable = false)
	public TMihConsulta getTMihConsulta() {
		return this.TMihConsulta;
	}

	public void setTMihConsulta(TMihConsulta TMihConsulta) {
		this.TMihConsulta = TMihConsulta;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CODENTIDAD", nullable = false, insertable = false, updatable = false)
	public TMihEntidad getTMihEntidad() {
		return this.TMihEntidad;
	}

	public void setTMihEntidad(TMihEntidad TMihEntidad) {
		this.TMihEntidad = TMihEntidad;
	}
	
	@Column(name = "LLAVE", length = 1)
	public Character getLlave() {
		return this.llave;
	}

	public void setLlave(Character llave) {
		this.llave = llave;
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		try {
			return String.valueOf(getId().getCodconsulta());
		} catch (Exception e) {
			log.error("Error en toString()", e);
		}
		return null;
	}
	
}
