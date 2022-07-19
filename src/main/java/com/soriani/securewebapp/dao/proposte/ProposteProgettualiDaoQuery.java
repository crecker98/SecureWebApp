package com.soriani.securewebapp.dao.proposte;

public interface ProposteProgettualiDaoQuery {
	
	final String inserPropostaStatement = "INSERT INTO PROPOSTE_PROGETTUALI(USERNAME, COD_CATEGORIA, FILE, NOME, DESCRIZIONE) VALUES (?, ?, ?, ?, ?)";

}
