package br.gov.ma.tce.reunire.dao.impl.cam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;

import br.gov.ma.tce.reunire.dao.DemonstrativoDao;
import br.gov.ma.tce.reunire.dao.impl.PrestacaoDaoImpl;
import br.gov.ma.tce.reunire.model.vo.cam.RelatorioCAM25VO;
import br.gov.ma.tce.reunire.model.vo.gestor.UnidadeVO;

@Stateless
public class RelatorioCAM25DaoImpl extends PrestacaoDaoImpl<RelatorioCAM25VO> implements DemonstrativoDao<RelatorioCAM25VO> {

	@SuppressWarnings("unchecked")
	@Override
	public List<RelatorioCAM25VO> recuperaDados(Map<String, Object> params) {
		
		String sql = 
				
		params.get("exercicio") != null && ((Integer)params.get("exercicio")).equals(new Integer(2017)) ?
				
		"select " + 
		"    emp.unidade_id, emp.numero_empenho ne, initcap(mod.nome) modalidade, emp.data_contabilizacao, nd.codigo, " + 
		"    (case  " + 
		"        when credor_extraordinario is not null then credor_extraordinario " + 
		"        when credor_cpf_cnpj is not null then credor_cpf_cnpj " + 
		"        when credor_unidade_id is not null then cast(credor_unidade_id as text) " + 
		"    end) credor, emp.valor val_emp, " + 
		"    coalesce(ref.valor, 0) val_ref, coalesce(anu.valor, 0) val_anu, " + 
		"    coalesce(liq.valor, 0) val_liq, coalesce(pag.valor, 0) val_pag, " + 
		"    (case  " + 
		"    	when coalesce(pag.valor, 0) > 0 then coalesce(liq.valor, 0) - coalesce(pag.valor, 0) " + 
		"    	when coalesce(liq.valor, 0) > 0 then coalesce(liq.valor, 0) - coalesce(emp.valor, 0) - coalesce(anu.valor, 0) - coalesce(ref.valor, 0) " + 
		"    	when coalesce(anu.valor, 0) > 0 then emp.valor + coalesce(ref.valor, 0) - coalesce(anu.valor, 0) " + 
		"    	when coalesce(ref.valor, 0) > 0 then emp.valor + coalesce(ref.valor, 0)  " + 
		"    	else emp.valor " + 
		"    end) saldo " + 
		"from  " + 
		"	remessa.empenho emp " + 
		"left join remessa.modalidade_empenho mod on " + 
		"	mod.modalidade_empenho_id = emp.modalidade_empenho_id " + 
		"left join remessa.natureza_despesa nd on  " + 
		"	nd.natureza_despesa_id = emp.natureza_despesa_id " + 
		"left join remessa.fonte_recursos fon on " + 
		"	fon.fonte_recursos_id = emp.fonte_recurso_id " + 
		"left join ( " + 
		"    select  " + 
		"		ref.empenho_id, sum(ref.valor) valor " + 
		"    from  " + 
		"		remessa.empenho_reforco ref " + 
		"    group by " + 
		"		ref.empenho_id " + 
		") ref on ref.empenho_id = emp.empenho_id " + 
		"left join ( " + 
		"	select  " + 
		"		anu.empenho_id, sum(anu.valor) valor  " + 
		"	from  " + 
		"		remessa.empenho_anulado anu " + 
		"	group by " + 
		"		anu.empenho_id " + 
		") anu on anu.empenho_id = emp.empenho_id " + 
		"left join ( " + 
		"	select " + 
		"		liq.empenho_id, sum(sub.valor) valor " + 
		"	from  " + 
		"		remessa.liquidacao liq " + 
		"	inner join remessa.subelemento_liquidacao sub on " + 
		"		sub.liquidacao_id = liq.liquidacao_id " + 
		"	left join remessa.liquidacao_estorno est on " + 
		"		est.liquidacao_id = liq.liquidacao_id " + 
		"	where " + 
		"		est.liquidacao_id is null and liq.empenho_id is not null " + 
		"	group by " + 
		"		liq.empenho_id " + 
		") liq on liq.empenho_id = emp.empenho_id " + 
		"left join ( " + 
		"    select  " + 
		"		liq.empenho_id, sum(coalesce(sub.valor, 0) - coalesce(subdev.valor, 0)) valor " + 
		"    from  " + 
		"		remessa.pagamento pag " + 
		"    inner join remessa.liquidacao liq on " + 
		"		liq.liquidacao_id = pag.liquidacao_id " + 
		"    inner join remessa.subelemento_liquidacao sub on " + 
		"		sub.liquidacao_id = liq.liquidacao_id " + 
		"    left join remessa.pagamento_estornado est on " + 
		"		est.pagamento_id = pag.pagamento_id " + 
		"    left join remessa.pagamento_devolucao dev on " + 
		"		dev.pagamento_id = pag.pagamento_id " + 
		"    left join remessa.subelemento_devolucao_pagamento subdev on " + 
		"		subdev.pagamento_devolucao_id = dev.pagamento_devolucao_id " + 
		"    where " + 
		"		est.pagamento_id is null and liq.empenho_id is not null " + 
		"    group by " + 
		"		liq.empenho_id " + 
		") pag on pag.empenho_id = emp.empenho_id " + 
		"where " + 
		"	unidade_id in (:unidades) " + 
		"order by " + 
		"	unidade_id, data_contabilizacao, numero_empenho"
		
		:
		
		"select " + 
			"emp.unidade_id, emp.numero_empenho ne, initcap(mod.nome) modalidade, emp.data_contabilizacao, " + 
			"regexp_replace(cast(emp.natureza_despesa as text), '([[:digit:]]{1})([[:digit:]]{1})([[:digit:]]{2})([[:digit:]]{2})([[:digit:]]{2})', '\\1.\\2.\\3.\\4.\\5') codigo, " +
			"(case " + 
				"when emp.credor_extraordinario is not null then credor_extraordinario " +
				"when emp.credor_cpf_cnpj is not null and length(cast(emp.credor_cpf_cnpj as text)) = 11 then regexp_replace(cast(credor_cpf_cnpj as text), '([[:digit:]]{3})([[:digit:]]{3})([[:digit:]]{3})([[:digit:]]{2})', '\\1.\\2.\\3-\\4') " +
				"when emp.credor_cpf_cnpj is not null and length(cast(emp.credor_cpf_cnpj as text)) > 11 then regexp_replace(lpad(cast(credor_cpf_cnpj as text), '14', '0'), '([[:digit:]]{2})([[:digit:]]{3})([[:digit:]]{3})([[:digit:]]{4})([[:digit:]]{2})', '\\1.\\2.\\3/\\4-\\5')  " +
				"when emp.unidade_ug is not null then cast(emp.unidade_ug as text) " +
				"else cast(emp.credor_cpf_cnpj as text) " +
			"end) credor, " + 
			"emp.valor val_emp, coalesce(ref.valor, 0) val_ref, coalesce(anu.valor, 0) val_anu, coalesce(liq.valor, 0) val_liq, coalesce(pag.valor, 0) val_pag, " +
			 "(case " +  
			    	"when coalesce(pag.valor, 0) > 0 then coalesce(liq.valor, 0) - coalesce(pag.valor, 0) " + 
			    	"when coalesce(liq.valor, 0) > 0 then coalesce(liq.valor, 0) - coalesce(emp.valor, 0) - coalesce(anu.valor, 0) - coalesce(ref.valor, 0) " + 
			    	"when coalesce(anu.valor, 0) > 0 then emp.valor + coalesce(ref.valor, 0) - coalesce(anu.valor, 0) " + 
			    	"when coalesce(ref.valor, 0) > 0 then emp.valor + coalesce(ref.valor, 0) " +  
			    	"else emp.valor " + 
			    "end) saldo " +
		"from " + 
			"sae_importacao.empenho emp " +
		"left join remessa.modalidade_empenho mod on mod.modalidade_empenho_id = emp.modalidade_empenho " +
		"left join ( " +
			"select " +
				"ref.empenho_id, sum(ref.valor) valor " +
			"from sae_importacao.reforco_empenho ref " +
			"group by " +
				"ref.empenho_id " +
		") ref on ref.empenho_id = emp.empenho_id " +
		"left join ( " +
			"select " +
				"anu.empenho_id, sum(anu.valor) valor " +
			"from sae_importacao.anulacao_empenho anu " +
			"group by " + 
				"anu.empenho_id " +
		") anu on anu.empenho_id = emp.empenho_id " +
		"left join ( " +
			"select " + 
				"liq.empenho_id, sum(sub.valor_subelemento) valor " +
			"from " +
				"sae_importacao.liquidacao liq " +
			"inner join sae_importacao.sublemento_liquidacao sub on " +
				"sub.liquidacao_id = liq.liquidacao_id " +
			"left join sae_importacao.estorno_liquidacao est on " +
				"est.liquidacao_id = liq.liquidacao_id " +
			"where " +
				"est.liquidacao_id is null and liq.empenho_id is not null " +
			"group by " +
				"liq.empenho_id " +
		") liq on liq.empenho_id = emp.empenho_id " +
		"left join ( " +
			"select " + 
				"liq.empenho_id, sum(coalesce(sub.valor_subelemento, 0) - coalesce(dev.valor, 0)) valor " +
			"from  " +
				"sae_importacao.pagamento pag " +
			"inner join sae_importacao.liquidacao liq on liq.liquidacao_id = pag.liquidacao_id " +
			"inner join sae_importacao.sublemento_liquidacao sub on sub.liquidacao_id = liq.liquidacao_id " +
			"left join sae_importacao.estorno_pagamento est on est.pagamento_id = pag.pagamento_id " +
			"left join sae_importacao.devolucao_pagamento dev on dev.pagamento_id = pag.pagamento_id " +
			"where " +
				"est.pagamento_id is null and liq.empenho_id is not null " +
			"group by " +
				"liq.empenho_id " +
		") pag on pag.empenho_id = emp.empenho_id " +
		"where " +
			"emp.unidade_id in (:unidades) " +
		"order by " +
			"emp.unidade_id, emp.data_contabilizacao, emp.numero_empenho";
		
		List<UnidadeVO> listaUnidades = recuperarUnidades(params);
		List<Integer> listaIdsUnidades = extrairIds(listaUnidades);
		
		List<Object[]> rows = entityManager.createNativeQuery(sql)
				.setParameter("unidades", listaIdsUnidades)
				.getResultList();
		
		List<RelatorioCAM25VO> dados = new ArrayList<RelatorioCAM25VO>();
		
		for(Object[] row : rows) {
			
			RelatorioCAM25VO dado = new RelatorioCAM25VO();
			
			Optional<UnidadeVO> unidade = listaUnidades.stream().filter(u -> u.getId().equals(Integer.parseInt(String.valueOf(row[0])))).findFirst();
			
			dado.setIdUnidade(Integer.parseInt(String.valueOf(row[0])));
			dado.setDescricaoUnidade(unidade != null ? unidade.get().getNome().toUpperCase() : "");
			
			if (unidade != null) {
				
				dado.setIdOrgao(unidade.get().getOrgao().getId());
				dado.setDescricaoOrgao(unidade.get().getOrgao().getNome());
			}
			
			dado.setNumeroEmpenho(String.valueOf(row[1]));
			dado.setModalidade(String.valueOf(row[2]));
			dado.setDataContabil(toDate(row[3]));
			dado.setNaturezaDespesa(String.valueOf(row[4]));
			dado.setCredor(row[5] != null ? String.valueOf(row[5]) : "");
			dado.setValorEmpenhado(toBigDecimal(row[6]));
			dado.setValorReforcado(toBigDecimal(row[7]));
			dado.setValorAnulado(toBigDecimal(row[8]));
			dado.setValorLiquidado(toBigDecimal(row[9]));
			dado.setValorPago(toBigDecimal(row[10]));
			dado.setSaldo(toBigDecimal(row[11]));
			
			dados.add(dado);
		}
		
		return dados;
	}

	@Override
	public String getNomeRelatorio() {
		return "relatoriocam25.jasper";
	}
}