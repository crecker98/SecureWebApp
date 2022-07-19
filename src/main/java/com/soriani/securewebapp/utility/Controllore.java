package com.soriani.securewebapp.utility;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;



public class Controllore {
	
	//COSTANTI PER GESTIRE I TIPI DI FILE DA CARICARE
	private static final String JPEG_EXTENSION = "image/jpeg";
	private static final String PNG_EXTENSION = "image/png";
	private static final String TXT_EXTENSION = "text/plain";
	
	/**
	 * metodo che controllo se la stringa non contiene numeri
	 * @param stringa
	 * @return
	 */
	public static boolean isString(String stringa) {
		
		boolean response = false;
		String stringPattern = "^[a-zA-Z]+$";
		Pattern pattern = Pattern.compile(stringPattern);
		Matcher matcher = pattern.matcher(stringa.subSequence(0, stringa.length()));
		response = matcher.find();
		
		return response;
		
	}
	
	/**
	 * metodo che controlla se la stringa è alfanumerica
	 * @param stringa
	 * @return
	 */
	public static boolean isAlfanumericString(String stringa) {
		
		boolean matchFound = false;
		String patternStr = "^[a-zA-Z0-9]+$";
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(stringa.subSequence(0, stringa.length()));
		matchFound = matcher.find();
		
		return matchFound;
		
	}
	
	/**
	 * metodo che controlla se la password rispetta i vincoli di inserimento
	 * @param pwd
	 * @return
	 */
	public static boolean checkPassword(String pwd) {
		
		short score = 0;
		if(pwd.length() >= 8) {

			if(Pattern.compile("[A-Z]").matcher(pwd).find()) {
				score++;
			}
			if(Pattern.compile("\\d").matcher(pwd).find()) {
				score++;
			}
			if(Pattern.compile("[$@$!%*#?&]").matcher(pwd).find()) {
				score++;
			}
		}
		return score >= 3;
		
	}
	
	/**
	 * controlla estensione e dimensione dell'immagine del profilo che viene caricata
	 * @param content
	 * @return
	 * @throws IOException
	 * @throws SAXException
	 * @throws TikaException
	 */
	public static String checkImmagineDelProfilo(InputStream content) throws IOException, SAXException, TikaException{
		
		String response = null;
		BodyContentHandler handler = new BodyContentHandler();
		Metadata metadata = new Metadata();
		
		Parser parser = new AutoDetectParser();
		parser.parse(content, handler, metadata, new ParseContext());
		
		if(Integer.parseInt(metadata.get("File Size").split(" ")[0]) > 1000000) {
			response = "Dimensione dell'immagine troppo grande (max 1MB)";
		}
		if(!metadata.get("Content-Type").equals(JPEG_EXTENSION) && !metadata.get("Content-Type").equals(PNG_EXTENSION)) {
			response = "Il formato dell'immagine non è valido!";
		}
		
		return response;
		
	}
	
	public static String checkFileProposta(InputStream content) throws IOException, SAXException, TikaException{
		
		String response = null;
		BodyContentHandler handler = new BodyContentHandler();
		Metadata metadata = new Metadata();
		
		Parser parser = new AutoDetectParser();
		parser.parse(content, handler, metadata, new ParseContext());
		
		if(Integer.parseInt(metadata.get("File Size").split(" ")[0]) > 1000000) {
			response = "Dimensione della proposta troppo grande (max 1MB)";
		}
		if(!metadata.get("Content-Type").split(";")[0].equals(TXT_EXTENSION)) {
			response = "Il formato del file non è valido!";
		}
		
		return response;
		
	}

}
