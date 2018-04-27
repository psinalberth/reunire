package br.gov.ma.tce.reunire.dao.impl.d;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import br.gov.ma.tce.reunire.dao.DemonstrativoDao;
import br.gov.ma.tce.reunire.dao.impl.PrestacaoDaoImpl;
import br.gov.ma.tce.reunire.model.vo.d.RelatorioD009AVO;
import br.gov.ma.tce.reunire.model.vo.gestor.UnidadeVO;

@Stateless
public class RelatorioD009DaoImpl extends PrestacaoDaoImpl<RelatorioD009AVO> implements DemonstrativoDao<RelatorioD009AVO> {

	@Override
	public List<RelatorioD009AVO> recuperaDados(Map<String, Object> params) {
		
		String sql = "select " + 
				"  d1.unidade_orcamentaria_id, " + 
				"  sum(fg1) fg1, sum(fg2) fg2, sum(fg3) fg3, sum(fg4) fg4, sum(fg5) fg5, sum(fg6) fg6, sum(fg7) fg7, sum(fg8) fg8, sum(fg9) fg9, " + 
				"  sum(fg10) fg10, sum(fg11) fg11, sum(fg12) fg12, sum(fg13) fg13, sum(fg14) fg14, sum(fg15) fg15, sum(fg16) fg16, sum(fg17) fg17, " + 
				"  sum(fg18) fg18, sum(fg19) fg19, sum(fg20) fg20, sum(fg21) fg21, sum(fg22) fg22, sum(fg23) fg23, sum(fg24) fg24, sum(fg25) fg25, " + 
				"  sum(fg26) fg26, sum(fg27) fg27, sum(fg28) fg28, sum(fg99) fg99 " + 
				"from ( " + 
				"  select " + 
				"     d.unidade_orcamentaria_id, " + 
				"    (case when  d.funcao = 1 then d.valor_atual else 0 end) fg1, " + 
				"    (case when d.funcao = 2 then d.valor_atual else 0 end) fg2, " + 
				"    (case when d.funcao = 3 then d.valor_atual else 0 end) fg3, " + 
				"    (case when d.funcao = 4 then d.valor_atual else 0 end) fg4, " + 
				"    (case when d.funcao = 5 then d.valor_atual else 0 end) fg5, " + 
				"    (case when d.funcao = 6 then d.valor_atual else 0 end) fg6, " + 
				"    (case when d.funcao = 7 then d.valor_atual else 0 end) fg7, " + 
				"    (case when d.funcao = 8 then d.valor_atual else 0 end) fg8, " + 
				"    (case when d.funcao = 9 then d.valor_atual else 0 end) fg9, " + 
				"    (case when d.funcao = 10 then d.valor_atual else 0 end) fg10, " + 
				"    (case when d.funcao = 11 then d.valor_atual else 0 end) fg11, " + 
				"    (case when d.funcao = 12 then d.valor_atual else 0 end) fg12, " + 
				"    (case when d.funcao = 13 then d.valor_atual else 0 end) fg13, " + 
				"    (case when d.funcao = 14 then d.valor_atual else 0 end) fg14, " + 
				"    (case when d.funcao = 15 then d.valor_atual else 0 end) fg15, " + 
				"    (case when d.funcao = 16 then d.valor_atual else 0 end) fg16, " + 
				"    (case when d.funcao = 17 then d.valor_atual else 0 end) fg17, " + 
				"    (case when d.funcao = 18 then d.valor_atual else 0 end) fg18, " + 
				"    (case when d.funcao = 19 then d.valor_atual else 0 end) fg19, " + 
				"    (case when d.funcao = 20 then d.valor_atual else 0 end) fg20, " + 
				"    (case when d.funcao = 21 then d.valor_atual else 0 end) fg21, " + 
				"    (case when d.funcao = 22 then d.valor_atual else 0 end) fg22, " + 
				"    (case when d.funcao = 23 then d.valor_atual else 0 end) fg23, " + 
				"    (case when d.funcao = 24 then d.valor_atual else 0 end) fg24, " + 
				"    (case when d.funcao = 25 then d.valor_atual else 0 end) fg25, " + 
				"    (case when d.funcao = 26 then d.valor_atual else 0 end) fg26, " + 
				"    (case when d.funcao = 27 then d.valor_atual else 0 end) fg27, " + 
				"    (case when d.funcao = 28 then d.valor_atual else 0 end) fg28, " + 
				"    (case when d.funcao = 99 then d.valor_atual else 0 end) fg99  " + 
				"  from prestacao.d006 d order by d.unidade_orcamentaria_id) d1 " + 
				" where d1.unidade_orcamentaria_id in(:unidade) " + 
				" group by d1.unidade_orcamentaria_id " + 
				" order by d1.unidade_orcamentaria_id ";
		
		List<RelatorioD009AVO> listaVo = new ArrayList<>();
		
		List<UnidadeVO> listaUnidadeVO = recuperarUnidades(params);
		List<Integer> listaIdsUnidades = extrairIds(listaUnidadeVO);				
				
		@SuppressWarnings("unchecked")
		List<Object[]> lista = entityManager.createNativeQuery(sql)
				.setParameter("unidade", listaIdsUnidades)
				.getResultList();
		
		for(Object[] l : lista) {
			
			RelatorioD009AVO relatorio = new RelatorioD009AVO();
			relatorio.setIdUnidade(Integer.parseInt(l[0].toString()));
			
			for(UnidadeVO listaUnidade : listaUnidadeVO) {
				if(listaUnidade.getId() == relatorio.getIdUnidade().intValue()) {
					relatorio.setDescricaoUnidade(listaUnidade.getNome());
					relatorio.setOrgao(listaUnidade.getOrgao().getId() +" - "+ listaUnidade.getOrgao().getNome());
				}
			}
			
			for (int i = 0; i < 30; i++) {
				if(i+1 == 30) break;
				relatorio.addValor(i, new BigDecimal(new Double(l[i+1].toString())));				
			}
			
			BigDecimal total = Arrays.asList(relatorio.getSaldos()).stream().reduce(BigDecimal.ZERO, BigDecimal::add);
			relatorio.addValor(29, total);
			
			listaVo.add(relatorio);
		}
		
		return listaVo;
	}

	@Override
	public String getNomeRelatorio() {
		return "relatoriod009.jasper";
	}

}
