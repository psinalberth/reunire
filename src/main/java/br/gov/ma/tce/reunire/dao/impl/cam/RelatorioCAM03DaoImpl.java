
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
import br.gov.ma.tce.reunire.model.vo.cam.RelatorioCAM03VO;
import br.gov.ma.tce.reunire.model.vo.gestor.UnidadeVO;

@Stateless
public class RelatorioCAM03DaoImpl extends PrestacaoDaoImpl<RelatorioCAM03VO> implements DemonstrativoDao<RelatorioCAM03VO> {

	@SuppressWarnings("unchecked")
	@Override
	public List<RelatorioCAM03VO> recuperaDados(Map<String, Object> params) {
		
		String sql = "select upper(cam03.tipo_ato) ||' nº '|| LPAD(cam03.numero_ato, '8', '0') numero_ato, to_char(cam03.data_ato,'dd/MM/yyyy') data_ato, CAM03.tipo_credito tipo_credito, cam03.valor valor, cam03.dotacao_inicial dotacao_inicial " + 
				" from prestacao.cam03 cam03 " +
				"where cam03.unidade_id in(:unidade) " +
				" group by numero_ato, data_ato, tipo_credito,  cam03.tipo_ato, valor, dotacao_inicial " +
				" order by numero_ato, data_ato, tipo_credito, dotacao_inicial";
		
		String schema = params.get("exercicio") != null && ((Integer)params.get("exercicio")).equals(new Integer(2018)) ? "prestacao2018" : "prestacao";
		sql = sql.replaceAll("prestacao", schema);
		
		List<UnidadeVO> listaUnidadeVO = recuperarUnidades(params);
		List<Integer> listaIdsUnidades = extrairIds(listaUnidadeVO);
		
		List<RelatorioCAM03VO> listaVo = new ArrayList<>();
		List<Object[]> lista = entityManager.createNativeQuery(sql)
				.setParameter("unidade", listaIdsUnidades)
				.getResultList();
		
		for(Object[] l : lista) {
			
			RelatorioCAM03VO relatorio = new RelatorioCAM03VO();
			
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			Date data = null;
			try {
				data = formato.parse(l[1].toString());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			relatorio.setDataAto(data);
			
			relatorio.setTipoCredito(l[2].toString());
			relatorio.setNumeroAto(l[0].toString());
			relatorio.setValor(new BigDecimal(Double.parseDouble(l[3].toString())));
			relatorio.setDotacaoInicial(new BigDecimal(Double.parseDouble(l[4].toString())));
			listaVo.add(relatorio);	
			
		}
		
		return listaVo;
	}

	@Override
	public String getNomeRelatorio() {
		return "relatoriocam03.jasper";
	}

}
