package br.gov.ma.tce.reunire.dao.impl.d;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;

import br.gov.ma.tce.reunire.dao.DemonstrativoDao;
import br.gov.ma.tce.reunire.dao.impl.PrestacaoDaoImpl;
import br.gov.ma.tce.reunire.model.vo.d.RelatorioD006AVO;
import br.gov.ma.tce.reunire.model.vo.gestor.UnidadeVO;

@Stateless
public class RelatorioD007DaoImpl extends PrestacaoDaoImpl<RelatorioD006AVO> implements DemonstrativoDao<RelatorioD006AVO> {
	
	@Override
	public String getNomeRelatorio() {
		return "relatoriod007.jasper";
	}

	@Override
	public List<RelatorioD006AVO> recuperaDados(Map<String, Object> params) {
		
		String sql = 
		
		"select " +
				"d.unidade_orcamentaria_id, " + 
				"d.funcao, d.subfuncao, fg.nome nome_funcao, sf.nome nome_subfuncao, " +
				"(case when prog.id_programa is not null then prog.codigo else d.programa end) programa, " +
				"(case when prog.id_programa is not null then prog.denominacao else 'NÃO INFORMADO' end) nome_programa, " +
				"(case when acao.id_acao is not null then acao.codigo_prefeitura else d.acao end) acao, " +
				"(case when acao.id_acao is not null then acao.descricao else d.descricao end) descricao, " +
				"coalesce(d.valor_atual, 0) valor " + 
			"from  " + 
				"prestacao.d006 d  " +
			"left join gestor.unidade_ente und on  " +
				"und.unidade_id = d.unidade_orcamentaria_id  " +
			"left join remessa.funcao fg on  " + 
				"fg.funcao_id = d.funcao  " + 
			"left join remessa.subfuncao sf on  " + 
				"sf.subfuncao_id = d.subfuncao  " +
			"left join sae.sae_acao acao on  " +
				"acao.codigo_prefeitura = d.acao and  " +
				"acao.unidade = d.unidade_orcamentaria_id and  " +
				"acao.funcao = d.funcao and  " +
				"acao.subfuncao = d.subfuncao  " +
			"left join sae.sae_programa prog on  " +
				"(prog.id_programa = acao.id_programa or prog.codigo = d.programa and prog.ente = und.ente_id) " +
			"where  " + 
				"d.unidade_orcamentaria_id in (:unidades)  " + 
			"order by  " + 
				"d.funcao, d.subfuncao, programa, acao";
		
		List<RelatorioD006AVO> dados = new ArrayList<>();
		
		List<UnidadeVO> listaUnidades = recuperarUnidades(params);
		List<Integer> listaIdsUnidades = extrairIds(listaUnidades);		
		
		@SuppressWarnings("unchecked")
		List<Object[]> rows = entityManager.createNativeQuery(sql)
				.setParameter("unidades", listaIdsUnidades)				
				.getResultList();
		
		for(Object[] row : rows) {
			
			RelatorioD006AVO dado = new RelatorioD006AVO();
			
			Optional<UnidadeVO> unidade = listaUnidades.stream().filter(u -> u.getId().equals(Integer.parseInt(String.valueOf(row[0])))).findFirst();
			
			dado.setIdUnidade(Integer.parseInt(String.valueOf(row[0])));
			dado.setDescricaoUnidade(unidade != null ? unidade.get().getNome().toUpperCase() : "");
			
			if (unidade != null) {
				
				dado.setIdOrgao(unidade.get().getOrgao().getId());
				dado.setDescricaoOrgao(unidade.get().getOrgao().getNome());
			}
			
			dado.setFuncaoGoverno(row[1].toString());
			dado.setSubfuncaoGoverno(row[2].toString());
			dado.setNomeFuncao(row[3].toString());
			dado.setNomeSubFuncao(row[4].toString());
			dado.setPrograma(row[5] != null ? String.valueOf(row[5]) : "NÃO INFORMADO");
			dado.setNomePrograma(row[6] != null ? String.valueOf(row[6]) : "NÃO INFORMADO");
			dado.setAcao(row[7] != null ? String.valueOf(row[7]) : "NÃO INFORMADO");
			dado.setNomeAcao(row[8] != null ? String.valueOf(row[8]) : "NÃO INFORMADO");
			
			Integer codigoAcao = Integer.valueOf(row[7].toString());
			Integer codigoFuncao = Integer.valueOf(row[1].toString());
			
			if (codigoAcao % 2 == 0) {
				
				dado.setValorAtividade(toBigDecimal(row[9]));
				dado.setValorProjeto(BigDecimal.ZERO);
				dado.setValorOperacoesEspeciais(BigDecimal.ZERO);
				
			} else if(codigoFuncao == 28) {
				
				dado.setValorOperacoesEspeciais(toBigDecimal(row[9]));
				dado.setValorProjeto(BigDecimal.ZERO);
				dado.setValorAtividade(BigDecimal.ZERO);
			
			} else {
				
				dado.setValorProjeto(toBigDecimal(row[9]));
				dado.setValorAtividade(BigDecimal.ZERO);
				dado.setValorOperacoesEspeciais(BigDecimal.ZERO);
			}
			
			dados.add(dado);
		}
		
		params.put("tipo", 7);
		
		return dados;
	}
}
