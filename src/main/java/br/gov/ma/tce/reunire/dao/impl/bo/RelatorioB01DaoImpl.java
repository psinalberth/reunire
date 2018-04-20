package br.gov.ma.tce.reunire.dao.impl.bo;

import static br.gov.ma.tce.reunire.util.Util.soma;

import java.math.BigDecimal;
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
		
		String sql = 
				
		"select " + 
			"regexp_replace(bo.natureza_receita, '[.]', '', 'g') nr, " + 
			"bo.previsao_inicial val_pin, bo.previsao_atualizada val_pat, bo.receita_realizada val_rre " + 
		"from " + 
			"prestacao.bo01 bo " +
		"where " + 
			"bo.unidade_id in (:unidades) and " +
			"((:modulo is null) or (bo.modulo_id = :modulo)) and " +
			"regexp_replace(bo.natureza_receita, '[.]', '', 'g') ~ '^([17][12345679]|[28][123]|2[45]|9[7])' and " +
			"(not regexp_replace(bo.natureza_receita, '[.]', '', 'g') ~ '^21(140600|230700)') " +
		"order by " +
			"regexp_replace(bo.natureza_receita, '[.]', '', 'g')";
		
		List<Object[]> rows = entityManager.createNativeQuery(sql)
				.setParameter("unidades", listaIdsUnidades)
				.setParameter("modulo", 1)
				.getResultList();
		
		List<RelatorioB01VO> dados = new ArrayList<RelatorioB01VO>();
		
		dados.add(new RelatorioB01VO("Receitas Correntes (I)", "Receita Tributária", 
					  soma("^[17]1", rows, 1), soma("^[17]1", rows, 2), soma("^[17]1", rows, 3)));
		
		dados.add(new RelatorioB01VO("Receitas Correntes (I)", "Receita de Contribuições", 
				  soma("^[17]2", rows, 1), soma("^[17]2", rows, 2), soma("^[17]2", rows, 3)));
		
		dados.add(new RelatorioB01VO("Receitas Correntes (I)", "Receita Patrimonial", 
				  soma("^[17]3", rows, 1), soma("^[17]3", rows, 2), soma("^[17]3", rows, 3)));
		
		dados.add(new RelatorioB01VO("Receitas Correntes (I)", "Receita Agropecuária", 
				  soma("^[17]4", rows, 1), soma("^[17]4", rows, 2), soma("^[17]4", rows, 3)));
		
		dados.add(new RelatorioB01VO("Receitas Correntes (I)", "Receita Industrial", 
				  soma("^[17]5", rows, 1), soma("^[17]5", rows, 2), soma("^[17]5", rows, 3)));
		
		dados.add(new RelatorioB01VO("Receitas Correntes (I)", "Receita de Serviços", 
				  soma("^[17]6", rows, 1), soma("^[17]6", rows, 2), soma("^[17]6", rows, 3)));
		
		dados.add(new RelatorioB01VO("Receitas Correntes (I)", "Transferências Correntes", 
				  soma("^[179]7", rows, 1), soma("^[179]7", rows, 2), soma("^[179]7", rows, 3)));
		
		dados.add(new RelatorioB01VO("Receitas Correntes (I)", "Outras Receitas Correntes", 
				  soma("^[17]9", rows, 1), soma("^[17]9", rows, 2), soma("^[17]9", rows, 3)));
		
		dados.add(new RelatorioB01VO("Receitas de Capital (II)", "Operações de Crédito", 
				  soma("^21", rows, 1), soma("^21", rows, 2), soma("^21", rows, 3)));
		
		dados.add(new RelatorioB01VO("Receitas de Capital (II)", "Alienação de Bens", 
				  soma("^[28]2", rows, 1), soma("^[28]2", rows, 2), soma("^[28]2", rows, 3)));
		
		dados.add(new RelatorioB01VO("Receitas de Capital (II)", "Amortizações de Empréstimos", 
				  soma("^[28]3", rows, 1), soma("^[28]3", rows, 2), soma("^[28]3", rows, 3)));
		
		dados.add(new RelatorioB01VO("Receitas de Capital (II)", "Transferências de Capital", 
				  soma("^24", rows, 1), soma("^24", rows, 2), soma("^24", rows, 3)));
		
		dados.add(new RelatorioB01VO("Receitas de Capital (II)", "Outras Receitas de Capital", 
				  soma("^25", rows, 1), soma("^25", rows, 2), soma("^25", rows, 3)));
		
		sql = 
				
		"select " + 
		"	total, total_categoria, categoria, origem, especie, sum(val_pin) val_pin, sum(val_pat) val_pat, sum(val_rre) val_rre  " + 
		"from ( " + 
		"select " + 
		"	'TOTAL (VII) = (V + VI)' total, " + 
		"	'SUBTOTAL COM REFINANCIAMENTO (V) =  (III + IV)' total_categoria, 'Operações de Crédito/Refinanciamento (IV)' categoria,  " + 
		"	(case when vw.codigo_natureza_receita like '2.1.1.%' then 'Operações de Crédito Internas' else 'Operações de Crédito Externas' end) origem, " + 
		"	(case when vw.codigo_natureza_receita ~ '2.1.(1.1|2.2).01.00' then 'Mobiliária' else 'Contratual' end) especie, " + 
		"	coalesce(bo.val_pin, 0) val_pin, coalesce(bo.val_pat, 0) val_pat, coalesce(bo.val_rre, 0) val_rre " + 
		"from  " + 
		"	sae.vw_natureza_receita vw  " + 
		"left outer join ( " + 
		"	select  " + 
		"		regexp_replace(bo.natureza_receita, '[.]', '', 'g') nr,  " + 
		"		sum(bo.previsao_inicial) val_pin, sum(bo.previsao_atualizada) val_pat, sum(bo.receita_realizada) val_rre  " + 
		"	from  " + 
		"		prestacao.bo01 bo " + 
		"	where  " + 
		"		bo.unidade_id in (:unidades) " + 
		"	group by " + 
		"		regexp_replace(bo.natureza_receita, '[.]', '', 'g')) bo on bo.nr = regexp_replace(vw.codigo_natureza_receita, '[.]', '', 'g') " + 
		"where " + 
		"	vw.codigo_natureza_receita ~ '^2.1.((1.1|2.2).01.00|1.4.06.00|2.3.07.00)' and vw.ativo = 'S' " + 
		"	 " + 
		"union all " + 
		" " + 
		"select " + 
		"	'TOTAL (VII) = (V + VI)' total, " + 
		"	'SUBTOTAL COM REFINANCIAMENTO (V) =  (III + IV)' total_categoria, 'Operações de Crédito/Refinanciamento (IV)' categoria,  " + 
		"	'Operações de Crédito Externas' origem, 'Mobiliária' especie, null val_pin, null val_pat, null val_rre " + 
		"   " + 
		"union all " + 
		"   " + 
		"select " + 
		"	'TOTAL (VII) = (V + VI)' total, " + 
		"	'SUBTOTAL COM REFINANCIAMENTO (V) =  (III + IV)' total_categoria, 'Operações de Crédito/Refinanciamento (IV)' categoria,  " + 
		"	'Operações de Crédito Externas' origem, 'Contratual' especie, 0 val_pin, 0 val_pat, 0 val_rre " + 
		"     " + 
		"union all " + 
		" " + 
		"select " + 
		"	'TOTAL (VII) = (V + VI)' total, " + 
		"	'SUBTOTAL COM REFINANCIAMENTO (V) =  (III + IV)' total_categoria, 'Operações de Crédito/Refinanciamento (IV)' categoria,  " + 
		"	'Operações de Crédito Internas' origem, 'Mobiliária' especie, null val_pin, null val_pat, null val_rre " + 
		"	 " + 
		"union all " + 
		" " + 
		"select " + 
		"	'TOTAL (VII) = (V + VI)' total, " + 
		"	'SUBTOTAL COM REFINANCIAMENTO (V) =  (III + IV)' total_categoria, 'Operações de Crédito/Refinanciamento (IV)' categoria,  " + 
		"	'Operações de Crédito Internas' origem, 'Contratual' especie, 0 val_pin, 0 val_pat, 0 val_rre " + 
		"	 " + 
		"union all " + 
		" " + 
		"select " + 
		"	'TOTAL (VII) = (V + VI)' total, " + 
		"	'' total_categoria, '' categoria,  " + 
		"	'Déficit (VI)' origem, '' especie, null val_pin, null val_pat, null val_rre " + 
		"	 " + 
		"union all " + 
		" " + 
		"select " + 
		"	'' total, '' total_categoria, 'Saldos de Exercícios Anteriores (Utilizados Para Créditos Adicionais)' categoria, " + 
		"	'Recursos Arrecadados em Exercícios Anteriores' origem, '' especie, null val_pin, sum(coalesce(arrecadado_anterior, 0)) val_pat, 0 val_rre " + 
		"from  " + 
		"	prestacao.bo05 where unidade_id in (:unidades) " + 
		" " + 
		"union all " + 
		" " + 
		"select " + 
		"	'' total, '' total_categoria, 'Saldos de Exercícios Anteriores (Utilizados Para Créditos Adicionais)' categoria, " + 
		"	'Superávit Financeiro' origem, '' especie, null val_pin, sum(coalesce(superavit_financeiro, 0)) val_pat, 0 val_rre " + 
		"from  " + 
		"	prestacao.bo05 where unidade_id in (:unidades) " + 
		"	 " + 
		"union all " + 
		" " + 
		"select " + 
		"	'' total, '' total_categoria, 'Saldos de Exercícios Anteriores (Utilizados Para Créditos Adicionais)' categoria, " + 
		"	'Reabertura de Créditos Adicionais' origem, '' especie, null val_pin, sum(coalesce(credito_adicional, 0)) val_pat, 0 val_rre " + 
		"from  " + 
		"	prestacao.bo05 where unidade_id in (:unidades)) result " + 
		"group by " + 
		"	total, total_categoria, categoria, origem, especie " + 
		"order by " + 
		"(case  " + 
		"	when total = 'TOTAL (VII) = (V + VI)' then 1 " + 
		"	when total = 'Saldos de Exercícios Anteriores (Utilizados Para Créditos Adicionais)' then 2 " + 
		"end), " + 
		"(case  " + 
		"	when categoria = 'Receitas Correntes (I)' then 1 " + 
		"	when categoria = 'Receitas de Capital (II)' then 2 " + 
		"	when categoria = 'Recursos Arrecadados em Exercícios Anteriores (III)' then 3 " + 
		"    when categoria = 'Operações de Crédito/Refinanciamento (IV)' then 4 " + 
		"end), " + 
		"(case  " + 
		"    when origem = 'Receita Tributária' then 1 " + 
		"    when origem = 'Receita de Contribuições' then 2 " + 
		"    when origem = 'Receita Patrimonial' then 3 " + 
		"    when origem = 'Receita Agropecuária' then 4 " + 
		"    when origem = 'Receita Industrial' then 5 " + 
		"    when origem = 'Receita de Serviços' then 6 " + 
		"    when origem = 'Transferências Correntes' then 7 " + 
		"    when origem = 'Outras Receitas Correntes' then 8 " + 
		"    when origem = 'Operações de Crédito' then 9 " + 
		"    when origem = 'Alienação de Bens' then 10 " + 
		"    when origem = 'Amortizações de Empréstimos' then 11 " + 
		"    when origem = 'Transferências de Capital' then 12 " + 
		"    when origem = 'Outras Receitas de Capital' then 13 " + 
		"    when origem = 'Operações de Crédito Internas' then 14 " + 
		"    when origem = 'Operações de Crédito Externas' then 15 " + 
		"    when origem = 'Recursos Arrecadados em Exercícios Anteriores' then 16 " + 
		"    when origem = 'Superávit Financeiro' then 17 " + 
		"    when origem = 'Reabertura de Créditos Adicionais' then 18 " + 
		"  end), " + 
		"(case  " + 
		"	when especie = 'Mobiliária' then 1 " + 
		"	when especie = 'Contratual' then 2 " + 
		"end)";
		
		rows = entityManager.createNativeQuery(sql).setParameter("unidades", listaIdsUnidades).getResultList();
		
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
		
		String sql = 
				
		"select  " + 
		"	total, total_categoria, categoria, grupo, modalidade, " + 
		"	sum(val_din) val_din, sum(val_dat) val_dat, sum(val_dee) val_dee, sum(val_del) val_del, sum(val_dep) val_dep " + 
		"from ( " + 
		"	select " + 
		"		'TOTAL (XIV) = (XII + XIII)' total,  " + 
		"		'SUBTOTAL DAS DESPESAS (XI) = (VIII + IX + X)' total_categoria, " + 
		"		(case  " + 
		"			when vw.codigo ~ '^3' then 'Despesas Correntes (VIII)' " + 
		"	      	when vw.codigo ~ '^4' then 'Despesas de Capital (IX)' " + 
		"	      	when vw.codigo ~ '^9' then 'Reserva de Contingência (X)' " + 
		"		end) categoria, " + 
		"		(case  " + 
		"			when vw.codigo ~ '^3.1' then 'Pessoal e Encargos Pessoais' " + 
		"	      	when vw.codigo ~ '^3.2' then 'Juros e Encargos da Dívida' " + 
		"	      	when vw.codigo ~ '^3.3' then 'Outras Despesas Correntes' " + 
		"	      	when vw.codigo ~ '^4.4' then 'Investimentos' " + 
		"	      	when vw.codigo ~ '^4.5' then 'Inversões Financeiras' " + 
		"	      	when vw.codigo ~ '^4.6' then 'Amortização da Dívida' else '' " + 
		"		end) grupo, '' modalidade, " + 
		"		coalesce(bo.val_din, 0) val_din, coalesce(bo.val_dat, 0) val_dat, coalesce(bo.val_dee, 0) val_dee, " + 
		"		coalesce(bo.val_del, 0) val_del, coalesce(bo.val_dep, 0) val_dep " + 
		"	from  " + 
		"		sae.vw_natureza_despesa vw " + 
		"	left outer join ( " + 
		"		select " + 
		"			regexp_replace(bo.natureza_despesa, '[.]', '', 'g') nd, " + 
		"			sum(bo.dotacao_inicial) val_din, sum(bo.dotacao_atualizada) val_dat, sum(bo.despesa_empenhada) val_dee, " + 
		"			sum(bo.despesa_liquidada) val_del, sum(bo.despesa_paga) val_dep " + 
		"		from  " + 
		"			prestacao.bo02 bo " + 
		"		where " + 
		"			bo.unidade_id in (:unidades) " + 
		"		group by " + 
		"			regexp_replace(bo.natureza_despesa, '[.]', '', 'g')) bo on bo.nd = regexp_replace(vw.codigo, '[.]', '', 'g') " + 
		"	where " + 
		"		vw.codigo ~ '^(3.[123]|4.[456]|9)' and not vw.codigo ~ '4.6.90.7[67].00' and vw.ativo = 'S' " + 
		"		 " + 
		"  union all " + 
		"	 " + 
		"  select  " + 
		"    'TOTAL (XIV) = (XII + XIII)' total, 'SUBTOTAL COM REFINANCIAMENTO (XIII) = (XI + XII)' total_categoria, 'Amortização da Dívida/Refinanciamento (XII)' categoria, " + 
		"    'Amortização da Dívida Interna' grupo, 'Dívida Mobiliária' modalidade, 0 val_din, 0 val_dat, 0 val_dee,0 val_del, 0 val_dep " + 
		" " + 
		"  union all " + 
		" " + 
		"  select  " + 
		"    'TOTAL (XIV) = (XII + XIII)' total, 'SUBTOTAL COM REFINANCIAMENTO (XIII) = (XI + XII)' total_categoria, 'Amortização da Dívida/Refinanciamento (XII)' categoria, " + 
		"    'Amortização da Dívida Interna' grupo, 'Outras Dívidas' modalidade, 0 val_din, 0 val_dat, 0 val_dee,0 val_del, 0 val_dep  " + 
		" " + 
		"  union all " + 
		" " + 
		"  select  " + 
		"    'TOTAL (XIV) = (XII + XIII)' total, 'SUBTOTAL COM REFINANCIAMENTO (XIII) = (XI + XII)' total_categoria, 'Amortização da Dívida/Refinanciamento (XII)' categoria, " + 
		"    'Amortização da Dívida Externa' grupo, 'Dívida Mobiliária' modalidade, 0 val_din, 0 val_dat, 0 val_dee,0 val_del, 0 val_dep  " + 
		" " + 
		"  union all " + 
		"   " + 
		"  select " + 
		"	'TOTAL (XIV) = (XII + XIII)' total, " + 
		"	'' total_categoria, '' categoria,  " + 
		"	'Superávit (XIII)' grupo, '' modalidade, null val_din, null val_dat, null val_dee, null val_del, null val_dep " + 
		"	 " + 
		"  union all " + 
		" " + 
		"  select  " + 
		"    'TOTAL (XIV) = (XII + XIII)' total, 'SUBTOTAL COM REFINANCIAMENTO (XIII) = (XI + XII)' total_categoria, 'Amortização da Dívida/Refinanciamento (XII)' categoria, " + 
		"    'Amortização da Dívida Externa' grupo, 'Outras Dívidas' modalidade, 0 val_din, 0 val_dat, 0 val_dee,0 val_del, 0 val_dep " + 
		"     " + 
		"  union all " + 
		"   " + 
		"  select  " + 
		"  	'Reserva do RPPS' total, '' total_categoria, '' categoria, '' grupo, '' modalidade,  " + 
		"  	null val_din, sum(coalesce(bo.reserva_rpps, 0)) val_dat, null val_dee, null val_del, null val_dep " + 
		"  from  " + 
		"  	prestacao.bo05 bo where bo.unidade_id in (:unidades) " + 
		"  group by " + 
		"  	total, total_categoria, categoria, grupo, modalidade) result " + 
		"group by " + 
		"	total, total_categoria, categoria, grupo, modalidade " + 
		"order by " + 
		"	(case  " + 
		"		when total = 'TOTAL (XIV) = (XII + XIII)' then 1  " + 
		"		else 2 " + 
		"	end), " + 
		"	(case  " + 
		"	    when categoria = 'Despesas Correntes (VIII)' then 1 " + 
		"	    when categoria = 'Despesas de Capital (IX)' then 2 " + 
		"	    when categoria = 'Reserva de Contingência (X)' then 3 " + 
		"	    when categoria = 'Reserva do RPPS (XI)' then 4 " + 
		"	end), " + 
		"	(case " + 
		"	    when grupo = 'Amortização da Dívida Interna' then 1 " + 
		"	    when grupo = 'Amortização da Dívida Externa' then 2 " + 
		"	    when grupo = 'Pessoal e Encargos Pessoais' then 3 " + 
		"	    when grupo = 'Juros e Encargos da Dívida' then 4 " + 
		"	    when grupo = 'Outras Despesas Correntes' then 5 " + 
		"	    when grupo = 'Investimentos' then 6 " + 
		"	    when grupo = 'Inversões Financeiras' then 7 " + 
		"	    when grupo = 'Amortização da Dívida' then 8 " + 
		"	end), " + 
		"	(case  " + 
		"	    when modalidade = 'Dívida Mobiliária' then 1 " + 
		"	    when modalidade = 'Outras Dívidas' then 2 " + 
		"	end)";	
				
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