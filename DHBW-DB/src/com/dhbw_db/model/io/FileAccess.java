/**
 * FileAccess.java Created by jhoppmann on Mar 10, 2013
 */
package com.dhbw_db.model.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

/**
 * The FileAccess class helps perform common file operations by providing a
 * simplified interface
 * 
 * @author jhoppmann
 * @version 0.1
 * @since
 */
public class FileAccess {

	/**
	 * Appends a line to a given file, or is allowed to create a new file if the
	 * old doesn't exist
	 * 
	 * @param file The target file
	 * @param line The line to append
	 * @param create If the file should be created if it doesn't exist already.
	 * @throws IOException If something is wrong with the file system.
	 */
	public static void appendLine(File file, String line, boolean create)
			throws IOException {

		if (!file.exists()) {
			if (create) {
				file.getParentFile()
					.mkdirs();
				file.createNewFile();
			} else
				throw new FileNotFoundException("File " + file.toString()
						+ " not found.");
		}

		BufferedWriter output = new BufferedWriter(new FileWriter(	file,
																	true));
		output.append(line);
		output.newLine();
		output.flush();
		output.close();
	}

	/**
	 * This method loads a text file from the mails subdirectory and returns it
	 * contents
	 * 
	 * @param filename The name of the file to read
	 * @return Returns the read file's contents as a string
	 * @throws IOException If something goes wrong while reading
	 */
	public String loadMailText(String filename) throws IOException {
		InputStream stream = getClass().getResourceAsStream("/mails/"
				+ filename);
		if (stream == null) {
			return "Null stream.";
		}
		byte[] content = new byte[stream.available()];
		stream.read(content);

		return new String(content);
	}
}
