package br.gov.ma.tce.reunire.dao.impl.d;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import br.gov.ma.tce.reunire.dao.DemonstrativoDao;
import br.gov.ma.tce.reunire.dao.impl.PrestacaoDaoImpl;
import br.gov.ma.tce.reunire.model.vo.d.RelatorioD006AVO;
import br.gov.ma.tce.reunire.model.vo.gestor.UnidadeVO;

@Stateless
public class RelatorioD006DaoImpl extends PrestacaoDaoImpl<RelatorioD006AVO> implements DemonstrativoDao<RelatorioD006AVO> {

	@Override
	public String getNomeRelatorio() {
		return "lei4320anexovi.jasper";
	}

	@Override
	public List<RelatorioD006AVO> recuperaDados(Map<String, Object> params) {
		
		String sql = "select d.unidade_id as UNIDADE, d.funcao, d.subfuncao, " + 
				"d.acao, d.programa, f.nome as nomeFuncao, sub.nome as nomeSubFuncao, " + 
				"prog.denominacao, acao.descricao, coalesce(d.valor_atual, 0) " +  
				"from prestacao.d006 d, remessa.funcao f, remessa.subfuncao sub, sae.sae_programa prog, sae.sae_acao acao " + 
				"where d.funcao = f.funcao_id " +
				"and d.subfuncao = sub.subfuncao_id " + 
				"and d.programa = prog.codigo " + 
				"and d.acao = acao.codigo_prefeitura " +
				"and d.unidade_id in (:unidade) " +	
				"order by d.unidade_id, d.funcao, d.subfuncao, d.programa, d.acao ";
		
		List<RelatorioD006AVO> listaVo = new ArrayList<>();
		
		List<UnidadeVO> listaUnidadeVO = recuperarUnidades(params);
		List<Integer> listaIdsUnidades = extrairIds(listaUnidadeVO);		
		
		@SuppressWarnings("unchecked")
		List<Object[]> lista = entityManager.createNativeQuery(sql)
				.setParameter("unidade", listaIdsUnidades)				
				.getResultList();
		
		for(Object[] l : lista) {
			RelatorioD006AVO relatorio = new RelatorioD006AVO();
			relatorio.setIdUnidade(Integer.parseInt(l[0].toString()));
			
			for(UnidadeVO listaUnidade : listaUnidadeVO) {
				if(listaUnidade.getId() == relatorio.getIdUnidade().intValue()) {
					relatorio.setDescricaoUnidade(listaUnidade.getNome());
				}
			}
			
			relatorio.setFuncaoGoverno(l[1].toString());
			relatorio.setSubfuncaoGoverno(l[2].toString());
			relatorio.setAcao(l[3].toString());
			relatorio.setPrograma(l[4].toString());
			relatorio.setNomeFuncao(l[5].toString());
			relatorio.setNomeSubFuncao(l[6].toString());
			relatorio.setNomePrograma(l[7].toString());
			relatorio.setNomeAcao(l[8].toString());
			
			
			Integer codigoAcao = Integer.valueOf(l[3].toString());
			
			if(codigoAcao % 2 == 0) {
				relatorio.setValorAtividade(new BigDecimal((new Double(l[9].toString()))));
				relatorio.setValorProjeto(new BigDecimal(new Double(0)));
			}else {
				relatorio.setValorProjeto(new BigDecimal((new Double(l[9].toString()))));
				relatorio.setValorAtividade(new BigDecimal(new Double(0)));
			}
			
			/*relatorio.setValorOperacoesEspeciais(new BigDecimal((new Double(l[7].toString()))));*/
			
			listaVo.add(relatorio);
		}
		
		return listaVo;
	}
}
