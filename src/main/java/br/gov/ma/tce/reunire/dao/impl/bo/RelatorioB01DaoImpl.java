package br.gov.ma.tce.reunire.dao.impl.bo;

import static br.gov.ma.tce.reunire.util.Util.soma;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import br.gov.ma.tce.reunire.dao.DemonstrativoDao;
import br.gov.ma.tce.reunire.dao.impl.PrestacaoDaoImpl;
import br.gov.ma.tce.reunire.model.vo.bo.RelatorioB01VO;
import br.gov.ma.tce.reunire.model.vo.bo.RelatorioB02VO;
import br.gov.ma.tce.reunire.model.vo.bo.RelatorioB03VO;
import br.gov.ma.tce.reunire.model.vo.gestor.UnidadeVO;

@Stateless
public class RelatorioB01DaoImpl extends PrestacaoDaoImpl<RelatorioB01VO> implements DemonstrativoDao<RelatorioB01VO> {
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RelatorioB01VO> recuperaDados(Map<String, Object> params) {
		
		List<UnidadeVO> listaUnidades = recuperarUnidades(params);
		List<Integer> listaIdsUnidades = extrairIds(listaUnidades);
		
		String sql = 
				
//		"select  " + 
//			"id, nr, val_pin, val_pat, val_rre  " + 
//		"from (  " +
//			"select  " + 
//				"1 id, regexp_replace(bo.natureza_receita, '[.]', '', 'g') nr,  " + 
//				"sum(bo.previsao_inicial) val_pin, sum(bo.previsao_atualizada) val_pat, sum(bo.receita_realizada) val_rre  " + 
//			"from  " + 
//				"prestacao.bo01 bo  " + 
//			"where  " + 
//				"bo.unidade_id in (:unidades) and  " + 
//				"((:modulo is null) or (bo.modulo_id = :modulo)) and  " + 
//				"regexp_replace(bo.natureza_receita, '[.]', '', 'g') ~ '^([17][12345679]|[28][123]|2[45]|9[237])' and  " + 
//				"(not regexp_replace(bo.natureza_receita, '[.]', '', 'g') ~ '^21(140600|230700)')  " + 
//			"group by  " + 
//				"regexp_replace(bo.natureza_receita, '[.]', '', 'g')  " + 
//				
//			"union all  " +
//			
//			"select  " + 
//				"2 id, regexp_replace(bo.natureza_receita, '[.]', '', 'g') nr,  " + 
//				"sum(bo.previsao_inicial) val_pin, sum(bo.previsao_atualizada) val_pat, sum(bo.receita_realizada) val_rre  " + 
//			"from  " + 
//				"prestacao.bo01 bo  " + 
//			"where  " + 
//				"bo.unidade_id in (:unidades) and  " + 
//				"((:modulo is null) or (bo.modulo_id = :modulo)) and  " + 
//				"regexp_replace(bo.natureza_receita, '[.]', '', 'g') ~ '^21((11|22)0100|140600|230700)' " + 
//			"group by  " + 
//				"regexp_replace(bo.natureza_receita, '[.]', '', 'g')) result " + 
//		"order by " +
//			"id, nr";
		
		"select  " + 
		"id, nome, nr, val_pin, val_pat, val_rre  " + 
		"from (  " + 
		"select  " + 
		"1 id, e.nome, regexp_replace(bo.natureza_receita, '[.]', '', 'g') nr,  " + 
		"sum(bo.previsao_inicial) val_pin, sum(bo.previsao_atualizada) val_pat, sum(bo.receita_realizada) val_rre  " + 
		"from  " + 
		"prestacao.bo01 bo " + 
		"join gestor.vw_estrutura_organizacional vw on bo.unidade_id = vw.unidade_id " + 
		"join gestor.ente e on e.ente_id = vw.ente_id " + 
		"where  " + 
		"((:modulo is null) or (bo.modulo_id = :modulo)) and  " + 
		"regexp_replace(bo.natureza_receita, '[.]', '', 'g') ~ '^([17][12345679]|[28][123]|2[45]|9[237])' and  " + 
		"(not regexp_replace(bo.natureza_receita, '[.]', '', 'g') ~ '^21(140600|230700)')  " + 
		"group by  " + 
		"e.nome, regexp_replace(bo.natureza_receita, '[.]', '', 'g')  " + 
		"union all  " + 
		"select  " + 
		"2 id, e.nome, regexp_replace(bo.natureza_receita, '[.]', '', 'g') nr,  " + 
		"sum(bo.previsao_inicial) val_pin, sum(bo.previsao_atualizada) val_pat, sum(bo.receita_realizada) val_rre  " + 
		"from  " + 
		"prestacao.bo01 bo " + 
		"join gestor.vw_estrutura_organizacional vw on bo.unidade_id = vw.unidade_id " + 
		"join gestor.ente e on e.ente_id = vw.ente_id " + 
		"where " + 
		"((:modulo is null) or (bo.modulo_id = :modulo)) and  " + 
		"regexp_replace(bo.natureza_receita, '[.]', '', 'g') ~ '^21((11|22)0100|140600|230700)' " + 
		"group by  " + 
		"e.nome, regexp_replace(bo.natureza_receita, '[.]', '', 'g')) result " + 
		"order by " + 
		"id, nome, nr asc";
		
		List<Object[]> rows = entityManager.createNativeQuery(sql)
				.setParameter("modulo", params.get("modulo") != null ? Integer.valueOf(String.valueOf(params.get("modulo"))) : 1)
				.getResultList();
		
		Map<String, List<Object[]>> mapa = new TreeMap<String, List<Object []>>();
		
		File file = new File("/home/saeti18/lol.csv");
		FileWriter fw = null;
		try {
			fw = new FileWriter(file);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (Object [] row : rows) {
			
			String ente = String.valueOf(row[1]);
			
			if (mapa.get(ente) == null) {
				
				List<Object[]> lista = new ArrayList<Object[]>();
				lista.add(row);
				
				mapa.put(ente, lista);
			} else {
				mapa.get(ente).add(row);
			}
		}
		
		for (Map.Entry<String, List<Object[]>> entry : mapa.entrySet()) {
			
			List<RelatorioB01VO> dados = new ArrayList<RelatorioB01VO>();
			
			String key = entry.getKey();
			
			rows = entry.getValue();
			
			// Receitas Correntes
			
			dados.add(new RelatorioB01VO("Receitas Correntes (I)", "Receita Tributária", "SUBTOTAL DAS RECEITAS (III) = (I + II)", "",
					soma(1, "^[17]1", rows, 3), soma(1, "^[17]1", rows, 4), soma(1, "^[17]1", rows, 5)));
			
			dados.add(new RelatorioB01VO("Receitas Correntes (I)", "Receita de Contribuições", "SUBTOTAL DAS RECEITAS (III) = (I + II)", "",
					soma(1, "^[17]2", rows, 3).subtract(soma(1, "^93", rows, 3)), soma(1, "^[17]2", rows, 4).subtract(soma(1, "^93", rows, 4)), soma(1, "^[17]2", rows, 5).subtract(soma(1, "^93", rows, 5))));
			
			dados.add(new RelatorioB01VO("Receitas Correntes (I)", "Receita Patrimonial", "SUBTOTAL DAS RECEITAS (III) = (I + II)", "",
					soma(1, "^[17]3", rows, 3), soma(1, "^[17]3", rows, 4), soma(1, "^[17]3", rows, 5)));
			
			dados.add(new RelatorioB01VO("Receitas Correntes (I)", "Receita Agropecuária", "SUBTOTAL DAS RECEITAS (III) = (I + II)", "",
					soma(1, "^[17]4", rows, 3), soma(1, "^[17]4", rows, 4), soma(1, "^[17]4", rows, 5)));
			
			dados.add(new RelatorioB01VO("Receitas Correntes (I)", "Receita Industrial", "SUBTOTAL DAS RECEITAS (III) = (I + II)", "",
					soma(1, "^[17]5", rows, 3), soma(1, "^[17]5", rows, 4), soma(1, "^[17]5", rows, 5)));
			
			dados.add(new RelatorioB01VO("Receitas Correntes (I)", "Receita de Serviços", "SUBTOTAL DAS RECEITAS (III) = (I + II)", "",
					soma(1, "^[17]6", rows, 3), soma(1, "^[17]6", rows, 4), soma(1, "^[17]6", rows, 5)));
			
			dados.add(new RelatorioB01VO("Receitas Correntes (I)", "Transferências Correntes", "SUBTOTAL DAS RECEITAS (III) = (I + II)", "",
					soma(1, "^[17]7", rows, 3).subtract(soma(1, "^97", rows, 3)), soma(1, "^[17]7", rows, 4).subtract(soma(1, "^97", rows, 4)), soma(1, "^[17]7", rows, 5).subtract(soma(1, "^97", rows, 5))));
			
			dados.add(new RelatorioB01VO("Receitas Correntes (I)", "Outras Receitas Correntes", "SUBTOTAL DAS RECEITAS (III) = (I + II)", "",
					soma(1, "^[17]9", rows, 3), soma(1, "^[17]9", rows, 4), soma(1, "^[17]9", rows, 5)));
			
			// Receitas de Capital		
			
			dados.add(new RelatorioB01VO("Receitas de Capital (II)", "Operações de Crédito", "SUBTOTAL DAS RECEITAS (III) = (I + II)", "",
					soma(1, "^21", rows, 3), soma(1, "^21", rows, 4), soma(1, "^21", rows, 5)));
			
			dados.add(new RelatorioB01VO("Receitas de Capital (II)", "Alienação de Bens", "SUBTOTAL DAS RECEITAS (III) = (I + II)", "",
					soma(1, "^[28]2", rows, 3), soma(1, "^[28]2", rows, 4), soma(1, "^[28]2", rows, 5)));
			
			dados.add(new RelatorioB01VO("Receitas de Capital (II)", "Amortizações de Empréstimos", "SUBTOTAL DAS RECEITAS (III) = (I + II)", "",
					soma(1, "^[28]3", rows, 3), soma(1, "^[28]3", rows, 4), soma(1, "^[28]3", rows, 5)));
			
			dados.add(new RelatorioB01VO("Receitas de Capital (II)", "Transferências de Capital", "SUBTOTAL DAS RECEITAS (III) = (I + II)", "",
					soma(1, "^24", rows, 3), soma(1, "^24", rows, 4), soma(1, "^24", rows, 5)));
			
			dados.add(new RelatorioB01VO("Receitas de Capital (II)", "Outras Receitas de Capital", "SUBTOTAL DAS RECEITAS (III) = (I + II)", "",
					soma(1, "^25", rows, 3), soma(1, "^25", rows, 4), soma(1, "^25", rows, 5)));
			
			// Operações de Crédito
			
			dados.add(new RelatorioB01VO("Operações de Crédito/Refinanciamento (IV)", "Operações de Crédito Internas", "SUBTOTAL COM REFINANCIAMENTO (V) = (III + IV)", "Mobiliária", 
					null, null, null));
			
			dados.add(new RelatorioB01VO("Operações de Crédito/Refinanciamento (IV)", "Operações de Crédito Internas", "SUBTOTAL COM REFINANCIAMENTO (V) = (III + IV)", "Contratual", 
					soma(2, "21140600", rows, 3), soma(2, "21140600", rows, 4), soma(2, "21140600", rows, 5)));
			
			dados.add(new RelatorioB01VO("Operações de Crédito/Refinanciamento (IV)", "Operações de Crédito Externas", "SUBTOTAL COM REFINANCIAMENTO (V) = (III + IV)", "Mobiliária", 
					null, null, null));
			
			dados.add(new RelatorioB01VO("Operações de Crédito/Refinanciamento (IV)", "Operações de Crédito Externas", "SUBTOTAL COM REFINANCIAMENTO (V) = (III + IV)", "Contratual", 
					soma(2, "21230700", rows, 3), soma(2, "21230700", rows, 4), soma(2, "21230700", rows, 5)));
			
			BigDecimal previsaoInicial = dados.stream().filter(x -> x.getPrevisaoInicial() != null).map(RelatorioB01VO::getPrevisaoInicial).reduce(BigDecimal.ZERO, BigDecimal::add);
			BigDecimal previsaoAtualizada = dados.stream().filter(x -> x.getPrevisaoInicial() != null).map(RelatorioB01VO::getPrevisaoAtualizada).reduce(BigDecimal.ZERO, BigDecimal::add);;
			BigDecimal receitaRealizada = dados.stream().filter(x -> x.getPrevisaoInicial() != null).map(RelatorioB01VO::getReceitasRealizadas).reduce(BigDecimal.ZERO, BigDecimal::add);;
			
			try {
				fw.write(key + "," + previsaoInicial + "," + previsaoAtualizada + "," + receitaRealizada + "\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println(key + "," + previsaoInicial + "," + previsaoAtualizada + "," + receitaRealizada);
		}
		
		try {
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<RelatorioB02VO> dadosPagina2 = recuperaDadosPagina2(listaIdsUnidades, params);
		params.put("DATA2", dadosPagina2);
		
		List<RelatorioB03VO> dadosPagina3 = recuperarDadosPagina3(listaIdsUnidades, params);
		params.put("DATA3", dadosPagina3);
		
		List<RelatorioB03VO> dadosPagina4 = recuperarDadosPagina4(listaIdsUnidades, params);
		params.put("DATA4", dadosPagina4);
		
		BigDecimal totalReceitasRealizadas = BigDecimal.ZERO;
		
		BigDecimal totalDespesasEmpenhadas = BigDecimal.ZERO;
		
		if (totalReceitasRealizadas.compareTo(totalDespesasEmpenhadas) > 0) {
			
			RelatorioB02VO superavit = new RelatorioB02VO();
			superavit.setGrupo("Superávit (XIII)");
			
			superavit.setDespesasEmpenhadas(totalReceitasRealizadas.compareTo(totalDespesasEmpenhadas) > 0 ? totalReceitasRealizadas.subtract(totalDespesasEmpenhadas) : null);
			
			dadosPagina2.add(superavit);
			
		} else if (totalReceitasRealizadas.compareTo(totalDespesasEmpenhadas) < 0) {
			
			RelatorioB01VO deficit = new RelatorioB01VO();
			deficit.setOrigem("Déficit (VI)");
			
			deficit.setReceitasRealizadas(totalReceitasRealizadas.compareTo(totalDespesasEmpenhadas) < 0 ? totalDespesasEmpenhadas.subtract(totalReceitasRealizadas) : null);
			
	//		dados.add(deficit);
		}
		
		// Dados Não Codificados
		
		sql = 
				
		"select " +
			"sum(arrecadado_anterior) arrecadado_anterior, sum(superavit_financeiro) superavit_financeiro, " +
			"sum(credito_adicional) credito_adicional, sum(reserva_rpps) reserva_rpps " +
		"from " + 
			"prestacao.bo05 " +
		"where " +
			"unidade_id in (:unidades) and " +
			"((:modulo is null) or (modulo_id = :modulo))";
		
		RelatorioB02VO reservaRPPS = new RelatorioB02VO();
		reservaRPPS.setTotal("Reserva do RPPS");
		
		try {
		
			Object[] row = (Object[]) entityManager.createNativeQuery(sql)
					.setParameter("unidades", listaIdsUnidades)
					.setParameter("modulo", params.get("modulo") != null ? Integer.valueOf(String.valueOf(params.get("modulo"))) : 1)
					.getSingleResult();
			
			params.put("recursosAnteriores", toBigDecimal(row[0]));
			params.put("superavit", toBigDecimal(row[1]));
			params.put("creditoAdicional", toBigDecimal(row[2]));
			
			reservaRPPS.setDotacaoAtualizada(toBigDecimal(row[3]));
		
		} catch (NoResultException ex) {
			
			params.put("recursosAnteriores", BigDecimal.ZERO);
			params.put("superavit", BigDecimal.ZERO);
			params.put("creditoAdicional", BigDecimal.ZERO);
			
			reservaRPPS.setDotacaoAtualizada(BigDecimal.ZERO);
		}
		
		dadosPagina2.add(reservaRPPS);
		
		return null;
	}
	
	private List<RelatorioB02VO> recuperaDadosPagina2(List<Integer> listaIdsUnidades, Map<String, Object> params) {
		
		String sql = 
				
		"select  " +
			"id, nome, nd, val_din, val_dat, val_dee, val_del, val_dep  " +
		"from (  " +
			"select  " +
				"1 id, e.nome, regexp_replace(natureza_despesa, '[.]', '', 'g') nd,  " + 
				"sum(dotacao_inicial) val_din, sum(dotacao_atualizada) val_dat, sum(despesa_empenhada) val_dee,  " + 
				"sum(despesa_liquidada) val_del, sum(despesa_paga) val_dep  " +
			"from  " + 
				"prestacao.bo02 bo  " +
				"join gestor.vw_estrutura_organizacional vw on bo.unidade_id = vw.unidade_id " + 
				"join gestor.ente e on e.ente_id = vw.ente_id " +
			"where  " +
				"((:modulo is null) or (modulo_id = :modulo)) and  " +
				"regexp_replace(natureza_despesa, '[.]', '', 'g') ~ '^(3[123]|4[456]|99999900)' and  " +
				"(not regexp_replace(natureza_despesa, '[.]', '', 'g') ~ '46907[67]00')  " +
			"group by  " +
				"e.nome, regexp_replace(natureza_despesa, '[.]', '', 'g')  " +
				
			"union all  " +
			
			"select  " +
				"2 id, e.nome, regexp_replace(natureza_despesa, '[.]', '', 'g') nd,  " + 
				"sum(dotacao_inicial) val_din, sum(dotacao_atualizada) val_dat, sum(despesa_empenhada) val_dee,  " + 
				"sum(despesa_liquidada) val_del, sum(despesa_paga) val_dep  " +
			"from  " + 
			"prestacao.bo02 bo  " +
			"join gestor.vw_estrutura_organizacional vw on bo.unidade_id = vw.unidade_id " + 
			"join gestor.ente e on e.ente_id = vw.ente_id " +
			"where  " +
				"((:modulo is null) or (modulo_id = :modulo)) and  " +
				"regexp_replace(natureza_despesa, '[.]', '', 'g') ~ '46907[67]00'  " +
			"group by  " +
				"e.nome, regexp_replace(natureza_despesa, '[.]', '', 'g')) result  " +
		"order by " +
			"id, nome, nd";
				
		@SuppressWarnings("unchecked")
		List<Object[]> rows = entityManager.createNativeQuery(sql)
			.setParameter("modulo", params.get("modulo") != null ? Integer.valueOf(String.valueOf(params.get("modulo"))) : 1)
			.getResultList();
		
		Map<String, List<Object[]>> mapa = new TreeMap<String, List<Object []>>();
		
		File file = new File("/home/saeti18/despesa.csv");
		FileWriter fw = null;
		try {
			fw = new FileWriter(file);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (Object [] row : rows) {
			
			String ente = String.valueOf(row[1]);
			
			if (mapa.get(ente) == null) {
				
				List<Object[]> lista = new ArrayList<Object[]>();
				lista.add(row);
				
				mapa.put(ente, lista);
			} else {
				mapa.get(ente).add(row);
			}
		}
		
		for (Map.Entry<String, List<Object[]>> entry : mapa.entrySet()) {
			
			String key = entry.getKey();
			
			rows = entry.getValue();
			
			List<RelatorioB02VO> dados = new ArrayList<RelatorioB02VO>();
			
			// Despesas Correntes - ^3[123]
			
			dados.add(new RelatorioB02VO("Despesas Correntes (VIII)", "SUBTOTAL DAS DESPESAS (XI) = (VIII + IX + X)", "Pessoal e Encargos Sociais", 
					soma(1, "^31", rows, 3), soma(1, "^31", rows, 4), soma(1, "^31", rows, 5), soma(1, "^31", rows, 6), soma(1, "^31", rows, 7)));
			
			dados.add(new RelatorioB02VO("Despesas Correntes (VIII)", "SUBTOTAL DAS DESPESAS (XI) = (VIII + IX + X)", "Juros e Encargos da Dívida", 
					soma(1, "^32", rows, 3), soma(1, "^32", rows, 4), soma(1, "^32", rows, 5), soma(1, "^32", rows, 6), soma(1, "^32", rows, 7)));
			
			dados.add(new RelatorioB02VO("Despesas Correntes (VIII)", "SUBTOTAL DAS DESPESAS (XI) = (VIII + IX + X)", "Outras Despesas Correntes", 
					soma(1, "^33", rows, 3), soma(1, "^33", rows, 4), soma(1, "^33", rows, 5), soma(1, "^33", rows, 6), soma(1, "^33", rows, 7)));
			
			// Despesas de Capital - ^4[456]
			
			dados.add(new RelatorioB02VO("Despesas de Capital (IX)", "SUBTOTAL DAS DESPESAS (XI) = (VIII + IX + X)", "Investimentos", 
					soma(1, "^44", rows, 3), soma(1, "^44", rows, 4), soma(1, "^44", rows, 5), soma(1, "^44", rows, 6), soma(1, "^44", rows, 7)));
			
			dados.add(new RelatorioB02VO("Despesas de Capital (IX)", "SUBTOTAL DAS DESPESAS (XI) = (VIII + IX + X)", "Inversões Financeiras", 
					soma(1, "^45", rows, 3), soma(1, "^45", rows, 4), soma(1, "^45", rows, 5), soma(1, "^45", rows, 6), soma(1, "^45", rows, 7)));
			
			dados.add(new RelatorioB02VO("Despesas de Capital (IX)", "SUBTOTAL DAS DESPESAS (XI) = (VIII + IX + X)", "Amortização da Dívida", 
					soma(1, "^46", rows, 3), soma(1, "^46", rows, 4), soma(1, "^46", rows, 5), soma(1, "^46", rows, 6), soma(1, "^46", rows, 7)));
			
			// Reserva de Contingência - 99999900
			
			dados.add(new RelatorioB02VO("Reserva de Contingência (X)", "SUBTOTAL DAS DESPESAS (XI) = (VIII + IX + X)", null, 
					soma(1, "99999900", rows, 3), soma(1, "99999900", rows, 4), soma(1, "99999900", rows, 5), soma(1, "99999900", rows, 6), soma(1, "99999900", rows, 7)));
			
			// Amortização da Dívida/Refinanciamento
			
			RelatorioB02VO adim = new RelatorioB02VO("Amortização da Dívida/Refinanciamento (XII)", "SUBTOTAL COM REFINANCIAMENTO (XIII) = (XI + XII)", "Amortização da Dívida Interna",
					soma(2, "46907600", rows, 3), soma(2, "46907600", rows, 4), soma(2, "46907600", rows, 5), soma(2, "46907600", rows, 6), soma(2, "46907600", rows, 7));
			
			adim.setModalidade("Dívida Mobiliária");
			
			dados.add(adim);
			
			RelatorioB02VO adio = new RelatorioB02VO("Amortização da Dívida/Refinanciamento (XII)", "SUBTOTAL COM REFINANCIAMENTO (XIII) = (XI + XII)", "Amortização da Dívida Interna",
					soma(2, "46907700", rows, 3), soma(2, "46907700", rows, 4), soma(2, "46907700", rows, 5), soma(2, "46907700", rows, 6), soma(2, "46907700", rows, 7));
			
			adio.setModalidade("Outras Dívidas");
			
			dados.add(adio);
			
			RelatorioB02VO adem = new RelatorioB02VO("Amortização da Dívida/Refinanciamento (XII)", "SUBTOTAL COM REFINANCIAMENTO (XIII) = (XI + XII)", "Amortização da Dívida Externa",
					soma(2, "46907600", rows, 3), soma(2, "46907600", rows, 4), soma(2, "46907600", rows, 5), soma(2, "46907600", rows, 6), soma(2, "46907600", rows, 7));
			
			adem.setModalidade("Dívida Mobiliária");
			
			dados.add(adem);
			
			RelatorioB02VO adeo = new RelatorioB02VO("Amortização da Dívida/Refinanciamento (XII)", "SUBTOTAL COM REFINANCIAMENTO (XIII) = (XI + XII)", "Amortização da Dívida Externa",
					soma(2, "46907700", rows, 3), soma(2, "46907700", rows, 4), soma(2, "46907700", rows, 5), soma(2, "46907700", rows, 6), soma(2, "46907700", rows, 7));
			
			adeo.setModalidade("Outras Dívidas");
			
			dados.add(adeo);
			
			BigDecimal din = dados.stream().filter(x -> x.getDotacaoInicial() != null).map(RelatorioB02VO::getDotacaoInicial).reduce(BigDecimal.ZERO, BigDecimal::add);
			BigDecimal dat = dados.stream().filter(x -> x.getDotacaoAtualizada() != null).map(RelatorioB02VO::getDotacaoAtualizada).reduce(BigDecimal.ZERO, BigDecimal::add);
			BigDecimal dee = dados.stream().filter(x -> x.getDespesasEmpenhadas() != null).map(RelatorioB02VO::getDespesasEmpenhadas).reduce(BigDecimal.ZERO, BigDecimal::add);
			BigDecimal del = dados.stream().filter(x -> x.getDespesasLiquidadas() != null).map(RelatorioB02VO::getDespesasLiquidadas).reduce(BigDecimal.ZERO, BigDecimal::add);
			BigDecimal dep = dados.stream().filter(x -> x.getDespesasPagas() != null).map(RelatorioB02VO::getDespesasPagas).reduce(BigDecimal.ZERO, BigDecimal::add);
			
			try {
				fw.write(key + "," + din + "," + dat + "," + dee + "," + del + "," + dep + "\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try {
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	private List<RelatorioB03VO> recuperarDadosPagina3(List<Integer> listaIdsUnidades, Map<String, Object> params) {
		
		String sql = 
		
		"select  " + 
			"1 id, regexp_replace(natureza_despesa, '[.]', '', 'g') nd,  " + 
			"sum(inscrito_anterior) inscrito_anterior, sum(inscrito_31_anterior) inscrito_31_anterior,  " + 
			"sum(liquidado) liquidado, sum(pago) pago, sum(cancelado) cancelado  " + 
		"from  " + 
			"prestacao.bo03  " + 
		"where  " + 
			"unidade_id in (:unidades) and  " + 
			"((:modulo is null) or (modulo_id = :modulo)) and " +
			"regexp_replace(natureza_despesa, '[.]', '', 'g') ~ '^(3[123]|4[456])' " +
		"group by  " + 
			"regexp_replace(natureza_despesa, '[.]', '', 'g')  " +
		"order by  " +
			"id, regexp_replace(natureza_despesa, '[.]', '', 'g')";
		
		@SuppressWarnings("unchecked")
		List<Object[]> rows = entityManager.createNativeQuery(sql)
		.setParameter("unidades", listaIdsUnidades)
		.setParameter("modulo", params.get("modulo") != null ? Integer.valueOf(String.valueOf(params.get("modulo"))) : 1)
		.getResultList();
		
		List<RelatorioB03VO> dados = new ArrayList<RelatorioB03VO>();
		
		// Despesas Correntes
		
		dados.add(new RelatorioB03VO("Despesas Correntes", "Pessoal e Encargos Sociais", 
				soma(1, "^31", rows, 2), soma(1, "^31", rows, 3), soma(1, "^31", rows, 4), soma(1, "^31", rows, 5), soma(1, "^31", rows, 6)));
		
		dados.add(new RelatorioB03VO("Despesas Correntes", "Juros e Encargos da Dívida", 
				soma(1, "^32", rows, 2), soma(1, "^32", rows, 3), soma(1, "^32", rows, 4), soma(1, "^32", rows, 5), soma(1, "^32", rows, 6)));
		
		dados.add(new RelatorioB03VO("Despesas Correntes", "Outras Despesas Correntes", 
				soma(1, "^33", rows, 2), soma(1, "^33", rows, 3), soma(1, "^33", rows, 4), soma(1, "^33", rows, 5), soma(1, "^33", rows, 6)));
		
		// Despesas de Capital
		
		dados.add(new RelatorioB03VO("Despesas de Capital", "Investimentos", 
				soma(1, "^44", rows, 2), soma(1, "^44", rows, 3), soma(1, "^44", rows, 4), soma(1, "^44", rows, 5), soma(1, "^44", rows, 6)));
		
		dados.add(new RelatorioB03VO("Despesas de Capital", "Inversões Financeiras", 
				soma(1, "^45", rows, 2), soma(1, "^45", rows, 3), soma(1, "^45", rows, 4), soma(1, "^45", rows, 5), soma(1, "^45", rows, 6)));
		
		dados.add(new RelatorioB03VO("Despesas de Capital", "Amortização da Dívida", 
				soma(1, "^46", rows, 2), soma(1, "^46", rows, 3), soma(1, "^46", rows, 4), soma(1, "^46", rows, 5), soma(1, "^46", rows, 6)));
		
		return dados;
	}
	
	private List<RelatorioB03VO> recuperarDadosPagina4(List<Integer> listaIdsUnidades, Map<String, Object> params) {
		
		String sql = 
				
		"select  " + 
			"1 id, regexp_replace(natureza_despesa, '[.]', '', 'g') nd,  " + 
			"sum(inscrito_anterior) inscrito_anterior, sum(inscrito_31_anterior) inscrito_31_anterior,  " + 
			"sum(pago) pago, sum(cancelado) cancelado  " + 
		"from  " + 
			"prestacao.bo04 " + 
		"where  " + 
			"unidade_id in (:unidades) and  " + 
			"((:modulo is null) or (modulo_id = :modulo)) and " +
			"regexp_replace(natureza_despesa, '[.]', '', 'g') ~ '^(3[123]|4[456])' " +
		"group by  " + 
			"regexp_replace(natureza_despesa, '[.]', '', 'g')  " +
		"order by  " +
			"id, regexp_replace(natureza_despesa, '[.]', '', 'g')";
		
		@SuppressWarnings("unchecked")
		List<Object[]> rows = entityManager.createNativeQuery(sql)
		.setParameter("unidades", listaIdsUnidades)
		.setParameter("modulo", params.get("modulo") != null ? Integer.valueOf(String.valueOf(params.get("modulo"))) : 1)
		.getResultList();
		
		List<RelatorioB03VO> dados = new ArrayList<RelatorioB03VO>();
		
		// Despesas Correntes
		
		dados.add(new RelatorioB03VO("Despesas Correntes", "Pessoal e Encargos Sociais", 
				soma(1, "^31", rows, 2), soma(1, "^31", rows, 3), null, soma(1, "^31", rows, 4), soma(1, "^31", rows, 5)));
		
		dados.add(new RelatorioB03VO("Despesas Correntes", "Juros e Encargos da Dívida", 
				soma(1, "^32", rows, 2), soma(1, "^32", rows, 3), null, soma(1, "^32", rows, 4), soma(1, "^32", rows, 5)));
		
		dados.add(new RelatorioB03VO("Despesas Correntes", "Outras Despesas Correntes", 
				soma(1, "^33", rows, 2), soma(1, "^33", rows, 3), null, soma(1, "^33", rows, 4), soma(1, "^33", rows, 5)));
		
		// Despesas de Capital
		
		dados.add(new RelatorioB03VO("Despesas de Capital", "Investimentos", 
				soma(1, "^44", rows, 2), soma(1, "^44", rows, 3), null, soma(1, "^44", rows, 4), soma(1, "^44", rows, 5)));
		
		dados.add(new RelatorioB03VO("Despesas de Capital", "Inversões Financeiras", 
				soma(1, "^45", rows, 2), soma(1, "^45", rows, 3), null, soma(1, "^45", rows, 4), soma(1, "^45", rows, 5)));
		
		dados.add(new RelatorioB03VO("Despesas de Capital", "Amortização da Dívida", 
				soma(1, "^46", rows, 2), soma(1, "^46", rows, 3), null, soma(1, "^46", rows, 4), soma(1, "^46", rows, 5)));
		
		return dados;
	}
	
	@Override
	public String getNomeRelatorio() {
		return "relatoriobo01.jasper";
	}
}