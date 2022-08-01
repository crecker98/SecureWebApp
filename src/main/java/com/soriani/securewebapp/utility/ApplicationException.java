package com.soriani.securewebapp.utility;

/**
 * La classe <code>ApplicationException</code> è l'eccezzione 
 * che rappresenta gli errori applicativi
 * 
 * @author christiansoriani
 *
 */
public final class ApplicationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7736694343504825829L;
	
	/**
	 * messaggio da visualizzare
	 */
	private String messaggio;

	public String getMessaggio() {
		return messaggio;
	}

	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
	}
	
	/**
	 * costruttore di default
	 */
	public ApplicationException() {
		super();
	}
	
	/**
	 * costrutture che permette la visualizzazione del messaggio
	 */
	public ApplicationException(String _messaggio) {
		messaggio = _messaggio;
	}
	
}
