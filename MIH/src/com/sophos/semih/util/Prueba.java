package com.sophos.semih.util;

import java.security.GeneralSecurityException;

public class Prueba {

	public static void main(String[] args) throws GeneralSecurityException {
		
		Utilidades util=new Utilidades();
		
		String clave=util.encrypt("d3c0ntr0lA");
		System.out.println("La clave es: "+clave);
	}

}
