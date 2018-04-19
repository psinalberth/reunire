package br.gov.ma.tce.reunire.dao.impl.cam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;

import br.gov.ma.tce.reunire.dao.DemonstrativoDao;
import br.gov.ma.tce.reunire.dao.impl.PrestacaoDaoImpl;
import br.gov.ma.tce.reunire.model.vo.RelatorioCAM13VO;
import br.gov.ma.tce.reunire.model.vo.gestor.UnidadeVO;

@Stateless
public class RelatorioCAM13DaoImpl extends PrestacaoDaoImpl<RelatorioCAM13VO> implements DemonstrativoDao<RelatorioCAM13VO> {

	@SuppressWarnings("unchecked")
	@Override
	public List<RelatorioCAM13VO> recuperaDados(Map<String, Object> params) {
		
		List<UnidadeVO> listaUnidades = recuperarUnidades(params);
		List<Integer> listaIdsUnidades = extrairIds(listaUnidades);
		
		String sql = 
				
		"select " + 
		"	unidade_id, nome, " + 
		"	regexp_replace(lpad(cpf, 11, '0'), '([[:digit:]]{3})([[:digit:]]{3})([[:digit:]]{3})([[:digit:]]{2})', '\\1.\\2.\\3-\\4') cpf, " +  
		"	data_inscricao, data_concessao, tipo_beneficio, provento " + 
		"from  " + 
		"	prestacao.cam13 cam " + 
		"where " + 
		"	cam.unidade_id in (:unidades) and " + 
		"	((:modulo is null) or (cam.modulo_id = :modulo)) " + 
		"order by " + 
		"	cam.unidade_id, nome";
		
		List<Object[]> rows = entityManager.createNativeQuery(sql)
				.setParameter("unidades", listaIdsUnidades)
				.setParameter("modulo", 1)
				.getResultList();
		
		List<RelatorioCAM13VO> dados = new ArrayList<RelatorioCAM13VO>();
		
		for (Object[] row : rows) {
			
			RelatorioCAM13VO dado = new RelatorioCAM13VO();
			
			Optional<UnidadeVO> unidade = listaUnidades.stream().filter(u -> u.getId().equals(Integer.parseInt(String.valueOf(row[0])))).findFirst();
			
			dado.setIdUnidade(Integer.parseInt(String.valueOf(row[0])));
			dado.setDescricaoUnidade(unidade != null ? unidade.get().getNome().toUpperCase() : "");
			dado.setNome(String.valueOf(row[1]));
			dado.setCpf(String.valueOf(row[2]));
			dado.setDataInscricao(toDate(row[3]));
			dado.setDataConcessao(toDate(row[4]));
			dado.setTipoBeneficio(String.valueOf(row[5]));
			dado.setProvento(toBigDecimal(row[6]));
			
			dados.add(dado);
		}
		
		return dados;
	}

	@Override
	public String getNomeRelatorio() {
		return "relatoriocam13.jasper";
	}
}