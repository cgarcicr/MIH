package com.sophos.semih.bean;

import java.util.ArrayList;
import java.util.List;

import com.sophos.semih.model.Persona;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@ApplicationScoped
public class PersonaBean {
	
	private List<Persona> lista= new ArrayList<>();
	private List<Persona> filtroPersona=new ArrayList<>();
	private String color;
	
	public PersonaBean(){
		lista.add(new Persona("Camilo", 30));
		lista.add(new Persona("John", 24));
		lista.add(new Persona("Paulo", 29));
		lista.add(new Persona("Laura", 34));
		lista.add(new Persona("Deivi", 35));
		lista.add(new Persona("Carmona", 28));
		lista.add(new Persona("Atul", 50));
		this.color="azul claro";
	}


	
	public List<Persona> getLista() {
		return lista;
	}

	public void setLista(List<Persona> lista) {
		this.lista = lista;
	}



	public List<Persona> getFiltroPersona() {
		return filtroPersona;
	}



	public void setFiltroPersona(List<Persona> filtroPersona) {
		this.filtroPersona = filtroPersona;
	}



	public String getColor() {
		return color;
	}



	public void setColor(String color) {
		this.color = color;
	}
	
	


}
