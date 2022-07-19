package com.soriani.securewebapp.business;

import java.io.Serializable;

public class Utente implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6808669203666641967L;
	private String username;
	private String nome;
	private String cognome;
	private byte[] immagineProfilo;
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public byte[] getImmagineProfilo() {
		return immagineProfilo;
	}
	public void setImmagineProfilo(byte[] immagineProfilo) {
		this.immagineProfilo = immagineProfilo;
	}
	
	

}
