package br.gov.ma.tce.reunire.dao.impl.d;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;

import br.gov.ma.tce.reunire.dao.DemonstrativoDao;
import br.gov.ma.tce.reunire.dao.impl.PrestacaoDaoImpl;
import br.gov.ma.tce.reunire.model.vo.d.RelatorioD011AVO;
import br.gov.ma.tce.reunire.model.vo.gestor.UnidadeVO;

@Stateless
public class RelatorioD011DaoImpl extends PrestacaoDaoImpl<RelatorioD011AVO> implements DemonstrativoDao<RelatorioD011AVO> {
	
	@Override
	public String getNomeRelatorio() {
		return "relatoriod011.jasper";
	}

	@Override
	public List<RelatorioD011AVO> recuperaDados(Map<String, Object> params) {
		
		String sql = 
				
		"select  " + 
		"	d.unidade_orcamentaria_id, " + 
		"	d.funcao, d.subfuncao, fg.nome nome_funcao, sf.nome nome_subfuncao, " + 
		"	(case when prog.id_programa is not null then prog.codigo else d.programa end) programa, " + 
		"	(case when prog.id_programa is not null then prog.denominacao else 'NÃO INFORMADO' end) nome_programa, " + 
		"	(case when acao.id_acao is not null then acao.codigo_prefeitura else d.acao end) acao, " + 
		"	(case when acao.id_acao is not null then acao.descricao else d.descricao end) descricao, " + 
		"	coalesce(d.credito_suplementar, 0) credito_sup, coalesce(d.credito_extraordinario, 0) credito_ext,  " + 
		"	coalesce(d.despesa_realizada, 0) despesa_realizada " + 
		"from  " + 
		"	prestacao.d011 d " + 
		"left join gestor.unidade_ente und on   " + 
		"	und.unidade_id = d.unidade_orcamentaria_id   " + 
		"left join remessa.funcao fg on   " + 
		"	fg.funcao_id = d.funcao   " + 
		"left join remessa.subfuncao sf on   " + 
		"	sf.subfuncao_id = d.subfuncao   " + 
		"left join sae.sae_acao acao on   " + 
		"	acao.codigo_prefeitura = d.acao and   " + 
		"	acao.unidade = d.unidade_orcamentaria_id and   " + 
		"	acao.funcao = d.funcao and   " + 
		"	acao.subfuncao = d.subfuncao   " + 
		"left join sae.sae_programa prog on   " + 
		"	(prog.id_programa = acao.id_programa or prog.codigo = d.programa and prog.ente = und.ente_id)  " + 
		"where  " + 
		"	d.unidade_orcamentaria_id in (:unidades) and " + 
		"	((:modulo is null) or (d.modulo_id = :modulo)) " + 
		"order by " + 
		"	d.funcao, d.subfuncao, programa, acao";
		
		List<RelatorioD011AVO> dados = new ArrayList<>();
		
		List<UnidadeVO> listaUnidades = recuperarUnidades(params);
		List<Integer> listaIdsUnidades = extrairIds(listaUnidades);
		
		@SuppressWarnings("unchecked")
		List<Object[]> rows = entityManager.createNativeQuery(sql)
				.setParameter("unidades", listaIdsUnidades)
				.setParameter("modulo", params.get("modulo"))
				.getResultList();
		
		for(Object[] row : rows) {
			
			RelatorioD011AVO dado = new RelatorioD011AVO();
			
			Optional<UnidadeVO> unidade = listaUnidades.stream().filter(u -> u.getId().equals(Integer.parseInt(String.valueOf(row[0])))).findFirst();
			
			dado.setIdUnidade(Integer.parseInt(String.valueOf(row[0])));
			dado.setDescricaoUnidade(unidade != null ? unidade.get().getNome().toUpperCase() : "");
			
			dado.setFuncaoGoverno(row[1].toString());
			dado.setSubfuncaoGoverno(row[2].toString());
			dado.setNomeFuncao(row[3] != null ? String.valueOf(row[3]) : "NÃO INFORMADO");
			dado.setNomeSubFuncao(row[4] != null ? String.valueOf(row[4]) : "NÃO INFORMADO");
			dado.setPrograma(row[5] != null ? String.valueOf(row[5]) : "NÃO INFORMADO");
			dado.setNomePrograma(row[6] != null ? String.valueOf(row[6]) : "NÃO INFORMADO");
			dado.setAcao(row[7] != null ? String.valueOf(row[7]) : "NÃO INFORMADO");
			dado.setNomeAcao(row[8] != null ? String.valueOf(row[8]) : "NÃO INFORMADO");
			
			dado.setValorCreditoOrcamentario(toBigDecimal(row[9]));
			dado.setValorCreditoEspecial(toBigDecimal(row[10]));
			dado.setValorDespesaRealizada(toBigDecimal(row[11]));
			
			dados.add(dado);
		}
		
		return dados;
	}
}