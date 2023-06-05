package com.soriani.securewebapp.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
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

	public static final String PWD_REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$";

	/**
	 * metodo che controllo la conformità della stringa in base alla regola che gli viene passata
	 * @param stringa
	 * @return
	 */
	public static boolean checkString(String stringa, String stringPattern) {

		Pattern pattern = Pattern.compile(stringPattern);
		Matcher matcher = pattern.matcher(stringa.subSequence(0, stringa.length()));
		return matcher.find();

	}
	
	/**
	 * metodo che controlla se la password rispetta i vincoli di inserimento
	 * @param pwd
	 * @return
	 */
	public static void checkPassword(byte[] pwd, byte[] repeatPwd) throws ApplicationException {
		
		boolean ok = false;
		boolean okRepeat = false;
		CharBuffer pw = StandardCharsets.UTF_8.decode(ByteBuffer.wrap(pwd));
		CharBuffer repeatPw = StandardCharsets.UTF_8.decode(ByteBuffer.wrap(repeatPwd));
		ApplicationException ex = new ApplicationException();

		if(!pw.equals(repeatPw)) {
			ex.setMessaggio("Le password non coincidono");
			throw ex;
		}

		if(pw.length() >= 8 && repeatPw.length() >= 8) {

			Pattern pattern = Pattern.compile(PWD_REGEX);
			Matcher matcher = pattern.matcher(pw);
			ok =  matcher.find();

			matcher = pattern.matcher(repeatPw);
			okRepeat = matcher.find();

		}else {
			ex.setMessaggio("Le password non rispettano la lunghezza minima di 8 caratteri");
			throw ex;
		}

		Arrays.fill(pw.array(), ' ');
		Arrays.fill(repeatPw.array(), ' ');

		if(!ok) {
			ex.setMessaggio("Password non conferme ai criteri di approvazione");
			throw ex;
		}

		if(!okRepeat) {
			ex.setMessaggio("Ripeti Password non conferme ai criteri di approvazione");
			throw ex;
		}

		Servizi.clearArray(pwd);
		Servizi.clearArray(repeatPwd);

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

		}catch (IOException | SAXException | TikaException e) {
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
				throw exception;
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
	private static boolean checkFileContent(Part file) throws IOException {
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
