package br.gov.ma.tce.reunire.dao.impl.d;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import br.gov.ma.tce.reunire.dao.DemonstrativoDao;
import br.gov.ma.tce.reunire.dao.impl.PrestacaoDaoImpl;
import br.gov.ma.tce.reunire.model.vo.d.RelatorioD010VO;
import br.gov.ma.tce.reunire.model.vo.gestor.UnidadeVO;

@Stateless
public class RelatorioD010DaoImpl extends PrestacaoDaoImpl<RelatorioD010VO> implements DemonstrativoDao<RelatorioD010VO>{

	@Override
	public List<RelatorioD010VO> recuperaDados(Map<String, Object> params) {
	
		String sql = 
		
		"select " +
			"unidade_id, nr, descricao, sum(previsao_atualizada) previsao_atualizada, sum(receita_realizada) receita_realizada " +
		"from (" +
			"select " + 
				"unidade_id,  " + 
				"regexp_replace(natureza_receita, '[.]', '', 'g') nr,  " + 
				"(case when nr.id_natureza_receita is not null then nr.descricao else 'CLASSIFICAÇÃO DESCONHECIDA' end) descricao,  " + 
				"(case " +
					"when natureza_receita like '9%' and previsao_atualizada > 0 then previsao_atualizada * (-1) " +
					"when natureza_receita like '9%' and previsao_atualizada < 0 then previsao_atualizada " +
					"else previsao_atualizada end) previsao_atualizada, " +
				"(case " +
					"when natureza_receita like '9%' and receita_realizada > 0 then receita_realizada * (-1) " +
					"when natureza_receita like '9%' and receita_realizada < 0 then receita_realizada " +
					"else receita_realizada end) receita_realizada " +
			"from  " + 
				"prestacao.bo01 bo  " +
			"left join sae.vw_natureza_receita nr on  " + 
				"(regexp_replace(nr.codigo_natureza_receita, '[.]', '', 'g') = regexp_replace(natureza_receita, '[.]', '', 'g') and nr.ativo = 'S') " +
			"where  " + 
				"unidade_id in (:unidades) and  " +
				"((:modulo is null) or (modulo_id = :modulo)) and  " +
				"cast(regexp_replace(natureza_receita, '[.]', '', 'g') as integer) > 0  " +
			"order by  " +
				"unidade_id, regexp_replace(natureza_receita, '[.]', '', 'g')) r " + 
		"group by unidade_id, nr, descricao " + 
		"order by unidade_id, nr";
		
		List<RelatorioD010VO> dados = new ArrayList<RelatorioD010VO>();
		
		List<UnidadeVO> listaUnidades = recuperarUnidades(params);
		List<Integer> listaIdsUnidades = extrairIds(listaUnidades);
		
		@SuppressWarnings("unchecked")
		List<Object[]> rows = entityManager.createNativeQuery(sql)
				.setParameter("unidades", listaIdsUnidades)
				.setParameter("modulo", params.get("modulo"))
				.getResultList();
		
		
		// Remover todos os itens que possuam descendentes
		
		/*rows.removeIf(new Predicate<Object[]>() {

			@Override
			public boolean test(Object[] obj) {
				
				Pattern pattern = Pattern.compile(".*([1-9]+)");
				String codigo = String.valueOf(obj[1]);
				Matcher matcher = pattern.matcher(codigo);
				
				matcher.find();
				
				return (rows.stream().filter(item -> String.valueOf(item[1]).startsWith(codigo.substring(0, matcher.end())) && Integer.valueOf(String.valueOf(item[1])).compareTo(Integer.valueOf(String.valueOf(obj[1]))) > 0).collect(Collectors.toList())).size() > 0;
			}
		});*/
		
		for (Object [] row : rows) {
		
			RelatorioD010VO dado = new RelatorioD010VO();
			
			Optional<UnidadeVO> unidade = listaUnidades.stream().filter(u -> u.getId().equals(Integer.parseInt(String.valueOf(row[0])))).findFirst();
			
			dado.setIdUnidade(Integer.parseInt(String.valueOf(row[0])));
			dado.setCodigoNaturezaReceita(String.valueOf(row[1]).
					replaceFirst("(\\d{1})(\\d{1})(\\d{1})(\\d{1})(\\d{2})(\\d{2})", "$1.$2.$3.$4.$5.$6"));
			
			dado.setDescricao(String.valueOf(row[2]));
			
			if (dado.getDescricao().equals("CLASSIFICAÇÃO DESCONHECIDA")) {
				
				try {
					
					Object naturezaReceita = entityManager.createNativeQuery("select descricao from sae.vw_natureza_receita where codigo_natureza_receita = :natureza limit 1")
							.setParameter("natureza", dado.getCodigoNaturezaReceita())
							.getSingleResult();
					
					if (naturezaReceita != null) {
						dado.setDescricao(String.valueOf(naturezaReceita));
					}
					
				} catch (NoResultException ex) {
					dado.setDescricao("CLASSIFICAÇÃO DESCONHECIDA");
				}
			}
			
			dado.setDescricaoUnidade(unidade != null ? unidade.get().getNome().toUpperCase() : "");
			
			dado.setValorOrcado(toBigDecimal(row[3]));
			dado.setValorArrecadado(toBigDecimal(row[4]));
			dado.setValorMais(dado.getValorArrecadado().compareTo(dado.getValorOrcado()) > 0 ? dado.getValorArrecadado().subtract(dado.getValorOrcado()) : BigDecimal.ZERO);
			dado.setValorMenos(dado.getValorOrcado().compareTo(dado.getValorArrecadado()) > 0 ? dado.getValorOrcado().subtract(dado.getValorArrecadado()) : BigDecimal.ZERO);
			
			dados.add(dado);
		}
		
		return dados;
	}

	@Override
	public String getNomeRelatorio() {	
		return "relatoriod010.jasper";
	}
}
