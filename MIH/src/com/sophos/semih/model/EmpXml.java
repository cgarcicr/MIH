package com.sophos.semih.model;

// Generated 9/10/2013 04:13:43 PM by Hibernate Tools 4.0.0

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * EmpXml generated by hbm2java
 */
@Entity
@Table(name = "EMP_XML")
public class EmpXml implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5819605857213589033L;
	private EmpXmlId id;

	public EmpXml() {
	}

	public EmpXml(EmpXmlId id) {
		this.id = id;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "id", column = @Column(name = "ID", precision = 22, scale = 0)),
			@AttributeOverride(name = "empname", column = @Column(name = "EMPNAME", length = 40)),
			@AttributeOverride(name = "empsal", column = @Column(name = "EMPSAL", precision = 22, scale = 0)),
			@AttributeOverride(name = "empjoindate", column = @Column(name = "EMPJOINDATE", length = 7)) })
	public EmpXmlId getId() {
		return this.id;
	}

	public void setId(EmpXmlId id) {
		this.id = id;
	}

}