package br.gov.ma.tce.reunire.dao.impl.d;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
		
		List<UnidadeVO> listaUnidadeVO = recuperarUnidades(params);
		List<Integer> listaIdsUnidades = extrairIds(listaUnidadeVO);		
		
		List<RelatorioD002BVO> listaVo = new ArrayList<>();
		
		@SuppressWarnings("unchecked")
		List<Object[]> lista = entityManager.createNativeQuery(sql)
				.setParameter("unidade", listaIdsUnidades)
				.getResultList();
				
		for(Object[] l : lista) {
			RelatorioD002BVO relatorio = new RelatorioD002BVO();	
			relatorio.setIdUnidade(Integer.parseInt(l[0].toString()));
			for(UnidadeVO listaUnidade : listaUnidadeVO) {
				if(listaUnidade.getId() == relatorio.getIdUnidade().intValue()) {
					relatorio.setDescricaoUnidade(listaUnidade.getNome());
				}
			}
			relatorio.setCodigoCategoriaEconomica(l[1].toString());
			relatorio.setDescricaoCategoriaEconomica(l[2].toString());
			relatorio.setCodigoGrupoDespesa(l[3].toString());
			relatorio.setDescricaoGrupoDespesa(l[4].toString());
			relatorio.setCodigoModalidadeAplicacao(l[5].toString());
			relatorio.setDescricaoModalidadeAplicacao(l[6].toString());
			relatorio.setCodigoElementoDespesa(l[7].toString());
			relatorio.setDescricaoElementoDespesa(l[8].toString());			
			relatorio.setValor(new BigDecimal((new Double(l[9].toString()))));
			listaVo.add(relatorio);
		}
		
		return listaVo;
	}
}
