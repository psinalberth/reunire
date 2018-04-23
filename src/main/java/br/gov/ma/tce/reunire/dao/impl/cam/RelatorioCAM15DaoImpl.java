package br.gov.ma.tce.reunire.dao.impl.cam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import br.gov.ma.tce.reunire.dao.DemonstrativoDao;
import br.gov.ma.tce.reunire.dao.impl.PrestacaoDaoImpl;
import br.gov.ma.tce.reunire.model.vo.cam.RelatorioCAM15VO;
import br.gov.ma.tce.reunire.model.vo.gestor.UnidadeVO;

@Stateless
public class RelatorioCAM15DaoImpl extends PrestacaoDaoImpl<RelatorioCAM15VO> implements DemonstrativoDao<RelatorioCAM15VO> {

	@SuppressWarnings("unchecked")
	@Override
	public List<RelatorioCAM15VO> recuperaDados(Map<String, Object> params) {
		
		String sql = "select c.unidade_id, c.numero_oficio, c.natureza, c.cpf_cnpj_credor, c.valor_inscrito, c.valor_pago " +
				"from prestacao.cam15 c " +
				"where c.unidade_id in(:unidade) "+
				"group by c.unidade_id, c.numero_oficio, c.natureza, c.cpf_cnpj_credor, c.valor_inscrito, c.valor_pago "+
				"order by c.unidade_id, c.numero_oficio, c.natureza ";
		
		List<UnidadeVO> listaUnidadeVO = recuperarUnidades(params);
		List<Integer> listaIdsUnidades = extrairIds(listaUnidadeVO);
		
		List<RelatorioCAM15VO> listaVo = new ArrayList<>();
		
		List<Object[]> lista = entityManager.createNativeQuery(sql)
				.setParameter("unidade", listaIdsUnidades)
				.getResultList();
		
		for(Object[] l : lista) {
			RelatorioCAM15VO relatorio = new RelatorioCAM15VO();
			relatorio.setIdUnidade(Integer.parseInt(l[0].toString()));
			
			for(UnidadeVO listaUnidade : listaUnidadeVO) {
				if(listaUnidade.getId() == relatorio.getIdUnidade().intValue()) {
					relatorio.setDescricaoUnidade(listaUnidade.getNome());
				}
			}
			
			relatorio.setNumeroOficio(l[1].toString());
			relatorio.setNatureza(l[2].toString());
			relatorio.setCpf_cnpj_credor(l[3].toString());
			relatorio.setValorInscrito(new BigDecimal(Double.parseDouble(l[4].toString())));
			relatorio.setValorPago(new BigDecimal(Double.parseDouble(l[5].toString())));
			listaVo.add(relatorio);
		}
		
		return listaVo;
	}

	@Override
	public String getNomeRelatorio() {		
		return "relatorioCAM15.jasper";
	}

}
