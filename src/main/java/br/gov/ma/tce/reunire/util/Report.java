package br.gov.ma.tce.reunire.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleCsvExporterConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;

public class Report {
	
	public static File criarRelatorio(String pathRelatorios, List<?> result, String nomeArquivo, String formatoRelatorio) {
		
		try {
			
			InputStream is = new FileInputStream(pathRelatorios + "/" + nomeArquivo);
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(result);
			JasperPrint print = JasperFillManager.fillReport(is, null, dataSource);
			
			String pathTemporario = System.getProperty("java.io.tmpdir");
			
			File file = null;
			
			if (formatoRelatorio == null || formatoRelatorio.toUpperCase().equals("PDF")) {
				
				file = File.createTempFile(print.getName(), ".pdf", new File(pathTemporario));
				JasperExportManager.exportReportToPdfFile(print, file.getAbsolutePath());
				
			} else if (formatoRelatorio.toUpperCase().equals("CSV")) {
				
				file = File.createTempFile(print.getName(), ".csv", new File(pathTemporario));
				
				FileOutputStream fos = new FileOutputStream(file);
				
				JRCsvExporter exporter= new JRCsvExporter();
				exporter.setExporterInput(new SimpleExporterInput(print));
				exporter.setExporterOutput(new SimpleWriterExporterOutput(fos));
				
				SimpleCsvExporterConfiguration configuration = new SimpleCsvExporterConfiguration();
				configuration.setWriteBOM(Boolean.TRUE);
				configuration.setRecordDelimiter("\r|");
				exporter.setConfiguration(configuration);
				exporter.exportReport();
				
			} else if (formatoRelatorio.toUpperCase().equals("HTML")) {
				
			} else if (formatoRelatorio.toUpperCase().equals("XLS")) {
				
				file = File.createTempFile(print.getName(), ".xls", new File(pathTemporario));	
		        FileOutputStream fos = new FileOutputStream(file);

				JRXlsExporter exporter = new JRXlsExporter();
				
				exporter.setExporterInput(new SimpleExporterInput(print));
				exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(fos));
				exporter.exportReport();	
			}
			
			return file;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			
		} catch (JRException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
