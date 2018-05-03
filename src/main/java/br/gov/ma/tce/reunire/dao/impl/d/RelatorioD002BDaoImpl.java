package br.gov.ma.tce.reunire.dao.impl.d;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

import javax.ejb.Stateless;

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
		
		String sql = "select " + 
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
				"order by unidade, cod_cat, cod_gru, cod_mod, cod_elm ";
		
		List<UnidadeVO> listaUnidades = recuperarUnidades(params);
		List<Integer> listaIdsUnidades = extrairIds(listaUnidades);		
		
		List<RelatorioD002BVO> dados = new ArrayList<>();
		
		@SuppressWarnings("unchecked")
		List<Object[]> rows = entityManager.createNativeQuery(sql)
			.setParameter("unidade", listaIdsUnidades)
			.getResultList();
				
		for(Object[] row : rows) {
			
			RelatorioD002BVO dado = new RelatorioD002BVO();	
			
			Optional<UnidadeVO> unidade = listaUnidades.stream().filter(u -> u.getId().equals(Integer.parseInt(String.valueOf(row[0])))).findFirst();
			
			dado.setIdUnidade(Integer.parseInt(String.valueOf(row[0])));
			dado.setDescricaoUnidade(unidade != null ? unidade.get().getNome().toUpperCase() : "");
			dado.setCodigoCategoriaEconomica(row[1].toString());
			dado.setDescricaoCategoriaEconomica(row[2].toString());
			dado.setCodigoGrupoDespesa(row[3].toString());
			dado.setDescricaoGrupoDespesa(row[4].toString());
			dado.setCodigoModalidadeAplicacao(row[5].toString());
			dado.setDescricaoModalidadeAplicacao(row[6].toString());
			dado.setCodigoElementoDespesa(row[7].toString());
			dado.setDescricaoElementoDespesa(row[8].toString());			
			dado.setValor(new BigDecimal((new Double(row[9].toString()))));
			
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
