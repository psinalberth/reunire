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
				.setParameter("modulo", 1)
				.getResultList();
		
		List<RelatorioCAM21VO> dados = new ArrayList<RelatorioCAM21VO>(rows.size());
		
		for (Object [] row : rows) {
			
			RelatorioCAM21VO dado = new RelatorioCAM21VO();
			
			Optional<UnidadeVO> unidade = listaUnidades.stream().filter(u -> u.getId().equals(Integer.parseInt(String.valueOf(row[0])))).findFirst();
			
			dado.setIdUnidade(Integer.parseInt(String.valueOf(row[0])));
			dado.setDescricaoUnidade(unidade != null ? unidade.get().getNome().toUpperCase() : "");
			dado.setDeliberacao(String.valueOf(row[1]));
			dado.setExercicio(Integer.valueOf(String.valueOf(row[2])));
			dado.setRecomendacao(String.valueOf(row[3]));
			dado.setSituacao(String.valueOf(row[4]));
			dado.setJustificativa(String.valueOf(row[5]));
			
			dados.add(dado);
		}
		
		return dados;
	}

	@Override
	public String getNomeRelatorio() {
		return "relatoriocam21.jasper";
	}
}