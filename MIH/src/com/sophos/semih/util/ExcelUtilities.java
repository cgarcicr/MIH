package com.sophos.semih.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import oracle.sql.TIMESTAMP;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.jsf.FacesContextUtils;

import com.sophos.semih.common.Constants;
import com.sophos.semih.model.TMihParametro;
import com.sophos.semih.service.ParametroManager;
import com.sun.rowset.CachedRowSetImpl;

public class ExcelUtilities {
	
	private static final Log log = LogFactory.getLog(ExcelUtilities.class);
	private ParametroManager paramManager;
	private BeanFactory factory;
	
	
	
	public ExcelUtilities() {
		this.factory = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
		this.paramManager = (ParametroManager) factory.getBean("parametroManager"); 
	}


	/**
	 * Permite generar un archivo de excel utilizando los registros ingresados.
	 * 
	 * @param registros			Registros a ingresar en el excel
	 * @param nombreArchivo		Nombre del archivo generado
	 * @return					
	 */
	public boolean generarExcel(CachedRowSetImpl registros, String nombreArchivo) {

		boolean sinDatos = false;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		OutputStream ops = null;
		try {
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet(nombreArchivo);
			XSSFRow encabezado = sheet.createRow(0);

			rs = registros.getOriginal();
			rsmd = rs.getMetaData();
			int numColumnas = rsmd.getColumnCount();
			
			// Se pone el encabezado
			for (int i = 0; i < numColumnas; i++) {
				XSSFCell cell = encabezado.createCell(i);
				cell.setCellValue(rsmd.getColumnLabel(i + 1));
			}

			// Se escriben los registros
			int numFila = 1;
			while (rs.next()) {
				XSSFRow row = sheet.createRow(numFila);
				for (int i = 0; i < numColumnas; i++) {
					if (rs.getObject(i+1) instanceof TIMESTAMP) {
						TIMESTAMP fecha = (TIMESTAMP)rs.getObject(i + 1);
						row.createCell(i).setCellValue(fecha.stringValue());
					} else {
						row.createCell(i).setCellValue(rs.getString(i + 1));
					}
				}
				numFila++;
			}
			
			if (numFila == 1)
				sinDatos = true;
			else {

				FacesContext facesContext = FacesContext.getCurrentInstance();
				ExternalContext externalContext = facesContext.getExternalContext();
				externalContext.setResponseContentType("application/vnd.ms-excel");
				externalContext.setResponseHeader("Content-Disposition", "attachment; filename=" + nombreArchivo + ".xlsx");
				ops = externalContext.getResponseOutputStream();
				workbook.write(ops);
				facesContext.responseComplete();
			}

		} catch (Exception e) {
			
			log.error("Error al generar el archivo de Excel", e);
		} finally {
						
			try {
				if (ops != null) {
					ops.close();
				}
			} catch (IOException e) {
				log.error("Error al generar el archivo de Excel", e);
			}
			try {
				if (registros != null) {
					registros.close();
				}
			} catch (SQLException e) {
				log.error("Error al generar el archivo de Excel", e);
			}
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				log.error("Error al generar el archivo de Excel", e);
			}
		}

