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
public class RelatorioD006DaoImpl extends PrestacaoDaoImpl<RelatorioD006AVO> implements DemonstrativoDao<RelatorioD006AVO> {

	@Override
	public String getNomeRelatorio() {
		return "relatoriod006.jasper";
	}

	@Override
	public List<RelatorioD006AVO> recuperaDados(Map<String, Object> params) {
		
		String sql = 
		
		"select " +
			"d.unidade_orcamentaria_id, d.funcao, d.subfuncao, fg.nome nome_funcao, sf.nome nome_subfuncao, d.programa, d.acao, d.descricao, " +
			"coalesce(d.valor_atual, 0) valor " +
		"from " + 
			"prestacao.d006 d " +
		"left join remessa.funcao fg on " +
			"fg.funcao_id = d.funcao " +
		"left join remessa.subfuncao sf on " +
			"sf.subfuncao_id = d.subfuncao " +
		"where " +
			"d.unidade_orcamentaria_id in (:unidades) " +
		"order by " +
			"d.unidade_orcamentaria_id, d.programa, d.acao";
		
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
			dado.setPrograma(row[5].toString());
			dado.setAcao(row[6].toString());
			dado.setNomePrograma("NÃO INFORMADO");
			dado.setNomeAcao(row[7].toString());
			
			Integer codigoAcao = Integer.valueOf(row[6].toString());
			Integer codigoFuncao = Integer.valueOf(row[1].toString());
			
			if (codigoAcao % 2 == 0) {
				
				dado.setValorAtividade(toBigDecimal(row[8]));
				dado.setValorProjeto(BigDecimal.ZERO);
				dado.setValorOperacoesEspeciais(BigDecimal.ZERO);
				
			} else if(codigoFuncao == 28) {
				
				dado.setValorOperacoesEspeciais(toBigDecimal(row[8]));
				dado.setValorProjeto(BigDecimal.ZERO);
				dado.setValorAtividade(BigDecimal.ZERO);
			
			} else {
				
				dado.setValorProjeto(toBigDecimal(row[8]));
				dado.setValorAtividade(BigDecimal.ZERO);
				dado.setValorOperacoesEspeciais(BigDecimal.ZERO);
			}
			
			dados.add(dado);
		}
		
		return dados;
	}
}
