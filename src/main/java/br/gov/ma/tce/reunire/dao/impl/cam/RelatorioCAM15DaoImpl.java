package br.gov.ma.tce.reunire.dao.impl.cam;

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
		
		String sql = 
				
		"select " +
			"unidade_id, upper(numero_oficio) numero_oficio, upper(natureza) natureza, " + 
			"regexp_replace(cpf_cnpj_credor, '[./-]', '', 'g') cpf_cnpj_credor, " + 
			"valor_inscrito, valor_pago " +
		"from " + 
			"prestacao.cam15 " +
		"where " +
			"unidade_id in (:unidades) " +
		"order by " +
			"unidade_id, upper(numero_oficio), upper(natureza), regexp_replace(cpf_cnpj_credor, '[./-]', '', 'g')";
		
		List<UnidadeVO> listaUnidades = recuperarUnidades(params);
		List<Integer> listaIdsUnidades = extrairIds(listaUnidades);
		
		List<RelatorioCAM15VO> dados = new ArrayList<>();
		
		List<Object[]> rows = entityManager.createNativeQuery(sql)
				.setParameter("unidades", listaIdsUnidades)
				.getResultList();
		
		for(Object[] row : rows) {
			
			RelatorioCAM15VO dado = new RelatorioCAM15VO();
			
			dado.setIdUnidade(Integer.parseInt(row[0].toString()));
			
			dado.setNumeroOficio(row[1].toString());
			dado.setNatureza(row[2].toString());
			dado.setCpf_cnpj_credor(toPessoa(row[3]));
			dado.setValorInscrito(toBigDecimal(row[4]));
			dado.setValorPago(toBigDecimal(row[5]));
			dados.add(dado);
		}
		
		return dados;
	}

	@Override
	public String getNomeRelatorio() {		
		return "relatorioCAM15.jasper";
	}

}
