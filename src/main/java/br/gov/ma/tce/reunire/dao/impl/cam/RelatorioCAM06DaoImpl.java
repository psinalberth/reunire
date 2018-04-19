package br.gov.ma.tce.reunire.dao.impl.cam;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import br.gov.ma.tce.reunire.dao.DemonstrativoDao;
import br.gov.ma.tce.reunire.dao.impl.PrestacaoDaoImpl;
import br.gov.ma.tce.reunire.model.vo.cam.RelatorioCAM06VO;
import br.gov.ma.tce.reunire.model.vo.gestor.UnidadeVO;

public class RelatorioCAM06DaoImpl extends PrestacaoDaoImpl<RelatorioCAM06VO> implements DemonstrativoDao<RelatorioCAM06VO> {

	@SuppressWarnings("unchecked")
	@Override
	public List<RelatorioCAM06VO> recuperaDados(Map<String, Object> params) {
		
		String sql = "select c.unidade_id, c.numero_processo, c.cpf_suprido, c.finalidade_adiantamento, c.data_recebimento, c.valor, c.prazo_aplicacao, c.data_prestacao, c.situacao " + 
				"from prestacao.cam06 c " + 
				"where c.unidade_id in(:unidade) " + 
				"group by c.unidade_id, c.numero_processo, c.cpf_suprido, c.finalidade_adiantamento, c.data_recebimento, c.valor, c.prazo_aplicacao, c.data_prestacao, c.situacao " + 
				"order by c.unidade_id, c.numero_processo, c.cpf_suprido, c.finalidade_adiantamento, c.data_recebimento, c.prazo_aplicacao, c.data_prestacao, c.situacao";
		
		List<UnidadeVO> listaUnidadeVO = recuperarUnidades(params);
		List<Integer> listaIdsUnidades = extrairIds(listaUnidadeVO);
		
		List<RelatorioCAM06VO> listaVo = new ArrayList<>();
		
		List<Object[]> lista = entityManager.createNativeQuery(sql)
				.setParameter("unidade", listaIdsUnidades)
				.getResultList();
		
		for(Object[] l : lista) {
			
			RelatorioCAM06VO relatorio = new RelatorioCAM06VO();
			relatorio.setIdUnidade(Integer.parseInt(l[0].toString()));
			
			for(UnidadeVO listaUnidade : listaUnidadeVO) {
				if(listaUnidade.getId() == relatorio.getIdUnidade().intValue()) {
					relatorio.setDescricaoUnidade(listaUnidade.getNome());
				}
			}
			relatorio.setNumeroProcesso(l[1].toString());
			relatorio.setCpfSuprido(l[2].toString());
			relatorio.setFinalidadeAdiantamento(l[3].toString());
			
			SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
			
			Date data1 = null;
			try {
				data1 = formato.parse(l[4].toString());
			} catch (ParseException e) {
				e.printStackTrace();
			}			
			
			relatorio.setDataRecebimento(data1);
			relatorio.setValor(new BigDecimal(Double.parseDouble(l[5].toString())));
			relatorio.setPrazoAplicacao(Integer.parseInt(l[6].toString()));
			
			Date data2 = null;
			try {
				data2 = formato.parse(l[7].toString());
			} catch (ParseException e) {
				e.printStackTrace();
			}	
			
			relatorio.setDataPrestacaoContas(data2);
			relatorio.setSituacao(l[8].toString());			
		}
		
		return listaVo;
	}

	@Override
	public String getNomeRelatorio() {		
		return "";
	}

	
	

}
