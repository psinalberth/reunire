package br.gov.ma.tce.reunire.dao.impl.cam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;

import br.gov.ma.tce.reunire.dao.DemonstrativoDao;
import br.gov.ma.tce.reunire.dao.impl.PrestacaoDaoImpl;
import br.gov.ma.tce.reunire.model.vo.cam.RelatorioCAM23VO;
import br.gov.ma.tce.reunire.model.vo.gestor.UnidadeVO;

@Stateless
public class RelatorioCAM23DaoImpl extends PrestacaoDaoImpl<RelatorioCAM23VO> implements DemonstrativoDao<RelatorioCAM23VO> {
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RelatorioCAM23VO> recuperaDados(Map<String, Object> params) {
		
		List<UnidadeVO> listaUnidades = recuperarUnidades(params);
		List<Integer> listaIdsUnidades = extrairIds(listaUnidades);
		
		String sql = 
				
		"select " +
			"unidade_id, identificacao, quantidade_estoque, valor_medio, setor " +
		"from " +
			"prestacao.cam23 " +
		"where " +
			"unidade_id in (:unidades) and " +
			"((:modulo is null) or (modulo_id = :modulo))";
		
		String schema = params.get("exercicio") != null && ((Integer)params.get("exercicio")).equals(new Integer(2018)) ? "prestacao2018" : "prestacao";
		sql = sql.replaceAll("prestacao", schema);
		
		List<Object[]> rows = entityManager.createNativeQuery(sql)
				.setParameter("unidades", listaIdsUnidades)
				.setParameter("modulo", params.get("modulo"))
				.getResultList();
		
		List<RelatorioCAM23VO> dados = new ArrayList<RelatorioCAM23VO>(rows.size());
		
		for (Object[] row : rows) {
			
			RelatorioCAM23VO dado = new RelatorioCAM23VO();
			
			Optional<UnidadeVO> unidade = listaUnidades.stream().filter(u -> u.getId().equals(Integer.parseInt(String.valueOf(row[0])))).findFirst();
			
			dado.setIdUnidade(Integer.parseInt(String.valueOf(row[0])));
			dado.setDescricaoUnidade(unidade != null ? unidade.get().getNome().toUpperCase() : "");
			
			if (unidade != null) {
				
				dado.setIdOrgao(unidade.get().getOrgao().getId());
				dado.setDescricaoOrgao(unidade.get().getOrgao().getNome());
			}
			
			dado.setIdentificacaoBem(String.valueOf(row[1]));
			dado.setQuantidade(Integer.parseInt(String.valueOf(row[2])));
			dado.setValorUnitario(row[3] != null ? new BigDecimal(String.valueOf(row[3])) : null);
			dado.setSetorResponsavel(String.valueOf(row[4]));
			
			dados.add(dado);
		}
		
		dados.sort((d1, d2) -> d1.getIdOrgao().compareTo(d2.getIdOrgao()));
		
		return dados;
	}

	@Override
	public String getNomeRelatorio() {
		return "relatoriocam23.jasper";
	}
}