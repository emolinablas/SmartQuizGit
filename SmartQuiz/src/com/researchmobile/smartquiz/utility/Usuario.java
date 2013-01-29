package com.researchmobile.smartquiz.utility;

import java.io.Serializable;

public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;
	private static String usuario;
	private static String clave;
	public static String getUsuario() {
		return usuario;
	}
	public static void setUsuario(String usuario) {
		Usuario.usuario = usuario;
	}
	public static String getClave() {
		return clave;
	}
	public static void setClave(String clave) {
		Usuario.clave = clave;
	}

}
