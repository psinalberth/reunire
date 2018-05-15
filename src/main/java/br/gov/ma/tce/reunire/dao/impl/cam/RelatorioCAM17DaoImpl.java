package br.gov.ma.tce.reunire.dao.impl.cam;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;

import br.gov.ma.tce.reunire.dao.DemonstrativoDao;
import br.gov.ma.tce.reunire.dao.impl.PrestacaoDaoImpl;
import br.gov.ma.tce.reunire.model.vo.cam.RelatorioCAM17VO;
import br.gov.ma.tce.reunire.model.vo.gestor.UnidadeVO;

@Stateless
public class RelatorioCAM17DaoImpl extends PrestacaoDaoImpl<RelatorioCAM17VO> implements DemonstrativoDao<RelatorioCAM17VO> {
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RelatorioCAM17VO> recuperaDados(Map<String, Object> params) {
		
		List<UnidadeVO> listaUnidades = recuperarUnidades(params);
		List<Integer> listaIdsUnidades = extrairIds(listaUnidades);
		
		String sql = 
				
		"select " +
			"unidade_id, numero_processo_judicial, data_acao, " + 
			"(case " +
				"when length(cpf_cnpj_credor) = 11 then regexp_replace(cpf_cnpj_credor, '([[:digit:]]{3})([[:digit:]]{3})([[:digit:]]{3})([[:digit:]]{2})', '\\1.\\2.\\3-\\4') " +
				"when length(cpf_cnpj_credor) = 14 then regexp_replace(cpf_cnpj_credor, '([[:digit:]]{2})([[:digit:]]{3})([[:digit:]]{3})([[:digit:]]{4})([[:digit:]]{2})', '\\1.\\2.\\3/\\4-\\5') " +
				"else cpf_cnpj_credor " +
			"end) cpf_cnpj_credor, " +
			"objeto, data_pagamento, valor_estimado " +
		"from " +
			"prestacao.cam17 " +
		"where " +
			"unidade_id in (:unidades) and " +
			"((:modulo is null) or (modulo_id = :modulo))";
		
		List<Object[]> rows = entityManager.createNativeQuery(sql)
				.setParameter("unidades", listaIdsUnidades)
				.setParameter("modulo", 1)
				.getResultList();
		
		List<RelatorioCAM17VO> dados = new ArrayList<RelatorioCAM17VO>(rows.size());
		
		for (Object[] row : rows) {
			
			RelatorioCAM17VO dado = new RelatorioCAM17VO();
			
			Optional<UnidadeVO> unidade = listaUnidades.stream().filter(u -> u.getId().equals(Integer.parseInt(String.valueOf(row[0])))).findFirst();
			
			dado.setIdUnidade(Integer.parseInt(String.valueOf(row[0])));
			dado.setDescricaoUnidade(unidade != null ? unidade.get().getNome().toUpperCase() : "");
			dado.setNumeroProcesso(String.valueOf(row[1]));
			
			Calendar date = Calendar.getInstance();
			date.setTimeInMillis(((Timestamp) row[2]).getTime());
			
			dado.setDataAcao(date.get(Calendar.YEAR) > 1899 ? date.getTime() : null);
			dado.setIdentificacaoCredor(String.valueOf(row[3]));
			dado.setObjeto(String.valueOf(row[4]));
			
			date.setTimeInMillis(((Timestamp) row[5]).getTime());
			
			dado.setDataPagamento(date.get(Calendar.YEAR) > 1899 ? date.getTime() : null);
			dado.setValorPagamento(row[6] != null ? new BigDecimal(String.valueOf(row[6])) : null);
			
			dados.add(dado);
		}
		
		return dados;
	}

	@Override
	public String getNomeRelatorio() {
		return "relatoriocam17.jasper";
	}
}