package br.gov.ma.tce.reunire.dao.impl.cam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;

import br.gov.ma.tce.reunire.dao.DemonstrativoDao;
import br.gov.ma.tce.reunire.dao.impl.PrestacaoDaoImpl;
import br.gov.ma.tce.reunire.model.vo.cam.RelatorioCAM21VO;
import br.gov.ma.tce.reunire.model.vo.gestor.UnidadeVO;

@Stateless
public class RelatorioCAM21DaoImpl extends PrestacaoDaoImpl<RelatorioCAM21VO> implements DemonstrativoDao<RelatorioCAM21VO> {

	@SuppressWarnings("unchecked")
	@Override
	public List<RelatorioCAM21VO> recuperaDados(Map<String, Object> params) {
		
		List<UnidadeVO> listaUnidades = recuperarUnidades(params);
		List<Integer> listaIdsUnidades = extrairIds(listaUnidades);
		
		String sql = 
				
		"select " +
			"cam.unidade_id, cam.numero_deliberacao, cam.exercicio, cam.recomendacao, cam.situacao, cam.providencia, cam.justificativa " +
		"from " +
			"prestacao.cam21 cam " +
		"where " +
			"cam.unidade_id in (:unidades) and " +
			"((:modulo is null) or (cam.modulo_id = :modulo)) " +
		"order by " +
			"cam.unidade_id, cam.exercicio, cam.numero_deliberacao";
		
		List<Object[]> rows = entityManager.createNativeQuery(sql)
				.setParameter("unidades", listaIdsUnidades)
				.setParameter("modulo", params.get("modulo"))
				.getResultList();
		
		List<RelatorioCAM21VO> dados = new ArrayList<RelatorioCAM21VO>(rows.size());
		
		for (Object [] row : rows) {
			
			RelatorioCAM21VO dado = new RelatorioCAM21VO();
			
			Optional<UnidadeVO> unidade = listaUnidades.stream().filter(u -> u.getId().equals(Integer.parseInt(String.valueOf(row[0])))).findFirst();
			
			dado.setIdUnidade(Integer.parseInt(String.valueOf(row[0])));
			dado.setDescricaoUnidade(unidade != null ? unidade.get().getNome().toUpperCase() : "");
			dado.setDeliberacao(row[1] != null ? String.valueOf(row[1]) : "N/A");
			dado.setExercicio(Integer.valueOf(String.valueOf(row[2])));
			dado.setRecomendacao(row[3] != null ? String.valueOf(row[3]) : "N/A");
			dado.setSituacao(row[4] != null ? String.valueOf(row[4]) : "N/A");
			dado.setProvidencias(row[5] != null ? String.valueOf(row[5]) : "N/A");
			dado.setJustificativa(row[6] != null ? String.valueOf(row[6]) : "N/A");
			
			dados.add(dado);
		}
		
		return dados;
	}

	@Override
	public String getNomeRelatorio() {
		return "relatoriocam21.jasper";
	}
}