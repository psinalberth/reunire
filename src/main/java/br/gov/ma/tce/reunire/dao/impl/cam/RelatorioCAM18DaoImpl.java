package br.gov.ma.tce.reunire.dao.impl.cam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;

import br.gov.ma.tce.reunire.dao.DemonstrativoDao;
import br.gov.ma.tce.reunire.dao.impl.PrestacaoDaoImpl;
import br.gov.ma.tce.reunire.model.vo.cam.RelatorioCAM18VO;
import br.gov.ma.tce.reunire.model.vo.gestor.UnidadeVO;

@Stateless
public class RelatorioCAM18DaoImpl extends PrestacaoDaoImpl<RelatorioCAM18VO> implements DemonstrativoDao<RelatorioCAM18VO> {

	@SuppressWarnings("unchecked")
	@Override
	public List<RelatorioCAM18VO> recuperaDados(Map<String, Object> params) {
		
		List<UnidadeVO> listaUnidades = recuperarUnidades(params);
		List<Integer> listaIdsUnidades = extrairIds(listaUnidades);
		
		String sql =
				
		"select " + 
			"cam18.unidade_id, credito, provisao, " +
			"cam19.tipo, " +
			"(case " + 
				"when cam19.cpf_cnpj_credor ~ '000[[:digit:]]{3}$' then regexp_replace(lpad(cam19.cpf_cnpj_credor, 14, '0'), '([[:digit:]]{2})([[:digit:]]{3})([[:digit:]]{3})([[:digit:]]{4})([[:digit:]]{2})', '\\1.\\2.\\3/\\4-\\5') " +
				"when cam19.cpf_cnpj_credor ~ '^[0-9]*$' and length(cam19.cpf_cnpj_credor) > 1 then regexp_replace(lpad(cpf_cnpj_credor, 11, '0'), '([[:digit:]]{3})([[:digit:]]{3})([[:digit:]]{3})([[:digit:]]{2})', '\\1.\\2.\\3-\\4') " +
				"else cam19.cpf_cnpj_credor " +
			"end) credor, " +
			"cam19.valor_principal, cam19.valor_multa, cam19.valor_juro " +
		"from ( " +
			"select " +
				"cam18.modulo_id, cam18.unidade_id, sum(credito_inscrito) credito, sum(provisao_divida) provisao " +
			"from " + 
				"prestacao.cam18 cam18 " +
			"where " +
				"cam18.unidade_id in (:unidades) and " +
				"((:modulo is null) or (cam18.modulo_id = :modulo)) " +
			"group by " +
				"cam18.modulo_id, cam18.unidade_id " +
			"order by " +
				"cam18.modulo_id, cam18.unidade_id) cam18 " +
		"join ( " +
			"select " + 
				"modulo_id, unidade_id, tipo, cpf_cnpj_credor, " + 
				"sum(valor_principal) valor_principal, sum(valor_multa) valor_multa, sum(valor_juro) valor_juro " +
			"from " +
				"prestacao.cam19 " +
			"where " +
				"unidade_id in (:unidades) and " +
				"((:modulo is null) or (modulo_id = :modulo)) " +
			"group by " +
				"modulo_id, unidade_id, cpf_cnpj_credor, tipo " +
			"order by " +
				"modulo_id, unidade_id, cpf_cnpj_credor, tipo) cam19 " + 
		"on " +
			"cam18.modulo_id = cam19.modulo_id and " +
			"cam18.unidade_id = cam19.unidade_id";
		
		List<Object[]> rows = entityManager.createNativeQuery(sql)
				.setParameter("unidades", listaIdsUnidades)
				.setParameter("modulo", 1)
				.getResultList();
		
		List<RelatorioCAM18VO> dados = new ArrayList<RelatorioCAM18VO>(rows.size());
		
		for (Object[] row : rows) {
			
			RelatorioCAM18VO dado = new RelatorioCAM18VO();
			
			Optional<UnidadeVO> unidade = listaUnidades.stream().filter(u -> u.getId().equals(Integer.parseInt(String.valueOf(row[0])))).findFirst();
			
			dado.setIdUnidade(Integer.parseInt(String.valueOf(row[0])));
			dado.setDescricaoUnidade(unidade != null ? unidade.get().getNome().toUpperCase() : "");
			dado.setTipo(String.valueOf(row[3]));
			dado.setIdentificacaoDevedor(String.valueOf(row[4]));
			dado.setValorPrincipal(toBigDecimal(row[5]));
			dado.setValorMulta(toBigDecimal(row[6]));
			dado.setValorJuros(toBigDecimal(row[7]));
			
			dados.add(dado);
		}
		
		return dados;
	}

	@Override
	public String getNomeRelatorio() {
		return "relatoriocam18.jasper";
	}
}