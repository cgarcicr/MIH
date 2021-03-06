package com.sophos.semih.model;

// Generated 9/10/2013 04:13:43 PM by Hibernate Tools 4.0.0

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
 * TMihEntxusr generated by hbm2java
 */
@Entity
@Table(name = "T_MIH_ENTXUSR")
public class TMihEntxusr implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6502638646589276961L;
	private TMihEntxusrId id;
	private TMihUsuario TMihUsuario;
	private static final Log log = LogFactory.getLog(TMihEntxusr.class);
	
	public TMihEntxusr() {
	}

	public TMihEntxusr(TMihEntxusrId id, TMihUsuario TMihUsuario) {
		this.id = id;
		this.TMihUsuario = TMihUsuario;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "codusuario", column = @Column(name = "CODUSUARIO", nullable = false, precision = 8, scale = 0)),
			@AttributeOverride(name = "codentidad", column = @Column(name = "CODENTIDAD", nullable = false, precision = 8, scale = 0)) })
	public TMihEntxusrId getId() {
		return this.id;
	}

	public void setId(TMihEntxusrId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CODUSUARIO", nullable = false, insertable = false, updatable = false)
	public TMihUsuario getTMihUsuario() {
		return this.TMihUsuario;
	}

	public void setTMihUsuario(TMihUsuario TMihUsuario) {
		this.TMihUsuario = TMihUsuario;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		try {
			return getTMihUsuario().toString();
		} catch (Exception e) {
			log.error("Error en toString()", e);
		}
		return null;
	}
}
