package com.sophos.semih.model;

// Generated 9/10/2013 04:13:43 PM by Hibernate Tools 4.0.0

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * TMihLogs generated by hbm2java
 */
@Entity
@Table(name = "T_MIH_LOGS")
public class TMihLogs implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5186226246162048458L;
	private TMihLogsId id;

	public TMihLogs() {
	}

	public TMihLogs(TMihLogsId id) {
		this.id = id;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "tabla", column = @Column(name = "TABLA", length = 120)),
			@AttributeOverride(name = "usuario", column = @Column(name = "USUARIO", length = 80)),
			@AttributeOverride(name = "accion", column = @Column(name = "ACCION", length = 1)),
			@AttributeOverride(name = "descripcion", column = @Column(name = "DESCRIPCION", length = 1600)),
			@AttributeOverride(name = "feregistro", column = @Column(name = "FEREGISTRO")) })
	public TMihLogsId getId() {
		return this.id;
	}

	public void setId(TMihLogsId id) {
		this.id = id;
	}

}