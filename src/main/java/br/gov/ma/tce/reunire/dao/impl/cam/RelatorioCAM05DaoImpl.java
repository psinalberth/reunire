package br.gov.ma.tce.reunire.dao.impl.cam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;

import br.gov.ma.tce.reunire.dao.DemonstrativoDao;
import br.gov.ma.tce.reunire.dao.impl.PrestacaoDaoImpl;
import br.gov.ma.tce.reunire.model.vo.cam.RelatorioCAM05VO;
import br.gov.ma.tce.reunire.model.vo.gestor.UnidadeVO;

@Stateless
public class RelatorioCAM05DaoImpl extends PrestacaoDaoImpl<RelatorioCAM05VO> implements DemonstrativoDao<RelatorioCAM05VO> {
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RelatorioCAM05VO> recuperaDados(Map<String, Object> params) {
		
		List<UnidadeVO> listaUnidades = recuperarUnidades(params);
		List<Integer> listaIdsUnidades = extrairIds(listaUnidades);
		
		String sql = 
				
		"	select unidade_id, finalidade_veiculo, cpf_cnpj_proprietario, nome_proprietario, " + 
		"		modelo_veiculo, placa_veiculo, renavam, cam05_id " +
		"	from prestacao.cam05 cam " +
		"	where cam.unidade_id in (:unidades) and " +
		"	((:modulo is null) or (cam.modulo_id = :modulo)) ";
		
		List<Object[]> rows = entityManager.createNativeQuery(sql)
				.setParameter("unidades", listaIdsUnidades)
				.setParameter("modulo", 1)
				.getResultList();
		
		List<RelatorioCAM05VO> dados = new ArrayList<RelatorioCAM05VO>(rows.size());
		
		params.put("exercicio", 2017);
		
		for (Object [] row : rows) {
			
			RelatorioCAM05VO dado = new RelatorioCAM05VO();
			
			Optional<UnidadeVO> unidade = listaUnidades.stream().filter(u -> u.getId().equals(Integer.parseInt(String.valueOf(row[0])))).findFirst();
			
			dado.setIdUnidade(Integer.parseInt(String.valueOf(row[0])));
			dado.setDescricaoUnidade(unidade != null ? unidade.get().getNome().toUpperCase() : "");
			dado.setFinalidade(String.valueOf(row[1]));
			dado.setCpfCnpjProprietario(String.valueOf(row[2]));
			dado.setNomeProprietario(String.valueOf(row[3]));
			dado.setModeloVeiculo(String.valueOf(row[4]));
			dado.setPlavaDoVeiculo(String.valueOf(row[5]));
			dado.setRenavam(String.valueOf(row[6]));
			dado.setId(Integer.parseInt( String.valueOf(row[7])));
			
			dados.add(dado);
		}
		
		return dados;
	}
	
	@Override
	public String getNomeRelatorio() {
		return "relatoriocam05.jasper";
	}
}