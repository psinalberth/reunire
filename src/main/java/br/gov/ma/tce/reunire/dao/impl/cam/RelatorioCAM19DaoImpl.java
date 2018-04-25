package br.gov.ma.tce.reunire.dao.impl.cam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;

import br.gov.ma.tce.reunire.dao.DemonstrativoDao;
import br.gov.ma.tce.reunire.dao.impl.PrestacaoDaoImpl;
import br.gov.ma.tce.reunire.model.vo.cam.RelatorioCAM19VO;
import br.gov.ma.tce.reunire.model.vo.gestor.UnidadeVO;

@Stateless
public class RelatorioCAM19DaoImpl extends PrestacaoDaoImpl<RelatorioCAM19VO> implements DemonstrativoDao<RelatorioCAM19VO> {

	@SuppressWarnings("unchecked")
	@Override
	public List<RelatorioCAM19VO> recuperaDados(Map<String, Object> params) {
		
		List<UnidadeVO> listaUnidades = recuperarUnidades(params);
		List<Integer> listaIdsUnidades = extrairIds(listaUnidades);
		
		String sql =
				
		"select " +
			"unidade_id, cpf_cnpj_credor credor, tipo, " + 
			"sum(valor_principal) valor_principal, sum(valor_multa) valor_multa, sum(valor_juro) valor_juro " +
		"from " + 
			"prestacao.cam19 " +
		"where " +
			"unidade_id in (:unidades) and " +
			"((:modulo is null) or (modulo_id = :modulo)) " +
		"group by " +
			"unidade_id, cpf_cnpj_credor, tipo " +
		"order by " +
			"unidade_id, cpf_cnpj_credor, tipo";
		
		List<Object[]> rows = entityManager.createNativeQuery(sql)
				.setParameter("unidades", listaIdsUnidades)
				.setParameter("modulo", 1)
				.getResultList();
		
		List<RelatorioCAM19VO> dados = new ArrayList<RelatorioCAM19VO>(rows.size());
		
		for (Object[] row : rows) {
			
			RelatorioCAM19VO dado = new RelatorioCAM19VO();
			
			Optional<UnidadeVO> unidade = listaUnidades.stream().filter(u -> u.getId().equals(Integer.parseInt(String.valueOf(row[0])))).findFirst();
			
			dado.setIdUnidade(Integer.parseInt(String.valueOf(row[0])));
			dado.setDescricaoUnidade(unidade != null ? unidade.get().getNome().toUpperCase() : "");
			dado.setIdentificacaoDevedor(toPessoa(row[1]));
			dado.setTipo(String.valueOf(row[2]));
			dado.setValorPrincipal(toBigDecimal(row[3]));
			dado.setValorMulta(toBigDecimal(row[4]));
			dado.setValorJuros(toBigDecimal(row[5]));
			
			dados.add(dado);
		}
		
		return dados;
	}

	@Override
	public String getNomeRelatorio() {
		return "relatoriocam19.jasper";
	}
}