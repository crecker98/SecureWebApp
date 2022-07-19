package com.soriani.securewebapp.utility;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.Arrays;

import com.soriani.securewebapp.dao.sale.SaleDao;
import com.soriani.securewebapp.dao.utenti.UtentiDao;

public final class Password {
	
	/**
	 * metodo che costruisce la nuova password per l'utente
	 * @param pwd
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public static void setPassword(byte[] pwd, String username) throws NoSuchAlgorithmException, IOException{
			
		byte[] salt = generateSalt(12);
		byte[] newPwd = Servizi.appendArrays(pwd, salt);
		MessageDigest msgDigest = MessageDigest.getInstance("SHA-256");
		byte[] hashVal = msgDigest.digest(newPwd) ;
		Servizi.clearArray(pwd);
		Servizi.clearArray(newPwd);
		Servizi.saveBytes(salt, "salt" + username + ".bin");
		Servizi.saveBytes(hashVal, "password" + username + ".bin");
		Servizi.clearArray(salt);
		Servizi.clearArray(hashVal);
		
	}
	
	/**
	 * metodo che controlla la password inserita al momento del login
	 * @param pwd
	 * @param username
	 * @return
	 * @throws ApplicationException
	 * @throws SQLException
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 */
	public static boolean checkPassword(byte[] pwd, String username) throws ApplicationException, SQLException, IOException, NoSuchAlgorithmException {
		
		byte[] salt = SaleDao.getSaleDao().readSaltFromUsername(username);
		byte[] newPwd = Servizi.appendArrays(pwd, salt);
		MessageDigest msgDigest = MessageDigest.getInstance("SHA-256");
		byte[] hashVal1 = msgDigest.digest(newPwd);
		Servizi.clearArray(pwd);
		Servizi.clearArray(newPwd);
		byte[] hashVal12 = UtentiDao.getUtenteDao().readPasswordFromUsername(username);
		boolean verificato = Arrays.equals(hashVal1, hashVal12);
		Servizi.clearArray(hashVal1);
		Servizi.clearArray(hashVal12);
		return verificato;
		
	}
	
	/**
	 * metodo che genera il sale per la costruzione della password
	 * @param n
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	private static byte[] generateSalt(int n) throws NoSuchAlgorithmException {
		
		byte[] bytes = new byte[20];
		SecureRandom.getInstanceStrong().nextBytes(bytes);
		return bytes;
		
	}
	
	

}
