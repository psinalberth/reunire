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
import br.gov.ma.tce.reunire.model.vo.cam.RelatorioCAM20VO;
import br.gov.ma.tce.reunire.model.vo.gestor.UnidadeVO;

@Stateless
public class RelatorioCAM20DaoImpl extends PrestacaoDaoImpl<RelatorioCAM20VO> implements DemonstrativoDao<RelatorioCAM20VO> {

	@SuppressWarnings("unchecked")
	@Override
	public List<RelatorioCAM20VO> recuperaDados(Map<String, Object> params) {
		
		List<UnidadeVO> listaUnidades = recuperarUnidades(params);
		List<Integer> listaIdsUnidades = extrairIds(listaUnidades);
		
		String sql = 
				
		"select " +
			"unidade_id, numero_deliberacao, " +
			"(case " +
				"when length(cpf_cnpj_devedor) = 11 then regexp_replace(cpf_cnpj_devedor, '([[:digit:]]{3})([[:digit:]]{3})([[:digit:]]{3})([[:digit:]]{2})', '\\1.\\2.\\3-\\4') " +
				"when length(cpf_cnpj_devedor) = 14 then regexp_replace(cpf_cnpj_devedor, '([[:digit:]]{2})([[:digit:]]{3})([[:digit:]]{3})([[:digit:]]{4})([[:digit:]]{2})', '\\1.\\2.\\3/\\4-\\5') " +
				"else cpf_cnpj_devedor " +
			"end) cpf_cnpj_devedor, " +
			"providencia, valor_ressarcido, data_ressarcido, valor_imputado " +
		"from " + 
			"prestacao.cam20 " +
		"where " +
			"unidade_id in (:unidades) and " +
			"((:modulo is null) or (modulo_id = :modulo)) " +
		"order by " +
			"unidade_id, numero_deliberacao, data_ressarcido";
		
		List<Object[]> rows = entityManager.createNativeQuery(sql)
				.setParameter("unidades", listaIdsUnidades)
				.setParameter("modulo", 1)
				.getResultList();
		
		List<RelatorioCAM20VO> dados = new ArrayList<RelatorioCAM20VO>(rows.size());
		
		for (Object[] row : rows) {
			
			RelatorioCAM20VO dado = new RelatorioCAM20VO();
			
			Optional<UnidadeVO> unidade = listaUnidades.stream().filter(u -> u.getId().equals(Integer.parseInt(String.valueOf(row[0])))).findFirst();
			
			dado.setIdUnidade(Integer.parseInt(String.valueOf(row[0])));
			dado.setDescricaoUnidade(unidade != null ? unidade.get().getNome().toUpperCase() : "");
			dado.setDeliberacao(String.valueOf(row[1]));
			dado.setIdentificacaoDevedor(String.valueOf(row[2]));
			dado.setProvidencias(String.valueOf(row[3]));
			dado.setValorRessarcido(row[4] != null ? new BigDecimal(String.valueOf(row[4])) : null);
			
			Calendar cal = Calendar.getInstance();
			
			if (row[5] != null) {
				cal.setTimeInMillis(((Timestamp)row[5]).getTime());
			}
			
			dado.setDataRessarcimento(row[5] != null ? cal.getTime() : null);
			dado.setValorDebito(row[6] != null ? new BigDecimal(String.valueOf(row[6])) : null);
			
			dados.add(dado);
		}
		
		return dados;
	}

	@Override
	public String getNomeRelatorio() {
		return "relatoriocam20.jasper";
	}
}