package br.gov.ma.tce.reunire.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import br.gov.ma.tce.reunire.dao.DemonstrativoDao;
import br.gov.ma.tce.reunire.util.Lookup;
import br.gov.ma.tce.reunire.util.Report;

public class RelatorioService {
	
	private Integer ente;
	private Integer orgao;
	private Integer unidadeGestora;
	private Integer poder;
	private Integer exercicio;
	private String diretorioRelatorios;
	
	private Properties properties;
	
	@SuppressWarnings("rawtypes")
	private DemonstrativoDao dao;
	
	@SuppressWarnings("rawtypes")
	public RelatorioService(String diretorioRelatorios, String tipoRelatorio, Integer ente, Integer orgao, Integer unidadeGestora, Integer poder, Integer exercicio) {
		
		this.diretorioRelatorios = diretorioRelatorios;
		this.ente = ente;
		this.orgao = orgao;
		this.unidadeGestora = unidadeGestora;
		this.poder = poder;
		this.exercicio = exercicio;
		this.properties = carregarProperties();
		
		Class<?> classe;
		
		try {
			
			classe = Class.forName(properties.getProperty(tipoRelatorio));
			dao = (DemonstrativoDao) Lookup.dao(classe);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}	
	
	public List<?> recuperarDados() {
		
		try {
			
			List<?> result = dao.recuperaDados(ente, orgao, unidadeGestora, poder, exercicio);
			
			return result;
			
		} catch (Exception ex) {
			return null;
		}
	}
	
	private Properties carregarProperties() {
		
		Properties properties = new Properties();
		
		try {
			
			InputStream is = this.getClass().getClassLoader().getResourceAsStream("META-INF/daoimpl.properties");
			
			properties.load(is);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return properties;
	}

	public Properties getProperties(List<?> dados, String formato) {
		return Report.getProperties(diretorioRelatorios, dados, dao.getNomeRelatorio(), formato);
	}
}