		return sinDatos;
	}

	
	/**
	 * Permite generar un archivo de excel utilizando los registros ingresados.
	 * 
	 * @param registros			Registros a ingresar en el excel
	 * @param nombreArchivo		Nombre del archivo generado
	 * @return					
	 */
	public boolean generarExcelDisco(CachedRowSetImpl registros, String nombreArchivo) {
		
		boolean sinDatos = false;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		FileOutputStream elFichero = null;
		try {
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet(Constants.HOJA_EXPORTAR);
			XSSFRow encabezado = sheet.createRow(0);
			
			rs = registros.getOriginal();
			rsmd = rs.getMetaData();
			int numColumnas = rsmd.getColumnCount();
			CommandExecuter.setSession();
			
			// Se pone el encabezado
			for (int i = 0; i < numColumnas; i++) {
				XSSFCell cell = encabezado.createCell(i);
				cell.setCellValue(rsmd.getColumnLabel(i + 1));
			}
			
			// Se escriben los registros
			int numFila = 1;
			while (rs.next()) {
				XSSFRow row = sheet.createRow(numFila);
				for (int i = 0; i < numColumnas; i++) {
					if (rs.getObject(i+1) instanceof TIMESTAMP) {
						TIMESTAMP fecha = (TIMESTAMP)rs.getObject(i + 1);
						row.createCell(i).setCellValue(fecha.stringValue());
					} else {
						row.createCell(i).setCellValue(rs.getString(i + 1));
					}
				}
				numFila++;
			}
			
			CommandExecuter.resetSession();
			
			if (numFila == 1)
				sinDatos = true;
			else {
				try {
					TMihParametro rutaSalida = paramManager.getParametroById("RUTA_SALIDA");
					File ruta = new File(rutaSalida.getValor());
					if (!ruta.isDirectory()) {
						ruta.mkdir();
					}
					
					elFichero = new FileOutputStream(rutaSalida.getValor() + File.separatorChar + nombreArchivo + ".xlsx");
					workbook.write(elFichero);
				} catch (Exception e) {
					
					//TEST_INICIO
					String rutaSalida = "C:\\\\salida_excel";
					File ruta = new File(rutaSalida);
					if (!ruta.isDirectory()) {
						ruta.mkdir();
					}
					elFichero = new FileOutputStream(rutaSalida + File.separatorChar + nombreArchivo + ".xlsx");
					workbook.write(elFichero);
					//TEST_FIN
					
					log.error("Error al generar el archivo de Excel", e);
				}
			}
			
		} catch (Exception e) {
			log.error("Error al generar el archivo de Excel", e);
		} finally {
			try {
				if (registros != null) {
					registros.close();
				}
			} catch (SQLException e) {
				log.error("Error al generar el archivo de Excel", e);
			}
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				log.error("Error al generar el archivo de Excel", e);
			}
			try {
				if (elFichero != null) {
					elFichero.close();
				}
			} catch (IOException e) {
				log.error("Error al generar el archivo de Excel", e);
			}
		}
		
		return sinDatos;
	}

	/**
	 * Permite añadir lineas al archivo de excel especificado.
	 * 
	 * @param registros			Registros a ingresar en el excel.
	 * @param nombreArchivo		Nombre del archivo generado.
	 * @param filaInicio 		Fila a partir de la cual se va a llenar el archivo.
	 * @return					
	 */
	public boolean llenarExcel(CachedRowSetImpl registros, String nombreArchivo, int filaInicio) {
		boolean sinDatos = false;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		FileOutputStream elFichero = null;
		try {
			
			TMihParametro rutaSalida = paramManager.getParametroById("RUTA_SALIDA");
			String rutaArchivo = rutaSalida.getValor() + File.separatorChar + nombreArchivo + ".xlsx";
			
			//TEST_INICIO
			File ruta = new File(rutaArchivo);
			if (!ruta.isFile()) {
				rutaArchivo = "C:\\\\salida_excel" + File.separatorChar + nombreArchivo + ".xlsx";
			}
			//TEST_FIN
			
			FileInputStream fileInputStream = new FileInputStream(rutaArchivo);
			Workbook workbook = new SXSSFWorkbook(100);
			workbook = WorkbookFactory.create(fileInputStream); 
			
			Sheet sheet = workbook.getSheet(Constants.HOJA_EXPORTAR);
			rs = registros.getOriginal();
			rsmd = rs.getMetaData();
			int numColumnas = rsmd.getColumnCount();
			CommandExecuter.setSession();
			
			// Se escriben los registros
			while (rs.next()) {
				Row row = sheet.createRow(filaInicio);
				for (int i = 0; i < numColumnas; i++) {
					if (rs.getObject(i+1) instanceof TIMESTAMP) {
						TIMESTAMP fecha = (TIMESTAMP)rs.getObject(i + 1);
						row.createCell(i).setCellValue(fecha.stringValue());
					} else {
						row.createCell(i).setCellValue(rs.getString(i + 1));
					}
				}
				filaInicio++;
			}
			
			// Se salva el libro.
	        try {
	            elFichero = new FileOutputStream(rutaArchivo);
	            workbook.write(elFichero);
	        } catch (Exception e) {
	        	
	        	log.error("Error al añadir filas al archivo de Excel especificado.", e);
	        }
			
			CommandExecuter.resetSession();
		} catch (Exception e) {
			
			log.error("Error al añadir filas al archivo de Excel especificado.", e);
		} finally {
			try {
				if (registros != null) {
					registros.close();
				}
			} catch (SQLException e) {
				log.error("Error al generar el archivo de Excel", e);
			}
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				log.error("Error al generar el archivo de Excel", e);
			}
			try {
				if (elFichero != null) {
					elFichero.close();
				}
			} catch (IOException e) {
				log.error("Error al generar el archivo de Excel", e);
			}
		}
		
		return sinDatos;
	}
	
	
	public boolean llenarExcel2(CachedRowSetImpl registros, String nombreArchivo, int filaInicio) {
		boolean sinDatos = false;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		FileOutputStream elFichero = null;
		FileInputStream fileInputStream = null;
		try {
			
			TMihParametro rutaSalida = paramManager.getParametroById("RUTA_SALIDA");
			String rutaArchivo = rutaSalida.getValor() + File.separatorChar + nombreArchivo + ".xlsx";
			
			//TEST_INICIO
			File ruta = new File(rutaArchivo);
			if (!ruta.isFile()) {
				rutaArchivo = "C:\\\\salida_excel" + File.separatorChar + nombreArchivo + ".xlsx";
			}
			//TEST_FIN
			
			fileInputStream = new FileInputStream(rutaArchivo);
			XSSFWorkbook wb_template = new XSSFWorkbook(fileInputStream);
			
			SXSSFWorkbook workbook = new SXSSFWorkbook(wb_template);
			
			SXSSFSheet sheet = (SXSSFSheet) workbook.getSheet(Constants.HOJA_EXPORTAR);
			sheet.setRandomAccessWindowSize(100);
			rs = registros.getOriginal();
			rsmd = rs.getMetaData();
			int numColumnas = rsmd.getColumnCount();
			CommandExecuter.setSession();
			
			// Se escriben los registros
			while (rs.next()) {
				Row row = sheet.createRow(filaInicio);
				for (int i = 0; i < numColumnas; i++) {
					if (rs.getObject(i+1) instanceof TIMESTAMP) {
						TIMESTAMP fecha = (TIMESTAMP)rs.getObject(i + 1);
						row.createCell(i).setCellValue(fecha.stringValue());
					} else {
						row.createCell(i).setCellValue(rs.getString(i + 1));
					}
				}
				filaInicio++;
			}
			
			// Se salva el libro.
            elFichero = new FileOutputStream(rutaArchivo);
            workbook.write(elFichero);
			
			CommandExecuter.resetSession();
		} catch (Exception e) {
			
			log.error("Error al añadir filas al archivo de Excel especificado.", e);
		} finally {
			try {
				if (registros != null) {
					registros.close();
				}
			} catch (SQLException e) {
				log.error("Error al generar el archivo de Excel", e);
			}
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				log.error("Error al generar el archivo de Excel", e);
			}
			try {
				if (elFichero != null) {
					elFichero.close();
				}
			} catch (IOException e) {
				log.error("Error al generar el archivo de Excel", e);
			}
			try {
				if (fileInputStream != null) {
					fileInputStream.close();
				}
			} catch (IOException e) {
				log.error("Error al generar el archivo de Excel", e);
			}
		}
		
		return sinDatos;
	}

	public SXSSFWorkbook llenarExcel3(CachedRowSetImpl registros, String nombreArchivo, int filaInicio, SXSSFWorkbook workbook, String rutaArchivo) {
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		FileOutputStream fos = null;
		FileInputStream fis = null;
		try {
			
			SXSSFSheet sheet = (SXSSFSheet) workbook.getSheet(Constants.HOJA_EXPORTAR);
			sheet.setRandomAccessWindowSize(100);
			rs = registros.getOriginal();
			rsmd = rs.getMetaData();
			int numColumnas = rsmd.getColumnCount();
			
			// Se escriben los registros
			while (rs.next()) {
				Row row = sheet.createRow(filaInicio);
				for (int i = 0; i < numColumnas; i++) {
					if (rs.getObject(i+1) instanceof TIMESTAMP) {
						TIMESTAMP fecha = (TIMESTAMP)rs.getObject(i + 1);
						row.createCell(i).setCellValue(fecha.stringValue());
					} else {
						row.createCell(i).setCellValue(rs.getString(i + 1));
					}
				}
				filaInicio++;
			}
			return workbook;
		} catch (Exception e) {
			log.error("Error al añadir filas al archivo de Excel especificado.", e);
		} finally {
			try {
				if (registros != null) {
					registros.close();
				}
			} catch (SQLException e) {
				log.error("Error al generar el archivo de Excel", e);
			}
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				log.error("Error al generar el archivo de Excel", e);
			}
			try {
				if (fos != null) {
					fos.flush();
					fos.close();
				}
			} catch (IOException e) {
				log.error("Error al generar el archivo de Excel", e);
			}
			try {
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				log.error("Error al generar el archivo de Excel", e);
			}
		}
		
		return null;
	}
	
	/**
	 * Permite descargar el archivo especificado
	 * 
	 * @param archivo			Nombre del archivo
	 * @param rutaArchivo		Ruta absoluta del archivo en el servidor
	 * @param eliminarArchivo	Flag para indicar si se debe borrar el archivo luego de ser descargado.
	 */
	public void descargarArchivo(String archivo, String rutaArchivo, boolean eliminarArchivo) {
		File file = new File(rutaArchivo);
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

		ServletOutputStream out = null;
		FileInputStream input = null;
		
		
		try {
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment;filename=" + archivo + ".xlsx");
			response.setContentLength((int) file.length());

			input = new FileInputStream(file);
			byte[] buffer = new byte[1024];
			out = response.getOutputStream();
			int i = 0;
			while ((i = input.read(buffer)) != -1) {
				out.write(buffer);
				out.flush();
			}
			FacesContext.getCurrentInstance().getResponseComplete();

			//Se elimina el archivo
			if (eliminarArchivo) {
				file.delete();
			}

		} catch (IOException err) {
			log.error("Error al intentar descargar el archivo", err);
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException err) {
				log.error("Error al intentar descargar el archivo", err);
			}
			try {
				if (input != null) {
					input.close();
				}
			} catch (IOException err) {
				log.error("Error al intentar descargar el archivo", err);
			}
		}
	}
	
}
