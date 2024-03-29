package com.soriani.securewebapp.dao.categorie;

public interface CategorieDaoQuery {
	
	String readCategorieStatement = "SELECT * FROM CATEGORIE ";

	String readCategoriesGroupBy = "SELECT C.DESCRIZIONE CATEGORIA, C.CODICE CODICECAT, FORMAT(( (COUNT(P.CODICE) / (SELECT COUNT(CODICE) TOTP FROM PROPOSTE_PROGETTUALI WHERE CATEGORIA = CODICECAT)) * 100 ) , 1) AVG FROM CATEGORIE C, PROPOSTE_PROGETTUALI P WHERE C.CODICE = P.CATEGORIA AND P.UTENTE = ? GROUP BY C.CODICE ";

}
