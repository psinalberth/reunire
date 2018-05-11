package br.gov.ma.tce.reunire.dao.impl.d;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import br.gov.ma.tce.reunire.dao.DemonstrativoDao;
import br.gov.ma.tce.reunire.dao.impl.PrestacaoDaoImpl;
import br.gov.ma.tce.reunire.model.vo.d.RelatorioD001VO;
import br.gov.ma.tce.reunire.model.vo.gestor.UnidadeVO;

import static br.gov.ma.tce.reunire.util.Util.soma;

@Stateless
public class RelatorioD001DaoImpl extends PrestacaoDaoImpl<RelatorioD001VO> implements DemonstrativoDao<RelatorioD001VO> {

	@Override
	public List<RelatorioD001VO> recuperaDados(Map<String, Object> params) {
		
		List<UnidadeVO> listaUnidades = recuperarUnidades(params);
		List<Integer> listaIdsUnidades = extrairIds(listaUnidades);
		
		List<BigDecimal> receitas = recuperarReceitas(params, listaIdsUnidades);
		List<BigDecimal> despesas = recuperarDespesas(params, listaIdsUnidades);
		
		RelatorioD001VO dado = new RelatorioD001VO();
		
		dado.setIdUnidade(422);
		dado.setReceitas(receitas.toArray(new BigDecimal[receitas.size()]));
		dado.setDespesas(despesas.toArray(new BigDecimal[despesas.size()]));
		
		List<RelatorioD001VO> dados = new ArrayList<RelatorioD001VO>();
		
		dados.add(dado);
		
		return dados;
	}

	@SuppressWarnings("unchecked")
	private List<BigDecimal> recuperarReceitas(Map<String, Object> params, List<Integer> listaIdsUnidades) {
		
		String sql = 
				
		"select  " + 
			"id, nr, val_pin, val_pat, val_rre  " + 
		"from (  " +
			"select  " + 
				"1 id, regexp_replace(bo.natureza_receita, '[.]', '', 'g') nr,  " + 
				"sum(bo.previsao_inicial) val_pin, sum(bo.previsao_atualizada) val_pat, sum(bo.receita_realizada) val_rre  " + 
			"from  " + 
				"prestacao.bo01 bo  " + 
			"where  " + 
				"bo.unidade_id in (:unidades) and  " + 
				"((:modulo is null) or (bo.modulo_id = :modulo)) and  " + 
				"regexp_replace(bo.natureza_receita, '[.]', '', 'g') ~ '^([17][12345679]|[28][123]|2[45]|9[237])' and  " + 
				"(not regexp_replace(bo.natureza_receita, '[.]', '', 'g') ~ '^21(140600|230700)')  " + 
			"group by  " + 
				"regexp_replace(bo.natureza_receita, '[.]', '', 'g')  " + 
				
			"union all  " +
			
			"select  " + 
				"2 id, regexp_replace(bo.natureza_receita, '[.]', '', 'g') nr,  " + 
				"sum(bo.previsao_inicial) val_pin, sum(bo.previsao_atualizada) val_pat, sum(bo.receita_realizada) val_rre  " + 
			"from  " + 
				"prestacao.bo01 bo  " + 
			"where  " + 
				"bo.unidade_id in (:unidades) and  " + 
				"((:modulo is null) or (bo.modulo_id = :modulo)) and  " + 
				"regexp_replace(bo.natureza_receita, '[.]', '', 'g') ~ '^21((11|22)0100|140600|230700)' " + 
			"group by  " + 
				"regexp_replace(bo.natureza_receita, '[.]', '', 'g')) result " + 
		"order by " +
			"id, nr";
		
		List<Object[]> rows = entityManager.createNativeQuery(sql)
				.setParameter("unidades", listaIdsUnidades)
				.setParameter("modulo", params.get("modulo") != null ? Integer.valueOf(String.valueOf(params.get("modulo"))) : 1)
				.getResultList();
		
		List<BigDecimal> receitas = new ArrayList<BigDecimal>();
		
		// Receitas Correntes
		
		receitas.add(soma(1, "^[17]1", rows, 4));
		receitas.add(soma(1, "^[17]2", rows, 4));
		receitas.add(soma(1, "^[17]3", rows, 4));
		receitas.add(soma(1, "^[17]4", rows, 4));
		receitas.add(soma(1, "^[17]5", rows, 4));
		receitas.add(soma(1, "^[17]6", rows, 4));
		receitas.add(soma(1, "^[17]7", rows, 4));
		receitas.add(soma(1, "^[17]9", rows, 4));
		receitas.add(soma(1, "^97", rows, 4));
		
		// Receitas de Capital
		
		receitas.add(soma(1, "^21", rows, 4));
		receitas.add(soma(1, "^[28]2", rows, 4));
		receitas.add(soma(1, "^[28]3", rows, 4));
		receitas.add(soma(1, "^24", rows, 4));
		receitas.add(soma(1, "^25", rows, 4));
		
		return receitas;
	}

