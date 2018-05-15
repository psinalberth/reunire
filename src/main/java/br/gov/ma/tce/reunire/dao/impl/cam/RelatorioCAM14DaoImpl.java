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
import br.gov.ma.tce.reunire.model.vo.cam.RelatorioCAM14VO;
import br.gov.ma.tce.reunire.model.vo.gestor.UnidadeVO;

@Stateless
public class RelatorioCAM14DaoImpl extends PrestacaoDaoImpl<RelatorioCAM14VO> implements DemonstrativoDao<RelatorioCAM14VO> {

	@SuppressWarnings("unchecked")
	@Override
	public List<RelatorioCAM14VO> recuperaDados(Map<String, Object> params) {
		
		List<UnidadeVO> listaUnidades = recuperarUnidades(params);
		List<Integer> listaIdsUnidades = extrairIds(listaUnidades);
		
		String sql =  
		
		"select " +
			"unidade_id, " +
			"(case " +
				"when length(cpf_cnpj_contratado) = 11 then regexp_replace(cpf_cnpj_contratado, '([[:digit:]]{3})([[:digit:]]{3})([[:digit:]]{3})([[:digit:]]{2})', '\\1.\\2.\\3-\\4') " + 
				"when length(cpf_cnpj_contratado) = 14 then regexp_replace(cpf_cnpj_contratado, '([[:digit:]]{2})([[:digit:]]{3})([[:digit:]]{3})([[:digit:]]{4})([[:digit:]]{2})', '\\1.\\2.\\3/\\4-\\5') " +
				"else cpf_cnpj_contratado " +
			"end) cpf_cnpj_contratado, " +
			"contratado, cnpj_contratante, contratante, data_contratacao, valor_contratacao, valor_pago " +
		"from ( " +
			"select " +
				"unidade_id, " +
				"(case when contratado ~ '^([0-9]+\\.?[0-9]*|\\.[0-9]+)$' then contratado else cpf_cnpj_contratado end) cpf_cnpj_contratado, " +
				"(case when contratado ~ '^([0-9]+\\.?[0-9]*|\\.[0-9]+)$' then cpf_cnpj_contratado else contratado end) contratado, " +
				"(case " +
					"when length(cnpj_contratante) = 11 then regexp_replace(cnpj_contratante, '([[:digit:]]{3})([[:digit:]]{3})([[:digit:]]{3})([[:digit:]]{2})', '\\1.\\2.\\3-\\4') " + 
					"when length(cnpj_contratante) = 14 then regexp_replace(cnpj_contratante, '([[:digit:]]{2})([[:digit:]]{3})([[:digit:]]{3})([[:digit:]]{4})([[:digit:]]{2})', '\\1.\\2.\\3/\\4-\\5') " +
				"end) cnpj_contratante, contratante, data_contratacao, valor_contratacao, valor_pago " +
			"from " + 
				"prestacao.cam14 " +
			"where " +
				"unidade_id in (:unidades) and " +
				"((:modulo is null) or (modulo_id = :modulo)) " +
			"order by " + 
				"unidade_id, data_contratacao, contratado, contratante) r";
		
		List<Object[]> rows = entityManager.createNativeQuery(sql)
				.setParameter("unidades", listaIdsUnidades)
				.setParameter("modulo", 1)
				.getResultList();
		
		List<RelatorioCAM14VO> dados = new ArrayList<RelatorioCAM14VO>(rows.size());
		
		for (Object[] row : rows) {
			
			RelatorioCAM14VO dado = new RelatorioCAM14VO();
			
			Optional<UnidadeVO> unidade = listaUnidades.stream().filter(u -> u.getId().equals(Integer.parseInt(String.valueOf(row[0])))).findFirst();
			
			dado.setIdUnidade(Integer.parseInt(String.valueOf(row[0])));
			dado.setDescricaoUnidade(unidade != null ? unidade.get().getNome().toUpperCase() : "");
			dado.setIdentificacaoContratado(String.valueOf(row[1]));
			dado.setNomeContratado(String.valueOf(row[2]));
			dado.setIdentificacaoContratante(row[3] == null ? null : String.valueOf(row[3]));
			dado.setNomeContratante(String.valueOf(row[4]));
			
			Calendar date = Calendar.getInstance();
			date.setTimeInMillis(((Timestamp) row[5]).getTime());
			
			dado.setDataContratacao(date.get(Calendar.YEAR) > 1899 ? date.getTime() : null);
			dado.setValorContratacao(row[6] != null ? new BigDecimal(String.valueOf(row[6])) : null);
			dado.setValorPago(row[7] != null ? new BigDecimal(String.valueOf(row[7])) : null);
			
			dados.add(dado);
		}
		
		return dados;
	}

	@Override
	public String getNomeRelatorio() {
		return "relatoriocam14.jasper";
	}
}