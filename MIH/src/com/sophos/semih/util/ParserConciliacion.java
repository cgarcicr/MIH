package com.sophos.semih.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
 * Clase para hacer el parse de los archivos de rechazos
 * @author Ricardo José Ramírez Blauvelt
 */
public class ParserConciliacion extends Thread {

	/** Log */
	private static final Log log = LogFactory.getLog(ParserConciliacion.class);
	
	/** Instancia de archivos */
	private String txtInName;

	/** Instancia de Linea de Negocio*/
	private String lineaNegocio;
	
	/** Instancia de Aplicativo */
	private String aplicativo;
	
	/** HashMap de Campos */
	private HashMap<String, String> hashCampos;
	
	/** Instancia de Tabla */
	private String tabla;
	
	/** Instancia de archivos */
	private File txtIn, excelOut;

	/** Instancia de lector */
	private BufferedReader in;

	/** Instancia del buffer para el excel */
	private BufferedOutputStream outExcel;
	
	/** Número de columnas del reporte */
	public static final int COLUMNAS = 2;
	
	/** Formato para números enteros */
	public static final String formatoEnteros = "#,##0";

	private static final String regexp   = "(Total logical records ([A-Za-z]+)[:]\\s+?(\\d+))|(Table\\s+?([A-Za-z0-9_]+)[,]\\s+?([A-Za-z0-9\\s+?]+)[.])"; 
	private static final String regexpEs = "(Total de registros l(.*)gicos (.*)[:]\\s+?(\\d+))|(Tabla\\s+?([A-Za-z0-9_]+)[,.]\\s+?(.*)[.])"; 

	/**
	 * Método de entrada
	 * @param args 
	 */
	public static void main(String[] args) {
		new ParserConciliacion().start();
	}

	/**
	 * Constructor de la clase
	 */
	public ParserConciliacion() {
		
	}

	/**
	 * Hilo de ejecución
	 */
	@Override public void run() {
		try {
			
			// Archivo de entrada
			// txtIn = new File(FILE.......);
			txtIn = new File(txtInName);
			
			// Obtiene el nombre del archivo de entrada
			String nombre = txtIn.getName();
			
			// Busca la extensión
			final int posicionPunto = nombre.lastIndexOf(".");
			if(posicionPunto != -1) {
				final String ext = nombre.substring(posicionPunto);
				nombre = nombre.replaceAll(ext, "");
			}
			
			// Define el archivo de salida
			excelOut = new File(txtIn.getParentFile(), nombre + ".xlsx");
			
			// Crea el buffer de lectura 
			in = new BufferedReader(new FileReader(txtIn));
			
			// Ejecuta el proceso
			ejecutarParser();
			
			// Abre el archivo
			//Desktop.getDesktop().open(excelOut);
			
		} catch (Throwable t) {
			
			log.error("Error al ejecutar el parser", t);

		} finally {
			
			// Cierra el registro de entrada
			if(in != null) {
				try {
					in.close();
				} catch (IOException ioe) {
					log.error("Error al ejecutar el parser de conciliación", ioe);
				}
			}
			
			// Cierra el Excel de salida
			if(outExcel != null) {
				try {
					outExcel.close();
				} catch (IOException ioe) {
					log.error("Error al ejecutar el parser de conciliación", ioe);
				}
			}
		}
	}

