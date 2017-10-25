package com.sophos.semih.model;

import java.io.Serializable;
import java.util.ArrayList;

public class MenuItem implements Serializable{

	private static final long serialVersionUID = -1132918272421063181L;
	private String titulo;
	private String url;
	private String metodo;
	private ArrayList<MenuItem> hijos;
	
		
	public MenuItem(String titulo, String url, ArrayList<MenuItem> hijos) {
		super();
		this.titulo = titulo;
		this.url = url;
		this.hijos = hijos;
	}
	
	public MenuItem(String titulo, String url) {
		super();
		this.titulo = titulo;
		this.url = url;
	}
	
	public MenuItem() {
		super();
	}
	
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public ArrayList<MenuItem> getHijos() {
		return hijos;
	}
	public void setHijos(ArrayList<MenuItem> hijos) {
		this.hijos = hijos;
	}

	public String getMetodo() {
		return metodo;
	}

	public void setMetodo(String metodo) {
		this.metodo = metodo;
	}
}
