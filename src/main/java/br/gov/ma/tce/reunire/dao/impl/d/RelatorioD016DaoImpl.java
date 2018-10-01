package br.gov.ma.tce.reunire.dao.impl.d;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import br.gov.ma.tce.reunire.dao.DemonstrativoDao;
import br.gov.ma.tce.reunire.dao.impl.PrestacaoDaoImpl;
import br.gov.ma.tce.reunire.model.vo.d.RelatorioD016AVO;
import br.gov.ma.tce.reunire.model.vo.gestor.UnidadeVO;

@Stateless
public class RelatorioD016DaoImpl extends PrestacaoDaoImpl<RelatorioD016AVO> implements DemonstrativoDao<RelatorioD016AVO> {
	
	@Override
	public String getNomeRelatorio() {
		return "relatoriod016.jasper";
	}

	@Override
	public List<RelatorioD016AVO> recuperaDados(Map<String, Object> params) {
		
		String sql = "	select unidade_id, titulo, numero_lei, data_lei, valor_emissao, " + 
					 "	saldo_circulacao, movimento_emissao,  movimento_resgate, saldo_seguinte_valor " + 
					 "	from prestacao.d016 " + 
					 "	where unidade_id in(:unidade) " +
					 "	order by titulo";
		
		List<RelatorioD016AVO> listaVo = new ArrayList<>();
		
		List<UnidadeVO> listaUnidadeVO = recuperarUnidades(params);
		List<Integer> listaIdsUnidades = extrairIds(listaUnidadeVO);
		
		@SuppressWarnings("unchecked")
		List<Object[]> lista = entityManager.createNativeQuery(sql)
				.setParameter("unidade", listaIdsUnidades)				
				.getResultList();
		
		for(Object[] l : lista) {
			RelatorioD016AVO relatorio = new RelatorioD016AVO();
			relatorio.setIdUnidade(Integer.parseInt(l[0].toString()));
			
			for(UnidadeVO listaUnidade : listaUnidadeVO) {
				if(listaUnidade.getId() == relatorio.getIdUnidade().intValue()) {
					relatorio.setDescricaoUnidade(listaUnidade.getNome());
				}
			}
			 
			relatorio.setTitulo(l[1].toString());
			relatorio.setLeis(l[2].toString());
			
			Date data = new Date();
			String novaData = null;
			
			if (l[3] != null) {
			
				data = (Date) l[3];
				
				DateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
				
				 novaData = formatador.format(data);
			}
			
			relatorio.setDataDaLei(l[3]  != null ? novaData : "N/A");
			relatorio.setValorEmissao(new BigDecimal(new Double(l[4].toString())));
			relatorio.setSaldoAnterior(new BigDecimal(new Double(l[5].toString())));
			relatorio.setMovimentoEmissao(new BigDecimal(new Double(l[6].toString())));
			relatorio.setMovimentoPagamento(new BigDecimal(new Double(l[7].toString())));
			relatorio.setSaldoSeguinte(new BigDecimal(new Double(l[8].toString())));
			
			listaVo.add(relatorio);
		}
		
		return listaVo;
	}
}