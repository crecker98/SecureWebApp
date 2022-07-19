package com.soriani.securewebapp.business;

import java.util.Vector;

public class GRPCategoria extends Vector<Categoria> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8077874695030749611L;
	
	/**
	 * Restituisce la categoria in base alla posizione che gli viene passata
	 * @param indice
	 * @return
	 */
	public Categoria getCategoria(int indice) {
		return (Categoria) this.get(indice);
	}

}
