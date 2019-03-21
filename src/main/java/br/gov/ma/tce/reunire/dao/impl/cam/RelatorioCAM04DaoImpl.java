package br.gov.ma.tce.reunire.dao.impl.cam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;

import br.gov.ma.tce.reunire.dao.DemonstrativoDao;
import br.gov.ma.tce.reunire.dao.impl.PrestacaoDaoImpl;
import br.gov.ma.tce.reunire.model.vo.cam.RelatorioCAM04VO;
import br.gov.ma.tce.reunire.model.vo.gestor.UnidadeVO;

@Stateless
public class RelatorioCAM04DaoImpl extends PrestacaoDaoImpl<RelatorioCAM04VO> implements DemonstrativoDao<RelatorioCAM04VO> {

	@SuppressWarnings("unchecked")
	@Override
	public List<RelatorioCAM04VO> recuperaDados(Map<String, Object> params) {
		
		List<UnidadeVO> listaUnidades = recuperarUnidades(params);
		List<Integer> listaIdsUnidades = extrairIds(listaUnidades);
		
		String sql = 
		
		"select " + 
		"	unidade_id, upper(nome_povoado) nome_povoado, distancia, upper(forma_acesso) forma_acesso " + 
		"from " + 
		"	prestacao.cam04 " + 
		"where " + 
		"	unidade_id in (:unidades) and " + 
		"	((:modulo is null) or (modulo_id = :modulo)) " + 
		"order by " + 
		"	unidade_id, distancia, nome_povoado";
		
		String schema = params.get("exercicio") != null && ((Integer)params.get("exercicio")).equals(new Integer(2018)) ? "prestacao2018" : "prestacao";
		sql = sql.replaceAll("prestacao", schema);
		
		List<Object[]> rows = entityManager.createNativeQuery(sql)
				.setParameter("unidades", listaIdsUnidades)
				.setParameter("modulo", 1)
				.getResultList();
		
		List<RelatorioCAM04VO> dados = new ArrayList<RelatorioCAM04VO>(rows.size());
		
		params.put("exercicio", params.get("exercicio"));
		
		for (Object [] row : rows) {
			
			RelatorioCAM04VO dado = new RelatorioCAM04VO();
			
			Optional<UnidadeVO> unidade = listaUnidades.stream().filter(u -> u.getId().equals(Integer.parseInt(String.valueOf(row[0])))).findFirst();
			
			dado.setIdUnidade(Integer.parseInt(String.valueOf(row[0])));
			dado.setDescricaoUnidade(unidade != null ? unidade.get().getNome().toUpperCase() : "");
			dado.setNomePovoado(String.valueOf(row[1]));
			dado.setDistancia(new Double(String.valueOf(row[2])));
			dado.setFormaAcesso(String.valueOf(row[3]));
			
			dados.add(dado);
		}
		
		return dados;
	}

	@Override
	public String getNomeRelatorio() {
		return "relatoriocam04.jasper";
	}
}