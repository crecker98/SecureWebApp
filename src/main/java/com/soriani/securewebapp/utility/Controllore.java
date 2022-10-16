package com.soriani.securewebapp.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import javax.servlet.http.Part;


public final class Controllore {

	public static final String REGEX_FILE_CONTENT="(<script>|<\\/script>|\\.jsp|\\?[a-zA-Z]+=)";
	
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
	 * Check dei metadati del file
	 * @param file
	 * @param contentTypes
	 * @return
	 */
	public static void checkFile(Part file, ArrayList<String> contentTypes) throws ApplicationException {

		ApplicationException exception = new ApplicationException();
		if(!contentTypes.contains(file.getContentType())){
			exception.setMessaggio("Estensione del file non ammessa");
			throw exception;
		}

		InputStream content = null;
		Metadata metadata = new Metadata();
		try {
			content = file.getInputStream();
			AutoDetectParser parser = new AutoDetectParser();
			parser.parse(content, new BodyContentHandler(), metadata, new org.apache.tika.parser.ParseContext());
			content.close();

		}catch (IOException e) {
			e.printStackTrace();
			exception.setMessaggio("Errore nell'elaborazione del file");
			throw exception;
		} catch (SAXException e) {
			e.printStackTrace();
			exception.setMessaggio("Errore nell'elaborazione del file");
			throw exception;
		} catch (TikaException e) {
			e.printStackTrace();
			exception.setMessaggio("Errore nell'elaborazione del file");
			throw exception;
		}

		String parsedContent = metadata.get(Metadata.CONTENT_TYPE);
		for(String type : contentTypes){
			if (!parsedContent.contains(type)){
				exception.setMessaggio("Estensione del file non ammessa");
				throw exception;
			}
			break;
		}

		try{
			if(!checkFileContent(file)){
				exception.setMessaggio("Contenuto del file malevolo!");
			}
		} catch (IOException e){
			e.printStackTrace();
			exception.setMessaggio("Impossibile leggere il contenuto del file");
			throw exception;
		}

	}

	/**
	 * Metodo che controlla il testo contenuto all'interno del file txt.
	 * Viene controllato che non ci sia codice malevolo al suo interno
	 * @param file
	 * @return true se NON � presente codice malevolo, false altrimenti
	 * @throws IOException
	 */
	private static boolean checkFileContent(Part file) throws IOException
	{
		boolean validContent = true;
		InputStream in = file.getInputStream();

		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		while(reader.ready()) {
			String line = reader.readLine();
			Pattern regex = Pattern.compile(REGEX_FILE_CONTENT);
			Matcher m = regex.matcher(line);
			if ( m.find() )
				validContent=false;
		}
		reader.close();
		in.close();
		return validContent;
	}

}
