package br.gov.ma.tce.reunire.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.zkoss.zk.ui.Executions;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class Report {
	
	public static String criarRelatorio(List<?> result, String nomeArquivo, String formatoRelatorio) {
		
		String pathRelatorios = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/_reports");
		
		try {
			
			InputStream is = new FileInputStream(pathRelatorios + "/" + nomeArquivo);
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(result);
			JasperPrint print = JasperFillManager.fillReport(is, null, dataSource);
			
			String pathTemporario = System.getProperty("java.io.tmpdir");
			
			File file = File.createTempFile(print.getName(), ".pdf", new File(pathTemporario));
			JasperExportManager.exportReportToPdfFile(print, file.getAbsolutePath());
			
			return file.getAbsolutePath();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			
		} catch (JRException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static File criarRelatorio(List<?> result, String nomeArquivo) {
		
		String pathRelatorios = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/_reports");
		
		try {
			
			InputStream is = new FileInputStream(pathRelatorios + "/" + nomeArquivo);
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(result);
			JasperPrint print = JasperFillManager.fillReport(is, null, dataSource);
			
			String pathTemporario = System.getProperty("java.io.tmpdir");
			
			File file = File.createTempFile(print.getName(), ".pdf", new File(pathTemporario));
			JasperExportManager.exportReportToPdfFile(print, file.getAbsolutePath());
			
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
	
	private void toCSV() {
		
	}
	
	private void toPDF() {
		
	}
	
	private void toHTML() {
		
	}
	
	private void toExcel() {
		
	}
}
