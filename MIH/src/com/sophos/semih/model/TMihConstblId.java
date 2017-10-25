package com.sophos.semih.model;

// Generated Dec 11, 2013 4:35:18 PM by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * TMihConstblId generated by hbm2java
 */
@Embeddable
public class TMihConstblId implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1578723270749566832L;
	private int codentidad;
	private int codconsulta;
	private byte sec;
	private static final Log log = LogFactory.getLog(TMihConstblId.class);

	public TMihConstblId() {
	}

	public TMihConstblId(int codentidad, int codconsulta, byte sec) {
		this.codentidad = codentidad;
		this.codconsulta = codconsulta;
		this.sec = sec;
	}

	@Column(name = "CODENTIDAD", nullable = false, precision = 8, scale = 0)
	public int getCodentidad() {
		return this.codentidad;
	}

	public void setCodentidad(int codentidad) {
		this.codentidad = codentidad;
	}

	@Column(name = "CODCONSULTA", nullable = false, precision = 8, scale = 0)
	public int getCodconsulta() {
		return this.codconsulta;
	}

	public void setCodconsulta(int codconsulta) {
		this.codconsulta = codconsulta;
	}

	@Column(name = "SEC", nullable = false, precision = 2, scale = 0)
	public byte getSec() {
		return this.sec;
	}

	public void setSec(byte sec) {
		this.sec = sec;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TMihConstblId))
			return false;
		TMihConstblId castOther = (TMihConstblId) other;

		return (this.getCodentidad() == castOther.getCodentidad())
				&& (this.getCodconsulta() == castOther.getCodconsulta())
				&& (this.getSec() == castOther.getSec());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getCodentidad();
		result = 37 * result + this.getCodconsulta();
		result = 37 * result + this.getSec();
		return result;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		try {
			return String.valueOf(getCodentidad());
		} catch (Exception e) {
			log.error("Error en toString()", e);
		}
		return null;
	}

}