	@SuppressWarnings("unchecked")
	private List<BigDecimal> recuperarDespesas(Map<String, Object> params, List<Integer> listaIdsUnidades) {
		
		String sql = 
				
		"select  " +
			"id, nd, val_din, val_dat, val_dee, val_del, val_dep  " +
		"from (  " +
			"select  " +
				"1 id, regexp_replace(natureza_despesa, '[.]', '', 'g') nd,  " + 
				"sum(dotacao_inicial) val_din, sum(dotacao_atualizada) val_dat, sum(despesa_empenhada) val_dee,  " + 
				"sum(despesa_liquidada) val_del, sum(despesa_paga) val_dep  " +
			"from  " + 
				"prestacao.bo02  " +
			"where  " +
				"unidade_id in (:unidades) and  " +
				"((:modulo is null) or (modulo_id = :modulo)) and  " +
				"regexp_replace(natureza_despesa, '[.]', '', 'g') ~ '^(3[123]|4[456]|99999900)' and  " +
				"(not regexp_replace(natureza_despesa, '[.]', '', 'g') ~ '46907[67]00')  " +
			"group by  " +
				"regexp_replace(natureza_despesa, '[.]', '', 'g')  " +
				
			"union all  " +
			
			"select  " +
				"2 id, regexp_replace(natureza_despesa, '[.]', '', 'g') nd,  " + 
				"sum(dotacao_inicial) val_din, sum(dotacao_atualizada) val_dat, sum(despesa_empenhada) val_dee,  " + 
				"sum(despesa_liquidada) val_del, sum(despesa_paga) val_dep  " +
			"from  " + 
				"prestacao.bo02  " +
			"where  " +
				"unidade_id in (:unidades) and  " +
				"((:modulo is null) or (modulo_id = :modulo)) and  " +
				"regexp_replace(natureza_despesa, '[.]', '', 'g') ~ '46907[67]00'  " +
			"group by  " +
				"regexp_replace(natureza_despesa, '[.]', '', 'g')) result  " +
		"order by " +
			"id, nd";
		
		List<Object[]> rows = entityManager.createNativeQuery(sql)
			.setParameter("unidades", listaIdsUnidades)
			.setParameter("modulo", params.get("modulo") != null ? Integer.valueOf(String.valueOf(params.get("modulo"))) : 1)
			.getResultList();
		
		List<BigDecimal> despesas = new ArrayList<BigDecimal>();
		
		// Despesas Correntes
		
		despesas.add(soma(1, "^31", rows, 4));
		despesas.add(soma(1, "^32", rows, 4));
		despesas.add(soma(1, "^33", rows, 4));
		
		// Despesas de Capital
		
		despesas.add(soma(1, "^44", rows, 4));
		despesas.add(soma(1, "^45", rows, 4));
		despesas.add(soma(1, "^46", rows, 4));
		
		// Reserva de Contingência
		
		params.put("reservaContingencia", soma(1, "99999900", rows, 4));
		
		return despesas;
	}

	@Override
	public String getNomeRelatorio() {
		return "relatoriod001.jasper";
	}
}