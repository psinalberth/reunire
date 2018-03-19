package br.gov.ma.tce.reunire.service;

import java.io.File;
import java.util.List;

import br.gov.ma.tce.reunire.dao.DemonstrativoDao;
import br.gov.ma.tce.reunire.dao.TipoRelatorioDao;
import br.gov.ma.tce.reunire.dao.impl.TipoRelatorioDaoImpl;
import br.gov.ma.tce.reunire.model.TipoRelatorio;
import br.gov.ma.tce.reunire.util.Lookup;
import br.gov.ma.tce.reunire.util.Report;

public class RelatorioService {
	
	private Integer tipoPecaRelatorio;
	private Integer ente;
	private Integer orgao;
	private Integer unidadeGestora;
	private Integer exercicio;
	private String formatoRelatorio;
	
	private static final String PATH_DAO_IMPL = "br.gov.ma.tce.reunire.dao.impl.";
	
	public RelatorioService(Integer tipoPecaRelatorio, Integer ente, Integer orgao, Integer unidadeGestora,
			Integer exercicio, String formatoRelatorio) {
	
		this.tipoPecaRelatorio = tipoPecaRelatorio;
		this.ente = ente;
		this.orgao = orgao;
		this.unidadeGestora = unidadeGestora;
		this.exercicio = exercicio;
		this.formatoRelatorio = formatoRelatorio;
	}

	private TipoRelatorio recuperaTipoRelatorio() {
		return ((TipoRelatorioDao) Lookup.dao(TipoRelatorioDaoImpl.class)).byId(tipoPecaRelatorio);
	}
	
	public File getFile() {
		
		TipoRelatorio tipoRelatorio = recuperaTipoRelatorio();
		
		try {
			
			Class<?> classe = Class.forName(PATH_DAO_IMPL + tipoRelatorio.getClasse());
			
			@SuppressWarnings("rawtypes")
			DemonstrativoDao dao = (DemonstrativoDao) Lookup.dao(classe);
			
			List<?> result = dao.recuperaDados(ente, orgao, unidadeGestora, exercicio);
			
			return Report.criarRelatorio(result, dao.getNomeRelatorio());
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}