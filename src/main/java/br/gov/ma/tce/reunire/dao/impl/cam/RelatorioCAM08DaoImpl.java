package br.gov.ma.tce.reunire.dao.impl.cam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;

import br.gov.ma.tce.reunire.dao.DemonstrativoDao;
import br.gov.ma.tce.reunire.dao.impl.PrestacaoDaoImpl;
import br.gov.ma.tce.reunire.model.vo.cam.RelatorioCAM08VO;
import br.gov.ma.tce.reunire.model.vo.gestor.UnidadeVO;

@Stateless
public class RelatorioCAM08DaoImpl extends PrestacaoDaoImpl<RelatorioCAM08VO> implements DemonstrativoDao<RelatorioCAM08VO> {
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RelatorioCAM08VO> recuperaDados(Map<String, Object> params) {
		
		List<UnidadeVO> listaUnidades = recuperarUnidades(params);
		List<Integer> listaIdsUnidades = extrairIds(listaUnidades);
		
		String sql = 
				
		"select unidade_id, unidade_ensino, endereco, " + 
		"	vaga_pre_escola, vaga_fundamental, vaga_medio, " + 
		"	quantidade_sala, quantidade_laboratorio, quantidade_biblioteca, quantidade_quadra, coalesce(vaga_creche, 0) vaga_creche " + 
		"from prestacao.cam08 cam " +
		"   where cam.unidade_id in (:unidades) and " +
		"	((:modulo is null) or (cam.modulo_id = :modulo)) ";
		
		List<Object[]> rows = entityManager.createNativeQuery(sql)
				.setParameter("unidades", listaIdsUnidades)
				.setParameter("modulo", 1)
				.getResultList();
		
		List<RelatorioCAM08VO> dados = new ArrayList<RelatorioCAM08VO>(rows.size());
		
		params.put("exercicio", 2017);
		
		for (Object [] row : rows) {
			
			RelatorioCAM08VO dado = new RelatorioCAM08VO();
			
			Optional<UnidadeVO> unidade = listaUnidades.stream().filter(u -> u.getId().equals(Integer.parseInt(String.valueOf(row[0])))).findFirst();
			
			dado.setIdUnidade(Integer.parseInt(String.valueOf(row[0])));
			dado.setDescricaoUnidade(unidade != null ? unidade.get().getNome().toUpperCase() : "");
			dado.setUnidadeEnsino(String.valueOf(row[1]));
			dado.setEndereco(String.valueOf(row[2]));
			dado.setCapacidadeAlunosPreEscolar(Integer.parseInt(String.valueOf(row[3])));
			dado.setCapacidadeAlunosFundamental(Integer.parseInt(String.valueOf(row[4])));
			dado.setCapacidadeAlunosEnsinoMedio(Integer.parseInt(String.valueOf(row[5])));
			dado.setNumeroSalaDeAula(Integer.parseInt(String.valueOf(row[6])));
			dado.setNumeroLaboratorio(Integer.parseInt(String.valueOf(row[7])));
			dado.setNumeroBiblioteca(Integer.parseInt(String.valueOf(row[8])));
			dado.setNumeroQuadraEsporte(Integer.parseInt(String.valueOf(row[9])));
			dado.setNumeroCreches(Integer.valueOf(String.valueOf(row[10])));
			
			dados.add(dado);
		}
		
		return dados;
	}
	
	@Override
	public String getNomeRelatorio() {
		return "relatoriocam08.jasper";
	}
}