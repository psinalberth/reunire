package br.gov.ma.tce.reunire.dao.impl.bo;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import br.gov.ma.tce.reunire.dao.DemonstrativoDao;
import br.gov.ma.tce.reunire.dao.impl.PrestacaoDaoImpl;
import br.gov.ma.tce.reunire.model.vo.bo.RelatorioB01VO;
import br.gov.ma.tce.reunire.model.vo.bo.RelatorioB02VO;
import br.gov.ma.tce.reunire.model.vo.bo.RelatorioB03VO;
import br.gov.ma.tce.reunire.model.vo.gestor.UnidadeVO;

@Stateless
public class RelatorioB01DaoImpl extends PrestacaoDaoImpl<RelatorioB01VO> implements DemonstrativoDao<RelatorioB01VO> {
	
	@Override
	public String getNomeRelatorio() {
		return "relatoriobo2.jasper";
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RelatorioB01VO> recuperaDados(Map<String, Object> params) {
		
		List<UnidadeVO> listaUnidades = recuperarUnidades(params);
		List<Integer> listaIdsUnidades = extrairIds(listaUnidades);
		
		String sql = null;
		
		try {
			sql = new String(Files.readAllBytes(Paths.get(this.getClass().getResource("relatoriobo01.sql").getFile())));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		List<Object[]> rows = entityManager.createNativeQuery(sql).setParameter("unidades", listaIdsUnidades).getResultList();
		
		List<RelatorioB01VO> dados = new ArrayList<RelatorioB01VO>(rows.size());
		
		for (Object[] row : rows) {
			
			RelatorioB01VO dado = new RelatorioB01VO();
			
			dado.setTotal(String.valueOf(row[0]));
			dado.setTotalCategoria(String.valueOf(row[1]));
			dado.setCategoria(String.valueOf(row[2]));
			dado.setOrigem(String.valueOf(row[3]));
			dado.setEspecie(String.valueOf(row[4]));
			dado.setPrevisaoInicial(row[5] != null ? new BigDecimal(row[5].toString()) : null);
			dado.setPrevisaoAtualizada(row[6] != null ? new BigDecimal(row[6].toString()) : null);
			dado.setReceitasRealizadas(row[7] != null ? new BigDecimal(row[7].toString()) : null);
			
			dados.add(dado);
		}
		
		List<RelatorioB02VO> dadosPagina2 = recuperaDadosPagina2(listaIdsUnidades);
		params.put("DATA2", dadosPagina2);
		
		List<RelatorioB03VO> dadosPagina3 = recuperarDadosPagina3(listaIdsUnidades);
		params.put("DATA3", dadosPagina3);
		
		List<RelatorioB03VO> dadosPagina4 = recuperarDadosPagina4(listaIdsUnidades);
		params.put("DATA4", dadosPagina4);
		
		BigDecimal totalPrevisaoInicial = dados.stream().filter(d -> d.getPrevisaoInicial() != null)
				.map(RelatorioB01VO::getPrevisaoInicial).reduce(BigDecimal.ZERO, BigDecimal::add);
		
		BigDecimal totalDotacaoInicial = dadosPagina2.stream().filter(d -> d.getDotacaoInicial() != null)
				.map(RelatorioB02VO::getDotacaoInicial).reduce(BigDecimal.ZERO, BigDecimal::add);
		
		BigDecimal totalPrevisaoAtualizada = dados.stream().filter(d -> d.getPrevisaoAtualizada() != null)
				.map(RelatorioB01VO::getPrevisaoAtualizada).reduce(BigDecimal.ZERO, BigDecimal::add);
		
		BigDecimal totalDotacaoAtualizada = dadosPagina2.stream().filter(d -> d.getDotacaoAtualizada() != null)
				.map(RelatorioB02VO::getDotacaoAtualizada).reduce(BigDecimal.ZERO, BigDecimal::add);
		
		BigDecimal totalReceitasRealizadas = dados.stream().filter(d -> d.getReceitasRealizadas() != null)
				.map(RelatorioB01VO::getReceitasRealizadas).reduce(BigDecimal.ZERO, BigDecimal::add);
		
		BigDecimal totalDespesasEmpenhadas = dadosPagina2.stream().filter(d -> d.getDespesasEmpenhadas() != null)
				.map(RelatorioB02VO::getDespesasEmpenhadas).reduce(BigDecimal.ZERO, BigDecimal::add);
		
		RelatorioB01VO deficit = dados.get(17);
		
		deficit.setPrevisaoInicial(totalPrevisaoInicial.compareTo(totalDotacaoInicial) < 0 ? totalDotacaoInicial.subtract(totalPrevisaoInicial) : null);
		deficit.setPrevisaoAtualizada(totalPrevisaoAtualizada.compareTo(totalDotacaoAtualizada) < 0 ? totalDotacaoAtualizada.subtract(totalPrevisaoAtualizada) : null);
		deficit.setReceitasRealizadas(totalReceitasRealizadas.compareTo(totalDespesasEmpenhadas) < 0 ? totalDespesasEmpenhadas.subtract(totalReceitasRealizadas) : null);
		
		RelatorioB02VO superavit = dadosPagina2.get(11);
		
		superavit.setDotacaoInicial(totalPrevisaoInicial.compareTo(totalDotacaoInicial) > 0 ? totalPrevisaoInicial.subtract(totalDotacaoInicial) : null);
		superavit.setDotacaoAtualizada(totalPrevisaoAtualizada.compareTo(totalDotacaoAtualizada) > 0 ? totalPrevisaoAtualizada.subtract(totalDotacaoAtualizada) : null);
		superavit.setDespesasEmpenhadas(totalReceitasRealizadas.compareTo(totalDespesasEmpenhadas) > 0 ? totalReceitasRealizadas.subtract(totalDespesasEmpenhadas) : null);
		
		return dados;
	}
	
	private List<RelatorioB02VO> recuperaDadosPagina2(List<Integer> listaIdsUnidades) {
		
		String sql = null;
		
		try {
			sql = new String(Files.readAllBytes(Paths.get(this.getClass().getResource("relatoriobo02.sql").getFile())));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		@SuppressWarnings("unchecked")
		List<Object[]> rows = entityManager.createNativeQuery(sql).setParameter("unidades", listaIdsUnidades).getResultList();

		List<RelatorioB02VO> dados = new ArrayList<RelatorioB02VO>(rows.size());

		
		for (Object[] row : rows) {
			
			RelatorioB02VO dado = new RelatorioB02VO();
			
			dado.setTotal(String.valueOf(row[0]));
			dado.setTotalCategoria(String.valueOf(row[1]));
			dado.setCategoria(String.valueOf(row[2]));
			dado.setGrupo(String.valueOf(row[3]));
			dado.setModalidade(String.valueOf(row[4]));
			dado.setDotacaoInicial(row[5] != null ? new BigDecimal(row[5].toString()) : null);
			dado.setDotacaoAtualizada(row[6] != null ? new BigDecimal(row[6].toString()) : null);
			dado.setDespesasEmpenhadas(row[7] != null ? new BigDecimal(row[7].toString()) : null);
			dado.setDespesasLiquidadas(row[8] != null ? new BigDecimal(row[8].toString()) : null);
			dado.setDespesasPagas(row[9] != null ? new BigDecimal(row[9].toString()) : null);
			
			dados.add(dado);
		}
		
		return dados;
	}
	
	private List<RelatorioB03VO> recuperarDadosPagina3(List<Integer> listaIdsUnidades) {
		
		String sql = 
		
		"select " +
			"(case when vw.codigo ~ '^3.' then 'Despesas Correntes' else 'Despesas de Capital' end) categoria, " +
			"(case " + 
				"when vw.codigo ~ '^3.1' then 'Pessoal e Encargos Pessoais' " +
		      	"when vw.codigo ~ '^3.2' then 'Juros e Encargos da Dívida' " +
		      	"when vw.codigo ~ '^3.3' then 'Outras Despesas Correntes' " +
		      	"when vw.codigo ~ '^4.4' then 'Investimentos' " +
		      	"when vw.codigo ~ '^4.5' then 'Inversões Financeiras' " +
		      	"when vw.codigo ~ '^4.6' then 'Amortização da Dívida' " + 
			"end) grupo, " +
			"coalesce(bo.inscrito_anterior, 0) val_ria, coalesce(bo.inscrito_31_anterior, 0) val_rie, coalesce(bo.liquidado, 0) val_rli, " +
			"coalesce(bo.pago, 0) val_rpg, coalesce(bo.cancelado, 0) val_rcn " +
		"from " + 
			"sae.vw_natureza_despesa vw " +
		"left outer join ( " +
			"select " + 
				"regexp_replace(natureza_despesa, '[.]', '', 'g') nd, sum(inscrito_anterior) inscrito_anterior, sum(inscrito_31_anterior) inscrito_31_anterior, " + 
				"sum(liquidado) liquidado, sum(pago) pago, sum(cancelado) cancelado " + 
			"from " + 
				"prestacao.bo03 " +
			"where " +
				"unidade_id in (:unidades) " +
			"group by " + 
				"regexp_replace(natureza_despesa, '[.]', '', 'g')) bo on bo.nd = regexp_replace(vw.codigo, '[.]', '', 'g') and vw.ativo = 'S' " +
		"where " +
			"vw.codigo ~ '^(3.[123]|4.[456])' and vw.ativo = 'S' " +
		"order by " +
			"(case when vw.codigo ~ '^3.' then 1 else 2 end), " +
			"(case " +
				"when vw.codigo ~ '^3.1' then 1 " +
		      	"when vw.codigo ~ '^3.2' then 2 " +
		      	"when vw.codigo ~ '^3.3' then 3 " +
		      	"when vw.codigo ~ '^4.4' then 4 " +
		      	"when vw.codigo ~ '^4.5' then 5 " +
		      	"when vw.codigo ~ '^4.6' then 6 " + 
			"end)";
		
		@SuppressWarnings("unchecked")
		List<Object[]> rows = entityManager.createNativeQuery(sql).setParameter("unidades", listaIdsUnidades).getResultList();
		
		List<RelatorioB03VO> dados = new ArrayList<RelatorioB03VO>(rows.size());
		
		for (Object[] row : rows) {
			
			RelatorioB03VO dado = new RelatorioB03VO();
			
			dado.setCategoria(String.valueOf(row[0]));
			dado.setGrupo(String.valueOf(row[1]));
			dado.setRestosExercicioAnterior(row[2] != null ? new BigDecimal(row[2].toString()) : null);
			dado.setRestosTrintaEUm(row[3] != null ? new BigDecimal(row[3].toString()) : null);
			dado.setRestosLiquidados(row[4] != null ? new BigDecimal(row[4].toString()) : null);
			dado.setRestosPagos(row[5] != null ? new BigDecimal(row[5].toString()) : null);
			dado.setRestosCancelados(row[6] != null ? new BigDecimal(row[6].toString()) : null);
			
			dados.add(dado);
		}
		
		return dados;
	}
	
	private List<RelatorioB03VO> recuperarDadosPagina4(List<Integer> listaIdsUnidades) {
		
		String sql = 
				
		"select " +
			"(case when vw.codigo ~ '^3.' then 'Despesas Correntes' else 'Despesas de Capital' end) categoria, " +
			"(case " + 
				"when vw.codigo ~ '^3.1' then 'Pessoal e Encargos Pessoais' " +
		      	"when vw.codigo ~ '^3.2' then 'Juros e Encargos da Dívida' " +
		      	"when vw.codigo ~ '^3.3' then 'Outras Despesas Correntes' " +
		      	"when vw.codigo ~ '^4.4' then 'Investimentos' " +
		      	"when vw.codigo ~ '^4.5' then 'Inversões Financeiras' " +
		      	"when vw.codigo ~ '^4.6' then 'Amortização da Dívida' " + 
			"end) grupo, " +
			"coalesce(bo.inscrito_anterior, 0) val_ria, coalesce(bo.inscrito_31_anterior, 0) val_rie, coalesce(bo.pago, 0) val_rpg, " + 
			"coalesce(bo.cancelado, 0) val_rcn " +
		"from " + 
			"sae.vw_natureza_despesa vw " +
		"left outer join ( " +
			"select " + 
				"regexp_replace(natureza_despesa, '[.]', '', 'g') nd, sum(inscrito_anterior) inscrito_anterior, sum(inscrito_31_anterior) inscrito_31_anterior, " + 
				"sum(pago) pago, sum(cancelado) cancelado " + 
			"from " + 
				"prestacao.bo04 " +
			"where " + 
				"unidade_id in (:unidades) " +
			"group by " + 
				"regexp_replace(natureza_despesa, '[.]', '', 'g')) bo on bo.nd = regexp_replace(vw.codigo, '[.]', '', 'g') and vw.ativo = 'S' " +
		"where " +
			"vw.codigo ~ '^(3.[123]|4.[456])' and vw.ativo = 'S' " +
		"order by " +
			"(case when vw.codigo ~ '^3.' then 1 else 2 end), " +
			"(case " + 
				"when vw.codigo ~ '^3.1' then 1 " +
		      	"when vw.codigo ~ '^3.2' then 2 " +
		      	"when vw.codigo ~ '^3.3' then 3 " +
		      	"when vw.codigo ~ '^4.4' then 4 " +
		      	"when vw.codigo ~ '^4.5' then 5 " +
		      	"when vw.codigo ~ '^4.6' then 6 " +
			"end)";
		
		@SuppressWarnings("unchecked")
		List<Object[]> rows = entityManager.createNativeQuery(sql).setParameter("unidades", listaIdsUnidades).getResultList();
		
		List<RelatorioB03VO> dados = new ArrayList<RelatorioB03VO>(rows.size());
		
		for (Object[] row : rows) {
			
			RelatorioB03VO dado = new RelatorioB03VO();
			
			dado.setCategoria(String.valueOf(row[0]));
			dado.setGrupo(String.valueOf(row[1]));
			dado.setRestosExercicioAnterior(row[2] != null ? new BigDecimal(row[2].toString()) : null);
			dado.setRestosTrintaEUm(row[3] != null ? new BigDecimal(row[3].toString()) : null);
			dado.setRestosPagos(row[4] != null ? new BigDecimal(row[4].toString()) : null);
			dado.setRestosCancelados(row[5] != null ? new BigDecimal(row[5].toString()) : null);
			
			dados.add(dado);
		}
		
		return dados;
	}
}