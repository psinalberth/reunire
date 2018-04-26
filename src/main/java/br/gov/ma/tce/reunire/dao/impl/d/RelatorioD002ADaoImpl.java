package br.gov.ma.tce.reunire.dao.impl.d;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

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
		"	(case " +
		"		when cr.id_categoria_ec_receita is not null then  cr.codigo || '.0.0.0.00.00' " +
		"		else substring(d.nr, 1, 1) || '.0.0.0.00.00' " +
		"	end) cod_cat, cr.descricao desc_cat, " +
		"	(case  " +
		"		when o.id_origem is not null then cr.codigo || '.'|| o.codigo || '.0.0.00.00' " +
		"		else substring(d.nr, 1, 1) || '.' || substring(d.nr, 2, 1) || '.0.0.00.00' " +
		"	end) cod_ori,  " +
		"	o.descricao desc_ori, " +
		"	(case  " +
		"		when e.id_especie is not null then cr.codigo || '.'|| o.codigo || '.' ||  e.codigo || '.0.00.00' " +
		"		else substring(d.nr, 1, 1) || '.' || substring(d.nr, 2, 1) || '.' || substring(d.nr, 3, 1) || '.0.00.00' " +
		"	end) cod_esp, e.descricao desc_esp,  " +
		"	(case  " +
		"		when r.id_rubrica is not null then cr.codigo || '.'|| o.codigo || '.' ||  e.codigo || '.' || r.codigo || '.00.00' " +
		"		else substring(d.nr, 1, 1) || '.' || substring(d.nr, 2, 1) || '.' || substring(d.nr, 3, 1) || '.' || substring(d.nr, 4, 1) || '.00.00' " +
		"	end) cod_rub, r.descricao desc_rub,   " +
		"	(case  " +
		"		when a.id_alinea is not null then cr.codigo || '.'|| o.codigo || '.' ||  e.codigo || '.' || r.codigo || '.' || to_char(a.codigo, 'FM00') || '.00' " +
		"		else substring(d.nr, 1, 1) || '.' || substring(d.nr, 2, 1) || '.' || substring(d.nr, 3, 1) || '.' || substring(d.nr, 4, 1) || '.' || to_char(cast(substring(d.nr, 5, 2) as integer), 'FM00') || '.00' " +
		"	end) cod_ali, a.descricao desc_ali, " +
		"	d.valor_atual, nr.id_natureza_receita " +
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
		"       valor_atual <> 0 and " +
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
		
		List<RelatorioD002AVO> dados = new ArrayList<>();
		
		List<UnidadeVO> listaUnidades = recuperarUnidades(params);
		List<Integer> listaIdsUnidades = extrairIds(listaUnidades);
				
		@SuppressWarnings("unchecked")
		List<Object[]> lista = entityManager.createNativeQuery(sql)
				.setParameter("unidades", listaIdsUnidades)
				.setParameter("modulo", Integer.valueOf(String.valueOf(params.get("modulo"))))
				.getResultList();
		
		for(Object[] row : lista) {
			
			RelatorioD002AVO dado = new RelatorioD002AVO();
			
			Optional<UnidadeVO> unidade = listaUnidades.stream().filter(u -> u.getId().equals(Integer.parseInt(String.valueOf(row[0])))).findFirst();
			
			dado.setIdUnidade(Integer.parseInt(String.valueOf(row[0])));
			dado.setDescricaoUnidade(unidade != null ? unidade.get().getNome().toUpperCase() : "");
			dado.setCodigoCategoriaEconomica(row[1].toString());
			dado.setCodigoOrigem(row[3].toString());
			dado.setCodigoEspecie(row[5].toString());
			dado.setCodigoRubrica(row[7].toString());
			dado.setCodigoAlinea(row[9].toString());
			dado.setValor(toBigDecimal(row[11]));
			
			if (row[12] == null) {
				
				try {
					
					Object categoria = entityManager.createNativeQuery("select descricao from sae.vw_natureza_receita where codigo_natureza_receita = :natureza and ativo = 'S'")
							.setParameter("natureza", dado.getCodigoCategoriaEconomica())
							.getSingleResult();
					
					if (categoria != null) {
						dado.setDescricaoCategoriaEconomica(String.valueOf(categoria));
					}
					
				} catch (NoResultException ex) {
					dado.setDescricaoCategoriaEconomica("CLASSIFICAÇÃO DESCONHECIDA");
				}
				
				try {
					
					Object origem = entityManager.createNativeQuery("select descricao from sae.vw_natureza_receita where codigo_natureza_receita = :natureza and ativo = 'S'")
							.setParameter("natureza", dado.getCodigoOrigem())
							.getSingleResult();
					
					if (origem != null) {
						dado.setDescricaoOrigem(String.valueOf(origem));
					}
					
				} catch (NoResultException ex) {
					dado.setDescricaoOrigem("CLASSIFICAÇÃO DESCONHECIDA");
				}
				
				try {
					
					Object especie = entityManager.createNativeQuery("select descricao from sae.vw_natureza_receita where codigo_natureza_receita = :natureza and ativo = 'S'")
							.setParameter("natureza", dado.getCodigoEspecie())
							.getSingleResult();
					
					if (especie != null) {
						dado.setDescricaoEspecie(String.valueOf(especie));
					}
					
				} catch (NoResultException ex) {
					dado.setDescricaoEspecie("CLASSIFICAÇÃO DESCONHECIDA");
				}
				
				try {
					
					Object rubrica = entityManager.createNativeQuery("select descricao from sae.vw_natureza_receita where codigo_natureza_receita = :natureza and ativo = 'S'")
							.setParameter("natureza", dado.getCodigoRubrica())
							.getSingleResult();
					
					if (rubrica != null) {
						dado.setDescricaoRubrica(String.valueOf(rubrica));
					}
					
				} catch (NoResultException ex) {
					dado.setDescricaoRubrica("CLASSIFICAÇÃO DESCONHECIDA");
				}
				
				try {
					
					Object alinea = entityManager.createNativeQuery("select descricao from sae.vw_natureza_receita where codigo_natureza_receita = :natureza and ativo = 'S'")
							.setParameter("natureza", dado.getCodigoAlinea())
							.getSingleResult();
					
					if (alinea != null) {
						dado.setDescricaoAlinea(String.valueOf(alinea));
					}
					
				} catch (NoResultException ex) {
					dado.setDescricaoAlinea("CLASSIFICAÇÃO DESCONHECIDA");
				}
				
			} else {
			
				dado.setDescricaoCategoriaEconomica(row[2].toString());
				dado.setDescricaoOrigem(row[4].toString());
				dado.setDescricaoEspecie(row[6].toString());
				dado.setDescricaoRubrica(row[8].toString());
				dado.setDescricaoAlinea(row[10].toString());
			}
			
			dados.add(dado);
		}
		
		return dados;
	}
}
