package com.sophos.semih.bean;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sophos.semih.model.Campo;

public class ConsultaDinamicaBeanTest {
	
	private ConsultaDinamicaBean cdb;
	
	public ConsultaDinamicaBeanTest(){
		cdb = new ConsultaDinamicaBean("");
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Dado una lista de campos de la entidad uno, de los cuales uno es un campo clave, y una lista
	 * de campos de la entidad dos, se debe generar un query que haga un join de ambas tablas y en el
	 * resultado muestre �nicamente los campos seleccionados.
	 */
	@Test
	public void testCreateQuery() {
		
		ArrayList<Campo> campose1 = new ArrayList<Campo>();
		
		//Campo clave.
		Campo campo = new Campo();
		campo.setNombreCorto("campo1");
		campo.setClave("Y");
		campose1.add(campo);
		
		campo = new Campo();
		campo.setNombreCorto("campo2");
		campose1.add(campo);
		
		campo = new Campo();
		campo.setNombreCorto("campo3");
		campose1.add(campo);
		
		ArrayList<Campo> campose2 = new ArrayList<Campo>();
		campo = new Campo();
		campo.setNombreCorto("campo1");
		campose2.add(campo);
		
		campo = new Campo();
		campo.setNombreCorto("campo2");
		campose2.add(campo);
		
		String actual = cdb.createQuery("estructura1", "estructura2", campose1, campose2, "fkEntidad2");
		String expected = "Select estructura1.campo1, estructura1.campo2, estructura1.campo3, estructura2.campo1, estructura2.campo2 from estructura1 estructura1 join estructura2 estructura2 on estructura1.campo1 = estructura2.fkEntidad2;";
		
		assertEquals(expected, actual);
	}
	
	/**
	 * Dado una lista de campos de la entidad uno, de los cuales uno es un campo clave, y una lista
	 * de campos de la entidad dos, se debe generar un query que haga un join de ambas tablas y en el
	 * resultado muestre �nicamente los campos seleccionados.
	 */
	@Test
	public void testCreateCompositeQuery() {
		
		ArrayList<Campo> campose1 = new ArrayList<Campo>();
		
		//Campo clave.
		Campo campo = new Campo();
		campo.setNombreCorto("campo1");
		campo.setClave("Y");
		campose1.add(campo);
		
		campo = new Campo();
		campo.setNombreCorto("campo2");
		campose1.add(campo);
		
		campo = new Campo();
		campo.setNombreCorto("campo3");
		campose1.add(campo);
		
		ArrayList<Campo> campose2 = new ArrayList<Campo>();
		campo = new Campo();
		campo.setNombreCorto("campo1");
		campose2.add(campo);
		
		campo = new Campo();
		campo.setNombreCorto("campo2");
		campose2.add(campo);
		
		List<Campo> claveForanea = new ArrayList<Campo>();
		Campo foranea = new Campo();
		foranea.setNombreCorto("campo1");
		claveForanea.add(foranea);
		
		String actual = cdb.createQuery("estructura1", "estructura2", campose1, campose2, claveForanea);
		String expected = "Select estructura1.campo1, estructura1.campo2, estructura1.campo3, estructura2.campo1, estructura2.campo2 from estructura1 estructura1 join estructura2 estructura2 on estructura1.campo1 = estructura2.campo1;";
		
		assertEquals(expected, actual);
	}

}
