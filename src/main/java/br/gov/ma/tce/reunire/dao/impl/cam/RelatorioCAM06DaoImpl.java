package br.gov.ma.tce.reunire.dao.impl.cam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;

import br.gov.ma.tce.reunire.dao.DemonstrativoDao;
import br.gov.ma.tce.reunire.dao.impl.PrestacaoDaoImpl;
import br.gov.ma.tce.reunire.model.vo.cam.RelatorioCAM06VO;
import br.gov.ma.tce.reunire.model.vo.gestor.UnidadeVO;

@Stateless
public class RelatorioCAM06DaoImpl extends PrestacaoDaoImpl<RelatorioCAM06VO> implements DemonstrativoDao<RelatorioCAM06VO> {

	@SuppressWarnings("unchecked")
	@Override
	public List<RelatorioCAM06VO> recuperaDados(Map<String, Object> params) {
		
		String sql = 
				
		"select " + 
			"unidade_id, numero_processo, cpf_suprido, finalidade_adiantamento, data_recebimento, valor, prazo_aplicacao, data_prestacao, situacao " +
		"from " +
			"prestacao.cam06 " +
		"where " +
			"unidade_id in (:unidades) and " +
			"((:modulo is null) or (modulo_id = :modulo)) " +
		"order by " +
			"unidade_id, numero_processo, cpf_suprido, finalidade_adiantamento, data_recebimento, valor, prazo_aplicacao, data_prestacao, situacao";
		
		String schema = params.get("exercicio") != null && ((Integer)params.get("exercicio")).equals(new Integer(2018)) ? "prestacao2018" : "prestacao";
		sql = sql.replaceAll("prestacao", schema);
		
		List<UnidadeVO> listaUnidades = recuperarUnidades(params);
		List<Integer> listaIdsUnidades = extrairIds(listaUnidades);
		
		List<Object[]> rows = entityManager.createNativeQuery(sql)
				.setParameter("unidades", listaIdsUnidades)
				.setParameter("modulo", params.get("modulo"))
				.getResultList();
		
		List<RelatorioCAM06VO> dados = new ArrayList<RelatorioCAM06VO>(rows.size());
		
		for(Object[] row : rows) {
			
			RelatorioCAM06VO dado = new RelatorioCAM06VO();
			
			Optional<UnidadeVO> unidade = listaUnidades.stream().filter(u -> u.getId().equals(Integer.parseInt(String.valueOf(row[0])))).findFirst();
			
			dado.setIdUnidade(Integer.parseInt(String.valueOf(row[0])));
			dado.setDescricaoUnidade(unidade != null ? unidade.get().getNome().toUpperCase() : "");
			dado.setNumeroProcesso(String.valueOf(row[1]));
			dado.setCpfSuprido(toPessoa(row[2]));
			dado.setFinalidadeAdiantamento(String.valueOf(row[3]));
			dado.setDataRecebimento(toDate(row[4]));
			dado.setValor(toBigDecimal(row[5]));
			dado.setPrazoAplicacao(Integer.valueOf(String.valueOf(row[6])));
			dado.setDataPrestacaoContas(toDate(row[7]));
			dado.setSituacao(String.valueOf(row[8]));
			
			dados.add(dado);
		}
		
		return dados;
	}

	@Override
	public String getNomeRelatorio() {		
		return "relatoriocam06.jasper";
	}
}