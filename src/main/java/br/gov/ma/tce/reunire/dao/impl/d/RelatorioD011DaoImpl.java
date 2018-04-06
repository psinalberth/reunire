package br.gov.ma.tce.reunire.dao.impl.d;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import br.gov.ma.tce.reunire.dao.DemonstrativoDao;
import br.gov.ma.tce.reunire.dao.impl.PrestacaoDaoImpl;
import br.gov.ma.tce.reunire.model.vo.d.RelatorioD011AVO;
import br.gov.ma.tce.reunire.model.vo.gestor.UnidadeVO;

@Stateless
public class RelatorioD011DaoImpl extends PrestacaoDaoImpl<RelatorioD011AVO> implements DemonstrativoDao<RelatorioD011AVO> {

	@SuppressWarnings("unchecked")
	@Override
	public List<RelatorioD011AVO> recuperaDados(Integer ente, Integer orgao, Integer unidadeGestora, Integer poder,
			Integer exercicio) {
		
		String sql = "select d.unidade_id, d.funcao, d.subfuncao, " + 
					 "d.programa, d.acao,  d.acaoDescricao, nd.descricao, " + 
					 "d.credito_suplementar, d.credito_extraordinario, d.despesa_realizada " + 
					 "	from sae.vw_natureza_despesa nd " + 
					"	inner join ( " + 
					"	select d.descricao, d.unidade_id, d.funcao, " + 
					"		d.subfuncao, d.programa, d.acao, acao.descricao acaoDescricao, " + 
					"		sum(d.credito_suplementar) credito_suplementar, " + 
					"		sum(d.credito_extraordinario) credito_extraordinario, " + 
					"		sum(d.despesa_realizada) despesa_realizada " + 
					"	from prestacao.d011 d, sae.sae_acao acao " + 
					"	where d.acao = acao.codigo_prefeitura " + 
					"	and d.unidade_id in (:unidade) "+  
					"	group by d.descricao, d.unidade_id, d.funcao, d.subfuncao, d.programa, d.acao, acao.descricao ) " + 
					"d on d.descricao = regexp_replace(nd.descricao, '[ ]', '', 'g')";
		
		List<RelatorioD011AVO> listaVo = new ArrayList<>();
		
		List<Integer> listaIdsUnidades = recuperarIdsUnidades(ente, orgao, poder, unidadeGestora);
		
		List<UnidadeVO> listaUnidadeVO = recuperarUnidades(ente, orgao, poder, unidadeGestora);
		

		
		List<Object[]> lista = entityManager.createNativeQuery(sql)
				.setParameter("unidade", listaIdsUnidades)				
				.getResultList();
		
		for(Object[] l : lista) {
			RelatorioD011AVO relatorio = new RelatorioD011AVO();
			relatorio.setIdUnidade(Integer.parseInt(l[0].toString()));
			
			for(UnidadeVO listaUnidade : listaUnidadeVO) {
				if(listaUnidade.getId() == relatorio.getIdUnidade().intValue()) {
					relatorio.setDescricaoUnidade(listaUnidade.getNome());
				}
			}
			
			relatorio.setCodigoFuncao(l[1].toString());
			relatorio.setCodigoSubFuncao(l[2].toString());
			relatorio.setCodigoPrograma(l[3].toString());
			relatorio.setCodigoAcao(l[4].toString());
			relatorio.setDescricaoAcao(l[5].toString());
			relatorio.setDescricaoNaturezaDespesa(l[6].toString());
			relatorio.setValorCreditoOrcamentario(new BigDecimal(new Double(l[7].toString())));
			relatorio.setValorCreditoEspecial(new BigDecimal(new Double(l[8].toString())));
			relatorio.setValorDespesaRealizada(new BigDecimal(new Double(l[9].toString())));
			
			listaVo.add(relatorio);
		}
		
		return listaVo;
	}

	@Override
	public String getNomeRelatorio() {
		return "lei4320anexoxiTESTE.jasper";
	}

	@Override
	public List<RelatorioD011AVO> recuperaDados(Map<String, Object> params) {
		
		String sql = "select d.unidade_id, d.funcao, d.subfuncao, " + 
				 "d.programa, d.acao,  d.acaoDescricao, nd.descricao, " + 
				 "d.credito_suplementar, d.credito_extraordinario, d.despesa_realizada " + 
				 "	from sae.vw_natureza_despesa nd " + 
				"	inner join ( " + 
				"	select d.descricao, d.unidade_id, d.funcao, " + 
				"		d.subfuncao, d.programa, d.acao, acao.descricao acaoDescricao, " + 
				"		sum(d.credito_suplementar) credito_suplementar, " + 
				"		sum(d.credito_extraordinario) credito_extraordinario, " + 
				"		sum(d.despesa_realizada) despesa_realizada " + 
				"	from prestacao.d011 d, sae.sae_acao acao " + 
				"	where d.acao = acao.codigo_prefeitura " + 
				"	and d.unidade_id in (:unidade) "+  
				"	group by d.descricao, d.unidade_id, d.funcao, d.subfuncao, d.programa, d.acao, acao.descricao ) " + 
				"d on d.descricao = regexp_replace(nd.descricao, '[ ]', '', 'g')";
		
		List<RelatorioD011AVO> listaVo = new ArrayList<>();
		
		List<UnidadeVO> listaUnidadeVO = recuperarUnidades(params);
		List<Integer> listaIdsUnidades = extrairIds(listaUnidadeVO);
		
		@SuppressWarnings("unchecked")
		List<Object[]> lista = entityManager.createNativeQuery(sql)
				.setParameter("unidade", listaIdsUnidades)				
				.getResultList();
		
		for(Object[] l : lista) {
			RelatorioD011AVO relatorio = new RelatorioD011AVO();
			relatorio.setIdUnidade(Integer.parseInt(l[0].toString()));
			
			for(UnidadeVO listaUnidade : listaUnidadeVO) {
				if(listaUnidade.getId() == relatorio.getIdUnidade().intValue()) {
					relatorio.setDescricaoUnidade(listaUnidade.getNome());
				}
			}
			
			relatorio.setCodigoFuncao(l[1].toString());
			relatorio.setCodigoSubFuncao(l[2].toString());
			relatorio.setCodigoPrograma(l[3].toString());
			relatorio.setCodigoAcao(l[4].toString());
			relatorio.setDescricaoAcao(l[5].toString());
			relatorio.setDescricaoNaturezaDespesa(l[6].toString());
			relatorio.setValorCreditoOrcamentario(new BigDecimal(new Double(l[7].toString())));
			relatorio.setValorCreditoEspecial(new BigDecimal(new Double(l[8].toString())));
			relatorio.setValorDespesaRealizada(new BigDecimal(new Double(l[9].toString())));
			
			listaVo.add(relatorio);
		}
		
		return listaVo;
	}
}