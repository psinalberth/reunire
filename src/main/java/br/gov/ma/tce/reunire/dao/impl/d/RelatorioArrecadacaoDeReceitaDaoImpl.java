package br.gov.ma.tce.reunire.dao.impl.d;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;

import br.gov.ma.tce.reunire.dao.DemonstrativoDao;
import br.gov.ma.tce.reunire.dao.impl.PrestacaoDaoImpl;
import br.gov.ma.tce.reunire.model.vo.d.RelatorioArrecadacaoReceitaVO;
import br.gov.ma.tce.reunire.model.vo.gestor.UnidadeVO;

@Stateless
public class RelatorioArrecadacaoDeReceitaDaoImpl extends PrestacaoDaoImpl<RelatorioArrecadacaoReceitaVO> implements DemonstrativoDao<RelatorioArrecadacaoReceitaVO> {

	@Override
	public String getNomeRelatorio() {
		return "relatorioarrecadacaoreceita.jasper";
	}

	@Override
	public List<RelatorioArrecadacaoReceitaVO> recuperaDados(Map<String, Object> params) {
		
		String sql = 
				
		" select receita.unidade_id, receita.data_contabilizacao, receita.numero_documento, receita.historico, " +
		"	(case        " +
		"		when item.credor_cpf_cnpj is not null and length(cast(item.credor_cpf_cnpj as text)) = 11 then regexp_replace(cast(credor_cpf_cnpj as text), '([[:digit:]]{3})([[:digit:]]{3})([[:digit:]]{3})([[:digit:]]{2})', '\\1.\\2.\\3-\\4') " +
		"		when item.credor_cpf_cnpj is not null and length(cast(item.credor_cpf_cnpj as text)) > 11 then regexp_replace(lpad(cast(credor_cpf_cnpj as text), '14', '0'), '([[:digit:]]{2})([[:digit:]]{3})([[:digit:]]{3})([[:digit:]]{4})([[:digit:]]{2})', '\\1.\\2.\\3/\\4-\\5')  " +
		"		when item.credor_extraordinario is not null then item.credor_extraordinario " + 
		"		when item.credor_unidade_gestora_id is not null then cast(item.credor_unidade_gestora_id as text) " + 
		"	end)  credor, " + 
		"	regexp_replace(cast(item.natureza_receita_id as text),  '([[:digit:]]{1})([[:digit:]]{1})([[:digit:]]{2})([[:digit:]]{2})([[:digit:]]{2})', '\\1.\\2.\\3.\\4.\\5'), " + 
		"	natureza.nome as descricaoNaturezaReceita, (item.valor - coalesce(anulacao.valorAnulacao, 0) - coalesce(rest.valorRestituicao, 0)) as valorArrecadacao " + 
		" from remessa.arrecadacao_receita receita " + 
		" 	inner join remessa.item_receita item on item.receita_id = receita.arrecadacao_receita_id " + 
		" 	left join remessa.natureza_receita natureza on natureza.natureza_receita_id = item.natureza_receita_id " +
		"	left join ( " +
		"		select " +
		"			natureza.natureza_receita_id ,  sum(anulacao.valor) valorAnulacao " +
		"		from remessa.receita_anulacao anulacao " +
		"		inner join remessa.item_receita item on " + 	
		"		anulacao.item_receita_id = item.item_receita_id " +
		"		inner join remessa.natureza_receita natureza on " + 
		"		natureza.natureza_receita_id = item.natureza_receita_id" +
		"		group by " +
		"			natureza.natureza_receita_id  " +
		"	) anulacao on anulacao.natureza_receita_id = item.natureza_receita_id " +
		"	left join ( " +
		"		select " +
		"			natureza.natureza_receita_id , sum(rest.valor) valorRestituicao " +
		"		from remessa.receita_restituicao rest " +
		"		inner join remessa.item_receita item on " +
		"		rest.item_receita_id = item.item_receita_id " +
		"		inner join remessa.natureza_receita natureza on " + 
		"		natureza.natureza_receita_id = item.natureza_receita_id" +	
		"		group by " +
		"			natureza.natureza_receita_id  " +
		"	) rest on rest.natureza_receita_id = item.natureza_receita_id " +
		"	left join remessa.receita_estorno estorno on estorno.receita_id = receita.arrecadacao_receita_id " +
		" where receita.unidade_id in (:unidades) " + 
		" and estorno.receita_id is null and receita.arrecadacao_receita_id is not null" +  
		" order by item.natureza_receita_id	";
		
		List<RelatorioArrecadacaoReceitaVO> dados = new ArrayList<>();
		
		List<UnidadeVO> listaUnidades = recuperarUnidades(params);
		List<Integer> listaIdsUnidades = extrairIds(listaUnidades);
				
		@SuppressWarnings("unchecked")
		List<Object[]> lista = entityManager.createNativeQuery(sql)
				.setParameter("unidades", listaIdsUnidades)
				.getResultList();
		
		for(Object[] row : lista) {
			
			RelatorioArrecadacaoReceitaVO dado = new RelatorioArrecadacaoReceitaVO();
			
			Optional<UnidadeVO> unidade = listaUnidades.stream().filter(u -> u.getId().equals(Integer.parseInt(String.valueOf(row[0])))).findFirst();
			
			dado.setIdUnidade(Integer.parseInt(String.valueOf(row[0])));
			dado.setDescricaoUnidade(unidade != null ? unidade.get().getNome().toUpperCase() : "");
			dado.setDataContabil(toDate(row[1]));
			dado.setNumeroDocumento(row[2].toString());
			dado.setHistorico(row[3].toString());
			dado.setCredorNaturezaReceita(row[4].toString());
			dado.setCodigoNatureza(row[5].toString());
			dado.setDescricaoNaturezaReceita(row[6].toString());
			dado.setValorArrecadacao(toBigDecimal(row[7]));
			
			dados.add(dado);
		}
		
		return dados;
	}
}
