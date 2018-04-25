package br.gov.ma.tce.reunire.dao.impl.d;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;

import br.gov.ma.tce.reunire.dao.DemonstrativoDao;
import br.gov.ma.tce.reunire.dao.impl.PrestacaoDaoImpl;
import br.gov.ma.tce.reunire.model.vo.d.RelatorioD002AVO;
import br.gov.ma.tce.reunire.model.vo.gestor.UnidadeVO;

@Stateless
public class RelatorioD002ADaoImpl extends PrestacaoDaoImpl<RelatorioD002AVO> implements DemonstrativoDao<RelatorioD002AVO> {

	@Override
	public String getNomeRelatorio() {
		return "relatoriod002a.jasper";
	}

	@Override
	public List<RelatorioD002AVO> recuperaDados(Map<String, Object> params) {
		
		String sql = 
				
		"select " + 
		"	d.unidade_id, " + 
		"	cr.codigo || '.0.0.0.00.00' cod_cat, cr.descricao desc_cat,  " + 
		"	cr.codigo || '.'|| o.codigo || '.0.0.00.00' cod_ori, o.descricao desc_ori,  " + 
		"	cr.codigo || '.'|| o.codigo || '.' ||  e.codigo || '.0.00.00' cod_esp, e.descricao desc_esp,  " + 
		"	cr.codigo || '.'|| o.codigo || '.' ||  e.codigo || '.' || r.codigo || '.00.00' cod_rub, r.descricao desc_rub,   " + 
		"	cr.codigo || '.'|| o.codigo || '.' ||  e.codigo || '.' || r.codigo || '.' || to_char(a.codigo, 'FM00') || '.00' cod_ali, a.descricao desc_ali,  " + 
		"	d.valor_atual " + 
		"from ( " + 
		"	select " + 
		"		unidade_id, substring(regexp_replace(natureza_receita, '[.]', '', 'g'), 1, 6) || '00' nr, descricao,  " + 
		"		sum(case  " + 
		"				when natureza_receita like '9%' and valor_atual > 0 then valor_atual * (-1) " + 
		"				when natureza_receita like '9%' and valor_atual < 0 then valor_atual " + 
		"				else valor_atual " + 
		"			end) valor_atual " + 
		"	from  " + 
		"		prestacao.d002a " + 
		"	where " + 
		"		unidade_id in (:unidades) and " + 
		"		((:modulo is null) or (modulo_id = :modulo)) " + 
		"	group by " + 
		"		unidade_id, substring(regexp_replace(natureza_receita, '[.]', '', 'g'), 1, 6), descricao " + 
		"	order by " + 
		"		unidade_id, substring(regexp_replace(natureza_receita, '[.]', '', 'g'), 1, 6), descricao) d	 " + 
		"left join (select id_natureza_receita, regexp_replace(codigo_natureza_receita, '[.]', '', 'g') codigo_natureza_receita from sae.vw_natureza_receita where codigo_natureza_receita ~ '00$' and ativo = 'S') vw on " + 
		"	vw.codigo_natureza_receita = d.nr " + 
		"left join sae.sae_natureza_receita nr on nr.id_natureza_receita = vw.id_natureza_receita and nr.ativo = 'S'  " + 
		"left join sae.sae_categoria_ec_receita cr on cr.id_categoria_ec_receita = nr.id_categoria_ec_receita and cr.ativo = 'S'  " + 
		"left join sae.sae_origem o on o.id_origem = nr.id_origem and o.ativo = 'S'  " + 
		"left join sae.sae_especie e on e.id_especie = nr.id_especie and e.ativo = 'S'  " + 
		"left join sae.sae_rubrica r on r.id_rubrica = nr.id_rubrica and r.ativo = 'S'  " + 
		"left join sae.sae_alinea a on a.id_alinea = nr.id_alinea and a.ativo = 'S' " + 
		"left join sae.sae_sub_alinea s on s.id_sub_alinea = nr.id_sub_alinea and s.ativo = 'S' " + 
		"order by " + 
		"	cod_cat, cod_ori, cod_esp, cod_rub, cod_ali ";
		
		List<RelatorioD002AVO> listaVo = new ArrayList<>();
		
		List<UnidadeVO> listaUnidadeVO = recuperarUnidades(params);
		List<Integer> listaIdsUnidades = extrairIds(listaUnidadeVO);
		
				
		@SuppressWarnings("unchecked")
		List<Object[]> lista = entityManager.createNativeQuery(sql)
				.setParameter("unidades", listaIdsUnidades)
				.setParameter("modulo", Integer.valueOf(String.valueOf(params.get("modulo"))))
				.getResultList();
		
		for(Object[] l : lista) {
			
			RelatorioD002AVO relatorio = new RelatorioD002AVO();
			
			Optional<UnidadeVO> unidade = listaUnidadeVO.stream().filter(u -> u.getId().equals(Integer.parseInt(String.valueOf(l[0])))).findFirst();
			
			relatorio.setIdUnidade(Integer.parseInt(String.valueOf(l[0])));
			relatorio.setDescricaoUnidade(unidade != null ? unidade.get().getNome().toUpperCase() : "");
			relatorio.setCodigoCategoriaEconomica(l[1].toString());
			relatorio.setDescricaoCategoriaEconomica(l[2].toString());
			relatorio.setCodigoOrigem(l[3].toString());
			relatorio.setDescricaoOrigem(l[4].toString());
			relatorio.setCodigoEspecie(l[5].toString());
			relatorio.setDescricaoEspecie(l[6].toString());
			relatorio.setCodigoRubrica(l[7].toString());
			relatorio.setDescricaoRubrica(l[8].toString());
			relatorio.setCodigoAlinea(l[9].toString());
			relatorio.setDescricaoAlinea(l[10].toString());
			relatorio.setValor(new BigDecimal((new Double(l[11].toString()))));
			listaVo.add(relatorio);
		}
		
		return listaVo;
	}
}
