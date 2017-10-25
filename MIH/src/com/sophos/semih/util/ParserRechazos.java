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
public class ParserRechazos extends Thread {

	/** Log */
	private static final Log log = LogFactory.getLog(ParserRechazos.class);
	
	/** Instancia de archivos */
	private String txtInName;

	/** Instancia de archivos */
	private File txtIn, excelOut;

	/** Instancia de lector */
	private BufferedReader in;

	/** Instancia del buffer para el excel */
	private BufferedOutputStream outExcel;
	
	/** Número de columnas del reporte */
	public static final int COLUMNAS = 6;
	
	/** Formato para números enteros */
	public static final String formatoEnteros = "#,##0";

	/** Expresión regular para interpretar los registros */
	private static final String regexp =   "Record (\\d+): ((Rejected - Error on table ([A-Za-z0-9_]+)[.,]\\s?((column ([A-Za-z0-9_]+)\\.\\s?)?((ORA-(\\d+))?:?\\s?[\\w\\p{L}/@#\\s()\".:-_]+)))|(Discarded - all columns null.))";
	private static final String regexpEs = "Registro (\\d+): ((Rechazado - Error en tabla ([A-Za-z0-9_]+)[.,]\\s?((columna ([A-Za-z0-9_]+)\\.\\s?)?((ORA-(\\d+))?:?\\s?[\\w\\p{L}/@#\\s()\".:-_]+)))|(Desechado - todas las columnas son nulas.))";

	/**
	 * Método de entrada
	 * @param args 
	 */
	public static void main(String[] args) {
		new ParserRechazos().start();
	}

	/**
	 * Constructor de la clase
	 */
	public ParserRechazos() {
		
	}

	/**
	 * Hilo de ejecución
	 */
	@Override public void run() {
		try {
			
			// Archivo de entrada
//			txtIn = new File(FILE.......);
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

			log.error("Error al ejecutar el parser de rechazos", t);

		} finally {
			
			// Cierra el registro de entrada
			if(in != null) {
				try {
					in.close();
				} catch (IOException ioe) {
					log.error("Error al ejecutar el parser de rechazos", ioe);
				}
			}
			
			// Cierra el Excel de salida
			if(outExcel != null) {
				try {
					outExcel.close();
				} catch (IOException ioe) {
					log.error("Error al ejecutar el parser de rechazos", ioe);
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
		final Sheet sheet = wb.createSheet("Detalle de rechazos");

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
		styleEncabezado.setAlignment(CellStyle.ALIGN_CENTER);

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
		celdaTitulo.setCellValue("Registro de rechazos para la tabla: ");
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

		// Resumen
		final Row filaResumen = sheet.createRow(excelRow++);
		
		// Fila vacía
		sheet.createRow(excelRow++);

		// Encabezado en Excel
		final int filaEncabezado = excelRow++;
		final Row enc = sheet.createRow(filaEncabezado);
		
		// Escribe los encabezados
		int encCol = 0;
		for(String s : Arrays.asList("Serial", "Tabla", "Registro", "Error", "Columna", "Descripción")) {
			// Crea la celda
			final Cell c = enc.createCell(encCol++);
			c.setCellStyle(styleEncabezado);
			c.setCellValue(s);
		}
		
		// Lee el archivo línea a línea
		String entidad = null;
		int cont = 0;
		String linea;
		while((linea = in.readLine()) != null) {
			// Incrementa el contador
			cont++;

			// Crea el comparador
			m.reset(linea);
			mes.reset(linea);
			
			// Compara
			if(!mes.lookingAt() && !m.lookingAt()) {
				continue;
			}
			
			if(mes.lookingAt()){
				
			// Lee los datos
				final String entEs = mes.group(4);
				final int regEs = Integer.parseInt(mes.group(1));
				final String errEs = mes.group(9);
				final String colEs = mes.group(7);
				final String desEs = mes.group(11) == null ? mes.group(5) : mes.group(11);
				
				// Crea la fila Excel
				final Row fila = sheet.createRow(excelRow++);
				
				// Serial
				final Cell celdaSer = fila.createCell(0);
				celdaSer.setCellStyle(styleEnteros);
				celdaSer.setCellValue(cont);
				
				// Entidad
				final Cell celdaEnt = fila.createCell(1);
				celdaEnt.setCellStyle(styleBase);
				celdaEnt.setCellValue(entEs);
				
				// Registro
				final Cell celdaReg = fila.createCell(2);
				celdaReg.setCellStyle(styleEnteros);
				celdaReg.setCellValue(regEs);
				
				// Error
				final Cell celdaErr = fila.createCell(3);
				celdaErr.setCellStyle(styleBase);
				celdaErr.setCellValue(errEs);
				
				// Columna
				final Cell celdaCol = fila.createCell(4);
				celdaCol.setCellStyle(styleBase);
				celdaCol.setCellValue(colEs);
				
				// Descripción
				final Cell celdaDes = fila.createCell(5);
				celdaDes.setCellStyle(styleBase);
				celdaDes.setCellValue(desEs);
				
				// Guarda la entidad
				if((entidad == null && entEs != null)) {
					entidad = entEs;
				}
			}
			
			if(m.lookingAt()){
			
			// Lee los datos
				final String ent = m.group(4);
				final int reg = Integer.parseInt(m.group(1));
				final String err = m.group(9);
				final String col = m.group(7);
				final String des = m.group(11) == null ? m.group(5) : m.group(11);
			
				// Crea la fila Excel
				final Row fila = sheet.createRow(excelRow++);
				
				// Serial
				final Cell celdaSer = fila.createCell(0);
				celdaSer.setCellStyle(styleEnteros);
				celdaSer.setCellValue(cont);
				
				// Entidad
				final Cell celdaEnt = fila.createCell(1);
				celdaEnt.setCellStyle(styleBase);
				celdaEnt.setCellValue(ent);
				
				// Registro
				final Cell celdaReg = fila.createCell(2);
				celdaReg.setCellStyle(styleEnteros);
				celdaReg.setCellValue(reg);
				
				// Error
				final Cell celdaErr = fila.createCell(3);
				celdaErr.setCellStyle(styleBase);
				celdaErr.setCellValue(err);
				
				// Columna
				final Cell celdaCol = fila.createCell(4);
				celdaCol.setCellStyle(styleBase);
				celdaCol.setCellValue(col);
				
				// Descripción
				final Cell celdaDes = fila.createCell(5);
				celdaDes.setCellStyle(styleBase);
				celdaDes.setCellValue(des);
				
				// Guarda la entidad
				if((entidad == null && ent != null)) {
					entidad = ent;
				}
			}
		}
		
		// Completa el título
		celdaTitulo.setCellValue(celdaTitulo.getStringCellValue() + entidad);
		
		// Resumen
//		final Row filaResumen = sheet.createRow(excelRow++);
		final Cell celdaLabel = filaResumen.createCell(0);
		celdaLabel.setCellStyle(styleBase);
		celdaLabel.setCellValue("Total");
		final Cell celdaTotal = filaResumen.createCell(1);
		celdaTotal.setCellStyle(styleBase);
		celdaTotal.setCellValue(cont);
		
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

}