	/**
	 * Proceso
	 * @throws Exception 
	 */
	private void ejecutarParser() throws Exception {
		// Inicio
		final long ini = System.currentTimeMillis();

		// Crea el libro de Excel
		final Workbook wb = new XSSFWorkbook();
		
		// Crea la hoja
		final Sheet sheet = wb.createSheet("Detalle de conciliación");

		// Crea la fuente base
		final Font font = wb.createFont();
		font.setFontHeightInPoints((short) 9);
		font.setFontName("Segoe UI");

		// Crea la fuente para encabezados
		final Font fontEnc = wb.createFont();
		fontEnc.setFontHeightInPoints((short) 9);
		fontEnc.setFontName("Segoe UI");
		fontEnc.setBoldweight(Font.BOLDWEIGHT_BOLD);

		// Crea la fuente para el título
		final Font fontTit = wb.createFont();
		fontTit.setFontHeightInPoints((short) 14);
		fontTit.setFontName("Segoe UI");
		fontTit.setBoldweight(Font.BOLDWEIGHT_BOLD);

		// Crea la fuente para las notas
		final Font fontNot = wb.createFont();
		fontNot.setFontHeightInPoints((short) 8);
		fontNot.setFontName("Segoe UI");
		fontNot.setItalic(true);
		fontNot.setColor(IndexedColors.GREY_50_PERCENT.getIndex());

		// Formateador de campos
		final DataFormat df = wb.createDataFormat();

		// Crea el estilo básico
		final CellStyle styleBase = wb.createCellStyle();
		styleBase.setFont(font);
		styleBase.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

		// Crea el estilo básico centrada
		final CellStyle styleBaseCenter = wb.createCellStyle();
		styleBaseCenter.cloneStyleFrom(styleBase);
		styleBaseCenter.setAlignment(CellStyle.ALIGN_CENTER);
		
		// Crea el estilo para notas
		final CellStyle styleNotas = wb.createCellStyle();
		styleNotas.cloneStyleFrom(styleBaseCenter);
		styleNotas.setFont(fontNot);

		// Crea el estilo para encabezados
		final CellStyle styleEncabezado = wb.createCellStyle();
		styleEncabezado.cloneStyleFrom(styleBase);
		styleEncabezado.setFont(fontEnc);
		//styleEncabezado.setAlignment(CellStyle.ALIGN_CENTER);

		// Crea el estilo título
		final CellStyle styleTitulo = wb.createCellStyle();
		styleTitulo.cloneStyleFrom(styleBase);
		styleTitulo.setFont(fontTit);
		styleTitulo.setAlignment(CellStyle.ALIGN_CENTER);

		// Estilo para enteros
		final CellStyle styleEnteros = wb.createCellStyle();
		styleEnteros.setFont(font);
		styleEnteros.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		styleEnteros.setDataFormat(df.getFormat(formatoEnteros));
		
		// Define la expresión regular
		final Pattern p = Pattern.compile(regexp);
		final Pattern pes = Pattern.compile(regexpEs);
		
		// Define el comparador
		final Matcher m = p.matcher("");
		final Matcher mes = pes.matcher("");
		
		// Filas en excel
		int excelRow = 0;
		
		// Título
		final Row filaTitulo = sheet.createRow(excelRow++);
		final Cell celdaTitulo = filaTitulo.createCell(0);
		celdaTitulo.setCellStyle(styleTitulo);
		celdaTitulo.setCellValue("Reporte de Conciliación");
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, COLUMNAS - 1));
		
		// Archivo
		final int rowArchivo = excelRow++;
		final Row filaArchivo = sheet.createRow(rowArchivo);
		final Cell celdaArchivo = filaArchivo.createCell(0);
		celdaArchivo.setCellStyle(styleBaseCenter);
		celdaArchivo.setCellValue("Reporte construido a partir de " + txtIn.getCanonicalPath());
		sheet.addMergedRegion(new CellRangeAddress(rowArchivo, rowArchivo, 0, COLUMNAS - 1));
		
		// Detalles
		final int rowDetalles = excelRow++;
		final Row filaDetalles = sheet.createRow(rowDetalles);
		final Cell celdaDetalles = filaDetalles.createCell(0);
		celdaDetalles.setCellStyle(styleNotas);
		celdaDetalles.setCellValue("Generado el " + DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL).format(new Date()));
		sheet.addMergedRegion(new CellRangeAddress(rowDetalles, rowDetalles, 0, COLUMNAS - 1));
		
		// Fila vacía
		sheet.createRow(excelRow++);
		
		// Fila Linea de Negocio
		final int rowLineaNegocio = excelRow++;
		final Row filaLineaNegocio =  sheet.createRow(rowLineaNegocio);
		final Cell celdaLineaNegocio = filaLineaNegocio.createCell(0);
		celdaLineaNegocio.setCellStyle(styleEncabezado);
		celdaLineaNegocio.setCellValue("Linea de Negocio:");
		final Cell celdaLineaNegocioValue = filaLineaNegocio.createCell(1);
		celdaLineaNegocioValue.setCellValue(lineaNegocio);
		celdaLineaNegocioValue.setCellStyle(styleNotas);
		
		// Fila Aplicativo
		final int rowAplicativo = excelRow++;
		final Row filaAplicativo =  sheet.createRow(rowAplicativo);
		final Cell celdaAplicativo = filaAplicativo.createCell(0);
		celdaAplicativo.setCellStyle(styleEncabezado);
		celdaAplicativo.setCellValue("Aplicativo:");
		final Cell celdaAplicativoValue = filaAplicativo.createCell(1);
		celdaAplicativoValue.setCellValue(aplicativo);
		celdaAplicativoValue.setCellStyle(styleNotas);
		
		// Fila Tabla
		final int rowTabla = excelRow++;
		final Row filaTabla =  sheet.createRow(rowTabla);
		final Cell celdaTabla = filaTabla.createCell(0);
		celdaTabla.setCellStyle(styleEncabezado);
		celdaTabla.setCellValue("Tabla:");
		final Cell celdaTablaValue = filaTabla.createCell(1);
		celdaTablaValue.setCellValue(tabla);
		celdaTablaValue.setCellStyle(styleNotas);
		
		// Fila vacía
		sheet.createRow(excelRow++);
		
		// Encabezado en Excel
		@SuppressWarnings("unused")
		String entidad = null;
		int reg = 0;
		final int filaEncabezado = excelRow++;
		final Row enc = sheet.createRow(filaEncabezado);
		
		// Escribe los encabezados
		int encCol = 0;
		for(String s : Arrays.asList("Concepto", "Resultado")) {
			// Crea la celda
			final Cell c = enc.createCell(encCol++);
			c.setCellStyle(styleEncabezado);
			c.setCellValue(s);
		}
		

		// Lee el archivo línea a línea
		String linea;
		while((linea = in.readLine()) != null) {

			// Crea el comparador
			m.reset(linea);
			mes.reset(linea);
			
			// Compara
			if(!m.lookingAt() && !mes.lookingAt()) {
				continue;
			}
			
			// Lee los datos
			if (m.lookingAt()){
				
				final String proc = m.group(2);
				if (m != null && m.group(3) != null && !m.group(3).isEmpty()){
					reg = Integer.parseInt(m.group(3));
				}
				// Entidad
				if (m != null && m.group(4) != null && !m.group(4).isEmpty()){
					if (linea.matches(m.group(4))){
						entidad = m.group(5);
					}
				}	
	
				// Skipped
				if (m != null && m.group(2) != null && !m.group(2).isEmpty()){
					if (proc.equals("skipped")){
						final Row filaSkipped = sheet.createRow(excelRow++);
						final Cell celdaSkipped = filaSkipped.createCell(0);
						celdaSkipped.setCellStyle(styleBase);
						celdaSkipped.setCellValue("Total de registros omitidos: ");
						
						final Cell celdaSkippedVal = filaSkipped.createCell(1);
						celdaSkippedVal.setCellStyle(styleNotas);
						celdaSkippedVal.setCellValue(reg);	
					}
				}
				// Read
				if (m != null && m.group(2) != null && !m.group(2).isEmpty()){
					if (proc.equals("read")){
						final Row filaRead = sheet.createRow(excelRow++);
						final Cell celdaRead = filaRead.createCell(0);
						celdaRead.setCellStyle(styleBase);
						celdaRead.setCellValue("Total de registros leidos: ");
								
						final Cell celdaReadVal = filaRead.createCell(1);
						celdaReadVal.setCellStyle(styleNotas);
						celdaReadVal.setCellValue(reg);	
					}	
				}
				// Rejected
				if (m != null && m.group(2) != null && !m.group(2).isEmpty()){
					if (proc.equals("rejected")){
						final Row filaRejected = sheet.createRow(excelRow++);
						final Cell celdaRejected = filaRejected.createCell(0);
						celdaRejected.setCellStyle(styleBase);
						celdaRejected.setCellValue("Total de registros rechazados: ");
								
						final Cell celdaRejectedVal = filaRejected.createCell(1);
						celdaRejectedVal.setCellStyle(styleNotas);
						celdaRejectedVal.setCellValue(reg);	
					}
				}
				// Skipped
				if (m != null && m.group(2) != null && !m.group(2).isEmpty()){
					if (proc.equals("discarded")){
						final Row filaDiscarded = sheet.createRow(excelRow++);
						final Cell celdaDiscarded = filaDiscarded.createCell(0);
						celdaDiscarded.setCellStyle(styleBase);
						celdaDiscarded.setCellValue("Total de registros descartados: ");
						
						final Cell celdaDiscardedVal = filaDiscarded.createCell(1);
						celdaDiscardedVal.setCellStyle(styleNotas);
						celdaDiscardedVal.setCellValue(reg);	
					}	
				}
			}
			if(mes.lookingAt()){
				final String proc = mes.group(3);
				if (mes != null && mes.group(4) != null && !mes.group(4).isEmpty()){
					reg = Integer.parseInt(mes.group(4));
				}
				// Entidad
				if (mes != null && mes.group(4) != null && !mes.group(4).isEmpty()){
					if (linea.matches(mes.group(4))){
						entidad = mes.group(5);
					}
				}	
	
				// Skipped
				if (mes != null && mes.group(3) != null && !mes.group(3).isEmpty()){
					if (proc.equals("ignorados")){
						final Row filaSkipped = sheet.createRow(excelRow++);
						final Cell celdaSkipped = filaSkipped.createCell(0);
						celdaSkipped.setCellStyle(styleBase);
						celdaSkipped.setCellValue("Total de registros omitidos: ");
						
						final Cell celdaSkippedVal = filaSkipped.createCell(1);
						celdaSkippedVal.setCellStyle(styleNotas);
						celdaSkippedVal.setCellValue(reg);	
					}
				}
				// Read
				if (mes != null && mes.group(3) != null && !mes.group(3).isEmpty()){
					if (!proc.equals("ignorados")&&!proc.equals("rechazados")&&!proc.equals("desechados")){
						final Row filaRead = sheet.createRow(excelRow++);
						final Cell celdaRead = filaRead.createCell(0);
						celdaRead.setCellStyle(styleBase);
						celdaRead.setCellValue("Total de registros leídos: ");
								
						final Cell celdaReadVal = filaRead.createCell(1);
						celdaReadVal.setCellStyle(styleNotas);
						celdaReadVal.setCellValue(reg);	
					}	
				}
				// Rejected
				if (mes != null && mes.group(3) != null && !mes.group(3).isEmpty()){
					if (proc.equals("rechazados")){
						final Row filaRejected = sheet.createRow(excelRow++);
						final Cell celdaRejected = filaRejected.createCell(0);
						celdaRejected.setCellStyle(styleBase);
						celdaRejected.setCellValue("Total de registros rechazados: ");
								
						final Cell celdaRejectedVal = filaRejected.createCell(1);
						celdaRejectedVal.setCellStyle(styleNotas);
						celdaRejectedVal.setCellValue(reg);	
					}
				}
				// Skipped
				if (mes != null && mes.group(3) != null && !mes.group(3).isEmpty()){
					if (proc.equals("desechados")){
						final Row filaDiscarded = sheet.createRow(excelRow++);
						final Cell celdaDiscarded = filaDiscarded.createCell(0);
						celdaDiscarded.setCellStyle(styleBase);
						celdaDiscarded.setCellValue("Total de registros desechados: ");
						
						final Cell celdaDiscardedVal = filaDiscarded.createCell(1);
						celdaDiscardedVal.setCellStyle(styleNotas);
						celdaDiscardedVal.setCellValue(reg);	
					}	
				}
			}
		}
		
		if (!(hashCampos == null)){
			
			// Fila vacía
			sheet.createRow(excelRow++);
			
			// Fila Tabla
			final int rowSumatoria = excelRow++;
			final Row filaSumatoria =  sheet.createRow(rowSumatoria);
			final Cell celdaSumatoria = filaSumatoria.createCell(0);
			celdaSumatoria.setCellStyle(styleEncabezado);
			celdaSumatoria.setCellValue("Sumatoria");
			
			@SuppressWarnings("rawtypes")
			Iterator iterador = hashCampos.keySet().iterator();  
			   
			while (iterador.hasNext()) {  
			   String key = iterador.next().toString();  
			   String value = hashCampos.get(key).toString();  
	            
				final Row filaCampo = sheet.createRow(excelRow++);
				final Cell celdaCampo = filaCampo.createCell(0);
				celdaCampo.setCellStyle(styleBase);
				celdaCampo.setCellValue(key);
				
				final Cell celdaCampoTotal = filaCampo.createCell(1);
				celdaCampoTotal.setCellStyle(styleNotas);
				celdaCampoTotal.setCellValue(value);
			}
		
		}
		
		// Ajusta el tiempo
		celdaDetalles.setCellValue(celdaDetalles.getStringCellValue() + " en " + (System.currentTimeMillis() - ini) + "ms");
		
		// Ajusta las columnas
		for(int i = 0 ; i < COLUMNAS ; i++) {
			sheet.autoSizeColumn(i);
		}
		
		// Establece los autofiltros
		sheet.setAutoFilter(new CellRangeAddress(filaEncabezado, filaEncabezado, 0, COLUMNAS - 1));
		
		// Crea el buffer de salida
		outExcel = new BufferedOutputStream(new FileOutputStream(excelOut));
		
		// Cierra el libro
		wb.write(outExcel);
	}
	
	public String getTxtInName() {
		return txtInName;
	}

	public void setTxtInName(String txtInName) {
		this.txtInName = txtInName;
	}

	public String getLineaNegocio() {
		return lineaNegocio;
	}

	public void setLineaNegocio(String lineaNegocio) {
		this.lineaNegocio = lineaNegocio;
	}

	public String getAplicativo() {
		return aplicativo;
	}

	public void setAplicativo(String aplicativo) {
		this.aplicativo = aplicativo;
	}

	public String getTabla() {
		return tabla;
	}

	public void setTabla(String tabla) {
		this.tabla = tabla;
	}

	public HashMap<String, String> getHashCampos() {
		return hashCampos;
	}

	public void setHashCampos(HashMap<String, String> hashCampos) {
		this.hashCampos = hashCampos;
	}
	

}
