package br.gov.ma.tce.reunire.dao.impl.d;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import br.gov.ma.tce.reunire.dao.DemonstrativoDao;
import br.gov.ma.tce.reunire.dao.impl.PrestacaoDaoImpl;
import br.gov.ma.tce.reunire.model.vo.d.RelatorioD006AVO;
import br.gov.ma.tce.reunire.model.vo.gestor.UnidadeVO;

@Stateless
public class RelatorioD006DaoImpl extends PrestacaoDaoImpl<RelatorioD006AVO> implements DemonstrativoDao<RelatorioD006AVO> {

	@SuppressWarnings("unchecked")
	@Override
	public List<RelatorioD006AVO> recuperaDados(Integer ente, Integer orgao, Integer unidadeGestora, Integer poder,
			Integer exercicio) {
		
		String sql = "select d.unidade_id as UNIDADE, d.funcao, d.subfuncao, " + 
				"d.acao, d.programa, f.nome as nomeFuncao, sub.nome as nomeSubFuncao, d.valor_atual " + 
				"from prestacao.d006 d, remessa.funcao f, remessa.subfuncao sub " + 
				"where d.funcao = f.funcao_id "
				+"and d.subfuncao = sub.subfuncao_id "
				+"and d.unidade_id in (:unidade)";
		
		List<RelatorioD006AVO> listaVo = new ArrayList<>();
		//List<Integer> listaIdsUnidades = recuperarIdsUnidades(ente, orgao, poder, unidadeGestora);
		
		List<UnidadeVO> listaUnidadeVO = recuperarUnidades(ente, orgao, poder, unidadeGestora);
		

		
		List<Object[]> lista = entityManager.createNativeQuery(sql)
				.setParameter("unidade", 228)				
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
			
			
			Integer codigoAcao = Integer.valueOf(l[3].toString());
			
			if(codigoAcao % 2 == 0) {
				relatorio.setValorAtividade(new BigDecimal((new Double(l[7].toString()))));
				relatorio.setValorProjeto(new BigDecimal(new Double(0)));
			}else {
				relatorio.setValorProjeto(new BigDecimal((new Double(l[7].toString()))));
				relatorio.setValorAtividade(new BigDecimal(new Double(0)));
			}
			
			/*relatorio.setValorOperacoesEspeciais(new BigDecimal((new Double(l[7].toString()))));*/
			
			listaVo.add(relatorio);
		}
		
		return listaVo;
	}

	@Override
	public String getNomeRelatorio() {
		return "lei4320anexovi.jasper";
	}
		

}
