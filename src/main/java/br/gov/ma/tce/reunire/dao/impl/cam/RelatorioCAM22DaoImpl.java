package br.gov.ma.tce.reunire.dao.impl.cam;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import br.gov.ma.tce.reunire.dao.DemonstrativoDao;
import br.gov.ma.tce.reunire.dao.impl.PrestacaoDaoImpl;
import br.gov.ma.tce.reunire.model.vo.cam.RelatorioCAM22VO;
import br.gov.ma.tce.reunire.model.vo.gestor.UnidadeVO;

@Stateless
public class RelatorioCAM22DaoImpl extends PrestacaoDaoImpl<RelatorioCAM22VO> implements DemonstrativoDao<RelatorioCAM22VO> {

	@SuppressWarnings("unchecked")
	@Override
	public List<RelatorioCAM22VO> recuperaDados(Map<String, Object> params) {
		
		String sql = "select c.unidade_id, c.identificacao, c.destinacao, c.valor, c.situacao, to_char(c.data_aquisicao,'dd/MM/yyyy') data_aquisicao " + 
				"from prestacao.cam22 c "
				+ "where c.unidade_id in(:unidade) "
				+ "order by c.unidade_id, c.identificacao, c.destinacao, c.situacao, data_aquisicao";

		List<UnidadeVO> listaUnidadeVO = recuperarUnidades(params);
		List<Integer> listaIdsUnidades = extrairIds(listaUnidadeVO);
		
		List<RelatorioCAM22VO> listaVo = new ArrayList<>();
		
		List<Object[]> lista = entityManager.createNativeQuery(sql)
				.setParameter("unidade", listaIdsUnidades)
				.getResultList();
		
		for(Object[] l : lista) {
			RelatorioCAM22VO relatorio = new RelatorioCAM22VO();
			
			relatorio.setIdUnidade(Integer.parseInt(l[0].toString()));
			
			for(UnidadeVO listaUnidade : listaUnidadeVO) {
				if(listaUnidade.getId() == relatorio.getIdUnidade().intValue()) {
					relatorio.setDescricaoUnidade(listaUnidade.getNome());
				}
			}
			
			relatorio.setIdentificacao(l[1].toString());
			relatorio.setDestinacao(l[2].toString());
			relatorio.setValor(new BigDecimal(Double.parseDouble(l[3].toString())));
			relatorio.setSituacao(l[4].toString());
			
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			
			Date data = null;
			try {
				data = formato.parse(l[5].toString());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			relatorio.setDataAquisicao(data);
			listaVo.add(relatorio);			
		}
		
		return listaVo;
	}

	@Override
	public String getNomeRelatorio() {		
		return "relatorioCAM22.jasper";
	}

}
