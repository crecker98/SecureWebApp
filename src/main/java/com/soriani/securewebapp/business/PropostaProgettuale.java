package com.soriani.securewebapp.business;

import java.io.Serializable;

public final class PropostaProgettuale implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2094568555308107344L;
	
	private int codice;
	private Categoria categoria;

	private Utente utente;

	private String nome;
	private String descrizione;
	private String dataAggiungta;
	private byte[] file;
	
	public int getCodice() {
		return codice;
	}
	public void setCodice(int codice) {
		this.codice = codice;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getDataAggiungta() {
		return dataAggiungta;
	}
	public void setDataAggiungta(String dataAggiungta) {
		this.dataAggiungta = dataAggiungta;
	}
	public byte[] getFile() {
		return file;
	}
	public void setFile(byte[] file) {
		this.file = file;
	}
	
	

}
