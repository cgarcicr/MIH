package com.sophos.semih.model;

import org.apache.commons.lang.builder.HashCodeBuilder;


public class Campo implements java.io.Serializable {

	private static final long serialVersionUID = -7255700476734794968L;
	private String nombreCorto;
	private String nombreCompleto;
	private String descripcion;
	private String tipoDatoCompleto;
	private String tipoDato;
	private String longitud;
	private String precision;
	private String clave;
	private boolean claveForanea;
	
	public Campo() {
		
	}

	public Campo(String nombreCorto, String nombreCompleto, String descripcion,
			String tipoDatoCompleto, String tipoDato, String longitud,
			String precision, String clave) {		
		this.nombreCorto = nombreCorto;
		this.nombreCompleto = nombreCompleto;
		this.descripcion = descripcion;
		this.tipoDatoCompleto = tipoDatoCompleto;
		this.tipoDato = tipoDato;
		this.longitud = longitud;
		this.precision = precision;
		this.clave = clave;
	}
	
	public Campo(String nombreCorto, String nombreCompleto, String descripcion,
			String tipoDatoCompleto, String tipoDato, String longitud,
			String precision, String clave, boolean claveForanea) {		
		this.nombreCorto = nombreCorto;
		this.nombreCompleto = nombreCompleto;
		this.descripcion = descripcion;
		this.tipoDatoCompleto = tipoDatoCompleto;
		this.tipoDato = tipoDato;
		this.longitud = longitud;
		this.precision = precision;
		this.clave = clave;
	}
	
//	Getters y Setters
	public String getNombreCorto() {
		return nombreCorto;
	}
	public void setNombreCorto(String nombreCorto) {
		this.nombreCorto = nombreCorto;
	}
	public String getNombreCompleto() {
		return nombreCompleto;
	}
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getTipoDatoCompleto() {
		return tipoDatoCompleto;
	}
	public void setTipoDatoCompleto(String tipoDatoCompleto) {
		this.tipoDatoCompleto = tipoDatoCompleto;
	}
	public String getTipoDato() {
		return tipoDato;
	}
	public void setTipoDato(String tipoDato) {
		this.tipoDato = tipoDato;
	}
	public String getLongitud() {
		return longitud;
	}
	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}
	public String getPrecision() {
		return precision;
	}
	public void setPrecision(String precision) {
		this.precision = precision;
	}
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
	public boolean getClaveForanea() {
		return claveForanea;
	}
	public void setClaveForanea(boolean claveForanea) {
		this.claveForanea = claveForanea;
	}
	
	@Override
	public String toString(){
		return this.getNombreCorto();		
	}
	
	@Override
	public boolean equals(Object obj){
		if (obj != null) {
			if ( (((Campo)obj).getNombreCorto()).equals(this.getNombreCorto())  ) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
        return new HashCodeBuilder(17, 31).append(nombreCorto).toHashCode();
    }
}
