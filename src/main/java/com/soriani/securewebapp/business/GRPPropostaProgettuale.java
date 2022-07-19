package com.soriani.securewebapp.business;

import java.util.Vector;

public class GRPPropostaProgettuale extends Vector<PropostaProgettuale> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7728704990983234879L;
	
	/**
	 * Restituisce la proposta progettuale in base alla posizione che gli viene passata
	 * @param indice
	 * @return
	 */
	public PropostaProgettuale getPropostaProgettuale(int indice) {
		return (PropostaProgettuale) this.get(indice);
	}

}
