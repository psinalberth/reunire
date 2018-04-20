package br.gov.ma.tce.reunire.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import br.gov.ma.tce.reunire.dao.DemonstrativoDao;
import br.gov.ma.tce.reunire.dao.impl.gestor.ConsultasGestoresImpl;
import br.gov.ma.tce.reunire.model.vo.gestor.EnteVO;
import br.gov.ma.tce.reunire.model.vo.gestor.OrgaoVO;
import br.gov.ma.tce.reunire.model.vo.gestor.PoderVO;
import br.gov.ma.tce.reunire.model.vo.gestor.UnidadeVO;
import br.gov.ma.tce.reunire.util.Lookup;
import br.gov.ma.tce.reunire.util.Report;

public class RelatorioService {
	
	private Map<String, Object> params;
	
	private Properties properties;
	
	@SuppressWarnings("rawtypes")
	private DemonstrativoDao dao;
	
	@SuppressWarnings({ "rawtypes" })
	public RelatorioService(Map<String, Object> params) {
		
		this.params = params;
		this.properties = carregarProperties();
		
		try {
			
			if (params.get("tipoRelatorio") instanceof String) {
			
				Class<?> classe = this.getClass().getClassLoader().loadClass(properties.getProperty((String) params.get("tipoRelatorio")));
				dao = (DemonstrativoDao) Lookup.dao(classe);
				
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public List<?> recuperarDados() {
		
		try {
			
			List<?> result = dao.recuperaDados(params);
			
			return result;
			
		} catch (Exception ex) {
			return null;
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map<String, List<?>> recuperarDadosLote() {
		
		String [] tiposRelatorio = (String[]) params.get("tipoRelatorio");
		
		Map<String, List<?>> sources = new TreeMap<String, List<?>>();
		
		for (int i = 0; i < tiposRelatorio.length; i++) {
			
			Class<?> classe;
			try {
				classe = this.getClass().getClassLoader().loadClass(properties.getProperty(tiposRelatorio[i]));
				dao = (DemonstrativoDao) Lookup.dao(classe);
				
				sources.put(dao.getNomeRelatorio(), dao.recuperaDados(params));
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		return sources;
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
	private void preencherParametrosRelatorio(Map<String, Object> params) {
		
		ConsultasGestoresImpl daoGestores = Lookup.dao(ConsultasGestoresImpl.class);
		
		if (params.get("enteId") != null) {
			params.put("ente", ((EnteVO) daoGestores.byId(EnteVO.class, (Integer) params.get("enteId"))).getNome().toUpperCase());
		}
		
		if (params.get("poderId") != null) {
			
			PoderVO poderVO = ((PoderVO) daoGestores.byId(PoderVO.class, (Integer) params.get("poderId")));
			
			params.put("poder", poderVO.getNome().toUpperCase());
			params.put("jurisdicionado", poderVO.getNome().toUpperCase());
		}
		
		if (params.get("orgaoId") != null) {
			
			OrgaoVO orgaoVO = (OrgaoVO) daoGestores.byId(OrgaoVO.class, (Integer) params.get("orgaoId"));
			
			params.put("ente", orgaoVO.getEnte().getNome().toUpperCase());
			params.put("orgao", orgaoVO.getNome().toUpperCase());
		}
		
		if (params.get("unidadeId") != null) {
			
			UnidadeVO unidadeVO = (UnidadeVO) daoGestores.byId(UnidadeVO.class, (Integer) params.get("unidadeId"));
			
			params.put("unidade", unidadeVO.getNome().toUpperCase());
			params.put("orgao", unidadeVO.getOrgao().getNome().toUpperCase());
			params.put("ente", unidadeVO.getOrgao().getEnte().getNome().toUpperCase());
		}
		
		params.put("SUBREPORT_DIR", params.get("reportDir") + "/");
	}
	
	public Properties getProperties(List<?> dados,  Map<String, Object> params, String formato) {
		
		preencherParametrosRelatorio(params);
		
		return Report.getProperties((String) params.get("reportDir"), dados, dao.getNomeRelatorio(), params, formato);
	}
	
	public Properties getProperties(Map<String, List<?>> dados, Map<String, Object> params, String formato) {
		
		preencherParametrosRelatorio(params);
		
		return Report.getProperties((String) params.get("reportDir"), dados, params, formato);
	}
	
	public Map<String, Object> getParams() {
		return params;
	}
	
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
}