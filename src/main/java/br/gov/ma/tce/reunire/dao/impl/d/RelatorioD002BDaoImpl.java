package br.gov.ma.tce.reunire.dao.impl.d;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import br.gov.ma.tce.reunire.dao.DemonstrativoDao;
import br.gov.ma.tce.reunire.dao.impl.PrestacaoDaoImpl;
import br.gov.ma.tce.reunire.model.vo.d.RelatorioD002BVO;
import br.gov.ma.tce.reunire.model.vo.gestor.UnidadeVO;

@Stateless
public class RelatorioD002BDaoImpl extends PrestacaoDaoImpl<RelatorioD002BVO> implements DemonstrativoDao<RelatorioD002BVO> {

	@Override
	public String getNomeRelatorio() {
		return "relatoriod002b.jasper";
	}

	@Override
	public List<RelatorioD002BVO> recuperaDados(Map<String, Object> params) {
		
		String sql = 
		
		"select  " + 
		"	d.unidade_id, " + 
		"	(case  " + 
		"		when cd.id_categoria_ec_despesa is not null then cd.codigo || '.0.00.00.00' " + 
		"		else substring(d.nd, 1, 1) || '.0.00.00.00' " + 
		"	end) cod_cat, cd.descricao desc_cat,  " + 
		"	(case  " + 
		"		when gru.id_grupo_despesa is not null then cd.codigo || '.'|| gru.codigo || '.00.00.00' " + 
		"		else  substring(d.nd, 1, 1) || '.' || substring(d.nd, 2, 1) || '.00.00.00' " + 
		"	end) cod_gru, gru.descricao desc_gru, " + 
		"	(case  " + 
		"		when ma.id_modalidade_aplicacao is not null then cd.codigo || '.'|| gru.codigo || '.' || to_char(ma.codigo, 'FM00') || '.00.00' " + 
		"		else  substring(d.nd, 1, 1) || '.' || substring(d.nd, 2, 1) || '.' || substring(d.nd, 3, 2) || '.00.00' " + 
		"	end) cod_mod, ma.descricao desc_mod, " + 
		"	(case  " + 
		"		when ele.id_elemento_despesa is not null then cd.codigo || '.'|| gru.codigo || '.' || to_char(ma.codigo, 'FM00') || '.' || to_char(ele.codigo, 'FM00') || '.00' " + 
		"		else  substring(d.nd, 1, 1) || '.' || substring(d.nd, 2, 1) || '.' || substring(d.nd, 3, 2) || '.' || substring(d.nd, 5, 2) || '.00' " + 
		"	end) cod_ele, ele.descricao desc_ele, " + 
		"	d.valor_atual, nd.id_natureza_despesa " + 
		"from ( " + 
		"	select " + 
		"		d.unidade_id, substring(regexp_replace(d.natureza_despesa, '[.]', '', 'g'), 1, 6) || '00' nd, d.descricao, " + 
		"		sum(d.valor_atual) valor_atual " + 
		"	from  " + 
		"		prestacao.d002b d " + 
		"	where " + 
		"		d.unidade_id in (:unidades) and " + 
		"		d.valor_atual <> 0 and  " + 
		"		((:modulo is null) or (d.modulo_id = :modulo))  " + 
		"	group by " + 
		"		d.unidade_id, substring(regexp_replace(d.natureza_despesa, '[.]', '', 'g'), 1, 6), d.descricao " + 
		"	order by " + 
		"		d.unidade_id, substring(regexp_replace(d.natureza_despesa, '[.]', '', 'g'), 1, 6), d.descricao) d " + 
		"left join (select id, regexp_replace(codigo, '[.]', '', 'g') codigo_natureza_despesa from sae.vw_natureza_despesa where codigo ~ '00$' and ativo = 'S') vw on " + 
		"	vw.codigo_natureza_despesa = d.nd " + 
		"left join sae.sae_natureza_despesa nd on nd.id_natureza_despesa = vw.id and nd.ativo = 'S' " + 
		"left join sae.sae_categoria_ec_despesa cd on cd.id_categoria_ec_despesa = nd.id_categoria_ec_despesa and cd.ativo = 'S' " + 
		"left join sae.sae_grupo_despesa gru on gru.id_grupo_despesa = nd.id_grupo_despesa and gru.ativo = 'S' " + 
		"left join sae.sae_modalidade_aplicacao ma on ma.id_modalidade_aplicacao = nd.id_modalidade_aplicacao and ma.ativo = 'S' " + 
		"left join sae.sae_elemento_despesa ele on ele.id_elemento_despesa = nd.id_elemento_despesa and ele.ativo = 'S' " + 
		"left join sae.sae_sub_elemento_despesa sub on sub.id_sub_elemento_despesa = nd.id_sub_elemento_despesa and sub.ativo = 'S' " + 
		"order by " + 
		"	cod_cat, cod_gru, cod_mod, cod_ele";
			
				/*"select " + 
				"   d.unidade_id unidade, " + 
				"   ced.codigo || '.0.00.00.00' cod_cat, ced.descricao desc_cat, " + 
				"   ced.codigo || '.'|| gru.codigo || '.00.00.00' cod_gru, gru.descricao desc_gru, " + 
				"   ced.codigo || '.'|| gru.codigo || '.' ||  ma.codigo || '.00.00' cod_mod, ma.descricao desc_mod, " + 
				"   ced.codigo || '.'|| gru.codigo || '.' ||  ma.codigo || '.' || to_char(ed.codigo, 'FM00') || '.00' cod_elm, ed.descricao desc_elm, " + 
				"   sum(d.valor_atual) valor " + 
				"from prestacao.d002b d " + 
				"	inner join sae.vw_natureza_despesa vw_nd on regexp_replace(d.natureza_despesa, '[.]', '', 'g') = regexp_replace(vw_nd.codigo, '[.]','','g') and vw_nd.ativo = 'S' " + 
				"	inner join sae.sae_natureza_despesa nd on nd.id_natureza_despesa = vw_nd.id and nd.ativo = 'S' " + 
				"	inner join sae.sae_categoria_ec_despesa ced on ced.id_categoria_ec_despesa = nd.id_categoria_ec_despesa and ced.ativo = 'S' " + 
				"	inner join sae.sae_grupo_despesa gru on gru.id_grupo_despesa = nd.id_grupo_despesa and gru.ativo = 'S' " + 
				"	inner join sae.sae_modalidade_aplicacao ma on ma.id_modalidade_aplicacao = nd.id_modalidade_aplicacao and ma.ativo = 'S' " + 
				"	inner join sae.sae_elemento_despesa ed on ed.id_elemento_despesa = nd.id_elemento_despesa and ed.ativo = 'S' " + 
				"where d.unidade_id in(:unidade) " +
				"group by unidade, cod_cat, desc_cat, cod_gru, desc_gru, cod_mod, desc_mod, cod_elm, desc_elm " + 
				"order by unidade, cod_cat, cod_gru, cod_mod, cod_elm ";*/
		
		List<UnidadeVO> listaUnidades = recuperarUnidades(params);
		List<Integer> listaIdsUnidades = extrairIds(listaUnidades);		
		
		List<RelatorioD002BVO> dados = new ArrayList<>();
		
		@SuppressWarnings("unchecked")
		List<Object[]> rows = entityManager.createNativeQuery(sql)
			.setParameter("unidades", listaIdsUnidades)
			.setParameter("modulo", params.get("modulo"))
			.getResultList();
				
		for(Object[] row : rows) {
			
			RelatorioD002BVO dado = new RelatorioD002BVO();	
			
			Optional<UnidadeVO> unidade = listaUnidades.stream().filter(u -> u.getId().equals(Integer.parseInt(String.valueOf(row[0])))).findFirst();
			
			dado.setIdUnidade(Integer.parseInt(String.valueOf(row[0])));
			dado.setDescricaoUnidade(unidade != null ? unidade.get().getNome().toUpperCase() : "");
			dado.setCodigoCategoriaEconomica(row[1].toString());
			dado.setCodigoGrupoDespesa(row[3].toString());
			dado.setCodigoModalidadeAplicacao(row[5].toString());
			dado.setCodigoElementoDespesa(row[7].toString());
			dado.setValor(new BigDecimal((new Double(row[9].toString()))));
			
			if (row[10] == null) {
				
				try {
					
					Object categoria = entityManager.createNativeQuery("select descricao from sae.vw_natureza_despesa where codigo = :natureza and ativo = 'S'")
							.setParameter("natureza", dado.getCodigoCategoriaEconomica())
							.getSingleResult();
					
					if (categoria != null) {
						dado.setDescricaoCategoriaEconomica(String.valueOf(categoria));
					}
					
				} catch (NoResultException ex) {
					dado.setDescricaoCategoriaEconomica("CLASSIFICAÇÃO DESCONHECIDA");
				}
				
				try {
					
					Object grupo = entityManager.createNativeQuery("select descricao from sae.vw_natureza_despesa where codigo = :natureza and ativo = 'S'")
							.setParameter("natureza", dado.getCodigoGrupoDespesa())
							.getSingleResult();
					
					if (grupo != null) {
						dado.setDescricaoGrupoDespesa(String.valueOf(grupo));
					}
					
				} catch (NoResultException ex) {
					dado.setDescricaoGrupoDespesa("CLASSIFICAÇÃO DESCONHECIDA");
				}
				
				try {
					
					Object modalidade = entityManager.createNativeQuery("select descricao from sae.vw_natureza_despesa where codigo = :natureza and ativo = 'S'")
							.setParameter("natureza", dado.getCodigoModalidadeAplicacao())
							.getSingleResult();
					
					if (modalidade != null) {
						dado.setDescricaoModalidadeAplicacao(String.valueOf(modalidade));
					}
					
				} catch (NoResultException ex) {
					dado.setDescricaoModalidadeAplicacao("CLASSIFICAÇÃO DESCONHECIDA");
				}
				
				try {
					
					Object elemento = entityManager.createNativeQuery("select descricao from sae.vw_natureza_despesa where codigo = :natureza and ativo = 'S'")
							.setParameter("natureza", dado.getCodigoElementoDespesa())
							.getSingleResult();
					
					if (elemento != null) {
						dado.setDescricaoElementoDespesa(String.valueOf(elemento));
					}
					
				} catch (NoResultException ex) {
					dado.setDescricaoElementoDespesa("CLASSIFICAÇÃO DESCONHECIDA");
				}
				
				
			} else {
			
				dado.setDescricaoCategoriaEconomica(row[2].toString());
				dado.setDescricaoGrupoDespesa(row[4].toString());
				dado.setDescricaoModalidadeAplicacao(row[6].toString());
				dado.setDescricaoElementoDespesa(row[8].toString());
			}
			
			dados.add(dado);
		}
		
		Optional<RelatorioD002BVO> reservaContingencia = dados.stream().filter(d -> d.getCodigoElementoDespesa().equals("9.9.99.99.00")).findFirst();
		
		if (reservaContingencia != null && reservaContingencia.isPresent()) {
			params.put("reservaContingencia", reservaContingencia.get().getValor());
		}
		
		dados.removeIf(new Predicate<RelatorioD002BVO>() {

			@Override
			public boolean test(RelatorioD002BVO item) {
				return item.getCodigoElementoDespesa().equals("9.9.99.99.00");
			}
		});
		
		return dados;
	}
}
