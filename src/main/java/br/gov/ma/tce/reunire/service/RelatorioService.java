package br.gov.ma.tce.reunire.service;

import java.io.File;
import java.util.List;
import java.util.Properties;

import br.gov.ma.tce.reunire.dao.DemonstrativoDao;
import br.gov.ma.tce.reunire.dao.TipoRelatorioDao;
import br.gov.ma.tce.reunire.dao.impl.TipoRelatorioDaoImpl;
import br.gov.ma.tce.reunire.model.TipoRelatorio;
import br.gov.ma.tce.reunire.util.Lookup;
import br.gov.ma.tce.reunire.util.Report;

public class RelatorioService {
		
	private static final String PATH_DAO_IMPL = "br.gov.ma.tce.reunire.dao.impl.";
	
	static RelatorioService service;
	private TipoRelatorio tipoRelatorio;
	
	public static List<?> recuperarDados(Integer tipoRelatorio, Integer ente, Integer orgao, Integer unidadeGestora, Integer exercicio) {
		
		if (service == null) {
			service = new RelatorioService();
		}
		
		try {
			
			TipoRelatorio tipo = ((TipoRelatorioDao) Lookup.dao(TipoRelatorioDaoImpl.class)).byId(tipoRelatorio);
			
			Class<?> classe = Class.forName(PATH_DAO_IMPL + tipo.getClasse());
			
			@SuppressWarnings("rawtypes")
			DemonstrativoDao dao = (DemonstrativoDao) Lookup.dao(classe);
			
			List<?> result = dao.recuperaDados(ente, orgao, unidadeGestora, exercicio);
			
			return result;
			
		} catch (Exception ex) {
			return null;
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static File gerarArquivo(String pathRelatorios, Integer tipoRelatorio, List<?> result, String formato) {
		
		TipoRelatorio tipo = ((TipoRelatorioDao) Lookup.dao(TipoRelatorioDaoImpl.class)).byId(tipoRelatorio);
		
		Class<?> classe = null;
		DemonstrativoDao dao = null;
		
		try {
			
			classe = Class.forName(PATH_DAO_IMPL + tipo.getClasse());
			dao = (DemonstrativoDao) Lookup.dao(classe);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return Report.criarRelatorio(pathRelatorios, result, dao.getNomeRelatorio(), formato);
	}

	public static Properties getProperties(String pathRelatorios, Integer tipoPecaRelatorio, List<?> dados,
			String formato) {
		
		return Report.getProperties(pathRelatorios, dados, relatorio, formato);
	}
	
	private TipoRelatorio getTipoRelatorio() {
		return tipoRelatorio;
	}
}