package com.soriani.securewebapp.business;

/**
 * @author Christian Soriani
 */
public final class Categoria{

	public Categoria(){

	}
	
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
