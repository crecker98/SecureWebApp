package com.soriani.securewebapp.utility;

import com.soriani.securewebapp.business.CustomCookie;
import com.soriani.securewebapp.business.Utente;
import com.soriani.securewebapp.dao.utenti.UtentiDao;
import com.soriani.securewebapp.web.condivisi.GestoreSessione;
import org.apache.commons.lang3.ArrayUtils;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

public final class Servizi {

	private static final String EMAIL_REGEX = "^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})$";
	private static final String USERNAME_REGEX = "^[a-zA-Z0-9]+$";
	private static final String NAME_REGEX = "^([ \\u00c0-\\u01ffa-zA-Z'\\-])+$";

	/**
	 * metodo che prende in sessione
	 * @param request
	 */
	public static void takePassword(HttpServletRequest request) throws ApplicationException {

		byte[] password = request.getParameter("password").getBytes(StandardCharsets.UTF_8);
		byte[] repeatPassword = request.getParameter("ripeti_password").getBytes(StandardCharsets.UTF_8);

		Controllore.checkPassword(password, repeatPassword);

	}

	/**
	 * metodo che permette di unire due array di byte
	 * 
	 * @param a
	 * @param b
	 * @return
	 * @throws IOException
	 */
	public static byte[] appendArrays(byte[] a, byte[] b) throws IOException {

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		outputStream.write(a);
		outputStream.write(b);
		byte c[] = outputStream.toByteArray();
		return c;
	}

	/**
	 * metodo che pulisce array di byte
	 * 
	 * @param a
	 */
	public static void clearArray(byte[] a) {
		for (int i = 0; i < a.length; i++) {
			a[i] = 0;
		}
	}

	/**
	 * metodo che crea file
	 * 
	 * @param a
	 * @param fileName
	 * @throws IOException
	 */
	public static void saveBytes(byte[] a, String fileName) throws IOException {

		FileOutputStream fos = new FileOutputStream(fileName);
		fos.write(a);
		fos.close();

	}

	/**
	 * metodo che legge bytes da un file
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static byte[] readBytes(String fileName) throws IOException {

		File file = new File(fileName);
		Path path = Paths.get(file.getAbsolutePath());
		byte[] data = Files.readAllBytes(path);
		return data;

	}

	/**
	 * metodo che elimina un file
	 */
	public static void deleteFile(String fileName) {

		File file = new File(fileName);
		file.delete();

	}

	/**
	 * metodo che legge bytes da input stream
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	public static byte[] readAllBytes(InputStream inputStream) throws IOException {
		
		final int bufLen = 4 * 0x400; // 4KB
		byte[] buf = new byte[bufLen];
		int readLen;
		IOException exception = null;

		try {
			try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
				while ((readLen = inputStream.read(buf, 0, bufLen)) != -1)
					outputStream.write(buf, 0, readLen);

				return outputStream.toByteArray();
			}
		} catch (IOException e) {
			exception = e;
			throw e;
		} finally {
			if (exception == null)
				inputStream.close();
			else
				try {
					inputStream.close();
				} catch (IOException e) {
					exception.addSuppressed(e);
				}
		}
		
	}
	
	/**
	 * meotodo che genera un Universally Unique Identifiers
	 * @return
	 */
	public static String generateUUID() {
		
		return UUID.randomUUID().toString();
		
	}

	/**
	 * meotodo che consente di controllare le informazioni dell'acount dell'utente in fase di registrazione e/o di modifica profilo
	 * @param form
	 * @param utente
	 * @return
	 * @throws ApplicationException
	 */
	public static Utente checkInfoUtente(HttpServletRequest request, HashMap<String, String> form, Utente utente) throws ApplicationException {

		if(!Controllore.checkString(form.get("nome"), NAME_REGEX)) {
			throw new ApplicationException("Inserire un nome valido");
		}

		if(!Controllore.checkString(form.get("cognome"), NAME_REGEX)) {
			throw new ApplicationException("Inserire un cognome valido");
		}

		if(!Controllore.checkString(form.get("username"), USERNAME_REGEX)) {
			throw new ApplicationException("Inserire un username valido, non può contenere caratteri speciali!");
		}

		if(!Controllore.checkString(form.get("email"), EMAIL_REGEX)) {
			throw new ApplicationException("Inserire una mail valida, rispettando il formato mail@dominio.it");
		}

		Utente utenteLoggato = GestoreSessione.getUtenteLoggato(request);
		if( (utenteLoggato != null && !form.get("username").equals(utenteLoggato.getUsername())) || request.getParameter("operazione").equals("registration") ) {
			try {

				if(UtentiDao.getUtenteDao().readUtenteFromUsername(form.get("username"))) {
					throw new ApplicationException("Username già in uso. Riprova!");
				}

			}catch(SQLException e) {
				e.printStackTrace();
				throw new ApplicationException("Errore di connessione!");
			}catch(ApplicationException e1) {
				e1.printStackTrace();
				throw new ApplicationException(e1.getMessaggio());
			}
		}

		utente.setNome(form.get("nome"));
		utente.setCognome(form.get("cognome"));
		utente.setUsername(form.get("username"));
		utente.setEmail(form.get("email"));

		return utente;

	}

	public static HashMap<String, String> setDatiFormUtente(HttpServletRequest request) {

		HashMap<String, String> form = new HashMap<String, String>();
		form.put("nome", request.getParameter("nome") != null ? request.getParameter("nome") : "" );
		form.put("cognome", request.getParameter("cognome") != null ? request.getParameter("cognome") : "");
		form.put("username", request.getParameter("username") != null ? request.getParameter("username") : "");
		form.put("email", request.getParameter("email") != null ? request.getParameter("email") : "" );

		return form;

	}

	/**
	 * meotodo che permette di criptare una string mediante l'uso dell'algoritmo AES
	 * @param cookie
	 * @return
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public static CustomCookie encryptAES(String cookie) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

		CustomCookie customCookieToSave = new CustomCookie();

		Cipher cipher = Cipher.getInstance("AES");
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		kgen.init(256);

		SecretKey skey = kgen.generateKey();
		byte[] raw = skey.getEncoded();

		SecretKeySpec secretKeySpec = new SecretKeySpec(raw,  "AES");
		cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

		byte[] encoded = cookie.getBytes(StandardCharsets.UTF_8);

		customCookieToSave.setKey(raw);
		customCookieToSave.setValue(cipher.doFinal(encoded));

		return customCookieToSave;

	}

	/**
	 * meotodo che permette di decriptare una string mediante l'uso dell'algoritmo AES
	 * @param cookie
	 * @return
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws InvalidKeyException
	 */
	public static String decryptAES(CustomCookie cookie) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {

		Cipher cipher = Cipher.getInstance("AES");

		SecretKeySpec secretKeySpec = new SecretKeySpec(cookie.getKey(),  "AES");
		cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

		return new String(cipher.doFinal(cookie.getValue()), StandardCharsets.UTF_8);

	}

}
