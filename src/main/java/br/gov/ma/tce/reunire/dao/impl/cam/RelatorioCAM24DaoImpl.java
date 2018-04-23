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
import br.gov.ma.tce.reunire.model.vo.cam.RelatorioCAM24VO;
import br.gov.ma.tce.reunire.model.vo.gestor.UnidadeVO;

@Stateless
public class RelatorioCAM24DaoImpl extends PrestacaoDaoImpl<RelatorioCAM24VO> implements DemonstrativoDao<RelatorioCAM24VO> {

	@SuppressWarnings("unchecked")
	@Override
	public List<RelatorioCAM24VO> recuperaDados(Map<String, Object> params) {
		
		String sql = "select c.unidade_id, to_char(c.competencia,'dd/MM/yyyy') competencia, c.unidade_utilizacao_id, c.tipo_folha, c.cota_empregado, c.cota_empregador, c.regime, c.numero_documento " + 
				"from prestacao.cam24 c " + 
				"where c.unidade_id in(:unidade) and c.competencia is not null "+
				"group by c.unidade_id, competencia, c.unidade_utilizacao_id, c.tipo_folha, c.cota_empregado, c.cota_empregador, c.regime, c.numero_documento " +
				"order by c.unidade_id, competencia, c.unidade_utilizacao_id, c.tipo_folha";
		
		List<UnidadeVO> listaUnidadeVO = recuperarUnidades(params);
		List<Integer> listaIdsUnidades = extrairIds(listaUnidadeVO);
		
		List<RelatorioCAM24VO> listaVo = new ArrayList<>();
		
		List<Object[]> lista = entityManager.createNativeQuery(sql)
				.setParameter("unidade", listaIdsUnidades)
				.getResultList();
		
		for(Object[] l : lista) {
			RelatorioCAM24VO relatorio = new RelatorioCAM24VO();
			
			relatorio.setIdUnidade(Integer.parseInt(l[0].toString()));
			
			for(UnidadeVO listaUnidade : listaUnidadeVO) {
				if(listaUnidade.getId() == relatorio.getIdUnidade().intValue())
					relatorio.setDescricaoUnidade(listaUnidade.getNome());				
			}
			
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			
			Date data = null;
			try {
				data = formato.parse(l[1].toString());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			relatorio.setCompetencia(data);
			relatorio.setUnidadeOrcamentaria(Integer.parseInt(l[2].toString()));
			relatorio.setFolhaPagamento(l[3].toString());
			relatorio.setCota_empregado(new BigDecimal(Double.parseDouble(l[4].toString())));
			relatorio.setCota_empregador(new BigDecimal(Double.parseDouble(l[5].toString())));
			relatorio.setRegime(l[6].toString());
			relatorio.setDocumento(l[7].toString());
			listaVo.add(relatorio);	
		}		
		
		return listaVo;
	}

	@Override
	public String getNomeRelatorio() {
		return "relatorioCAM24.jasper";
	}

}
