package br.gov.ma.tce.reunire.dao.impl.cam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;

import br.gov.ma.tce.reunire.dao.DemonstrativoDao;
import br.gov.ma.tce.reunire.dao.impl.PrestacaoDaoImpl;
import br.gov.ma.tce.reunire.model.vo.cam.RelatorioCAM11VO;
import br.gov.ma.tce.reunire.model.vo.gestor.UnidadeVO;

@Stateless
public class RelatorioCAM11DaoImpl extends PrestacaoDaoImpl<RelatorioCAM11VO> implements DemonstrativoDao<RelatorioCAM11VO> {
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RelatorioCAM11VO> recuperaDados(Map<String, Object> params) {
		
		List<UnidadeVO> listaUnidades = recuperarUnidades(params);
		List<Integer> listaIdsUnidades = extrairIds(listaUnidades);
		
		String sql = 
				
		"	select unidade_id, quantidade_efetivo_anomenos1, " + 
		"				valor_efetivo_anomenos1, quantidade_efetivo, " + 
		"				valor_efetivo, quantidade_efetivo_anomais1, " + 
		"				valor_efetivo_anomais1, quantidade_efetivo_anomais2, " + 
		"				valor_efetivo_anomais2, " + 
		"				quantidade_comissionado_anomenos1, " + 
		"				valor_comissionado_anomenos1, quantidade_comissionado, " + 
		"				valor_comissionado, quantidade_comissionado_anomais1, " + 
		"				valor_comissionado_anomais1, quantidade_comissionado_anomais2, " + 
		"				valor_comissionado_anomais2, " + 
		"				quantidade_temporario_anomenos1, " + 
		"				valor_temporario_anomenos1, quantidade_temporario, " + 
		"				valor_temporario, quantidade_temporario_anomais1, " + 
		"				valor_temporario_anomais1, quantidade_temporario_anomais2, " + 
		"				valor_temporario_anomais2, receita_liquida_anomenos1, receita_liquida, " + 
		"				receita_liquida_anomais1, receita_liquida_anomais2 " + 
		"	from prestacao.cam11 cam " +
		"		where cam.unidade_id in (:unidades) and " + 
		"		((:modulo is null) or (cam.modulo_id = :modulo))";
		
		List<Object[]> rows = entityManager.createNativeQuery(sql)
				.setParameter("unidades", listaIdsUnidades)
				.setParameter("modulo", 1)
				.getResultList();
		

		List<RelatorioCAM11VO> dados = new ArrayList<RelatorioCAM11VO>(rows.size());
		
		params.put("exercicio", 2017);
		
		for (Object [] row : rows) {
			
			RelatorioCAM11VO dado = new RelatorioCAM11VO();
			
			Optional<UnidadeVO> unidade = listaUnidades.stream().filter(u -> u.getId().equals(Integer.parseInt(String.valueOf(row[0])))).findFirst();
			
			dado.setIdUnidade(Integer.parseInt(String.valueOf(row[0])));
			dado.setDescricaoUnidade(unidade != null ? unidade.get().getNome().toUpperCase() : "");
			
			dado.setEfetivo(String.valueOf("EFETIVO"));
			dado.setQuantidadeEfetivoAnoMenos1(Integer.parseInt(String.valueOf(row[1])));
			dado.setValorEfetivoAnoMenos1(new BigDecimal(new Double(String.valueOf(row[2]))));
			dado.setQuantidadeEfetivo(Integer.parseInt(String.valueOf(row[3])));
			dado.setValorEfetivo(new BigDecimal(new Double(String.valueOf(row[4]))));
			dado.setQuantidadeEfetivoAnoMais1(Integer.parseInt(String.valueOf(row[5])));
			dado.setValorEfetivoAnoMais1(new BigDecimal(new Double(String.valueOf(row[6]))));
			dado.setQuantidadeEfetivoAnoMais2(Integer.parseInt(String.valueOf(row[7])));
			dado.setValorEfetivoAnoMais2(new BigDecimal(new Double(String.valueOf(row[8]))));
			
			dado.setComissionado(String.valueOf("COMISSIONADO"));
			dado.setQuantidadeComissionadoAnoMenos1(Integer.parseInt(String.valueOf(row[9])));
			dado.setValorComissionadoAnoMenos1(new BigDecimal(new Double(String.valueOf(row[10]))));
			dado.setQuantidadeComissionado(Integer.parseInt(String.valueOf(row[11])));
			dado.setValorComissionado(new BigDecimal(new Double(String.valueOf(row[12]))));
			dado.setQuantidadeComissionadoAnoMais1(Integer.parseInt(String.valueOf(row[13])));
			dado.setValorComissionadoAnoMais1(new BigDecimal(new Double(String.valueOf(row[14]))));
			dado.setQuantidadeComissionadoAnoMais2(Integer.parseInt(String.valueOf(row[15])));
			dado.setValorComissionadoAnoMais2(new BigDecimal(new Double(String.valueOf(row[16]))));
			
			dado.setTemporario(String.valueOf("TEMPORÁRIO"));
			dado.setQuantidadeTemporarioAnoMenos1(Integer.parseInt(String.valueOf(row[17])));
			dado.setValorTemporarioAnoMenos1(new BigDecimal(new Double(String.valueOf(row[18]))));
			dado.setQuantidadeTemporario(Integer.parseInt(String.valueOf(row[19])));
			dado.setValorTemporario(new BigDecimal(new Double(String.valueOf(row[20]))));
			dado.setQuantidadeTemporarioAnoMais1(Integer.parseInt(String.valueOf(row[21])));
			dado.setValorTemporarioAnoMais1(new BigDecimal(new Double(String.valueOf(row[22]))));
			dado.setQuantidadeTemporarioAnoMais2(Integer.parseInt(String.valueOf(row[23])));
			dado.setValorTemporarioAnoMais2(new BigDecimal(new Double(String.valueOf(row[24]))));
			
			dado.setRcl(String.valueOf("RCL"));
			dado.setReceitaLiquidaAnoMenos1(new BigDecimal(new Double(String.valueOf(row[25]))));
			dado.setReceitaLiquida(new BigDecimal(new Double(String.valueOf(row[26]))));
			dado.setReceitaLiquidaAnoMais1(new BigDecimal(new Double(String.valueOf(row[27]))));
			dado.setReceitaLiquidaAnoMais2(new BigDecimal(new Double(String.valueOf(row[28]))));
			
			dados.add(dado);
		}
		
		return dados;
	}
	
	@Override
	public String getNomeRelatorio() {
		return "relatoriocam11.jasper";
	}
}