package com.sophos.semih.util;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.LinkedList;

import jcifs.UniAddress;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import jcifs.smb.SmbFileOutputStream;
import jcifs.smb.SmbFilenameFilter;
import jcifs.smb.SmbSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author Reza
 */

public class SmbFunction {

	private UniAddress domain;
	private NtlmPasswordAuthentication authentication;
	private static final Log log = LogFactory.getLog(ExcelUtilities.class);
	private static String error;

	public SmbFunction() {

	}

	/**
	 * 
	 * @param address
	 * @param username
	 * @param password
	 * @throws java.lang.Exception
	 */
	public void login(String address, String username, String password) throws Exception {

		setDomain(UniAddress.getByName(address));
		setAuthentication(new NtlmPasswordAuthentication(address, username, password));
		SmbSession.logon(getDomain(), authentication);

	}

	/**
	 * 
	 * @param path
	 * @return
	 * @throws java.lang.Exception
	 */
	public LinkedList<String> getList(String path) throws Exception {
		LinkedList<String> fList = new LinkedList<String>();
		SmbFile f = new SmbFile(path, authentication);

		SmbFilenameFilter filter = new SmbFilenameFilter() {

			@Override
			public boolean accept(SmbFile file, String name) throws SmbException {
				return name.endsWith(".txt") || name.endsWith(".xml");
			}
		};

		SmbFile[] fArr = f.listFiles(filter);

		for (int a = 0; a < fArr.length; a++) {
			fList.add(fArr[a].getName());
		}

		return fList;
	}

	/**
	 * 
	 * @param path
	 * @return
	 * @throws java.lang.Exception
	 */
	public boolean checkDirectory(String path) throws Exception {
		if (!isExist(path)) {
			return false;
		}

		if (!isDir(path)) {
			return false;
		}

		return true;
	}

	/**
	 * 
	 * @param path
	 * @return
	 * @throws java.lang.Exception
	 */
	public boolean isExist(String path) throws Exception {
		SmbFile sFile = new SmbFile(path, authentication);

		return sFile.exists();
	}

	/**
	 * 
	 * @param path
	 * @return
	 * @throws java.lang.Exception
	 */
	public boolean isDir(String path) throws Exception {
		SmbFile sFile = new SmbFile(path, authentication);

		return sFile.isDirectory();
	}

	/**
	 * 
	 * @param path
	 * @throws java.lang.Exception
	 */
	public void createDir(String path) throws Exception {
		SmbFile sFile = new SmbFile(path, authentication);

		sFile.mkdir();
	}

	// /**
	// *
	// * @param url
	// * @throws java.lang.Exception
	// */
	// public void createFile(String url, SmbFileOutputStream excel) throws
	// Exception
	// {
	// SmbFile sFile = new SmbFile(url, authentication);
	// SmbFileOutputStream out = new SmbFileOutputStream();
	//
	// sFile.mkdir();
	// }

	/**
	 * 
	 * @param path
	 * @throws java.lang.Exception
	 */
	public void delete(String path) throws Exception {
		SmbFile sFile = new SmbFile("smb://" + path, authentication);
		sFile.delete();
	}

	/**
	 * 
	 * @param path
	 * @return
	 * @throws java.lang.Exception
	 */
	public long size(String path) throws Exception {
		SmbFile sFile = new SmbFile(path, authentication);

		return sFile.length();
	}

	/**
	 * 
	 * @param path
	 * @param archivoOut
	 * @param string
	 * @return
	 * @throws java.lang.Exception
	 */
	public String copyFile(String archivoIn, String archivoOut) throws Exception {
		SmbFile sFile = new SmbFile("smb://" + archivoIn, authentication);
		StringBuilder builder = new StringBuilder();
		builder = readFileContent(sFile, builder);
		archivoOut = writeFileContent(archivoOut, builder);
		return archivoOut;
	}

	/**
	 * @throws UnknownHostException
	 * @throws MalformedURLException
	 * 
	 * @param sFile
	 *            builder
	 * @return
	 * @throws SmbException
	 *             , MalformedURLException, UnknownHostExceptio
	 */
	private StringBuilder readFileContent(SmbFile sFile, StringBuilder builder) {

		BufferedReader reader = null;
		try {

			reader = new BufferedReader(new InputStreamReader(new SmbFileInputStream(sFile)));

		} catch (SmbException ex) {
			log.error("Error SmbException()", ex);
			error = ex.getLocalizedMessage();
		} catch (MalformedURLException ex) {
			log.error("Error MalformedURLException()", ex);
			error = ex.getLocalizedMessage();
		} catch (UnknownHostException ex) {
			log.error("Error UnknownHostException()", ex);
			error = ex.getLocalizedMessage();

		}

		String lineReader = null;
		{
			try {

				while ((lineReader = reader.readLine()) != null) {
					builder.append(lineReader).append("\n");
				}

			} catch (IOException exception) {
				log.error("Error UnknownHostException()", exception);
				error = exception.getLocalizedMessage();
			}

			finally {
				try {
					reader.close();
				} catch (IOException ex) {
					log.error("Error UnknownHostException()", ex);
					error = ex.getLocalizedMessage();
				}
			}
		}
		return builder;
	}

	/**
	 * @throws UnknownHostException
	 * @throws MalformedURLException
	 * 
	 * @param sFile
	 *            builder
	 * @return
	 * @throws SmbException
	 *             , MalformedURLException, UnknownHostExceptio
	 */
	public String writeFileContent(String archivoOut, StringBuilder builder) {

		try {

			SmbFile cFile = null;
			try {

				cFile = new SmbFile("smb://" + archivoOut, authentication);
				SmbFileOutputStream out = new SmbFileOutputStream(cFile);
				out.write(String.valueOf(builder).getBytes());
				out.flush();
				out.close();

			} catch (Exception exception) {
				log.error("Error Exception()", exception);
				error = exception.getLocalizedMessage();
			}

			return archivoOut;
		} catch (Exception e) {
			log.error("Error Exception()", e);
			error = e.getLocalizedMessage();
		}
		return null;
	}

	/**
	 * 
	 * @param path
	 * @return
	 * @throws java.lang.Exception
	 */
	public String getFileName(String path) throws Exception {
		SmbFile sFile = new SmbFile(path, authentication);

		return sFile.getName();
	}

	/**
	 * @return the domain
	 */
	public UniAddress getDomain() {
		return domain;
	}

	/**
	 * @param domain
	 *            the domain to set
	 */
	public void setDomain(UniAddress domain) {
		this.domain = domain;
	}

	/**
	 * @return the authentication
	 */
	public NtlmPasswordAuthentication getAuthentication() {
		return authentication;
	}

	/**
	 * @param authentication
	 *            the authentication to set
	 */
	public void setAuthentication(NtlmPasswordAuthentication authentication) {
		this.authentication = authentication;
	}

}

