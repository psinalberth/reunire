package br.gov.ma.tce.reunire.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import br.gov.ma.tce.reunire.dao.DemonstrativoDao;
import br.gov.ma.tce.reunire.dao.impl.gestor.ConsultasGestoresImpl;
import br.gov.ma.tce.reunire.model.vo.gestor.EnteVO;
import br.gov.ma.tce.reunire.model.vo.gestor.OrgaoVO;
import br.gov.ma.tce.reunire.model.vo.gestor.PoderVO;
import br.gov.ma.tce.reunire.model.vo.gestor.UnidadeVO;
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Properties getProperties(List<?> dados, String formato) {
		
		Map<String , Object> params = new HashMap<String, Object>();
		
		ConsultasGestoresImpl daoGestores = Lookup.dao(ConsultasGestoresImpl.class);
		
		if (ente != null) {
			params.put("ente", ((EnteVO) daoGestores.byId(EnteVO.class, ente)).getNome().toUpperCase());
		}
		
		if (poder != null) {
			
			PoderVO poderVO = ((PoderVO) daoGestores.byId(PoderVO.class, poder));
			
			params.put("poder", poderVO.getNome().toUpperCase());
			params.put("jurisdicionado", poderVO.getNome().toUpperCase());
		}
		
		if (orgao != null) {
			
			OrgaoVO orgaoVO = (OrgaoVO) daoGestores.byId(OrgaoVO.class, orgao);
			
			params.put("ente", orgaoVO.getEnte().getNome().toUpperCase());
			params.put("orgao", orgaoVO.getNome().toUpperCase());
		}
		
		if (unidadeGestora != null) {
			
			UnidadeVO unidadeVO = (UnidadeVO) daoGestores.byId(UnidadeVO.class, unidadeGestora);
			
			params.put("unidade", unidadeVO.getNome().toUpperCase());
			params.put("orgao", unidadeVO.getOrgao().getNome().toUpperCase());
			params.put("ente", unidadeVO.getOrgao().getEnte().getNome().toUpperCase());
		}
		
		return Report.getProperties(diretorioRelatorios, dados, dao.getNomeRelatorio(), params, formato);
	}
}