package br.gov.ma.tce.reunire.dao.impl.d;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
		
		String sql = "select d.unidade_id, d.funcao, d.subfuncao, d.programa, d.acao, acao.descricao descricaoAcao, " + 
				"		d.descricao descricaoNaturezaDespesa, d.credito_suplementar,  " + 
				"		d.credito_extraordinario, d.despesa_realizada " + 
				"	from prestacao.d011 d,  sae.sae_acao acao " + 
				"	where d.acao = acao.codigo_prefeitura " +
				"	and d.unidade_id in (:unidade) "
				/*"order by d.unidade_id, d.funcao, d.subfuncao, d.programa, d.acao "*/;
		
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
		

}
