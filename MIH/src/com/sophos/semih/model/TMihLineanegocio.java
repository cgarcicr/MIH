package com.sophos.semih.model;

// Generated 9/10/2013 04:13:43 PM by Hibernate Tools 4.0.0

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * TMihLineanegocio generated by hbm2java
 */
@Entity
@Table(name = "T_MIH_LINEANEGOCIO", uniqueConstraints = @UniqueConstraint(columnNames = {
		"NOMBRE", "CODPROYECTO" }))
public class TMihLineanegocio implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7409245375220238706L;
	private int codigo;
	private TMihProyecto TMihProyecto;
	private String nombre;
	private String descripcion;
	private Character estado;
	private Date feregistro;
	private Set<TMihUsuario> TMihUsuarios = new HashSet<TMihUsuario>(0);
	private Set<TMihAplicacion> TMihAplicaciones = new HashSet<TMihAplicacion>(0);
	private static final Log log = LogFactory.getLog(TMihLineanegocio.class);

	public TMihLineanegocio() {
	}

	public TMihLineanegocio(int codigo) {
		this.codigo = codigo;
	}

	public TMihLineanegocio(int codigo, TMihProyecto TMihProyecto,
			String nombre, String descripcion, Character estado,
			Date feregistro, Set<TMihUsuario> TMihUsuarios, Set<TMihAplicacion> TMihAplicaciones) {
		this.codigo = codigo;
		this.TMihProyecto = TMihProyecto;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.estado = estado;
		this.feregistro = feregistro;
		this.TMihUsuarios = TMihUsuarios;
		this.TMihAplicaciones = TMihAplicaciones;
	}

	@Id
	@Column(name = "CODIGO", unique = true, nullable = false, precision = 8, scale = 0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_ln_id")
	@SequenceGenerator(name="seq_ln_id", sequenceName="SEQ_MIH_LINEANEGOCIO",schema="SEMIH",allocationSize=5)
	public int getCodigo() {
		return this.codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CODPROYECTO")
	public TMihProyecto getTMihProyecto() {
		return this.TMihProyecto;
	}

	public void setTMihProyecto(TMihProyecto TMihProyecto) {
		this.TMihProyecto = TMihProyecto;
	}

	@Column(name = "NOMBRE", length = 160)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "DESCRIPCION", length = 1600)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Column(name = "ESTADO", length = 1)
	public Character getEstado() {
		return this.estado;
	}

	public void setEstado(Character estado) {
		this.estado = estado;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "FEREGISTRO", length = 7)
	public Date getFeregistro() {
		return this.feregistro;
	}

	public void setFeregistro(Date feregistro) {
		this.feregistro = feregistro;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "TMihLineanegocio")
	public Set<TMihUsuario> getTMihUsuarios() {
		return this.TMihUsuarios;
	}

	public void setTMihUsuarios(Set<TMihUsuario> TMihUsuarios) {
		this.TMihUsuarios = TMihUsuarios;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "TMihLineanegocio")
	public Set<TMihAplicacion> getTMihAplicaciones() {
		return this.TMihAplicaciones;
	}

	public void setTMihAplicaciones(Set<TMihAplicacion> TMihAplicaciones) {
		this.TMihAplicaciones = TMihAplicaciones;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		try {
			return getNombre();
		} catch (Exception e) {
			log.error("Error en toString()", e);
		}
		return null;
	}

}
