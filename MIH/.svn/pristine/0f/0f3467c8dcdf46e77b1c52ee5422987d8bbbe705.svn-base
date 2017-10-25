package com.sophos.semih.util;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.POIXMLProperties;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 
 * @author Ricardo José Ramírez Blauvelt
 */
public class ExplorarDirectorio {

	/** Columna */
	private int columna;

	/** Fila */
	private int fila;

	/** Hoja */
	private Sheet sheet;

	/** Fuente */
	private Font font, fontDir;

	/** Estilo */
	private CellStyle styleBase, styleDir;
	/** Log para mensajes */
	private static final Log log = LogFactory.getLog(ExplorarDirectorio.class);

	/**
	 * Constructor
	 */
	public ExplorarDirectorio() {

	}

	public Workbook generarIndice(File ruta) {
		try {
			// Integridad
			if (ruta == null || !ruta.isDirectory() || !ruta.canRead()) {
				log.error("La ruta \"" + ruta + "\" no es accesible");
				return null;
			}

			// Explora la ruta y genera el índice
			final Workbook wb = explorar(ruta);

			// Retorna el archivo
			return wb;
		} catch (Exception e) {
			log.error("No se pudo generar índice", e);
		} finally {

		}
		return null;
	}

	/**
	 * Procedimiento que explora un directorio y genera un índice
	 * 
	 * @throws Exception
	 */
	private Workbook explorar(File ruta) throws Exception {
		// Crea el libro de Excel
		final Workbook wb = new XSSFWorkbook();

		// Crea la hoja
		sheet = wb.createSheet("Índice " + ruta.getName());

		// Crea la fuente base
		font = wb.createFont();
		font.setFontHeightInPoints((short) 9);
		font.setFontName("Segoe UI");

		// Fuente para directorios
		fontDir = wb.createFont();
		fontDir.setFontHeightInPoints((short) 9);
		fontDir.setFontName("Segoe UI");
		fontDir.setBoldweight(Font.BOLDWEIGHT_BOLD);

		// Crea el estilo básico
		styleBase = wb.createCellStyle();
		styleBase.setFont(font);
		styleBase.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

		// Crea estilo para directorios
		styleDir = wb.createCellStyle();
		styleDir.cloneStyleFrom(styleBase);
		styleDir.setFont(fontDir);

		// Inicializa
		fila = columna = 0;

		// Raíz
		final Row root = sheet.createRow(fila++);
		final Cell cRoot = root.createCell(columna++);
		cRoot.setCellStyle(styleDir);
		cRoot.setCellValue(ruta.getCanonicalPath());

		// Inicia la iteración
		iterar(ruta.listFiles());

		// Cambia el autor del archivo
		POIXMLProperties xmlProps = ((XSSFWorkbook) wb).getProperties();
		POIXMLProperties.CoreProperties coreProps = xmlProps
				.getCoreProperties();
		coreProps.setCreator("SEMIH - Sophos Banking");

		// Entrega el libro generado
		return wb;
	}

	/**
	 * Procedimiento para iterar sobre la estructura de directorios y extraer la
	 * lista de archivos disponibles
	 * 
	 * @param archivos
	 * @throws java.lang.Exception
	 */
	public void iterar(File[] archivos) throws Exception {
		// Recorre la lista de archivos
		for (File f : archivos) {
			// Si es un directorio, sigue iterando
			if (f.isDirectory()) {
				// Crea el directorio
				final Row dir = sheet.createRow(fila++);
				final Cell cDir = dir.createCell(columna++);
				cDir.setCellStyle(styleDir);
				cDir.setCellValue(f.getName());

				// Itera sobre el directorio
				iterar(f.listFiles());

				// Reduce la columna
				columna--;

			} else {
				// Crea el archivo
				final Row arc = sheet.createRow(fila++);
				final Cell cArc = arc.createCell(columna);
				cArc.setCellStyle(styleBase);
				cArc.setCellValue(f.getName());
			}
		}
	}

}
