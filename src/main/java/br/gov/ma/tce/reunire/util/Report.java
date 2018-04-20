package br.gov.ma.tce.reunire.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleCsvExporterConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;

public class Report {
	
	public static Properties getProperties(String pathRelatorios, List<?> dados, String relatorio, Map<String, Object> params, String formato) {
		
		try {
			
			InputStream input = new FileInputStream(pathRelatorios + "/" + relatorio);
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(dados);
			JasperPrint jasperPrint = JasperFillManager.fillReport(input, params, dataSource);
			
			String diretorioFinalRelatorio = System.getProperty("java.io.tmpdir");
			
			Properties properties = null;
			
			if (formato.toUpperCase().equals("PDF")) {
				properties = exportarPDF(diretorioFinalRelatorio, jasperPrint);
				
			} else if (formato.toUpperCase().equals("XLS")) {
				properties = exportarXLS(diretorioFinalRelatorio, jasperPrint);
				
			} else if (formato.toUpperCase().equals("CSV")) {
				properties = exportarCSV(diretorioFinalRelatorio, jasperPrint);
				
			} else if (formato.toUpperCase().equals("HTML")) {
				properties = exportarHTML(diretorioFinalRelatorio, jasperPrint);
			}
			
			return properties;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			
		} catch (JRException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static Properties getProperties(String pathRelatorios, Map<String, List<?>> dados, Map<String, Object> params, String formato) {
		
		InputStream input = null;
		JRBeanCollectionDataSource dataSource = null;
		JasperPrint jasperPrintPrincipal = null;
		
		Iterator<Entry<String, List<?>>> iterator = dados.entrySet().iterator();
		
		Map.Entry<String, List<?>> primeiroReport = iterator.next(); 
		
		try {
		
			input = new FileInputStream(pathRelatorios + "/" + primeiroReport.getKey());
			dataSource = new JRBeanCollectionDataSource(primeiroReport.getValue());
			jasperPrintPrincipal = JasperFillManager.fillReport(input, params, dataSource);
			
			while (iterator.hasNext()) {
				
				Map.Entry<String, List<?>> dado = iterator.next();
				
				input = new FileInputStream(pathRelatorios + "/" + dado.getKey());
				dataSource = new JRBeanCollectionDataSource(dado.getValue());
				JasperPrint jasperPrintFilho = JasperFillManager.fillReport(input, params, dataSource);
				
				for (JRPrintPage page : jasperPrintFilho.getPages()) {
					jasperPrintPrincipal.addPage(page);
				}
			}
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			
		} catch (JRException e) {
			e.printStackTrace();
		}
		
		try {
			
			String diretorioFinalRelatorio = System.getProperty("java.io.tmpdir");
			
			Properties properties = null;
			
			if (formato.toUpperCase().equals("PDF")) {
				properties = exportarPDF(diretorioFinalRelatorio, jasperPrintPrincipal);
				
			} else if (formato.toUpperCase().equals("XLS")) {
				properties = exportarXLS(diretorioFinalRelatorio, jasperPrintPrincipal);
				
			} else if (formato.toUpperCase().equals("CSV")) {
				properties = exportarCSV(diretorioFinalRelatorio, jasperPrintPrincipal);
				
			} else if (formato.toUpperCase().equals("HTML")) {
				properties = exportarHTML(diretorioFinalRelatorio, jasperPrintPrincipal);
			}
			
			return properties;
			
		} catch (IOException ex) {
			ex.printStackTrace();
			
		} catch (JRException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private static Properties criarProperties(JasperPrint jasperPrint, String extensao, String formato, File arquivo) {
		
		Properties properties = new Properties();
		
		properties.put("nome", jasperPrint.getName());
		properties.put("extensao", extensao);
		properties.put("formato", formato);
		properties.put("arquivo", arquivo);
		
		return properties;
	}
	
	private static Properties exportarPDF(String path, JasperPrint jasperPrint) throws IOException, JRException {
		
		File file = File.createTempFile(jasperPrint.getName(), ".pdf", new File(path));
		JasperExportManager.exportReportToPdfFile(jasperPrint, file.getAbsolutePath());
		
		Properties properties = criarProperties(jasperPrint, "pdf", "application/pdf", file);
		
		return properties;
	}
	
	private static Properties exportarXLS(String path, JasperPrint jasperPrint) throws IOException, JRException {
		
		File file = File.createTempFile(jasperPrint.getName(), ".xls", new File(path));	
        FileOutputStream fos = new FileOutputStream(file);

		JRXlsExporter exporter = new JRXlsExporter();
		
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(fos));
		exporter.exportReport();
		
		Properties properties = criarProperties(jasperPrint, "xls", "application/vnd.ms-excel", file);
		
		return properties;
	}
	
	private static Properties exportarCSV(String path, JasperPrint jasperPrint) throws IOException, JRException {
		
		File file = File.createTempFile(jasperPrint.getName(), ".csv", new File(path));
		
		FileOutputStream output = new FileOutputStream(file);
		
		JRCsvExporter exporter= new JRCsvExporter();
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleWriterExporterOutput(output));
		
		SimpleCsvExporterConfiguration configuration = new SimpleCsvExporterConfiguration();
		configuration.setWriteBOM(Boolean.TRUE);
		configuration.setRecordDelimiter("\r|");
		exporter.setConfiguration(configuration);
		exporter.exportReport();
		
		Properties properties = criarProperties(jasperPrint, "csv", "application/csv", file);
		
		return properties;
	}
	
	private static Properties exportarHTML(String path, JasperPrint jasperPrint) throws IOException, JRException {
		
		File file = File.createTempFile(jasperPrint.getName(), ".html", new File(path));
		FileOutputStream output = new FileOutputStream(file);
		
		HtmlExporter exporter = new HtmlExporter();
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleHtmlExporterOutput(output));
		
		exporter.exportReport();
		
		Properties properties = criarProperties(jasperPrint, "html", "text/html", file);
		
		return properties;
	}
}