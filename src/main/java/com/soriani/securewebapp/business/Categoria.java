package com.soriani.securewebapp.business;

import java.io.Serializable;

public class Categoria implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4860480427547544483L;
	
	private int codice;
	private String descrizione;
	
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public int getCodice() {
		return codice;
	}
	public void setCodice(int codice) {
		this.codice = codice;
	}

}
