package br.gov.ma.tce.reunire.dao.impl.cam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;

import br.gov.ma.tce.reunire.dao.DemonstrativoDao;
import br.gov.ma.tce.reunire.dao.impl.PrestacaoDaoImpl;
import br.gov.ma.tce.reunire.model.vo.cam.RelatorioCAM10VO;
import br.gov.ma.tce.reunire.model.vo.gestor.UnidadeVO;

@Stateless
public class RelatorioCAM10DaoImpl extends PrestacaoDaoImpl<RelatorioCAM10VO> implements DemonstrativoDao<RelatorioCAM10VO> {

	@SuppressWarnings("unchecked")
	@Override
	public List<RelatorioCAM10VO> recuperaDados(Map<String, Object> params) {
		
		List<UnidadeVO> listaUnidades = recuperarUnidades(params);
		List<Integer> listaIdsUnidades = extrairIds(listaUnidades);
		
		String sql = 
				
		"select " + 
			"unidade_id, upper(tipo_estabelecimento) tipo_estabelecimento, upper(nome) nome, servico_disponivel, upper(endereco) endereco, " +
			"quantidade_medico, quantidade_enfermeiro, quantidade_outro, quantidade_atendimento " +
		"from " + 
			"prestacao.cam10 " +
		"where " +
			"unidade_id in (:unidades) and " +
			"((:modulo is null) or (modulo_id = :modulo)) " +
		"order by unidade_id, tipo_estabelecimento, nome, servico_disponivel";
		
		List<Object[]> rows = entityManager.createNativeQuery(sql)
				.setParameter("unidades", listaIdsUnidades)
				.setParameter("modulo", 1)
				.getResultList();
		
		List<RelatorioCAM10VO> dados = new ArrayList<RelatorioCAM10VO>(rows.size());
		
		params.put("exercicio", 2017);
		
		for (Object [] row : rows) {
			
			RelatorioCAM10VO dado = new RelatorioCAM10VO();
			
			Optional<UnidadeVO> unidade = listaUnidades.stream().filter(u -> u.getId().equals(Integer.parseInt(String.valueOf(row[0])))).findFirst();
			
			dado.setIdUnidade(Integer.parseInt(String.valueOf(row[0])));
			dado.setDescricaoUnidade(unidade != null ? unidade.get().getNome().toUpperCase() : "");
			dado.setTipo(String.valueOf(row[1]));
			dado.setNome(String.valueOf(row[2]));
			dado.setServicos(String.valueOf(String.valueOf(row[3])));
			dado.setEndereco(String.valueOf(row[4]));
			dado.setNumeroMedicos(Integer.parseInt(String.valueOf(row[5])));
			dado.setNumeroEnfermeiros(Integer.parseInt(String.valueOf(row[6])));
			dado.setNumeroOutros(Integer.parseInt(String.valueOf(row[7])));
			dado.setAtendimentos(Integer.parseInt(String.valueOf(row[8])));
			
			dados.add(dado);
		}
		
		return dados;
	}

	@Override
	public String getNomeRelatorio() {
		return "relatoriocam10.jasper";
	}
}
