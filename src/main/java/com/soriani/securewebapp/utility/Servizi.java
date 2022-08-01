package com.soriani.securewebapp.utility;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public final class Servizi {

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
	 * metodo che legge bytes da file
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

}
