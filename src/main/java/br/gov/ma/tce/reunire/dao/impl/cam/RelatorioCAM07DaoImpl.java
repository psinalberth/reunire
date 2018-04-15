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
import br.gov.ma.tce.reunire.model.vo.cam.RelatorioCAM07VO;
import br.gov.ma.tce.reunire.model.vo.gestor.UnidadeVO;

@Stateless
public class RelatorioCAM07DaoImpl extends PrestacaoDaoImpl<RelatorioCAM07VO> implements DemonstrativoDao<RelatorioCAM07VO> {

	@Override
	@SuppressWarnings("unchecked")
	public List<RelatorioCAM07VO> recuperaDados(Map<String, Object> params) {
		
		List<UnidadeVO> listaUnidades = recuperarUnidades(params);
		List<Integer> listaIdsUnidades = extrairIds(listaUnidades);
		
		String sql = 
		
		"select " +
			"unidade_id, " +
			"(case " + 
				"when length(cpf_cnpj_credor) = 11 then regexp_replace(cpf_cnpj_credor, '([[:digit:]]{3})([[:digit:]]{3})([[:digit:]]{3})([[:digit:]]{2})', '\\1.\\2.\\3-\\4') " +
				"when length(cpf_cnpj_credor) = 14 then regexp_replace(cpf_cnpj_credor, '([[:digit:]]{2})([[:digit:]]{3})([[:digit:]]{3})([[:digit:]]{4})([[:digit:]]{2})', '\\1.\\2.\\3/\\4-\\5') " +
				"else cpf_cnpj_credor " +
			"end) cpf_cnpj_credor, upper(nome) nome, " +
			"data_vencimento, valor_contrato, parcela " +
		"from " + 
			"prestacao.cam07 " +
		"where " +
			"unidade_id in (:unidades) and " +
			"((:modulo is null) or (modulo_id = :modulo)) " +
		"order by " +
			"unidade_id, cpf_cnpj_credor, data_vencimento";
		
		
		List<Object[]> rows = entityManager.createNativeQuery(sql)
				.setParameter("unidades", listaIdsUnidades)
				.setParameter("modulo", 1)
				.getResultList();
		
		List<RelatorioCAM07VO> dados = new ArrayList<RelatorioCAM07VO>(rows.size());
		
		params.put("exercicio", 2017);
		
		for (Object [] row : rows) {
			
			RelatorioCAM07VO dado = new RelatorioCAM07VO();
			
			Optional<UnidadeVO> unidade = listaUnidades.stream().filter(u -> u.getId().equals(Integer.parseInt(String.valueOf(row[0])))).findFirst();
			
			dado.setIdUnidade(Integer.parseInt(String.valueOf(row[0])));
			dado.setDescricaoUnidade(unidade != null ? unidade.get().getNome().toUpperCase() : "");
			dado.setCredor(String.valueOf(row[1]));
			
			Calendar date = Calendar.getInstance();
			date.setTimeInMillis(((Timestamp) row[3]).getTime());
			
			dado.setDataVencimento(date.getTime());
			dado.setValorContrato(row[4] != null ? new BigDecimal(String.valueOf(row[4])) : null);
			dado.setParcelasResgatadas(row[5] != null ? new BigDecimal(String.valueOf(row[5])) : null);
			
			dados.add(dado);
		}
		
		return dados;
	}

	@Override
	public String getNomeRelatorio() {
		return "relatoriocam07.jasper";
	}
}
