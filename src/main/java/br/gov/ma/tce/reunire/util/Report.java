package br.gov.ma.tce.reunire.util;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.CsvExporterConfiguration;
import net.sf.jasperreports.export.CsvReportConfiguration;
import net.sf.jasperreports.export.Exporter;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.HtmlExporterConfiguration;
import net.sf.jasperreports.export.HtmlExporterOutput;
import net.sf.jasperreports.export.HtmlReportConfiguration;
import net.sf.jasperreports.export.OutputStreamExporterOutput;
import net.sf.jasperreports.export.PdfExporterConfiguration;
import net.sf.jasperreports.export.PdfReportConfiguration;
import net.sf.jasperreports.export.SimpleCsvExporterConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import net.sf.jasperreports.export.WriterExporterOutput;
import net.sf.jasperreports.export.XlsExporterConfiguration;
import net.sf.jasperreports.export.XlsReportConfiguration;

public class Report {
	
	public static Properties getProperties(String pathRelatorios, List<?> dados, String relatorio, Map<String, Object> params, String formato) {
		
		try {
			
			//JRVirtualizer virtualizer = new JRSwapFileVirtualizer(1000, new JRSwapFile(pathRelatorios, 1024, 100), true);
			//params.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);
			
			InputStream input = new FileInputStream(pathRelatorios + "/" + relatorio);
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(dados);
			JasperPrint jasperPrint = JasperFillManager.fillReport(input, params, dataSource);
			
			Properties properties = null;
			
			if (formato.toUpperCase().equals("PDF")) {
				properties = exportarPDF(jasperPrint);
				
			} else if (formato.toUpperCase().equals("XLS")) {
				properties = exportarXLS(jasperPrint);
				
			} else if (formato.toUpperCase().equals("CSV")) {
				properties = exportarCSV(jasperPrint);
				
			} else if (formato.toUpperCase().equals("HTML")) {
				properties = exportarHTML(jasperPrint);
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
			
			Properties properties = null;
			
			if (formato.toUpperCase().equals("PDF")) {
				properties = exportarPDF(jasperPrintPrincipal);
				
			} else if (formato.toUpperCase().equals("XLS")) {
				properties = exportarXLS(jasperPrintPrincipal);
				
			} else if (formato.toUpperCase().equals("CSV")) {
				properties = exportarCSV(jasperPrintPrincipal);
				
			} else if (formato.toUpperCase().equals("HTML")) {
				properties = exportarHTML(jasperPrintPrincipal);
			}
			
			return properties;
			
		} catch (IOException ex) {
			ex.printStackTrace();
			
		} catch (JRException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private static Properties criarProperties(JasperPrint jasperPrint, String extensao, String formato, ByteArrayOutputStream arquivo) {
		
		Properties properties = new Properties();
		
		properties.put("nome", jasperPrint.getName());
		properties.put("extensao", extensao);
		properties.put("formato", formato);
		properties.put("arquivo", arquivo);
		
		return properties;
	}
	
	private static Properties exportarPDF(JasperPrint jasperPrint) throws IOException, JRException {
		
		ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
		
		Exporter<ExporterInput, PdfReportConfiguration, PdfExporterConfiguration, OutputStreamExporterOutput> exporter = new JRPdfExporter();
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(arrayOutputStream));
		exporter.exportReport();
		arrayOutputStream.close();
		
		Properties properties = criarProperties(jasperPrint, "pdf", "application/pdf", arrayOutputStream);
		
		return properties;
	}
	
	private static Properties exportarXLS(JasperPrint jasperPrint) throws IOException, JRException {
		
		ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();

		Exporter<ExporterInput, XlsReportConfiguration, XlsExporterConfiguration, OutputStreamExporterOutput> exporter = new  JRXlsExporter();
			
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(arrayOutputStream));
		exporter.exportReport();
		arrayOutputStream.close();
		
		Properties properties = criarProperties(jasperPrint, "xls", "application/vnd.ms-excel", arrayOutputStream);
		
		return properties;
	}
	
	private static Properties exportarCSV(JasperPrint jasperPrint) throws IOException, JRException {
		
		ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
		
		Exporter<ExporterInput, CsvReportConfiguration, CsvExporterConfiguration, WriterExporterOutput> exporter = new JRCsvExporter();
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleWriterExporterOutput(arrayOutputStream));
		SimpleCsvExporterConfiguration configuration = new SimpleCsvExporterConfiguration();
		configuration.setWriteBOM(Boolean.TRUE);
		configuration.setRecordDelimiter(",");
		exporter.setConfiguration(configuration);
		exporter.exportReport();
		arrayOutputStream.close();
		
		Properties properties = criarProperties(jasperPrint, "csv", "application/csv", arrayOutputStream);
		
		return properties;
	}
	
	private static Properties exportarHTML(JasperPrint jasperPrint) throws IOException, JRException {
		
		ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
		Exporter<ExporterInput, HtmlReportConfiguration, HtmlExporterConfiguration, HtmlExporterOutput> exporter = new HtmlExporter();
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleHtmlExporterOutput(arrayOutputStream));
		
		exporter.exportReport();
		arrayOutputStream.close();
		
		Properties properties = criarProperties(jasperPrint, "html", "text/html", arrayOutputStream);
		
		return properties;
	}
}